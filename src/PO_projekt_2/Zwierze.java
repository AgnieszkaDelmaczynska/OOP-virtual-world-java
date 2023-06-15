package PO_projekt_2;
import java.util.Random;

public abstract class Zwierze extends Organizm
{
    public Zwierze( Swiat swiat, TypOrganizmu typOrganizmu, int sila, int inicjatywa, Koordynaty koordynaty, int tura_urodzenia)
    {
        super(swiat, typOrganizmu, sila, inicjatywa, koordynaty, tura_urodzenia);
    }

    @Override
    public boolean czy_zwierze()
    {
        return true;
    }

    @Override
    public void akcja()
    {
        Koordynaty wylosowana_pozycja = planowanie_przyszlego_ruchu();
        if (get_swiat().czy_miejsce_zajete(wylosowana_pozycja))
        {
            if (get_swiat().co_na_polu(wylosowana_pozycja) != this)      kolizja(get_swiat().co_na_polu(wylosowana_pozycja));
            else if (get_swiat().co_na_polu(wylosowana_pozycja) != this) wykonaj_ruch(wylosowana_pozycja);
        }
        else if (get_swiat().co_na_polu(wylosowana_pozycja) != this)     wykonaj_ruch(wylosowana_pozycja);
    }

    @Override
    public void kolizja(Organizm other)
    {
        System.out.println(other.OrganizmToSring());
        if (get_typ_organizmu() == other.get_typ_organizmu())
        {
            Random rand = new Random();
            if (rand.nextInt(100) < 50)
                rozmnazanie(other);
        }
        else
        {
            if (supermoc(this, other)) return;
            if (other.supermoc(this, other)) return;
            if (get_sila() >= other.get_sila())
            {
                Informator.dodaj_informacje(OrganizmToSring() + " zabija " + other.OrganizmToSring());
                get_swiat().usun_organizm_ze_swiata(other);
                wykonaj_ruch(other.get_koordynaty());
            }
            else
            {
                Informator.dodaj_informacje(other.OrganizmToSring() + " zabija " + OrganizmToSring());
                get_swiat().usun_organizm_ze_swiata(this);
            }
        }
    }

    private void rozmnazanie(Organizm other)
    {
        if (this.get_czy_sie_rozmnazal() || other.get_czy_sie_rozmnazal()) return;
        Koordynaty tmp1Punkt = this.daj_niezajete_pole(get_koordynaty());
        if (tmp1Punkt.equals(get_koordynaty()))
        {
            Koordynaty tmp2Punkt = other.daj_niezajete_pole(other.get_koordynaty());
            if (!tmp2Punkt.equals(other.get_koordynaty()))
            {
                Organizm tmpOrganizm = TworzenieOrganizmow.StworzNowyOrganizm(this.get_swiat(), get_typ_organizmu(), tmp2Punkt);
                assert tmpOrganizm != null;
                Informator.dodaj_informacje("Nowe zwierze: " + tmpOrganizm.OrganizmToSring());
                get_swiat().pchnij_na_liste(tmpOrganizm);
                set_czy_sie_rozmnazal(true);
                other.set_czy_sie_rozmnazal(true);
            }
        }
        else
        {
            Organizm tmpOrganizm = TworzenieOrganizmow.StworzNowyOrganizm(this.get_swiat(), get_typ_organizmu(), tmp1Punkt);
            assert tmpOrganizm != null;
            Informator.dodaj_informacje("Nowe zwierze : " + tmpOrganizm.OrganizmToSring());
            get_swiat().pchnij_na_liste(tmpOrganizm);
            set_czy_sie_rozmnazal(true);
            other.set_czy_sie_rozmnazal(true);
        }
    }

    protected Koordynaty planowanie_przyszlego_ruchu()
    {
        Random rand = new Random();
        if (rand.nextInt(100) < 50)
            return daj_losowe_pole(get_koordynaty());
        else
            return get_koordynaty();
    }
}