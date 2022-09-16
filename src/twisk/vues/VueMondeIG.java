package twisk.vues;

import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import twisk.designPattern.Observateur;
import twisk.mondeIG.ArcIG;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
import twisk.mondeIG.PointDeControleIG;

import java.util.Iterator;

/**
 * La classe VueMondeIG.
 */
public class VueMondeIG extends Pane implements Observateur {

    private final MondeIG monde;

    /**
     * Instancie une nouvelle VueMondeIG;
     *
     * @param monde le monde
     */
    public VueMondeIG(MondeIG monde) {
        this.monde = monde;
        monde.ajouterObservateur(this);
        creerEtapesEtPdc();
        //On gère le drag'n'drop
        this.setOnDragDropped(this::dragDropped);
        this.setOnDragOver(this::dragOver);
    }

    @Override
    public void reagir() {
        getChildren().clear();
        gererStyle();
        for (Iterator<ArcIG> it = monde.iterateurArc(); it.hasNext(); ) {
            ArcIG arc = it.next();
            VueArcIG vueArcIG = new VueArcIG(monde, arc);
            if (arc.estSelectionnee()) {
                vueArcIG.setColor(Color.RED);
            }
            getChildren().add(vueArcIG);
        }
        creerEtapesEtPdc();
    }

    private void gererStyle() {
        switch (monde.getStyle()) {
            case "classique":
                this.getParent().setStyle("-fx-background-color : white");
                break;

            case "nuit":
                this.getParent().setStyle("-fx-background-color : black");
                break;
        }
    }

    /**
     * Creer les vues des etapes et des pdcs.
     */
    public void creerEtapesEtPdc() {
        for (Iterator<EtapeIG> it = monde.iterateurEtape(); it.hasNext(); ) {
            EtapeIG etape = it.next();
            VueActiviteIG vueActiviteIG = new VueActiviteIG(monde, etape);
            if (etape.estSelectionnee()) {
                vueActiviteIG.setStyle("-fx-border-color : red; -fx-border-width : 2px");
            }
            getChildren().add(vueActiviteIG);
            for (PointDeControleIG p : etape) {
                VuePointDeControleIG vuePointDeControleIG = new VuePointDeControleIG(monde, p);
                getChildren().add(vuePointDeControleIG);
            }
        }
    }

    private void dragDropped(DragEvent dragEvent) {
        Dragboard db = dragEvent.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            String nodeId = db.getString();
            VueEtapeIG vueEtapeIG = (VueEtapeIG) this.lookup("#" + nodeId);
            if (vueEtapeIG != null) {
                int dgX = (int) dragEvent.getX(), dgY = (int) dragEvent.getY();
                vueEtapeIG.relocate(dgX, dgY);
                monde.deplacerEtape(nodeId, dgX, dgY);
                success = true;
            }
        }
        dragEvent.setDropCompleted(success);
        monde.notifierObservateur();
        dragEvent.consume();
    }

    private void dragOver(DragEvent dragEvent) {
        if (dragEvent.getDragboard().hasString()) {
            dragEvent.acceptTransferModes(TransferMode.MOVE);
        }
        dragEvent.consume();
    }
}
