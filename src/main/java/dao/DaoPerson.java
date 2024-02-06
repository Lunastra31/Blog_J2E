package dao;

import beans.Person;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fabien
 */
public class DaoPerson extends Dao<Person> {

    /**
     * Constructeur de la classe DaoPerson. Il spécifie le nom de la table
     * "person" qui sera gérée par cette classe.
     */
    public DaoPerson() {
        super("userstable");
    }

    /**
     * Crée un objet Person à partir des données d'un ResultSet.
     *
     * @param rs Le ResultSet contenant les données.
     * @return L'objet Person créé à partir des données du ResultSet.
     * @throws SQLException En cas d'erreur lors de l'accès aux données du
     * ResultSet.
     */
    @Override
    protected Person createObject(ResultSet rs) throws SQLException {
        Person bean = new Person(); // Crée une nouvelle instance de l'objet Person
        bean.setId_person(rs.getInt("id_person")); // Récupère et attribue l'ID de la personne
        bean.setLogin(rs.getString("login")); // Récupère et attribue le login de la personne
        bean.setPassword(rs.getString("password")); // Récupère et attribue le mot de passe de la personne
        bean.setActivate(rs.getBoolean("activate")); // Récupère et attribue l'activité de la personne
        return bean; // Retourne l'objet Person créé avec les données du ResultSet
    }

    /**
     * Lit une personne à partir de la base de données en utilisant son
     * identifiant.
     *
     * @param id L'identifiant de la personne à lire.
     * @return L'objet Person correspondant à l'identifiant donné, ou null si
     * non trouvé.
     */
    @Override
    public Person read(Integer id) {
        Person obj = null; // Initialise un objet Person à null
        String sql = "SELECT * FROM " + tableConfigKey + " WHERE id_" + tableConfigKey + "=?"; // Requête SQL pour récupérer une personne par son ID
        try {
            // Prépare la requête SQL avec un paramètre pour l'ID de la personne
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id); // Attribue la valeur de l'ID au paramètre de la requête
            // Exécute la requête et récupère le résultat
            ResultSet rs = pstmt.executeQuery();
            // Si le résultat contient une ligne, crée un objet Person correspondant
            if (rs.first()) {
                obj = createObject(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoPerson.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obj; // Retourne l'objet Person trouvé ou null s'il n'existe pas
    }

    /**
     * Insère une personne dans la base de données.
     *
     * @param obj L'objet Person à insérer.
     */
    @Override
    public void insert(Person obj) {
        String sql = "INSERT INTO " + tableConfigKey + " (login, password, activate) VALUES (?, ?, ?)"; // Requête SQL pour insérer une personne
        try {
            // Prépare la requête SQL avec des paramètres pour le login, le mot de passe et l'ID de rôle de la personne
            PreparedStatement pstmt = connection.prepareStatement(
                    sql, // la requête exécutée
                    PreparedStatement.RETURN_GENERATED_KEYS // Récupérer les clés générées
            );
            pstmt.setString(1, obj.getLogin()); // Attribue le login de la personne au paramètre 1
            pstmt.setString(2, obj.getPassword()); // Attribue le mot de passe de la personne au paramètre 2
            pstmt.setBoolean(3, obj.isActivate()); // Attribue l'activité la personne au paramètre 3

            // Exécute la requête pour insérer la personne dans la base de données
            pstmt.executeUpdate();

            // Récupère les clés générées
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.first()) {
                obj.setId(rs.getInt(1)); // Attribue l'ID généré à la personne
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Utilisateur déjà existant", ex); // Gère une exception si l'utilisateur existe déjà
        }
    }

    /**
     * Met à jour les informations d'une personne dans la base de données.
     *
     * @param obj L'objet Person à mettre à jour.
     */
    @Override
    public void update(Person obj) {
        String sql = "UPDATE " + tableConfigKey
                + " SET login=?, password=?, activate=? WHERE id_"
                + tableConfigKey + "=?"; // Requête SQL pour mettre à jour les informations d'une personne
        try {
            // Prépare la requête SQL avec des paramètres pour le login, le mot de passe, l'ID de rôle et l'ID de la personne
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, obj.getLogin()); // Attribue le login de la personne au paramètre 1
            pstmt.setString(2, obj.getPassword()); // Attribue le mot de passe de la personne au paramètre 2
            pstmt.setBoolean(3, obj.isActivate()); // Attribue l'activité de la personne au paramètre 3
            pstmt.setInt(4, obj.getId());

            // Exécute la requête pour mettre à jour les informations de la personne dans la base de données
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("L'update n'a pas eu lieu", ex);
        }
    }

    public void banUser(Integer id, boolean bool) {
        String sql = "UPDATE " + tableConfigKey + " SET activate=? WHERE id_" + tableConfigKey + "=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setBoolean(1, bool);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Le ban n'a pas eu lieu", ex);
        }

    }

    public void unbanUser(Integer id, boolean bool) {
        String sql = "UPDATE " + tableConfigKey + " SET activate=? WHERE id_" + tableConfigKey + "=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setBoolean(1, bool);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Le deban n'a pas eu lieu", ex);
        }
    }

    /**
     * Récupère la liste de toutes les personnes enregistrées dans la base de
     * données.
     *
     * @return Une collection d'objets Person représentant toutes les personnes.
     */
    @Override
    public Collection<Person> list() {
        Collection persons = new ArrayList(); // Initialise une collection pour stocker les personnes
        String sql = "SELECT * FROM " + tableConfigKey; // Requête SQL pour récupérer toutes les personnes
        try {
            // Prépare la requête SQL pour récupérer toutes les personnes
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(); // Exécute la requête et récupère le résultat

            // Parcourt le résultat et crée un objet Person pour chaque ligne, puis l'ajoute à la collection
            while (rs.next()) {
                Person person = createObject(rs);
                persons.add(person);
            }
            rs.close(); // Ferme le ResultSet
            pstmt.close(); // Ferme le PreparedStatement
        } catch (SQLException ex) {
            Logger.getLogger(DaoPerson.class.getName()).log(Level.SEVERE, null, ex);
        }
        return persons; // Retourne la collection d'objets Person
    }

    /**
     * Récupère la liste de toutes les personnes enregistrées dans la base de
     * données, à l'exception de l'administrateur.
     *
     * @return Une collection d'objets Person représentant toutes les personnes,
     * sauf l'administrateur.
     */
    public Collection<Person> listWithoutAdmin() {
        Collection<Person> list = new ArrayList<>(); // Initialise une collection pour stocker les personnes
        String sql = "SELECT * FROM " + tableConfigKey + " WHERE id_person <> 1"; // Requête SQL pour récupérer toutes les personnes sauf l'administrateur
        try {
            // Prépare la requête SQL pour récupérer les personnes sauf l'administrateur
            PreparedStatement pstmt = MySqlConnection
                    .getInstance()
                    .prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(); // Exécute la requête et récupère le résultat

            // Parcourt le résultat et crée un objet Person pour chaque ligne, puis l'ajoute à la collection
            while (rs.next()) {
                list.add(createObject(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoPerson.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list; // Retourne la collection d'objets Person (sans l'administrateur)
    }

    /**
     * Récupère un objet Person à partir de son login.
     *
     * @param login Le login de la personne.
     * @return L'objet Person correspondant au login donné, ou null s'il
     * n'existe pas.
     */
    public Person findByLogin(String login) {
        Person obj = null; // Initialise un objet Person à null
        String sql = "SELECT * FROM " + tableConfigKey + " WHERE login=?"; // Requête SQL pour récupérer la personne par son login
        try {
            // Prépare la requête SQL avec un paramètre pour le login
            PreparedStatement pstmt = MySqlConnection.getInstance().prepareStatement(sql);
            pstmt.setString(1, login); // Attribue le login au paramètre 1

            ResultSet rs = pstmt.executeQuery(); // Exécute la requête et récupère le résultat

            // Si la requête renvoie un résultat, crée un objet Person avec les données et l'affecte à obj
            if (rs.first()) {
                obj = createObject(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obj; // Retourne l'objet Person correspondant au login, ou null s'il n'existe pas
    }

    /**
     * Supprime une personne de la base de données en fonction de son ID.
     *
     * @param id L'ID de la personne à supprimer.
     */
    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM " + tableConfigKey + " WHERE id_person=?"; // Requête SQL pour supprimer la personne par son ID
        PreparedStatement pstmt;
        try {
            // Prépare la requête SQL avec un paramètre pour l'ID
            pstmt = MySqlConnection.getInstance().prepareStatement(sql);
            pstmt.setInt(1, id); // Attribue l'ID au paramètre 1
            pstmt.executeUpdate(); // Exécute la requête pour supprimer la personne
        } catch (SQLException ex) {
            Logger.getLogger(DaoPerson.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
