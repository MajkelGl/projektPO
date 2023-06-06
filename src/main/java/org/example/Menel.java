package org.example;

import static java.lang.Math.abs;
import static java.lang.Math.min;

public class Menel extends Zly implements Pozycja{


    public Menel( int pozycja_x, int pozycja_y, int max_pieniedzy, int min_pieniedzy)
    {
        this.pozycja_x = pozycja_x;
        this.pozycja_y = pozycja_y;
        this.max_pieniedzy = max_pieniedzy;
        this.min_pieniedzy = min_pieniedzy;
    }

    public int czy_podniesie_pieniadza(int x, int y)
    {
        if (abs(pozycja_x - x) <= 1 && abs(pozycja_y - y) <= 1)
            return 1;
        else
            return 0;
    }

    public int czy_moze_okrasc(int x, int y)
    {
        if(przerwa_od_kradzenia != 0)
            return 0;
        else
        {
            if (abs(pozycja_x - x) <= 1 && abs(pozycja_y - y) <= 1)
                return 1;
            else
                return 0;
        }
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
        return 3;
    }
}
