package org.example;
import static java.lang.Math.abs;

class Glowny_Bohater extends Obiekt implements Pozycja{
    //ilosc pieniedzy
    public int ilosc_pieniedzy;
    //na ile widzi
    int pole_widzenia;
    //ile razy byl okradziony
    //private int kradziez = 0;


    public Glowny_Bohater(int pozycja_x, int pozycja_y, int ilosc_pieniedzy, int pole_widzenia)
    {
        this.pozycja_x = pozycja_x;
        this.pozycja_y = pozycja_y;
        this.ilosc_pieniedzy = ilosc_pieniedzy;
        this.pole_widzenia = pole_widzenia;
    }


    //funkcja ktora pokazuje czy glowny bohater widzi klucz
    public int czy_widzi_klucz(int x, int y) {
        if (abs(pozycja_x - x) <= pole_widzenia && abs(pozycja_y - y) <= pole_widzenia)
            return 1;
        else
            return 0;
    }

    //funkcja sprawdzajaca czy glowny bohater moze podniesc klucz
    public int czy_moze_podniesc_klucz(int x, int y){
        if(pozycja_x == x && pozycja_y == y)
            return 1;
        else
            return 0;
    }

    //funkcja ktora okresla czy glowny bohater widzi pieniadze
    public int czy_widzi_pieniadze(int x, int y)
    {
        if (abs(pozycja_x - x) <= pole_widzenia && abs(pozycja_y - y) <= pole_widzenia)
            return 1;
        else
            return 0;
    }

    //funkcja okreslajaca czy glowny bohater moze podniesc pieniadze
    public int czy_podniesie_pieniadza(int x, int y)
    {
        if (abs(pozycja_x - x) <= 1 && abs(pozycja_y - y) <= 1)
            return 1;
        else
            return 0;
    }


    //funkcja ktora pozwala glownemu bohaterowi kierowac sie w kierunku klucza/pieniedzy
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
        return 1;
    }
}
