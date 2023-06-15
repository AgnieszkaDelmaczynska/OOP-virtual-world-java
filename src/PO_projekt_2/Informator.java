package PO_projekt_2;

public class Informator
{
    protected static String informacja = "";
    public static void dodaj_informacje(String komentarz)
    {
        if (informacja.equals(""))
            informacja = komentarz + "\n";
        else
            informacja += komentarz + "\n";
    }
    public static void wyczysc_informacje()
    {
        informacja = "";
    }
    public static String get_informacje()
    {
        return informacja;
    }
}