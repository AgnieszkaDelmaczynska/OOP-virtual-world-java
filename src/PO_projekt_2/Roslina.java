package PO_projekt_2;
import java.util.Random;

public abstract class Roslina extends Organizm
{
    protected Roslina(Swiat swiat, TypOrganizmu typOrganizmu, int sila, int inicjatywa, Koordynaty koordynaty, int tura_urodzenia)
    {
        super(swiat, typOrganizmu, sila, inicjatywa, koordynaty, tura_urodzenia);
    }

    @Override
    public boolean czy_zwierze()
    {
        return false;
    }

    @Override
    public void akcja()
    {
        Random rand = new Random();
        if (rand.nextInt(100) < 40)
            rozprzestrzenianie();
    }

    protected void rozprzestrzenianie()
    {
        Koordynaty niezajete_pole = this.daj_niezajete_pole(get_koordynaty());
        if (!niezajete_pole.equals(get_koordynaty()))
        {
            Organizm nowy_organizm = TworzenieOrganizmow.StworzNowyOrganizm(this.get_swiat(), get_typ_organizmu(), niezajete_pole);
            assert nowy_organizm != null;
            Informator.dodaj_informacje("Nowa roslina: " + nowy_organizm.OrganizmToSring());
            get_swiat().pchnij_na_liste(nowy_organizm);
        }
    }

    @Override
    public void kolizja(Organizm other) { }
}