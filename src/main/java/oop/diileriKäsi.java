package oop;

// Klass laiendab tavaliseKäe klassi
public class diileriKäsi extends tavalineKäsi {

    // Käega seotud tegevustes akkumuleeruvad sõnumdi (teated tegevustest enne käe maha panekut)
    private String varasemadSõnumidKäeValdajale = "";

    // Konstruktor
    public diileriKäsi(laualOlevadKaardid kaardidMängus, double panus) {
        super(kaardidMängus, panus);
    }

    // Black Jackis on diileri kaardid kätte jagatavad esimesena. Üks kaart on näha, teine maha pööratud.
    public String näitaAlgulKätt() {
        String näita = "";
        näita = näita + "Diileril on esialgu käes kaardid: ÜKS PEIDETUD KAART ja ";
        näita = näita + "["+ this.getKäesOlevadKaardid().get(1).getUnicodeSümbol() + " - " + this.getKäesOlevadKaardid().get(1).getKaardiMast() + " " + this.getKäesOlevadKaardid().get(1).getKaardiNimetus() + "] ";
        return näita;
    }

    // Diileri käe avamine peale seda kui tavamängija on oma sammud ära teinud, st selle sammuga seotud sõnumid
    @Override
    public String näitaKätt() {
        String näita = "";
        näita = näita + "Diileril on hetkel käes kaardid: ";
        for(Kaart k: this.getKäesOlevadKaardid()) {
            näita = näita + "["+ k.getUnicodeSümbol() + " - " + k.getKaardiMast() + " " + k.getKaardiNimetus() + "] ";
        }

        näita = näita + "Diileri käe väärtus on: " + this.arvutaKäeVäärtus()  + ". ";

        return näita;
    }

    // Kaardi juurde küsimine diileri tekki ja seotud sõnumid, katab üle sama nimega meetodi peaklassist
    @Override
    public boolean küsiÜksKaartJuurde(laualOlevadKaardid kaardidMängus) {
        if(this.iskäsiOnAktiivne())
        {
            this.getKäesOlevadKaardid().add(kaardidMängus.annaPakistMängimataKaart());
            if(this.arvutaKäeVäärtus() > 21) {
                this.setKäsiOnAktiivne(false);
                this.setÜleLimiidi(true);
                this.setVõit(this.getPanus());
                this.varasemadSõnumidKäeValdajale = "Lisanduv kaart ajas käe diileri jaoks lõhki. Diiler kaotas " + this.getPanus() + " eurot!";
                return true;
            }
        }
        return false;
    }

}
