package oop;

import java.util.ArrayList;
import java.util.List;

public class KüsimuseHoidik {

    private List<String> Küsimused = new ArrayList<>();
    private List<String[]> vastusevariandid = new ArrayList<>();
    private List<boolean[]> õigedVastused = new ArrayList<>();
    private Integer küsimusiHoidikus;
    private Integer esitamiselOlevKüsimus;

    public KüsimuseHoidik() {
        this.küsimusiHoidikus = 0;
    }

    public void addKüsimusteplok(String Küsimus,
                                 String[] vastusevariandid,
                                 boolean[] õigedVastused) {

        this.Küsimused.add(Küsimus);
        this.vastusevariandid.add(vastusevariandid);
        this.õigedVastused.add(õigedVastused);
        this.küsimusiHoidikus++;
    }

    public Integer getKüsimusiHoidikus() {
        return this.küsimusiHoidikus;
    }

    public void setEsitamiselOlevKüsimus(int esitamiselOlevKüsimus) {
        this.esitamiselOlevKüsimus = esitamiselOlevKüsimus;
    }

    public Integer getEsitamiselOlevKüsimus() {
        return this.esitamiselOlevKüsimus;
    }

    public String getKüsimus() {
        return this.Küsimused.get(this.esitamiselOlevKüsimus-1);
    }

    public boolean[] getÕigedValed() {
        return this.õigedVastused.get(this.esitamiselOlevKüsimus-1);
    }

    public String[] getVasusevariandid () {
        return this.vastusevariandid.get(this.esitamiselOlevKüsimus-1);
    }

}
