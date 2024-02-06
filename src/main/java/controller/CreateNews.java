/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.News;
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
@WebServlet(urlPatterns = "/createNews")
public class CreateNews extends HttpServlet {

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
        // Redirige vers la page de création d'article (createNews.jsp)
        request.getServletContext()
                .getRequestDispatcher("/WEB-INF/createNews.jsp")
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
        // Traitement du formulaire de création de la news
        forms.NewsFormChecker checker = new forms.NewsFormChecker(request);

        // Vérifie et récupère les données du formulaire
        News bean = checker.checkForm();

        // Si le formulaire contient des erreurs, réaffiche le formulaire avec les messages d'erreurs
        if (!checker.getErrors().isEmpty()) {
            request.setAttribute("errors", checker.getErrors());
            request.setAttribute("bean", bean);
            request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/createNews.jsp")
                    .forward(request, response);
        } else {
            // Sinon, redirige vers la page du profil de l'utilisateur
            response.sendRedirect(request.getServletContext()
                    .getContextPath() + "/myNews");
        }
    }

}
