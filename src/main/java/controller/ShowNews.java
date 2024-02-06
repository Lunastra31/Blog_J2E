/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.Comment;
import beans.News;
import beans.Person;
import dao.DaoComment;
import dao.DaoFactory;
import dao.DaoNews;
import forms.CommentFormChecker;
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
@WebServlet(urlPatterns = "/showNews")
public class ShowNews extends HttpServlet {

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
        Integer id = null;
        try {
            // Récupère l'identifiant de la news à afficher depuis les paramètres de la requête
            id = Integer.valueOf(request.getParameter("id"));
            request.setAttribute("idNews", id);
            // Initialise un DAO pour gérer les news
            DaoNews daon = new DaoNews();
            // Récupère toutes les news de la base de données
            Collection<News> newsList = DaoFactory.getNewsDao().list();
            request.setAttribute("news", newsList);
            
            Collection<Comment> commentList = DaoFactory.getCommentDao().findByNews(id);
            request.setAttribute("comments", commentList);

            if (id != null) {
                // Si un identifiant spécifique a été fourni, lit les données liées à la news
                News news = daon.read(id);
                request.setAttribute("selectedNews", news);
            }
        } catch (NumberFormatException ex) {
            // Gère une exception si la conversion de l'identifiant échoue
            throw new RuntimeException("Récupération de la news impossible", ex);
        }

        // Redirige vers la page showNews après la récupération des données
        request.getRequestDispatcher("/WEB-INF/showNews.jsp").forward(request, response);
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
        //Traitement du formulaire de création du commentaire
        CommentFormChecker checker = new CommentFormChecker(request);
        Comment comment = checker.checkForm();
        //Si le formulaire est faux, on le réaffiche avec les messages d'erreurs
        if (!checker.getErrors().isEmpty()) {
            request.setAttribute("errors", checker.getErrors());
            request.setAttribute("bean", comment);
            request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/showNews.jsp")
                    .forward(request, response);
        } //sinon on le renvoie sur la page du news
        else {
            DaoComment daoc = new DaoComment();
            daoc.insert(comment);
            response.sendRedirect(request.getServletContext()
                    .getContextPath() + "/showNews?id=" + comment.getId_news());
        }
    }
}
