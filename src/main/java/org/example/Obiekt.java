package org.example;

import java.util.Random;

public abstract class Obiekt {
    //lokalizacja
    public int pozycja_x;
    public int pozycja_y;

    public void Poruszanie_sie(int rozmiar)
    {
        int poz_x_Test = 0;
        int poz_y_Test = 0;
        int n = 0;
        Random rand = new Random();
        do {
            poz_x_Test = pozycja_x;
            poz_y_Test = pozycja_y;
            int n1 = rand.nextInt(8) + 1;
            n = n1;
            switch (n)
            {
                case 1 ->
                {
                    poz_x_Test--;
                    poz_y_Test--;
                }
                case 2 -> poz_x_Test--;
                case 3 -> {poz_x_Test--; poz_y_Test++;}
                case 4 -> poz_y_Test++;
                case 5 -> {poz_x_Test++; poz_y_Test++;}
                case 6 -> poz_x_Test++;
                case 7 -> {poz_x_Test++; poz_y_Test--;}
                case 8 -> poz_y_Test--;
            }
        }
        while( poz_x_Test < 0 || poz_x_Test >= rozmiar || poz_y_Test < 0 || poz_y_Test >= rozmiar);

        switch (n)
        {
            case 1 ->
            {
                pozycja_x--;
                pozycja_y--;
            }
            case 2 -> pozycja_x--;
            case 3 -> {
                pozycja_x--; pozycja_y++;}
            case 4 -> pozycja_y++;
            case 5 -> {
                pozycja_x++; pozycja_y++;}
            case 6 -> pozycja_x++;
            case 7 -> {
                pozycja_x++; pozycja_y--;}
            case 8 -> pozycja_y--;
        }
    }
}
