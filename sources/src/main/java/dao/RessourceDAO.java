package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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


		
	private Connection connexion;
	
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
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/projetWebDataBase","Alexis","Alexis");
			PreparedStatement pst = con.prepareStatement(SQL_INSERT);
			pst.setLong(1,0);
			pst.setLong(2,ressource.getUserId());
			System.out.println(ressource.getUserId());
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
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/projetWebDataBase","Alexis","Alexis");
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
		System.out.println("Chargement de la database fait ressourcerespid");
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
		System.out.println("Chargement de la database fait ressourcerespid");
		
		try {			
			statement = connexion.createStatement();
			
			// Exécution de la requête
			resultat = statement.executeQuery("SELECT * FROM Ressources WHERE idManagerMaint="+id);
			System.out.println("Exécution de la requete fait");
			
			// Récupération des données
			while (resultat.next()) {
				System.out.println(resultat);
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
				System.out.println("Exécution :"+ressource.getNom());

				
				System.out.println("Création de l'objet fait");
				
				listRessource.add(ressource);
				System.out.println("Ajout de l'objet à la liste d'objet fait");
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
                System.out.print("recupRessourceId : idManagerMaint = ");
                System.out.println(idManagerMaint);
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
		// Chargement du driver JDBC
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		}
		
		try {
			connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetWebDataBase", "Alexis", "Alexis");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
