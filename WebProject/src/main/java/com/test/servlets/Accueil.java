package com.test.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.bdd.Demandes;
import com.test.beans.Demande;

@WebServlet("/Accueil")
public class Accueil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Accueil() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Demandes listDemandes = new Demandes();
		request.setAttribute("demandes", listDemandes.recupDemandes());
        this.getServletContext().getRequestDispatcher("/WEB-INF/accueil.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		Demande demande = new Demande();
		
		demande.setIdUser(97);
		demande.setIdSource(58);
		demande.setIdManagerMaint(24);
		demande.setState("Nouveau Test pour ID Request");
		demande.setDescription(request.getParameter("anomalie"));
		
		Demandes tableDemande = new Demandes();
		tableDemande.ajoutDemande(demande);
		
		//Pour vérifier l'ajout
		request.setAttribute("demandes", tableDemande.recupDemandes());
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/accueil.jsp").forward(request, response);
	}

}