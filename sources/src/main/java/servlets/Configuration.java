package servlets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;

/**
 * Servlet permettant de g�rer la page de configuration de connexion � la base de donn�es
 * 	Cette page ce trouve � l'url /configuration et est associ� � la vue /WEB-INF/views/welcomePage.jsp
 */
@WebServlet("/configuration")
public class Configuration extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private static final String VUE		 	= "/WEB-INF/views/configuration.jsp";
	private static final String VUE_HOME 	= "/WEB-INF/views/home.jsp";
   
    // COMMANDES

	/**
	 * Commande appel� lorsqu'une personne envoie des informations � la page /configuration � l'aide de la m�thode POST
	 * 	Cette m�thode est utilis� afin de r�cup�rer les donn�es de configuration de connexion � la base de donn�es qui 
	 * 		ce fait en 2 temps:
	 * 				1) Doit v�rifier que la configuration donn� est correcte
	 * 				2) Enregistrer la configuration dans un fichier
	 * 
	 * La partie v�rification de la configuration a �t� commenc� mais n'as malheureusement pas pu �tre fini.
	 * Cependant, le code le v�rifiant a d�j� �t� mis en place sous commentaire, il faut v�rifier c�t� DAOFactory TODO
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("type") != null) {
			
			/*
			 * Partie de v�rification de la configuration de la connexion � la base de donn�es
			 */
			if (request.getParameter("type").equals("check")) {
				//if (!DAOFactory.verifyConfiguration(request.getParameter("scheme"), request.getParameter("adress"),
				//									request.getParameter("username"), request.getParameter("password"))) { 
				//	request.setAttribute("infoConfiguration", "Base de donn�e non valide");
				//} else {
					request.setAttribute("conf", true);
					request.setAttribute("conf_scheme", request.getParameter("scheme"));
					request.setAttribute("conf_adress", request.getParameter("adress"));
					request.setAttribute("conf_username", request.getParameter("username"));
					request.setAttribute("conf_password", request.getParameter("password"));
					request.setAttribute("infoConfiguration", "r�cup�ration de check");
				//}
				this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
				
			/*
			 * Partie de sauvegarde de la configuration dans le dossier /src/dao/dao.properties
			 */
			} else if (request.getParameter("type").equals("validate")) {
				request.setAttribute("conf", true);
				request.removeAttribute("conf_scheme");
				request.removeAttribute("conf_adress");
				request.removeAttribute("conf_username");
				request.removeAttribute("conf_password");
				BufferedWriter bw = null;
				try {
					
					// On r�cup�re le chemin du fichier o� enregistrer la configuration (/src/dao/dao.properties
					String path = DAOFactory.class.getResource("dao.properties").getFile();
					File file = new File(path);
					
					// On (re)cr�er le fichier en lui donnant tout les droits
					if (!file.exists()) {
						file.createNewFile();
					} else {
						file.delete();
						file.createNewFile();
					}
					if (!file.canRead()) {
						file.setReadable(true);
					}
					if (!file.canWrite()) {
						file.setWritable(true);
					}
						
					FileWriter fw = new FileWriter(file);
					bw = new BufferedWriter(fw);

					String scheme   = request.getParameter("scheme");
					String adress   = request.getParameter("adress");
					String username = request.getParameter("username");
					String password = request.getParameter("password");
					
					// On enregistre la configuration sous la forme 
					//		url = jdbc:mysql://adress:3306/scheme
					//		driver = com.mysql.jdbc.Driver
					//		user = username
					//		password = password
					bw.write("url = jdbc:mysql://" + adress + ":3306/" + scheme);
					bw.newLine();
					bw.write("driver = com.mysql.jdbc.Driver");
					bw.newLine();
					bw.write("user = " + username);
					bw.newLine();
					bw.write("password = " + password);
				} catch (IOException e) {
					// TODO
					request.setAttribute("infoConfiguration", "erreur �crite de la configuration : " + e);
				} finally {
					try {
						// Si tout s'est bien pass�, on ferme les instances des objets et on met � jour
						//	DAOFactory en disant que la base de donn�e � bien �t� v�rifi�.
						// 	A pour cons�quence de faire renvoyer vrai par DAOFactory.dbIsValide()
						if (bw != null) {
							bw.close();
						}
						DAOFactory.setDataBase();
						DAOFactory.insertAdmin("jdbc:mysql://" + request.getParameter("adress") + ":3306/" + request.getParameter("scheme"),request.getParameter("username"),request.getParameter("password"));
						DAOFactory.insertRespon("jdbc:mysql://" + request.getParameter("adress") + ":3306/" + request.getParameter("scheme"),request.getParameter("username"),request.getParameter("password"));
					} catch (Exception e) {
						// TODO
						request.setAttribute("infoConfiguration", "erreur fermeture de la configuration : " + e);
					}
					response.sendRedirect(request.getContextPath() + "/Home");
				}
			}
		}
	}

	/**
	 * Commande appel� lorsqu'une personne souhaite r�cup�rer le contenu de la page /configuration � l'aide de la m�thode GET
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}
}
