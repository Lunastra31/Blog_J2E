/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.Vote;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fabien
 */
public class DaoVote extends Dao<Vote> {

    /**
     * Constructeur par défaut.
     */
    public DaoVote() {
        super("votestable");
    }

    /**
     * Crée un objet Vote à partir d'un ResultSet.
     *
     * @param rs ResultSet contenant les données du vote.
     * @return Un objet Vote.
     * @throws SQLException Si une erreur SQL survient.
     */
    @Override
    protected Vote createObject(ResultSet rs) throws SQLException {
        Vote bean = new Vote();
        bean.setId_vote(rs.getInt("id_vote"));
        bean.setId_person(rs.getInt("id_person"));
        bean.setId_news(rs.getInt("id_news"));
        bean.setPositive(rs.getInt("positive"));
        bean.setNegative(rs.getInt("negative"));
        bean.setVoted(rs.getBoolean("voted"));
        return bean;
    }

    /**
     * Lit un vote à partir de son identifiant.
     *
     * @param id L'identifiant du vote à lire.
     * @return L'objet Vote correspondant à l'identifiant, ou null si non
     * trouvé.
     */
    @Override
    public Vote read(Integer id) {
        Vote bean = null;
        String sql = "SELECT * FROM " + tableConfigKey + " WHERE id_" + tableConfigKey + "=?";
        try {
            // Prépare la requête SQL avec un paramètre d'ID
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);

            // Exécute la requête et récupère le résultat
            ResultSet rs = pstmt.executeQuery();

            // Si le résultat contient une ligne, crée un objet vote
            if (rs.first()) {
                bean = createObject(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoNews.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bean;
    }

    /**
     * Insère un nouvel enregistrement de vote dans la base de données.
     *
     * @param obj L'objet Vote à insérer.
     */
    @Override
    public void insert(Vote obj) {
        // Requête SQL pour l'insertion d'un nouveau commentaire
        String sql = "INSERT INTO " + tableConfigKey + " (id_person, id_news, positive, negative, voted)"
                + " VALUES (?, ?, ?, ?, ?)";
        try {
            // Prépare la requête SQL et spécifie qu'on veut récupérer les clés générées
            PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            // Attribution des valeurs aux paramètres
            pstmt.setInt(1, obj.getId_person());
            pstmt.setInt(2, obj.getId_news());
            pstmt.setInt(3, obj.getPositive());
            pstmt.setInt(4, obj.getNegative());
            pstmt.setBoolean(5, obj.isVoted());
            // Exécute la requête
            pstmt.executeUpdate();

            // Récupère les clés générées (ID du vote)
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.first()) {
                obj.setId(rs.getInt(1)); // Affecte l'ID généré à l'objet Vote
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Création du bean a échoué", ex);
        }
    }

    /**
     * Met à jour un enregistrement de vote dans la base de données.
     *
     * @param obj L'objet Vote à mettre à jour.
     */
    @Override
    protected void update(Vote obj) {
        String sql = "UPDATE " + tableConfigKey
                + " SET id_vote=?, id_person=?, id_news=?, positive=?, negative=?, voted=? WHERE id_"
                + tableConfigKey + "=?"; // Requête SQL pour mettre à jour les informations d'une personne
        try {
            // Prépare la requête SQL avec des paramètres pour le login, le mot de passe, l'ID de rôle et l'ID de la personne
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, obj.getId_vote()); // Attribue le login de la personne au paramètre 1
            pstmt.setInt(2, obj.getId_person()); // Attribue le mot de passe de la personne au paramètre 2
            pstmt.setInt(3, obj.getId_news());
            pstmt.setInt(4, obj.getPositive());
            pstmt.setInt(5, obj.getNegative());
            pstmt.setBoolean(6, obj.isVoted());

            // Exécute la requête pour mettre à jour les informations de la personne dans la base de données
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DaoPerson.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Renvoie une collection de votes (non implémenté).
     *
     * @return Une collection de votes.
     */
    @Override
    public Collection<Vote> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Non implémenté
    }

    /**
     * Compte le nombre d'enregistrements dans la tableConfigKey de la base de
     * données.
     *
     * @return Le nombre d'enregistrements dans la tableConfigKey.
     */
    public int countPositive(int id_news) {
        int count = 0;

        // Construction de la requête SQL de comptage
        String sql = "SELECT COUNT(*) FROM " + tableConfigKey + " WHERE positive > 0 AND id_news=?";
        try {
            // Préparation de la requête SQL avec un espace réservé pour l'ID
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id_news);
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

    /**
     * Compte le nombre d'enregistrements dans la tableConfigKey de la base de
     * données.
     *
     * @return Le nombre d'enregistrements dans la tableConfigKey.
     */
    public int countNegative(int id_news) {
        int count = 0;

        // Construction de la requête SQL de comptage
        String sql = "SELECT COUNT(*) FROM " + tableConfigKey + " WHERE negative > 0 AND id_news=?";
        try {
            // Préparation de la requête SQL avec un espace réservé pour l'ID
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id_news);
            ResultSet rs = pstmt.executeQuery();
            if (rs.first()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            // En cas d'erreur lors de l'exécution de la requête, enregistre l'exception dans les journaux
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -count;
    }

    public boolean checkIfUserHasVoted(int id_person, int id_news) {
        boolean hasVoted = false;
        try {
            // Requête SQL pour vérifier si l'utilisateur a déjà voté pour cet élément
            String sql = "SELECT COUNT(*) FROM vote WHERE id_person = ? AND id_news = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id_person);
            pstmt.setInt(2, id_news);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    // L'utilisateur a déjà voté pour cet élément
                    hasVoted = true;

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoVote.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hasVoted;
    }
}
