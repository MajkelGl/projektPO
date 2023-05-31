package org.example;
import static java.lang.Math.abs;

class MainCharacter extends obiekt implements Pozycja{
    //ilosc pieniedzy
    public int ilosc_pieniedzy;
    //na ile widzi
    int polewidzenia;
    //ile razy byl okradziony
    //private int kradziez = 0;


    public MainCharacter(int pozycjax, int pozycjay, int ilosc_pieniedzy, int polewidzenia)
    {
        this.pozycjax = pozycjax;
        this.pozycjay = pozycjay;
        this.ilosc_pieniedzy = ilosc_pieniedzy;
        this.polewidzenia = polewidzenia;
    }


    //funkcja ktora pokazuje czy widzi klucz MC i uzaleznia czy idzie w strone klucza czy nie
    public int czy_widzi_klucz(int x, int y) {
        if (abs(pozycjax - x) <= polewidzenia && abs(pozycjay - y) <= polewidzenia)
            return 1;
        else
            return 0;
    }

    public int czy_moze_podniesc_klucz(int x, int y){
        if(pozycjax == x && pozycjay == y)
            return 1;
        else
            return 0;
    }

    //funkcja dzieki ktorej MC idzie do pieniedzy
    public int czy_widzi_pieniadze(int x, int y)
    {
        if (abs(pozycjax - x) <= polewidzenia && abs(pozycjay - y) <= polewidzenia)
            return 1;
        else
            return 0;
    }

    public int czy_podniesie_pieniadza(int x, int y)
    {
        if (abs(pozycjax - x) <= 1 && abs(pozycjay - y) <= 1)
            return 1;
        else
            return 0;
    }

    //funkcja dzieki ktore postac sie bedzie poruszac losowo
//    public void poruszaniesie(int rozmiar) {
//        //System.out.println("ruch losowy");
//        Random rand = new Random();
//        int n;
//        if (pozycjax == 0) {
//            if (pozycjay == 0) {
//                n = rand.nextInt(3) + 1;
//                switch (n) {
//                    case 1 -> pozycjay++;
//                    case 2 -> {
//                        pozycjax++;
//                        pozycjay++;
//                    }
//                    case 3 -> pozycjax++;
//                }
//            } else if (pozycjay == rozmiar - 1) {
//                n = rand.nextInt(3) + 1;
//                switch (n) {
//                    case 1 -> pozycjax++;
//                    case 2 -> {
//                        pozycjax++;
//                        pozycjay--;
//                    }
//                    case 3 -> pozycjay--;
//                }
//            } else {
//                n = rand.nextInt(5) + 1;
//                switch (n) {
//                    case 1 -> pozycjay++;
//                    case 2 -> {
//                        pozycjax++;
//                        pozycjay++;
//                    }
//                    case 3 -> pozycjax++;
//                    case 4 -> {
//                        pozycjax++;
//                        pozycjay--;
//                    }
//                    case 5 -> pozycjay--;
//                }
//            }
//        } else if (pozycjax == rozmiar - 1) {
//            if (pozycjay == 0) {
//                n = rand.nextInt(3) + 1;
//                switch (n) {
//                    case 1 -> pozycjax--;
//                    case 2 -> {
//                        pozycjax--;
//                        pozycjay++;
//                    }
//                    case 3 -> pozycjay++;
//                }
//            } else if (pozycjay == rozmiar - 1) {
//                n = rand.nextInt(3) + 1;
//                switch (n) {
//                    case 1 -> pozycjay--;
//                    case 2 -> {
//                        pozycjax--;
//                        pozycjay--;
//                    }
//                    case 3 -> pozycjax--;
//                }
//            } else {
//                n = rand.nextInt(5) + 1;
//                switch (n) {
//                    case 1 -> pozycjay--;
//                    case 2 -> {
//                        pozycjax--;
//                        pozycjay--;
//                    }
//                    case 3 -> pozycjax++;
//                    case 4 -> {
//                        pozycjax--;
//                        pozycjay++;
//                    }
//                    case 5 -> pozycjay++;
//                }
//            }
//        } else if (pozycjay == 0) {
//            n = rand.nextInt(5) + 1;
//            switch (n) {
//                case 1 -> pozycjax--;
//                case 2 -> {
//                    pozycjax--;
//                    pozycjay++;
//                }
//                case 3 -> pozycjay++;
//                case 4 -> {
//                    pozycjax++;
//                    pozycjay++;
//                }
//                case 5 -> pozycjax++;
//            }
//        } else if (pozycjay == rozmiar - 1) {
//            n = rand.nextInt(5) + 1;
//            switch (n) {
//                case 1 -> pozycjax++;
//                case 2 -> {
//                    pozycjax++;
//                    pozycjay--;
//                }
//                case 3 -> pozycjay--;
//                case 4 -> {
//                    pozycjax--;
//                    pozycjay--;
//                }
//                case 5 -> pozycjax--;
//            }
//        }
//        else
//        {
//            n = rand.nextInt(8) + 1;
//            switch (n) {
//                case 1 -> {
//                    pozycjax--;
//                    pozycjay--;
//                }
//                case 2 -> pozycjax--;
//                case 3 -> {pozycjax--; pozycjay++;}
//                case 4 -> pozycjay++;
//                case 5 -> {pozycjax++; pozycjay++;}
//                case 6 -> pozycjax++;
//                case 7 -> {pozycjax++; pozycjay--;}
//                case 8 -> pozycjay--;
//            }
//        }
//    }

    //funkcja ktora pozwala MC kierowac sie w kierunku klucza
    public void wstrone(int x, int y)
    {
        //System.out.println("ruch wstrone");
        int X, Y;
        X = x - pozycjax;
        Y = y - pozycjay;
        if(X > 0)
            pozycjax++;
        else if(X < 0)
            pozycjax--;

        if(Y > 0)
            pozycjay++;
        else if(Y < 0)
            pozycjay--;
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
        return 1;
    }
}
