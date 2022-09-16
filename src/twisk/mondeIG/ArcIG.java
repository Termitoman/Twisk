package twisk.mondeIG;

/**
 * La classe Arc.
 */
public class ArcIG {
    private final PointDeControleIG pt1;
    private final PointDeControleIG pt2;
    private boolean estSelectionnee;

    /**
     * Instancie une nouvelle Arc.
     *
     * @param pt1 Le point de contrôle depuis lequel l'arc pars
     * @param pt2 Le point de contrôle vers lequel l'arc pointe
     */
    public ArcIG(PointDeControleIG pt1, PointDeControleIG pt2) {
        this.pt1 = pt1;
        this.pt2 = pt2;
        this.estSelectionnee = false;
    }

    /**
     * Retourne le pt1.
     *
     * @return le pt1
     */
    public PointDeControleIG getPt1() {
        return pt1;
    }

    /**
     * Retourne le pt2.
     *
     * @return le pt2
     */
    public PointDeControleIG getPt2() {
        return pt2;
    }

    /**
     * Renvoie un booléen qui indique si l'arc est reliée à l'atape donnée ou non.
     *
     * @param e l'étape
     * @return le booléen
     */
    public boolean estRelieAEtape(EtapeIG e) {
        for (PointDeControleIG pt : e)
            if (getPt1().getId().equals(pt.getId()) || getPt2().getId().equals(pt.getId()))
                return true;
        return false;
    }

    /**
     * Renvoie un booléen qui indique si l'arc est sélectionné.
     *
     * @return le booléen
     */
    public boolean estSelectionnee() {
        return estSelectionnee;
    }

    /**
     * Sélectionne l'arc si il ne l'est pas déjà ou déselectionne celui-ci si il est déjà sélectionné
     */
    public void inverseSelectionnee() {
        estSelectionnee = !estSelectionnee;
    }
}
