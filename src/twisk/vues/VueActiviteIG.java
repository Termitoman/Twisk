package twisk.vues;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;

/**
 * La classe VueActiviteIG.
 */
public class VueActiviteIG extends VueEtapeIG {
    private final HBox hBox;

    /**
     * Instancie une nouvelle VueActiviteIG.
     *
     * @param monde le monde
     * @param etape l'étape(activitée)
     */
    public VueActiviteIG(MondeIG monde, EtapeIG etape) {
        super(monde, etape);
        hBox = new HBox();
        hBox.setStyle("-fx-background-color: #cc99ff; -fx-border-color: #990099; -fx-border-width: 3px; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
        hBox.getChildren().add(new Label("Delai : " + etape.getDelai() + "\nEcart : " + etape.getEcart()));
        this.getChildren().add(hBox);
    }

    @Override
    public void reagir() {
    }
}
