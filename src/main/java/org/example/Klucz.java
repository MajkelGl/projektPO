package org.example;

public class Klucz extends Obiekt implements Pozycja{


    //do implementacji klucza
    public Klucz(int pozycja_x, int pozycja_y)
    {
        this.pozycja_x = pozycja_x;
        this.pozycja_y = pozycja_y;
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
        return 6;
    }
}
