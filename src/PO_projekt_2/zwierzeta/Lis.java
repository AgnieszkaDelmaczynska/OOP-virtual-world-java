package PO_projekt_2.zwierzeta;
import PO_projekt_2.Swiat;
import PO_projekt_2.Organizm;
import PO_projekt_2.Zwierze;
import java.awt.*;
import java.util.Random;

public class Lis extends Zwierze
{
    private static final int LIS_SILA = 3;
    private static final int LIS_INICJATYWA = 7;
    public Lis(Swiat swiat, Koordynaty koordynaty, int tura_urodzenia)
    {
        super(swiat, TypOrganizmu.LIS, LIS_SILA, LIS_INICJATYWA, koordynaty, tura_urodzenia);
        set_kolor(new Color(255, 186, 120));
    }

    @Override
    public String typ_organizmu_to_string() {
        return "Lis";
    }

    @Override
    public Koordynaty daj_losowe_pole(Koordynaty koordynaty) {
        odblokuj_wszystkie_kierunki();
        int pozX = koordynaty.getX();
        int pozY = koordynaty.getY();
        int sizeX = get_swiat().get_rozmiar_x();
        int sizeY = get_swiat().get_rozmiar_y();
        int ile_wolnych = 0;
        Organizm tmpOrganizm;

        if (pozX == 0) zablokuj_kierunek(Kierunek.LEWO);
        else {
            tmpOrganizm = get_swiat().getPlansza()[pozY][pozX - 1];
            if (tmpOrganizm != null && tmpOrganizm.get_sila() > this.get_sila()) {
                zablokuj_kierunek(Kierunek.LEWO);
            } else ile_wolnych++;
        }

        if (pozX == sizeX - 1) zablokuj_kierunek(Kierunek.PRAWO);
        else {
            tmpOrganizm = get_swiat().getPlansza()[pozY][pozX + 1];
            if (tmpOrganizm != null && tmpOrganizm.get_sila() > this.get_sila()) {
                zablokuj_kierunek(Kierunek.PRAWO);
            } else ile_wolnych++;
        }

        if (pozY == 0) zablokuj_kierunek(Kierunek.GORA);
        else {
            tmpOrganizm = get_swiat().getPlansza()[pozY - 1][pozX];
            if (tmpOrganizm != null && tmpOrganizm.get_sila() > this.get_sila()) {
                zablokuj_kierunek(Kierunek.GORA);
            } else ile_wolnych++;
        }

        if (pozY == sizeY - 1) zablokuj_kierunek(Kierunek.DOL);
        else {
            tmpOrganizm = get_swiat().getPlansza()[pozY + 1][pozX];
            if (tmpOrganizm != null && tmpOrganizm.get_sila() > this.get_sila()) {
                zablokuj_kierunek(Kierunek.DOL);
            } else ile_wolnych++;
        }

        if (ile_wolnych == 0) return new Koordynaty(pozX, pozY);
        while (true) {
            Random rand = new Random();
            int upperbound = 100;
            int wylosowany = rand.nextInt(upperbound);
            //RUCH W LEWO
            if (wylosowany < 25 && !kierunek_zablokowany(Kierunek.LEWO))
                return new Koordynaty(pozX - 1, pozY);
                //RUCH W PRAWO
            else if (wylosowany >= 25 && wylosowany < 50 && !kierunek_zablokowany(Kierunek.PRAWO))
                return new Koordynaty(pozX + 1, pozY);
                //RUCH W GORE
            else if (wylosowany >= 50 && wylosowany < 75 && !kierunek_zablokowany(Kierunek.GORA))
                return new Koordynaty(pozX, pozY - 1);
                //RUCH W DOL
            else if (wylosowany >= 75 && !kierunek_zablokowany(Kierunek.DOL))
                return new Koordynaty(pozX, pozY + 1);
        }
    }
}