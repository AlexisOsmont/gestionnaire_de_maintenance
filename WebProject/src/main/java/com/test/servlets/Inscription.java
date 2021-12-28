package com.test.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CreationRessource")
public class Inscription extends HttpServlet {
	
	// ATTRIBUTS
	
	private static final String VUE		 	= "/WEB-INF/inscription.jsp";

	// COMMANDES

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		System.out.println(username);
		String password = request.getParameter("password");
		System.out.println(password);
		String role = request.getParameter("role");
		System.out.println(role);
		request.setAttribute("infoRegistration", "Creation de l'utilisateur '" + username + "' failed !");
		doGet(request, response);
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}
}
