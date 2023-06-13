package org.example;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemException;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class Main {

    // Tablica stringow potrzebna do wizualizacji
    static String[][] Pozycje = new String[100][100];
    static int Ilosc_Ruchow = 0;

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
        System.out.println("\033[H\033[2J");
        for( int i = 0; i < rozmiar; i++)
        {
            for( int j = 0; j < rozmiar; j++)
            {
                Pozycje[i][j] = "·";
            }
        }
    }


    //Metoda, ktora dodaje obiekty do tablicy wizualizacyjnej
    public static void Dodawanie(int typ, int miejscex, int miejscey) {
        switch (typ) {
            case 1 -> Pozycje[miejscey][miejscex] = ANSI_PURPLE + "G" + ANSI_RESET;
            case 2 -> Pozycje[miejscey][miejscex] = ANSI_RED + "D" + ANSI_RESET;
            case 3 -> Pozycje[miejscey][miejscex] = ANSI_YELLOW + "M" + ANSI_RESET;
            case 4 -> Pozycje[miejscey][miejscex] = ANSI_BLUE + "P" + ANSI_RESET;
            case 5 -> Pozycje[miejscey][miejscex] = ANSI_GREEN + "$" + ANSI_RESET;
            case 6 -> Pozycje[miejscey][miejscex] = ANSI_CYAN + "K" + ANSI_RESET;
        }
    }

    // metoda wyswietlajaca tablice wizualizacyjna
    public static void Wyswietlanie_Tablicy(int rozmiar) throws InterruptedException {
        for( int i = 0; i < rozmiar; i++)
        {
            for( int j = 0; j < rozmiar; j++)
            {
                System.out.print(Pozycje[i][j]);
                //Thread.sleep(0,1);
            }
            System.out.println();
        }
        Thread.sleep(1000);
    }
    public static void main(String[] args) throws InterruptedException, IOException {

        //Potrzebne metody do stworzenia pliku i zapisywania do niego wynikow symulacji
        Writer plik = new FileWriter("wyniki.txt", true );
        BufferedWriter zapis = new BufferedWriter(plik);

        Random Losowy = new Random();
        //inicjalizacja rozmiaru planszy
        System.out.println("rozmiar mapy -> 50\n\n");
        //Scanner scan = new Scanner(System.in);
        int Rozmiar = 100;//scan.nextInt();

        ///int ilosc_main = atoi(args[1]);

        //zaimplementowanie głównej postaci
        Glowny_Bohater Marek = new Glowny_Bohater(Losowy.nextInt(Rozmiar), Losowy.nextInt(Rozmiar), 50, 5);

        //implementacja pieniędzy
        Pieniadze[] pieniadze = new Pieniadze[10];
        for(int i = 0; i < 10;i++)
        {
            pieniadze[i] = new Pieniadze(Losowy.nextInt(Rozmiar),Losowy.nextInt(Rozmiar),Losowy.nextInt(30 - 10 + 1) + 10);
        }

        //implementacja menela

        Menel[] menele = new Menel[30];
        for(int i = 0; i < menele.length;i++)
            menele[i] = new Menel(Losowy.nextInt(50),Losowy.nextInt(50),5,1);


        //implementacja dresa
        Dres[] dresy = new Dres[30];
        for(int i = 0; i < dresy.length;i++)
            dresy[i] = new Dres(Losowy.nextInt(Rozmiar),Losowy.nextInt(Rozmiar),10,5,0);

        //implementacja klucza
        Klucz Klucz = new Klucz(Losowy.nextInt(Rozmiar), Losowy.nextInt(Rozmiar));

        //implementacja policjanta
        Policjant[] Policjant = new Policjant[1];
        for(int i = 0; i < Policjant.length;i++)
            Policjant[i] = new Policjant(Losowy.nextInt(Rozmiar),Losowy.nextInt(Rozmiar),2);

        //uzależnienie jak się gra skończyła 2 -> aresztowanie wszystkich meneli i dresow 1 -> zebranie klucza  0 -> okradzenie ze wszystkich pieniędzy
        int Ktory = -1;

        //puszczenie losowania do kradzieży
        int Kradziez;

        //Jezeli parametr ten zostanie zmieniony to zostanie wyswietlony aktualny stan mapy
        int Akcja = 0;

        //Zmienne zliczajace liczbe wykonanych kradziezy i aresztowan
        int Ilosc_Kradziezy = 0;
        int Ilosc_Kradziezy_D = 0;
        int Ilosc_Kradziezy_M = 0;
        int Ilosc_Aresztowan = 0;

        //Zmienna zliczajaca liczbe zabranych pieniedzy podczas kradziezy
        int Ilosc_Zabranych = 0;

        //Jaki typ akcji został wykonany
        String Typ_Akcji = " ";

        int liczba;//parametry potrzebne do okreslania ktory obiekt jest blizej przy ruchu glownego bohatera i menela
        int odleglosc, tymcz_odlegl;

        //wyswietlenie mapy jak wyglada przed ruchami
        Reset(Rozmiar);
          System.out.println("Mapa wyglada tak:");
        Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
        for (Menel menel : menele)
            Dodawanie(menel.GimmeType(), menel.Gimmex(), menel.Gimmey());
        for (Dres dres : dresy)
            Dodawanie(dres.GimmeType(), dres.Gimmex(), dres.Gimmey());
        for (Pieniadze value : pieniadze)
            Dodawanie(value.GimmeType(), value.Gimmex(), value.Gimmey());
        for (org.example.Policjant policjant : Policjant)
            Dodawanie(policjant.GimmeType(), policjant.Gimmex(), policjant.Gimmey());
         Dodawanie(Klucz.GimmeType(), Klucz.Gimmex(), Klucz.Gimmey());
              Wyswietlanie_Tablicy(Rozmiar);
          System.out.println();
        Reset(Rozmiar);

        while (true) {

            Ilosc_Ruchow++;
            //sprawdzenie czy glowny bohater moze podniesc klucz jezeli tak to koniec gry
            if(Marek.Czy_moze_podniesc_klucz(Klucz.pozycja_x,Klucz.pozycja_y) == 1)
            {
                Akcja++;
                Klucz = null;
                Typ_Akcji += "\nPodniesienie klucza przez Glownego Bohatera";
                Ktory = 1;
                break;
            }
            //jak glowny bohater nie moze podniesc klucza to sprawdzenie czy go widzi
            //jak tak to poruszanie sie w strone klucza
            //a po ruchu ponowne sprawdzenie czy moze podniesc klucz
            else if(Marek.Czy_widzi_klucz(Klucz.pozycja_x, Klucz.pozycja_y) == 1) {
                Marek.w_strone(Klucz.pozycja_x, Klucz.pozycja_y);
                Akcja++;
                Typ_Akcji += "\nRuch w strone klucza przez Glownego Bohatera";

                if (Marek.Czy_moze_podniesc_klucz(Klucz.pozycja_x, Klucz.pozycja_y) == 1) {
                    Typ_Akcji += "\nPodniesienie klucza przez Glownego Bohatera";
                    Klucz = null;
                    Ktory = 1;
                    break;
                }
            }
            else
            {
                for(int i = 0; i < pieniadze.length; i++) //sprawdzenie czy glowny bohater moze podniesc pieniadze
                {
                    if (pieniadze[i] != null && Marek.Czy_podniesie_pieniadza(pieniadze[i].pozycja_x, pieniadze[i].pozycja_y) == 1)
                    {
                        Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                        for (Menel menel : menele)
                            if (menel != null)
                                Dodawanie(menel.GimmeType(), menel.Gimmex(), menel.Gimmey());
                        for (Dres dres : dresy)
                            if (dres != null)
                                Dodawanie(dres.GimmeType(), dres.Gimmex(), dres.Gimmey());
                        for (Pieniadze value : pieniadze) {
                            if (value != null)
                                Dodawanie(value.GimmeType(), value.Gimmex(), value.Gimmey());
                        }
                        for (org.example.Policjant policjant : Policjant)
                            Dodawanie(policjant.GimmeType(), policjant.Gimmex(), policjant.Gimmey());
                        Dodawanie(Klucz.GimmeType(), Klucz.Gimmex(), Klucz.Gimmey());
                             System.out.println("Przed podniesieniem Pieniadza przez Glownego Bohatera");
                                             Wyswietlanie_Tablicy(Rozmiar);
                             System.out.println();
                        Reset(Rozmiar);

                        Marek.ilosc_pieniedzy += pieniadze[i].wartosc;
                        Typ_Akcji += "\nGłowny bohater podnosi pieniadze   ->   " + pieniadze[i].wartosc;
                        Typ_Akcji += "\nGlowny bohater ma teraz " + Marek.ilosc_pieniedzy + " pieniedzy";

                        pieniadze[i] = null;
                        Akcja++;
                        Typ_Akcji += "\nPodniesienie pieniadza przez Glownego Bohatera";
                    }

                }
                odleglosc = 2147483647; //parametry potrzebne do okreslenia ktore pieniadze sa najblizej
                liczba = -1;//jak zmieni wartosc to znaczy ze jest jakis pieniadz ktory ma wartosc != null i do ktorego moze podejsc glowny bohater
                for(int i = 0; i < pieniadze.length; i++)
                {
                    if(pieniadze[i] != null && Marek.Czy_widzi_pieniadze(pieniadze[i].pozycja_x,pieniadze[i].pozycja_y) == 1)
                    {
                        tymcz_odlegl = (pieniadze[i].pozycja_x - Marek.pozycja_x)*(pieniadze[i].pozycja_x - Marek.pozycja_x) + (pieniadze[i].pozycja_y - Marek.pozycja_y)*(pieniadze[i].pozycja_y - Marek.pozycja_y);
                        if(tymcz_odlegl < odleglosc)
                        {
                            odleglosc = tymcz_odlegl;
                            liczba = i;
                        }
                    }
                }
                if(liczba != -1)
                {
                    Akcja++;
                    Typ_Akcji += "Ruch w strone pieniadza przez Glownego bohatera";
                    Marek.w_strone(pieniadze[liczba].pozycja_x, pieniadze[liczba].pozycja_y);

                    if (pieniadze[liczba] != null && Marek.Czy_podniesie_pieniadza(pieniadze[liczba].pozycja_x, pieniadze[liczba].pozycja_y) == 1)
                    {
                        Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                        for (Menel menel : menele)
                            if (menel != null)
                                Dodawanie(menel.GimmeType(), menel.Gimmex(), menel.Gimmey());
                        for (Dres dres : dresy)
                            if (dres != null)
                                Dodawanie(dres.GimmeType(), dres.Gimmex(), dres.Gimmey());
                        for (Pieniadze value : pieniadze)
                            if (value != null)
                                Dodawanie(value.GimmeType(), value.Gimmex(), value.Gimmey());
                        for (org.example.Policjant policjant : Policjant)
                            Dodawanie(policjant.GimmeType(), policjant.Gimmex(), policjant.Gimmey());
                        Dodawanie(Klucz.GimmeType(), Klucz.Gimmex(), Klucz.Gimmey());
                             System.out.println("Przed podniesieniem Pieniadza przez Glownego Bohatera");
                                              Wyswietlanie_Tablicy(Rozmiar);
                            System.out.println();
                        Reset(Rozmiar);

                        Marek.ilosc_pieniedzy += pieniadze[liczba].wartosc;
                        Typ_Akcji += "\nGłowny bohater podnosi pieniadze   ->   " + pieniadze[liczba].wartosc;
                        Typ_Akcji += "\nGlowny bohater ma teraz " + Marek.ilosc_pieniedzy + " pieniedzy";

                        pieniadze[liczba] = null;
                        Akcja++;
                        Typ_Akcji += "\nPodniesienie pieniadza przez Glownego Bohatera";
                    }
                }
                else
                    Marek.Poruszanie_sie(Rozmiar);
            }


            for(int i = 0; i < dresy.length;i++)
            {
                if(dresy[i] != null)
                {
                    if(dresy[i].Czy_moze_okrasc(Marek.pozycja_x, Marek.pozycja_y) == 1)
                    {
                        Akcja++;
                        Typ_Akcji += "\nKradziez przez Dresa";
                        Ilosc_Kradziezy++;
                        Ilosc_Kradziezy_D++;
                        Kradziez = Losowy.nextInt(dresy[i].max_pieniedzy - dresy[i].min_pieniedzy + 1) + dresy[i].min_pieniedzy;//losowanie z przedzialu liczby ile kradnie dres
                        Ilosc_Zabranych += Kradziez;
                        Marek.ilosc_pieniedzy -= Kradziez;//kradziez dresa


                        Typ_Akcji += "\nDres ukradł -> " + Kradziez + "     ruch   " + Ilosc_Ruchow;
                        Typ_Akcji += "\nGlownemu bohaterowi zostalo -> " + Marek.ilosc_pieniedzy;


                        Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                        for (Menel menel : menele)
                            if (menel != null)
                                Dodawanie(menel.GimmeType(), menel.Gimmex(), menel.Gimmey());
                        for (Dres dres : dresy)
                            if (dres != null)
                                Dodawanie(dres.GimmeType(), dres.Gimmex(), dres.Gimmey());
                        for (Pieniadze value : pieniadze)
                            if (value != null)
                                Dodawanie(value.GimmeType(), value.Gimmex(), value.Gimmey());
                        for (org.example.Policjant policjant : Policjant)
                            Dodawanie(policjant.GimmeType(), policjant.Gimmex(), policjant.Gimmey());
                        Dodawanie(Klucz.GimmeType(), Klucz.Gimmex(), Klucz.Gimmey());
                           System.out.println("Przed kradzieza dresa");
                                      Wyswietlanie_Tablicy(Rozmiar);
                            System.out.println();
                        Reset(Rozmiar);

                        if (Marek.ilosc_pieniedzy <= 0)//sprawdzenie czy dres okradl na tyle ze jest koniec gry
                        {
                            Ktory = 0;
                            break;
                        }
                        dresy[i].przerwa_od_kradzenia = 7;//ustawienie przerwy dla dresa zeby nie chodzil za MC i kradl go caly czas
                        dresy[i].Poruszanie_sie(Rozmiar);
                    }
                    else if(dresy[i].Czy_widzi_cos(Marek.pozycja_x, Marek.pozycja_y) == 1)
                    {
                        Akcja++;
                        Typ_Akcji += "\nRuch Dresa w strone Glownego Bohatera";

                        dresy[i].wstrone(Marek.pozycja_x, Marek.pozycja_y);
                        if(dresy[i].Czy_moze_okrasc(Marek.pozycja_x, Marek.pozycja_y) == 1)
                        {
                            Akcja++;
                            Typ_Akcji += "\nKradziez przez Dresa";
                            Ilosc_Kradziezy++;
                            Ilosc_Kradziezy_D++;
                            Kradziez = Losowy.nextInt(dresy[i].max_pieniedzy - dresy[i].min_pieniedzy + 1) + dresy[i].min_pieniedzy;//losowanie z przedzialu liczby ile kradnie dres
                            Ilosc_Zabranych += Kradziez;
                            Marek.ilosc_pieniedzy -= Kradziez;//kradziez dresa


                            Typ_Akcji += "\nDres ukradł -> " + Kradziez + "     ruch   " + Ilosc_Ruchow;
                            Typ_Akcji += "\nGlownemu bohaterowi zostalo -> " + Marek.ilosc_pieniedzy;


                            Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                            for (Menel menel : menele)
                                if (menel != null)
                                    Dodawanie(menel.GimmeType(), menel.Gimmex(), menel.Gimmey());
                            for (Dres dres : dresy)
                                if (dres != null)
                                    Dodawanie(dres.GimmeType(), dres.Gimmex(), dres.Gimmey());
                            for (Pieniadze value : pieniadze)
                                if (value != null)
                                    Dodawanie(value.GimmeType(), value.Gimmex(), value.Gimmey());
                            for (org.example.Policjant policjant : Policjant)
                                Dodawanie(policjant.GimmeType(), policjant.Gimmex(), policjant.Gimmey());
                            Dodawanie(Klucz.GimmeType(), Klucz.Gimmex(), Klucz.Gimmey());
                                   System.out.println("Przed kradzieza dresa");
                                                   Wyswietlanie_Tablicy(Rozmiar);
                                     System.out.println();
                            Reset(Rozmiar);

                            if (Marek.ilosc_pieniedzy <= 0)//sprawdzenie czy dres okradl na tyle ze jest koniec gry
                            {
                                Ktory = 0;
                                break;
                            }
                            dresy[i].przerwa_od_kradzenia = 7;//ustawienie przerwy dla dresa zeby nie chodzil za MC i kradl go caly czas
                            dresy[i].Poruszanie_sie(Rozmiar);
                        }
                    }
                    else
                    {
                        dresy[i].Poruszanie_sie(Rozmiar);
                        dresy[i].przerwa_od_kradzenia--;
                    }
                }
            }



            //ruch meneli
            for(int j = 0; j < menele.length;j++)
            {
                if(menele[j] != null)
                {
                    if(menele[j].Czy_moze_okrasc(Marek.pozycja_x, Marek.pozycja_y) == 1)
                    {
                        Akcja++;
                        Ilosc_Kradziezy++;
                        Ilosc_Kradziezy_M++;
                        Kradziez = Losowy.nextInt(menele[j].max_pieniedzy - menele[j].min_pieniedzy + 1) + menele[j].min_pieniedzy;//losowanie z przedzialu liczby ile kradnie menel
                        Ilosc_Zabranych += Kradziez;
                        Marek.ilosc_pieniedzy -= Kradziez;//kradziez menela
                        Typ_Akcji += ("\nMenel ukradł -> " + Kradziez + "     ruch   " + Ilosc_Ruchow);
                        Typ_Akcji += ("\nGlownemu bohaterowi zostalo -> " + Marek.ilosc_pieniedzy);
                        Typ_Akcji += "\nKradziez menela";

                        //wyswietlanie przed kradzieza
                        Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                        for (Menel menel : menele)
                            if (menel != null)
                                Dodawanie(menel.GimmeType(), menel.Gimmex(), menel.Gimmey());
                        for (Dres dres : dresy)
                            if (dres != null)
                                Dodawanie(dres.GimmeType(), dres.Gimmex(), dres.Gimmey());
                        for (Pieniadze value : pieniadze)
                            if (value != null)
                                Dodawanie(value.GimmeType(), value.Gimmex(), value.Gimmey());
                        for (org.example.Policjant policjant : Policjant)
                            Dodawanie(policjant.GimmeType(), policjant.Gimmex(), policjant.Gimmey());
                        Dodawanie(Klucz.GimmeType(), Klucz.Gimmex(), Klucz.Gimmey());
                             System.out.println("Przed kradzieza przez menela");
                                          Wyswietlanie_Tablicy(Rozmiar);
                            System.out.println();
                        Reset(Rozmiar);
                        if (Marek.ilosc_pieniedzy <= 0)//sprawdzenie czy menel okradl na tyle ze jest koniec gry
                        {
                            Ktory = 0;
                            break;
                        }
                        menele[j].przerwa_od_kradzenia = 3;//ustawienie przerwy od kradzenia dla menela


                        //po mozliwej kradziezy sprawdzenie czy moze podniesc jakies pieniadze
                        for (int i = 0; i < pieniadze.length; i++)
                        {
                            if (pieniadze[i] != null && menele[j].Czy_podniesie_pieniadza(pieniadze[i].pozycja_x, pieniadze[i].pozycja_y) == 1)
                            {
                                Akcja++;
                                Typ_Akcji += "Podniesienie pieniadza przez menela";

                                //wyswietlanie przed podniesieniem pieniadza
                                Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                                for (Menel menel : menele)
                                    if (menel != null)
                                        Dodawanie(menel.GimmeType(), menel.Gimmex(), menel.Gimmey());
                                for (Dres dres : dresy)
                                    if (dres != null)
                                        Dodawanie(dres.GimmeType(), dres.Gimmex(), dres.Gimmey());
                                for (Pieniadze value : pieniadze)
                                    if (value != null)
                                        Dodawanie(value.GimmeType(), value.Gimmex(), value.Gimmey());
                                for (org.example.Policjant policjant : Policjant)
                                    Dodawanie(policjant.GimmeType(), policjant.Gimmex(), policjant.Gimmey());
                                Dodawanie(Klucz.GimmeType(), Klucz.Gimmex(), Klucz.Gimmey());
                                         System.out.println("Przed podniesieniem pieniadza przez menela");
                                                       Wyswietlanie_Tablicy(Rozmiar);
                                         System.out.println();
                                Reset(Rozmiar);

                                pieniadze[i] = null;
                            }
                        }
                        menele[j].Poruszanie_sie(Rozmiar);//ruszanie sie menela po sprawdzeniu czy cos moze zrobic
                    }
                    else
                    {
                        if(menele[j].przerwa_od_kradzenia != 0)
                            menele[j].przerwa_od_kradzenia--;
                        for (int i = 0; i < pieniadze.length;i++)
                        {
                            if(pieniadze[i] != null && menele[j].Czy_podniesie_pieniadza(pieniadze[i].pozycja_x,pieniadze[i].pozycja_y) == 1)
                            {
                                Akcja++;
                                Typ_Akcji += "\nPodniesienie pieniadza przez menela";

                                //wyswietlanie przed podniesieniem pieniadza
                                Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                                for (Menel menel : menele)
                                    if (menel != null)
                                        Dodawanie(menel.GimmeType(), menel.Gimmex(), menel.Gimmey());
                                for (Dres dres : dresy)
                                    if (dres != null)
                                        Dodawanie(dres.GimmeType(), dres.Gimmex(), dres.Gimmey());
                                for (Pieniadze value : pieniadze)
                                    if (value != null)
                                        Dodawanie(value.GimmeType(), value.Gimmex(), value.Gimmey());
                                for (org.example.Policjant policjant : Policjant)
                                    Dodawanie(policjant.GimmeType(), policjant.Gimmex(), policjant.Gimmey());
                                Dodawanie(Klucz.GimmeType(), Klucz.Gimmex(), Klucz.Gimmey());
                                     System.out.println("Przed podniesieniem pieniadza przez menela");
                                                         Wyswietlanie_Tablicy(Rozmiar);
                                     System.out.println();
                                Reset(Rozmiar);

                                pieniadze[i] = null;
                            }
                        }
                        //po wszystkich mozliwych sprawdzeniach poruszanie sie menela
                        menele[j].Poruszanie_sie(Rozmiar);
                    }
                }
            }

            //trzeba dorzucic warunek aresztowania na poczatku czy policjant moze aresztowac przed ruchem kogos


            //ruch policjanta
            int pomoc1, pomoc2;//wartosci potrzebne do wyswietlenia sytuacji przed ruchem policjanta
            for(int p = 0; p < Policjant.length;p++) {
                if (Ilosc_Ruchow == 1) {
                    //na samym poczatku sprawdzenie czy od razu moze kogos aresztowac
                    for (int i = 0; i < dresy.length; i++) {
                        if (dresy[i] != null && Policjant[p].Czy_moze_aresztowac(dresy[i].pozycja_x, dresy[i].pozycja_y) == 1) {
                            Akcja++;
                            Typ_Akcji += "\nAresztowanie dresa";
                            Ilosc_Aresztowan++;
                            Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                            for (Menel menel : menele)
                                if (menel != null)
                                    Dodawanie(menel.GimmeType(), menel.Gimmex(), menel.Gimmey());
                            for (Dres dres : dresy)
                                if (dres != null)
                                    Dodawanie(dres.GimmeType(), dres.Gimmex(), dres.Gimmey());
                            for (Pieniadze value : pieniadze)
                                if (value != null)
                                    Dodawanie(value.GimmeType(), value.Gimmex(), value.Gimmey());
                            for (org.example.Policjant policjant : Policjant)
                                Dodawanie(policjant.GimmeType(), policjant.Gimmex(), policjant.Gimmey());
                            Dodawanie(Klucz.GimmeType(), Klucz.Gimmex(), Klucz.Gimmey());
                               System.out.println("Przed aresztowaniem Dresa przed ruchem policjanta pierwszy ruch");
                                                  Wyswietlanie_Tablicy(Rozmiar);
                               System.out.println();
                            Reset(Rozmiar);

                            dresy[i] = null;
                        }
                    }
                    //to samo sprawdzenie dla meneli aresztowanie
                    for (int i = 0; i < menele.length; i++) {
                        if (menele[i] != null && Policjant[p].Czy_moze_aresztowac(menele[i].pozycja_x, menele[i].pozycja_y) == 1) {
                            Akcja++;
                            Typ_Akcji += "\nAresztowanie menela";
                            Ilosc_Aresztowan++;

                            Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                            for (Menel menel : menele)
                                if (menel != null)
                                    Dodawanie(menel.GimmeType(), menel.Gimmex(), menel.Gimmey());
                            for (Dres dres : dresy)
                                if (dres != null)
                                    Dodawanie(dres.GimmeType(), dres.Gimmex(), dres.Gimmey());
                            for (Pieniadze value : pieniadze)
                                if (value != null)
                                    Dodawanie(value.GimmeType(), value.Gimmex(), value.Gimmey());
                            for (org.example.Policjant policjant : Policjant)
                                Dodawanie(policjant.GimmeType(), policjant.Gimmex(), policjant.Gimmey());
                            Dodawanie(Klucz.GimmeType(), Klucz.Gimmex(), Klucz.Gimmey());
                               System.out.println("Przed aresztowaniem Menela przed ruchem policjanta pierwszy ruch");
                                                 Wyswietlanie_Tablicy(Rozmiar);
                                System.out.println();
                            Reset(Rozmiar);

                            menele[i] = null;
                        }
                    }
                } else {
                    //wyliczanie odleglosci do najblizszego dresa
                    int odlegloscdres = 2147483647, liczbadres = -1;
                    for (int i = 0; i < dresy.length; i++) {
                        if (dresy[i] != null && Policjant[p].Czy_widzi_dresa_lub_menela(dresy[i].pozycja_x, dresy[i].pozycja_y) == 1) {
                            tymcz_odlegl = (dresy[i].pozycja_x - Policjant[p].pozycja_x) * (dresy[i].pozycja_x - Policjant[p].pozycja_x) + (dresy[i].pozycja_y - Policjant[p].pozycja_y) * (dresy[i].pozycja_y - Policjant[p].pozycja_y);
                            if (tymcz_odlegl < odlegloscdres) {
                                odlegloscdres = tymcz_odlegl;
                                liczbadres = i;
                            }
                        }
                    }


                    //wyliczanie odleglosci do najblizszego menela
                    int odlegloscmenel = 2147483647, liczbamenel = -1;
                    for (int i = 0; i < menele.length; i++) {
                        if (menele[i] != null && Policjant[p].Czy_widzi_dresa_lub_menela(menele[i].pozycja_x, menele[i].pozycja_y) == 1) {
                            tymcz_odlegl = (menele[i].pozycja_x - Policjant[p].pozycja_x) * (menele[i].pozycja_x - Policjant[p].pozycja_x) + (menele[i].pozycja_y - Policjant[p].pozycja_y) * (menele[i].pozycja_y - Policjant[p].pozycja_y);
                            if (tymcz_odlegl < odlegloscmenel) {
                                odlegloscmenel = tymcz_odlegl;
                                liczbamenel = i;
                            }
                        }
                    }

                    //uzaleznienie ruchu policjanta od odleglosci od menela/dresa jednak gdy taka sama odleglosc to idzie do dresa
                    if (liczbamenel == -1 && liczbadres == -1)
                        Policjant[p].Poruszanie_sie(Rozmiar);
                    else if (odlegloscmenel < odlegloscdres) {
                        Akcja++;
                        Typ_Akcji += "\nRuch Policjanta w strone Menela";
                        pomoc1 = Policjant[p].pozycja_x;
                        pomoc2 = Policjant[p].pozycja_y;
                        Policjant[p].w_strone(menele[liczbamenel].pozycja_x, menele[liczbamenel].pozycja_y);
                        if (Policjant[p].Czy_moze_aresztowac(menele[liczbamenel].pozycja_x, menele[liczbamenel].pozycja_y) == 1) {
                            Akcja++;
                            Typ_Akcji += "\nAresztowanie Menala";
                            Ilosc_Aresztowan++;
                            Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                            for (Menel menel : menele)
                                if (menel != null)
                                    Dodawanie(menel.GimmeType(), menel.Gimmex(), menel.Gimmey());
                            for (Dres dres : dresy)
                                if (dres != null)
                                    Dodawanie(dres.GimmeType(), dres.Gimmex(), dres.Gimmey());
                            for (Pieniadze value : pieniadze)
                                if (value != null)
                                    Dodawanie(value.GimmeType(), value.Gimmex(), value.Gimmey());
                            for(int j = 0; j < Policjant.length;j++)
                                if(j != p)
                                    Dodawanie(Policjant[j].GimmeType(), Policjant[j].Gimmex(), Policjant[j].Gimmey());
                            Dodawanie(Policjant[p].GimmeType(), pomoc1, pomoc2);
                            Dodawanie(Klucz.GimmeType(), Klucz.Gimmex(), Klucz.Gimmey());
                               System.out.println("Przed aresztowaniem Menela przed ruchem policjanta");
                               Wyswietlanie_Tablicy(Rozmiar);
                               System.out.println();
                            Reset(Rozmiar);

                            Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                            for (Menel menel : menele)
                                if (menel != null)
                                    Dodawanie(menel.GimmeType(), menel.Gimmex(), menel.Gimmey());
                            for (Dres dres : dresy)
                                if (dres != null)
                                    Dodawanie(dres.GimmeType(), dres.Gimmex(), dres.Gimmey());
                            for (Pieniadze value : pieniadze)
                                if (value != null)
                                    Dodawanie(value.GimmeType(), value.Gimmex(), value.Gimmey());
                            for (org.example.Policjant policjant : Policjant)
                                Dodawanie(policjant.GimmeType(), policjant.Gimmex(), policjant.Gimmey());
                            Dodawanie(Klucz.GimmeType(), Klucz.Gimmex(), Klucz.Gimmey());
                                 System.out.println("Przed aresztowaniem Menela po ruchu policjanta");
                                  Wyswietlanie_Tablicy(Rozmiar);
                                 System.out.println();
                            Reset(Rozmiar);

                            menele[liczbamenel] = null;

                            int il_meneli = 0, il_dresow = 0;
                            for (Menel menel : menele)
                                if (menel != null)
                                    il_meneli++;
                            for (Dres dres : dresy)
                                if (dres != null)
                                    il_dresow++;

                            if(il_dresow == 0 && il_meneli == 0)
                            {
                                Ktory = 2;
                                break;
                            }
                        }
                    } else {
                        Akcja++;
                        Typ_Akcji += "\nRuch Policjanta w strone Dresa";
                        pomoc1 = Policjant[p].pozycja_x;
                        pomoc2 = Policjant[p].pozycja_y;
                        Policjant[p].w_strone(dresy[liczbadres].pozycja_x, dresy[liczbadres].pozycja_y);
                        if (Policjant[p].Czy_moze_aresztowac(dresy[liczbadres].pozycja_x, dresy[liczbadres].pozycja_y) == 1) {
                            Akcja++;
                            Typ_Akcji += "\nAresztowanie dresa";
                            Ilosc_Aresztowan++;

                            Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                            for (Menel menel : menele)
                                if (menel != null)
                                    Dodawanie(menel.GimmeType(), menel.Gimmex(), menel.Gimmey());
                            for (Dres dres : dresy)
                                if (dres != null)
                                    Dodawanie(dres.GimmeType(), dres.Gimmex(), dres.Gimmey());
                            for (Pieniadze value : pieniadze)
                                if (value != null)
                                    Dodawanie(value.GimmeType(), value.Gimmex(), value.Gimmey());
                            for(int j = 0; j < Policjant.length;j++)
                                if(j != p)
                                    Dodawanie(Policjant[j].GimmeType(), Policjant[j].Gimmex(), Policjant[j].Gimmey());
                            Dodawanie(Policjant[p].GimmeType(), pomoc1, pomoc2);
                            Dodawanie(Klucz.GimmeType(), Klucz.Gimmex(), Klucz.Gimmey());
                               System.out.println("Przed aresztowaniem Dresa przed ruchem policjanta");
                                                      Wyswietlanie_Tablicy(Rozmiar);
                               System.out.println();
                            Reset(Rozmiar);


                            Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
                            for (Menel menel : menele)
                                if (menel != null)
                                    Dodawanie(menel.GimmeType(), menel.Gimmex(), menel.Gimmey());
                            for (Dres dres : dresy)
                                if (dres != null)
                                    Dodawanie(dres.GimmeType(), dres.Gimmex(), dres.Gimmey());
                            for (Pieniadze value : pieniadze)
                                if (value != null)
                                    Dodawanie(value.GimmeType(), value.Gimmex(), value.Gimmey());
                            for (org.example.Policjant policjant : Policjant)
                                Dodawanie(policjant.GimmeType(), policjant.Gimmex(), policjant.Gimmey());
                            Dodawanie(Klucz.GimmeType(), Klucz.Gimmex(), Klucz.Gimmey());
                               System.out.println("Przed aresztowaniem Dresa po ruchu policjanta");
                               Wyswietlanie_Tablicy(Rozmiar);
                               System.out.println();
                            Reset(Rozmiar);

                            dresy[liczbadres] = null;

                            int il_meneli = 0, il_dresow = 0;
                            for (Menel menel : menele)
                                if (menel != null)
                                    il_meneli++;
                            for (Dres dres : dresy)
                                if (dres != null)
                                    il_dresow++;

                            if(il_dresow == 0 && il_meneli == 0)
                            {
                                Ktory = 2;
                                break;
                            }
                        }
                    }
                }
            }

            //dodawanie poszczegolnych elementow do tablicy wyswietlajacej
            Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
            for (Menel menel : menele)
                if (menel != null)
                    Dodawanie(menel.GimmeType(), menel.Gimmex(), menel.Gimmey());
            for (Dres dres : dresy)
                if (dres != null)
                    Dodawanie(dres.GimmeType(), dres.Gimmex(), dres.Gimmey());
            for (Pieniadze value : pieniadze)
                if (value != null)
                    Dodawanie(value.GimmeType(), value.Gimmex(), value.Gimmey());
            for (org.example.Policjant policjant : Policjant)
                Dodawanie(policjant.GimmeType(), policjant.Gimmex(), policjant.Gimmey());
            Dodawanie(Klucz.GimmeType(), Klucz.Gimmex(), Klucz.Gimmey());

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
            if(Ktory == 1 || Ktory == 2 || Ktory == 0)
                break;
        }


        Dodawanie(Marek.GimmeType(), Marek.Gimmex(), Marek.Gimmey());
        for (Menel menel : menele)
            if (menel != null)
                Dodawanie(menel.GimmeType(), menel.Gimmex(), menel.Gimmey());
        for (Dres dres : dresy)
            if (dres != null)
                Dodawanie(dres.GimmeType(), dres.Gimmex(), dres.Gimmey());
        for (Pieniadze value : pieniadze)
            if (value != null)
                Dodawanie(value.GimmeType(), value.Gimmex(), value.Gimmey());
        for (org.example.Policjant policjant : Policjant)
            Dodawanie(policjant.GimmeType(), policjant.Gimmex(), policjant.Gimmey());
        if(Klucz != null)
            Dodawanie(Klucz.GimmeType(), Klucz.Gimmex(), Klucz.Gimmey());

        if( Akcja > 0)
        {
            System.out.println("Tura numer -> " + Ilosc_Ruchow);
            System.out.println("Typ akcji : " + Typ_Akcji);
            Wyswietlanie_Tablicy(Rozmiar);
            System.out.println();
        }

        if (Ktory == 1) {
            System.out.println("zebranie klucza");
            //Zapis w postaci : 1. Typ zakonczenia gry, 2. Liczba ruchow, 3. Liczba posiadanych pieniedzy
            //4.  Liczba zabranych pieniedzy
            //5. Liczba kradziezy wszystkich
            //6. Liczba kradziezy dres 7. Liczba kradziezy menel 8. Liczba aresztowan
            zapis.write("1" + ";" +  Ilosc_Ruchow + ";" + Marek.ilosc_pieniedzy + ";" + Ilosc_Zabranych + ";" + Ilosc_Kradziezy + ";" +
                    Ilosc_Kradziezy_D + ";" + Ilosc_Kradziezy_M + ";" + Ilosc_Aresztowan + "\n");
        }
        else if(Ktory == 2) {
            System.out.println("aresztowanie wszystkich meneli i dresow");
            zapis.write("2" + ";" + Ilosc_Ruchow + ";" + Marek.ilosc_pieniedzy + ";" + Ilosc_Zabranych + ";" + Ilosc_Kradziezy + ";" +
                    Ilosc_Kradziezy_D + ";" + Ilosc_Kradziezy_M + ";" + Ilosc_Aresztowan + "\n");
        }
        else {
            System.out.println("okradzenie");
            zapis.write("3" + ";" + Ilosc_Ruchow + ";" + "0" + ";" + Ilosc_Zabranych + ";" + Ilosc_Kradziezy + ";" +
                    Ilosc_Kradziezy_D + ";" + Ilosc_Kradziezy_M + ";" + Ilosc_Aresztowan + "\n");
            //huh
        }
        System.out.println("koniecgry,    ilosc ruchow -> " + Ilosc_Ruchow);
//        System.out.println("Ilosc kradziezy" + Ilosc_Kradziezy);
//        System.out.println(Ilosc_Aresztowan);
        zapis.close();
    }

}