package controllers;

import beans.Person;
import forms.FormChecker;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import libs.PasswordAuthentication;

public class LogonFormChecker extends FormChecker<Person> {

    private String login, pwd, confirm;

    public LogonFormChecker(HttpServletRequest request) {
        super(new Person(), request);
    }

    @Override
    public Person checkForm() {
        // Récupérer les données du formulaire
        login = request.getParameter("login");
        pwd = request.getParameter("password");
        confirm = request.getParameter("confirm");
        // Remplir le bean avec mot de passe chiffré
        PasswordAuthentication pa = new PasswordAuthentication();
        bean.setLogin(login);
        bean.setPassword(pa.hash(pwd.toCharArray()));
        // Vérifier les données du formulaire et remplire le dictionnaire errors
        if (!isValidEmail(login)) {
            errors.put("login", new RuntimeException("Votre mail est invalide"));
        }
        if (pwd.length() < 3) {
            errors.put("password", new RuntimeException("Mot de passe trop court, minimum 3 caractères"));
        }
        if (!confirm.equals(pwd)) {
            errors.put("confirm", new RuntimeException("Les mots de passes ne sont pas identiques"));
        }

        // Retourner le bean
        return bean;
    }

    public Person checkFormLogin() {
        // Récupérer les données du formulaire
        login = request.getParameter("login");
        // Remplir le bean avec mot de passe chiffré
        bean.setLogin(login);
        // Retourner le bean
        return bean;
    }
// Méthode pour vérifier si l'adresse e-mail est valide en utilisant une regex

    private boolean isValidEmail(String email) {
        // Définition de l'expression régulière pour correspondre à une adresse e-mail
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        // Création d'un objet Pattern à partir de l'expression régulière
        Pattern pattern = Pattern.compile(regex);
        // Création d'un objet Matcher pour appliquer l'expression régulière à l'adresse e-mail
        Matcher matcher = pattern.matcher(email);
        // Utilisation de la méthode matches() pour vérifier si l'adresse e-mail correspond à l'expression régulière
        boolean isEmailValid = matcher.matches();
        // Retour de la valeur booléenne indiquant si l'adresse e-mail est valide
        return isEmailValid;
    }
}
