package org.example;
import java.util.Random;

public class Main {

    // Tablica stringow potrzebna do wizualizacji
    static String[][] Pozycje = new String[25][25];


    // Kolorki :}
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";

    // Metoda resetujaca wyglad tablicy wizualizacyjnej
    public static void Reset(int rozmiar)
    {
        for( int i = 0; i < rozmiar; i++)
        {
            for( int j = 0; j < rozmiar; j++)
            {
                Pozycje[i][j] = "#";
            }
        }
    }


    //Metoda, ktora dodaje obiekty do tablicy wizualizacyjnej
    public static void Dodawanie(int typ, int miejscex, int miejscey) {
        switch (typ) {
            case 1:
                Pozycje[miejscey][miejscex] = ANSI_PURPLE + "G" + ANSI_RESET;
                break;
            case 2:
                Pozycje[miejscey][miejscex] = ANSI_RED + "D" + ANSI_RESET;
                break;
            case 3:
                Pozycje[miejscey][miejscex] = ANSI_YELLOW + "M" + ANSI_RESET;
                break;
            case 4:
                Pozycje[miejscey][miejscex] = ANSI_BLUE + "P" + ANSI_RESET;
                break;
            case 5:
                Pozycje[miejscey][miejscex] = ANSI_GREEN + "F" + ANSI_RESET;
                break;
            case 6:
                Pozycje[miejscey][miejscex] = ANSI_CYAN + "K" + ANSI_RESET;
                break;

        }
    }

    // metoda wyswietlajaca tablice wizualizacyjna
    public static void WyswietlanieTablicy(int rozmiar)
    {
        for( int i = 0; i < rozmiar; i++)
        {
            for( int j = 0; j < rozmiar; j++)
            {
                System.out.print(Pozycje[i][j]);
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        Random losowy = new Random();
        //inicjalizacja rozmiaru planszy
        System.out.println("rozmiar mapy -> 25\n\n");
        //Scanner scan = new Scanner(System.in);
        int rozmiar = 25;//scan.nextInt();

        //zaimplementowanie głównej postaci
        Glowny_Bohater Marek = new Glowny_Bohater(1, 1, 50, 5);

        //implementacja pieniądza
        Pieniadze kasa = new Pieniadze(15,5,50);

        //implementacja menela
          Menel Kuba = new Menel(0,rozmiar-1,5,1);

        //List<Menel> menele = new ArrayList<>();
//        for(int i = 0; i < 5;i++)
//        {
//            Menel m = new Menel();
//            m.pozycjax = losowy.nextInt(99 - 0);
//        }
//        implementacja tablicy danej liczby meneli
//        Menel menele[]= new Menel[10];
//        menele[1].pozycjax = 1;

        //implementacja dresa
        Dres Seba = new Dres(rozmiar-1,0,10,5,0);

        //implementacja klucza
        Klucz key = new Klucz(rozmiar - 5, rozmiar - 4);

        //implementacja policjanta
        Policjant Policjant = new Policjant(1, 1, 2);

        //element zliczający ile ruchów trwała gra
        int iloscruchow = 0;

        //uzależnienie jak się gra skończyła 2 -> aresztowanie wszystkich meneli i dresow 1 -> zebranie klucza  0 -> okradzenie ze wszystkich pieniędzy
        int ktory = 0;

        //puszczenie losowania do kradzieży
        int kradziez;

        //Jezeli parametr ten zostanie zmieniony to zostanie wyswietlony aktualny stan mapy
        int akcja = 0;

        //Jaki typ akcji został wykonany
        String TypAkcji = " ";

        while (true) {
            iloscruchow++;
            //sprawdzenie czy glowny bohater moze podniesc klucz jezeli tak to koniec gry
            if(Marek.czy_moze_podniesc_klucz(key.pozycja_x,key.pozycja_y) == 1)
            {
                akcja++;
                key = null;
                TypAkcji += "\nPodniesienie klucza przez Glownego Bohatera";
                ktory = 1;
                break;
            }
            //jak glowny bohater nie moze podniesc klucza to sprawdzenie czy go widzi
            //jak tak to poruszanie sie w strone klucza
            //a po ruchu ponowne sprawdzenie czy moze podniesc klucz
            else if(Marek.czy_widzi_klucz(key.pozycja_x, key.pozycja_y) == 1) {
                Marek.w_strone(key.pozycja_x, key.pozycja_y);
                akcja++;
                TypAkcji += "\nRuch w strone klucza przez Glownego Bohatera";


                if (Marek.czy_moze_podniesc_klucz(key.pozycja_x, key.pozycja_y) == 1) {
                    TypAkcji += "\nPodniesienie klucza przez Glownego Bohatera";
                    key = null;
                    ktory = 1;
                    break;
                }
            }
            //jak nie widzi klucza to sprawdzenie czy moze podniesc pieniadze
            else if(kasa != null && Marek.czy_podniesie_pieniadza(kasa.pozycja_x, kasa.pozycja_y) == 1) {
                akcja++;
                TypAkcji += "\nPodniesienie pieniadza przez Glownego Bohatera";

                Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                if(Kuba != null)
                    Dodawanie(Kuba.GimmeType(), Kuba.Gimmex(), Kuba.Gimmey());
                if(Seba != null)
                    Dodawanie(Seba.GimmeType(), Seba.Gimmex(), Seba.Gimmey());
                Dodawanie(kasa.GimmeType(),kasa.Gimmex(), kasa.Gimmey());
                Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
                Dodawanie(key.GimmeType(), key.Gimmex(), key.Gimmey());
                System.out.println("Przed podniesieniem Pieniadza przez Glownego Bohatera");
                WyswietlanieTablicy(rozmiar);
                System.out.println();
                Reset(rozmiar);

                Marek.ilosc_pieniedzy += kasa.wartosc;
                TypAkcji += "Głowny bohater podnosi pieniadze   ->   " + kasa.wartosc;
                TypAkcji += "Glowny bohater ma teraz " + Marek.ilosc_pieniedzy + " pieniedzy";
                kasa = null;
                //z tego wynika ze nie widzi klucza a pieniadze podniosl takze ruch losowy bo nic innego nie widzi
                Marek.poruszanie_sie(rozmiar);
            }
            //jak nie moze podniesc pieniedzy to sprawdzenie czy moze isc w ich kierunku
            else if (kasa != null && Marek.czy_widzi_pieniadze(kasa.pozycja_x, kasa.pozycja_y) == 1)//jak MC nie widzi klucza to sprawdza czy widzi pieniadze
            {
                Marek.w_strone(kasa.pozycja_x, kasa.pozycja_y);
                akcja++;
                TypAkcji += "\nRuch w strone pieniedzy przez Glownego Bohatera";
                //ponowne sprawdzenie czy moze podniesc pieniadze
                if (Marek.czy_podniesie_pieniadza(kasa.pozycja_x, kasa.pozycja_y) == 1) {
                    akcja++;
                    TypAkcji += "\nPodniesienie pieniadza przez Glownego Bohatera";

                    Marek.ilosc_pieniedzy += kasa.wartosc;
                    TypAkcji += "\nGłowny bohater podnosi pieniadze   ->   " + kasa.wartosc;
                    TypAkcji += "\nGlowny bohater ma teraz " + Marek.ilosc_pieniedzy + " pieniedzy";

                    Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                    if(Kuba != null)
                        Dodawanie(Kuba.GimmeType(), Kuba.Gimmex(), Kuba.Gimmey());
                    if(Seba != null)
                        Dodawanie(Seba.GimmeType(), Seba.Gimmex(), Seba.Gimmey());
                    Dodawanie(kasa.GimmeType(),kasa.Gimmex(), kasa.Gimmey());
                    Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
                    Dodawanie(key.GimmeType(), key.Gimmex(), key.Gimmey());
                    System.out.println("Przed podniesieniem Pieniadza po ruchu Glownego Bohatera");
                    WyswietlanieTablicy(rozmiar);
                    System.out.println();
                    Reset(rozmiar);


                    kasa = null;
                }
            }
            else//jak marek nic nie widzi porusza sie losowo
            {
                Marek.poruszanie_sie(rozmiar);
            }


            //ruch dresa
            if(Seba != null) {
                if (iloscruchow == 1 && Seba.czy_moze_okrasc(Marek.pozycja_x, Marek.pozycja_y) == 1)//sprawdzenie czy dres moze okrasc glownego bohatera
                {
                    akcja++;
                    TypAkcji += "\nKradziez przez Dresa";
                    kradziez = losowy.nextInt(Seba.max_pieniedzy - Seba.min_pieniedzy + 1) + Seba.min_pieniedzy;//losowanie z przedzialu liczby ile kradnie dres
                    Marek.ilosc_pieniedzy -= kradziez;//kradziez dresa


                    TypAkcji += "\nDres ukradł -> " + kradziez + "     ruch   " + iloscruchow;
                    TypAkcji += "\nGlownemu bohaterowi zostalo -> " + Marek.ilosc_pieniedzy;


                    Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                    if(Kuba != null)
                        Dodawanie(Kuba.GimmeType(), Kuba.Gimmex(), Kuba.Gimmey());
                    Dodawanie(Seba.GimmeType(), Seba.Gimmex(), Seba.Gimmey());
                    if( kasa != null)
                        Dodawanie(kasa.GimmeType(),kasa.Gimmex(), kasa.Gimmey());
                    Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
                    Dodawanie(key.GimmeType(), key.Gimmex(), key.Gimmey());
                    System.out.println("Przed kradzieza po ruchu dresa");
                    WyswietlanieTablicy(rozmiar);
                    System.out.println();
                    Reset(rozmiar);

                    if (Marek.ilosc_pieniedzy <= 0)//sprawdzenie czy dres okradl na tyle ze jest koniec gry
                        break;
                    Seba.przerwa_od_kradzenia = 7;//ustawienie przerwy dla dresa zeby nie chodzil za MC i kradl go caly czas
                }
                else if(Seba.czy_widzi_cos2(Marek.pozycja_x, Marek.pozycja_y) == 1)
                {
                    int pomoc1, pomoc2 ;//potrzebne do przetrzymania pozycji Dresa przed jego ruchem do wyswietlania
                    pomoc1 = Seba.pozycja_x;
                    pomoc2 = Seba.pozycja_y;
                    Seba.wstrone(Marek.pozycja_x, Marek.pozycja_y);

                    if (Seba.czy_moze_okrasc(Marek.pozycja_x, Marek.pozycja_y) == 1)//sprawdzenie czy dres moze okrasc
                    {
                        akcja++;
                        TypAkcji += "\nKradziez przez Dresa";
                        kradziez = losowy.nextInt(Seba.max_pieniedzy - Seba.min_pieniedzy + 1) + Seba.min_pieniedzy;//losowanie z przedzialu liczby ile kradnie dres
                        Marek.ilosc_pieniedzy -= kradziez;//kradziez dresa

                        //wyswietlanie przed ruchem dresa
                        Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                        if(Kuba != null)
                            Dodawanie(Kuba.GimmeType(), Kuba.Gimmex(), Kuba.Gimmey());
                        Dodawanie(Seba.GimmeType(), pomoc1, pomoc2);
                        if( kasa != null)
                            Dodawanie(kasa.GimmeType(),kasa.Gimmex(), kasa.Gimmey());

                        Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
                        Dodawanie(key.GimmeType(), key.Gimmex(), key.Gimmey());
                        System.out.println("Przed kradzieza przed ruchem dresa");
                        WyswietlanieTablicy(rozmiar);
                        System.out.println();
                        Reset(rozmiar);

                        TypAkcji += "\nDres ukradł -> " + kradziez + "     ruch   " + iloscruchow ;
                        TypAkcji += "\nGlownemu bohaterowi zostalo -> " + Marek.ilosc_pieniedzy;

                        if (Marek.ilosc_pieniedzy <= 0)//sprawdzenie czy dres okradl na tyle ze jest koniec gry
                            break;
                        Seba.przerwa_od_kradzenia = 7;//ustawienie przerwy dla dresa zeby nie chodzil za MC i kradl go caly czas
                    }
                }
                else
                {
                    if(Seba.przerwa_od_kradzenia != 0)
                        Seba.przerwa_od_kradzenia--;
                    Seba.poruszanie_sie(rozmiar);
                }
            }


            //ruch menela
            if(Kuba != null) {
                if (Kuba.czy_moze_okrasc(Marek.pozycja_x, Marek.pozycja_y) == 1)//ruch menela
                {
                    akcja++;
                    TypAkcji += "\nKradziez ";
                    kradziez = losowy.nextInt(Kuba.max_pieniedzy - Kuba.min_pieniedzy + 1) + Kuba.min_pieniedzy;//losowanie z przedzialu liczby ile kradnie menel
                    Marek.ilosc_pieniedzy -= kradziez;//kradziez menela
                    System.out.println("Menel ukradł -> " + kradziez + "     ruch   " + iloscruchow);
                    System.out.println("Glownemu bohaterowi zostalo -> " + Marek.ilosc_pieniedzy);
                    if (Marek.ilosc_pieniedzy <= 0)//sprawdzenie czy menel okradl na tyle ze jest koniec gry
                        break;
                    Kuba.przerwa_od_kradzenia = 3;//ustawienie przerwy od kradzenia dla menela
                    Kuba.poruszanie_sie(rozmiar);//ruszanie sie menela
                }
                else if(kasa != null && Kuba.czy_podniesie_pieniadza(kasa.pozycja_x,kasa.pozycja_y) == 1)
                {
                    akcja++;
                    TypAkcji += "Podniesienie pieniadza przez menela";


                    //wyswietlanie przed podniesieniem pieniadza
                    Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                    Dodawanie(Kuba.GimmeType(), Kuba.Gimmex(), Kuba.Gimmey());
                    if(Seba != null)
                        Dodawanie(Seba.GimmeType(), Seba.Gimmex(), Seba.Gimmey());
                    Dodawanie(kasa.GimmeType(),kasa.Gimmex(), kasa.Gimmey());
                    Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
                    Dodawanie(key.GimmeType(), key.Gimmex(), key.Gimmey());
                    System.out.println("Przed podniesieniem pieniadza przez menela");
                    WyswietlanieTablicy(rozmiar);
                    System.out.println();
                    Reset(rozmiar);

                    Kuba.przerwa_od_kradzenia--;
                    kasa = null;
                    Kuba.poruszanie_sie(rozmiar);
                }
                else
                {
                    Kuba.poruszanie_sie(rozmiar);
                    Kuba.przerwa_od_kradzenia--;
                    if (Kuba.czy_moze_okrasc(Marek.pozycja_x, Marek.pozycja_y) == 1) {
                        akcja++;
                        TypAkcji += "\nKradziez ";
                        kradziez = losowy.nextInt(Kuba.max_pieniedzy - Kuba.min_pieniedzy + 1) + Kuba.min_pieniedzy;//losowanie z przedzialu liczby ile kradnie menel
                        Marek.ilosc_pieniedzy -= kradziez;//kradziez menela

                        //wyswietlanie
                        Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                        Dodawanie(Kuba.GimmeType(), Kuba.Gimmex(), Kuba.Gimmey());
                        if(Seba != null)
                            Dodawanie(Seba.GimmeType(), Seba.Gimmex(), Seba.Gimmey());
                        if(kasa != null)
                            Dodawanie(kasa.GimmeType(),kasa.Gimmex(), kasa.Gimmey());
                        Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
                        Dodawanie(key.GimmeType(), key.Gimmex(), key.Gimmey());
                        System.out.println("Przed kradzieza po ruchu dresa");
                        WyswietlanieTablicy(rozmiar);
                        System.out.println();
                        Reset(rozmiar);
                        System.out.println("Menel ukradł -> " + kradziez + "     ruch   " + iloscruchow);
                        System.out.println("Glownemu bohaterowi zostalo -> " + Marek.ilosc_pieniedzy);
                        if (Marek.ilosc_pieniedzy <= 0)//sprawdzenie czy menel ukradl na tyle ze jest koniec gry
                            break;
                        Kuba.przerwa_od_kradzenia = 3;
                    }
                }
            }

            //pozniej ruszanie sie policjanta
            if (Seba != null && Policjant.czy_widzi_dresa_lub_menela(Seba.pozycja_x, Seba.pozycja_y) == 1)
                {
                    akcja++;
                    TypAkcji += "\nPoruszanie sie w strone dresa";

                    int pomoc1, pomoc2 ;//potrzebne do przetrzymania pozycji policjanta przed jego ruchem do wyswietlania
                    pomoc1 = Policjant.pozycja_x;
                    pomoc2 = Policjant.pozycja_y;
                    Policjant.w_strone(Seba.pozycja_x, Seba.pozycja_y);
                    if (Policjant.czy_moze_aresztowac(Seba.pozycja_x, Seba.pozycja_y) == 1)
                        {
                            //wyswietlanie przed ruchem policjanta
                            Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                            if(Kuba != null)
                                Dodawanie(Kuba.GimmeType(), Kuba.Gimmex(), Kuba.Gimmey());
                            Dodawanie(Seba.GimmeType(), Seba.Gimmex(), Seba.Gimmey());
                            if( kasa != null)
                                Dodawanie(kasa.GimmeType(),kasa.Gimmex(), kasa.Gimmey());
                            Dodawanie(Policjant.GimmeType(), pomoc1, pomoc2);
                            Dodawanie(key.GimmeType(), key.Gimmex(), key.Gimmey());
                            System.out.println("Przed aresztowaniem Dresa przed ruchem policjanta");
                            WyswietlanieTablicy(rozmiar);
                            System.out.println();
                            Reset(rozmiar);


                            akcja++;
                            TypAkcji += "\nAresztowanie dresa ";

                            //wyswietlanie po ruchu policjanta
                            Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                            if(Kuba != null)
                                Dodawanie(Kuba.GimmeType(), Kuba.Gimmex(), Kuba.Gimmey());
                            Dodawanie(Seba.GimmeType(), Seba.Gimmex(), Seba.Gimmey());
                            if( kasa != null)
                                Dodawanie(kasa.GimmeType(),kasa.Gimmex(), kasa.Gimmey());
                            Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
                            Dodawanie(key.GimmeType(), key.Gimmex(), key.Gimmey());
                            System.out.println("Przed aresztowaniem Dresa po ruchu policjanta");
                            WyswietlanieTablicy(rozmiar);
                            System.out.println();
                            Reset(rozmiar);


                            Seba = null;
                            if(Kuba == null)
                            {
                                ktory = 2;
                                break;
                            }
                        }
                }
            else if(Kuba != null && Policjant.czy_widzi_dresa_lub_menela(Kuba.pozycja_x, Kuba.pozycja_y) == 1)
                {
                    akcja++;
                    TypAkcji += "\nPoruszanie sie w strone menela ";
                    int pomoc1, pomoc2 ;//potrzebne do przetrzymania pozycji policjanta przed jego ruchem do wyswietlania
                    pomoc1 = Policjant.pozycja_x;
                    pomoc2 = Policjant.pozycja_y;
                    Policjant.w_strone(Kuba.pozycja_x, Kuba.pozycja_y);
                    if (Policjant.czy_moze_aresztowac(Kuba.pozycja_x, Kuba.pozycja_y) == 1)
                        {
                            //wyswietlanie przed ruchem policjanta
                            Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                            Dodawanie(Kuba.GimmeType(), Kuba.Gimmex(), Kuba.Gimmey());
                            if(Seba != null)
                                Dodawanie(Seba.GimmeType(), Seba.Gimmex(), Seba.Gimmey());

                            if( kasa != null)
                                Dodawanie(kasa.GimmeType(),kasa.Gimmex(), kasa.Gimmey());
                            Dodawanie(Policjant.GimmeType(), pomoc1, pomoc2);
                            Dodawanie(key.GimmeType(), key.Gimmex(), key.Gimmey());
                            System.out.println("Przed aresztowaniem Menela przed ruchem policjanta");
                            WyswietlanieTablicy(rozmiar);
                            System.out.println();
                            Reset(rozmiar);

                            akcja++;
                            TypAkcji += "\nAresztowanie menela ";

                            //wyswietlanie po ruchu policjanta
                            Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                            Dodawanie(Kuba.GimmeType(), Kuba.Gimmex(), Kuba.Gimmey());
                            if(Seba != null)
                                Dodawanie(Seba.GimmeType(), Seba.Gimmex(), Seba.Gimmey());

                            if( kasa != null)
                                Dodawanie(kasa.GimmeType(),kasa.Gimmex(), kasa.Gimmey());

                            Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
                            Dodawanie(key.GimmeType(), key.Gimmex(), key.Gimmey());

                            System.out.println("Przed aresztowaniem Menela po ruchu policjanta");
                            WyswietlanieTablicy(rozmiar);
                            Reset(rozmiar);
                            System.out.println();


                            Kuba = null;
                            //System.out.println("Policjant aresztuje menela");
                            if(Seba == null)
                            {
                                ktory = 2;
                                break;
                            }
                        }
                }
                else {
                    Policjant.poruszanie_sie(rozmiar);
                }


                //dodawanie poszczegolnych elementow do tablicy wyswietlajacej
            Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());

            if(Kuba != null)
                Dodawanie(Kuba.GimmeType(), Kuba.Gimmex(), Kuba.Gimmey());

            if(Seba != null)
                Dodawanie(Seba.GimmeType(), Seba.Gimmex(), Seba.Gimmey());

            if( kasa != null)
            {
                Dodawanie(kasa.GimmeType(),kasa.Gimmex(), kasa.Gimmey());
            }
            Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());

            Dodawanie(key.GimmeType(), key.Gimmex(), key.Gimmey());

            if( akcja > 0)
                {
                    System.out.println("Tura numer -> " + iloscruchow);
                    System.out.println("Typ akcji: " + TypAkcji);
                    WyswietlanieTablicy(rozmiar);
                    System.out.println();
                    System.out.println();
                }
                akcja = 0;
                TypAkcji = "";
                Reset(rozmiar);
        }

        Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());

        if(Kuba != null)
            Dodawanie(Kuba.GimmeType(), Kuba.Gimmex(), Kuba.Gimmey());

        if(Seba != null)
            Dodawanie(Seba.GimmeType(), Seba.Gimmex(), Seba.Gimmey());

        if( kasa != null)
        {
            Dodawanie(kasa.GimmeType(),kasa.Gimmex(), kasa.Gimmey());
        }
        Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
        if(key != null)
            Dodawanie(key.GimmeType(), key.Gimmex(), key.Gimmey());

        if( akcja > 0)
        {
            System.out.println("Tura numer -> " + iloscruchow);
            System.out.println("Typ akcji : " + TypAkcji);
            WyswietlanieTablicy(rozmiar);
            System.out.println();
        }

                if (ktory == 1)
                    System.out.println("zebranie klucza");
                else if(ktory == 2)
                    System.out.println("aresztowanie wszystkich meneli i dresow");
                else
                    System.out.println("okradzenie");
                System.out.println("koniecgry,    ilosc ruchow -> " + iloscruchow);
        }
    }