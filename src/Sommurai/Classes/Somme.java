package Sommurai.Classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by anthony on 9/15/15.
 */
public class Somme {
    //----------------------------------Attributs de la classe Somme
    private List<Integer> listeDeveloppee;
    private List<Integer> listeBrute;
    private int sommeGenerer;
    private int sommeCourante;
    private int moyenneGenerer;
    private int moyenneCourante;
    private int nbrDecoupage;
    private int chiffreNoise;

    /*
        Constructeur par défaut
         */
    public Somme() {
    }

    //----------------------------------Méthodes reliées à la classe Somme

    /**
     * @return Retourne une liste de nombre aléatoire qui contient entre 1 et 9 chiffres
     */
    public void genererListe(boolean noise, boolean mean) {
        List<Integer> uneListe = new ArrayList<Integer>();
        listeBrute = new ArrayList<Integer>();
        Random nombreAleatoire = new Random();
        int chiffreSomme, pourcentage;
        chiffreNoise = 0;

        nbrDecoupage = nombreAleatoire.nextInt((6 - 3) + 1) + 3;

        try {
            //On génère et on ajoute des chiffres à la liste
            for (int i = 1; i <= nbrDecoupage; i++) {
                //On calcul un pourcentage pour savoir si on génère un nombre entre 1 et 9 ou  11 et 99
                pourcentage = nombreAleatoire.nextInt((100 - 1) + 1) + 1;

                if (pourcentage <= 70)
                    chiffreSomme = nombreAleatoire.nextInt((9 - 1) + 1) + 1;
                else
                    chiffreSomme = nombreAleatoire.nextInt((99 - 10) + 1) + 10;


                listeBrute.add(chiffreSomme);

                //------SI C'EST UN NOMBRE, ON SÉPARE EN DEUX CHIFFRES DIFFÉRENTS------
                if (chiffreSomme >= 10) {
                    int chiffreDroite = chiffreSomme % 10;
                    int chiffreGauche = chiffreSomme /= 10;
                    uneListe.add(chiffreGauche);
                    uneListe.add(chiffreDroite);
                } else {
                    uneListe.add(chiffreSomme);
                }

                //Si on est à la dernière itération de la boucle on test
                if (i == nbrDecoupage) {
                    //S'il y a du bruit, on insère un chiffre aléatoire dans la liste
                    if (noise) {
                        chiffreNoise = nombreAleatoire.nextInt((9 - 1) + 1) + 1;
                        System.out.println("Noise: " + chiffreNoise);
                        int indexAleatoire = nombreAleatoire.nextInt((uneListe.size() - 1) + 1) + 1;

                        //On vérifie si on insère le noise dans à un endroit où on pourrait briser un nombre
                        int verifNbrDecomp = (uneListe.get(indexAleatoire) + uneListe.get(indexAleatoire + 1));
                        if (listeBrute.contains(verifNbrDecomp))
                            indexAleatoire = indexAleatoire + 2;

                        uneListe.add(indexAleatoire, chiffreNoise);
                    }
                    listeDeveloppee = uneListe;
                    System.out.println("Découpage: " + nbrDecoupage);
                    genererSomme(noise, mean);
                }
            }

        } catch (Exception e) {
            //Si on attrape une exception, on relance la génération
            genererListe(noise, mean);
        }
    }

    /**
     * Fonction qui génère une somme selon toutes les spécifications de l'énoncé
     */
    public void genererSomme(boolean noise, boolean mean) {
        int sommeTemp = 0;

        //Pour chaque chiffre de la liste, on additionne et on calcule la somme
        for (int i : listeBrute) {
            sommeTemp = sommeTemp + i;
            //Si on est au dernier chiffre de la liste, on test la somme avec un modulo. Si le résultat est 0, on attribut la somme
            //Sinon, on regénère la liste.
            if (i == listeBrute.get(listeBrute.size() - 1)) {
                if ((sommeGenerer % nbrDecoupage != 0) && mean) {
                    sommeGenerer = 0;
                    nbrDecoupage = 0;
                    listeDeveloppee.clear();
                    listeBrute.clear();
                    genererListe(noise, mean);
                } else {
                    sommeGenerer = sommeTemp - chiffreNoise;
                    moyenneGenerer = sommeGenerer / nbrDecoupage;
                    System.out.println("Somme: " + sommeGenerer);
                }
            }
        }
    }

    /*
    ----------------------------------GETTER/SETTER
     */

    public List<Integer> getListeDeveloppee() {
        return listeDeveloppee;
    }

    public void setListeDeveloppee(List<Integer> listeDeChiffre) {
        this.listeDeveloppee = listeDeChiffre;
    }

    public int getnbrDecoupage() {
        return nbrDecoupage;
    }

    public int getSommeGenerer() {
        return sommeGenerer;
    }

    public void setSommeGenerer(int sommeGenerer) {
        this.sommeGenerer = sommeGenerer;
    }

    public int getSommeCourante() {
        return sommeCourante;
    }

    public void setSommeCourante(int sommeCourante) {
        this.sommeCourante = sommeCourante;
    }

    public int getNbrDecoupage() {
        return nbrDecoupage;
    }

    public void setNbrDecoupage(int nbrDecoupage) {
        this.nbrDecoupage = nbrDecoupage;
    }

    public int getChiffreNoise() {
        return chiffreNoise;
    }

    public void setChiffreNoise(int chiffreNoise) {
        this.chiffreNoise = chiffreNoise;
    }

    public int getMoyenneGenerer() {
        return moyenneGenerer;
    }

    public void setMoyenneGenerer(int moyenneGenerer) {
        this.moyenneGenerer = moyenneGenerer;
    }

    public int getMoyenneCourante() {
        return moyenneCourante;
    }

    public void setMoyenneCourante(int moyenneCourante) {
        this.moyenneCourante = moyenneCourante;
    }

    public List<Integer> getListeBrute() {
        return listeBrute;
    }

    public void setListeBrute(List<Integer> listeBrute) {
        this.listeBrute = listeBrute;
    }
}
