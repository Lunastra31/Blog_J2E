/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.News;
import beans.Person;
import dao.DaoFactory;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author stag
 */
@WebServlet(urlPatterns = "/manageProfile")
public class ManageProfile extends HttpServlet {

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
        // Récupère l'identifiant de l'utilisateur à partir de la session
        Integer id_user = ((Person) (request.getSession().getAttribute("bean"))).getId();

        // Récupère la collection d'articles écrits par l'utilisateur
        Collection<News> news = DaoFactory.getNewsDao().findByUser(id_user);

        // Ajoute la collection d'articles à la requête
        request.setAttribute("news", news);

        // Redirige vers la page du profil de l'utilisateur (MyNews.jsp)
        request.getServletContext()
                .getRequestDispatcher("/WEB-INF/manageProfile.jsp")
                .forward(request, response);
    }

}
