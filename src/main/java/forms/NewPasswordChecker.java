/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import beans.Person;
import dao.DaoFactory;
import javax.servlet.http.HttpServletRequest;
import libs.PasswordAuthentication;

/**
 *
 * @author stag
 */
public class NewPasswordChecker extends FormChecker<Person> {

    private String ancient;
    private String newP;
    private String confirm;
    private final Person user;

    public NewPasswordChecker(Person bean, HttpServletRequest request) {
        super(bean, request);
        user = (Person) request.getSession().getAttribute("bean");
    }

    @Override
    public Person checkForm() {

        //Récupérer les données du formulaire
        ancient = request.getParameter("ancient");
        newP = request.getParameter("newP");
        confirm = request.getParameter("confirm");

        //Remplir le bean avec mot de passe chiffré
        // Construction du bean avec le nouveau mot de passe haché
        PasswordAuthentication authenticator = new PasswordAuthentication();
        bean.setId_person(user.getId_person());
        bean.setLogin(user.getLogin());
        bean.setActivate(user.isActivate());
        bean.setPassword(authenticator.hash(newP.toCharArray()));

        if (!authenticator.authenticate(ancient.toCharArray(), user.getPassword())) {
            // Previous password provided is incorrect
            errors.put("ancient", new RuntimeException("Ancien mot de passe incorrect"));
        }

        //Vérifier les données du formulaire et remplir le dictionnaire d'erreurs
        if (ancient.trim().length() < 3) {
            errors.put("ancient", new RuntimeException("Mot de passe trop court (minimum 3 lettres)."));
        }
        if (newP.length() < 3) {
            errors.put("newP", new RuntimeException("Mot de passe trop court (minimum 3 lettres)"));
        }
        if (!confirm.equals(newP)) {
            errors.put("confirm", new RuntimeException("Les mots de passes ne correspondent pas"));
        }

        // Si aucune erreur, enregistrez le nouveau mot de passe dans la base de données
        if (errors.isEmpty()) {
            DaoFactory.getPersonDao().update(bean);
        }

        //retourner le bean
        return bean;
    }

}
