package com.test.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Classe permettant de g�rer les connexions et la configuration � la base
 * 		de donn�es
 *	Sp�cifie l'ensemble des noms, attributs et contraintes des tables
 *		contenus dans la base de donn�es, en v�rifiant leurs pr�sences
 *		au d�marrage de l'application. Si elles ne sont pas pr�sentes, 
 *		alors cette classe cr�e les tables par elle-m�me
 */
public class DAOFactory {
	
	/*
			--------- INFORMATIONS DES TABLES UTILISEES ---------
	*/
	
	// Liste des tables
	// Table des utilisateurs 
	public static final String TABLE_USER				= "Utilisateurs";
	// Table contenant la liste des Demande	
	public static final String TABLE_DEMANDE			= "Demande";
	// Table contenant la liste des Ressource
	public static final String TABLE_RESSOURCE			= "Ressources";
	// Taille des descriptions
	public static final String SIZE_TEXTAREA		 	= "256";
	
	// Information des colonnes des tables
	//		Chaque colonne est d�fini tel qu'un nom et un type
	//		Si type == VARCHAR, alors il faut d�finir une taille

	private static final String[][] TABLE_USER_ROWS	  = {
			{"idUser", 			"INT"}, 					// Sp�cifique � la manipulation interne des donn�es
			{"username", 		"VARCHAR",		"100"}, 		// Pseudo des uitlisateurs
			{"pwd", 			"VARCHAR",		"100"},
			{"job",				"VARCHAR",		"100"},
	};
	private static final String[][] TABLE_DEMANDE_ROWS = {
			{"id", 					"INT"},						// Sp�cifique � la manipulation interne des donn�es
			{"idUser", 				"INT"}, 
			{"idSource", 			"INT"}, 
			{"idManagerMaint", 		"INT"}, 
			{"state", 				"VARCHAR",		"100"},
			{"description",			"VARCHAR",		SIZE_TEXTAREA}
	};
	private static final String[][] TABLE_RESSOURCE_ROWS = {
			{"idSource", 		"INT"},						// Sp�cifique � la manipulation interne des donn�es
			{"idUser",			"INT"},
			{"localisation", 	"VARCHAR",		"100"},
			{"description",			"VARCHAR",		SIZE_TEXTAREA}
	};

	// Contraintes des tables
	private static final String[] CONS_DEMANDE_PK = {
			"ALTER TABLE Demande ADD FOREIGN KEY FK_DEMANDE_RESSOURCE (idSource) REFERENCES Ressources(idSource)",
			"ALTER TABLE Demande ADD FOREIGN KEY FK_DEMANDE_RESPON (idManagerMaint) REFERENCES Utilisateurs(idUser)"
	};
	private static final String[] CONS_RESSOURCE_PK = {
			"ALTER TABLE Ressources ADD FOREIGN KEY FK_RESSOURCE_USER (idUser) REFERENCES Utilisateurs(idUser)",					
	};
	
	// Liste des tables 
	//		Etant d�fini tel qu'un nom, les informations des colonnes, et des contraintes
	private static final Object[][] LIST_TABLES		  = {
			{TABLE_DEMANDE, 	TABLE_DEMANDE_ROWS,	CONS_DEMANDE_PK},
			{TABLE_RESSOURCE, 	TABLE_RESSOURCE_ROWS,	CONS_RESSOURCE_PK},
	};
	
	private static final boolean VERIFY_TABLE = true;
	
	/*
			--------- INFORMATIONS DU DAOFACTORY FILE --------- 
	*/

	// nom de l'attribut dans les servlets contenant le DAOFactory
	public static final String NAME_ATT 			  = "daofactory";
	
	// Propri�t� du fichier dao
	public static final String FILE_PROP			  = "/dao/dao.properties";
	private static final String PROP_URL			  = "url";
	private static final String PROP_DRIVER			  = "driver";
	private static final String PROP_USER			  = "user";
	private static final String PROP_PASSWORD		  = "password";
	private static boolean is_validate 				  = false;
	
	/*
			--------- INFORMATIONS DE L'OBJET DAOFACTORY --------- 
	*/
	
	private String url;
	private String user;
	private String password;
	
	DAOFactory(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
	}
    
	/**
	 * Renvoie un objet DAO pour les utilisateurs
	 * @return {@link dao.UserDAO}
	 */
	public UserDAO getUtilisateurDao() {
		return new UserDAO(this);
	}
    
	/**
	 * Renvoie un objet DAO pour les kanbans
	 * @return {@link dao.KanbanDAO}
	 *
	public DemandeDAO getDemandeDao() {
		return new DemandeDAO(this);
	}
	*/
	/**
	 * Renvoie un objet DAO pour les taches
	 * @return {@link dao.RessourceDAO}
	 */
	public RessourceDAO getRessourceDao() {
		return new RessourceDAO(this);
	}
	
	/**
	 * Renvoie vrai uniquement si la configuration d'acc�s � la base
	 * 	de donn�es � �t� correctement faite, et valid�
	 */
	public static boolean dbIsValidate() {
		return DAOFactory.is_validate;
	}
	
	/**
	 * Permet de valider la configuration � la base de donn�e
	 * TODO : la v�rification que la configuration d'acc�s est correcte doit �tre faite
	 * 	ici
	 */
	public static void setValidationdb(boolean bool) {
		//if (DAOFactory.verifyConfiguration()) {
			DAOFactory.is_validate = bool;
			if (bool) {
		    	try {
		    		if (VERIFY_TABLE) {
		    			DAOFactory instance = DAOFactory.getInstance();
		            	verifyTables(instance);
		    		}
		    	} catch (DAOConfigurationException e) {
		    		throw e;
		    	}
			}
		//}
	}
	
	/**
	 * M�thode de v�rification de la bonne configuration d'acc�s � la base de donn�es
	 */
	public static boolean verifyConfiguration() {
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
    		return false;
    	}
    	
    	try {
    		properties.load(fileProp);
    		fileUrl = properties.getProperty(PROP_URL);
    		fileUser = properties.getProperty(PROP_USER);
    		filePassword = properties.getProperty(PROP_PASSWORD);
        	return verifyConfiguration(
        			fileUrl.split("//")[1].split("/")[1],
        			fileUrl.split("//")[1].split(":")[0],
        			fileUser,
        			filePassword);
    	} catch (IOException e) {
    		return false;
    	}
	}

	/**
	 * M�thode de v�rification de la bonne configuration d'acc�s � la base de donn�es
	 */
	public static boolean verifyConfiguration(String scheme, String adress, String user, String password) {

    	// On cr�e une instance de l'acc�s � la base de donn�e et on v�rifie que les tables
    	//	sont bien pr�sentes.
    	DAOFactory instance = new DAOFactory("jdbc:mysql://" + adress + ":3306/" + scheme, user, password);
    	try {
    		instance.getConnection();
    	} catch (SQLException e) {
    		return false;
    	}
		return true;
	}
	
    /**
     * M�thode charg�e de r�cup�rer les informations de connexion � la base de
     * donn�es, charger le driver JDBC et retourner une instance de la Factory
     * @return {@link dao.DAOFactory}
     * @throws DAOConfigurationException
     */
    public static DAOFactory getInstance() throws DAOConfigurationException {
    	
    	// Properties permet de lire un fichier contenant des lignes sous la forme
    	//		key = value
    	//	et permet de r�cup�rer par la suite les propri�t�s � l'aide de getProperty(key) 
    	Properties properties = new Properties();
    	String fileUrl;
    	String fileDriver;
    	String fileUser;
    	String filePassword;
    	
    	// On charge le fichier dans un InputStream pour �viter de devoir donner le
    	//		chemin absolu et g�rer une exception
    	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    	InputStream fileProp = classLoader.getResourceAsStream(FILE_PROP);
    	if (fileProp == null) {
    		throw new DAOConfigurationException("File '" + FILE_PROP + "' not founded.");
    	}
    	
    	try {
    		properties.load(fileProp);
    		fileUrl = properties.getProperty(PROP_URL);
    		fileDriver = properties.getProperty(PROP_DRIVER);
    		fileUser = properties.getProperty(PROP_USER);
    		filePassword = properties.getProperty(PROP_PASSWORD);
    	} catch (IOException e) {
    		throw new DAOConfigurationException("Cannot load file '" + FILE_PROP + "'.", e);
    	}
    	
    	// On charge le driver permettant de se connecter � la base de donn�e
    	try {
    		Class.forName(fileDriver);
    	} catch (ClassNotFoundException e) {
    		throw new DAOConfigurationException("Driver not found in classpath.", e);
    	}

    	// On cr�e une instance de l'acc�s � la base de donn�e et on v�rifie que les tables
    	//	sont bien pr�sentes.
    	DAOFactory instance = new DAOFactory(fileUrl, fileUser, filePassword);
		return instance;
    }
    
    /**
     * Renvoie une connexion � la base de donn�e.
     * @return {@link Connection}
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
    	return DriverManager.getConnection(url, user, password);
    }
    
    /**
     * M�thode priv�e v�rifiant que la base de donn�e contient bien les tables.
     * @param factory
     * @throws DAOConfigurationException
     */
    private static void verifyTables(DAOFactory factory) throws DAOConfigurationException {
    	try {
        	Connection connection = factory.getConnection();
        	DatabaseMetaData meta = connection.getMetaData();
        	for (Object[] dataTable : LIST_TABLES) {
        		
        		// dataColumns[.][0] correspond au nom  de la colonne
        		// dataColumns[.][1] correspond au type de la colonne
        		// dataColumns[.][2] correspond � la taille du varchar
        		String nameTable 		= (String) 	   dataTable[0];
        		String[][] dataColumns 	= (String[][]) dataTable[1];
        		String[] consTable		= (dataTable.length == 3 ? (String[]) dataTable[2] : null);
        		
        		// R�cup�re les m�tadonn�es de la table
            	ResultSet table = meta.getTables(null, null, nameTable, null);
            	
            	if (!table.next()) {
            		// Si la table n'existe pas, on la cr�er
            		
            		createTable(nameTable, dataColumns, consTable, factory);
            	} else {
            		// Si la table existe, on v�rifie que les colonnes sont les bonnes
            		
            		// On r�cup�re les donn�es des colonnes existantes
            		ResultSet columns = meta.getColumns(null, null, nameTable, null);
            		int index = 0;
            		while(columns.next()) {
            			
            			// V�rifie le nom de la colonne
            			if (!columns.getString("COLUMN_NAME").equals(dataColumns[index][0])) {
            				break;
            			}
            			
            			// V�rifie le type de la colonne
            			if (!columns.getString("TYPE_NAME").equals(dataColumns[index][1])) {
            				if (!(columns.getString("TYPE_NAME").equals("BIT") &&
            						columns.getInt("COLUMN_SIZE") == 1 && 
            						dataColumns[index][1].equals("BOOLEAN"))) {
                				break;
            				}
            			}
            			
            			// Lorsque le type est VARCHAR, v�rifie que la taille est bonne
            			if (columns.getString("TYPE_NAME").equals("VARCHAR") &&
            					!Integer.toString(columns.getInt("COLUMN_SIZE")).equals(dataColumns[index][2])) {
            				break;
            			}
            			++index;
            		}
            		
            		// Si le nombre de colonne correspondante est diff�rent du nombre de colonne n�cessaire, alors
            		//		les colonnes ne correspondent pas aux param�tres de la table
            		if (index != dataColumns.length) {
            			throw new DAOConfigurationException("Table " + nameTable + "does not contains the right columns " + index);
            		}
            	}
        	}
    	} catch (SQLException e) {
    		throw new DAOConfigurationException("Cannot verify tables required !" + e);
    	}
    }
	
    /**
	 * M�thode priv�e permettant de cr�er en table de nom 'name' et de colonnes columns
	 * 	columns doit �tre un tableau de tableau contenant un nom, un type, et sa taille si il s'agit d'un VARCHAR
	 * 	constraint permet de d�finir une contrainte
	 * @param name nom de la table
	 * @param columns les donn�es des colonnes (nom, type, taille)
	 * @param constraints les alterations de la table (contraintes)
	 * @param factory l'objet contenant la connexion � la bdd
	 * @throws DAOException
	 */
    // 
    private static void createTable(String name, String[][] columns, String[] constraints, DAOFactory factory) throws DAOException {
    	Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String request = String.format("CREATE TABLE %s(", name);
			if (columns[0][1].equals("VARCHAR")) {
				request += String.format("%s VARCHAR(%s)", columns[0][0], columns[0][2]);
			} else {
				request += String.format("%s %s", columns[0][0], columns[0][1]);
			}
			for (int i = 1; i < columns.length; ++i) {
				if (columns[i][1].equals("VARCHAR")) {
					request += String.format(", %s VARCHAR(%s)", columns[i][0], columns[i][2]);
				} else {
					request += String.format(", %s %s", columns[i][0], columns[i][1]);
				}
			}
			request += ");";
			
			// Cr�e la table
			connection = factory.getConnection();
			preparedStatement = DAOFactory.initializePreparedRequest(
					connection, 
					request,
					false);
			
			preparedStatement.execute();
			DAOFactory.close(preparedStatement);
			
			// Ajoute les contraintes
			if (constraints != null) {
				for (String constraint : constraints) {
					preparedStatement = DAOFactory.initializePreparedRequest(
							connection, 
							constraint,
							false);
					preparedStatement.execute();
					DAOFactory.close(preparedStatement);
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOFactory.close(resultSet, preparedStatement, connection);
		}
    }
	
	/* 			METHODES STATIC EXTERNES			*/
	
	public static PreparedStatement initializePreparedRequest(Connection connexion, String sql,
			boolean returnGeneratedKeys, Object... objets) throws SQLException {
		PreparedStatement preparedStatement = connexion.prepareStatement(sql, 
				returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
		for (int i=0; i < objets.length; ++i) {
			preparedStatement.setObject(i + 1, objets[i]);
		}
		
		return preparedStatement;
	}
	
	public static void close(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.println("Closing resultSet failed : " + e.getMessage());
			}
		}
	}
	
	public static void close(Connection connection) {
		if (connection  != null) {
			try {
				connection .close();
			} catch (SQLException e) {
				System.out.println("Closing connection failed : " + e.getMessage());
			}
		}
	}
	
	public static void close(Statement statement) {
		if (statement  != null) {
			try {
				statement .close();
			} catch (SQLException e) {
				System.out.println("Closing statement failed : " + e.getMessage());
			}
		}
	}
	
	public static void close(Statement statement, Connection connection) {
		close(statement);
		close(connection);
	}
	
	public static void close(ResultSet resultSet, Statement statement, Connection connection) {
		close(resultSet);
		close(connection);
		close(connection);
	}
}
