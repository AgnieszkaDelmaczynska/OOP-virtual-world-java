package PO_projekt_2.rosliny;
import PO_projekt_2.Swiat;
import PO_projekt_2.Organizm;
import PO_projekt_2.Roslina;
import PO_projekt_2.Informator;
import java.awt.*;
import java.util.Random;

public class BarszczSosnowskiego extends Roslina
{
    private static final int BARSZCZ_SOSNOWSKIEGO_SILA = 10;
    private static final int BARSZCZ_SOSNOWSKIEGO_INICJATYWA = 0;

    public BarszczSosnowskiego(Swiat swiat, Koordynaty koordynaty, int tura_urodzenia)
    {
        super(swiat, TypOrganizmu.BARSZCZ_SOSNOWSKIEGO, BARSZCZ_SOSNOWSKIEGO_SILA, BARSZCZ_SOSNOWSKIEGO_INICJATYWA, koordynaty, tura_urodzenia);
        set_kolor(new Color(146, 106, 71));
    }

    @Override
    public String typ_organizmu_to_string()
    {
        return "Barszcz Sosnowskiego";
    }

    @Override
    public void akcja()
    {
        daj_losowe_pole(get_koordynaty());
        int wspolrzedna_x = this.get_koordynaty().getX();
        int wspolrzedna_y = this.get_koordynaty().getY();
        for (int i = 0; i < 4; i++)
        {
            Organizm organizm_na_polu = null;
            switch(i)
            {
                case 0:
                    if (!kierunek_zablokowany(Kierunek.PRAWO))
                        organizm_na_polu = get_swiat().co_na_polu(new Koordynaty(wspolrzedna_x + 1, wspolrzedna_y));
                    break;
                case 1:
                    if (!kierunek_zablokowany(Kierunek.LEWO))
                        organizm_na_polu = get_swiat().co_na_polu(new Koordynaty(wspolrzedna_x - 1, wspolrzedna_y));
                    break;
                case 2:
                    if (!kierunek_zablokowany(Kierunek.GORA))
                        organizm_na_polu = get_swiat().co_na_polu(new Koordynaty(wspolrzedna_x, wspolrzedna_y - 1));
                        break;
                case 3:
                    if (!kierunek_zablokowany(Kierunek.DOL))
                        organizm_na_polu = get_swiat().co_na_polu(new Koordynaty(wspolrzedna_x, wspolrzedna_y + 1));
                        break;
            }
            if ((organizm_na_polu != null) && (organizm_na_polu.czy_zwierze()) && (!organizm_na_polu.typ_organizmu_to_string().equals("Cyberowca")))
            {
                Informator.dodaj_informacje(OrganizmToSring() + " zabija " + organizm_na_polu.OrganizmToSring());
                get_swiat().usun_organizm_ze_swiata(organizm_na_polu);
            }
        }
        rozprzestrzenianie_barszczu();
    }

    @Override
    public boolean supermoc(Organizm wykonujacy_akcje, Organizm atakowany)
    {
        if ((wykonujacy_akcje.get_sila() < BARSZCZ_SOSNOWSKIEGO_SILA) || (wykonujacy_akcje.czy_zwierze() && (!wykonujacy_akcje.typ_organizmu_to_string().equals("Cyberowca"))))
        {
            Informator.dodaj_informacje(this.OrganizmToSring() + " zabija " + wykonujacy_akcje.OrganizmToSring());
            get_swiat().usun_organizm_ze_swiata(wykonujacy_akcje);
        }
        if (wykonujacy_akcje.get_sila() >= BARSZCZ_SOSNOWSKIEGO_SILA)
        {
            Informator.dodaj_informacje(wykonujacy_akcje.OrganizmToSring() + " zjada " + this.OrganizmToSring());
            get_swiat().usun_organizm_ze_swiata(this);
            wykonujacy_akcje.wykonaj_ruch(atakowany.get_koordynaty());
        }
        return true;
    }

    public void rozprzestrzenianie_barszczu()
    {
        Random rand = new Random();
        int wylosowany = rand.nextInt(100);
        if (wylosowany < 10) rozprzestrzenianie();
    }
}