package PO_projekt_2.rosliny;
import PO_projekt_2.Swiat;
import PO_projekt_2.Organizm;
import PO_projekt_2.Roslina;
import PO_projekt_2.Informator;
import java.awt.*;

public class Guarana extends Roslina
{
    private static final int GUARANA_SILA = 0;
    private static final int GUARANA_INICJATYWA = 0;

    public Guarana(Swiat swiat, Koordynaty koordynaty, int tura_urodzenia)
    {
        super(swiat, TypOrganizmu.GUARANA, GUARANA_SILA, GUARANA_INICJATYWA, koordynaty, tura_urodzenia);
        set_kolor(new Color(236, 167, 255));
    }

    @Override
    public String typ_organizmu_to_string()
    {
        return "Guarana";
    }

    @Override
    public boolean supermoc(Organizm wykonujacy_akcje, Organizm atakowany)
    {
        Informator.dodaj_informacje(wykonujacy_akcje.OrganizmToSring() + " zjada " + this.OrganizmToSring());
        Koordynaty koordynaty = this.get_koordynaty();
        get_swiat().usun_organizm_ze_swiata(this);
        wykonujacy_akcje.wykonaj_ruch(koordynaty);
        wykonujacy_akcje.set_sila(wykonujacy_akcje.get_sila() + 3);
        Informator.dodaj_informacje("jego obecna siła wynosi: "+ wykonujacy_akcje.get_sila());
        return true;
    }
}