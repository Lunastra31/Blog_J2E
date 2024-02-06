package forms;

import beans.Bean;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Herbert Caffarel
 * @param <T> Le type de bean à utiliser
 */
public abstract class FormChecker<T extends Bean> {
    protected HttpServletRequest request;
    /**
     * Le bean à retourner. Il doit donc hériter de la classe abstraite Bean.
     */
    protected T bean;
    /**
     * La liste des erreurs du formulaire pour chaque champ. Les erreurs sont
     * des exceptions de type FormException.
     */
    protected Map<String, RuntimeException> errors;

    /**
     * Constructeur.
     *
     * @param bean Le bean à retourner après vérification
     * @param form Le container présentant le formulaire à vérifier
     */
    public FormChecker(T bean, HttpServletRequest request) {
        this.bean = bean;
        this.errors = new HashMap<>();
        this.request = request;
    }

    /**
     * Getter.
     *
     * @return Le dictionnaire des erreurs. La clé est le champ en erreur, la
     * valeur est une FormException.
     */
    public Map<String, RuntimeException> getErrors() {
        return errors;
    }

    /**
     * Méthode de vérification du formulaire. Contient tous les tests
     * nécessaires et retourne un bean préconstruit avec les valeurs du
     * formulaires.
     *
     * @return Le bean construit avec les données du formulaire.
     */
    public abstract T checkForm();
}
