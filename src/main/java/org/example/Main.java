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
                Pozycje[miejscex][miejscey] = ANSI_PURPLE + "G" + ANSI_RESET;
                break;
            case 2:
                Pozycje[miejscex][miejscey] = ANSI_RED + "D" + ANSI_RESET;
                break;
            case 3:
                Pozycje[miejscex][miejscey] = ANSI_YELLOW + "M" + ANSI_RESET;
                break;
            case 4:
                Pozycje[miejscex][miejscey] = ANSI_BLUE + "P" + ANSI_RESET;
                break;
            case 5:
                Pozycje[miejscex][miejscey] = ANSI_GREEN + "F" + ANSI_RESET;
                break;
            case 6:
                Pozycje[miejscex][miejscey] = ANSI_CYAN + "K" + ANSI_RESET;
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
        System.out.println("rozmiar mapy -> 25");
        //Scanner scan = new Scanner(System.in);
        int rozmiar = 25;//scan.nextInt();

        //zaimplementowanie głównej postaci
        Glowny_Bohater Marek = new Glowny_Bohater(1, 1, 50, 5);

        //implementacja pieniądza
        Pieniadze kasa = new Pieniadze(7,7,50);

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
        Klucz key = new Klucz(rozmiar - 5, rozmiar -5 );

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
                TypAkcji += "\nPodniesienie klucza ";
                ktory = 1;
                break;
            }
            //jak glowny bohater nie moze podniesc klucza to sprawdzenie czy go widzi
            //jak tak to poruszanie sie w strone klucza
            //a po ruchu ponowne sprawdzenie czy moze podniesc klucz
            else if(Marek.czy_widzi_klucz(key.pozycja_x, key.pozycja_y) == 1) {
                Marek.wstrone(key.pozycja_x, key.pozycja_y);
                akcja++;
                TypAkcji += "\nRuch w strone Klucza ";
                if (Marek.czy_moze_podniesc_klucz(key.pozycja_x, key.pozycja_y) == 1) {
                    TypAkcji += "\nPodniesienie klucza ";
                    key = null;
                    ktory = 1;
                    break;
                }
            }
            //jak nie widzi klucza to sprawdzenie czy moze podniesc pieniadze
            else if (kasa != null && Marek.czy_podniesie_pieniadza(kasa.pozycja_x, kasa.pozycja_y) == 1) {
                akcja++;
                TypAkcji += "\nPodniesienie pieniadza ";
                Marek.ilosc_pieniedzy += kasa.wartosc;
                System.out.println("Głowny bohater podnosi pieniadze   ->  " + kasa.wartosc);
                System.out.println("Glowny bohater ma teraz " + Marek.ilosc_pieniedzy + " pieniedzy");
                kasa = null;
                //z tego wynika ze nie widzi klucza a pieniadze podniosl takze ruch losowy bo nic innego nie widzi
                Marek.poruszanie_sie(rozmiar);
            }
            //jak nie moze podniesc pieniedzy to sprawdzenie czy moze isc w ich kierunku
            else if (kasa != null && Marek.czy_widzi_pieniadze(kasa.pozycja_x, kasa.pozycja_y) == 1 && kasa.wartosc != 0)//jak MC nie widzi klucza to sprawdza czy widzi pieniadze
            {
                Marek.wstrone(kasa.pozycja_x, kasa.pozycja_y);
                akcja++;
                TypAkcji += "\nRuch w strone pieniedzy ";
                //ponowne sprawdzenie czy moze podniesc pieniadze
                if (Marek.czy_podniesie_pieniadza(kasa.pozycja_x, kasa.pozycja_y) == 1) {
                    akcja++;
                    TypAkcji += "\nPodniesienie pieniadza ";
                    Marek.ilosc_pieniedzy += kasa.wartosc;
                    System.out.println("Głowny bohater podnosi pieniadze   ->  " + kasa.wartosc);
                    System.out.println("Glowny bohater ma teraz " + Marek.ilosc_pieniedzy + " pieniedzy");
                    kasa = null;
                }
            }
            else//jak marek nic nie widzi porusza sie losowo
            {
                Marek.poruszanie_sie(rozmiar);
            }


            //ruch dresa
            if(Seba != null) {
                if (Seba.czy_moze_okrasc(Marek.pozycja_x, Marek.pozycja_y) == 1)//sprawdzenie czy dres moze okrasc glownego bohatera
                {
                    akcja++;
                    TypAkcji += "\nKradziez ";
                    kradziez = losowy.nextInt(Seba.max_pieniedzy - Seba.min_pieniedzy + 1) + Seba.min_pieniedzy;//losowanie z przedzialu liczby ile kradnie dres
                    Marek.ilosc_pieniedzy -= kradziez;//kradziez dresa
                    System.out.println("Dres ukradł -> " + kradziez + "     ruch   " + iloscruchow);
                    System.out.println("Glownemu bohaterowi zostalo -> " + Marek.ilosc_pieniedzy);
                    if (Marek.ilosc_pieniedzy <= 0)//sprawdzenie czy dres okradl na tyle ze jest koniec gry
                        break;
                    Seba.przerwa_od_kradzenia = 7;//ustawienie przerwy dla dresa zeby nie chodzil za MC i kradl go caly czas
                    Seba.czy_widzi_cos(Marek.pozycja_x, Marek.pozycja_y, rozmiar);//ruszanie sie dresa
                } else {
                    Seba.czy_widzi_cos(Marek.pozycja_x, Marek.pozycja_y, rozmiar);//ruszanie sie dresa
                    if (Seba.czy_moze_okrasc(Marek.pozycja_x, Marek.pozycja_y) == 1)//sprawdzenie czy dres moze okrasc
                    {
                        akcja++;
                        TypAkcji += "\nKradziez ";
                        kradziez = losowy.nextInt(Seba.max_pieniedzy - Seba.min_pieniedzy + 1) + Seba.min_pieniedzy;//losowanie z przedzialu liczby ile kradnie dres
                        Marek.ilosc_pieniedzy -= kradziez;//kradziez dresa
                        System.out.println("Dres ukradł -> " + kradziez + "     ruch   " + iloscruchow);
                        System.out.println("Glownemu bohaterowi zostalo -> " + Marek.ilosc_pieniedzy);
                        Seba.przerwa_od_kradzenia = 7;//ustawienie przerwy dla dresa zeby nie chodzil za MC i kradl go caly czas
                        if (Marek.ilosc_pieniedzy <= 0)//sprawdzenie czy dres okradl na tyle ze jest koniec gry
                            break;
                    }
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
                } else {
                    Kuba.poruszanie_sie(rozmiar);
                    Kuba.przerwa_od_kradzenia--;
                    if (Kuba.czy_moze_okrasc(Marek.pozycja_x, Marek.pozycja_y) == 1) {
                        akcja++;
                        TypAkcji += "\nKradziez ";
                        kradziez = losowy.nextInt(Kuba.max_pieniedzy - Kuba.min_pieniedzy + 1) + Kuba.min_pieniedzy;//losowanie z przedzialu liczby ile kradnie menel
                        Marek.ilosc_pieniedzy -= kradziez;//kradziez menela
                        System.out.println("Menel ukradł -> " + kradziez + "     ruch   " + iloscruchow);
                        System.out.println("Glownemu bohaterowi zostalo -> " + Marek.ilosc_pieniedzy);
                        if (Marek.ilosc_pieniedzy <= 0)//sprawdzenie czy menel ukradl na tyle ze jest koniec gry
                            break;
                        Kuba.przerwa_od_kradzenia = 3;
                    }
                }
            }

            //pozniej po mozliwym aresztowaniu ruszanie sie policjanta
            if (Seba != null && Policjant.czy_widzi_dresa_lub_menela(Seba.pozycja_x, Seba.pozycja_y) == 1)
                {
                    akcja++;
                    TypAkcji += "\nPoruszanie sie w strone dresa";
                    Policjant.w_strone(Seba.pozycja_x, Seba.pozycja_y);
                    if (Policjant.czy_moze_aresztowac(Seba.pozycja_x, Seba.pozycja_y) == 1)
                        {
                            akcja++;
                            TypAkcji += "\nAresztowanie dresa ";
                            Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                            if(Kuba != null)
                                Dodawanie(Kuba.GimmeType(), Kuba.Gimmex(), Kuba.Gimmey());
                            Dodawanie(Seba.GimmeType(), Seba.Gimmex(), Seba.Gimmey());
                            if( kasa != null)
                                Dodawanie(kasa.GimmeType(),kasa.Gimmex(), kasa.Gimmey());

                            Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
                            Dodawanie(key.GimmeType(), key.Gimmex(), key.Gimmey());
                            System.out.println("Przed aresztowaniem po ruchu policjanta");
                            WyswietlanieTablicy(rozmiar);
                            System.out.println();
                            Reset(rozmiar);
                            Seba = null;
                            //System.out.println("Policjant aresztuje dresa");
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
                    Policjant.w_strone(Kuba.pozycja_x, Kuba.pozycja_y);
                    if (Policjant.czy_moze_aresztowac(Kuba.pozycja_x, Kuba.pozycja_y) == 1)
                        {
                            akcja++;
                            TypAkcji += "\nAresztowanie menela ";
                            Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                            Dodawanie(Kuba.GimmeType(), Kuba.Gimmex(), Kuba.Gimmey());
                            if(Seba != null)
                                Dodawanie(Seba.GimmeType(), Seba.Gimmex(), Seba.Gimmey());

                            if( kasa != null)
                                Dodawanie(kasa.GimmeType(),kasa.Gimmex(), kasa.Gimmey());

                            Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
                            Dodawanie(key.GimmeType(), key.Gimmex(), key.Gimmey());

                            System.out.println("Przed aresztowaniem po ruchu policjanta");
                            WyswietlanieTablicy(rozmiar);
                            System.out.println();
                            Kuba = null;
                            Reset(rozmiar);
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