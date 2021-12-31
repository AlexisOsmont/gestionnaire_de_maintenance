package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	private static final String SQL_INSERT 						= String.format("INSERT INTO Utilisateurs VALUES (?, ?, ?, ?);", DAOFactory.TABLE_USER);
	

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
			String query = "select * from Utilisateurs where username IN (?) AND pwd IN (?)";
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/projetWebDataBase","Alexis","Alexis");
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
	
	// COMMANDES

	/**
	 * Commande permettant de rajouter l'utilisateur dans la base de donn�es
	 * @param User user
	 * @throws DAOException
	 */
	public void createUser(User user) throws DAOException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/projetWebDataBase","Alexis","Alexis");
			PreparedStatement pst = con.prepareStatement(SQL_INSERT);
			pst.setLong(1,0);
			pst.setString(2,user.getUsername());
			pst.setString(3,user.getPassword());
			pst.setString(4,user.getRole());
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
	protected static User map(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getLong("idUser"));
		user.setUsername(resultSet.getString("username"));
		user.setPassword(resultSet.getString("pwd"));
		user.setRole(resultSet.getString("job"));
		
		return user;
	}
}
