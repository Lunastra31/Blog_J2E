package controller;

import beans.Person;
import dao.DaoFactory;
import dao.DaoNews;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Fabien
 */
@WebServlet(urlPatterns = {"/admin/deleteNews"})
public class DeleteNewsAdmin extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Initialise une variable pour stocker l'identifiant de la news à supprimer
        Integer id = null;

        // Récupère l'utilisateur à partir de la session
        Person user = (Person) request.getSession().getAttribute("bean");

        try {
            // Récupère l'identifiant de la news à supprimer depuis les paramètres de la requête
            id = Integer.valueOf(request.getParameter("id"));

            // Initialise un DAO pour gérer les news
            DaoNews daon = DaoFactory.getNewsDao();

            // Supprime l'article en utilisant l'identifiant
            daon.delete(id);
        } catch (NumberFormatException ex) {
            // Gère une exception si la conversion de l'identifiant échoue
            throw new RuntimeException("Suppression de l'article impossible");
        }

        // Redirige vers la page d'administration après la suppression
        response.sendRedirect(request.getServletContext().getContextPath() + "/admin");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        throw new ServletException("No code in doPost() here!");
    }
}
