package PO_projekt_2.zwierzeta;
import PO_projekt_2.Swiat;
import PO_projekt_2.Informator;
import PO_projekt_2.Zwierze;
import java.awt.*;
import java.util.Random;

public class Czlowiek extends Zwierze
{
    private static final int CZLOWIEK_SILA = 5;
    private static final int CZLOWIEK_INICJATYWA = 4;
    private Kierunek kierunek_ruchu;
    private final Umiejetnosc umiejetnosc;

    public Czlowiek(Swiat swiat, Koordynaty koordynaty, int tura_urodzenia)
    {
        super(swiat, TypOrganizmu.CZLOWIEK, CZLOWIEK_SILA, CZLOWIEK_INICJATYWA, koordynaty, tura_urodzenia);
        kierunek_ruchu = Kierunek.BRAK_KIERUNKU;
        umiejetnosc = new Umiejetnosc();
        set_kolor(new Color(29, 84, 173));
    }

    @Override
    public String typ_organizmu_to_string()
    {
        return "Człowiek";
    }

    @Override
    public void akcja()
    {
        if (umiejetnosc.get_czy_umiejetnosc_aktywna())
        {
            Informator.dodaj_informacje(OrganizmToSring() + ":\n 'Szybkość antylopy' jest aktywna (pozostały czas trwania: "+
                    + umiejetnosc.get_czas_trwania_umiejetnosci() + " tur)");
            szybkosc_antylopy();
        }
        Koordynaty zaplanowane_przyszle_miejsce = planowanie_przyszlego_ruchu();
        if (get_swiat().czy_miejsce_zajete(zaplanowane_przyszle_miejsce) && get_swiat().co_na_polu(zaplanowane_przyszle_miejsce) != this)
            kolizja(get_swiat().co_na_polu(zaplanowane_przyszle_miejsce));
        else if (get_swiat().co_na_polu(zaplanowane_przyszle_miejsce) != this)
            wykonaj_ruch(zaplanowane_przyszle_miejsce);
        kierunek_ruchu = Kierunek.BRAK_KIERUNKU;
        umiejetnosc.uaktualnij();
    }

    protected void szybkosc_antylopy()
    {
        if (umiejetnosc.get_czas_trwania_umiejetnosci() >= 2)
            przestaw_koordynaty();
        else
        {
            Random rand = new Random();
            int losuj = rand.nextInt(100);
            if (losuj < 50) // szansa, że wylosuje <50 to 50%
                przestaw_koordynaty();
        }
    }

    protected void przestaw_koordynaty()
    {
        get_swiat().getPlansza()[get_koordynaty().getY()][get_koordynaty().getX()] = null;
        if ((kierunek_ruchu == Kierunek.PRAWO) && (this.get_koordynaty().getX() != 20 - 1))
            this.get_koordynaty().setX(this.get_koordynaty().getX() + 1);
        else if ((kierunek_ruchu == Kierunek.LEWO) && (this.get_koordynaty().getX() != 0))
            this.get_koordynaty().setX(this.get_koordynaty().getX() - 1);
        if ((kierunek_ruchu == Kierunek.GORA) && (this.get_koordynaty().getY() != 0))
            this.get_koordynaty().setY(this.get_koordynaty().getY() - 1);
        else if ((kierunek_ruchu == Kierunek.DOL) && (this.get_koordynaty().getY() != 20 - 1))
            this.get_koordynaty().setY(this.get_koordynaty().getY() + 1);
    }

    @Override
    protected Koordynaty planowanie_przyszlego_ruchu()
    {
        int poz_x = get_koordynaty().getX();
        int poz_y = get_koordynaty().getY();
        daj_losowe_pole(get_koordynaty());
        if ((kierunek_ruchu == Kierunek.BRAK_KIERUNKU) || kierunek_zablokowany(kierunek_ruchu))
            return get_koordynaty();
        else
        {
            if (kierunek_ruchu == Kierunek.PRAWO)   return new Koordynaty(poz_x + 1, poz_y);
            if (kierunek_ruchu == Kierunek.LEWO)    return new Koordynaty(poz_x - 1, poz_y);
            if (kierunek_ruchu == Kierunek.GORA)    return new Koordynaty(poz_x, poz_y - 1);
            if (kierunek_ruchu == Kierunek.DOL)     return new Koordynaty(poz_x, poz_y + 1);
            else return new Koordynaty(poz_x, poz_y);
        }
    }

    public class Umiejetnosc
    {
        protected final int CZAS_TRWANIA_UMIEJETNOSCI = 5;
        protected final int DOSTEPNY_CZAS_AKTYWACJI = 10;
        protected boolean czy_aktywna;
        protected boolean czy_mozna_aktywowac;
        protected int czas_trwania;
        protected int dostepny_czas_aktywacji;

        public Umiejetnosc()
        {
            czy_aktywna = false;
            czy_mozna_aktywowac = true;
            czas_trwania = 0;
            dostepny_czas_aktywacji = 0;
        }

        public boolean get_czy_umiejetnosc_aktywna()                        { return czy_aktywna; }
        public void set_czy_umiejetnosc_aktywna(boolean czy_aktywna)        { this.czy_aktywna = czy_aktywna; }

        public boolean get_czy_mozna_aktywowac()                            { return czy_mozna_aktywowac; }
        public void set_czy_mozna_aktywowac(boolean czy_mozna_aktywowac)    { this.czy_mozna_aktywowac = czy_mozna_aktywowac; }

        public int get_czas_trwania_umiejetnosci()                          { return czas_trwania; }
        public void set_czas_trwania_umiejetnosci(int czas_trwania)         { this.czas_trwania = czas_trwania; }

        public int get_czas_aktywacji()                                     { return dostepny_czas_aktywacji; }
        public void set_czas_aktywacji(int dostepny_czas_aktywacji)         { this.dostepny_czas_aktywacji = dostepny_czas_aktywacji; }

        public void uaktualnij()
        {
            if (dostepny_czas_aktywacji > 0) dostepny_czas_aktywacji--;
            if (czas_trwania > 0) czas_trwania--;
            if (czas_trwania == 0) dezaktywuj_umiejetnosc();
            if (dostepny_czas_aktywacji == 0) czy_mozna_aktywowac = true;
        }

        public void aktywuj_umiejetnosc()
        {
            if (dostepny_czas_aktywacji == 0)
            {
                czy_aktywna = true;
                czy_mozna_aktywowac = false;
                dostepny_czas_aktywacji = DOSTEPNY_CZAS_AKTYWACJI;
                czas_trwania = CZAS_TRWANIA_UMIEJETNOSCI;
            }
            else if (dostepny_czas_aktywacji > 0)
            {
                Informator.dodaj_informacje( "\nUmiejetnosc 'Szybkosc antylopy' bedzie mozna wlaczyc po " +
                        + dostepny_czas_aktywacji + " turach\n");
            }
        }
        public void dezaktywuj_umiejetnosc()
        {
            czy_aktywna = false;
        }
    }

    public Kierunek get_kierunek()                      { return kierunek_ruchu;}
    public void set_kierunek(Kierunek kierunek_ruchu)   { this.kierunek_ruchu = kierunek_ruchu; }
    public Umiejetnosc get_umiejetnosc()                { return umiejetnosc; }
}