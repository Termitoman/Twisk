package twisk.mondeIG;

/**
 * La classe Point de controle.
 */
public class PointDeControleIG {
    private int posX;
    private int posY;
    private final String id;
    private final EtapeIG etape;

    /**
     * Instancie un nouveau Point de controle.
     *
     * @param id    l'identifiant
     * @param etape l'étape à laquelle le point appartient
     */
    public PointDeControleIG(String id, EtapeIG etape) {
        this.id = id;
        this.etape = etape;
    }

    /**
     * Change la position du pdc.
     *
     * @param posX la position en x
     * @param posY la position en  y
     */
    public void changePos(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Retourne pos x.
     *
     * @return la position en x
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Retourne pos y.
     *
     * @return la position en y
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Retourne id.
     *
     * @return l'identifiant
     */
    public String getId() {
        return id;
    }

    /**
     * Retourne etape.
     *
     * @return l'étape à laquelle appartient le pdc
     */
    public EtapeIG getEtape() {
        return etape;
    }
}
