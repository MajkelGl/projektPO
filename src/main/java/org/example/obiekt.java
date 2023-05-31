package org.example;

import java.util.Random;

public abstract class obiekt {
    //lokalizacja
    int pozycjax, pozycjay;

    public void PoruszanieSie(int rozmiar)
    {
        int pozxTest = 0;
        int pozyTest = 0;
        int n = 0;
        Random rand = new Random();
        do {
            pozxTest = pozycjax;
            pozyTest = pozycjay;
            int n1 = rand.nextInt(8) + 1;
            n = n1;
            switch (n)
            {
                case 1 ->
                {
                    pozxTest--;
                    pozyTest--;
                }
                case 2 -> pozxTest--;
                case 3 -> {pozxTest--; pozyTest++;}
                case 4 -> pozyTest++;
                case 5 -> {pozxTest++; pozyTest++;}
                case 6 -> pozxTest++;
                case 7 -> {pozxTest++; pozyTest--;}
                case 8 -> pozyTest--;
            }
        }
        while( pozxTest < 0 || pozxTest >= rozmiar || pozyTest < 0 || pozyTest >= rozmiar);

        switch (n)
        {
            case 1 ->
            {
                pozycjax--;
                pozycjay--;
            }
            case 2 -> pozycjax--;
            case 3 -> {pozycjax--; pozycjay++;}
            case 4 -> pozycjay++;
            case 5 -> {pozycjax++; pozycjay++;}
            case 6 -> pozycjax++;
            case 7 -> {pozycjax++; pozycjay--;}
            case 8 -> pozycjay--;
        }
    }
}
