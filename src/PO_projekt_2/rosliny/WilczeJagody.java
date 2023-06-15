package PO_projekt_2.rosliny;
import PO_projekt_2.Swiat;
import PO_projekt_2.Organizm;
import PO_projekt_2.Roslina;
import PO_projekt_2.Informator;
import java.awt.*;

public class WilczeJagody extends Roslina
{
    private static final int WILCZE_JAGODY_SILA = 99;
    private static final int WILCZE_JAGODY_INICJATYWA = 0;

    public WilczeJagody(Swiat swiat, Koordynaty koordynaty, int tura_urodzenia)
    {
        super(swiat, TypOrganizmu.WILCZE_JAGODY, WILCZE_JAGODY_SILA, WILCZE_JAGODY_INICJATYWA, koordynaty, tura_urodzenia);
        set_kolor(new Color(173, 130, 255));
    }

    @Override
    public String typ_organizmu_to_string()
    {
        return "Wilcze jagody";
    }

    @Override
    public boolean supermoc(Organizm wykonujacy_akcje, Organizm atakowany)
    {
        Informator.dodaj_informacje(wykonujacy_akcje.OrganizmToSring() + " zjada " + this.OrganizmToSring());
        if (wykonujacy_akcje.czy_zwierze())
        {
            Informator.dodaj_informacje(this.OrganizmToSring() + " zabijają " + wykonujacy_akcje.OrganizmToSring());
            get_swiat().usun_organizm_ze_swiata(wykonujacy_akcje);
        }
        if (wykonujacy_akcje.get_sila() >= WILCZE_JAGODY_SILA)
        {
            Informator.dodaj_informacje(wykonujacy_akcje.OrganizmToSring() + " zabija " + this.OrganizmToSring());
            get_swiat().usun_organizm_ze_swiata(this);
        }
        return true;
    }
}