/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import beans.Person;
import dao.DaoPerson;
import javax.servlet.http.HttpServletRequest;
import libs.PasswordAuthentication;

/**
 *
 * @author stag
 */
public class SuppressionChecker extends FormChecker<Person> {

    public SuppressionChecker(HttpServletRequest request) {
        super(new Person(), request);
    }

    @Override
    public Person checkForm() {
        //Récupérer les données du formulaire
        String login = request.getParameter("login");
        String pwd = request.getParameter("password");
        //Remplir le bean
        bean.setLogin(login);
        bean.setPassword(pwd);
        //Condition sur le login : n'existe pas déjà et mdp correct
        if (errors.isEmpty()) {
            PasswordAuthentication authenticator = new PasswordAuthentication();
            DaoPerson p = new DaoPerson();
            Person fromDb = p.findByLogin(login);
            if (fromDb == null || !authenticator.authenticate(pwd.toCharArray(), fromDb.getPassword())) {
                errors.put("password", new RuntimeException("Erreur dans la saisie"));
            } else {
                bean = fromDb;
                }
            }         
        return bean;
        }
    }        

