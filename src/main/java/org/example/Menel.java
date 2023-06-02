package org.example;

import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.min;

public class Menel extends zly implements Pozycja{


    public Menel( int pozycjax, int pozycjay, int max_pieniedzy, int min_pieniedzy)
    {
        this.pozycjax = pozycjax;
        this.pozycjay = pozycjay;
        this.max_pieniedzy = max_pieniedzy;
        this.min_pieniedzy = min_pieniedzy;
    }
    public int czy_moze_okrasc(int x, int y,int rozmiar)
    {
        if(przerwaodkradzenia != 0)
            return 0;
        else
        {
            if (abs(pozycjax - x) <= 1 && abs(pozycjay - y) <= 1)
                return 1;
            else
                return 0;
        }
    }

//    public void poruszaniesie(int rozmiar) {
//        Random rand = new Random();
//        int n;
//        if (pozycjax == 0) {
//            if (pozycjay == 0) {
//                n = rand.nextInt(3) + 1;
//                switch (n) {
//                    case 1 -> pozycjay++;
//                    case 2 -> {
//                        pozycjax++;
//                        pozycjay++;
//                    }
//                    case 3 -> pozycjax++;
//                }
//            } else if (pozycjay == rozmiar - 1) {
//                n = rand.nextInt(3) + 1;
//                switch (n) {
//                    case 1 -> pozycjax++;
//                    case 2 -> {
//                        pozycjax++;
//                        pozycjay--;
//                    }
//                    case 3 -> pozycjay--;
//                }
//            } else {
//                n = rand.nextInt(5) + 1;
//                switch (n) {
//                    case 1 -> pozycjay++;
//                    case 2 -> {
//                        pozycjax++;
//                        pozycjay++;
//                    }
//                    case 3 -> pozycjax++;
//                    case 4 -> {
//                        pozycjax++;
//                        pozycjay--;
//                    }
//                    case 5 -> pozycjay--;
//                }
//            }
//        } else if (pozycjax == rozmiar - 1) {
//            if (pozycjay == 0) {
//                n = rand.nextInt(3) + 1;
//                switch (n) {
//                    case 1 -> pozycjax--;
//                    case 2 -> {
//                        pozycjax--;
//                        pozycjay++;
//                    }
//                    case 3 -> pozycjay++;
//                }
//            } else if (pozycjay == rozmiar - 1) {
//                n = rand.nextInt(3) + 1;
//                switch (n) {
//                    case 1 -> pozycjay--;
//                    case 2 -> {
//                        pozycjax--;
//                        pozycjay--;
//                    }
//                    case 3 -> pozycjax--;
//                }
//            } else {
//                n = rand.nextInt(5) + 1;
//                switch (n) {
//                    case 1 -> pozycjay--;
//                    case 2 -> {
//                        pozycjax--;
//                        pozycjay--;
//                    }
//                    case 3 -> pozycjax++;
//                    case 4 -> {
//                        pozycjax--;
//                        pozycjay++;
//                    }
//                    case 5 -> pozycjay++;
//                }
//            }
//        } else if (pozycjay == 0) {
//            n = rand.nextInt(5) + 1;
//            switch (n) {
//                case 1 -> pozycjax--;
//                case 2 -> {
//                    pozycjax--;
//                    pozycjay++;
//                }
//                case 3 -> pozycjay++;
//                case 4 -> {
//                    pozycjax++;
//                    pozycjay++;
//                }
//                case 5 -> pozycjax++;
//            }
//        } else if (pozycjay == rozmiar - 1) {
//            n = rand.nextInt(5) + 1;
//            switch (n) {
//                case 1 -> pozycjax++;
//                case 2 -> {
//                    pozycjax++;
//                    pozycjay--;
//                }
//                case 3 -> pozycjay--;
//                case 4 -> {
//                    pozycjax--;
//                    pozycjay--;
//                }
//                case 5 -> pozycjax--;
//            }
//        }
//        else
//        {
//            n = rand.nextInt(8) + 1;
//            switch (n) {
//                case 1 -> {
//                    pozycjax--;
//                    pozycjay--;
//                }
//                case 2 -> pozycjax--;
//                case 3 -> {pozycjax--; pozycjay++;}
//                case 4 -> pozycjay++;
//                case 5 -> {pozycjax++; pozycjay++;}
//                case 6 -> pozycjax++;
//                case 7 -> {pozycjax++; pozycjay--;}
//                case 8 -> pozycjay--;
//            }
//        }
//    }
    //Metoda zwracajaca pozycje x
    public int Gimmex()
    {
        return pozycjax;
    }

    //Metoda zwracajaco pozycje y
    public int Gimmey()
    {
        return pozycjay;
    }

    //Metoda zwracajaca typ obiektu
    public int GimmeType()
    {
        return 3;
    }
}
