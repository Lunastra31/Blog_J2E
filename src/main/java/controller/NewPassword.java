/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.Person;
import forms.NewPasswordChecker;
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
@WebServlet(urlPatterns = "/user/profile")
public class NewPassword extends HttpServlet {

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
        // Redirige vers la page de création d'un nouveau mot de passe (newPassword.jsp)
        request.getServletContext()
                .getRequestDispatcher("/WEB-INF/newPassword.jsp")
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
        // Traitement du formulaire de changement de mot de passe
        NewPasswordChecker checker = new NewPasswordChecker(new Person(), request);
        Person person = checker.checkForm();

        // S'il y a des erreurs, redirige vers la page du formulaire avec les erreurs
        if (!checker.getErrors().isEmpty()) {
            request.setAttribute("errors", checker.getErrors());
            request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/manageProfile.jsp")
                    .forward(request, response);
        } else {
            // Affiche un message de succès et redirige vers la page de réussite
            request.setAttribute("successMessage", "Mot de passe mis à jour avec succès !");
            request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/success.jsp")
                    .forward(request, response);
        }
    }
}
