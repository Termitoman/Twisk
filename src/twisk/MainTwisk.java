package twisk;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import twisk.mondeIG.MondeIG;
import twisk.outils.TailleComposants;
import twisk.vues.VueMenu;
import twisk.vues.VueMondeIG;
import twisk.vues.VueOutils;

/**
 * La classe MainTwisk.
 */
public class MainTwisk extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        MondeIG monde = new MondeIG();
        BorderPane root = new BorderPane();
        VueOutils vueOutils = new VueOutils(monde);
        root.setBottom(vueOutils);
        VueMondeIG vueMondeIG = new VueMondeIG(monde);
        root.setCenter(vueMondeIG);
        VueMenu vueMenu = new VueMenu(monde);
        root.setTop(vueMenu);
        root.setStyle("-fx-background-color : white");
        TailleComposants tailleComposants = TailleComposants.getInstance();
        primaryStage.setScene(new Scene(root, tailleComposants.getLargeurFenetre(), tailleComposants.getHauteurFenetre()));
        primaryStage.setTitle("Twisk");
        primaryStage.show();
    }

    /**
     * Le point d'entrée de l'application.
     *
     * @param args les arguments donnés
     */
    public static void main(String[] args) {
        launch(args);
    }
}
