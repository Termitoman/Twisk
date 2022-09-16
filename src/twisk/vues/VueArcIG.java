package twisk.vues;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.transform.Rotate;
import twisk.designPattern.Observateur;
import twisk.mondeIG.ArcIG;
import twisk.mondeIG.MondeIG;

/**
 * The type Vue arc ig.
 */
public class VueArcIG extends Pane implements Observateur {
    private final MondeIG monde;
    private final ArcIG arcIG;
    private final Line line;
    private final Polyline fleche;   //On initialise la fleche pour pouvoir écrire la fonction setFill

    /**
     * Instancie une nouvelle VueArcIg.
     *
     * @param monde le monde
     * @param arcIG l'arc qui correspond à la vue
     */
    public VueArcIG(MondeIG monde, ArcIG arcIG) {
        this.monde = monde;
        monde.ajouterObservateur(this);

        this.arcIG = arcIG;
        fleche = new Polyline();

        double ptDX = arcIG.getPt1().getPosX(), ptDy = arcIG.getPt1().getPosY(), ptAX = arcIG.getPt2().getPosX(), ptAY = arcIG.getPt2().getPosY(); //Pour la lisibilité

        //On crée la flèche
        fleche.getPoints().addAll(
                ptAX, ptAY,
                ptAX + 20.0, ptAY + 5.0,
                ptAX + 20.0, ptAY - 5.0,
                ptAX, ptAY);
        fleche.setFill(Color.BLUE);

        //On calcule l'angle de la flèche

        //On calcule le delta l'équation de la droite formée par les 2 points (y = delta*x + b)
        double delta = (ptDy - ptAY) / (ptDX - ptAX);

        //On calcule la tangente de l'angle entre cette droite et la droite horizontale passant par le point d'arrivée, le calcul étant valeurAbsolue de delta à cause de la droite horizontale
        double tangenteAngle = Math.abs(delta);

        //On en déduit l'angle
        double angleRad = Math.atan(tangenteAngle);
        double angle = Math.toDegrees(angleRad);

        //On change l'angle selon la position du point de départ par rapport au point d'arrivée
        if (ptDX >= ptAX && ptDy < ptAY)
            angle = -angle;
        else if (ptDX < ptAX && ptDy >= ptAY)
            angle = 180 - angle;
        else if (ptDX < ptAX && ptDy < ptAY)
            angle = -(180 - angle);


        //On s'occupe maintenant de la rotation
        Rotate rotate = new Rotate();
        rotate.setAngle(angle);
        rotate.setPivotX(ptAX);
        rotate.setPivotY(ptAY);

        fleche.getTransforms().addAll(rotate);

        line = new Line(ptDX, ptDy, ptAX, ptAY);
        line.setStroke(Color.BLUE);
        line.setStrokeWidth(3);

        line.setOnMouseClicked(t -> monde.arcSelectione(arcIG));
        fleche.setOnMouseClicked(t -> monde.arcSelectione(arcIG));

        getChildren().addAll(line, fleche);
        this.setPickOnBounds(false);
    }

    @Override
    public void reagir() {
    }

    /**
     * Définit la coleur de la vue.
     *
     * @param color la coleur
     */
    public void setColor(Color color) {
        fleche.setFill(color);
        fleche.setStroke(color);
        line.setStroke(color);
    }
}
