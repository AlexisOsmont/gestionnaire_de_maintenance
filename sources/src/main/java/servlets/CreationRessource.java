package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Ressource;
import dao.RessourceDAO;




/**
 * Servlet permettant de g�rer la cr�ation d'un kanban
 * 	Cette page ce trouve � l'url /createKanban et est associ� � la vue /WEB-INF/views/createKanban.jsp
 * 	Cette page n'est accessible que si l'attribut de session 'user' est d�fini, c�d si un utilisateur
 * 		est actuellement connect�
 */


@WebServlet("/CreationRessource")
public class CreationRessource extends HttpServlet {
	
	// ATTRIBUTS
	
	private static final String VUE		 	= "/WEB-INF/views/creationRessource.jsp";
	
	private RessourceDAO ressourceDao;

	// COMMANDES

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			System.out.println("je suis dans le servlets");
			String name = request.getParameter("name");
			String localisation = request.getParameter("localisation");
			String description = request.getParameter("description");

			this.ressourceDao = new RessourceDAO();
			Ressource ressource = new Ressource();
			ressource.setDescription(description);
			ressource.setNom(name);
			ressource.setLocalisation(localisation);
			ressource.setUserId((long) 1);
			ressourceDao.createRessource(ressource);
	        response.sendRedirect(request.getContextPath() + "/Profile");
	}
}
