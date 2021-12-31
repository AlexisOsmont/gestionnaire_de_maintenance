package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DemandeDAO;
import beans.Demande;

@WebServlet("/Accueil")
public class Accueil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Accueil() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		DemandeDAO listDemandes = new DemandeDAO();
		request.setAttribute("demandes", listDemandes.recupDemandes());
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/accueil.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		Demande demande = new Demande();
		
		demande.setIdRequest(0);
		demande.setIdUser(8);
		demande.setIdSource(3);
		demande.setIdManagerMaint(1);
		demande.setState("Test ajout d'une requête");
		demande.setDescription(request.getParameter("anomalie"));
		
		DemandeDAO tableDemande = new DemandeDAO();
		tableDemande.ajoutDemande(demande);
		
		//Pour vérifier l'ajout
		request.setAttribute("demandes", tableDemande.recupDemandes());
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/accueil.jsp").forward(request, response);
	}

}