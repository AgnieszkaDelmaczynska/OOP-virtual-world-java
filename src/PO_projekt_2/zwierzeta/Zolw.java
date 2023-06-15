package PO_projekt_2.zwierzeta;
import PO_projekt_2.Swiat;
import PO_projekt_2.Organizm;
import PO_projekt_2.Zwierze;
import PO_projekt_2.Informator;
import java.awt.*;
import java.util.Random;

public class Zolw extends Zwierze
{
    private static final int ZOLW_SILA = 2;
    private static final int ZOLW_INICJATYWA = 1;

    public Zolw(Swiat swiat, Koordynaty koordynaty, int tura_urodzenia)
    {
        super(swiat, TypOrganizmu.ZOLW, ZOLW_SILA, ZOLW_INICJATYWA, koordynaty, tura_urodzenia);
        set_kolor(new Color(147, 255, 138));
    }

    @Override
    public String typ_organizmu_to_string()
    {
        return "Zolw";
    }

    @Override
    public boolean supermoc(Organizm wykonujacy_akcje, Organizm atakowany)
    {
        if (this == wykonujacy_akcje)
        {
            if (wykonujacy_akcje.get_sila() >= atakowany.get_sila()) return false;
            else
            {
                if (atakowany.get_sila() < 5 && atakowany.czy_zwierze())
                {
                    Informator.dodaj_informacje(OrganizmToSring() + " odgania " + atakowany.OrganizmToSring());
                    return true;
                }
                else return false;
            }
        }
        else
        {
            if (wykonujacy_akcje.czy_zwierze() && wykonujacy_akcje.get_sila() < 5)
            {
                Informator.dodaj_informacje(OrganizmToSring() + " odgania " + wykonujacy_akcje.OrganizmToSring());
                return true;
            }
            else
                return false;
        }

    }
    @Override
    protected Koordynaty planowanie_przyszlego_ruchu()
    {
        Random rand = new Random();
        if (rand.nextInt(100) < 25)
            return daj_losowe_pole(get_koordynaty());
        else
            return get_koordynaty();
    }
}