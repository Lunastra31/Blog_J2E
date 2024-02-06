package controller;

import dao.DaoNews;
import dao.DaoPerson;
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
@WebServlet(urlPatterns = "/admin")
public class Admin extends HttpServlet {

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
        // Initialise un DAO pour gérer les personnes
        DaoPerson daop = new DaoPerson();

        // Récupère la liste des utilisateurs sans l'administrateur
        request.setAttribute("users", daop.listWithoutAdmin());

        // Initialise un DAO pour gérer les news
        DaoNews daon = new DaoNews();

        // Récupère la liste des news
        request.setAttribute("news", daon.list());

        // Redirige vers la page du système d'administration (admin.jsp)
        request.getServletContext()
                .getRequestDispatcher("/WEB-INF/admin.jsp")
                .forward(request, response);

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
