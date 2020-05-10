package oop;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;


public class Mangulaud extends Application {

    //Mängu enda seaded
    Integer kaardipakke = 10;
    laualOlevadKaardid mängusOlevadkaardid = new laualOlevadKaardid(kaardipakke);


    double mängijaSaldo = 0;
    double diileriSaldo = 0;

    diileriKäsi diileriKaardidKäes;
    tavalineKäsi sinuKäsi;

    HashMap<String, double[]> saldomuutusteAjalugu = new HashMap<String, double[]>();



    @Override
    public void start(Stage peaLava) throws IOException {

        // Loon piiripaani
        BorderPane piiriPaan = new BorderPane();
        piiriPaan.setMinWidth(900);
        piiriPaan.setMinHeight(500);

        // Loon diileri ja mängija käed
        FlowPane Dealer = new FlowPane(10, 10);
        FlowPane Player = new FlowPane(10, 10);
        Dealer.setPrefWrapLength(700);
        Player.setPrefWrapLength(700);
        //Dealer.setStyle("-fx-background-color: #7eb8da");
        //Player.setStyle("-fx-background-color: #be9ddf");


        // Lisan mängukaardid
        Map<String, Image> piltideMap = new HashMap<String, Image>();
        File kaust = new File("KaartidePildid");
        File[] piltideFailideList = kaust.listFiles();

        for (File f : piltideFailideList) {
            FileInputStream sisse = new FileInputStream(f);
            piltideMap.put(f.getName().replace(".png",""), new Image(sisse, 150, 150, true, true));
            sisse.close();
        }


        // Kättehoidja
        VBox vbox = new VBox();
        Label diileriKäsi = new Label("DIILER ---> mäng ei ole veel aktiivne!");
        Label mängijaKäsi = new Label("MÄNGIJA ---> mäng ei ole veel aktiivne!");
        diileriKäsi.setFont(new Font("Arial", 16));
        mängijaKäsi.setFont(new Font("Arial", 16));
        diileriKäsi.setWrapText(true);
        mängijaKäsi.setWrapText(true);
        diileriKäsi.setStyle("-fx-font-weight: bold");
        mängijaKäsi.setStyle("-fx-font-weight: bold");
        diileriKäsi.setMaxWidth(700);
        mängijaKäsi.setMaxWidth(700);

        vbox.setVgrow(diileriKäsi, Priority.ALWAYS);
        vbox.setVgrow(Dealer, Priority.ALWAYS);
        vbox.setVgrow(mängijaKäsi, Priority.ALWAYS);
        vbox.setVgrow(Player, Priority.ALWAYS);
        vbox.getChildren().add(diileriKäsi);
        vbox.getChildren().add(Dealer);
        vbox.getChildren().add(mängijaKäsi);
        vbox.getChildren().add(Player);
        vbox.setPadding(new Insets(0, 0, 10,10));

        // Lisan kättehoidja paanile
        piiriPaan.setLeft(vbox);


        // nuppodehoidik
        VBox vboxNupud = new VBox();
        vboxNupud.setPadding(new Insets(10, 50, 50, 50));
        vboxNupud.setSpacing(10);

        Button uusMängNupp = new Button("Uus mäng");
        vboxNupud.setVgrow(uusMängNupp, Priority.ALWAYS);
        vboxNupud.getChildren().add(uusMängNupp);
        uusMängNupp.setStyle("-fx-background-color: #4cd7d0;-fx-border-color: #4cd7d0;-fx-border-radius: 5;-fx-padding: 6 6 6 6;");

        Button lõpetaMäng = new Button("Lõpeta mäng");
        vboxNupud.setVgrow(lõpetaMäng, Priority.ALWAYS);
        vboxNupud.getChildren().add(lõpetaMäng);
        lõpetaMäng.setStyle("-fx-background-color: #f51720;-fx-border-color: #f51720;-fx-text-fill: white;-fx-border-radius: 5;-fx-padding: 6 6 6 6;");

        Button küsiKaartJuurde = new Button("Anna kaart");
        vboxNupud.setVgrow(küsiKaartJuurde, Priority.ALWAYS);
        vboxNupud.getChildren().add(küsiKaartJuurde);
        küsiKaartJuurde.setStyle("-fx-background-color: #4cd7d0;-fx-border-color: #4cd7d0;-fx-border-radius: 5;-fx-padding: 6 6 6 6;");

        Button keeraKaardidMaha = new Button("Käsi maha");
        vboxNupud.setVgrow(keeraKaardidMaha, Priority.ALWAYS);
        vboxNupud.getChildren().add(keeraKaardidMaha);
        keeraKaardidMaha.setStyle("-fx-background-color: #4cd7d0;-fx-border-color: #4cd7d0;-fx-border-radius: 5;-fx-padding: 6 6 6 6;");



        // Lisan kättehoidja paanile
        piiriPaan.setRight(vboxNupud);


        // Panuse küsimise popup aken
        TextInputDialog td = new TextInputDialog("Sisesta panus");
        td.getDialogPane().lookupButton(ButtonType.CANCEL).setDisable(true);


        //Teadetetahvel
        FlowPane teadetetahvelFlow = new FlowPane(10, 10);
        teadetetahvelFlow.setPrefWidth(600.0);
        teadetetahvelFlow.setColumnHalignment(HPos.CENTER);
        Label teadetetahvel = new Label("Hakkame mängima. Klikka nupule ---> uus mäng.");
        teadetetahvel.setFont(new Font("Arial", 16));
        teadetetahvelFlow.setStyle("-fx-background-color: #4cd7d0");
        teadetetahvel.setStyle("-fx-font-weight: bold");
        teadetetahvelFlow.getChildren().add(teadetetahvel);
        teadetetahvelFlow.setPadding(new Insets(15, 0, 15,10));
        piiriPaan.setBottom(teadetetahvelFlow);


        //Skooritahvel
        FlowPane skooritahvelFlow = new FlowPane(10, 10);
        skooritahvelFlow.setPrefWidth(600.0);
        skooritahvelFlow.setColumnHalignment(HPos.CENTER);
        Label skooritahvelFlowL1 = new Label("DIILERI SALDO: " + diileriSaldo + " eurot.");
        Label skooritahvelFlowL2 = new Label("MÄNGIJA SALDO: " + mängijaSaldo + " eurot.");
        skooritahvelFlowL1.setFont(new Font("Arial", 16));
        skooritahvelFlowL2.setFont(new Font("Arial", 16));
        skooritahvelFlowL1.setStyle("-fx-font-weight: bold");
        skooritahvelFlowL2.setStyle("-fx-font-weight: bold");
        skooritahvelFlowL1.setStyle("-fx-background-color: #4cd7d0");
        skooritahvelFlowL2.setStyle("-fx-background-color: #4cd7d0");
        skooritahvelFlow.getChildren().addAll(skooritahvelFlowL1, skooritahvelFlowL2);
        skooritahvelFlow.setPadding(new Insets(15, 0, 15,10));
        piiriPaan.setTop(skooritahvelFlow);


        // create a event handler
        EventHandler<ActionEvent> panuseSeadmine = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                td.setHeaderText("Sisesta panus");
                Optional<String> result = td.showAndWait();

                try{

                    Dealer.getChildren().clear();
                    Player.getChildren().clear();

                    String panusSõna = result.get();
                    int panus = Integer.parseInt(panusSõna);
                    teadetetahvel.setText("Mäng käib nüüd summale: " + panus);

                    diileriKaardidKäes = new diileriKäsi(mängusOlevadkaardid, panus);
                    diileriKäsi.setText("DIILER: " + diileriKaardidKäes.näitaAlgulKätt());

                    Dealer.getChildren().add(new ImageView(piltideMap.get("back")));

                    for(Kaart k: diileriKaardidKäes.getKäesOlevadKaardid().subList(1, diileriKaardidKäes.getKäesOlevadKaardid().size())) {
                        Dealer.getChildren().add(new ImageView(piltideMap.get(k.getPildiNimetus())));
                    }

                    sinuKäsi = new tavalineKäsi(mängusOlevadkaardid, panus);
                    mängijaKäsi.setText("MÄNGIJA: " + sinuKäsi.näitaKätt());

                    for(Kaart k: sinuKäsi.getKäesOlevadKaardid()) {
                        Player.getChildren().add(new ImageView(piltideMap.get(k.getPildiNimetus())));

                        if(sinuKäsi.getblackJack()) {
                            mängijaKäsi.setText("MÄNGIJA: " + "BLACK JACK! Sinu või!" + "Said raha " + sinuKäsi.getPanus() * 1.5);
                            teadetetahvel.setText("MÄNGIJA: " + "BLACK JACK! Sinu või!" + "Said raha " + sinuKäsi.getPanus() * 1.5 +
                                    " eurot. -----> Soovi korral saad uuesti mängida");


                            mängijaSaldo = mängijaSaldo + sinuKäsi.getPanus()*1.5;
                            diileriSaldo = diileriSaldo - sinuKäsi.getPanus()*1.5;

                            skooritahvelFlowL1.setText("DIILERI SALDO: " + diileriSaldo + " eurot.");
                            skooritahvelFlowL2.setText("MÄNGIJA SALDO: " + mängijaSaldo + " eurot.");

                            saldomuutusteAjalugu.put(("Mäng nr " + (saldomuutusteAjalugu.size()+1) + "(diileri saldo, mängija saldo: )"),
                                    new double[]{diileriSaldo, mängijaSaldo});
                        }
                    }



                } catch (NoSuchElementException nsel) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Hoiatusteade");
                    alert.setHeaderText("Nüüd on kõik kohe väga valesti");
                    alert.setContentText("Sa andsid numbri asemel tekstisisendi või tühja väärtuse. Ole hea - kirjuta number ja sea nii panus.");
                    alert.showAndWait();
                    uusMängNupp.fire();

                } catch (NumberFormatException NFE) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Hoiatusteade");
                    alert.setHeaderText("Nüüd on kõik kohe väga valesti");
                    alert.setContentText("Sa andsid numbri asemel tekstisisendi või tühja väärtuse. Ole hea - kirjuta number ja sea nii panus.");
                    alert.showAndWait();
                    uusMängNupp.fire();
                }

            }
        };

        uusMängNupp.setOnAction(panuseSeadmine);



        EventHandler<ActionEvent> kaardiJuurdeKüsimine = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                if(sinuKäsi.iskäsiOnAktiivne()) {
                    sinuKäsi.küsiÜksKaartJuurde(mängusOlevadkaardid);
                    mängijaKäsi.setText("MÄNGIJA: " + sinuKäsi.näitaKätt());

                    if(!sinuKäsi.iskäsiOnAktiivne()) {
                        teadetetahvel.setText("Sa kaotsasid just " + sinuKäsi.getPanus() + " eurot!");
                        mängijaSaldo = mängijaSaldo - sinuKäsi.getPanus();
                        diileriSaldo = diileriSaldo + sinuKäsi.getPanus();

                        skooritahvelFlowL1.setText("DIILERI SALDO: " + diileriSaldo + " eurot.");
                        skooritahvelFlowL2.setText("MÄNGIJA SALDO: " + mängijaSaldo + " eurot.");

                        saldomuutusteAjalugu.put(("Mäng nr " + (saldomuutusteAjalugu.size()+1) + "(diileri saldo, mängija saldo: )"),
                                new double[]{diileriSaldo, mängijaSaldo});
                    }


                    Player.getChildren().clear();
                    for(Kaart k: sinuKäsi.getKäesOlevadKaardid()) {
                        Player.getChildren().add(new ImageView(piltideMap.get(k.getPildiNimetus())));
                    }

                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Hoiatusteade");
                    alert.setHeaderText("Nüüd on kõik kohe väga valesti");
                    alert.setContentText("Sinu käsi ei ole enam aktiivne! Sa pead uue mängu alustama. St oled kaardid juba maha keeranud või oma skoorilt lõhki läinud.");
                    alert.showAndWait();
                }
            }
        };

        küsiKaartJuurde.setOnAction(kaardiJuurdeKüsimine);



        EventHandler<ActionEvent> kaardidMaha = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                if(sinuKäsi.iskäsiOnAktiivne()) {

                    sinuKäsi.pööraKaardidMaha();
                    teadetetahvel.setText("Pöörasin kaardid maha. Vaatame nüüd diileri kätt.");

                    mängijaKäsi.setText("MÄNGIJA: " + sinuKäsi.näitaKätt());
                    diileriKäsi.setText("DIILER: " + diileriKaardidKäes.näitaKätt());

                    Dealer.getChildren().clear();
                    for(Kaart k: diileriKaardidKäes.getKäesOlevadKaardid()) {
                        Dealer.getChildren().add(new ImageView(piltideMap.get(k.getPildiNimetus())));
                    }

                    if(diileriKaardidKäes.arvutaKäeVäärtus() > 16) {
                        diileriKaardidKäes.setKäsiOnAktiivne(false);
                        teadetetahvel.setText("Diileri käe väärtus on enam kui 16 ehk " +
                                diileriKaardidKäes.arvutaKäeVäärtus() +
                                "Diiler ei pea seegi kaarte juurde küsima. Vaatame kuidas mäng lõppes.");

                        if(sinuKäsi.compareTo(diileriKaardidKäes) == 1) {
                            teadetetahvel.setText("Diiler võitis. Sa kaotasid " + sinuKäsi.getPanus() + " eurot. ---> Soovi korral saad uuesti mängida!");

                            mängijaSaldo = mängijaSaldo - sinuKäsi.getPanus();
                            diileriSaldo = diileriSaldo + sinuKäsi.getPanus();

                            skooritahvelFlowL1.setText("DIILERI SALDO: " + diileriSaldo + " eurot.");
                            skooritahvelFlowL2.setText("MÄNGIJA SALDO: " + mängijaSaldo + " eurot.");

                            saldomuutusteAjalugu.put(("Mäng nr " + (saldomuutusteAjalugu.size()+1) + "(diileri saldo, mängija saldo: )"),
                                    new double[]{diileriSaldo, mängijaSaldo});

                        }
                        else if(sinuKäsi.compareTo(diileriKaardidKäes) == -1) {
                            teadetetahvel.setText("Diiler kaotas. Sa võitsid " + sinuKäsi.getPanus() + " eurot. ---> Soovi korral saad uuesti mängida!");

                            mängijaSaldo = mängijaSaldo + sinuKäsi.getPanus();
                            diileriSaldo = diileriSaldo - sinuKäsi.getPanus();

                            skooritahvelFlowL1.setText("DIILERI SALDO: " + diileriSaldo + " eurot.");
                            skooritahvelFlowL2.setText("MÄNGIJA SALDO: " + mängijaSaldo + " eurot.");

                            saldomuutusteAjalugu.put(("Mäng nr " + (saldomuutusteAjalugu.size()+1) + "(diileri saldo, mängija saldo: )"),
                                    new double[]{diileriSaldo, mängijaSaldo});

                        }
                        else if (sinuKäsi.compareTo(diileriKaardidKäes) == 0) {
                            teadetetahvel.setText("Jäite viiki! Saad oma raha tagasi." + " Soovi korral saad uuesti mängida!");
                        }
                    }

                    else if (diileriKaardidKäes.arvutaKäeVäärtus() <= 16) {
                        while(diileriKaardidKäes.arvutaKäeVäärtus() <= 16) {
                            teadetetahvel.setText("Diileri käe väärtus on vähem kui 16 ehk " +
                                    diileriKaardidKäes.arvutaKäeVäärtus() +
                                    "Diiler peab seega kaardi juurde küsima.");

                            diileriKaardidKäes.küsiÜksKaartJuurde(mängusOlevadkaardid);

                            diileriKäsi.setText("DIILER: " + diileriKaardidKäes.näitaKätt());
                            Dealer.getChildren().clear();
                            for(Kaart k: diileriKaardidKäes.getKäesOlevadKaardid()) {
                                Dealer.getChildren().add(new ImageView(piltideMap.get(k.getPildiNimetus())));
                            }

                            if(diileriKaardidKäes.arvutaKäeVäärtus() > 16) {
                                diileriKaardidKäes.setKäsiOnAktiivne(false);

                                teadetetahvel.setText("Diileri käe väärtus on nüüd üle 16 ja ta ei pea kaarte enam juurde võtma. "+ "Diileri käe väärtus on : " + diileriKaardidKäes.arvutaKäeVäärtus());

                                if(sinuKäsi.compareTo(diileriKaardidKäes) == 1) {
                                    teadetetahvel.setText("Diiler võitis. Sa kaotasid " + sinuKäsi.getPanus() + " eurot. ---> Soovi korral saad uuesti mängida!");

                                    mängijaSaldo = mängijaSaldo - sinuKäsi.getPanus();
                                    diileriSaldo = diileriSaldo + sinuKäsi.getPanus();

                                    skooritahvelFlowL1.setText("DIILERI SALDO: " + diileriSaldo + " eurot.");
                                    skooritahvelFlowL2.setText("MÄNGIJA SALDO: " + mängijaSaldo + " eurot.");

                                    saldomuutusteAjalugu.put(("Mäng nr " + (saldomuutusteAjalugu.size()+1) + "(diileri saldo, mängija saldo: )"),
                                            new double[]{diileriSaldo, mängijaSaldo});

                                }
                                else if(sinuKäsi.compareTo(diileriKaardidKäes) == -1) {
                                    teadetetahvel.setText("Diiler kaotas. Sa võitsid " + sinuKäsi.getPanus() + " eurot. ---> Soovi korral saad uuesti mängida!");

                                    mängijaSaldo = mängijaSaldo + sinuKäsi.getPanus();
                                    diileriSaldo = diileriSaldo - sinuKäsi.getPanus();

                                    skooritahvelFlowL1.setText("DIILERI SALDO: " + diileriSaldo + " eurot.");
                                    skooritahvelFlowL2.setText("MÄNGIJA SALDO: " + mängijaSaldo + " eurot.");

                                    saldomuutusteAjalugu.put(("Mäng nr " + (saldomuutusteAjalugu.size()+1) + "(diileri saldo, mängija saldo: )"),
                                            new double[]{diileriSaldo, mängijaSaldo});

                                }
                                else if (sinuKäsi.compareTo(diileriKaardidKäes) == 0) {
                                    teadetetahvel.setText("Jäite viiki! Saad oma raha tagasi." + " Soovi korral saad uuesti mängida!");
                                }
                            }
                        }

                    }

                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Hoiatusteade");
                    alert.setHeaderText("Nüüd on kõik kohe väga valesti");
                    alert.setContentText("Sul ei ole aktiivset kätt, mida lauale panna!");
                    alert.showAndWait();
                }




            }
        };

        keeraKaardidMaha.setOnAction(kaardidMaha);


        // Mängust väljumise popup aken

        TextInputDialog exitDialog = new TextInputDialog("Sisesta faili nimi, millesse kirjutada saldode muutumise jada");
        exitDialog.getDialogPane().lookupButton(ButtonType.CANCEL).setDisable(true);

        // Mängust väljumise nupu tegevus
        EventHandler<ActionEvent> mängustVäljumine = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                exitDialog.setHeaderText("Anna failile nimi");
                Optional<String> result = exitDialog.showAndWait();

                try(
                        FileWriter kirjutaja = new FileWriter(result.orElse("") + ".txt");
                        ) {

                    for(String k : saldomuutusteAjalugu.keySet()) {
                        String kirjutada = k + " (" + saldomuutusteAjalugu.get(k)[0] +", " + saldomuutusteAjalugu.get(k)[1] + ")." + ";\n";
                        kirjutaja.write(kirjutada);
                    }

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Lahkun mängust");
                    alert.setHeaderText("Sulgen mängu");
                    alert.setContentText("Sulgen mängu peale seda kui vajutad OK. Sinu andmed on kirjutatud faili " + result.orElse("") + ".txt!");
                    alert.showAndWait();
                    Platform.exit();
                }

                catch (IOException ioe) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Hoiatusteade");
                    alert.setHeaderText("Nüüd on kõik kohe väga valesti");
                    alert.setContentText("Faili kirjutamine ebaõnnetsus. Proofi failile teine nimi anda. ");
                    alert.showAndWait();
                    lõpetaMäng.fire();
                }


            }
        };

        lõpetaMäng.setOnAction(mängustVäljumine);



        Scene stseen1 = new Scene(piiriPaan, 900, 500, Color.WHITE);
        peaLava.setScene(stseen1);
        peaLava.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}

