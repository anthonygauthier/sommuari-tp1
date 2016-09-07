package Sommurai.Classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by anthony on 9/15/15.
 */
public class Somme {
    //----------------------------------Attributs de la classe Somme
    private List<Integer> listeDeChiffre;
    private int sommeGenerer;
    private int nbrDecoupage;

    /*
    Constructeur par défaut
     */
    public Somme(){}

    //----------------------------------Méthodes reliées à la classe Somme

    /**
     *
     * @return Retourne une liste de nombre aléatoire qui contient entre 1 et 9 chiffres
     */
    public void genererListe()
    {
        List<Integer> uneListe = new ArrayList<Integer>();
        Random nombreAleatoire = new Random();
        int chiffreSomme, pourcentage;

        nbrDecoupage = nombreAleatoire.nextInt((6-3)+1)+3;

        try {
            //On génère et on ajoute des chiffres à la liste
            for (int i = 1; i <= nbrDecoupage; i++) {
                //On calcul un pourcentage pour savoir si on génère un nombre entre 1 et 9 ou  11 et 99
                pourcentage = nombreAleatoire.nextInt((100-1)+1) + 1;

                if (pourcentage <= 70)
                    chiffreSomme = nombreAleatoire.nextInt((9-1)+1) + 1;
                else
                    chiffreSomme = nombreAleatoire.nextInt((99-10)+1) + 10;

                //------SI C'EST UN NOMBRE, ON SÉPARE EN DEUX CHIFFRES DIFFÉRENTS------
                if(chiffreSomme >= 10)
                {
                    uneListe.add(i - 1, chiffreSomme%10);
                    i++;
                    chiffreSomme /= 10;
                    uneListe.add(i - 1, chiffreSomme);
                }
                else
                {
                    uneListe.add(i - 1, chiffreSomme);
                }


                //Si on est à la dernière itération de la boucle on test
                if (i == nbrDecoupage) {
                    listeDeChiffre = uneListe;
                    System.out.println("Découpage: "+nbrDecoupage);
                    genererSomme();
                }
            }
        } catch (Exception e) {
            //Si on attrape une exception, on relance la génération
            genererListe();
        }
    }

    public void genererSomme()
    {
        int sommeTemp = 0;

        //Pour chaque chiffre de la liste, on additionne et on calcule la somme
        for(int i:listeDeChiffre)
        {
            sommeTemp = sommeTemp + i;
            //Si on est au dernier chiffre de la liste, on test la somme avec un modulo. Si le résultat est 0, on attribut la somme
            //Sinon, on regénère la liste.
            if(i == listeDeChiffre.get(listeDeChiffre.size() - 1))
            {
                if (sommeGenerer % nbrDecoupage != 0)
                {
                    sommeGenerer= 0;
                    nbrDecoupage =0;
                    listeDeChiffre.clear();
                    genererListe();
                }
                else
                {
                    sommeGenerer = sommeTemp;
                    System.out.println("Somme: "+sommeGenerer);
                }

            }
        }
    }

    /*
    ----------------------------------GETTER/SETTER
     */

    public List<Integer> getListeDeChiffre() {return listeDeChiffre;}
    public int getnbrDecoupage() {return nbrDecoupage;}
    public int getSommeGenerer() {
        return sommeGenerer;
    }
}
