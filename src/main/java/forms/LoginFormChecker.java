package forms;

import beans.Person;
import dao.DaoFactory;
import javax.servlet.http.HttpServletRequest;
import libs.PasswordAuthentication;

public class LoginFormChecker extends FormChecker<Person> {

    private String login, pwd;

    public LoginFormChecker(HttpServletRequest request) {
        super(new Person(), request);
    }

    @Override
    public Person checkForm() {
        // Récupérer les données du formulaire
        login = request.getParameter("login");
        pwd = request.getParameter("password");
        // Remplir le bean avec mot de passe chiffré
        bean.setLogin(login);
        PasswordAuthentication pa = new PasswordAuthentication();
        bean.setPassword(pa.hash(pwd.toCharArray()));
        // Vérifier les données du formulaire et remplir le dictionnaire errors
        if (login.trim().length() < 3) {
            errors.put("login", new RuntimeException("Login incorrect"));
        }
        if (pwd.length() < 3) {
            errors.put("password", new RuntimeException("Mot de passe trop court"));
        }
        // Vérifier si l'utilisateur existe en DB
        if (errors.isEmpty()) {
            Person fromDb = DaoFactory.getPersonDao().findByLogin(login);

            if (fromDb == null || !pa.authenticate(pwd.toCharArray(), fromDb.getPassword())) {
                System.out.println(fromDb.getPassword());
                System.out.println(bean);
                errors.put("login", new RuntimeException("Couple login/mot de passe faux."));
            } else {
                bean = fromDb;
            }
        }
        // retourner le bean
        return bean;
    }

}
