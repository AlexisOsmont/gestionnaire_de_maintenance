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

public class DemandeDAO {
	private Connection connexion;
	
	public static final String FILE_PROP			  = "/dao/dao.properties";
	private static final String PROP_URL			  = "url";
	private static final String PROP_USER			  = "user";
	private static final String PROP_PASSWORD		  = "password";
		
	private static String url;
	private static String userProp;
	private static String pwdProp;
	
	public List<Demande> recupDemandes() {
		List<Demande> listDemandes = new ArrayList<Demande>();
		java.sql.Statement statement = null; //représente requête SQL
		ResultSet resultat = null;
		
		loadDatabase();
		
		try {			
			statement = connexion.createStatement();
			
			// Exécution de la requête
			resultat = statement.executeQuery("SELECT * FROM Demande WHERE 1");
			
			// Récupération des données
			while (resultat.next()) {
				System.out.println(resultat);
				int idRequest = resultat.getInt("idRequest");
				int idUser = resultat.getInt("idUser");
				int idSource = resultat.getInt("idSource");
				int idManagerMaint = resultat.getInt("idManagerMaint");
				String state = resultat.getString("state");
				String description = resultat.getString("description");
				
				Demande demande = new Demande();
				demande.setIdRequest(idRequest);
				demande.setIdUser(idUser);
				demande.setIdSource(idSource);
				demande.setIdManagerMaint(idManagerMaint);
				demande.setState(state);
				demande.setDescription(description);
				
				
				listDemandes.add(demande);
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
		return listDemandes;
	}
	
	public void setDemandeValide(long id) {
		loadDatabase();
		try {
			PreparedStatement preparedStatement = connexion.prepareStatement("UPDATE Demande SET state=? WHERE idRequest=?");
			preparedStatement.setString(1, "valid");
			preparedStatement.setInt(2, (int)id);	
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void ajoutDemande(Demande demande) {
		loadDatabase();
		
		try {
			PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO Demande(idRequest, idUser, idSource, idManagerMaint, state, description) VALUES(?,?,?,?,?,?);");
			preparedStatement.setInt(1, maxId()+1);
			preparedStatement.setInt(2, (int)demande.getIdUser());
			preparedStatement.setInt(3, (int)demande.getIdSource());
			preparedStatement.setInt(4, (int)demande.getIdManagerMaint());
			preparedStatement.setString(5, demande.getState());
			preparedStatement.setString(6, demande.getDescription());
			
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Demande> recupDemandesId(long id) {
		List<Demande> listDemandes = new ArrayList<Demande>();
		java.sql.Statement statement = null; //représente requête SQL
		ResultSet resultat = null;
		
		loadDatabase();
		System.out.println("Chargement de la database fait");
		
		try {			
			statement = connexion.createStatement();
			
			// Exécution de la requête
			resultat = statement.executeQuery("SELECT * FROM Demande WHERE idManagerMaint="+id);
			System.out.println("Exécution de la requete fait");
			
			// Récupération des données
			while (resultat.next()) {
				int idRequest = resultat.getInt("idRequest");
				int idUser = resultat.getInt("idUser");
				int idSource = resultat.getInt("idSource");
				int idManagerMaint = resultat.getInt("idManagerMaint");
				String state = resultat.getString("state");
				String description = resultat.getString("description");
				
				Demande demande = new Demande();
				demande.setIdRequest(idRequest);
				demande.setIdUser(idUser);
				demande.setIdSource(idSource);
				demande.setIdManagerMaint(idManagerMaint);
				demande.setState(state);
				demande.setDescription(description);
				
				
				listDemandes.add(demande);
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
		return listDemandes;
	}
	
	// récupère les demandes lié à l'identifiant source id
    public List<Demande> recupDemandesIdSource(long id) {
        List<Demande> listDemandes = new ArrayList<Demande>();
        java.sql.Statement statement = null; //représente requête SQL
        ResultSet resultat = null;

        loadDatabase();

        try {
            statement = connexion.createStatement();

            // Exécution de la requête
            resultat = statement.executeQuery("SELECT * FROM Demande WHERE idSource="+id);

            // Récupération des données
            while (resultat.next()) {
                int idRequest = resultat.getInt("idRequest");
                int idUser = resultat.getInt("idUser");
                int idSource = resultat.getInt("idSource");
                int idManagerMaint = resultat.getInt("idManagerMaint");
                String state = resultat.getString("state");
                String description = resultat.getString("description");

                Demande demande = new Demande();
                demande.setIdRequest(idRequest);
                demande.setIdUser(idUser);
                demande.setIdSource(idSource);
                demande.setIdManagerMaint(idManagerMaint);
                demande.setState(state);
                demande.setDescription(description);


                listDemandes.add(demande);
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
        return listDemandes;
    }
	
	// UTILS
	
	// charge le driver JDBC et se connecte à la database "projetWebDataBase"
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
		System.out.println("Chargement de la database fait ressourcerespid");
		try {
			PreparedStatement ps = connexion.prepareStatement("SELECT max(idRequest) FROM Demande");
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
    		return;
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
    		return;
    	}
	}
	
}