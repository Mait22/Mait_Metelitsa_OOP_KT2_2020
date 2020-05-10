package oop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Collections; // Kasutan siit meetodit, et kaartide listi juhuslikku järjestusse seada ehk segada

// Laual saab olla kaarte mitme paki jagu
public class laualOlevadKaardid {

    private int kaardipakkideArv;
    private  int võtudKaardiJärg = 0; // pean meeles esimest laualt mitte läbi käinud kaarti

    // Kaardipakid ja kaardid listis - neid hakkan konstruktoris täitma
    private List<Kaardipakk> laualOlevadKaardipakid = new ArrayList<>();
    private List<Kaart> laualOlevadÜksikkaardid = new ArrayList<>();

    // Konstruktoris tõstan üksikute kaardipakkide kõik üksikkaardid ühte suurde listi kokku
    public laualOlevadKaardid(int kaardipakkideArv) {
        assert (kaardipakkideArv >= 1);
        this.kaardipakkideArv = kaardipakkideArv;
        for(int i = 1; i <= this.kaardipakkideArv; i++) {
            laualOlevadKaardipakid.add(new Kaardipakk(("Kaardipakk nr" + i) ));
        }

        for(Kaardipakk pakkKaarte : laualOlevadKaardipakid) {
            for(Kaart kaart : pakkKaarte.getKaardidPakis()) {
                laualOlevadÜksikkaardid.add(kaart);
            }
        }
        this.segaLaualOlevadKaardid();
    }

    // Segan üksikute kaaride koondlisti juhuslikku järjekorda
    public void segaLaualOlevadKaardid() {
        Collections.shuffle(this.laualOlevadÜksikkaardid);
    }

    // Meetod, mis ütleb kui palju laualt läbi käimata kaarte veel koondpakis järgi on
    public int kaarteJärg() {
        return laualOlevadÜksikkaardid.size() - võtudKaardiJärg;
    }

    // Võtab koondpakist kaardi - kui võtmata kaarte veel pakis on ja kannab selle maha; tagastab võetud kaardi kui
    // kaarti oli võimalik võtta
    public Kaart annaPakistMängimataKaart () {

        if(võtudKaardiJärg < laualOlevadÜksikkaardid.size()) {

            laualOlevadÜksikkaardid.get(võtudKaardiJärg).võtaKaartLaualtMaha();
            võtudKaardiJärg++;
            return laualOlevadÜksikkaardid.get(võtudKaardiJärg-1);
        }

        else {
            return null;
        }
    }
}
