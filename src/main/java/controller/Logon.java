package controller;

import beans.Person;
import controllers.LogonFormChecker;
import dao.DaoPerson;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/logon")
public class Logon extends HttpServlet {

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
                .getRequestDispatcher("/WEB-INF/logon.jsp")
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
            LogonFormChecker checker = new LogonFormChecker(request);
            Person person = checker.checkForm();

            // Si le formulaire contient des erreurs, réaffiche le formulaire avec les messages d'erreur
            // et invalide la session
            if (!checker.getErrors().isEmpty()) {
                session.invalidate();
                request.setAttribute("errors", checker.getErrors());
                request.setAttribute("bean", person);
                request.getServletContext()
                        .getRequestDispatcher("/WEB-INF/logon.jsp")
                        .forward(request, response);
            } else {
                // Sinon, persiste l'utilisateur en base de données, le met en session et redirige vers la page d'accueil
                DaoPerson daop = new DaoPerson();
                daop.insert(person);

                // Vérifie si l'utilisateur est un administrateur
                if (person.getId_person() != null && person.getId_person() == 1) {
                    session.setAttribute("admin", true);
                }

                // Redirige vers la page d'accueil
                response.sendRedirect(request.getServletContext().getContextPath() + "/");
            }
        }
    }

}
