/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import beans.Comment;
import beans.News;
import beans.Person;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Rene
 */
public class CommentFormChecker extends FormChecker<Comment> {
    private Integer idPerson;
    private Integer idNews;
    private String content;

    public CommentFormChecker(HttpServletRequest request) {
        super(new Comment(), request);
    }

    @Override
    public Comment checkForm() {
        idPerson = ((Person) request.getSession().getAttribute("bean")).getId();
        idNews = Integer.parseInt(request.getParameter("id"));
        content = request.getParameter("content");

        bean.setId_person(idPerson);
        bean.setId_news(idNews);
        bean.setContent_comment(content);
        if (content.isEmpty()) {
            errors.put("content", new RuntimeException("Vous ne pouvez pas envoyez un commentaire vide."));
        }
        return bean;
    }

}
