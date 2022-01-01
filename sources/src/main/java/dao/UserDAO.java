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
import beans.User;

/**
 * Objet permettant d'envoyer des requ�tes � la base donn�es, sur la table User
 * 	Cette table correspondant � l'ensemble des utilisateurs enregistr�s dans la base de donn�e
 * @cons <pre>
 *     $DESC$ UN objet utilisant factory afin d'initialiser les connexions � la base de donn�e
 *     $ARGS$ DAOFactory factory
 *     $PRE$
 *         DAOFactory.dbIsValidate() </pre>
 */
public class UserDAO {
	
	// ATTRIBUTS 
	
	// Requ�te qui insert une ligne dans la table User
	private static final String SQL_INSERT 						= String.format("INSERT INTO %s VALUES (?, ?, ?, ?);", DAOFactory.TABLE_USER);
	private static final String SQL_DEL 						= String.format("DELETE FROM %s WHERE idUser=?;", DAOFactory.TABLE_USER);
	private static final String SQL_DEL_ALL_RESS 				= String.format("DELETE FROM %s WHERE idManagerMaint=?;", DAOFactory.TABLE_DEMANDE);
	private static final String SQL_DEL_ALL_DEM 				= String.format("DELETE FROM %s WHERE idManagerMaint=?;", DAOFactory.TABLE_RESSOURCE);

	public static final String FILE_PROP			  = "/dao/dao.properties";
	private static final String PROP_URL			  = "url";
	private static final String PROP_USER			  = "user";
	private static final String PROP_PASSWORD		  = "password";
		
	private static String url;
	private static String userProp;
	private static String pwdProp;
	
	private Connection connexion;
	
	// COMMANDES

	

	// REQUETES
	
	/**
	 * Requ�te renvoyant l'utilisateur d'identifiant username et pwd
	 * @param Long id
	 * @return User user : user.getId() == id
	 * @throws DAOException
	 */
	public User connexion(String userName, String passwd) throws DAOException {
		try {
			verifyConfiguration();
			String query = "select * from Utilisateurs where username IN (?) AND pwd IN (?)";
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			Connection con = DriverManager.getConnection(url, userProp, pwdProp);
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, userName);
			ps.setString(2, passwd);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String userNameFound = rs.getString("username");
			User userFound = new User();
			userFound.setUsername(userNameFound);
			userFound.setPassword(rs.getString("pwd"));
			userFound.setId(rs.getInt("idUser"));
			userFound.setRole(rs.getString("job"));
			return userFound;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean userExist(String username) {
		
		loadDatabase();
		System.out.println("Chargement de la database fait ressourcerespid");
		try {
			PreparedStatement ps = connexion.prepareStatement("SELECT * FROM Utilisateurs WHERE username=?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();			
			//String query = "SELECT * FROM Ressources WHERE idManagerMaint=?";

			// Récupération des données
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch(SQLException e) {
		}		
		return false;
	}
	
	public List<User> recupRespon() {

		List<User> listUser = new ArrayList<User>();
		java.sql.Statement statement = null; //représente requête SQL
		ResultSet resultat = null;
		
		loadDatabase();

		try {			
			statement = connexion.createStatement();
			// Exécution de la requête
			PreparedStatement ps = connexion.prepareStatement("SELECT * FROM Utilisateurs WHERE job!=?");
			ps.setString(1, "admin");
			ResultSet rs = ps.executeQuery();

			// Récupération des données
			while (rs.next()) {
				int idUser = rs.getInt("idUser");
				String userName = rs.getString("username");
				String pwd = rs.getString("pwd");
				String role = rs.getString("job");
				User user = new User();
				user.setId((long) idUser);
				user.setUsername(userName);
				user.setPassword(pwd);
				user.setRole(role);
				listUser.add(user);
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
		return listUser;
	}
	
	// COMMANDES

	/**
	 * Commande permettant de rajouter l'utilisateur dans la base de donn�es
	 * @param User user
	 * @throws DAOException
	 */
	public void createUser(User user) throws DAOException {
		try {
			verifyConfiguration();
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			Connection con = DriverManager.getConnection(url, userProp, pwdProp);
			PreparedStatement pst = con.prepareStatement(SQL_INSERT);
			pst.setLong(1,maxId()+1);
			pst.setString(2,user.getUsername());
			pst.setString(3,user.getPassword());
			pst.setString(4,user.getRole());
			pst.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void suppUser(long id) throws DAOException {
		try {
			verifyConfiguration();
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			Connection con = DriverManager.getConnection(url, userProp, pwdProp);
			PreparedStatement pst1 = con.prepareStatement(SQL_DEL_ALL_DEM);
			PreparedStatement pst2 = con.prepareStatement(SQL_DEL_ALL_RESS);
			pst2.setLong(1,id);
			pst2.executeUpdate();
			pst1.setLong(1,id);
			pst1.executeUpdate();

			PreparedStatement pst3 = con.prepareStatement(SQL_DEL);
			pst3.setLong(1,id);
			pst3.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * M�thode priv�e permettant d'analyser le r�sultat d'une requ�te contenant les donn�es d'un utilisateur
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	protected static User map(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getLong("idUser"));
		user.setUsername(resultSet.getString("username"));
		user.setPassword(resultSet.getString("pwd"));
		user.setRole(resultSet.getString("job"));
		
		return user;
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
		System.out.println("Chargement de la database fait ressourcerespid");
		try {
			PreparedStatement ps = connexion.prepareStatement("SELECT max(idUser) FROM Utilisateurs");
			ResultSet rs = ps.executeQuery();			
			//String query = "SELECT * FROM Ressources WHERE idManagerMaint=?";
	
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
