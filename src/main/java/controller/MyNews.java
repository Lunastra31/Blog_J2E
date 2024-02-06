/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.News;
import beans.Person;
import dao.DaoFactory;
import dao.DaoPerson;
import forms.SuppressionChecker;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author stag
 */
@WebServlet(urlPatterns = "/myNews")
public class MyNews extends HttpServlet {

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
        Integer id_user = ((Person) (request.getSession().getAttribute("bean"))).getId_person();

        // Récupère la collection d'articles écrits par l'utilisateur
        Collection<News> news = DaoFactory.getNewsDao().findByUser(id_user);

        // Ajoute la collection d'articles à la requête
        request.setAttribute("news", news);

        // Redirige vers la page du profil de l'utilisateur (profile.jsp)
        request.getServletContext()
                .getRequestDispatcher("/WEB-INF/myNews.jsp")
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
        // Récupération de la session
        HttpSession session = request.getSession();

        // Traitement du formulaire de suppression
        SuppressionChecker checker = new SuppressionChecker(request);
        Person person = checker.checkForm();

        try {
            // Récupération de l'identifiant de l'utilisateur
            int id = person.getId_person();

            // Vérifie si l'utilisateur n'est pas un administrateur (id != 1)
            if (id != 1) {
                DaoPerson daop = new DaoPerson();

                // Invalide la session et supprime l'utilisateur
                session.invalidate();
                daop.delete(id);
            }
        } catch (NumberFormatException ex) {
            // Gère une exception si la conversion de l'identifiant échoue
            throw new RuntimeException("Suppression utilisateur impossible");
        }

        // Redirige vers la page d'accueil
        response.sendRedirect(request.getServletContext().getContextPath() + "/");
    }

    }

