package com.test.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet permettant de g�rer la cr�ation d'un kanban
 * 	Cette page ce trouve � l'url /createKanban et est associ� � la vue /WEB-INF/views/createKanban.jsp
 * 	Cette page n'est accessible que si l'attribut de session 'user' est d�fini, c�d si un utilisateur
 * 		est actuellement connect�
 */
@WebServlet("/CreationRessource")
public class CreationRessource extends HttpServlet {
	
	// ATTRIBUTS
	
	private static final String VUE		 	= "/WEB-INF/creationRessource.jsp";

	// COMMANDES

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		System.out.println(name);
		String local = request.getParameter("localisation");
		System.out.println(local);
		String description = request.getParameter("description");
		System.out.println(description);
		request.setAttribute("infoCreation", "Creation de la ressource '" + name + "' failed !");
		doGet(request, response);
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}
}
