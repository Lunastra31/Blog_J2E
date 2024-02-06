/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.Person;
import forms.LoginFormChecker;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rene
 */
@WebServlet(urlPatterns = "/login")
public class Login extends HttpServlet {

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
        // Affichage de la page d'inscription
        request.getServletContext()
                .getRequestDispatcher("/WEB-INF/login.jsp")
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

        // Si l'utilisateur est en session, le redirige vers la page d'accueil
        if (session.getAttribute("bean") != null) {
            response.sendRedirect(request.getServletContext().getContextPath() + "/");
        } else {
            // Traitement du formulaire d'inscription
            LoginFormChecker checker = new LoginFormChecker(request);
            Person person = checker.checkForm();

            // Si le formulaire contient des erreurs, réaffiche le formulaire avec les messages d'erreur
            // et invalide la session
            if (!checker.getErrors().isEmpty()) {
                session.invalidate();
                request.setAttribute("errors", checker.getErrors());
                request.setAttribute("bean", person);
                request.getServletContext()
                        .getRequestDispatcher("/WEB-INF/login.jsp")
                        .forward(request, response);
            } else {
                // Sinon, met l'utilisateur en session et redirige vers la page d'accueil
                session.setAttribute("bean", person);

                // Vérifie le statut d'activation de l'utilisateur
                if (person.isActivate() == false) {
                    // Si l'utilisateur est banni, redirige vers une page spécifique et invalide la session
                    request.getServletContext()
                            .getRequestDispatcher("/WEB-INF/banned.jsp")
                            .forward(request, response);
                    session.invalidate();
                } else if (person.getId_person() == 1) {
                    // Si l'utilisateur est un admin, le met en session et redirige vers la page d'accueil
                    session.setAttribute("loggedIn", true);
                    session.setAttribute("admin", true);
                    response.sendRedirect(request.getServletContext().getContextPath() + "/");
                } else {
                    // Sinon, met l'utilisateur en session et redirige vers la page d'accueil
                    session.setAttribute("loggedIn", true);
                    response.sendRedirect(request.getServletContext().getContextPath() + "/");
                }
            }
        }
    }

}
