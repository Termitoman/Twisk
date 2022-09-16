package twisk.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.mondeIG.ActiviteIG;
import twisk.mondeIG.PointDeControleIG;
import twisk.outils.FabriqueIdentifiant;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Le test de la classe PointDeControleIG.
 */
class PointDeControleIGTest {
    private PointDeControleIG p1;
    private PointDeControleIG p2;
    private ActiviteIG activiteIG;

    /**
     * Initialisation des données.
     */
    @BeforeEach
    void setUp() {
        FabriqueIdentifiant fB = FabriqueIdentifiant.getInstance();
        activiteIG = new ActiviteIG("test", "0");
        p1 = new PointDeControleIG(fB.getIDPointDeControle(), activiteIG);
        p2 = new PointDeControleIG(fB.getIDPointDeControle(), activiteIG);
    }

    /**
     * Test de la fonction changePos.
     */
    @Test
    void changePos() {
        p1.changePos(0, 0);
        assertEquals(p1.getPosX(), 0);
        assertEquals(p1.getPosY(), 0);
        p2.changePos(752, 456);
        assertEquals(p2.getPosX(), 752);
        assertEquals(p2.getPosY(), 456);
    }
}