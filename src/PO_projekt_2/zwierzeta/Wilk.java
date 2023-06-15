package PO_projekt_2.zwierzeta;
import PO_projekt_2.Swiat;
import PO_projekt_2.Zwierze;
import java.awt.*;

public class Wilk extends Zwierze
{
    private static final int WILK_SILA = 9;
    private static final int WILK_INICJATYWA = 5;

    public Wilk(Swiat swiat, Koordynaty koordynaty, int tura_urodzenia)
    {
        super(swiat, TypOrganizmu.WILK, WILK_SILA, WILK_INICJATYWA, koordynaty, tura_urodzenia);
        set_kolor(new Color(121, 121, 121));
    }

    @Override
    public String typ_organizmu_to_string()
    {
        return "Wilk";
    }
}