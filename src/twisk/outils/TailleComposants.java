package twisk.outils;

/**
 * Le singleton Taille composants.
 */
public class TailleComposants {
    private final int largeurActivite;
    private final int hauteurActivite;
    private final int tailleBouton;
    private final int largeurFenetre;
    private final int hauteurFenetre;
    private final int rayonPtsDeCtrl;
    private final int tailleIconeEntreeSortie;

    private TailleComposants() {
        largeurActivite = 110;
        hauteurActivite = 55;
        tailleBouton = 40;
        largeurFenetre = 1000;
        hauteurFenetre = 800;
        rayonPtsDeCtrl = 5;
        tailleIconeEntreeSortie = 15;
    }

    private static final TailleComposants instance = new TailleComposants();

    /**
     * Donne l'instance de TailleComposants.
     *
     * @return l'instance
     */
    public static TailleComposants getInstance() {
        return instance;
    }

    /**
     * Retourne largeur activite.
     *
     * @return la largeur d'une activité
     */
    public int getLargeurActivite() {
        return largeurActivite;
    }

    /**
     * Retourne hauteur activite.
     *
     * @return la hauteur d'une activité
     */
    public int getHauteurActivite() {
        return hauteurActivite;
    }

    /**
     * Retourne taille bouton.
     *
     * @return la taille du bouton "+"
     */
    public int getTailleBouton() {
        return tailleBouton;
    }

    /**
     * Retourne largeur fenetre.
     *
     * @return la largeur de la fenêtre
     */
    public int getLargeurFenetre() {
        return largeurFenetre;
    }

    /**
     * Retourne hauteur fenetre.
     *
     * @return la hauteur de la fenêtre
     */
    public int getHauteurFenetre() {
        return hauteurFenetre;
    }

    /**
     * Retourne rayon pts de ctrl.
     *
     * @return le rayon d'un point de contrôle
     */
    public int getRayonPtsDeCtrl() {
        return rayonPtsDeCtrl;
    }

    /**
     * Retourne taille icone entree sortie.
     *
     * @return la taille des icônes d'entrées et de sortie
     */
    public int getTailleIconeEntreeSortie() {
        return tailleIconeEntreeSortie;
    }
}
