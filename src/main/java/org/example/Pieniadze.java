package org.example;

public class Pieniadze extends Obiekt implements Pozycja{
    public int wartosc;

    public Pieniadze(int pozycja_x, int pozycja_y, int wartosc)
    {
        this.pozycja_x = pozycja_x;
        this.pozycja_y = pozycja_y;
        this.wartosc = wartosc;
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
        return 5;
    }
}
