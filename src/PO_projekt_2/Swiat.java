package PO_projekt_2;
import java.io.IOException;
import java.util.*;
import java.io.File;
import java.io.PrintWriter;

import PO_projekt_2.zwierzeta.Czlowiek;

public class Swiat
{
    private final int sizeX;
    private final int sizeY;
    private int numer_tury;
    private SwiatSwing swiat_swing;
    private Organizm[][] plansza;
    private final ArrayList<Organizm> lista_organizmow;
    private Czlowiek czlowiek;
    private boolean czy_czlowiek_zyje;
    private boolean czy_jest_koniec_gry;

    private void sort_list()
    {
        lista_organizmow.sort((organizm1, organizm2) -> { // lambda wyrażenie
            if (organizm1.get_inicjatywa() != organizm2.get_inicjatywa())
                return Integer.compare(organizm2.get_inicjatywa(), organizm1.get_inicjatywa()); // inicjatywa większa
            else
                return Integer.compare(organizm1.get_tura_urodzenia(), organizm2.get_tura_urodzenia()); // tura urodzenia mniejsza
        });
    }

    public Swiat(int sizeX, int sizeY, SwiatSwing swiat_swing)
    {
        this.swiat_swing = swiat_swing;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        numer_tury = 0;
        czy_jest_koniec_gry = false;
        czy_czlowiek_zyje = true;
        plansza = new Organizm[sizeY][sizeX];
        for (int i = 0; i < sizeY; i++)
            for (int j = 0; j < sizeX; j++)
                plansza[i][j] = null;
        lista_organizmow = new ArrayList<>();
    }

    public Swiat(SwiatSwing swiat_swing)
    {
        this.swiat_swing = swiat_swing;
        this.sizeX = 0;
        this.sizeY = 0;
        numer_tury = 0;
        czy_jest_koniec_gry = false;
        czy_czlowiek_zyje = true;
        lista_organizmow = new ArrayList<>();
    }

    public int get_rozmiar_x()                                      { return sizeX; }
    public int get_rozmiar_y()                                      { return sizeY; }
    public boolean czy_miejsce_zajete(Organizm.Koordynaty pole)     { return plansza[pole.getY()][pole.getX()] != null; }
    public Organizm co_na_polu(Organizm.Koordynaty pole)            { return plansza[pole.getY()][pole.getX()]; }
    public Organizm[][] getPlansza()                                { return plansza; }
    public Czlowiek getCzlowiek()                                   { return czlowiek; }
    public int get_numer_tury()                                     { return numer_tury; }
    public boolean get_czy_czlowiek_zyje()                          { return czy_czlowiek_zyje; }
    public void set_czy_jest_koniec_gry(boolean czy_jest_koniec_gry){ this.czy_jest_koniec_gry = czy_jest_koniec_gry; }
    public SwiatSwing get_swiat_Swing()                             { return swiat_swing; }
    public void set_swiat_Swing(SwiatSwing swiat_swing)             { this.swiat_swing = swiat_swing; }

    public void zapisz_swiat(String nazwa_pliku)
    {
        try
        {
            nazwa_pliku += ".txt";
            File file = new File(nazwa_pliku);
            file.createNewFile();
            PrintWriter print_writer = new PrintWriter(file);
            print_writer.print(sizeX + " ");
            print_writer.print(sizeY + " ");
            print_writer.print(numer_tury + " ");
            print_writer.print(czy_czlowiek_zyje + " ");
            print_writer.print(czy_jest_koniec_gry + "\n");
            for (Organizm organizm : lista_organizmow)
            {
                print_writer.print(organizm.get_typ_organizmu() + " ");
                print_writer.print(organizm.get_koordynaty().getX() + " ");
                print_writer.print(organizm.get_koordynaty().getY() + " ");
                print_writer.print(organizm.get_sila() + " ");
                print_writer.print(organizm.get_tura_urodzenia() + " ");
                print_writer.print(organizm.get_czy_zyje());
                if (organizm.get_typ_organizmu() == Organizm.TypOrganizmu.CZLOWIEK)
                {
                    print_writer.print(" " + czlowiek.get_umiejetnosc().get_czy_umiejetnosc_aktywna() + " ");
                    print_writer.print(czlowiek.get_umiejetnosc().get_czy_mozna_aktywowac() + " ");
                    print_writer.print(czlowiek.get_umiejetnosc().get_czas_trwania_umiejetnosci() + " ");
                    print_writer.print(czlowiek.get_umiejetnosc().get_czas_aktywacji());
                }
                print_writer.println();
            }
            print_writer.close();
        }
        catch (IOException exception)
        {
            System.out.println("Error: " + exception);
        }
    }

    public static Swiat wczytaj_swiat(String nazwa_pliku)
    {
        try
        {
            nazwa_pliku += ".txt";
            File file = new File(nazwa_pliku);
            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            String[] dane = line.split(" ");
            int sizeX = Integer.parseInt(dane[0]);
            int sizeY = Integer.parseInt(dane[1]);
            Swiat nowy_swiat = new Swiat(sizeX, sizeY, null);
            int numer_tury = Integer.parseInt(dane[2]);                 nowy_swiat.numer_tury = numer_tury;
            boolean czy_czlowiek_zyje = Boolean.parseBoolean(dane[3]);  nowy_swiat.czy_czlowiek_zyje = czy_czlowiek_zyje;
            boolean czy_jest_koniec_gry = Boolean.parseBoolean(dane[4]);nowy_swiat.czy_jest_koniec_gry = czy_jest_koniec_gry;
            nowy_swiat.czlowiek = null;

            while (scanner.hasNextLine())
            {
                line = scanner.nextLine();
                dane = line.split(" ");
                Organizm.TypOrganizmu TypOrganizmu = Organizm.TypOrganizmu.valueOf(dane[0]);
                int x = Integer.parseInt(dane[1]);
                int y = Integer.parseInt(dane[2]);

                Organizm tmpOrganizm = TworzenieOrganizmow.StworzNowyOrganizm(nowy_swiat, TypOrganizmu, new Organizm.Koordynaty(x, y));
                int sila = Integer.parseInt(dane[3]);             tmpOrganizm.set_sila(sila);
                int tura_urodzenia = Integer.parseInt(dane[4]);   tmpOrganizm.set_tura_urodzenia(tura_urodzenia);
                boolean czy_zyje = Boolean.parseBoolean(dane[5]); tmpOrganizm.set_czy_zyje(czy_zyje);
                if (TypOrganizmu == Organizm.TypOrganizmu.CZLOWIEK)
                {
                    nowy_swiat.czlowiek = (Czlowiek) tmpOrganizm;
                    boolean czy_aktywna = Boolean.parseBoolean(dane[6]);        nowy_swiat.czlowiek.get_umiejetnosc().set_czy_umiejetnosc_aktywna(czy_aktywna);
                    boolean czy_mozna_aktywowac = Boolean.parseBoolean(dane[7]);nowy_swiat.czlowiek.get_umiejetnosc().set_czy_mozna_aktywowac(czy_mozna_aktywowac);
                    int czas_trwania = Integer.parseInt(dane[8]);               nowy_swiat.czlowiek.get_umiejetnosc().set_czas_trwania_umiejetnosci(czas_trwania);
                    int dostepny_czas_aktywacji = Integer.parseInt(dane[9]);    nowy_swiat.czlowiek.get_umiejetnosc().set_czas_aktywacji(dostepny_czas_aktywacji);
                }
                nowy_swiat.pchnij_na_liste(tmpOrganizm);
            }
            scanner.close();
            return nowy_swiat;
        }
        catch (IOException exception)
        {
            System.out.println("Error: " + exception);
        }
        return null;
    }

    public void stworz_swiat(double zapelnienie)
    {
        int ile_organizmow = (int)Math.floor(400 * zapelnienie);
        Organizm.Koordynaty koordynaty = losuj_wolne_pole();
        Organizm temp = TworzenieOrganizmow.StworzNowyOrganizm(this, Organizm.TypOrganizmu.CZLOWIEK, koordynaty);
        pchnij_na_liste(temp);
        czlowiek = (Czlowiek) temp;
        for (int i = 0; i < ile_organizmow - 1; i++)
        {
            koordynaty = losuj_wolne_pole();
            if (!koordynaty.equals(new Organizm.Koordynaty(-1, -1)))
                pchnij_na_liste(TworzenieOrganizmow.StworzNowyOrganizm(this, Organizm.daj_dowolny_organizm(), koordynaty));
            else return;
        }
    }

    public void wykonaj_ture()
    {
        if (czy_jest_koniec_gry) return;
        numer_tury++;
        Informator.dodaj_informacje("Numer tury: " + numer_tury + "\n");
        sort_list();
        for (int i = 0; i < lista_organizmow.size(); i++)
            if (lista_organizmow.get(i).get_tura_urodzenia() != numer_tury && lista_organizmow.get(i).get_czy_zyje())
                lista_organizmow.get(i).akcja();
        int i = 0;
        while (i < lista_organizmow.size())
        {
            if (!lista_organizmow.get(i).get_czy_zyje())
            {
                lista_organizmow.remove(i);
                i--;
            }
            i++;
        }
        for (Organizm organizm : lista_organizmow)
            organizm.set_czy_sie_rozmnazal(false);
    }

    public Organizm.Koordynaty losuj_wolne_pole()
    {
        Random rand = new Random();
        for (int i = 0; i < sizeY; i++)
        {
            for (int j = 0; j < sizeX; j++)
            {
                if (plansza[i][j] == null)
                {
                    while (true)
                    {
                        int x = rand.nextInt(sizeX);
                        int y = rand.nextInt(sizeY);
                        if (plansza[y][x] == null)
                            return new Organizm.Koordynaty(x, y);
                    }
                }
            }
        }
        return new Organizm.Koordynaty(-1, -1);
    }

    public void pchnij_na_liste(Organizm organizm)
    {
        lista_organizmow.add(organizm);
        plansza[organizm.get_koordynaty().getY()][organizm.get_koordynaty().getX()] = organizm;
    }

    public void usun_organizm_ze_swiata(Organizm organizm)
    {
        plansza[organizm.get_koordynaty().getY()][organizm.get_koordynaty().getX()] = null;
        organizm.set_czy_zyje(false);
        if (organizm.get_typ_organizmu() == Organizm.TypOrganizmu.CZLOWIEK)
        {
            czy_czlowiek_zyje = false;
            czlowiek = null;
        }
    }
}