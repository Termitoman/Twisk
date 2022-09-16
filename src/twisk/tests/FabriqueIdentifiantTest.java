package twisk.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.outils.FabriqueIdentifiant;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Le test de la classe Fabrique identifiant.
 */
class FabriqueIdentifiantTest {
    private FabriqueIdentifiant fabID;

    /**
     * Initialisation des données.
     */
    @BeforeEach
    void setUp() {
        fabID = FabriqueIdentifiant.getInstance();
    }

    /**
     * Test de la fonction getIdentifiantEtape.
     */
    @Test
    void getIdentifiantEtape() {
        assertEquals(fabID.getIdentifiantEtape(), "0");
        assertEquals(fabID.getIdentifiantEtape(), "1");
        fabID.getIdentifiantEtape();
        fabID.getIdentifiantEtape();
        assertEquals(fabID.getIdentifiantEtape(), "4");
    }
}