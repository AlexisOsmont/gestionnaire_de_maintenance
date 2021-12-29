package dao;

/**
 * Classe d'exception permettant de sp�cifier une exception li� � la configuration
 * 		d'acc�s � la base de donn�es
 */
public class DAOConfigurationException extends RuntimeException {
   
	// ATTRIBUTS 
	
	private static final long serialVersionUID = 1047828793942551100L;

	// CONSTRUCTEURS
	
    public DAOConfigurationException(String message) {
        super(message);
    }

    public DAOConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOConfigurationException(Throwable cause) {
        super(cause);
    }
}
