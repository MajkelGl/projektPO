package org.example;
import java.util.Random;


public class Main {

    // Tablica stringow potrzebna do wizualizacji
    static String[][] Pozycje = new String[50][50];


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

//    public static int atoi(String wejscie)
//    {
//            int liczba = 0;
//            for (int i = 0; i < wejscie.length(); ++i)
//                liczba = liczba * 10 + wejscie.charAt(i) - '0';
//        return liczba;
//    }

    public static void main(String[] args) {
        Random Losowy = new Random();
        //inicjalizacja rozmiaru planszy
        System.out.println("rozmiar mapy -> 50\n\n");
        //Scanner scan = new Scanner(System.in);
        int Rozmiar = 50;//scan.nextInt();

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

        Menel[] menele = new Menel[5];
        for(int i = 0; i < menele.length;i++)
            menele[i] = new Menel(Losowy.nextInt(50),Losowy.nextInt(50),5,1);


        //implementacja dresa
        Dres Seba = new Dres(Losowy.nextInt(Rozmiar),Losowy.nextInt(Rozmiar),10,5,0);

        Dres[] dresy = new Dres[5];
        for(int i = 0; i < dresy.length;i++)
            dresy[i] = new Dres(Losowy.nextInt(Rozmiar),Losowy.nextInt(Rozmiar),10,5,0);

        //implementacja klucza
        Klucz Key = new Klucz(Losowy.nextInt(Rozmiar), Losowy.nextInt(Rozmiar));

        //implementacja policjanta
        Policjant Policjant = new Policjant(Losowy.nextInt(Rozmiar), Losowy.nextInt(Rozmiar), 2);

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
        Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
        Dodawanie(Key.GimmeType(), Key.Gimmex(), Key.Gimmey());
        Wyswietlanie_Tablicy(Rozmiar);
        System.out.println();
        Reset(Rozmiar);
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
                        Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
                        Dodawanie(Key.GimmeType(), Key.Gimmex(), Key.Gimmey());
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
                        Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
                        Dodawanie(Key.GimmeType(), Key.Gimmex(), Key.Gimmey());
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
                        Kradziez = Losowy.nextInt(dresy[i].max_pieniedzy - dresy[i].min_pieniedzy + 1) + dresy[i].min_pieniedzy;//losowanie z przedzialu liczby ile kradnie dres
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
                        Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
                        Dodawanie(Key.GimmeType(), Key.Gimmex(), Key.Gimmey());
                        System.out.println("Przed kradzieza dresa");
                        Wyswietlanie_Tablicy(Rozmiar);
                        System.out.println();
                        Reset(Rozmiar);

                        if (Marek.ilosc_pieniedzy <= 0)//sprawdzenie czy dres okradl na tyle ze jest koniec gry
                            break;
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
                            Kradziez = Losowy.nextInt(dresy[i].max_pieniedzy - dresy[i].min_pieniedzy + 1) + dresy[i].min_pieniedzy;//losowanie z przedzialu liczby ile kradnie dres
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
                            Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
                            Dodawanie(Key.GimmeType(), Key.Gimmex(), Key.Gimmey());
                            System.out.println("Przed kradzieza dresa");
                            Wyswietlanie_Tablicy(Rozmiar);
                            System.out.println();
                            Reset(Rozmiar);

                            if (Marek.ilosc_pieniedzy <= 0)//sprawdzenie czy dres okradl na tyle ze jest koniec gry
                                break;
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
                    for (Menel menel : menele)
                        if (menel != null)
                            Dodawanie(menel.GimmeType(), menel.Gimmex(), menel.Gimmey());
                    for (Dres dres : dresy)
                        if (dres != null)
                            Dodawanie(dres.GimmeType(), dres.Gimmex(), dres.Gimmey());
                    for (Pieniadze value : pieniadze)
                        if (value != null)
                            Dodawanie(value.GimmeType(), value.Gimmex(), value.Gimmey());
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
                else if(Seba.Czy_widzi_cos(Marek.pozycja_x, Marek.pozycja_y) == 1)
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
                        for (Menel menel : menele)
                            if (menel != null)
                                Dodawanie(menel.GimmeType(), menel.Gimmex(), menel.Gimmey());
                        for (Dres dres : dresy)
                            if (dres != null)
                                Dodawanie(dres.GimmeType(), dres.Gimmex(), dres.Gimmey());
                        for (Pieniadze value : pieniadze)
                            if (value != null)
                                Dodawanie(value.GimmeType(), value.Gimmex(), value.Gimmey());

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


            for(int j = 0; j < menele.length;j++)
            {
                if(menele[j] != null)
                {
                    if(menele[j].Czy_moze_okrasc(Marek.pozycja_x, Marek.pozycja_y) == 1)
                    {
                        Akcja++;
                        Kradziez = Losowy.nextInt(menele[j].max_pieniedzy - menele[j].min_pieniedzy + 1) + menele[j].min_pieniedzy;//losowanie z przedzialu liczby ile kradnie menel
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
                        Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
                        Dodawanie(Key.GimmeType(), Key.Gimmex(), Key.Gimmey());
                        System.out.println("Przed kradzieza przez menela");
                        Wyswietlanie_Tablicy(Rozmiar);
                        System.out.println();
                        Reset(Rozmiar);
                        if (Marek.ilosc_pieniedzy <= 0)//sprawdzenie czy menel okradl na tyle ze jest koniec gry
                            break;
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
                                Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
                                Dodawanie(Key.GimmeType(), Key.Gimmex(), Key.Gimmey());
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
                                Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
                                Dodawanie(Key.GimmeType(), Key.Gimmex(), Key.Gimmey());
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
                            for (Menel menel : menele)
                                if (menel != null)
                                    Dodawanie(menel.GimmeType(), menel.Gimmex(), menel.Gimmey());
                            for (Dres dres : dresy)
                                if (dres != null)
                                    Dodawanie(dres.GimmeType(), dres.Gimmex(), dres.Gimmey());
                            for (Pieniadze value : pieniadze)
                                if (value != null)
                                    Dodawanie(value.GimmeType(), value.Gimmex(), value.Gimmey());
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
                            for (Menel menel : menele)
                                if (menel != null)
                                    Dodawanie(menel.GimmeType(), menel.Gimmex(), menel.Gimmey());
                            for (Dres dres : dresy)
                                if (dres != null)
                                    Dodawanie(dres.GimmeType(), dres.Gimmex(), dres.Gimmey());
                            for (Pieniadze value : pieniadze)
                                if (value != null)
                                    Dodawanie(value.GimmeType(), value.Gimmex(), value.Gimmey());
                            Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
                            Dodawanie(Key.GimmeType(), Key.Gimmex(), Key.Gimmey());
                            System.out.println("Przed aresztowaniem Dresa po ruchu policjanta");
                            Wyswietlanie_Tablicy(Rozmiar);
                            System.out.println();
                            Reset(Rozmiar);


                            Seba = null;
                            int czy_koniec = 1;
                            for(int j = 0; j < menele.length;j++)
                                if(menele[j] != null)
                                {
                                    czy_koniec = 0;
                                    break;
                                }
                            if(czy_koniec == 1 && Seba == null) {
                                Ktory = 2;
                                break;
                            }
                        }
                }
            else
            {
                odleglosc = 2147483647; //parametry potrzebne do okreslenia ktory menel jest najblizej
                liczba = -1;
                for(int i = 0; i < menele.length;i++)
                {
                    if(menele[i] != null && Policjant.Czy_widzi_dresa_lub_menela(menele[i].pozycja_x, menele[i].pozycja_y) == 1)
                    {
                        tymcz_odlegl = (menele[i].pozycja_x - Policjant.pozycja_x)*(menele[i].pozycja_x - Policjant.pozycja_x) + (menele[i].pozycja_y - Policjant.pozycja_y)*(menele[i].pozycja_y - Policjant.pozycja_y);
                        if(tymcz_odlegl < odleglosc)
                        {
                            odleglosc = tymcz_odlegl;
                            liczba = i;
                        }
                    }
                }
                if(liczba != -1)
                {
                    Policjant.w_strone(menele[liczba].pozycja_x, menele[liczba].pozycja_y);
                    if (Policjant.Czy_moze_aresztowac(menele[liczba].pozycja_x, menele[liczba].pozycja_y) == 1)
                    {
                        Akcja++;
                        Typ_Akcji += "\nAresztowanie menela ";

                        //wyswietlanie po ruchu policjanta
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
                        Dodawanie(Policjant.GimmeType(), Policjant.Gimmex(), Policjant.Gimmey());
                        Dodawanie(Key.GimmeType(), Key.Gimmex(), Key.Gimmey());
                        System.out.println("Przed aresztowaniem Menela po ruchu policjanta");
                        Wyswietlanie_Tablicy(Rozmiar);
                        Reset(Rozmiar);
                        System.out.println();

                        menele[liczba] = null;
                        int czy_koniec = 1;
                        for(int j = 0; j < menele.length;j++)
                            if(menele[j] != null)
                            {
                                czy_koniec = 0;
                                break;
                            }
                        if(czy_koniec == 1 && Seba == null) {
                            Ktory = 2;
                            break;
                        }
                    }
                }
                else
                    Policjant.Poruszanie_sie(Rozmiar);
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
        for (Menel menel : menele)
            if (menel != null)
                Dodawanie(menel.GimmeType(), menel.Gimmex(), menel.Gimmey());
        for (Dres dres : dresy)
            if (dres != null)
                Dodawanie(dres.GimmeType(), dres.Gimmex(), dres.Gimmey());
        for (Pieniadze value : pieniadze)
            if (value != null)
                Dodawanie(value.GimmeType(), value.Gimmex(), value.Gimmey());
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