package twisk.outils;

/**
 * Le singleton Fabrique identifiant.
 */
public class FabriqueIdentifiant {
    private int noEtape;
    private int ptsDeCtrlID;

    private FabriqueIdentifiant() {
        noEtape = 0;
        ptsDeCtrlID = 0;
    }

    private static final FabriqueIdentifiant instance = new FabriqueIdentifiant();

    /**
     * Donne l'instance deFabriqueIdentifiant.
     *
     * @return l'instance
     */
    public static FabriqueIdentifiant getInstance() {
        return instance;
    }

    /**
     * Retourne identifiant etape.
     *
     * @return un identifiant unique destiné aux étapes
     */
    public String getIdentifiantEtape() {
        noEtape++; //On passe au numéro de l'étape suivante
        int temp = noEtape - 1;
        return temp + ""; //On retourne le numéro de l'étape actuel
    }

    /**
     * Retourne id point de controle.
     *
     * @return un identifiant unique destiné aux étapes
     */
    public String getIDPointDeControle() {
        ptsDeCtrlID++;
        int temp = ptsDeCtrlID - 1;
        return temp + "";
    }

    /**
     * Remet à 0 indetifiant étape.
     */
    public void resetIdEtape() {
        noEtape = 0;
    }
}
