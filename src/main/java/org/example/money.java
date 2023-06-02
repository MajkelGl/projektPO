package org.example;

public class money extends obiekt implements Pozycja{
    public int wartosc;

    public money(int pozycjax, int pozycjay, int wartosc)
    {
        this.pozycjax = pozycjax;
        this.pozycjay = pozycjay;
        this.wartosc = wartosc;
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
        return 5;
    }
}
