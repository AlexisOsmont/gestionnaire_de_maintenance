package beans;


/**
 * Objet bean permettant de manipuler/r�cup�rer les informations d'un utilisateur.
 */
public class Ressource {

	// ATTRIBUTS
	
	private Long id;
	private Long userId;
	private String localisation;
	private String description;
	private String nom;
	
	// CONSTRUCTEURS
	
	public Ressource() {
		// rien
	}
	
	// REQUETES
	
	/**
	 * L'identifiant de la requete.
	 */
	public Long getId() {
		return this.id;
	}
	
/**
	 * L'identifiant de l'utilisateur receptionneur.
	 */
	public Long getUserId() {
		return this.userId;
	}

	/**
	 * La localisation de la demande.
	 */
	public String getLocalisation() {
		return this.localisation;
	}

    /**
     * La description de la demande.
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
     * La description de la demande.
	 */
	public String getNom() {
		return this.nom;
	}
	
	// COMMANDES
	
	/**
	 * Fixe l'identifiant d'un utilisateur � id.
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	/**
	 * Fixe l'identifiant d'un receptionneur.
	 * @param id
	 */
	public void setUserId(Long id) {
		this.userId = id;
	}
	
	/**
	 * Fixe l'etat de la requete.
	 * @param localisation
	 */
	public void setLocalisation(String localisation) {
		this.localisation = localisation;
	}

	/**
	*  Fixe la description de la requete.
	*/
	public void setDescription(String description) {
		this.description = description;
	}
}