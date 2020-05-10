package oop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class tavalineKäsi implements Comparable<tavalineKäsi> {

    // Et käed oleksid skoori alusel võrreldavad
    public int compareTo(tavalineKäsi võrreldav) {
        if (võrreldav.arvutaKäeVäärtus() > this.arvutaKäeVäärtus() && !this.isÜleLimiidi() && !võrreldav.isÜleLimiidi() && !võrreldav.iskäsiOnAktiivne() && !this.iskäsiOnAktiivne()) {
            return 1;
        }
        else if (this.isÜleLimiidi() && !võrreldav.isÜleLimiidi() && !võrreldav.iskäsiOnAktiivne() && !this.iskäsiOnAktiivne()) {
            return 1;
        }
        else if (võrreldav.arvutaKäeVäärtus() < this.arvutaKäeVäärtus() && !this.isÜleLimiidi() && !võrreldav.isÜleLimiidi() && !võrreldav.iskäsiOnAktiivne() && !this.iskäsiOnAktiivne()) {
            return -1;
        }
        else if (!this.isÜleLimiidi() && võrreldav.isÜleLimiidi() && !võrreldav.iskäsiOnAktiivne() && !this.iskäsiOnAktiivne()) {
            return -1;
        }
        else {
            return 0;
        }
    }

    // käes olevad kaardid
    private List<Kaart> käesOlevadKaardid= new ArrayList<>();

    // Kas käes olevate kaartide väärtus on üle 21 ehk käsi lõhki
    private boolean üleLimiidi;

    // Kahe esimese kaardiga saadud 21 on Black Jack ja 1.5x võit
    private boolean blackJack;

    // Kui käes on "ruumi" uusi kaarte võtta ja kui mängija ei ole indikeerinud, et ta tahab käe maha panna tuleb kätt aktiivsena hoida
    // ehk võimaldada kätte pakist uusi kaarte küsida
    private boolean käsiOnAktiivne;

    // Käe alguses lauale tõstetud panus
    private double panus;

    // Käega võidetud või kaotatud summa
    private double võit;

    // Mängu jooksul tegevustes käe valdajale kogunevad staatusesõnumid - nt käe lõhkiminekust.
    private String varasemadSõnumidKäeValdajale;

    // Kuna kasutan seda klassi ka diileri käe peaklassina pean privaatsete väljade väärtustele ligipääsemiseks defineerima
    // rea nimeliselt mõistetavaid get meetodeid ja set meetodeid
    public double getPanus(){
        return this.panus;
    }

    public boolean getblackJack(){
        return this.blackJack;
    }

    public void setVõit(double võit){
        this.võit = võit;
    }

    public void setPanus(double panus){
        this.panus = panus;
    }

    public void setÜleLimiidi(boolean väärtus){
        this.üleLimiidi = true;
    }

    public void setKäsiOnAktiivne(boolean väärtus){
        this.käsiOnAktiivne = väärtus;
    }

    public List<Kaart> getKäesOlevadKaardid() {
        return käesOlevadKaardid;
    }

    public boolean isÜleLimiidi() {
        return üleLimiidi;
    }

    public boolean iskäsiOnAktiivne() {
        return käsiOnAktiivne;
    }

    // Tavakäe konstruktor - võtab aluseks suure kaardilademi kus üksikkaarte võtab nii mängija kui diiler
    // Mitu kätt mängides võetakse kaarte ikka samast "kuhjast" kuni laualt mitteläbikäinud kaarte seal veel on
    public tavalineKäsi(laualOlevadKaardid kaardidMängus, double panus) {

        this.käsiOnAktiivne = true;
        this.blackJack = false;
        this.varasemadSõnumidKäeValdajale = "";
        this.panus = panus;

        this.käesOlevadKaardid.add(kaardidMängus.annaPakistMängimataKaart());
        this.käesOlevadKaardid.add(kaardidMängus.annaPakistMängimataKaart());

        if(this.arvutaKäeVäärtus() == 21) {
            this.võit = this.panus * 2.5;
            this.käsiOnAktiivne = false;
            this.blackJack = true;
            this.varasemadSõnumidKäeValdajale = "Jess! BlackJack! " + "Võitsid " + this.võit + "€. (algne panus + 1.5x raha tagasi!)";
        }
    }

    // Meetod, et kätte üks kaart juurde küsida
    // Kui lisanduv kaart ajab käe lõhki seatakse klassi privaatsete muutujate väärtused vastavaks ja kaob võimalus uusi kaarte juurde küsida
    public boolean küsiÜksKaartJuurde(laualOlevadKaardid kaardidMängus) {
        if(this.käsiOnAktiivne)
        {
            this.käesOlevadKaardid.add(kaardidMängus.annaPakistMängimataKaart());
            if(this.arvutaKäeVäärtus() > 21) {
                this.käsiOnAktiivne = false;
                this.üleLimiidi = true;
                this.võit = this.panus * -1;
                this.varasemadSõnumidKäeValdajale = "Lisanduv kaart ajas käe lõhki. Kaotasid " + this.panus + " eurot!";
                return true;
            }
        }
        return false;
    }

    // Arvutab käes olevate kaartide väärtuse mängus - sh võttes arvesse, et äss saab olla väärtuselt nii 1 kui ka 11,
    // sõltuvalt selles, mis on mängijale kasulikum
    public Integer arvutaKäeVäärtus() {
        int Summa = 0;
        boolean ässKäes = false;

        for(Kaart k: käesOlevadKaardid) {
            Summa = Summa + k.getVäärtusMängus();
            if(k.getKaardiNimetus() == "äss")
            {
                ässKäes = true;
            }
        }

        if(!ässKäes) {
            return Summa;
        }

        else {
            if(Summa > 21) {
                return Summa - 10;
            }
            else {
                return Summa;
            }
        }
    }

    // Meetod käes olevate kaartide, selle väärtuse ja varasemates sammudes saadud teadete kuvamiseks
    public String näitaKätt() {
        String näita = "";
        näita = näita + "Sul on hetkel käes kaardid: ";
        for(Kaart k: this.käesOlevadKaardid) {
            näita = näita + "["+ k.getUnicodeSümbol() + " - " + k.getKaardiMast() + " " + k.getKaardiNimetus() + "] ";
        }
        näita = näita + " ";
        näita = näita + "Sinu käe väärtus on: " + this.arvutaKäeVäärtus()  + ". ";
        näita = näita + (this.käsiOnAktiivne ? "Soovi korral saad ühe kaardi juurde küsida" : "");
        näita = näita + " " + this.varasemadSõnumidKäeValdajale;

        return näita;
    }

    // Pane käsi maha ehk ära enam kaarte juurde küsi
    public void pööraKaardidMaha(){
        this.käsiOnAktiivne = false;
    }

}
