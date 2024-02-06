/*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
 */
package controller;

import beans.Person;
import beans.Vote;
import dao.DaoNews;
import dao.DaoVote;
import java.io.IOException;
import java.util.Date;
import dao.DaoVote;
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
@WebServlet(urlPatterns = "/Negative")
public class Negative extends HttpServlet {

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
        // Récupérer l'id de la news
        Integer id_news = Integer.parseInt(request.getParameter("id_news"));
        // Récupérer id de l'utilisateur => dans la session
        Integer id_user = (((Person) (request.getSession().getAttribute("bean"))).getId_person());
        // Ouvrir une DAO de vote
        DaoVote daov = new DaoVote();
        boolean hasVoted = daov.checkIfUserHasVoted(id_user, id_news);
        // Creation d'une daoNews pour ensuite récuperer sa date de création
        DaoNews daon = new DaoNews();
        // Récupération de la date de création de la news qui est un timestamp pour pouvoir ensuite la comparer à la date actuelle
        Date createdNews = daon.getCreationDate(id_news);
        // Création d'une instance de date pour ensuite pouvoir récupérer la date actuelle
        Date currentDate = new Date();
        // getTime() nous permet de récuperer la date actuelle, on soustrait ensuite la timestamp de la création de l'artile à la timestamp actuelle, on récupère une différence en milliseconde
        double differenceInMilliseconds = currentDate.getTime() - createdNews.getTime();
        // On convertit la différence en millisecondes en jour pour nous facilité la tâche
        double differenceInDays = differenceInMilliseconds / (24 * 60 * 60 * 1000);
        int votesNegatifs = daov.countNegative(id_news);
        request.getSession().setAttribute("votesNegatif", votesNegatifs);
        // Si la différence de jour est supérieur à 15 , l'utilisateur ne peux plus voter car le délai est dépassé
        if (differenceInDays > 15) {
            response.sendRedirect(request.getServletContext().getContextPath() + "/voteTooLate.jsp");
        } else if (!hasVoted) {
            // L'utilisateur n'a pas encore voté pour cette news
            // Insérer le vote dans la table
            Vote vote = new Vote();
            vote.setId_news(id_news);
            vote.setId_person(id_user);
            vote.setPositive(0);
            vote.setNegative(1);
            vote.setVoted(true);
            daov.insert(vote);

            // Afficher la page d'accueil
            response.sendRedirect(request.getServletContext().getContextPath() + "/showNews");
        } else {
            // Si l'utilisateur a déja voté pour cette news il ne pourra pas revoter
            response.sendRedirect(request.getServletContext().getContextPath() + "/voteError.jsp");
        }
    
}

/**
 * Handles the HTTP <code>POST</code> method.
 *
 * @param request servlet request
 * @param response servlet response
 * @throws ServletException if a servlet-specific error occurs
 * @throws IOException if an I/O error occurs
 */
}
