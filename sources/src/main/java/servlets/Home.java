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

import beans.User;
import dao.DemandeDAO;
import dao.UserDAO;




/**
 * Servlet permettant de g�rer la cr�ation d'un kanban
 * 	Cette page ce trouve � l'url /createKanban et est associ� � la vue /WEB-INF/views/createKanban.jsp
 * 	Cette page n'est accessible que si l'attribut de session 'user' est d�fini, c�d si un utilisateur
 * 		est actuellement connect�
 */


@WebServlet("/Home")
public class Home extends HttpServlet {
	
	// ATTRIBUTS
	
	private static final String VUE		 	= "/WEB-INF/views/home.jsp";
	
	private UserDAO userDao;

	// COMMANDES

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String passwd = request.getParameter("password");
			String userName = request.getParameter("login");
			this.userDao = new UserDAO();
			User userFound = userDao.getUserName(userName);
			System.out.println("user dans servlets home :"+userFound.getUsername());
			if (userFound == null) {
				request.setAttribute("infoLogin", "Data in login form was invalid, the password is not correct !");
	            doGet(request, response);
			} else {
				HttpSession session = request.getSession();

				session.setAttribute("user",  userFound);
				System.out.println("user dans servlets home :"+userFound.getRole());
				DemandeDAO listDemandes = new DemandeDAO();
				session.setAttribute("demandes", listDemandes.recupDemandes());
		        response.sendRedirect(request.getContextPath() + "/Profile");

	           // this.getServletContext().getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);	
			}	
	}
}
