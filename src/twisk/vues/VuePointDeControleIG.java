package twisk.vues;

import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import twisk.designPattern.Observateur;
import twisk.exceptions.DepartSurArriveeException;
import twisk.exceptions.ExisteDejaException;
import twisk.exceptions.MemeEtapeException;
import twisk.exceptions.TwiskException;
import twisk.mondeIG.MondeIG;
import twisk.mondeIG.PointDeControleIG;
import twisk.outils.TailleComposants;

/**
 * La classe VuePointDeControleIG.
 */
public class VuePointDeControleIG extends Circle implements Observateur {
    private final MondeIG monde;
    private final PointDeControleIG pointDeControleIG;

    /**
     * Instancie une nouvelle VuePointDeControleIG.
     *
     * @param monde             le monde
     * @param pointDeControleIG le point de controle
     */
    public VuePointDeControleIG(MondeIG monde, PointDeControleIG pointDeControleIG) {
        this.monde = monde;
        monde.ajouterObservateur(this);
        this.pointDeControleIG = pointDeControleIG;
        TailleComposants tailleComposants = TailleComposants.getInstance();
        this.setRadius(tailleComposants.getRayonPtsDeCtrl());
        this.setFill(Color.DEEPSKYBLUE);
        this.setOnMouseClicked(ActionEvent -> {
            try {
                monde.cliqueSurPDC(pointDeControleIG);
            } catch (MemeEtapeException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("MemeEtapeException");
                alert.setContentText(e.getMessage());
                alert.show();
                PauseTransition fermetureAutomatique = new PauseTransition(Duration.seconds(5));
                fermetureAutomatique.setOnFinished(t -> alert.close());
                fermetureAutomatique.play();
            } catch (ExisteDejaException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("ExisteDejaException");
                alert.setContentText(e.getMessage());
                alert.show();
                PauseTransition fermetureAutomatique = new PauseTransition(Duration.seconds(5));
                fermetureAutomatique.setOnFinished(t -> alert.close());
                fermetureAutomatique.play();
            } catch (DepartSurArriveeException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("DepartSurArriveeException");
                alert.setContentText(e.getMessage());
                alert.show();
                PauseTransition fermetureAutomatique = new PauseTransition(Duration.seconds(5));
                fermetureAutomatique.setOnFinished(t -> alert.close());
                fermetureAutomatique.play();
            } catch (TwiskException e) { //Nous ne rentrerons jamais dans cette clause mais elle est nécessaire quand même
                e.printStackTrace();
            }
        });
        this.setCenterX(pointDeControleIG.getPosX());
        this.setCenterY(pointDeControleIG.getPosY());

    }

    @Override
    public void reagir() {
    }
}
