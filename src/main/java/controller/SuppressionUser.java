/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.Person;
import dao.DaoPerson;
import forms.SuppressionChecker;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author stag
 */
@WebServlet(urlPatterns = "/myNews/delete")
public class SuppressionUser extends HttpServlet {

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
        // Traitement de la demande de suppression
        SuppressionChecker checker = new SuppressionChecker(request);
        Person person = checker.checkForm();

        try {
            // Récupère l'identifiant de l'utilisateur à supprimer
            int id = person.getId_person();

            // Vérifie si l'utilisateur n'est pas un administrateur (id != 1)
            if (id != 1) {
                DaoPerson daop = new DaoPerson();

                // Supprime l'utilisateur
                daop.delete(id);
            }
        } catch (NumberFormatException ex) {
            // Gère une exception si la conversion de l'identifiant échoue
            throw new RuntimeException("Suppression utilisateur impossible");
        }

        // Redirige vers la page d'accueil après la suppression
        response.sendRedirect(request.getServletContext().getContextPath() + "/index");
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
