package dao;

import beans.Comment;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cette classe représente l'accès aux données pour l'entité Commentaire
 * (Comment). Elle étend la classe abstraite Dao et implémente les opérations
 * spécifiques à Commentaire.
 *
 * @author Fabien
 */
public class DaoComment extends Dao<Comment> {

    /**
     * Constructeur par défaut.
     */
    public DaoComment() {
        super("commentstable");
    }

    /**
     * Crée un objet Commentaire à partir d'un ResultSet.
     *
     * @param rs ResultSet contenant les données du commentaire.
     * @return Un objet Commentaire.
     * @throws SQLException Si une erreur SQL survient.
     */
    @Override
    protected Comment createObject(ResultSet rs) throws SQLException {
        Comment bean = new Comment();
        bean.setId_comment(rs.getInt("id_comment"));
        bean.setId_news(rs.getInt("id_news"));
        bean.setId_person(rs.getInt("id_person"));
        bean.setContent_comment(rs.getString("content_comment"));
        bean.setCreated_comment(rs.getDate("created"));
        return bean;
    }

    /**
     * Lit un commentaire à partir de son identifiant.
     *
     * @param id Identifiant du commentaire.
     * @return Un objet Commentaire s'il existe, sinon null.
     */
    @Override
    public Comment read(Integer id) {
        Comment bean = null;
        String sql = "SELECT * FROM " + tableConfigKey + " WHERE id_" + tableConfigKey + "=?";
        try {
            // Prépare la requête SQL avec un paramètre d'ID
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);

            // Exécute la requête et récupère le résultat
            ResultSet rs = pstmt.executeQuery();

            // Si le résultat contient une ligne, crée un objet Commentaire
            if (rs.first()) {
                bean = createObject(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoComment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bean;
    }

    /**
     * Insère un nouveau commentaire dans la base de données.
     *
     * @param obj Objet Commentaire à insérer.
     */
    @Override
    public void insert(Comment obj) {
        // Requête SQL pour l'insertion d'un nouveau commentaire
        String sql = "INSERT INTO " + tableConfigKey + " (id_news, id_person, content_comment) VALUES (?, ?, ?)";
        try {
            // Prépare la requête SQL et spécifie qu'on veut récupérer les clés générées
            PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            // Attribution des valeurs aux paramètres
            pstmt.setInt(1, obj.getId_news());
            pstmt.setInt(2, obj.getId_person());
            pstmt.setString(3, obj.getContent_comment());

            // Exécute la requête
            pstmt.executeUpdate();

            // Récupère les clés générées (ID du commentaire)
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.first()) {
                obj.setId(rs.getInt(1)); // Affecte l'ID généré à l'objet Commentaire
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Création du bean comment a échoué", ex);
        }
    }

    @Override
    protected void update(Comment obj) {
        throw new UnsupportedOperationException("Non pris en charge pour le moment."); // À implémenter si nécessaire.
    }

    @Override
    public Collection<Comment> list() {
        Collection<Comment> allComments = new ArrayList(); // Collection pour stocker les commentaires
        String sql = "SELECT * FROM " + tableConfigKey + " ORDER BY created DESC"; // Requête SQL pour récupérer les commentaires
        try {
            // Prépare la requête SQL
            PreparedStatement pstmt = connection.prepareStatement(sql);

            // Exécute la requête et récupère le résultat
            ResultSet rs = pstmt.executeQuery();

            // Parcourt les résultats et crée des objets Comment pour chaque enregistrement
            while (rs.next()) {
                Comment comments = createObject(rs);
                allComments.add(comments);
            }

            // Ferme le ResultSet et le PreparedStatement
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(DaoPerson.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allComments; // Retourne la collection de commentaires
    }

    /**
     * Récupère une liste de commentaires créés par un utilisateur spécifié sur une news spécifique.
     *
     * @param id_news L'ID de la news
     * @return Une collection de commentaires sur une news.
     */
    public Collection<Comment> findByNews(int id_news) {
        Collection<Comment> list = new ArrayList<>(); // Collection pour stocker les articles créés par l'utilisateur
        String sql = "SELECT * FROM " + tableConfigKey + " WHERE id_news=? ORDER BY created DESC"; // Requête SQL pour récupérer les articles de l'utilisateur
        try {
            // Prépare la requête SQL avec un paramètre pour l'ID de la news
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id_news); // Attribue la valeur de l'ID de la news au paramètre

            // Exécute la requête et récupère le résultat
            ResultSet rs = pstmt.executeQuery();

            // Parcourt les résultats et crée des objets Article pour chaque enregistrement
            while (rs.next()) {
                list.add(createObject(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list; // Retourne la collection de commentaires associés à une news
    }
}
