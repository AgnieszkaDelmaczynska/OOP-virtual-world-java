package PO_projekt_2.rosliny;
import PO_projekt_2.Swiat;
import PO_projekt_2.Roslina;
import java.awt.*;
import java.util.Random;

public class Mlecz extends Roslina
{
    private static final int MLECZ_SILA = 0;
    private static final int MLECZ_INICJATYWA = 0;
    private static final int LICZBA_PROB_ROZPRZESTRZENIANIA = 3;

    public Mlecz(Swiat swiat, Koordynaty koordynaty, int tura_urodzenia)
    {
        super(swiat, TypOrganizmu.MLECZ, MLECZ_SILA, MLECZ_INICJATYWA, koordynaty, tura_urodzenia);
        set_kolor(new Color(253, 237, 74));
    }

    @Override
    public String typ_organizmu_to_string()
    {
        return "Mlecz";
    }

    @Override
    public void akcja()
    {
        Random rand = new Random();
        for (int i = 0; i < LICZBA_PROB_ROZPRZESTRZENIANIA; i++)
        {
            if (rand.nextInt(100) < 50)
                rozprzestrzenianie();
        }
    }
}