package org.example;

import static java.lang.Math.abs;

public class Dres extends Zly implements Pozycja{

    public Dres( int pozycja_x, int pozycja_y, int max_pieniedzy, int min_pieniedzy, int przerwa_od_kradzenia)
    {
        this.pozycja_x = pozycja_x;
        this.pozycja_y = pozycja_y;
        this.max_pieniedzy = max_pieniedzy;
        this.min_pieniedzy = min_pieniedzy;
        this.przerwa_od_kradzenia = przerwa_od_kradzenia;
    }
    private int pole_widzenia;

    {
        pole_widzenia = 2;
    }

    public int Czy_moze_okrasc(int x, int y)
    {
        if(przerwa_od_kradzenia != 0)
        {
            return 0;
        }
        else
        {
            if (abs(pozycja_x - x) <= 1 && abs(pozycja_y - y) <= 1) {
                return 1;
            } else
                return 0;
        }
    }

    public int Czy_widzi_cos(int x, int y)
    {
        if(przerwa_od_kradzenia != 0)
            return 0;
        else
        {
            if (abs(pozycja_x - x) <= pole_widzenia && abs(pozycja_y - y) <= pole_widzenia)
                return 1;
            else
                return 0;
        }
    }

    //funkcja ktora pozwala dresowi kierowac sie w kierunku glownego bohatera
    public void wstrone(int x, int y)
    {
        int X, Y;
        X = x - pozycja_x;
        Y = y - pozycja_y;
        if(X > 0)
            pozycja_x++;
        else if(X < 0)
            pozycja_x--;

        if(Y > 0)
            pozycja_y++;
        else if(Y < 0)
            pozycja_y--;
    }
    //Metoda zwracajaca pozycje x
    public int Gimmex()
    {
        return pozycja_x;
    }

    //Metoda zwracajaco pozycje y
    public int Gimmey()
    {
        return pozycja_y;
    }

    //Metoda zwracajaca typ obiektu
    public int GimmeType()
    {
        return 2;
    }
}
