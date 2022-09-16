package twisk.exceptions;

/**
 * La classe abstraite Twisk exception.
 */
public abstract class TwiskException extends Exception {
    /**
     * Instancie une nouvelle exception Twisk exception exception.
     *
     * @param message le message d'erreur
     */
    public TwiskException(String message) {
        super(message);
    }
}
