package com.test.bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.test.beans.Demande;

public class Demandes {
	private Connection connexion;
	
	public List<Demande> recupDemandes() {
		List<Demande> listDemandes = new ArrayList<Demande>();
		java.sql.Statement statement = null; //représente requête SQL
		ResultSet resultat = null;
		
		loadDatabase();
		System.out.println("Chargement de la database fait");
		
		try {			
			statement = connexion.createStatement();
			
			// Exécution de la requête
			resultat = statement.executeQuery("SELECT * FROM Demande WHERE 1");
			System.out.println("Exécution de la requete fait");
			
			// Récupération des données
			while (resultat.next()) {
				System.out.println(resultat);
				int idRequest = resultat.getInt("idRequest");
				System.out.println("Récupération de idRequest : " + String.valueOf(idRequest));
				int idUser = resultat.getInt("idUser");
				System.out.println("Récupération de idUser : " + String.valueOf(idUser));
				int idSource = resultat.getInt("idSource");
				System.out.println("Récupération de idSource : " + String.valueOf(idSource));
				int idManagerMaint = resultat.getInt("idManagerMaint");
				System.out.println("Récupération de idManagerMaint : " + String.valueOf(idManagerMaint));
				String state = resultat.getString("state");
				System.out.println("Récupération de state : " + state);
				String description = resultat.getString("description");
				System.out.println("Récupération de description : " + description);
				
				Demande demande = new Demande();
				demande.setIdRequest(idRequest);
				demande.setIdUser(idUser);
				demande.setIdSource(idSource);
				demande.setIdManagerMaint(idManagerMaint);
				demande.setState(state);
				demande.setDescription(description);
				
				System.out.println("Création de l'objet fait");
				
				listDemandes.add(demande);
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
		return listDemandes;
	}
	
	
	public void ajoutDemande(Demande demande) {
		loadDatabase();
		
		try {
			PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO Demande(idRequest, idUser, idSource, idManagerMaint, state, description) VALUES(?,?,?,?,?,?);");
			preparedStatement.setInt(1, demande.getIdRequest());
			preparedStatement.setInt(2, demande.getIdUser());
			preparedStatement.setInt(3, demande.getIdSource());
			preparedStatement.setInt(4, demande.getIdManagerMaint());
			preparedStatement.setString(5, demande.getState());
			preparedStatement.setString(6, demande.getDescription());
			
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// UTILS
	
	// charge le driver JDBC et se connecte à la database "projetWebDataBase"
	private void loadDatabase() {
		// Chargement du driver JDBC
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		}
		
		try {
			connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetWebDataBase", "projetWeb", "bewprojet");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
