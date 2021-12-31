package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	private static final String SQL_INSERT 						= String.format("INSERT INTO Ressources VALUES (?, ?, ?, ?, ?);", DAOFactory.TABLE_RESSOURCE);
		
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
			pst.setString(3,ressource.getLocalisation());
			pst.setString(4,ressource.getDescription());
			pst.setString(5,ressource.getNom());
			pst.executeUpdate();
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
	protected static Ressource map(ResultSet resultSet) throws SQLException {
		Ressource ressource = new Ressource();
		ressource.setId(resultSet.getLong("idUser"));
		
		return ressource;
	}
}
