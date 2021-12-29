package domain;

/**
 * Objet bean permettant de manipuler/r�cup�rer les informations d'un utilisateur.
 */
public class User {

	// ATTRIBUTS
	
	private Long id;
	private String username;
	private String name;
	private String surname;
	private String password;
    private String role;
	
	// CONSTRUCTEURS
	
	public User() {
		// rien
	}
	
	// REQUETES
	
	/**
	 * L'identifiant d'un utilisateur.
	 */
	public Long getId() {
		return this.id;
	}
	
	/**
	 * Nom d'utilisateur.
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * Le pr�nom de l'utilisateur.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Le nom de l'utilisateur.
	 */
	public String getSurname() {
		return this.surname;
	}
	
	/**
	 * Le mot de passe de l'utilisateur.
	 */
	public String getPassword() {
		return this.password;
	}

    /**
     * Le rôle de l'utilisateur.
	 */
	public String getRole() {
		return this.role;
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
	 * Fixe le nom d'utilisateur � username.
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Fixe le pr�nom de l'utilisateur � name.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Fixe le nom de l'utilisateur � surname.
	 * @param surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	/**
	 * Fixe le mot de passe de l'utilisateur � password.
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

     /**
     *  Fixe le rôle de l'utilisateur.
	 */
	public String getRole(String role) {
		this.role = role;
	}
}
