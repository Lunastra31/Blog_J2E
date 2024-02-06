package forms;

import beans.News;
import beans.Person;
import dao.DaoFactory;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Fabien
 */
public class NewsFormChecker extends FormChecker<News> {

    private Integer user;
    private String title;
    private String content;

    public NewsFormChecker(HttpServletRequest request) {
        super(new News(), request);
    }

    @Override
    public News checkForm() {
        // Récupérer les données du formulaire
        user = ((Person) request.getSession().getAttribute("bean")).getId();
        title = request.getParameter("title");
        content = request.getParameter("content");

        // Créer et retourner l'objet Article
        bean.setId_person(user);
        bean.setTitle_news(title);
        bean.setContent_news(content);

        // condition sur title : plus de 10 caractères
        if (title.trim().length() < 10) {
            errors.put("title", new RuntimeException("titre trop court (au moins 10 caractères)"));
        }
        // condition sur content : plus de 25 caractères
        if (content.trim().length() < 25) {
            errors.put("content", new RuntimeException("Contenu trop court (au moins 25 caractères)"));
        }

        // Si aucune erreur, enregistrez l'article dans la base de données
        if (errors.isEmpty()) {
            DaoFactory.getNewsDao().insert(bean);  // Insérer l'article dans la base de données
        }

        return bean;
    }

}
