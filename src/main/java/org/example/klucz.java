package org.example;

public class klucz extends obiekt implements Pozycja{


    public klucz(int pozycjax, int pozycjay)
    {
        this.pozycjax = pozycjax;
        this.pozycjay = pozycjay;
    }

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
        return 6;
    }
}
