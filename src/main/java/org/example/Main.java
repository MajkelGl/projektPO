package org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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
                Pozycje[miejscex][miejscey] = ANSI_PURPLE + "Y" + ANSI_RESET;
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
        MainCharacter Marek = new MainCharacter();
        Marek.pozycjax = 1;
        Marek.pozycjay = 1;
        Marek.ilosc_pieniedzy = 50;
        Marek.polewidzenia = 5;

        //implementacja pieniądza
        money kasa = new money();
        kasa.pozycjax = 7;
        kasa.pozycjay = 7;
        kasa.wartosc = 50;

        //implementacja menela
        Menel Kuba = new Menel();
        Kuba.pozycjax = 0;
        Kuba.pozycjay = rozmiar - 1;
        Kuba.max_pieniedzy = 5;
        Kuba.min_pieniedzy = 1;

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
        Dres seba = new Dres();
        seba.pozycjax = rozmiar - 1;
        seba.pozycjay = 0;
        seba.max_pieniedzy = 10;
        seba.min_pieniedzy = 5;
        seba.przerwaodkradzenia = 0;

        //implementacja klucza
        klucz key = new klucz();
        key.pozycjax = rozmiar - 5;
        key.pozycjay = rozmiar - 5;

        //implementacja policjanta
        Policjant policjant = new Policjant();
        policjant.pozycjax = 1;
        policjant.pozycjay = 1;
        policjant.czy_moze_aresztowac(seba.pozycjax, seba.pozycjay);
        policjant.czy_widzi_dresa_lub_menela(seba.pozycjax, seba.pozycjay);
        policjant.poruszaniesie(rozmiar);

        //element zliczający ile ruchów trwała gra
        int iloscruchow = 0;

        //uzależnienie jak się gra skończyła 1 -> zebranie klucza  0 -> okradzenie ze wszystkich pieniędzy
        int ktory = 0;

        //puszczenie losowania do kradzieży
        int kradziez;

        //Jezeli parametr ten zostanie zmieniony to zostanie wyswietlony aktualny stan mapy
        int akcja = 0;

        //Jaki typ akcji został wykonany
        String TypAkcji = " ";

        while (true) {
            iloscruchow++;
            // sprawdzenie czy MC widzi klucz,
            // jak widzi go to porusza sie w jego kierunku, gdy po ruchu stoi na nim zbiera go -> koniec gry
            // jak nie to sprawdza czy widzi pieniadze, jak tak to idzie w ich kierunku
            // a jak nie to porusza sie losowo
            if (Marek.czy_widzi_klucz(key.pozycjax, key.pozycjay) == 1) {
                Marek.wstrone(key.pozycjax, key.pozycjay);
                akcja++;
                TypAkcji += "zauwazenie klucza";
                if (Marek.czy_moze_podniesc_klucz(key.pozycjax, key.pozycjay) == 1) {
                    ktory = 1;
                    break;
                }
            }
            else if (Marek.czy_widzi_pieniadze(kasa.pozycjax, kasa.pozycjay) == 1 && kasa.wartosc != 0)//jak MC nie widzi klucza to sprawdza czy widzi pieniadze
            {
                Marek.wstrone(kasa.pozycjax, kasa.pozycjay);
                if (Marek.czy_podniesie_pieniadza(kasa.pozycjax, kasa.pozycjay) == 1) {
                    akcja++;
                    TypAkcji += "podniesienie pieniadza";
                    Marek.ilosc_pieniedzy += kasa.wartosc;
                    System.out.println("marek podnosi pieniadze   ->  " + kasa.wartosc);
                    kasa.wartosc = 0;
                }
            }
            else//jak marek nic nie widzi porusza sie losowo
            {
                Marek.poruszaniesie(rozmiar);
            }
            Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());//Dodanie do tablicy obiektu


            if(seba != null) {
                if (seba.czy_moze_okrasc(Marek.pozycjax, Marek.pozycjay) == 1)//sprawdzenie czy dres moze okrasc MC
                {
                    akcja++;
                    TypAkcji += "kradziez";
                    kradziez = losowy.nextInt(seba.max_pieniedzy - seba.min_pieniedzy + 1) + seba.min_pieniedzy;//losowanie z przedzialu liczby ile kradnie dres
                    Marek.ilosc_pieniedzy -= kradziez;//kradziez dresa
                    System.out.println("Dres ukradł -> " + kradziez + "     ruch   " + iloscruchow);
                    if (Marek.ilosc_pieniedzy <= 0)//sprawdzenie czy dres okradl na tyle ze jest koniec gry
                        break;
                    seba.przerwaodkradzenia = 7;//ustawienie przerwy dla dresa zeby nie chodzil za MC i kradl go caly czas
                    seba.czy_widzi_cos(Marek.pozycjax, Marek.pozycjay, rozmiar);//ruch dresa
                } else {
                    seba.czy_widzi_cos(Marek.pozycjax, Marek.pozycjay, rozmiar);//ruch dresa
                    if (seba.czy_moze_okrasc(Marek.pozycjax, Marek.pozycjay) == 1)//sprawdzenie czy dres moze okrasc
                    {
                        akcja++;
                        TypAkcji += "kradziez";
                        kradziez = losowy.nextInt(seba.max_pieniedzy - seba.min_pieniedzy + 1) + seba.min_pieniedzy;//losowanie z przedzialu liczby ile kradnie dres
                        Marek.ilosc_pieniedzy -= kradziez;//kradziez dresa
                        System.out.println("Dres ukradł -> " + kradziez + "     ruch   " + iloscruchow);
                        seba.przerwaodkradzenia = 7;//ustawienie przerwy dla dresa zeby nie chodzil za MC i kradl go caly czas
                        if (Marek.ilosc_pieniedzy <= 0)//sprawdzenie czy dres okradl na tyle ze jest koniec gry
                            break;
                    }
                }
                Dodawanie(seba.GimmeType(), seba.Gimmex(), seba.Gimmey());//Dodanie do tablicy obiektu
            }

            if(Kuba != null) {
                if (Kuba.czy_moze_okrasc(Marek.pozycjax, Marek.pozycjay, rozmiar) == 1)//ruch menela
                {
                    akcja++;
                    TypAkcji += "kradziez";
                    kradziez = losowy.nextInt(Kuba.max_pieniedzy - Kuba.min_pieniedzy + 1) + Kuba.min_pieniedzy;//losowanie z przedzialu liczby ile kradnie menel
                    Marek.ilosc_pieniedzy -= kradziez;//kradziez menela
                    System.out.println("Menel ukradł -> " + kradziez + "     ruch   " + iloscruchow);
                    if (Marek.ilosc_pieniedzy <= 0)//sprawdzenie czy menel okradl na tyle ze jest koniec gry
                        break;
                    Kuba.przerwaodkradzenia = 3;
                    Kuba.poruszaniesie(rozmiar);
                } else {
                    Kuba.poruszaniesie(rozmiar);
                    Kuba.przerwaodkradzenia--;
                    if (Kuba.czy_moze_okrasc(Marek.pozycjax, Marek.pozycjay, rozmiar) == 1) {
                        akcja++;
                        TypAkcji += "kradziez";
                        kradziez = losowy.nextInt(Kuba.max_pieniedzy - Kuba.min_pieniedzy + 1) + Kuba.min_pieniedzy;//losowanie z przedzialu liczby ile kradnie menel
                        Marek.ilosc_pieniedzy -= kradziez;//kradziez menela
                        System.out.println("Menel ukradł -> " + kradziez + "     ruch   " + iloscruchow);
                        if (Marek.ilosc_pieniedzy <= 0)//sprawdzenie czy menel ukradl na tyle ze jest koniec gry
                            break;
                        Kuba.przerwaodkradzenia = 3;
                    }
                }
                Dodawanie(Kuba.GimmeType(), Kuba.Gimmex(), Kuba.Gimmey());
            }
            //aresztowanie jezeli wartosc dresa lub menela jest rozna od null to aresztuje
            if(seba != null && policjant.czy_moze_aresztowac(seba.pozycjax, seba.pozycjay) == 1) {
                akcja++;
                TypAkcji += "aresztowanie";
                seba = null;
                System.out.println("policjant aresztuje dresa");
                //if(policjant.czy_widzi_dresa_lub_menela()
            }
            if (Kuba != null && policjant.czy_moze_aresztowac(Kuba.pozycjax, Kuba.pozycjay) == 1)
            {
                akcja++;
                TypAkcji += "aresztowanie";
                Kuba = null;
                System.out.println("policjant aresztuje menela");
            }

                if (seba != null && policjant.czy_widzi_dresa_lub_menela(seba.pozycjax, seba.pozycjay) == 1)
                {
                    akcja++;
                    TypAkcji += "Poruszanie sie w strone zlego";
                    policjant.wstrone(seba.pozycjax, seba.pozycjay);
                    if (policjant.czy_moze_aresztowac(seba.pozycjax, seba.pozycjay) == 1)
                    {
                        seba = null;
                        System.out.println("policjant aresztuje dresa");
                    }
                }
                else if(Kuba != null && policjant.czy_widzi_dresa_lub_menela(Kuba.pozycjax, Kuba.pozycjay) == 1)
                {
                    akcja++;
                    TypAkcji += "Poruszanie sie w strone zlego";
                    policjant.wstrone(Kuba.pozycjax, Kuba.pozycjay);
                if (policjant.czy_moze_aresztowac(Kuba.pozycjax, Kuba.pozycjay) == 1)
                    {
                        akcja++;
                        Kuba = null;
                        System.out.println("policjant aresztuje menela");
                    }
                }
                else {
                    policjant.poruszaniesie(rozmiar);
                }
                Dodawanie(policjant.GimmeType(), policjant.Gimmex(), policjant.Gimmey());
                Dodawanie(kasa.GimmeType(),kasa.Gimmex(), kasa.Gimmey());
                Dodawanie(key.GimmeType(), key.Gimmex(), key.Gimmey());
//                if( akcja > 0)
//                {
//                    System.out.println("Tura numer" + iloscruchow);
//                    System.out.println("Typ akcji" + TypAkcji);
//                    WyswietlanieTablicy(rozmiar);
//                    System.out.println();
//                    System.out.println();
//                }
            System.out.println("Tura numer" + iloscruchow);
            System.out.println("Typ akcji" + TypAkcji);
            WyswietlanieTablicy(rozmiar);
            System.out.println();
            System.out.println();
                akcja = 0;
                TypAkcji = "";
                Reset(rozmiar);
        }


//            if (Marek.czy_widzi_klucz(key.pozycjax, key.pozycjay, rozmiar) == 1)
//            {
//                ktory = 1;
//                break;
//            }
//            else
//            {
//                if (seba.czy_moze_okrasc(Marek.pozycjax, Marek.pozycjay) == 1)//sprawdzenie czy dres moze okrasc
//                {
//                    kradziez = losowy.nextInt(seba.max_pieniedzy - seba.min_pieniedzy + 1) + seba.min_pieniedzy;//losowanie z przedzialu liczby ile kradnie dres
//                    Marek.ilosc_pieniedzy -= kradziez;//kradziez dresa
//                    System.out.println("Dres ukradł -> " + kradziez);
//                    seba.przerwaodkradzenia = 7;//ustawienie przerwy dla dresa zeby nie chdzoil za MC i kradl go caly czas
//                    if (Marek.ilosc_pieniedzy <= 0)//sprawdzenie czy dres okradl na tyle ze jest koniec gry
//                    {
//                        break;
//                    }
//                    seba.czy_widzi_cos(Marek.pozycjax, Marek.pozycjay, rozmiar);//ruch dresa
//                    if (seba.czy_moze_okrasc(Marek.pozycjax, Marek.pozycjay) == 1) {
//                        kradziez = losowy.nextInt(seba.max_pieniedzy - seba.min_pieniedzy + 1) + seba.min_pieniedzy;//losowanie z przedzialu liczby ile kradnie dres
//                        Marek.ilosc_pieniedzy -= kradziez;//kradziez dresa
//                        System.out.println("Dres ukradł -> " + kradziez);
//                        if (Marek.ilosc_pieniedzy <= 0)//sprawdzenie czy dres okradl na tyle ze jest koniec gry{
//                            break;
//                    }
//                    seba.przerwaodkradzenia = 7;//ustawienie przerwy dla dresa zeby nie chdzoil za MC i kradl go caly czas
//                } else {
//                    if (Kuba.czy_moze_okrasc(Marek.pozycjax, Marek.pozycjay) == 1)//sprawdzenie czy menel moze okrasc
//                    {
//                        kradziez = losowy.nextInt(Kuba.max_pieniedzy - Kuba.min_pieniedzy + 1) + Kuba.min_pieniedzy;//losowanie z przedzialu liczby ile kradnie menel
//                        Marek.ilosc_pieniedzy -= kradziez;//kradziez menela
//                        System.out.println("menel ukradl -> " + kradziez);
//                        if (Marek.ilosc_pieniedzy <= 0)//sprawdzenie czy menel okradl na tyle ze jest koniec gry{
//                            break;
//                        Kuba.poruszaniesie(rozmiar);
//                    }
//                }
//                seba.czy_widzi_cos(Marek.pozycjax, Marek.pozycjay, rozmiar);
//                if (seba.czy_moze_okrasc(Marek.pozycjax, Marek.pozycjay) == 1) {
//                    kradziez = losowy.nextInt(seba.max_pieniedzy - seba.min_pieniedzy + 1) + seba.min_pieniedzy;//losowanie z przedzialu liczby ile kradnie dres
//                    Marek.ilosc_pieniedzy -= kradziez;//kradziez dresa
//                    System.out.println("Dres ukradł -> " + kradziez);
//                    seba.przerwaodkradzenia = 7;//ustawienie przerwy dla dresa zeby nie chdzoil za MC i kradl go caly czas
//                } else {
//                    if (Kuba.czy_moze_okrasc(Marek.pozycjax, Marek.pozycjay) == 1)//sprawdzenie czy menel moze okrasc
//                    {
//                        kradziez = losowy.nextInt(Kuba.max_pieniedzy - Kuba.min_pieniedzy + 1) + Kuba.min_pieniedzy;//losowanie z przedzialu liczby ile kradnie menel
//                        Marek.ilosc_pieniedzy -= kradziez;//kradziez menela
//                        System.out.println("menel ukradl -> " + kradziez);
//                        if (Marek.ilosc_pieniedzy <= 0)//sprawdzenie czy menel okradl na tyle ze jest koniec gry
//                        {
//                            koniecgry = true;
//                        } else {
//                            Kuba.poruszaniesie(rozmiar);
//                        }
//                    }
//                }
//            }
//        }
                if (ktory == 1)
                    System.out.println("zebranie klucza");
                else
                    System.out.println("okradzenie");
                System.out.println("koniecgry,    ilosc ruchow -> " + iloscruchow);
        }
    }