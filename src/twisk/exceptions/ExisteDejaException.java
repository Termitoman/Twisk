package twisk.exceptions;

/**
 * La classe Existe deja exception.
 */
public class ExisteDejaException extends TwiskException {
    /**
     * Instancie une nouvelle exception Existe deja exception.
     *
     * @param message le message d'erreur
     */
    public ExisteDejaException(String message) {
        super(message);
    }
}
