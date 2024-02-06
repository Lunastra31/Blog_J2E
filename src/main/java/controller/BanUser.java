/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.Person;
import dao.DaoPerson;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Rene
 */
@WebServlet(urlPatterns = "/admin/banUser")

public class BanUser extends HttpServlet {

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
        // Initialise une variable pour stocker l'identifiant de l'utilisateur
        Integer id = null;

        // Récupère l'utilisateur à partir de la session
        Person user = (Person) request.getSession().getAttribute("bean");

        try {
            // Récupère l'identifiant de l'utilisateur à supprimer depuis les paramètres de la requête
            id = Integer.valueOf(request.getParameter("id"));

            // Initialise un DAO pour gérer les personnes
            DaoPerson daop = new DaoPerson();

            // Supprime l'utilisateur en utilisant l'identifiant et indique de ne pas le bannir
            daop.banUser(id, false);
        } catch (NumberFormatException ex) {
            // Gère une exception si la conversion de l'identifiant échoue
            throw new RuntimeException("Suppression utilisateur impossible");
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
        throw new ServletException("No code here!");
    }

}
