package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DemandeDAO;
import beans.Demande;
import beans.User;

@WebServlet("/Profile")
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		User userFound = (User) session.getAttribute("user");
		System.out.println("user dans servlets doget Profile :"+userFound.getUsername());
		
		DemandeDAO listDemandes = new DemandeDAO();
		session.setAttribute("demandes", listDemandes.recupDemandes());

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/creationRessource.jsp").forward(request, response);

		//Pour vérifier l'ajout
		  //this.getServletContext().getRequestDispatcher("/WEB-INF/views/CreationCompte").forward(request, response);
	}

}