package PO_projekt_2;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import PO_projekt_2.zwierzeta.Czlowiek;
public class SwiatSwing implements KeyListener, ActionListener
{
    private static final int sizeX = 20;
    private static final int sizeY = 20;
    private static final double procentowe_zapelnienie = 0.3;
    JFrame ramka_gry;
    JMenu menu;
    JMenuItem nowa_gra, wczytaj_z_pliku, zapisz, wyjdz;
    JButton nowa_gra2, wczytaj_z_pliku2, zapisz2, wyjdz2;
    JPanel panel;
    Toolkit zestaw_narzedzi;
    Dimension wymiary;
    Legenda legenda = null;
    view_game_board view_game_board = null;
    view_informator view_informator = null;
    Swiat swiat = new Swiat(sizeX, sizeY, this);
    int margines;
    private int ROUND;

    public SwiatSwing(String title)
    {
        zestaw_narzedzi = Toolkit.getDefaultToolkit();
        wymiary = zestaw_narzedzi.getScreenSize();
        margines = wymiary.height / 80;

        ramka_gry = new JFrame(title);
        ramka_gry.setBounds((wymiary.width - 1600) / 2, (wymiary.height - 1200) / 2, 1900,  1000);
        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Agnieszka\\IdeaProjects\\projekt_2.0\\src\\PO_projekt_2\\icon.bmp");
        ramka_gry.setIconImage(icon);
        ramka_gry.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Witaj w grze Wirtualny świat! Zaczynamy?");
        label.setFont(new Font("Arial", Font.BOLD, 40));

        JOptionPane.showMessageDialog(null,label,"Witaj :)",JOptionPane.PLAIN_MESSAGE);

        Font f = new Font("sans-serif", Font.PLAIN, 12);
        UIManager.put("Menu.font", f);
        UIManager.put("MenuItem.font", f);

        JMenuBar menuBar = new JMenuBar();
        menu = new JMenu("Opcje");
        menu.setFont(menu.getFont().deriveFont(34.0f));
        menu.setForeground(new Color(7, 108, 189));
        menu.setIconTextGap(80);

        nowa_gra = new JMenuItem("<html><p style='text-align:center;'>nowa gra</p></html>");
        nowa_gra.setFont(nowa_gra.getFont().deriveFont(34.0f));
        nowa_gra.setForeground(new Color(1, 104, 186));
        nowa_gra.setBackground(new Color(164, 205, 239));

        zapisz = new JMenuItem("zapisz do pliku");
        zapisz.setFont(zapisz.getFont().deriveFont(34.0f));
        zapisz.setForeground(new Color(0, 81, 151));
        zapisz.setBackground(new Color(164, 172, 239));

        wczytaj_z_pliku = new JMenuItem("wczytaj z pliku");
        wczytaj_z_pliku.setFont(wczytaj_z_pliku.getFont().deriveFont(34.0f));
        wczytaj_z_pliku.setForeground(new Color(0, 57, 104));
        wczytaj_z_pliku.setBackground(new Color(199, 164, 239));

        wyjdz = new JMenuItem("wyjście");
        wyjdz.setFont(wyjdz.getFont().deriveFont(34.0f));
        wyjdz.setForeground(new Color(0, 32, 59));
        wyjdz.setBackground(new Color(239, 164, 214));

        nowa_gra.addActionListener(this);
        zapisz.addActionListener(this);
        wczytaj_z_pliku.addActionListener(this);
        wyjdz.addActionListener(this);

        menu.add(nowa_gra);
        menu.add(zapisz);
        menu.add(wczytaj_z_pliku);
        menu.add(wyjdz);

        menuBar.add(menu);

        ramka_gry.setJMenuBar(menuBar);
        ramka_gry.setLayout(new CardLayout());

        panel = new JPanel();
        panel.setFont(panel.getFont().deriveFont(34.0f));
        panel.setBackground(new Color(158, 182, 255, 195));
        panel.setLayout(null);

        ramka_gry.addKeyListener(this);
        ramka_gry.add(panel);
        ramka_gry.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent event) { }
    @Override
    public void keyReleased(KeyEvent event) { }

    @Override
    public void actionPerformed(ActionEvent action_event)
    {
        if (action_event.getSource() == nowa_gra)
        {
            Informator.wyczysc_informacje();
            swiat = new Swiat(sizeX, sizeY, this);
            swiat.stworz_swiat(procentowe_zapelnienie);
            if (view_game_board != null)    panel.remove(view_game_board);
            if (view_informator != null)    panel.remove(view_informator);
            if (legenda != null)            panel.remove(legenda);
            rozpocznij_gre();
        }
        else if (action_event.getSource() == zapisz)
        {
            String nazwa_pliku = JOptionPane.showInputDialog(ramka_gry, "Podaj nazwę pliku (bez rozszerzenia)\nSwiat zostanie zapisany do pliku txt", "swiat");
            Informator.dodaj_informacje("Zapisano stan swiata");
            swiat.zapisz_swiat(nazwa_pliku);
            view_informator.aktualizuj_informator();
        }
        else if (action_event.getSource() == wczytaj_z_pliku)
        {
            Informator.wyczysc_informacje();
            String nameOfFile = JOptionPane.showInputDialog(ramka_gry, "Podaj nazwe pliku (bez rozszerzenia)\nSwiat zostanie wczytany z pliku txt\"", "swiat");
            swiat = Swiat.wczytaj_swiat(nameOfFile);
            assert swiat != null;
            swiat.set_swiat_Swing(this);
            view_game_board = new view_game_board(swiat);
            legenda = new Legenda();
            view_informator = new view_informator();
            if (view_game_board != null)    panel.remove(view_game_board);
            if (legenda != null)            panel.remove(legenda);
            if (view_informator != null)    panel.remove(view_informator);
            rozpocznij_gre();
        }
        else if (action_event.getSource() == wyjdz) ramka_gry.dispose();
    }

    @Override
    public void keyPressed(KeyEvent key_event)
    {
        if (swiat != null)
        {
            int keyCode = key_event.getKeyCode();
            if (keyCode == KeyEvent.VK_ENTER)
            {
                if(!swiat.get_czy_czlowiek_zyje())
                    swiat.set_czy_jest_koniec_gry(true);
                else
                    Informator.dodaj_informacje("KONIEC GRY\nCzłowiek nie żyje");
            }
            else if (swiat.get_czy_czlowiek_zyje())
            {
                if (keyCode == KeyEvent.VK_RIGHT)       swiat.getCzlowiek().set_kierunek(Organizm.Kierunek.PRAWO);
                else if (keyCode == KeyEvent.VK_LEFT)   swiat.getCzlowiek().set_kierunek(Organizm.Kierunek.LEWO);
                else if (keyCode == KeyEvent.VK_UP)     swiat.getCzlowiek().set_kierunek(Organizm.Kierunek.GORA);
                else if (keyCode == KeyEvent.VK_DOWN)   swiat.getCzlowiek().set_kierunek(Organizm.Kierunek.DOL);
                else if (keyCode == KeyEvent.VK_U)
                {
                    Czlowiek.Umiejetnosc tmpUmiejetnosc = swiat.getCzlowiek().get_umiejetnosc();
                    if (tmpUmiejetnosc.get_czy_mozna_aktywowac())
                    {
                        tmpUmiejetnosc.aktywuj_umiejetnosc();
                        Informator.dodaj_informacje("Umiejetnosc 'Szybkosc antylopy' aktywowana.(Jej pozostaly czas trwania wynosi " + tmpUmiejetnosc.get_czas_trwania_umiejetnosci() + " tur)");
                    }
                    else if (tmpUmiejetnosc.get_czy_umiejetnosc_aktywna())
                    {
                        Informator.dodaj_informacje("Umiejetnosc byla juz aktywna (Jej pozostaly czas trwania wynosi " + "(będzie trwać" + tmpUmiejetnosc.get_czas_trwania_umiejetnosci() + " tur)");
                        view_informator.aktualizuj_informator(); return;
                    }
                    else
                    {
                        Informator.dodaj_informacje("Umiejetnosc mozna wlaczyc tylko po " + tmpUmiejetnosc.get_czas_aktywacji() + " turach");
                        view_informator.aktualizuj_informator(); return;
                    }
                }
                else
                {
                    Informator.dodaj_informacje("\nNieoznaczony symbol, wybierz inny klawisz");
                    view_informator.aktualizuj_informator(); return;
                }
            }
            else if (!swiat.get_czy_czlowiek_zyje())
            {
                Informator.dodaj_informacje("KONIEC GRY\nCzłowiek umarł");
                view_informator.aktualizuj_informator();
                swiat.set_czy_jest_koniec_gry(true);
                Informator.wyczysc_informacje(); return;
            }
            else
            {
                Informator.dodaj_informacje("\nNieoznaczony symbol, wybierz inny klawisz");
                view_informator.aktualizuj_informator();
                return;
            }
            Informator.wyczysc_informacje();
            swiat.wykonaj_ture();
            rysuj_swiat();
        }
    }

    private class view_game_board extends JPanel
    {
        private final int sizeX;
        private final int sizeY;
        final private PolePlanszy[][] pole_planszy;

        public view_game_board(Swiat swiat)
        {
            super();
            this.sizeX = swiat.get_rozmiar_x();
            this.sizeY = swiat.get_rozmiar_y();
            setBounds(panel.getX() + margines, panel.getY() + margines, panel.getHeight() * 5 / 6 - margines, panel.getHeight() * 5 / 6 - margines);

            pole_planszy = new PolePlanszy[sizeY][sizeX];
            for (int i = 0; i < sizeY; i++)
            {
                for (int j = 0; j < sizeX; j++)
                {
                    pole_planszy[i][j] = new PolePlanszy(j, i);
                    pole_planszy[i][j].addActionListener(new ActionListener()
                    {
                        @Override
                        public void actionPerformed(ActionEvent event)
                        {
                            if (event.getSource() instanceof PolePlanszy)
                            {
                                PolePlanszy pole = (PolePlanszy) event.getSource();
                                if (pole.puste)
                                {
                                    ListaOrganizmow listaOrganizmow = new ListaOrganizmow(pole.getX() + ramka_gry.getX(), pole.getY() + ramka_gry.getY(), new Organizm.Koordynaty(pole.getPozX(), pole.getPozY()));
                                }
                            }
                        }
                    });
                }
            }
            for (int i = 0; i < sizeY; i++)
                for (int j = 0; j < sizeX; j++)
                    this.add(pole_planszy[i][j]);
            this.setLayout(new GridLayout(sizeY, sizeX));
        }

        private class PolePlanszy extends JButton
        {
            private final int pozX;
            private final int pozY;
            private boolean puste;
            private Color kolor;

            public PolePlanszy(int X, int Y)
            {
                super();
                pozX = X;
                pozY = Y;
                puste = true;
                kolor = new Color(255, 250, 205);
                setBackground(kolor);
            }

            public void setEmpty(boolean empty)
            {
                puste = empty;
            }

            public void set_kolor(Color kolor)
            {
                this.kolor = kolor;
                setBackground(kolor);
            }

            public int getPozX() {
                return pozX;
            }
            public int getPozY() {
                return pozY;
            }
        }

        public void odswiezPlansze()
        {
            for (int i = 0; i < sizeY; i++)
            {
                for (int j = 0; j < sizeX; j++)
                {
                    Organizm tmpOrganizm = swiat.getPlansza()[i][j];
                    if (tmpOrganizm != null)
                    {
                        pole_planszy[i][j].setEmpty(false);
                        pole_planszy[i][j].setEnabled(false);
                        pole_planszy[i][j].set_kolor(tmpOrganizm.get_kolor());
                    }
                    else
                    {
                        pole_planszy[i][j].setEmpty(true);
                        pole_planszy[i][j].setEnabled(true);
                        pole_planszy[i][j].set_kolor(new Color(255, 246, 225));
                    }
                }
            }
        }
    }

    private class view_informator extends JPanel
    {
        private String wyswietlany_tekst;
        final private JTextArea textArea;

        public view_informator()
        {
            super();
            setBounds(view_game_board.getX() + view_game_board.getWidth() + margines, panel.getY() + margines, panel.getWidth() - view_game_board.getWidth() - margines * 3, panel.getHeight() * 5 / 6 - margines);
            wyswietlany_tekst = Informator.get_informacje();
            textArea = new JTextArea(wyswietlany_tekst);
            textArea.setFont(textArea.getFont().deriveFont(24.0f));
            textArea.setEditable(false);
            setLayout(new CardLayout());

            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setMargin(new Insets(5, 5, 5, 5));
            textArea.setBackground(new Color(255, 223, 248));
            JScrollPane scroll = new JScrollPane(textArea);
            add(scroll);
        }

        public void aktualizuj_informator()
        {
            String informacje =
                    "                                                          *** Wirtualny świat ***\n" +
                    "Agnieszka Delmaczyńska 184592 | U: włącz specjalną umiejętność | Enter:  kolejna tura\n" +
                    "                                                    ^\n" +
                    "Kierowanie człowiekiem: strzałki <   >\n" +
                    "                                                    v\n";
            wyswietlany_tekst = informacje + Informator.get_informacje();
            textArea.setText(wyswietlany_tekst);
        }
    }

    private class ListaOrganizmow extends JFrame
    {
        private JList jList;
        private String[] l_organizmow;
        private Organizm.TypOrganizmu[] TypOrganizmuList;

        public ListaOrganizmow(int x, int y, Organizm.Koordynaty wspolrzedne)
        {
            super("Dodaj organizm z listy na wybrane pole:");
            l_organizmow = new String[]{"Wilk", "Owca", "Cyberowca", "Lis", "Zolw", "Antylopa", "Barszcz Sosnowskiego", "Guarana", "Mlecz", "Trawa", "Wilcze jagody"};
            TypOrganizmuList = new Organizm.TypOrganizmu[]
                {
                    Organizm.TypOrganizmu.WILK,
                    Organizm.TypOrganizmu.OWCA,
                    Organizm.TypOrganizmu.CYBER_OWCA,
                    Organizm.TypOrganizmu.LIS,
                    Organizm.TypOrganizmu.ZOLW,
                    Organizm.TypOrganizmu.ANTYLOPA,
                    Organizm.TypOrganizmu.BARSZCZ_SOSNOWSKIEGO,
                    Organizm.TypOrganizmu.GUARANA,
                    Organizm.TypOrganizmu.MLECZ,
                    Organizm.TypOrganizmu.TRAWA,
                    Organizm.TypOrganizmu.WILCZE_JAGODY
                };
            setBounds(x, y, 350, 300);
            jList = new JList(l_organizmow);
            jList.setFont(jList.getFont().deriveFont(24.0f));
            jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            jList.addListSelectionListener(new ListSelectionListener()
            {
                @Override
                public void valueChanged(ListSelectionEvent event)
                {
                    Organizm tmpOrganizm = TworzenieOrganizmow.StworzNowyOrganizm(swiat, TypOrganizmuList[jList.getSelectedIndex()], wspolrzedne);
                    swiat.pchnij_na_liste(tmpOrganizm);
                    Informator.dodaj_informacje("Dodano ręcznie nowy organizm: " + tmpOrganizm.OrganizmToSring());
                    rysuj_swiat();
                    dispose();
                }
            });
            JScrollPane scroll_panel = new JScrollPane(jList);
            add(scroll_panel);
            setVisible(true);
        }
    }

    private class Legenda extends JPanel implements ActionListener
    {
        public Legenda()
        {
            super();
            setBounds(panel.getX() + margines, panel.getHeight() * 5/6 + margines, panel.getWidth() - margines * 2, panel.getHeight() / 6 - 2 * margines);
            setBackground(new Color(233, 211, 253));
            setLayout(new FlowLayout(FlowLayout.CENTER));

            int liczba_gatunkow = 12;
            JButton[] legenda1 = new JButton[liczba_gatunkow];

            legenda1[0] = new JButton("Czlowiek");
            legenda1[0].setFont(legenda1[0].getFont().deriveFont(24.0f));
            legenda1[0].setBackground(new Color(29, 84, 173));

            legenda1[1] = new JButton("Wilk");
            legenda1[1].setFont(legenda1[0].getFont().deriveFont(24.0f));
            legenda1[1].setBackground(new Color(121, 121, 121));

            legenda1[2] = new JButton("Owca");
            legenda1[2].setFont(legenda1[0].getFont().deriveFont(24.0f));
            legenda1[2].setBackground(new Color(253, 249, 164));

            legenda1[3] = new JButton("Cyber owca");
            legenda1[3].setFont(legenda1[0].getFont().deriveFont(24.0f));
            legenda1[3].setBackground(new Color(252, 136, 136));

            legenda1[4] = new JButton("Lis");
            legenda1[4].setFont(legenda1[0].getFont().deriveFont(24.0f));
            legenda1[4].setBackground(new Color(255, 186, 120));

            legenda1[5] = new JButton("Zolw");
            legenda1[5].setFont(legenda1[0].getFont().deriveFont(24.0f));
            legenda1[5].setBackground(new Color(147, 255, 138));

            legenda1[6] = new JButton("Antylopa");
            legenda1[6].setFont(legenda1[0].getFont().deriveFont(24.0f));
            legenda1[6].setBackground(new Color(106, 191, 253));

            for (int i = 0; i < 7; i++)
            {
                legenda1[i].setEnabled(false);
                add(legenda1[i]);
            }

            legenda1[7] = new JButton("Barszcz Sosnowskiego");
            legenda1[7].setFont(legenda1[0].getFont().deriveFont(24.0f));
            legenda1[7].setBackground(new Color(146, 106, 71));

            legenda1[8] = new JButton("Guarana");
            legenda1[8].setFont(legenda1[0].getFont().deriveFont(24.0f));
            legenda1[8].setBackground(new Color(236, 167, 255));

            legenda1[9] = new JButton("Mlecz");
            legenda1[9].setFont(legenda1[0].getFont().deriveFont(24.0f));
            legenda1[9].setBackground(new Color(253, 237, 74));

            legenda1[10] = new JButton("Trawa");
            legenda1[10].setFont(legenda1[0].getFont().deriveFont(24.0f));
            legenda1[10].setBackground(new Color(120, 193, 146));

            legenda1[11] = new JButton("Wilcze jagody");
            legenda1[11].setFont(legenda1[0].getFont().deriveFont(24.0f));
            legenda1[11].setBackground(new Color(173, 130, 255));

            for (int i = 7; i < liczba_gatunkow; i++)
            {
                legenda1[i].setEnabled(false);
                add(legenda1[i]);
            }

            int ile_funkcji = 2;
            JButton[] legenda2 = new JButton[ile_funkcji];

            legenda2[0] = new JButton("Zacznij nową grę");
            legenda2[0].setFont(legenda1[0].getFont().deriveFont(24.0f));
            legenda2[0].setBackground(new Color(164, 205, 239));
            legenda2[0].addActionListener(this);

            legenda2[1] = new JButton("Wyjdź z gry");
            legenda2[1].setFont(legenda1[0].getFont().deriveFont(24.0f));
            legenda2[1].setBackground(new Color(239, 164, 214));
            legenda2[1].addActionListener(this);

            nowa_gra2 = legenda2[0];
            wyjdz2 = legenda2[1];

            add(legenda2[0]);
            add(legenda2[1]);
        }

        @Override
        public void actionPerformed(ActionEvent event)
        {
            if (event.getSource() == nowa_gra2)
            {
                Informator.wyczysc_informacje();
                swiat = new Swiat(sizeX, sizeY, swiat.get_swiat_Swing());
                swiat.stworz_swiat(procentowe_zapelnienie);
                if (view_game_board != null)    panel.remove(view_game_board);
                if (view_informator != null)    panel.remove(view_informator);
                if (legenda != null)            panel.remove(legenda);
                rozpocznij_gre();
            }
            if (event.getSource() == wyjdz2)
            {
                ramka_gry.dispose();
            }
        }
    }

    private void rozpocznij_gre()
    {
        view_game_board = new view_game_board(swiat);   panel.add(view_game_board);
        view_informator = new view_informator();        panel.add(view_informator);
        legenda = new Legenda();                        panel.add(legenda);
        rysuj_swiat();
    }

    public Swiat get_swiat() {
        return swiat;
    }

    public void rysuj_swiat()
    {
        view_game_board.odswiezPlansze();
        view_informator.aktualizuj_informator();
        SwingUtilities.updateComponentTreeUI(ramka_gry);
        ramka_gry.requestFocusInWindow();
    }
}