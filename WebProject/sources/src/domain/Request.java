package domain;

/**
 * Objet bean permettant de manipuler/r�cup�rer les informations d'un utilisateur.
 */
public class Request {

	// ATTRIBUTS
	
	private Long id;
	private Long userId;
    private Long resourceId;
	private String state;
	private String description;
	
	// CONSTRUCTEURS
	
	public Request() {
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
		return this.id;
	}

	/**
	 * L'identifiant de la ressource impliquée.
	 */
	public Long getresourceId() {
		return this.id;
	}
	
	/**
	 * L'etat de la demande.
	 */
	public String getState() {
		return this.state;
	}

    /**
     * La description de la demande.
	 */
	public String getDescription() {
		return this.description;
	}
	
	// COMMANDES
	
	/**
	 * Fixe l'identifiant d'un utilisateur � id.
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Fixe l'identifiant d'un receptionneur.
	 * @param id
	 */
	public void setUserId(Long id) {
		this.userId = id;
	}

	/**
	 * Fixe l'identifiant de la ressource associée.
	 * @param id
	 */
	public void setresourceId(Long id) {
		this.resourceId = id;
	}
	
	/**
	 * Fixe l'etat de la requete.
	 * @param state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	*  Fixe la description de la requete.
	*/
	public String getDescription(String description) {
		this.description = description;
	}
}
