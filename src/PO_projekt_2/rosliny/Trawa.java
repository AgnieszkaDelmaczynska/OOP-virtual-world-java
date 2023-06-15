package PO_projekt_2.rosliny;
import PO_projekt_2.Swiat;
import PO_projekt_2.Roslina;
import java.awt.*;

public class Trawa extends Roslina
{
    private static final int TRAWa_SILA = 0;
    private static final int TRAWa_INICJATYWA = 0;

    public Trawa(Swiat swiat, Koordynaty koordynaty, int tura_urodzenia)
    {
        super(swiat, TypOrganizmu.TRAWA, TRAWa_SILA, TRAWa_INICJATYWA, koordynaty, tura_urodzenia);
        set_kolor(new Color(120, 193, 146));
    }

    @Override
    public String typ_organizmu_to_string()
    {
        return "Trawa";
    }
}