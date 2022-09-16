package twisk.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.exceptions.DepartSurArriveeException;
import twisk.exceptions.ExisteDejaException;
import twisk.exceptions.MemeEtapeException;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
import twisk.outils.FabriqueIdentifiant;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Le test de la classe MondeIG.
 */
class MondeIGTest {
    private MondeIG mondeIG;
    private final FabriqueIdentifiant fabriqueIdentifiant = FabriqueIdentifiant.getInstance();

    /**
     * Initialisation des données.
     */
    @BeforeEach
    void setUp() {
        fabriqueIdentifiant.resetIdEtape();
        mondeIG = new MondeIG();
    }

    /**
     * Test de la fonction ajouter.
     */
    @Test
    void ajouter() {
        assertEquals(mondeIG.getEtape("0").getNom(), "Activite0");
        assertEquals(mondeIG.nbEtapes(), 1);
        mondeIG.ajouter("Activite");
        assertEquals(mondeIG.getEtape("1").getNom(), "Activite1");
        assertEquals(mondeIG.nbEtapes(), 2);
        mondeIG.ajouter("Etape");
        assertThrows(NullPointerException.class, () -> mondeIG.getEtape("2").getNom());
        assertEquals(mondeIG.nbEtapes(), 2);
    }

    /**
     * Test de la fonction iterator.
     */
    @Test
    void iterator() {
        mondeIG.ajouter("Activite");
        int cpt = 0;
        for (Iterator<EtapeIG> it = mondeIG.iterateurEtape(); it.hasNext(); ) {
            EtapeIG e = it.next();
            assertEquals(e.getNom(), "Activite" + cpt);
            cpt++;
        }
        assertEquals(cpt, 2);

        mondeIG.etapeSelectionee(mondeIG.getEtape("1"));
        mondeIG.ajouter("Activite");
        mondeIG.supprimerLaSelection();
        cpt = 0;
        for (Iterator<EtapeIG> it = mondeIG.iterateurEtape(); it.hasNext(); ) {
            EtapeIG e = it.next();
            assertEquals(e.getNom(), "Activite" + cpt);
            cpt += 2;
        }
        assertEquals(cpt, 4);
    }

    /**
     * Test de la 2eme foncion ajouter.
     */
    @Test
    void testAjouter() {
        mondeIG.ajouter("Activite");

        //On teste le fait de faire un arc avec deux points dans la même étape
        assertThrows(MemeEtapeException.class, () -> mondeIG.ajouter(mondeIG.getEtape("0").getPdcI(0), mondeIG.getEtape("0").getPdcI(0)));
        assertEquals(mondeIG.getNbArcs(), 0);   //On vérifie qu'aucun arc n'ait été créé
        assertThrows(MemeEtapeException.class, () -> mondeIG.ajouter(mondeIG.getEtape("0").getPdcI(0), mondeIG.getEtape("0").getPdcI(1)));
        assertEquals(mondeIG.getNbArcs(), 0);   //On vérifie qu'aucun arc n'ait été créé

        //On teste un arc correct
        assertDoesNotThrow(() -> mondeIG.ajouter(mondeIG.getEtape("0").getPdcI(0), mondeIG.getEtape("1").getPdcI(0)));
        assertEquals(mondeIG.getNbArcs(), 1);   //On vérifie qu'un arc ait été créé

        //On teste le fait de vouloir construire un arc sur un autre arc
        assertThrows(ExisteDejaException.class, () -> mondeIG.ajouter(mondeIG.getEtape("0").getPdcI(0), mondeIG.getEtape("1").getPdcI(0)));
        assertEquals(mondeIG.getNbArcs(), 1);   //On vérifie qu'aucun arc n'ait été créé


        //On teste de construire des arcs pour avoir des pdc avec plusieurs départs ou entrées
        assertDoesNotThrow(() -> mondeIG.ajouter(mondeIG.getEtape("0").getPdcI(0), mondeIG.getEtape("1").getPdcI(3)));
        assertEquals(mondeIG.getNbArcs(), 2);   //On vérifie qu'un arc ait été créé
        assertDoesNotThrow(() -> mondeIG.ajouter(mondeIG.getEtape("0").getPdcI(2), mondeIG.getEtape("1").getPdcI(3)));
        assertEquals(mondeIG.getNbArcs(), 3);   //On vérifie qu'un arc ait été créé

        //On teste le fait de construire un arc ayant comme point de départ le point d'arrivée d'un autre arc
        assertThrows(DepartSurArriveeException.class, () -> mondeIG.ajouter(mondeIG.getEtape("1").getPdcI(3), mondeIG.getEtape("0").getPdcI(3)));
        assertEquals(mondeIG.getNbArcs(), 3);   //On vérifie qu'aucun arc n'ait été créé
        assertThrows(DepartSurArriveeException.class, () -> mondeIG.ajouter(mondeIG.getEtape("1").getPdcI(0), mondeIG.getEtape("0").getPdcI(0)));
        assertEquals(mondeIG.getNbArcs(), 3);   //On vérifie qu'aucun arc n'ait été créé
    }

    /**
     * Test de la fonction supprimerLaSelection.
     */
    @Test
    void supprimerLaSelection() {
        mondeIG.ajouter("Activite");
        mondeIG.ajouter("Activite");
        assertEquals(mondeIG.nbEtapes(), 3);

        mondeIG.etapeSelectionee(mondeIG.getEtape("1")); //On sélectionne l'étape que l'on veut supprimer
        mondeIG.supprimerLaSelection();
        assertEquals(mondeIG.nbEtapes(), 2);
        assertThrows(NullPointerException.class, () -> mondeIG.getEtape("1").getNom());
        assertEquals(mondeIG.getEtape("0").getIdentifiant(), "0");  //On vérifie que l'étape 0 existe toujours
        assertEquals(mondeIG.getEtape("2").getIdentifiant(), "2");  //On vérifie que l'étape 2 existe toujours

        mondeIG.etapeSelectionee(mondeIG.getEtape("0")); //On sélectionne l'étape que l'on veut supprimer
        mondeIG.supprimerLaSelection();
        assertEquals(mondeIG.nbEtapes(), 1);
        assertThrows(NullPointerException.class, () -> mondeIG.getEtape("0").getNom());
        assertEquals(mondeIG.getEtape("2").getIdentifiant(), "2");  //On vérifie que l'étape 2 existe toujours

        mondeIG.etapeSelectionee(mondeIG.getEtape("2")); //On sélectionne l'étape que l'on veut supprimer
        mondeIG.supprimerLaSelection();
        assertEquals(mondeIG.nbEtapes(), 0);
        assertThrows(NullPointerException.class, () -> mondeIG.getEtape("2").getNom());
    }
}