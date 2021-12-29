package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

	// Requ�te qui s�lectionne le plus grand identifiant dans table Ressource
	private static final String SQL_MAX_ID 						= String.format("SELECT max(idUser) FROM %s;", DAOFactory.TABLE_RESSOURCE);

	// Requ�te qui s�lectionne l'utilisateur d'identifiant dans la table Ressource
	private static final String SQL_SELECT_BY_ID 				= String.format("SELECT * FROM %s WHERE id=?;", DAOFactory.TABLE_RESSOURCE);

	// Requ�te qui s�lectionne l'utilisateur d'identifiant dans la table Ressource
	private static final String SQL_SELECT_BY_RESPON			= String.format("SELECT idSource, description, localisation FROM %s WHERE iDuser=?;", DAOFactory.TABLE_RESSOURCE);
	
	// Requ�te qui insert une ligne dans la table User
	private static final String SQL_INSERT 						= String.format("INSERT INTO %s VALUES (?, ?, ?, ?);", DAOFactory.TABLE_RESSOURCE);
	
	// Requ�te qui met � jour les informations d'un utilisateur dans la table User
	private static final String SQL_UPDATE_BY_ID				= String.format("UPDATE %s SET localisation=?, description=? WHERE idSource=?", DAOFactory.TABLE_RESSOURCE);
	
	private DAOFactory factory;
	
	// COMMANDES

	
	/**
	 * Contructeur de l'objet, prend en param�tre un DAOFactory uniquement si DAOFactory.dbIsValidate()
	 * @param factory
	 */
	RessourceDAO(DAOFactory factory) {
		this.factory = factory;
	}
	
	// REQUETES
	
	/**
	 * Requ�te renvoyant l'utilisateur d'identifiant id
	 * @param Long id
	 * @return Ressource ressource : ressource.getId() == id
	 * @throws DAOException
	 */
	public Ressource getRessource(Long id) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Ressource ressource = null;
		
		try {
			connection = factory.getConnection();
			preparedStatement = DAOFactory.initializePreparedRequest(
					connection, 
					SQL_SELECT_BY_ID, 
					false,
					id);
			
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				ressource = map(resultSet);
			} else {
				throw new DAOException("Any ressource corresponding to id='" + id + "'.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOFactory.close(resultSet, preparedStatement, connection);
		}
		return ressource;
	}
	
	/**
	 * Requ�te renvoyant un tableu de ressrouce de l'id du responsable donné
	 * @param Long id
	 * @return Ressource ressource : ressource.getId() == id
	 * @throws DAOException
	 */
	public List<Ressource> getResponREssource(long id) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Ressource> ressources = new ArrayList<Ressource>();
		
		try {
			connection = factory.getConnection();
			preparedStatement = DAOFactory.initializePreparedRequest(
					connection, 
					SQL_SELECT_BY_RESPON, 
					false,
					id);
			
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				ressources.add(map(resultSet));			
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOFactory.close(resultSet, preparedStatement, connection);
		}
		return ressources;
	}

	// COMMANDES

	/**
	 * Commande permettant de rajouter l'utilisateur dans la base de donn�es
	 * @param User user
	 * @throws DAOException
	 */
	public void createRessource(Ressource ressource) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			Long maxId = getNextId() + 1;
			connection = factory.getConnection();
			preparedStatement = DAOFactory.initializePreparedRequest(
					connection, 
					SQL_INSERT,
					false,
					maxId, ressource.getLocalisation() , ressource.getUserId(), ressource.getDescription());
			
			int status = preparedStatement.executeUpdate();
			if (status == 0) {
				throw new DAOException("Creating user failed !");
			}
			ressource.setId(maxId);
		} catch (SQLException e) {
			//throw new DAOException(e);
		} finally {
			DAOFactory.close(preparedStatement, connection);
		}
		
	}

	/**
	 * Commande permettant de mettre � jour les donn�es de l'utilisateur d'identifiant id
	 * @param User user
	 * @throws DAOException
	 */
	public void updateRessource(String localisation, long idSource, String description) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = factory.getConnection();
			preparedStatement = DAOFactory.initializePreparedRequest(
					connection, 
					SQL_UPDATE_BY_ID,
					false,
					localisation, description, idSource);
			
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
	protected static Ressource map(ResultSet resultSet) throws SQLException {
		Ressource ressource = new Ressource();
		ressource.setId(resultSet.getLong("idUser"));
		
		return ressource;
	}
}
