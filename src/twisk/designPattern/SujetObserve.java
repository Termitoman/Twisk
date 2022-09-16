package twisk.designPattern;

import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * La classe Sujet observé.
 */
public class SujetObserve {
    private final ArrayList<Observateur> observateurs;

    /**
     * Instancie un nouveau Sujet observé.
     */
    public SujetObserve() {
        observateurs = new ArrayList<>(10);
    }

    /**
     * Ajouter observateur.
     * Procédure qui ajoute un observateur sur le sujet observé.
     *
     * @param v l'observateur à rajouter
     */
    public void ajouterObservateur(Observateur v) {
        observateurs.add(v);
    }

    /**
     * Notifier observateur.
     * Procédure qui appelle la fonction régir des observateurs qui observent le sujet.
     */
    public void notifierObservateur() {
        IntStream.range(0, observateurs.size()).forEach(i -> observateurs.get(i).reagir());
    }
}
