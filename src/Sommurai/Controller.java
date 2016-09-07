package Sommurai;

import Sommurai.Classes.Somme;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.awt.*;

public class Controller {
    //Déclaration des éléments du FXML
    @FXML GridPane gridChiffres;

    //Déclaration d'une somme
    Somme uneSomme = new Somme();

    public void btnClickNext(ActionEvent event){
        uneSomme.genererListe();
        gridChiffres.getChildren().clear();
        afficheSomme();
    }

    public void btnChiffreAction(ActionEvent event) {

    }

    public void afficheSomme() {
        int compteur = 0;

        for(int chiffre : uneSomme.getListeDeChiffre())
        {
            Button btnTest = new Button();
            btnTest.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //TODO. Implanter la fonction pour déterminer si c'est le bon chiffre qui a été sélectionné
                }
            });
            btnTest.setText(String.valueOf(chiffre) + " ");
            gridChiffres.add(btnTest,compteur,0);
            compteur++;
        }
    }
}
