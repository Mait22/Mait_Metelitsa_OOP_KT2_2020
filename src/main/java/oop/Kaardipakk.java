package oop;

import java.util.ArrayList;
import java.util.List;

public class Kaardipakk {

    // Iga kaardipaki invariantsed omadused ehk klassimuutujad
    static String[] mastidPilt = {"H", "S", "D", "C"};
    static String[] kaartideNimedPilt = {"A", "2", "3", "4", "5", "6", "7", "8", "9",
            "10", "J", "Q", "K"};

    static String[] mastid = {"Ärtu", "Poti", "Ruutu", "Risti"};
    static String[] kaartideNimed = {"äss", "kaks", "kolm", "neli", "viis", "kuus", "seitse", "kaheksa", "üheksa",
            "kümme", "sõdur", "emand", "kuningas"};

    static String[] mastdieSümbolid = {"\u2665", "\u2660", "\u2666", "\u2663"};
    static Integer[] väärtused =  {11,2,3,4,5,6,7,8,9,10,10,10,10};

    private String kaardipakiKood;
    private List<Kaart> kaardidPakis= new ArrayList<>();

    // Kaardipaki loomine, sh iga kaardipakki identifitseeriv kood
    public Kaardipakk(String kaardipakiKood)  {
        this.kaardipakiKood = kaardipakiKood;

        for(int i = 0; i < 4; i++)
        {
            for(int ii = 0; ii < 13; ii++)
            {
                kaardidPakis.add(
                        new Kaart(
                                mastid[i],
                        kaartideNimed[ii],
                        väärtused[ii],
                        (kaartideNimed[ii] == "äss" ? true : false),
                        this.kaardipakiKood,
                        false,
                        mastdieSümbolid[i],
                                (kaartideNimedPilt[ii]+mastidPilt[i])
                        )
                );
            }
        }

    }


    // Get meetod pakis olevate kaartide listi saamiseks
    public List<Kaart> getKaardidPakis() {
        return kaardidPakis;
    }

    // Ilusam print meetod kaardipakile
    @Override
    public String toString() {
        return "kaardidPakis = " + kaardidPakis;
    }
}