package twisk.mondeIG;

import twisk.outils.FabriqueIdentifiant;
import twisk.outils.TailleComposants;

import java.util.Iterator;
import java.util.Random;

/**
 * The type Etape ig.
 */
public abstract class EtapeIG implements Iterable<PointDeControleIG> {
    /**
     * Le nom.
     */
    protected String nom;
    /**
     * L'identifiant.
     */
    protected String identifiant;
    /**
     * La position en x.
     */
    protected int posX;
    /**
     * La position en y.
     */
    protected int posY;
    /**
     * les Points de contrôles.
     */
    protected PointDeControleIG[] ptsDeCtrl;
    /**
     * Booléen indiquant si l'étape est Sélectionnée
     */
    protected boolean estSelectionnee;
    /**
     * Booléen indiquant si l'étape est une entree.
     */
    protected boolean estUneEntree;
    /**
     * Booléen indiquant si l'étape est une sortie.
     */
    protected boolean estUneSortie;
    /**
     * Le delai.
     */
    protected int delai;
    /**
     * L'ecart.
     */
    protected int ecart;

    /**
     * Instancie une Etape.
     *
     * @param nom le nom
     * @param idf l'identifiant
     */
    public EtapeIG(String nom, String idf) {
        this.nom = nom;
        this.identifiant = idf;
        FabriqueIdentifiant fI = FabriqueIdentifiant.getInstance();
        ptsDeCtrl = new PointDeControleIG[4];
        for (int i = 0; i < ptsDeCtrl.length; i++) {
            ptsDeCtrl[i] = new PointDeControleIG(fI.getIDPointDeControle(), this);
        }
        changePos();
        estSelectionnee = false;
        estUneEntree = false;
        estUneSortie = false;
        delai = 10;
        ecart = 5;
    }

    /**
     * Retourne le nom.
     *
     * @return le nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne pos x.
     *
     * @return la position en x de l'étape
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Retourne pos y.
     *
     * @return la position en y de l'étape
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Positionne l'étape aléatoirement sur la fenêtre.
     */
    public void changePos() {
        Random random = new Random();
        TailleComposants tC = TailleComposants.getInstance();
        posX = random.nextInt(tC.getLargeurFenetre() - tC.getLargeurActivite() - tC.getRayonPtsDeCtrl()) + tC.getRayonPtsDeCtrl();
        posY = random.nextInt(tC.getHauteurFenetre() - tC.getTailleBouton() * 2 - tC.getHauteurActivite() - tC.getRayonPtsDeCtrl()) + tC.getRayonPtsDeCtrl();
        repositionnerPdcs();
    }

    /**
     * Repositionne les points de contrôles.
     */
    public void repositionnerPdcs() {
        TailleComposants tC = TailleComposants.getInstance();
        //On positionne les points de controle en sachant que posX et posY correspondent aux coordonnées du point en haut à gauche de l'activité
        ptsDeCtrl[0].changePos(posX + tC.getLargeurActivite() + tC.getRayonPtsDeCtrl(), posY + tC.getHauteurActivite() / 2);
        ptsDeCtrl[1].changePos(posX + tC.getLargeurActivite() / 2, posY + tC.getHauteurActivite() + tC.getRayonPtsDeCtrl());
        ptsDeCtrl[2].changePos(posX - tC.getRayonPtsDeCtrl(), posY + tC.getHauteurActivite() / 2);
        ptsDeCtrl[3].changePos(posX + tC.getLargeurActivite() / 2, posY - tC.getRayonPtsDeCtrl());
    }

    @Override
    public Iterator<PointDeControleIG> iterator() {
        return new Iterator<>() {
            public int indice = 0;

            @Override
            public boolean hasNext() {
                return indice < ptsDeCtrl.length;
            }

            @Override
            public PointDeControleIG next() {
                indice++;
                return ptsDeCtrl[indice - 1];
            }
        };
    }

    /**
     * Retourne le pdc d'indice i
     *
     * @param i l'indice
     * @return le pdc demandé
     */
//Fonctions utiles pour les tests
    public PointDeControleIG getPdcI(int i) {
        return ptsDeCtrl[i];
    }

    /**
     * Retourne l'identifiant.
     *
     * @return the identifiant
     */
    public String getIdentifiant() {
        return identifiant;
    }

    /**
     * Défini le nom.
     *
     * @param nom le nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne un booléen indiquant si l'étape est sélectionnée ou non.
     *
     * @return le booléen
     */
    public boolean estSelectionnee() {
        return estSelectionnee;
    }

    /**
     * Sélectionne l'étape si elle ne l'est pas déjà ou déselectionne celle-ci si elle est déjà sélectionnee.
     */
    public void inverseSelectionnee() {
        estSelectionnee = !estSelectionnee;
    }

    /**
     * Inverse est une entree.
     */
    public void inverseEstUneEntree() {
        estUneEntree = !estUneEntree;
    }

    /**
     * Retourne un booléen indiquant si l'étape est une entrée ou non.
     *
     * @return le booléen
     */
    public boolean estUneEntree() {
        return estUneEntree;
    }

    /**
     * Inverse est une sortie.
     */
    public void inverseEstUneSortie() {
        estUneSortie = !estUneSortie;
    }


    /**
     * Retourne un booléen indiquant si l'étape est une sortie ou non..
     *
     * @return le booléen
     */
    public boolean estUneSortie() {
        return estUneSortie;
    }

    /**
     * Change la position de l'étape.
     *
     * @param x la nouvelle position en x
     * @param y la nouvelle position en y
     */
    public void changePos(int x, int y) {
        posX = (x);
        posY = (y);

        repositionnerPdcs();
    }


    /**
     * Définit le délai.
     *
     * @param delai le délai
     */
    public void setDelai(int delai) {
        this.delai = delai;
    }

    /**
     * Définit l'écart.
     *
     * @param ecart l'écart
     */
    public void setEcart(int ecart) {
        this.ecart = ecart;
    }

    /**
     * Retourne le delai.
     *
     * @return le delai
     */
    public int getDelai() {
        return this.delai;
    }

    /**
     * Retourne l'ecart.
     *
     * @return l'écart
     */
    public int getEcart() {
        return this.ecart;
    }
}
