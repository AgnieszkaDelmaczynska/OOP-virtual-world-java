package PO_projekt_2.zwierzeta;
import PO_projekt_2.Swiat;
import PO_projekt_2.Organizm;
import PO_projekt_2.rosliny.BarszczSosnowskiego;
import java.awt.*;

public class CyberOwca extends Owca
{
    private static final int CYBER_OWCA_SILA = 11;
    private static final int CYBER_OWCA_INICJATYWA = 4;

    public CyberOwca(Swiat swiat, Koordynaty koordynaty, int tura_urodzenia)
    {
        super(swiat, koordynaty, tura_urodzenia);
        set_kolor(new Color(252, 136, 136));
        setTypOrganizmu(TypOrganizmu.CYBER_OWCA);
        set_sila(CYBER_OWCA_SILA);
        setInicjatywa(CYBER_OWCA_INICJATYWA);
    }

    @Override
    public String typ_organizmu_to_string()
    {
        return "Cyberowca";
    }
    
    @Override
    public Koordynaty daj_losowe_pole(Koordynaty koordynaty) // nadpisane, bo pole nie ma być losowe, tylko losowe w stronę barszczu
    {
        boolean czy_jest_barszcz = false;   // szuka, czy jest barszcz na planszy
        for (int i = 0; i < get_swiat().get_rozmiar_y(); i++)
        {
            for (int j = 0; j < get_swiat().get_rozmiar_x(); j++)
            {
                if ((get_swiat().getPlansza()[i][j] != null) && (get_swiat().getPlansza()[i][j].get_typ_organizmu() == TypOrganizmu.BARSZCZ_SOSNOWSKIEGO))
                {
                    czy_jest_barszcz = true; break;
                }
            }
        }
        if (czy_jest_barszcz)   // jeśli jest, to kieruj się na niego
        {
            Koordynaty wyszukany_barszcz = znajdz_najblizszy_barszcz().get_koordynaty();
            int rozniaca_x = Math.abs(koordynaty.getX() - wyszukany_barszcz.getX());  // wartość bezwzględna z różnicy "x" od cyberowcy do barszczu
            int rozniaca_y = Math.abs(koordynaty.getY() - wyszukany_barszcz.getY());  // wartość bezwzględna z różnicy "y" od cyberowcy do barszczu
            if (rozniaca_x < rozniaca_y)
            {
                if (koordynaty.getY() > wyszukany_barszcz.getY()) return new Koordynaty(koordynaty.getX(), koordynaty.getY() - 1);
                else                                              return new Koordynaty(koordynaty.getX(), koordynaty.getY() + 1);
            }
            else
            {
                if (koordynaty.getX() > wyszukany_barszcz.getX()) return new Koordynaty(koordynaty.getX() - 1, koordynaty.getY());
                else                                              return new Koordynaty(koordynaty.getX() + 1, koordynaty.getY());
            }
        }
        else    // jeśli nie ma, to nie nadpisuj metody "losowe pole" w Cyberowcy i daj jakieś pole losowe, może nastąpić kolizja z innym organizmem
            return super.daj_losowe_pole(koordynaty);
    }

    private BarszczSosnowskiego znajdz_najblizszy_barszcz()
    {
        BarszczSosnowskiego najblizszy_barszcz = null;
        int najkrotsza_droga = get_swiat().get_rozmiar_x() + get_swiat().get_rozmiar_y(); // suma x i y, czyli max odległość, jaka może być po przekątnej
        for (int i = 0; i < get_swiat().get_rozmiar_y(); i++)
        {
            for (int j = 0; j < get_swiat().get_rozmiar_x(); j++)
            {
                Organizm organizm = get_swiat().getPlansza()[i][j];
                if ((organizm != null) && (organizm.get_typ_organizmu() == TypOrganizmu.BARSZCZ_SOSNOWSKIEGO))
                {
                    int rozniaca_x = Math.abs(get_koordynaty().getX() - organizm.get_koordynaty().getX());
                    int rozniaca_y = Math.abs(get_koordynaty().getY() - organizm.get_koordynaty().getY());
                    int nowa_potencjalna_najkrotsza_droga = rozniaca_x + rozniaca_y;
                    if (najkrotsza_droga > nowa_potencjalna_najkrotsza_droga) // jeśli znaleziono krótszą drogę do innegp barszczu
                    {
                        najkrotsza_droga = nowa_potencjalna_najkrotsza_droga;
                        najblizszy_barszcz = (BarszczSosnowskiego) organizm;  // ustaw nowy barszcz jako najbliższy
                    }
                }
            }
        }
        return najblizszy_barszcz;
    }
}