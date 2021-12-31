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
import beans.Demande;
import beans.Ressource;

@WebServlet("/Accueil")
public class Accueil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Accueil() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("id") == null) {
	        this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);

		}
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		String idRessource = request.getParameter("id");
		
		DemandeDAO listDemandes = new DemandeDAO();
		request.setAttribute("demandes", listDemandes.recupDemandesIdSource(Long.valueOf(idRessource).longValue()));
		
		RessourceDAO ressourceDB = new RessourceDAO();
		Ressource ressource = new Ressource();
		ressource = ressourceDB.recupRessourceId(Integer.valueOf(idRessource).intValue());
		session.setAttribute("ressource", ressource);
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/accueil.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("id") == null) {
	        this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
		}
		
		request.setCharacterEncoding("UTF-8");
		Demande demande = new Demande();
		HttpSession session = request.getSession();
		Ressource ressource = (Ressource) session.getAttribute("ressource");
		RessourceDAO ressourceDao = new RessourceDAO();
		System.out.println(ressource.getId());
		long idRespon = (long) ressourceDao.findRespon(ressource.getId());
		ressource.setUserId(idRespon);
		System.out.println("blabla"+idRespon);
		
		demande.setIdRequest(0);
		demande.setIdUser(000);
		demande.setIdSource(ressource.getId());
		demande.setIdManagerMaint(ressource.getUserId());
		demande.setState("en attente");
		demande.setDescription(request.getParameter("anomalie"));
		
		DemandeDAO tableDemande = new DemandeDAO();
		tableDemande.ajoutDemande(demande);
		System.out.println(demande.toString());
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/accueil.jsp").forward(request, response);
	}

}