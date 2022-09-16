package twisk.vues;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.util.Duration;
import twisk.designPattern.Observateur;
import twisk.exceptions.ParamIncorrectsException;
import twisk.mondeIG.MondeIG;

import java.util.Optional;

/**
 * La classe VueMenu.
 */
public class VueMenu extends MenuBar implements Observateur {
    private final MondeIG monde;
    private final Menu edition;
    private final Menu param;

    /**
     * Instancie une nouvelle VueMenu.
     *
     * @param monde le monde
     */
    public VueMenu(MondeIG monde) {
        this.monde = monde;
        this.monde.ajouterObservateur(this);
        //On s'occupe du menu Fichier
        Menu fichier = new Menu("Fichier");
        MenuItem quitter = new MenuItem("Quitter");
        quitter.setOnAction(t -> Platform.exit());
        fichier.getItems().add(quitter);

        //On s'occupe du menu Edition
        edition = new Menu("Edition");
        MenuItem suppr = new MenuItem("Supprimer la sélection");
        suppr.setOnAction(t -> monde.supprimerLaSelection());
        MenuItem renom = new MenuItem("Renommer la sélection");
        renom.setOnAction(t -> afficherFenetreDemandeNom());
        renom.setDisable(true);
        MenuItem effacer = new MenuItem("Effacer la sélection");
        effacer.setOnAction(t -> monde.effacerLaSelection());
        edition.getItems().addAll(suppr, renom, effacer);

        //On s'occupe du menu Monde
        Menu menuMonde = new Menu("Monde");
        MenuItem entree = new MenuItem("Entrée");
        entree.setOnAction(t -> monde.gestionEntree());
        MenuItem sortie = new MenuItem("Sortie");
        sortie.setOnAction(t -> monde.gestionSorties());
        menuMonde.getItems().addAll(entree, sortie);

        //On s'occupe du menu Paramètres
        param = new Menu("Paramètres");
        MenuItem delai = new MenuItem("Changer le délai");
        delai.setOnAction(t -> afficherFenetresDemandeDelai());
        MenuItem ecart = new MenuItem("Changer l'écart");
        ecart.setOnAction(t -> afficherFenetresDemandeEcart());
        param.getItems().addAll(delai, ecart);
        param.setDisable(true);

        //On s'occupe du menu Style
        Menu style = new Menu("Style");
        MenuItem classique = new MenuItem("Classique");
        classique.setOnAction(t -> monde.setStyle("classique"));
        MenuItem nuit = new MenuItem("Nuit");
        nuit.setOnAction(t -> monde.setStyle("nuit"));
        style.getItems().addAll(classique, nuit);

        getMenus().addAll(fichier, edition, menuMonde, param, style);
        this.setStyle("-fx-background-color : lightgrey");
    }

    private void afficherFenetreDemandeNom() {
        TextInputDialog textInputDialog = new TextInputDialog("Etape");
        textInputDialog.setTitle("Renommer la sélection");
        textInputDialog.setHeaderText("Entrer le nouveau nom de l'etape sélectionnée");
        textInputDialog.setContentText("Nom : ");
        Optional<String> resultat = textInputDialog.showAndWait();
        resultat.ifPresent(monde::renommerEtapeSelectionnee);
    }

    private void afficherFenetresDemandeDelai() {
        TextInputDialog textInputDialog = new TextInputDialog("10");
        textInputDialog.setTitle("Donnez un délai");
        textInputDialog.setHeaderText("Entrer le nouveau délai de l'etape sélectionnée (>=0 et >=écart)");
        textInputDialog.setContentText("Délai : ");

        Optional<String> resultat = textInputDialog.showAndWait();
        resultat.ifPresent(s -> {
            try {
                monde.changerDelaiEtapeSelectionnee(s);
            } catch (ParamIncorrectsException e) {
                afficherFenetreErreur(e);
            }
        });
    }

    private void afficherFenetresDemandeEcart() {
        TextInputDialog textInputDialog = new TextInputDialog("5");
        textInputDialog.setTitle("Donnez un écart");
        textInputDialog.setHeaderText("Entrer le nouvel écart de l'etape sélectionnée (<=délai et >=0)");
        textInputDialog.setContentText("Ecart : ");

        Optional<String> resultat = textInputDialog.showAndWait();
        resultat.ifPresent(s -> {
            try {
                monde.changerEcartEtapeSelectionnee(s);
            } catch (ParamIncorrectsException e) {
                afficherFenetreErreur(e);
            }
        });
    }

    private void afficherFenetreErreur(ParamIncorrectsException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("ParamIncorrectException");
        alert.setContentText(e.getMessage());
        alert.show();
        PauseTransition fermetureAutomatique = new PauseTransition(Duration.seconds(5));
        fermetureAutomatique.setOnFinished(t -> alert.close());
        fermetureAutomatique.play();
    }

    @Override
    public void reagir() {
        edition.getItems().get(1).setDisable(monde.nbEtapesSelectionnees() != 1);
        param.setDisable(monde.nbEtapesSelectionnees() != 1);
    }
}
