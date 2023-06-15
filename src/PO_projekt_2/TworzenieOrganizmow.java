package PO_projekt_2;
import PO_projekt_2.rosliny.*;
import PO_projekt_2.zwierzeta.*;

public class TworzenieOrganizmow
{
    public static Organizm StworzNowyOrganizm(Swiat swiat, Organizm.TypOrganizmu typ_organizmu, Organizm.Koordynaty koordynaty)
    {
        switch (typ_organizmu)
        {
            case CZLOWIEK: return new Czlowiek(swiat, koordynaty, swiat.get_numer_tury());
            case WILK: return new Wilk(swiat, koordynaty, swiat.get_numer_tury());
            case OWCA: return new Owca(swiat, koordynaty, swiat.get_numer_tury());
            case CYBER_OWCA: return new CyberOwca(swiat, koordynaty, swiat.get_numer_tury());
            case LIS: return new Lis(swiat, koordynaty, swiat.get_numer_tury());
            case ZOLW: return new Zolw(swiat, koordynaty, swiat.get_numer_tury());
            case ANTYLOPA: return new Antylopa(swiat, koordynaty, swiat.get_numer_tury());
            case TRAWA: return new Trawa(swiat, koordynaty, swiat.get_numer_tury());
            case MLECZ: return new Mlecz(swiat, koordynaty, swiat.get_numer_tury());
            case GUARANA: return new Guarana(swiat, koordynaty, swiat.get_numer_tury());
            case WILCZE_JAGODY: return new WilczeJagody(swiat, koordynaty, swiat.get_numer_tury());
            case BARSZCZ_SOSNOWSKIEGO: return new BarszczSosnowskiego(swiat, koordynaty, swiat.get_numer_tury());
            default: return null;
        }
    }
}