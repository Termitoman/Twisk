package twisk.vues;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import twisk.designPattern.Observateur;
import twisk.mondeIG.MondeIG;
import twisk.outils.TailleComposants;

/**
 * La classe VueOutils.
 */
public class VueOutils extends TilePane implements Observateur {

    private final MondeIG monde;
    private final Button button;

    /**
     * Instancie une nouvelle VueOutils.
     *
     * @param monde le monde
     */
    public VueOutils(MondeIG monde) {
        this.monde = monde;
        monde.ajouterObservateur(this);
        button = new Button();
        button.setOnAction(t -> monde.ajouter("Activite"));
        TailleComposants tailleComposants = TailleComposants.getInstance();
        Image image = new Image(getClass().getResourceAsStream("/twisk/ressources/images/plus.png"), tailleComposants.getTailleBouton(), tailleComposants.getTailleBouton(), true, true);
        ImageView imageView = new ImageView(image);
        button.setGraphic(imageView);
        button.setStyle("-fx-background-color:transparent; -fx-focus-color: transparent;");
        Tooltip tooltip = new Tooltip();
        button.setTooltip(tooltip);
        getChildren().add(button);
    }

    @Override
    public void reagir() {
    }
}
