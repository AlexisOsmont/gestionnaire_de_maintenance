package com.test.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.test.beans.User;

import com.test.dao.DAOException;
import com.test.dao.DAOFactory;


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

	// Requ�te qui s�lectionne le plus grand identifiant dans table User
	private static final String SQL_MAX_ID 						= String.format("SELECT max(idUser) FROM %s;", DAOFactory.TABLE_USER);

	// Requ�te qui s�lectionne l'utilisateur d'identifiant dans la table User
	private static final String SQL_SELECT_BY_ID 				= String.format("SELECT * FROM %s WHERE idUser=?;", DAOFactory.TABLE_USER);

	// Requ�te qui s�lectionne l'utilisateur � partir de son username et son password dans la table User
	private static final String SQL_SELECT_BY_USER_PASSWORD 	= String.format("SELECT * FROM %s WHERE username=? AND pwd=?;", DAOFactory.TABLE_USER);

	// Requ�te qui s�lectionne l'utilisateur d'identifiant dans la table User
	private static final String SQL_SELECT_BY_JOB				= String.format("SELECT idUser, username, job FROM %s WHERE job=?;", DAOFactory.TABLE_USER);
	
	// Requ�te qui s�lectionne l'identifiant d'un utilisateur dans la table User
	private static final String SQL_SELECT_ID_USER				= String.format("SELECT idUser FROM %s WHERE username=?", DAOFactory.TABLE_USER);

	// Requ�te qui s�lectionne le mot de passe enregistr� pour un utilisateur dans la table User
	private static final String SQL_SELECT_PWD_USER				= String.format("SELECT pwd FROM %s WHERE username=?", DAOFactory.TABLE_USER);
	
	// Requ�te qui insert une ligne dans la table User
	private static final String SQL_INSERT 						= String.format("INSERT INTO %s VALUES (?, ?, ?, ?, ?);", DAOFactory.TABLE_USER);
	
	// Requ�te qui met � jour les informations d'un utilisateur dans la table User
	private static final String SQL_UPDATE_BY_ID				= String.format("UPDATE %s SET username=?, pwd=? WHERE idUser=?", DAOFactory.TABLE_USER);

	// Algorithme de hachage utilis� pour le stockage des mots de passe
	private static final String DIGEST_ALGO = "SHA-256";
	private DAOFactory factory;
	
	// COMMANDES

	
	/**
	 * Contructeur de l'objet, prend en param�tre un DAOFactory uniquement si DAOFactory.dbIsValidate()
	 * @param factory
	 */
	UserDAO(DAOFactory factory) {
		this.factory = factory;
	}
	
	// REQUETES
	
	/**
	 * Requ�te renvoyant l'utilisateur d'identifiant id
	 * @param Long id
	 * @return User user : user.getId() == id
	 * @throws DAOException
	 */
	public User getUser(Long id) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = null;
		
		try {
			connection = factory.getConnection();
			preparedStatement = DAOFactory.initializePreparedRequest(
					connection, 
					SQL_SELECT_BY_ID, 
					false,
					id);
			
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				user = map(resultSet);
			} else {
				throw new DAOException("Any user corresponding to id='" + id + "'.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOFactory.close(resultSet, preparedStatement, connection);
		}
		return user;
	}
	
	/**
	 * Requ�te renvoyant un tableu d'utilisateur de role (job) job
	 * @param Long id
	 * @return User user : user.getId() == id
	 * @throws DAOException
	 */
	public List<User> getJobUser(String job) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<User> users = new ArrayList<User>();
		
		try {
			connection = factory.getConnection();
			preparedStatement = DAOFactory.initializePreparedRequest(
					connection, 
					SQL_SELECT_BY_JOB, 
					false,
					job);
			
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				users.add(map(resultSet));			
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOFactory.close(resultSet, preparedStatement, connection);
		}
		return users;
	}

	/**
	 * Requ�te r�cup�rant l'utilisateur de surnom username et de mot de passe password
	 * @param String username
	 * @param String password
	 * @return User user : user.getUsername() == usernale && user.getPassword() == password
	 * @throws DAOException
	 */
	public User getUser(String username, String password) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = null;
		
		try {
			connection = factory.getConnection();
			preparedStatement = DAOFactory.initializePreparedRequest(
					connection, 
					SQL_SELECT_BY_USER_PASSWORD, 
					false,
					username, password);
			
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				user = map(resultSet);
			} else {
				throw new DAOException("Any rows selected with name '" + username + "'.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOFactory.close(resultSet, preparedStatement, connection);
		}
		return user;
	}
	
	/**
	 * Requ�te permettant de r�cup�rer l'identifiant de l'utilisateur de nom name
	 * @param String name
	 * @return User user : user.getUsername() == name
	 * @throws DAOException
	 */
	public Long getUserId(String name) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Long id = null;
		
		try {
			connection = factory.getConnection();
			preparedStatement = DAOFactory.initializePreparedRequest(
					connection, 
					SQL_SELECT_ID_USER, 
					false,
					name);
			
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				id = resultSet.getLong("idUser");
			} else {
				throw new DAOException("This user does not exist !");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOFactory.close(resultSet, preparedStatement, connection);
		}
		return id;
	}
	
	/**
	 * Requ�te renvoyant le mot de passe de l'utilisateur username
	 * @param String username
	 * @return getUser(username, getUserPassword(username)) != null
	 * @throws DAOException
	 */
	public String getUserPassword(String username) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String password = null;
		
		try {
			connection = factory.getConnection();
			preparedStatement = DAOFactory.initializePreparedRequest(
					connection, 
					SQL_SELECT_PWD_USER, 
					false,
					username);
			
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				password = resultSet.getString("pwd");
			} else {
				throw new DAOException("This user does not exist !");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOFactory.close(resultSet, preparedStatement, connection);
		}
		return password;
	}
	
	/**
	 * Requ�te renvoyant vrai si l'utilisateur username existe dans la base de donn�es
	 * @param String username
	 * @return vrai ssi getUserId(username) != null
	 * @throws DAOException
	 */
	public boolean userExist(String username) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		boolean exist = false;
		
		try {
			connection = factory.getConnection();
			preparedStatement = DAOFactory.initializePreparedRequest(
					connection, 
					SQL_SELECT_ID_USER, 
					false,
					username);
			
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				exist = true;
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOFactory.close(resultSet, preparedStatement, connection);
		}
		return exist;
	}

	/**
	 * Requ�te permettant de renvoyer le mot de passe hash� avec du sel
	 * @param String username
	 * @param String passwordToHash
	 * @return
	 * @throws DAOException
	 */
    public String encodePassword(String username, String passwordToHash) throws DAOException {
        String saltString = generateSalt(12);
        passwordToHash = saltString + passwordToHash;
        
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance(DIGEST_ALGO);
            md.update(passwordToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i< bytes.length; i++)
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
           	throw new DAOException("Instance of '" + DIGEST_ALGO + "' does not exist for MessageDigest");
        }
        return saltString + generatedPassword;
    }
    
    /**
     * Requ�te permettant de v�rifier si le hachage de passwordToHash correspond au hachage du mot de passe
     * 	de l'utilisateur username, en utilisant le m�me seul contenu dans le mot de passe du l'utilisateur
     * @param String username
     * @param String passwordToHash
     * @return getUserPasswor(username) == encodePassword(username, passwordToHash)
     * @throws DAOException
     */
    public String verifyPassword(String username, String passwordToHash) throws DAOException  {
    	String storedPassword = null;
    	try {
        	storedPassword = getUserPassword(username);
    	} catch (DAOException e) {
    		throw e;
    	}
    	String salt = storedPassword.substring(0, 12);
    	passwordToHash = salt + passwordToHash;

        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance(DIGEST_ALGO);
            md.update(passwordToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i< bytes.length; i++)
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
           	throw new DAOException("Instance of '" + DIGEST_ALGO + "' does not exist for MessageDigest");
        }
        if (!(salt + generatedPassword).equals(storedPassword)) {
        	return null;
        }
        return storedPassword;
    }
    
    /**
     * Requ�te priv�e renvoyant un sel al�atoire de taille size
     * @param int size
     * @return length(generateSalt(size)) == size
     */
    private String generateSalt(int size) {
    	final String HEXA = "0123456789abcdef";
    	String salt = "";
    	for (int i = 0; i < size; ++i) {
    		salt += HEXA.charAt(new Random().nextInt(16));
    	}
    	
    	return salt;
    }
    
	// COMMANDES

	/**
	 * Commande permettant de rajouter l'utilisateur dans la base de donn�es
	 * @param User user
	 * @throws DAOException
	 */
	public void createUser(User user) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			Long maxId = getNextId() + 1;
			connection = factory.getConnection();
			preparedStatement = DAOFactory.initializePreparedRequest(
					connection, 
					SQL_INSERT,
					false,
					maxId, user.getUsername(), user.getPassword(), user.getRole());
			
			int status = preparedStatement.executeUpdate();
			if (status == 0) {
				throw new DAOException("Creating user failed !");
			}
			user.setId(maxId);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOFactory.close(preparedStatement, connection);
		}
		
	}

	/**
	 * Commande permettant de mettre � jour les donn�es de l'utilisateur d'identifiant id
	 * @param User user
	 * @throws DAOException
	 */
	public void updateUser(String name, String surname, String passwd, String username, Long id) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = factory.getConnection();
			preparedStatement = DAOFactory.initializePreparedRequest(
					connection, 
					SQL_UPDATE_BY_ID,
					false,
					username, passwd, id);
			
			int status = preparedStatement.executeUpdate();
			if (status == 0) {
				throw new DAOException("Updating user failed !");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOFactory.close(preparedStatement, connection);
		}
	}
	
	// OUTILS

	/**
	 * M�thode priv�e pour r�cup�rer le prochain id de la table User
	 * @return
	 * @throws DAOException
	 */
	private Long getNextId() throws DAOException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = factory.getConnection();
			preparedStatement = DAOFactory.initializePreparedRequest(
					connection, 
					SQL_MAX_ID,
					false);
			
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getLong(1);
			} else {
				throw new DAOException("Error while loading max id inside users table.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOFactory.close(resultSet, preparedStatement, connection);
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
}
