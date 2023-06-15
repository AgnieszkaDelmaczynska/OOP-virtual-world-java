package PO_projekt_2.zwierzeta;
import PO_projekt_2.Swiat;
import PO_projekt_2.Zwierze;
import java.awt.*;

public class Owca extends Zwierze
{
    private static final int OWCA_SILA = 4;
    private static final int OWCA_INICJATYWA = 4;

    public Owca(Swiat swiat, Koordynaty koordynaty, int tura_urodzenia)
    {
        super(swiat, TypOrganizmu.OWCA, OWCA_SILA, OWCA_INICJATYWA, koordynaty, tura_urodzenia);
        set_kolor(new Color(253, 249, 164));
    }

    @Override
    public String typ_organizmu_to_string()
    {
        return "Owca";
    }
}