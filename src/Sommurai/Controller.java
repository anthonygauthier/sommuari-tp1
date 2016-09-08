package Sommurai;

import Sommurai.Classes.Somme;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    //Déclaration des éléments du FXML
    @FXML
    private GridPane gridChiffres;
    @FXML
    private Text txtBut;
    @FXML
    private Text txtSomme;
    @FXML
    private CheckBox cbxNoise;
    @FXML
    private CheckBox cbxFindmean;
    private Somme uneSomme;
    private int nbrRegroupement = 0, indexPremierBtn = 0;
    ;
    private boolean noise, mean, mouseDown;
    private String stringSelMulti = "";

    /**
     * Constructeur par défaut de la classe Controller
     */
    public Controller() {
    }

    /**
     * Méthode qui exécute raffraîchi l'affichage lorsque l'utilisateur clique sur le bouton "NEXT"
     *
     * @param event (Click)
     */
    public void btnClickNext(ActionEvent event) {
        //On efface tout les boutons présents dans le GridPane et on réaffiche une nouvelle somme
        gridChiffres.getChildren().clear();
        nbrRegroupement = 0;
        txtSomme.setText("Somme: ");
        afficheSomme();
    }

    /**
     * Méthode qui affiche une somme avec les différents boutons
     */
    public void afficheSomme() {
        int compteur = 0;
        noise = false;
        mean = false;
        uneSomme = new Somme();


        //On vérifie si "noise" et/ou "mean" sont cochés
        if (cbxNoise.isSelected()) {
            noise = true;
        } else if (cbxFindmean.isSelected()) {
            mean = true;
        }

        uneSomme.genererListe(noise, mean);

        //On génère les différents boutons
        for (int chiffre : uneSomme.getListeDeveloppee()) {
            Button btnChiffre = new Button();

            GenerateMouseEvents(btnChiffre);

            btnChiffre.setStyle("-fx-font: 36 arial; -fx-color:gray;");
            btnChiffre.setText(String.valueOf(chiffre));
            gridChiffres.add(btnChiffre, compteur, 0);
            compteur++;
        }

        //On affiche le but
        if (mean) {
            txtBut.setText(String.valueOf(uneSomme.getMoyenneGenerer()));
            txtSomme.setText("Moyenne: ");
        } else {
            txtBut.setText(String.valueOf(uneSomme.getSommeGenerer()));
        }
    }

    /**
     * Méthode exécuté lors d'un clique sur un des boutons qui contient un chiffre
     *
     * @param btnChiffre (On prend en paramètre le bouton qui a été cliqué)
     */
    public void btnChiffreAction(Button btnChiffre) {
        int valeurBouton = Integer.parseInt(btnChiffre.getText());
        btnChiffre.setDisable(true);
        nbrRegroupement += 1;
        uneSomme.setSommeCourante(uneSomme.getSommeCourante() + Integer.parseInt(stringSelMulti));
        stringSelMulti = "";

        if (mean) {
            uneSomme.setMoyenneCourante((uneSomme.getSommeCourante()) / nbrRegroupement);
            txtSomme.setText("Moyenne: " + String.valueOf(uneSomme.getMoyenneCourante()) + " (" + nbrRegroupement + ")");
        } else {
            txtSomme.setText("Somme: " + String.valueOf(uneSomme.getSommeCourante()) + " (" + nbrRegroupement + ")");
        }

        changerCouleur(nbrRegroupement, btnChiffre, null);

        if (verifierSomme(mean)) {
            for (Node bouton : gridChiffres.getChildren()) {
                bouton.setStyle("-fx-font: 36 arial; -fx-color:green;");
                bouton.setDisable(true);
            }
        } else {
            if ((nbrRegroupement >= uneSomme.getNbrDecoupage()) || (uneSomme.getSommeCourante() > uneSomme.getSommeGenerer()) || ((uneSomme.getSommeCourante() == uneSomme.getSommeGenerer()) && (uneSomme.getNbrDecoupage() > nbrRegroupement))) {
                for (Node enfant : gridChiffres.getChildren()) {
                    enfant.setStyle("-fx-font: 36 arial; -fx-color:red;");
                    enfant.setDisable(true);
                }
            }
        }
    }

    /**
     * Méthode qui vérifie si la somme trouver par l'utilisateur
     *
     * @return retourne vrai ou faux
     */
    public boolean verifierSomme(boolean findMean) {
        //Si c'est une somme
        if (!findMean) {
            if ((uneSomme.getSommeCourante() == uneSomme.getSommeGenerer()) && (uneSomme.getNbrDecoupage() == nbrRegroupement)) {
                return true;
            } else if ((uneSomme.getSommeCourante() > uneSomme.getSommeGenerer()) && (uneSomme.getNbrDecoupage() > nbrRegroupement)) {
                return false;
            } else {
                return false;
            }
            //Si c'est une moyenne
        } else {
            if ((uneSomme.getMoyenneCourante() == uneSomme.getMoyenneGenerer()) && (uneSomme.getNbrDecoupage() == nbrRegroupement)) {
                return true;
            } else if ((uneSomme.getMoyenneCourante() > uneSomme.getMoyenneGenerer()) && (uneSomme.getNbrDecoupage() > nbrRegroupement)) {
                return false;
            } else {
                return false;
            }
        }
    }

    /**
     * Méthode qui est déclanché lors d'un clique sur le bouton GiveUp
     *
     * @param event
     */
    public void btnClickGiveUp(ActionEvent event) {
        int compteurCouleur = 1;
        int compteurBouton = 0;
        int nombreConcatener = 0;
        int indexListe = 0;
        boolean bContinuer = false;
        List<Integer> listeTempo = new ArrayList<Integer>(uneSomme.getListeBrute());
        String chaineConcatener = "";
        Node bouton2 = null;

        for (Node bouton : gridChiffres.getChildren()) {
            if (bContinuer) {
                //On réinitialise la chaine concatener, on la met à -1 pour éviter les erreurs lors des parseInt
                chaineConcatener = "-1";
                compteurBouton++;
                bContinuer = false;
                continue;
            }

            //On mémorise le bouton dans un bouton temporaire pour accéder au text
            Button btnCourant = (Button) bouton;

            if (gridChiffres.getChildren().size() > compteurBouton + 1) {
                bouton2 = gridChiffres.getChildren().get(compteurBouton + 1);
                Button btnProchain = (Button) bouton2;
                chaineConcatener = btnCourant.getText() + btnProchain.getText();
            }

            if (listeTempo.contains(Integer.parseInt(chaineConcatener))) {
                changerCouleur(compteurCouleur, null, bouton);
                changerCouleur(compteurCouleur, null, bouton2);
                indexListe = listeTempo.indexOf(Integer.parseInt(chaineConcatener));
                listeTempo.remove(indexListe);
                bContinuer = true;
            } else if (listeTempo.contains(Integer.parseInt(btnCourant.getText()))) {
                changerCouleur(compteurCouleur, null, bouton);
                indexListe = listeTempo.indexOf(Integer.parseInt(btnCourant.getText()));
                listeTempo.remove(indexListe);
            } else {
                //Noise
                bouton.setStyle("-fx-font: 36 arial; -fx-color:red;");
                bouton.setDisable(true);
            }
            compteurCouleur++;
            compteurBouton++;
        }
        txtSomme.setText("Somme: " + uneSomme.getSommeGenerer() + " (" + uneSomme.getNbrDecoupage() + ")");
    }

    /**
     * Méthode qui s'effectue lorsqu'on clique sur le bouton Reset
     *
     * @param event
     */
    public void btnClickReset(ActionEvent event) {
        if (cbxFindmean.isSelected())
            mean = true;
        else
            mean = false;

        nbrRegroupement = 0;
        if (mean) {
            uneSomme.setMoyenneCourante(0);
            txtSomme.setText("Moyenne: ");
            txtBut.setText(String.valueOf(uneSomme.getMoyenneGenerer()));
        } else {
            uneSomme.setSommeCourante(0);
            txtSomme.setText("Somme: ");
        }

        for (Node bouton : gridChiffres.getChildren()) {
            bouton.setStyle("-fx-font: 36 arial; -fx-color:gray;");
            bouton.setDisable(false);
        }
    }

    /**
     * Méthode qui change la couleur des boutons selon le nombre de découpage
     *
     * @param entierDeComparaison - nombre de découpage
     * @param boutonAChanger      - Le bouton dont on doit changer la couleur
     * @param nodeAChanger        - Le node qui sera transformer en bouton pour changer la couleur
     */
    public void changerCouleur(int entierDeComparaison, Button boutonAChanger, Node nodeAChanger) {
        if (nodeAChanger != null)
            boutonAChanger = (Button) nodeAChanger;

        switch (entierDeComparaison) {
            case 1:
                boutonAChanger.setStyle("-fx-font: 36 arial; -fx-color:#00FFFF;");
                break;
            case 2:
                boutonAChanger.setStyle("-fx-font: 36 arial; -fx-color:#00BFFF;");
                break;
            case 3:
                boutonAChanger.setStyle("-fx-font: 36 arial; -fx-color:#0080FF;");
                break;
            case 4:
                boutonAChanger.setStyle("-fx-font: 36 arial; -fx-color:#0040FF;");
                break;
            case 5:
                boutonAChanger.setStyle("-fx-font: 36 arial; -fx-color:#0404B4;");
                break;
            case 6:
                boutonAChanger.setStyle("-fx-font: 36 arial; -fx-color:#0B0B61;");
                break;
        }
        boutonAChanger.setDisable(true);
    }

    /**
     * Génère tous les évènements concernant la souris et la sélection multiple
     *
     * @param btnChiffre
     */
    private void GenerateMouseEvents(Button btnChiffre) {

        btnChiffre.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseDown = false;
                btnChiffreAction(btnChiffre);
            }
        });

        btnChiffre.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseDown = true;
                changerCouleur(nbrRegroupement + 1, btnChiffre, null);
                stringSelMulti += btnChiffre.getText();
                indexPremierBtn = gridChiffres.getChildren().indexOf(btnChiffre);
            }
        });

        //Méthodes de drag over
        btnChiffre.addEventHandler(MouseEvent.DRAG_DETECTED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                gridChiffres.startFullDrag();
            }
        });

        btnChiffre.addEventHandler(MouseDragEvent.MOUSE_DRAG_OVER, new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent event) {
                int indexBoutonCourant = gridChiffres.getChildren().indexOf(btnChiffre);

                if (indexBoutonCourant == (indexPremierBtn + 1) || indexBoutonCourant == (indexPremierBtn - 1)) {
                    indexPremierBtn = gridChiffres.getChildren().indexOf(btnChiffre);
                    stringSelMulti += btnChiffre.getText();
                    if (mean)
                        txtSomme.setText("Moyenne: " + Integer.parseInt(stringSelMulti) / nbrRegroupement + " (" + nbrRegroupement + ")");
                    else
                        txtSomme.setText("Somme: " + Integer.parseInt(stringSelMulti) + " (" + nbrRegroupement + ")");

                    changerCouleur(nbrRegroupement + 1, btnChiffre, null);
                } else if ((indexBoutonCourant == (indexPremierBtn + 1) || indexBoutonCourant == (indexPremierBtn - 1)) && btnChiffre.isDisabled()) {
                    btnChiffre.setDisable(false);
                    btnChiffre.setStyle("-fx-font: 36 arial; -fx-color:gray;");
                }
            }
        });
    }
}
