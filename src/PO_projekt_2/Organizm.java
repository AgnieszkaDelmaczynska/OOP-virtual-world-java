package PO_projekt_2;
import java.awt.*;
import java.util.Random;

public abstract class Organizm
{
    private Color kolor;
    private Swiat swiat;
    private final Koordynaty koordynaty;
    private TypOrganizmu typOrganizmu;
    private int sila;
    private int inicjatywa;
    private int tura_urodzenia;
    private boolean czy_zyje;
    protected final boolean[] kierunek;
    private boolean czy_sie_rozmnazal;

    public abstract void akcja();
    public abstract void kolizja(Organizm other);
    public abstract String typ_organizmu_to_string();
    public abstract boolean czy_zwierze();

    public Organizm(Swiat swiat, TypOrganizmu typOrganizmu, int sila, int inicjatywa, Organizm.Koordynaty koordynaty, int tura_urodzenia) //konstruktor organizmu, nadpisywany w innych klasach za pomoca słowa kluczowego "super();"
    {
        this.swiat = swiat;
        this.typOrganizmu = typOrganizmu;
        this.sila = sila;
        this.inicjatywa = inicjatywa;
        this.koordynaty = koordynaty;
        this.tura_urodzenia = tura_urodzenia;
        czy_zyje = true;
        kierunek = new boolean[]{true, true, true, true};
        set_czy_sie_rozmnazal(false);
    }

    public Swiat get_swiat()                                    { return swiat; }
    public void setSwiat(Swiat swiat)                           { this.swiat = swiat; }

    public Koordynaty get_koordynaty()                          { return koordynaty; }

    public TypOrganizmu get_typ_organizmu()                     { return typOrganizmu; }
    public void setTypOrganizmu(TypOrganizmu typOrganizmu)      { this.typOrganizmu = typOrganizmu; }

    // gettery i settery do prywatnych pól: siła, inicjatywa, tura urodzenia, czy_zyje, czy_sie_rozmnazal, kolor

    public int get_sila()                                       { return sila; }
    public void set_sila(int sila)                              { this.sila = sila; }

    public int get_inicjatywa()                                 { return inicjatywa; }
    public void setInicjatywa(int inicjatywa)                   { this.inicjatywa = inicjatywa; }

    public int get_tura_urodzenia()                             { return tura_urodzenia; }
    public void set_tura_urodzenia(int tura_urodzenia)          { this.tura_urodzenia = tura_urodzenia; }

    public boolean get_czy_zyje()                               { return czy_zyje; }
    public void set_czy_zyje(boolean czy_zyje)                  { this.czy_zyje = czy_zyje; }

    public boolean get_czy_sie_rozmnazal()                      { return czy_sie_rozmnazal; }
    public void set_czy_sie_rozmnazal(boolean czy_sie_rozmnazal){ this.czy_sie_rozmnazal = czy_sie_rozmnazal; }

    public Color get_kolor()                                    { return kolor;}
    public void set_kolor(Color kolor)                          {this.kolor = kolor;}

    public enum Kierunek    // enum wszystkich kierunków, w tym braku kierunku, gdyy wszystkie są zajęte
    {
        PRAWO(0),
        LEWO(1),
        GORA(2),
        DOL(3),
        BRAK_KIERUNKU(4);
        private final int kierunek;
        Kierunek(int kierunek)     { this.kierunek = kierunek; }
        public int getValue()      { return kierunek; }
    }

    public enum TypOrganizmu    // enum wszystkich typów organizmów, które występuja na świecie
    {
        CZLOWIEK,
        WILK,
        OWCA,
        LIS,
        ZOLW,
        ANTYLOPA,
        CYBER_OWCA,
        TRAWA,
        MLECZ,
        GUARANA,
        WILCZE_JAGODY,
        BARSZCZ_SOSNOWSKIEGO
    }

    static TypOrganizmu daj_dowolny_organizm() // losuje dowolny tep organizmu, użyte przy dodawaniu organizmów do planszy gry
    {
        Random rand = new Random();
        int tmp = rand.nextInt(11);
        switch(tmp)
        {
            case 0: return TypOrganizmu.WILK;
            case 1: return TypOrganizmu.OWCA;
            case 2: return TypOrganizmu.CYBER_OWCA;
            case 3: return TypOrganizmu.LIS;
            case 4: return TypOrganizmu.ZOLW;
            case 5: return TypOrganizmu.ANTYLOPA;
            case 6: return TypOrganizmu.TRAWA;
            case 7: return TypOrganizmu.MLECZ;
            case 8: return TypOrganizmu.GUARANA;
            case 9: return TypOrganizmu.WILCZE_JAGODY;
            case 10: return TypOrganizmu.BARSZCZ_SOSNOWSKIEGO;
        }
        return TypOrganizmu.WILK;
    }

    public String OrganizmToSring() // informacje wyświetlane o każdym organizmie: pozycja, siła, inicjatywa
    {
        return (typ_organizmu_to_string() + " x[" + koordynaty.getX() + "] y[" + koordynaty.getY() + "] (sila: " + sila + ") (inicjatywa: " + inicjatywa + ")");
    }

    public boolean supermoc(Organizm wykonujacy_akcje, Organizm atakowany) // supermoc nadpisywana w poszczególnych organizmach, wywoływana podczas kolizi
    {
        return false;
    }

    public static class Koordynaty // publiczna klasa, używana do nadania i określenia pozycji organizmów
    {
        private int pozycja_x;
        private int pozycja_y;
        public int getX()               { return pozycja_x; }
        public int getY()               { return pozycja_y; }
        public void setX(int x)         { this.pozycja_x = x; }
        public void setY(int y)         { this.pozycja_y = y; }
        public Koordynaty()             { pozycja_x = 0; pozycja_y = 0; }
        public Koordynaty(int x, int y) { this.pozycja_x = x; this.pozycja_y = y; }

        @Override
        public boolean equals(Object obiekt_do_porownania)
        {
            if (!(obiekt_do_porownania instanceof PO_projekt_2.Organizm.Koordynaty))  return false;
            if (obiekt_do_porownania == this)                                         return true;
            Koordynaty obj_do_porownania = (Koordynaty)obiekt_do_porownania;
            return pozycja_x == obj_do_porownania.pozycja_x && pozycja_y == obj_do_porownania.pozycja_y;
        }
    }

    public void wykonaj_ruch(Koordynaty zaplanowane_przyszle_miejsce) // wykonuje ruch na zaplanowane przyszłe miejsce
    {
        int wspolrzedna_x = zaplanowane_przyszle_miejsce.getX();
        int wspolrzedna_y = zaplanowane_przyszle_miejsce.getY();
        swiat.getPlansza()[koordynaty.getY()][koordynaty.getX()] = null;
        swiat.getPlansza()[wspolrzedna_y][wspolrzedna_x] = this;
        koordynaty.setX(wspolrzedna_x);
        koordynaty.setY(wspolrzedna_y);
    }

    public Koordynaty daj_losowe_pole(Koordynaty koordynaty) // losuje pole dowolne, może być zajęte
    {                                                        // użyte do planowania losowego ruchu zwierząt
        odblokuj_wszystkie_kierunki();
        int wspolrzedna_x = koordynaty.getX();
        int wspolrzedna_y = koordynaty.getY();
        int ile_wolnych = 0; //kierunki, które są możliwe, by wykonać ruch

        if (wspolrzedna_x == 19) zablokuj_kierunek(Kierunek.PRAWO); else ile_wolnych++;
        if (wspolrzedna_x == 0) zablokuj_kierunek(Kierunek.LEWO);   else ile_wolnych++;
        if (wspolrzedna_y == 0) zablokuj_kierunek(Kierunek.GORA);   else ile_wolnych++;
        if (wspolrzedna_y == 19) zablokuj_kierunek(Kierunek.DOL);   else ile_wolnych++;
        if (ile_wolnych == 0) return koordynaty; // nie udało się przestawić organizmu, zostaje na swoim miejscu

        while (true)
        {
            Random rand = new Random();
            int wylosowany = rand.nextInt(4);
            if (wylosowany == 0 && !kierunek_zablokowany(Kierunek.PRAWO))      return new Koordynaty(wspolrzedna_x + 1, wspolrzedna_y);
            else if (wylosowany == 1 && !kierunek_zablokowany(Kierunek.LEWO))  return new Koordynaty(wspolrzedna_x - 1, wspolrzedna_y);
            else if (wylosowany == 2 && !kierunek_zablokowany(Kierunek.GORA))  return new Koordynaty(wspolrzedna_x, wspolrzedna_y - 1);
            else if (wylosowany == 3 && !kierunek_zablokowany(Kierunek.DOL))   return new Koordynaty(wspolrzedna_x, wspolrzedna_y + 1);
        }
    }

    public Koordynaty daj_niezajete_pole(Koordynaty koordynaty) // losuje pole niezajęte
    {                                                           // użyte do rozmnażania się zwierząt i roślin, potrzebuja znaleźć losowe niezajęte pole, by wykonać ruch
        odblokuj_wszystkie_kierunki();
        int wspolrzedna_x = koordynaty.getX();
        int wspolrzedna_y = koordynaty.getY();
        int ile_wolnych = 0; //kierunki, które są możliwe, by wykonać ruch

        if (wspolrzedna_x == 19) zablokuj_kierunek(Kierunek.PRAWO);
        else
        {
            if (!swiat.czy_miejsce_zajete(new Koordynaty(wspolrzedna_x + 1, wspolrzedna_y))) ile_wolnych++;
            else zablokuj_kierunek(Kierunek.PRAWO);
        }

        if (wspolrzedna_x == 0) zablokuj_kierunek(Kierunek.LEWO);
        else
        {
            if (!swiat.czy_miejsce_zajete(new Koordynaty(wspolrzedna_x - 1, wspolrzedna_y))) ile_wolnych++;
            else zablokuj_kierunek(Kierunek.LEWO);
        }

        if (wspolrzedna_y == 0) zablokuj_kierunek(Kierunek.GORA);
        else
        {
            if (!swiat.czy_miejsce_zajete(new Koordynaty(wspolrzedna_x, wspolrzedna_y - 1))) ile_wolnych++;
            else zablokuj_kierunek(Kierunek.GORA);
        }

        if (wspolrzedna_y == 19) zablokuj_kierunek(Kierunek.DOL);
        else
        {
            if (!swiat.czy_miejsce_zajete(new Koordynaty(wspolrzedna_x, wspolrzedna_y + 1))) ile_wolnych++;
            else zablokuj_kierunek(Kierunek.DOL);
        }

        if (ile_wolnych == 0) return new Koordynaty(wspolrzedna_x, wspolrzedna_y);
        while (true)
        {
            Random rand = new Random();
            int wylosowany = rand.nextInt(4);
            if (wylosowany == 0 && !kierunek_zablokowany(Kierunek.PRAWO))      return new Koordynaty(wspolrzedna_x + 1, wspolrzedna_y);
            else if (wylosowany == 1 && !kierunek_zablokowany(Kierunek.LEWO))  return new Koordynaty(wspolrzedna_x - 1, wspolrzedna_y);
            else if (wylosowany == 2 && !kierunek_zablokowany(Kierunek.GORA))  return new Koordynaty(wspolrzedna_x, wspolrzedna_y - 1);
            else if (wylosowany == 3 && !kierunek_zablokowany(Kierunek.DOL))   return new Koordynaty(wspolrzedna_x, wspolrzedna_y + 1);
        }
    }

    protected boolean kierunek_zablokowany(Kierunek kierunek)  { return !(this.kierunek[kierunek.getValue()]); }
    protected void zablokuj_kierunek(Kierunek kierunek)        { this.kierunek[kierunek.getValue()] = false; }
    protected void odblokuj_kierunek(Kierunek kierunek)        { this.kierunek[kierunek.getValue()] = true; }

    protected void odblokuj_wszystkie_kierunki()
    {
        odblokuj_kierunek(Kierunek.PRAWO);
        odblokuj_kierunek(Kierunek.LEWO);
        odblokuj_kierunek(Kierunek.GORA);
        odblokuj_kierunek(Kierunek.DOL);
    }
}