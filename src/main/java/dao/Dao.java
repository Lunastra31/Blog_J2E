/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.Bean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import libs.Helpers;

/**
 *
 * @author Fabien
 * @param <T>
 */
public abstract class Dao<T extends Bean> {

    // Nom de la tableConfigKey dans la base de données
    protected String tableConfigKey;

    // Connexion à la base de données
    protected Connection connection;

    /**
     * Constructeur de la classe Dao.
     *
     * @param tableConfigKey Le nom de la table dans la base de données trouvé
     * dans config.properties.
     */
    public Dao(String tableConfigKey) {
        // Initialise la connexion à la base de données en utilisant MySqlConnection.getInstance()
        this.connection = MySqlConnection.getInstance();
        this.tableConfigKey = Helpers.getConfig().getProperty(tableConfigKey);
    }

    protected abstract T createObject(ResultSet rs) throws SQLException;

    /**
     * Récupère un objet de type T depuis la base de données en fonction de son
     * ID.
     *
     * @param id L'ID de l'objet à lire.
     * @return L'objet de type T récupéré depuis la base de données, ou null
     * s'il n'existe pas.
     */
    public abstract T read(Integer id);

    /**
     * Insère un nouvel objet de type T dans la base de données.
     *
     * @param obj L'objet à insérer.
     */
    protected abstract void insert(T obj);

    /**
     * Met à jour un objet de type T dans la base de données.
     *
     * @param obj L'objet à mettre à jour.
     */
    protected abstract void update(T obj);

    /**
     * Persiste un objet de type T dans la base de données. Si l'objet a un ID
     * nul, il est inséré, sinon il est mis à jour.
     *
     * @param obj L'objet à persister.
     */
    public void persist(T obj) {
        if (obj.getId() == null) {
            this.insert(obj);
        } else {
            this.update(obj);
        }
    }

    /**
     * Récupère une collection d'objets de type T depuis la base de données.
     *
     * @return Une collection d'objets de type T.
     */
    public abstract Collection<T> list();

    /**
     * Supprime un enregistrement de la base de données en fonction de son ID.
     *
     * @param id L'ID de l'enregistrement à supprimer.
     */
    public void delete(Integer id) {
        // Construction de la requête SQL de suppression
        String sql = "DELETE FROM " + tableConfigKey + " WHERE id_" + tableConfigKey + "=?";
        try {
            // Préparation de la requête SQL avec un espace réservé pour l'ID
            PreparedStatement pstmt = connection.prepareStatement(sql);

            // Définition de la valeur de l'ID dans la requête SQL en utilisant l'argument "id"
            pstmt.setInt(1, id);

            // Exécution de la requête SQL pour supprimer l'enregistrement
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            // En cas d'erreur lors de l'exécution de la requête, enregistre l'exception dans les journaux
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Supprime un objet de type T de la base de données en utilisant son ID.
     *
     * @param obj L'objet à supprimer.
     */
    public void delete(T obj) {
        delete(obj.getId());
    }

    /**
     * Compte le nombre d'enregistrements dans la tableConfigKey de la base de
     * données.
     *
     * @return Le nombre d'enregistrements dans la tableConfigKey.
     */
    public int count() {
        int count = 0;

        // Construction de la requête SQL de comptage
        String sql = "SELECT COUNT(*) FROM " + tableConfigKey;
        try {
            // Préparation de la requête SQL avec un espace réservé pour l'ID
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.first()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            // En cas d'erreur lors de l'exécution de la requête, enregistre l'exception dans les journaux
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

}
