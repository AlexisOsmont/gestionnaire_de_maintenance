package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import beans.Demande;
import beans.Ressource;

/**
 * Objet permettant d'envoyer des requ�tes � la base donn�es, sur la table User
 * 	Cette table correspondant � l'ensemble des utilisateurs enregistr�s dans la base de donn�e
 * @cons <pre>
 *     $DESC$ UN objet utilisant factory afin d'initialiser les connexions � la base de donn�e
 *     $ARGS$ DAOFactory factory
 *     $PRE$
 *         DAOFactory.dbIsValidate() </pre>
 */
public class RessourceDAO {
	
	// ATTRIBUTS 
	
	// Requ�te qui insert une ligne dans la table User
	private static final String SQL_INSERT 						= String.format("INSERT INTO %s VALUES (?, ?, ?, ?, ?);", DAOFactory.TABLE_RESSOURCE);
	private static final String SQL_DEL 						= String.format("DELETE FROM %s WHERE idSource=?;", DAOFactory.TABLE_RESSOURCE);
	private static final String SQL_FIND						= String.format("SELECT * FROM %s WHERE idManagerMaint=?;", DAOFactory.TABLE_RESSOURCE);

	public static final String FILE_PROP			  = "/dao/dao.properties";
	private static final String PROP_URL			  = "url";
	private static final String PROP_USER			  = "user";
	private static final String PROP_PASSWORD		  = "password";
		
	private Connection connexion;
	private static String url;
	private static String userProp;
	private static String pwdProp;
	
	// COMMANDES
	
	// REQUETES
	
	// COMMANDES

	/**
	 * Commande permettant de rajouter une ressource dans la base de donn�es
	 * @param User user
	 * @throws DAOException
	 */
	public void createRessource(Ressource ressource) throws DAOException {
		try {
			verifyConfiguration();
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			Connection con = DriverManager.getConnection(url, userProp, pwdProp);
			PreparedStatement pst = con.prepareStatement(SQL_INSERT);
			pst.setLong(1,maxId()+1);
			pst.setLong(2,ressource.getUserId());
			pst.setString(3,ressource.getLocalisation());
			pst.setString(4,ressource.getDescription());
			pst.setString(5,ressource.getNom());
			pst.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void suppRessource(long id) throws DAOException {
		try {
			verifyConfiguration();
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			Connection con = DriverManager.getConnection(url, userProp, pwdProp);
			DemandeDAO demande = new DemandeDAO();
			demande.suppDemandesIdSource(id);
			PreparedStatement pst = con.prepareStatement(SQL_DEL);
			pst.setLong(1,id);
			pst.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Récupère l'identifiant du responsable de la ressource d'identifiant id.
	public long findRespon(long id) throws DAOException {
		java.sql.Statement statement = null; //représente requête SQL
		ResultSet resultat = null;
		
		loadDatabase();
		int idRechercher = -1;
		try {
			statement = connexion.createStatement();
			
			// Exécution de la requête
			resultat = statement.executeQuery("SELECT * FROM Ressources WHERE idSource=" + id);
			//String query = "SELECT * FROM Ressources WHERE idManagerMaint=?";

			// Récupération des données
	        while (resultat.next()) {
				idRechercher = resultat.getInt("idManagerMaint");
        }
		} catch(SQLException e) {
			
		}
		return idRechercher;
	}
	
	// Récupère toutes les ressources du responsable d'identifiant id.
	public List<Ressource> recupRessourceRespId(long id) {
		List<Ressource> listRessource = new ArrayList<Ressource>();
		java.sql.Statement statement = null; //représente requête SQL
		ResultSet resultat = null;
		
		loadDatabase();
		
		try {			
			statement = connexion.createStatement();
			
			// Exécution de la requête
			resultat = statement.executeQuery("SELECT * FROM Ressources WHERE idManagerMaint="+id);
			
			// Récupération des données
			while (resultat.next()) {
				int idSource = resultat.getInt("idSource");
				int idManagerMaint = resultat.getInt("idManagerMaint");
				String localisation = resultat.getString("localisation");
				String description = resultat.getString("description");
				String nom = resultat.getString("nom");
				
				Ressource ressource = new Ressource();
				ressource.setId((long) idSource);
				ressource.setUserId((long) idManagerMaint);
				ressource.setLocalisation(localisation);
				ressource.setDescription(description);
				ressource.setNom(nom);

				
				
				listRessource.add(ressource);
			}
		} catch (SQLException e) {
		} finally {
			// Fermeture de la connexion à la base de données
			try {
				if (resultat != null) resultat.close();
				if (statement != null) statement.close();
				if (connexion != null) connexion.close();
			} catch (SQLException ignore) {
			}
		}
		return listRessource;
	}
	
	// Récupère la resource d'identifiant id
	public Ressource recupRessourceId(long id) {
        java.sql.Statement statement = null; //représente requête SQL
        ResultSet resultat = null;

        loadDatabase();

        Ressource ressource = new Ressource();
        try {
            statement = connexion.createStatement();

            // Exécution de la requête
            resultat = statement.executeQuery("SELECT * FROM Ressources WHERE idSource="+id);

            // Récupération des données
            while (resultat.next()) {
                int idSource = resultat.getInt("idSource");
                int idManagerMaint = resultat.getInt("idManagerMaint");
                String localisation = resultat.getString("localisation");
                String description = resultat.getString("description");
                String nom = resultat.getString("nom");


                ressource.setId((long) idSource);
                ressource.setUserId((long) idManagerMaint);
                ressource.setLocalisation(localisation);
                ressource.setDescription(description);
                ressource.setNom(nom);
            }
        } catch (SQLException e) {
        } finally {
            // Fermeture de la connexion à la base de données
            try {
                if (resultat != null) resultat.close();
                if (statement != null) statement.close();
                if (connexion != null) connexion.close();
            } catch (SQLException ignore) {
            }
        }
        return ressource;
    }
	

	/**
	 * M�thode priv�e permettant d'analyser le r�sultat d'une requ�te contenant les donn�es d'un utilisateur
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	protected static Ressource map(ResultSet resultSet) throws SQLException {
		Ressource ressource = new Ressource();
		ressource.setId(resultSet.getLong("idUser"));
		
		return ressource;
	}
	
	private void loadDatabase() {
		verifyConfiguration();
		// Chargement du driver JDBC
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		}
		
		try {
			connexion = DriverManager.getConnection(url, userProp, pwdProp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private int maxId() {
		loadDatabase();
		try {
			PreparedStatement ps = connexion.prepareStatement("SELECT max(idSource) FROM Ressources");
			ResultSet rs = ps.executeQuery();			
	
			// Récupération des données
			if (rs.next()) {
				return (int) rs.getLong(1);
			} else {
				throw new DAOException("Error while loading max id inside tasks table.");			
				}
		} catch(SQLException e) {
			throw new DAOException(e);
		}		
	}
	
	private static void verifyConfiguration() {
    	// Properties permet de lire un fichier contenant des lignes sous la forme
    	//		key = value
    	//	et permet de r�cup�rer par la suite les propri�t�s � l'aide de getProperty(key) 
    	Properties properties = new Properties();
    	String fileUrl;
    	String fileUser;
    	String filePassword;
    	
    	// On charge le fichier dans un InputStream pour �viter de devoir donner le
    	//		chemin absolu et g�rer une exception
    	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    	InputStream fileProp = classLoader.getResourceAsStream(FILE_PROP);
    	if (fileProp == null) {
    	}
    	
    	try {
    		properties.load(fileProp);
    		fileUrl = properties.getProperty(PROP_URL);
    		fileUser = properties.getProperty(PROP_USER);
    		filePassword = properties.getProperty(PROP_PASSWORD);
        	String schemeProp = fileUrl.split("//")[1].split("/")[1];
        	String adressProp = fileUrl.split("//")[1].split(":")[0];
        	url = "jdbc:mysql://" + adressProp + ":3306/" + schemeProp;
        	userProp = fileUser;
        	pwdProp = filePassword;
        	
    	} catch (IOException e) {
    	}
	}
	
}
