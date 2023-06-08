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
                Pozycje[i][j] = ".";
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
                Pozycje[miejscey][miejscex] = ANSI_GREEN + "$" + ANSI_RESET;
                break;
            case 6:
                Pozycje[miejscey][miejscex] = ANSI_CYAN + "K" + ANSI_RESET;
                break;

        }
    }

    // metoda wyswietlajaca tablice wizualizacyjna
    public static void Wyswietlanie_Tablicy(int rozmiar)
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
        Random Losowy = new Random();
        //inicjalizacja rozmiaru planszy
        System.out.println("rozmiar mapy -> 25\n\n");
        //Scanner scan = new Scanner(System.in);
        int Rozmiar = 25;//scan.nextInt();

        //zaimplementowanie głównej postaci
        Glowny_Bohater Marek = new Glowny_Bohater(1, 1, 50, 5);

        //implementacja pieniądza
        Pieniadze Kasa = new Pieniadze(15,5,50);

        //implementacja menela
          Menel Kuba = new Menel(0,Rozmiar-1,5,1);

        //List<Menel> menele = new ArrayList<>();
//        for(int i = 0; i < 5;i++)
//        {
//            Menel m = new Menel();
//            m.pozycjax = Losowy.nextInt(99 - 0);
//        }
//        implementacja tablicy danej liczby meneli
//        Menel menele[]= new Menel[10];
//        menele[1].pozycjax = 1;

        //implementacja dresa
        Dres Seba = new Dres(Rozmiar-1,0,10,5,0);

        //implementacja klucza
        Klucz Key = new Klucz(Rozmiar - 5, Rozmiar - 4);

        //implementacja policjanta
        Policjant Policjant = new Policjant(1, 1, 2);

        //element zliczający ile ruchów trwała gra
        int Ilosc_Ruchow = 0;

        //uzależnienie jak się gra skończyła 2 -> aresztowanie wszystkich meneli i dresow 1 -> zebranie klucza  0 -> okradzenie ze wszystkich pieniędzy
        int Ktory = 0;

        //puszczenie losowania do kradzieży
        int Kradziez;

        //Jezeli parametr ten zostanie zmieniony to zostanie wyswietlony aktualny stan mapy
        int Akcja = 0;

        //Jaki typ akcji został wykonany
        String Typ_Akcji = " ";

        while (true) {
            Ilosc_Ruchow++;
            //sprawdzenie czy glowny bohater moze podniesc klucz jezeli tak to koniec gry
            if(Marek.Czy_moze_podniesc_klucz(Key.pozycja_x,Key.pozycja_y) == 1)
            {
                Akcja++;
                Key = null;
                Typ_Akcji += "\nPodniesienie klucza przez Glownego Bohatera";
                Ktory = 1;
                break;
            }
            //jak glowny bohater nie moze podniesc klucza to sprawdzenie czy go widzi
            //jak tak to poruszanie sie w strone klucza
            //a po ruchu ponowne sprawdzenie czy moze podniesc klucz
            else if(Marek.Czy_widzi_klucz(Key.pozycja_x, Key.pozycja_y) == 1) {
                Marek.w_strone(Key.pozycja_x, Key.pozycja_y);
                Akcja++;
                Typ_Akcji += "\nRuch w strone klucza przez Glownego Bohatera";


                if (Marek.Czy_moze_podniesc_klucz(Key.pozycja_x, Key.pozycja_y) == 1) {
                    Typ_Akcji += "\nPodniesienie klucza przez Glownego Bohatera";
                    Key = null;
                    Ktory = 1;
                    break;
                }
            }
            //jak nie widzi klucza to sprawdzenie czy moze podniesc pieniadze
            else if(Kasa != null && Marek.Czy_podniesie_pieniadza(Kasa.pozycja_x, Kasa.pozycja_y) == 1) {
                Akcja++;
                Typ_Akcji += "\nPodniesienie pieniadza przez Glownego Bohatera";

                Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                if(Kuba != null)
                    Dodawanie(Kuba.GimmeType(), Kuba.Gimmex(), Kuba.Gimmey());
                if(Seba != null)
                    Dodawanie(Seba.GimmeType(), Seba.Gimmex(), Seba.Gimmey());
                Dodawanie(Kasa.GimmeType(),Kasa.Gimmex(), Kasa.Gimmey());
                Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
                Dodawanie(Key.GimmeType(), Key.Gimmex(), Key.Gimmey());
                System.out.println("Przed podniesieniem Pieniadza przez Glownego Bohatera");
                Wyswietlanie_Tablicy(Rozmiar);
                System.out.println();
                Reset(Rozmiar);

                Marek.ilosc_pieniedzy += Kasa.wartosc;
                Typ_Akcji += "Głowny bohater podnosi pieniadze   ->   " + Kasa.wartosc;
                Typ_Akcji += "Glowny bohater ma teraz " + Marek.ilosc_pieniedzy + " pieniedzy";
                Kasa = null;
                //z tego wynika ze nie widzi klucza a pieniadze podniosl takze ruch Losowy bo nic innego nie widzi
                Marek.Poruszanie_sie(Rozmiar);
            }
            //jak nie moze podniesc pieniedzy to sprawdzenie czy moze isc w ich kierunku
            else if (Kasa != null && Marek.Czy_widzi_pieniadze(Kasa.pozycja_x, Kasa.pozycja_y) == 1)//jak MC nie widzi klucza to sprawdza czy widzi pieniadze
            {
                Marek.w_strone(Kasa.pozycja_x, Kasa.pozycja_y);
                Akcja++;
                Typ_Akcji += "\nRuch w strone pieniedzy przez Glownego Bohatera";
                //ponowne sprawdzenie czy moze podniesc pieniadze
                if (Marek.Czy_podniesie_pieniadza(Kasa.pozycja_x, Kasa.pozycja_y) == 1) {
                    Akcja++;
                    Typ_Akcji += "\nPodniesienie pieniadza przez Glownego Bohatera";

                    Marek.ilosc_pieniedzy += Kasa.wartosc;
                    Typ_Akcji += "\nGłowny bohater podnosi pieniadze   ->   " + Kasa.wartosc;
                    Typ_Akcji += "\nGlowny bohater ma teraz " + Marek.ilosc_pieniedzy + " pieniedzy";

                    Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                    if(Kuba != null)
                        Dodawanie(Kuba.GimmeType(), Kuba.Gimmex(), Kuba.Gimmey());
                    if(Seba != null)
                        Dodawanie(Seba.GimmeType(), Seba.Gimmex(), Seba.Gimmey());
                    Dodawanie(Kasa.GimmeType(),Kasa.Gimmex(), Kasa.Gimmey());
                    Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
                    Dodawanie(Key.GimmeType(), Key.Gimmex(), Key.Gimmey());
                    System.out.println("Przed podniesieniem Pieniadza po ruchu Glownego Bohatera");
                    Wyswietlanie_Tablicy(Rozmiar);
                    System.out.println();
                    Reset(Rozmiar);

                    Kasa = null;
                }
            }
            else//jak marek nic nie widzi porusza sie losowo
            {
                Marek.Poruszanie_sie(Rozmiar);
            }


            //ruch dresa
            if(Seba != null) {
                if (Ilosc_Ruchow == 1 && Seba.Czy_moze_okrasc(Marek.pozycja_x, Marek.pozycja_y) == 1)//sprawdzenie czy dres moze okrasc glownego bohatera
                {
                    Akcja++;
                    Typ_Akcji += "\nKradziez przez Dresa";
                    Kradziez = Losowy.nextInt(Seba.max_pieniedzy - Seba.min_pieniedzy + 1) + Seba.min_pieniedzy;//losowanie z przedzialu liczby ile kradnie dres
                    Marek.ilosc_pieniedzy -= Kradziez;//kradziez dresa


                    Typ_Akcji += "\nDres ukradł -> " + Kradziez + "     ruch   " + Ilosc_Ruchow;
                    Typ_Akcji += "\nGlownemu bohaterowi zostalo -> " + Marek.ilosc_pieniedzy;


                    Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                    if(Kuba != null)
                        Dodawanie(Kuba.GimmeType(), Kuba.Gimmex(), Kuba.Gimmey());
                    Dodawanie(Seba.GimmeType(), Seba.Gimmex(), Seba.Gimmey());
                    if( Kasa != null)
                        Dodawanie(Kasa.GimmeType(),Kasa.Gimmex(), Kasa.Gimmey());
                    Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
                    Dodawanie(Key.GimmeType(), Key.Gimmex(), Key.Gimmey());
                    System.out.println("Przed kradzieza po ruchu dresa");
                    Wyswietlanie_Tablicy(Rozmiar);
                    System.out.println();
                    Reset(Rozmiar);

                    if (Marek.ilosc_pieniedzy <= 0)//sprawdzenie czy dres okradl na tyle ze jest koniec gry
                        break;
                    Seba.przerwa_od_kradzenia = 7;//ustawienie przerwy dla dresa zeby nie chodzil za MC i kradl go caly czas
                }
                else if(Seba.Czy_widzi_cos2(Marek.pozycja_x, Marek.pozycja_y) == 1)
                {
                    int Pomoc1, Pomoc2 ;//potrzebne do przetrzymania pozycji Dresa przed jego ruchem do wyswietlania
                    Pomoc1 = Seba.pozycja_x;
                    Pomoc2 = Seba.pozycja_y;
                    Seba.wstrone(Marek.pozycja_x, Marek.pozycja_y);

                    if (Seba.Czy_moze_okrasc(Marek.pozycja_x, Marek.pozycja_y) == 1)//sprawdzenie czy dres moze okrasc
                    {
                        Akcja++;
                        Typ_Akcji += "\nKradziez przez Dresa";
                        Kradziez = Losowy.nextInt(Seba.max_pieniedzy - Seba.min_pieniedzy + 1) + Seba.min_pieniedzy;//losowanie z przedzialu liczby ile kradnie dres
                        Marek.ilosc_pieniedzy -= Kradziez;//kradziez dresa

                        //wyswietlanie przed ruchem dresa
                        Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                        if(Kuba != null)
                            Dodawanie(Kuba.GimmeType(), Kuba.Gimmex(), Kuba.Gimmey());
                        Dodawanie(Seba.GimmeType(), Pomoc1, Pomoc2);
                        if( Kasa != null)
                            Dodawanie(Kasa.GimmeType(),Kasa.Gimmex(), Kasa.Gimmey());

                        Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
                        Dodawanie(Key.GimmeType(), Key.Gimmex(), Key.Gimmey());
                        System.out.println("Przed kradzieza przed ruchem dresa");
                        Wyswietlanie_Tablicy(Rozmiar);
                        System.out.println();
                        Reset(Rozmiar);

                        Typ_Akcji += "\nDres ukradł -> " + Kradziez + "     ruch   " + Ilosc_Ruchow ;
                        Typ_Akcji += "\nGlownemu bohaterowi zostalo -> " + Marek.ilosc_pieniedzy;

                        if (Marek.ilosc_pieniedzy <= 0)//sprawdzenie czy dres okradl na tyle ze jest koniec gry
                            break;
                        Seba.przerwa_od_kradzenia = 7;//ustawienie przerwy dla dresa zeby nie chodzil za MC i kradl go caly czas
                    }
                }
                else
                {
                    if(Seba.przerwa_od_kradzenia != 0)
                        Seba.przerwa_od_kradzenia--;
                    Seba.Poruszanie_sie(Rozmiar);
                }
            }


            //ruch menela
            if(Kuba != null) {
                if (Kuba.Czy_moze_okrasc(Marek.pozycja_x, Marek.pozycja_y) == 1)//ruch menela
                {
                    Akcja++;
                    Typ_Akcji += "\nKradziez ";
                    Kradziez = Losowy.nextInt(Kuba.max_pieniedzy - Kuba.min_pieniedzy + 1) + Kuba.min_pieniedzy;//losowanie z przedzialu liczby ile kradnie menel
                    Marek.ilosc_pieniedzy -= Kradziez;//kradziez menela
                    System.out.println("Menel ukradł -> " + Kradziez + "     ruch   " + Ilosc_Ruchow);
                    System.out.println("Glownemu bohaterowi zostalo -> " + Marek.ilosc_pieniedzy);
                    if (Marek.ilosc_pieniedzy <= 0)//sprawdzenie czy menel okradl na tyle ze jest koniec gry
                        break;
                    Kuba.przerwa_od_kradzenia = 3;//ustawienie przerwy od kradzenia dla menela
                    Kuba.Poruszanie_sie(Rozmiar);//ruszanie sie menela
                }
                else if(Kasa != null && Kuba.Czy_podniesie_pieniadza(Kasa.pozycja_x,Kasa.pozycja_y) == 1)
                {
                    Akcja++;
                    Typ_Akcji += "Podniesienie pieniadza przez menela";


                    //wyswietlanie przed podniesieniem pieniadza
                    Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                    Dodawanie(Kuba.GimmeType(), Kuba.Gimmex(), Kuba.Gimmey());
                    if(Seba != null)
                        Dodawanie(Seba.GimmeType(), Seba.Gimmex(), Seba.Gimmey());
                    Dodawanie(Kasa.GimmeType(),Kasa.Gimmex(), Kasa.Gimmey());
                    Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
                    Dodawanie(Key.GimmeType(), Key.Gimmex(), Key.Gimmey());
                    System.out.println("Przed podniesieniem pieniadza przez menela");
                    Wyswietlanie_Tablicy(Rozmiar);
                    System.out.println();
                    Reset(Rozmiar);

                    Kuba.przerwa_od_kradzenia--;
                    Kasa = null;
                    Kuba.Poruszanie_sie(Rozmiar);
                }
                else
                {
                    Kuba.Poruszanie_sie(Rozmiar);
                    Kuba.przerwa_od_kradzenia--;
                    if (Kuba.Czy_moze_okrasc(Marek.pozycja_x, Marek.pozycja_y) == 1) {
                        Akcja++;
                        Typ_Akcji += "\nKradziez ";
                        Kradziez = Losowy.nextInt(Kuba.max_pieniedzy - Kuba.min_pieniedzy + 1) + Kuba.min_pieniedzy;//losowanie z przedzialu liczby ile kradnie menel
                        Marek.ilosc_pieniedzy -= Kradziez;//kradziez menela

                        //wyswietlanie
                        Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                        Dodawanie(Kuba.GimmeType(), Kuba.Gimmex(), Kuba.Gimmey());
                        if(Seba != null)
                            Dodawanie(Seba.GimmeType(), Seba.Gimmex(), Seba.Gimmey());
                        if(Kasa != null)
                            Dodawanie(Kasa.GimmeType(),Kasa.Gimmex(), Kasa.Gimmey());
                        Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
                        Dodawanie(Key.GimmeType(), Key.Gimmex(), Key.Gimmey());
                        System.out.println("Przed kradzieza po ruchu dresa");
                        Wyswietlanie_Tablicy(Rozmiar);
                        System.out.println();
                        Reset(Rozmiar);
                        System.out.println("Menel ukradł -> " + Kradziez + "     ruch   " + Ilosc_Ruchow);
                        System.out.println("Glownemu bohaterowi zostalo -> " + Marek.ilosc_pieniedzy);


                        if (Marek.ilosc_pieniedzy <= 0)//sprawdzenie czy menel ukradl na tyle ze jest koniec gry
                            break;
                        Kuba.przerwa_od_kradzenia = 3;
                    }
                }
            }

            //pozniej ruszanie sie policjanta
            if (Seba != null && Policjant.Czy_widzi_dresa_lub_menela(Seba.pozycja_x, Seba.pozycja_y) == 1)
                {
                    Akcja++;
                    Typ_Akcji += "\nPoruszanie sie w strone dresa";

                    int Pomoc1, Pomoc2 ;//potrzebne do przetrzymania pozycji policjanta przed jego ruchem do wyswietlania
                    Pomoc1 = Policjant.pozycja_x;
                    Pomoc2 = Policjant.pozycja_y;
                    Policjant.w_strone(Seba.pozycja_x, Seba.pozycja_y);
                    if (Policjant.Czy_moze_aresztowac(Seba.pozycja_x, Seba.pozycja_y) == 1)
                        {
                            //wyswietlanie przed ruchem policjanta
                            Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                            if(Kuba != null)
                                Dodawanie(Kuba.GimmeType(), Kuba.Gimmex(), Kuba.Gimmey());
                            Dodawanie(Seba.GimmeType(), Seba.Gimmex(), Seba.Gimmey());
                            if( Kasa != null)
                                Dodawanie(Kasa.GimmeType(),Kasa.Gimmex(), Kasa.Gimmey());
                            Dodawanie(Policjant.GimmeType(), Pomoc1, Pomoc2);
                            Dodawanie(Key.GimmeType(), Key.Gimmex(), Key.Gimmey());
                            System.out.println("Przed aresztowaniem Dresa przed ruchem policjanta");
                            Wyswietlanie_Tablicy(Rozmiar);
                            System.out.println();
                            Reset(Rozmiar);


                            Akcja++;
                            Typ_Akcji += "\nAresztowanie dresa ";

                            //wyswietlanie po ruchu policjanta
                            Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                            if(Kuba != null)
                                Dodawanie(Kuba.GimmeType(), Kuba.Gimmex(), Kuba.Gimmey());
                            Dodawanie(Seba.GimmeType(), Seba.Gimmex(), Seba.Gimmey());
                            if( Kasa != null)
                                Dodawanie(Kasa.GimmeType(),Kasa.Gimmex(), Kasa.Gimmey());
                            Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
                            Dodawanie(Key.GimmeType(), Key.Gimmex(), Key.Gimmey());
                            System.out.println("Przed aresztowaniem Dresa po ruchu policjanta");
                            Wyswietlanie_Tablicy(Rozmiar);
                            System.out.println();
                            Reset(Rozmiar);


                            Seba = null;
                            if(Kuba == null)
                            {
                                Ktory = 2;
                                break;
                            }
                        }
                }
            else if(Kuba != null && Policjant.Czy_widzi_dresa_lub_menela(Kuba.pozycja_x, Kuba.pozycja_y) == 1)
                {
                    Akcja++;
                    Typ_Akcji += "\nPoruszanie sie w strone menela ";
                    int Pomoc1, Pomoc2 ;//potrzebne do przetrzymania pozycji policjanta przed jego ruchem do wyswietlania
                    Pomoc1 = Policjant.pozycja_x;
                    Pomoc2 = Policjant.pozycja_y;
                    Policjant.w_strone(Kuba.pozycja_x, Kuba.pozycja_y);
                    if (Policjant.Czy_moze_aresztowac(Kuba.pozycja_x, Kuba.pozycja_y) == 1)
                        {
                            //wyswietlanie przed ruchem policjanta
                            Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                            Dodawanie(Kuba.GimmeType(), Kuba.Gimmex(), Kuba.Gimmey());
                            if(Seba != null)
                                Dodawanie(Seba.GimmeType(), Seba.Gimmex(), Seba.Gimmey());

                            if( Kasa != null)
                                Dodawanie(Kasa.GimmeType(),Kasa.Gimmex(), Kasa.Gimmey());
                            Dodawanie(Policjant.GimmeType(), Pomoc1, Pomoc2);
                            Dodawanie(Key.GimmeType(), Key.Gimmex(), Key.Gimmey());
                            System.out.println("Przed aresztowaniem Menela przed ruchem policjanta");
                            Wyswietlanie_Tablicy(Rozmiar);
                            System.out.println();
                            Reset(Rozmiar);

                            Akcja++;
                            Typ_Akcji += "\nAresztowanie menela ";

                            //wyswietlanie po ruchu policjanta
                            Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                            Dodawanie(Kuba.GimmeType(), Kuba.Gimmex(), Kuba.Gimmey());
                            if(Seba != null)
                                Dodawanie(Seba.GimmeType(), Seba.Gimmex(), Seba.Gimmey());

                            if( Kasa != null)
                                Dodawanie(Kasa.GimmeType(),Kasa.Gimmex(), Kasa.Gimmey());

                            Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
                            Dodawanie(Key.GimmeType(), Key.Gimmex(), Key.Gimmey());

                            System.out.println("Przed aresztowaniem Menela po ruchu policjanta");
                            Wyswietlanie_Tablicy(Rozmiar);
                            Reset(Rozmiar);
                            System.out.println();


                            Kuba = null;
                            //System.out.println("Policjant aresztuje menela");
                            if(Seba == null)
                            {
                                Ktory = 2;
                                break;
                            }
                        }
                }
                else {
                    Policjant.Poruszanie_sie(Rozmiar);
                }


                //dodawanie poszczegolnych elementow do tablicy wyswietlajacej
            Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
            if(Kuba != null)
                Dodawanie(Kuba.GimmeType(), Kuba.Gimmex(), Kuba.Gimmey());
            if(Seba != null)
                Dodawanie(Seba.GimmeType(), Seba.Gimmex(), Seba.Gimmey());
            if( Kasa != null)
            {
                Dodawanie(Kasa.GimmeType(),Kasa.Gimmex(), Kasa.Gimmey());
            }
            Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
            Dodawanie(Key.GimmeType(), Key.Gimmex(), Key.Gimmey());

            if( Akcja > 0)
                {
                    System.out.println("Tura numer -> " + Ilosc_Ruchow);
                    System.out.println("Typ akcji: " + Typ_Akcji);
                    Wyswietlanie_Tablicy(Rozmiar);
                    System.out.println();
                    System.out.println();
                }
                Akcja = 0;
                Typ_Akcji = "";
                Reset(Rozmiar);
        }

        Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
        if(Kuba != null)
            Dodawanie(Kuba.GimmeType(), Kuba.Gimmex(), Kuba.Gimmey());
        if(Seba != null)
            Dodawanie(Seba.GimmeType(), Seba.Gimmex(), Seba.Gimmey());
        if(Kasa != null)
        {
            Dodawanie(Kasa.GimmeType(),Kasa.Gimmex(), Kasa.Gimmey());
        }
        Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
        if(Key != null)
            Dodawanie(Key.GimmeType(), Key.Gimmex(), Key.Gimmey());

        if( Akcja > 0)
        {
            System.out.println("Tura numer -> " + Ilosc_Ruchow);
            System.out.println("Typ akcji : " + Typ_Akcji);
            Wyswietlanie_Tablicy(Rozmiar);
            System.out.println();
        }

                if (Ktory == 1)
                    System.out.println("zebranie klucza");
                else if(Ktory == 2)
                    System.out.println("aresztowanie wszystkich meneli i dresow");
                else
                    System.out.println("okradzenie");
                System.out.println("koniecgry,    ilosc ruchow -> " + Ilosc_Ruchow);
        }
    }