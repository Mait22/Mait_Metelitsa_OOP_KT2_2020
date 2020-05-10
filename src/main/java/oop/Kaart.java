package oop;

public class Kaart {

    // Kaardi omadused
    private String kaardiMast;
    private String kaardiNimetus;
    private int väärtusMängus;
    private boolean alternatiivseltVäärtusÜks;
    private String kaardipakiKood;
    private boolean laualtLäbiKäinudKaart;
    private String unicodeSümbol;
    private String pildiNimetus;

    // Kaardi konstruktor
    public Kaart(String kaardiMast,
                 String kaardiNimetus,
                 int väärtusMängus,
                 boolean alternatiivseltVäärtusÜks,
                 String kaardipakiKood,
                 boolean laualtLäbiKäinudKaart,
                 String unicodeSümbol,
                 String pildiNimetus) {

        this.alternatiivseltVäärtusÜks = alternatiivseltVäärtusÜks;
        this.kaardiMast = kaardiMast;
        this.kaardiNimetus = kaardiNimetus;
        this.kaardipakiKood = kaardipakiKood;
        this.väärtusMängus = väärtusMängus;
        this.laualtLäbiKäinudKaart = laualtLäbiKäinudKaart;
        this.unicodeSümbol = unicodeSümbol;
        this.pildiNimetus  = pildiNimetus;
    }

    // Kaardi sümboli küsimine
    public String getUnicodeSümbol() {
        return unicodeSümbol;
    }

    // Kaardi väärtus BlackJack mängus
    public int getVäärtusMängus() {
        return väärtusMängus;
    }

    // Ässal on BlackJackis kaks väärtust 11 ja 1 - juhul kui kaart on äss, siis see väärtus on tõsi
    public boolean isAlternatiivseltVäärtusÜks() {
        return alternatiivseltVäärtusÜks;
    }

    // Kaardi masti küsimine
    public String getKaardiMast() {
        return kaardiMast;
    }

    // PildiNimetuseKüsimine
    public String getPildiNimetus() {
        return pildiNimetus;
    }

    // Kui konkreetse paki konkreetne kaart on korra mängulaual käinud ei tohi ta samas mängus enam uuesti lauda tulla
    public boolean isLaualtLäbiKäinudKaart() {
        return laualtLäbiKäinudKaart;
    }

    // Kaardi nimetuse küsimine
    public String getKaardiNimetus() {
        return kaardiNimetus;
    }

    // Kaardi lähetava paki koodi küsimine
    public String getKaardipakiKood() {
        return kaardipakiKood;
    }

    // Kaardi laualt läbikäimise äramärkimine
    public void võtaKaartLaualtMaha() {
        this.laualtLäbiKäinudKaart = true;
    }

    // Ilusam printmeetod
    @Override
    public String toString() {
        return "Kaart{" +
                "kaardiMast='" + kaardiMast + '\'' +
                ", kaardiNimetus='" + kaardiNimetus + '\'' +
                '}';
    }
}