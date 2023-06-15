package PO_projekt_2.zwierzeta;
import PO_projekt_2.Swiat;
import PO_projekt_2.Organizm;
import PO_projekt_2.Zwierze;
import PO_projekt_2.Informator;
import java.awt.*;
import java.util.Random;

public class Antylopa extends Zwierze
{
    private static final int ANTYLOPA_SILA = 4;
    private static final int ANTYLOPA_INICJATYWA = 4;

    public Antylopa(Swiat swiat, Koordynaty koordynaty, int tura_urodzenia)
    {
        super(swiat, TypOrganizmu.ANTYLOPA, ANTYLOPA_SILA, ANTYLOPA_INICJATYWA, koordynaty, tura_urodzenia);
        set_kolor(new Color(106, 191, 253));
    }

    @Override
    public String typ_organizmu_to_string()
    {
        return "Antylopa";
    }

    @Override
    public boolean supermoc(Organizm wykonujacy_akcje, Organizm atakowany)
    {
        Random rand = new Random();
        if (rand.nextInt(100) < 50)
        {
            if (this == wykonujacy_akcje)
            {
                Informator.dodaj_informacje(OrganizmToSring() + " uniknęła kolizji z " + atakowany.OrganizmToSring());
                Koordynaty tmpPozycja = daj_niezajete_pole(atakowany.get_koordynaty());
                if (!tmpPozycja.equals(atakowany.get_koordynaty()))
                    wykonaj_ruch(tmpPozycja);
            }
            else if (this == atakowany)
            {
                Informator.dodaj_informacje(OrganizmToSring() + " uniknęła kolizji z " + wykonujacy_akcje.OrganizmToSring());
                Koordynaty tmpPozycja = this.get_koordynaty();
                wykonaj_ruch(daj_niezajete_pole(this.get_koordynaty()));
                if (get_koordynaty().equals(tmpPozycja))
                {
                    get_swiat().usun_organizm_ze_swiata(this);
                    Informator.dodaj_informacje(wykonujacy_akcje.OrganizmToSring() + " zabija " + OrganizmToSring());
                }
                wykonujacy_akcje.wykonaj_ruch(tmpPozycja);
            }
            return true;
        } else return false;
    }
    @Override
    public void akcja()
    {
        for (int i = 0; i < 2; i++) // zasięg ruchu == 2
        {
            Koordynaty wylosowana_pozycja = planowanie_przyszlego_ruchu();
            if (get_swiat().czy_miejsce_zajete(wylosowana_pozycja))
            {
                if (get_swiat().co_na_polu(wylosowana_pozycja) != this)
                {
                    kolizja(get_swiat().co_na_polu(wylosowana_pozycja)); break;
                }
                else if (get_swiat().co_na_polu(wylosowana_pozycja) != this)
                    wykonaj_ruch(wylosowana_pozycja);
            }
            else if (get_swiat().co_na_polu(wylosowana_pozycja) != this)
                wykonaj_ruch(wylosowana_pozycja);
        }
    }
}