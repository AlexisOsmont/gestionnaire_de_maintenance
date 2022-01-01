package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DemandeDAO;
import dao.RessourceDAO;
import dao.UserDAO;
import beans.User;

@WebServlet("/Profile")
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		DemandeDAO listDemandes = new DemandeDAO();
		RessourceDAO listRessource = new RessourceDAO();
		UserDAO listUser = new UserDAO();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
	        response.sendRedirect(request.getContextPath() + "/Home");
		}

		request.setAttribute("demandes", listDemandes.recupDemandesId(user.getId()));
		request.setAttribute("ressource", listRessource.recupRessourceRespId(user.getId()));
		request.setAttribute("Users", listUser.recupRespon());
		
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		System.out.println("dopost profile");
		DemandeDAO listDemandes = new DemandeDAO();
		RessourceDAO listRessource = new RessourceDAO();
		UserDAO listUser = new UserDAO();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		request.setAttribute("demandes", listDemandes.recupDemandesId(user.getId()));
		request.setAttribute("ressource", listRessource.recupRessourceRespId(user.getId()));
		request.setAttribute("Users", listUser.recupRespon());
		if (request.getParameter("CreationRessource") != null) {
	        response.sendRedirect(request.getContextPath() + "/CreationRessource");
		} else if (request.getParameter("generateURL") != null) {
			request.setAttribute("ressourceId",  request.getParameter("generateURL"));
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/url.jsp").forward(request, response);
		} else if (request.getParameter("CreationUser") != null) {
	        response.sendRedirect(request.getContextPath() + "/CreationCompte");
		} else if (request.getParameter("suppRessource") != null) {
			RessourceDAO dao = new RessourceDAO();
			dao.suppRessource( (long) Integer.parseInt(request.getParameter("suppRessource")));
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
		}  else if (request.getParameter("suppUser") != null) {
			UserDAO dao = new UserDAO();
			dao.suppUser( (long) Integer.parseInt(request.getParameter("suppUser")));
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
		} else if (request.getParameter("validerDemande") != null) {
			DemandeDAO dao = new DemandeDAO();
			dao.setDemandeValide( (long) Integer.parseInt(request.getParameter("validerDemande")));
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
		} else {
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
		}
	}

}