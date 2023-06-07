package org.example;

import static java.lang.Math.abs;

public class Policjant extends Obiekt implements Pozycja{
    public int pole_widzenia;

    public Policjant(int pozycja_x, int pozycja_y, int pole_widzenia)
    {
        this.pozycja_x = pozycja_x;
        this.pozycja_y = pozycja_y;
        this.pole_widzenia = pole_widzenia;
    }

    public int Czy_moze_aresztowac(int x, int y)
    {
        if (abs(pozycja_x - x) <= 1 && abs(pozycja_y - y) <= 1)
            return 1;
        else
            return 0;
    }

    public int Czy_widzi_dresa_lub_menela(int x, int y) {
        if (abs(pozycja_x - x) <= pole_widzenia && abs(pozycja_y - y) <= pole_widzenia)
            return 1;
        else
            return 0;
    }

    //funkcja ktora pozwala policjantowi kierowac sie w kierunku dresa/menela
    public void w_strone(int x, int y)
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
        return 4;
    }
}
