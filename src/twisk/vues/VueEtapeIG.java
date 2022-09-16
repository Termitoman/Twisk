package twisk.vues;

import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import twisk.designPattern.Observateur;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
import twisk.outils.TailleComposants;

/**
 * La classe Vue etape ig.
 */
public abstract class VueEtapeIG extends VBox implements Observateur {
    /**
     * Le Monde.
     */
    protected final MondeIG monde;
    /**
     * L'Etape.
     */
    protected final EtapeIG etape;
    /**
     * Le Label.
     */
    protected final Label label;

    /**
     * Instancie une nouvelle VueEtapeIG.
     *
     * @param monde le monde
     * @param etape l'etape
     */
    public VueEtapeIG(MondeIG monde, EtapeIG etape) {
        this.monde = monde;
        monde.ajouterObservateur(this);
        this.etape = etape;
        TailleComposants tailleComposants = TailleComposants.getInstance();
        this.setMinSize(tailleComposants.getLargeurActivite(), tailleComposants.getHauteurActivite());
        this.setMaxSize(tailleComposants.getLargeurActivite(), tailleComposants.getHauteurActivite());
        setId(etape.getIdentifiant());
        this.relocate(etape.getPosX(), etape.getPosY());
        label = new Label(etape.getNom());
        label.setTextFill(Color.BLUEVIOLET);
        this.setOnMouseClicked(t -> monde.etapeSelectionee(etape));
        //On gère le drag'n'drop
        this.setOnDragDetected(this::dragDetected);
        this.getChildren().add(label);

        //On gère l'affichage d'icones pour les entrées et sorties
        if (etape.estUneEntree() && etape.estUneSortie()) {
            Image imageES = new Image(getClass().getResourceAsStream("/twisk/ressources/images/entree_&_sortie.png"), tailleComposants.getTailleIconeEntreeSortie(), tailleComposants.getTailleIconeEntreeSortie(), true, true);
            ImageView imageViewES = new ImageView(imageES);
            label.setGraphic(imageViewES);
        } else if (etape.estUneEntree()) {
            Image imageE = new Image(getClass().getResourceAsStream("/twisk/ressources/images/entree.png"), tailleComposants.getTailleIconeEntreeSortie(), tailleComposants.getTailleIconeEntreeSortie(), true, true);
            ImageView imageViewE = new ImageView(imageE);
            label.setGraphic(imageViewE);
        } else if (etape.estUneSortie()) {
            Image imageS = new Image(getClass().getResourceAsStream("/twisk/ressources/images/sortie.png"), tailleComposants.getTailleIconeEntreeSortie(), tailleComposants.getTailleIconeEntreeSortie(), true, true);
            ImageView imageViewS = new ImageView(imageS);
            label.setGraphic(imageViewS);
        }
    }

    private void dragDetected(MouseEvent mouseEvent) {
        Dragboard db = this.startDragAndDrop(TransferMode.MOVE);
        WritableImage snap = this.snapshot(new SnapshotParameters(), null);
        db.setDragView(snap);
        ClipboardContent content = new ClipboardContent();
        content.putString(this.getId());
        db.setContent(content);
        mouseEvent.consume();
    }

    /**
     * Retourne etape.
     *
     * @return l'étape sur laquelle la vue est basée
     */
    public EtapeIG getEtape() {
        return etape;
    }
}
