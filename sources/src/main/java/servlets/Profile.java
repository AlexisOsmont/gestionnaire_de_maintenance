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
		DemandeDAO listDemandes = new DemandeDAO();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		request.setAttribute("demandes", listDemandes.recupDemandesId(user.getId()));

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		System.out.println("dopost profile");
        response.sendRedirect(request.getContextPath() + "/CreationRessource");
	}

}