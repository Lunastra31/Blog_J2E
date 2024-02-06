package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import libs.Helpers;

/**
 * Implémentation du singleton pour la connexion à la BD pour empêcher
 * l'héritage et donc la création de multiples singletons ; on déclare la classe
 * en final
 *
 * @author Fabien
 */
public final class MySqlConnection {

    private static Connection instance;  // Instance unique de la connexion

    /**
     * Constructeur privé pour empêcher l'instanciation directe
     */
    private MySqlConnection() {
    }

    /**
     * Récupère l'instance de la connexion à la base de données
     *
     * @return L'instance de la connexion à la base de données
     */
    public static Connection getInstance() {
        if (instance == null) {
            try {
                // Récupération des informations de configuration
                Properties config = Helpers.getConfig();
                // Chargement du driver JDBC
                Class.forName(config.getProperty("driver"));
                // Établissement de la connexion à la base de données
                instance = DriverManager.getConnection(
                        config.getProperty("protocol") + "://" // protocole de l'URL
                        + config.getProperty("host") + ":" // hôte de l'URL
                        + config.getProperty("port") + "/" // port de l'URL
                        + config.getProperty("dbname"), // nom de la base de données dans l'URL
                        config.getProperty("login"), // login
                        config.getProperty("password") // mot de passe
                );
            } catch (ClassNotFoundException | SQLException | RuntimeException ex) {
                // Gestion des exceptions en cas de problème de connexion
                throw new RuntimeException("Il y a eu un problème de connexion à la DB causé par ", ex);
            }
        }
        return instance;  // Retourne l'instance de connexion
    }

}
