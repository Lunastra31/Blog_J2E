package dao;

import beans.News;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cette classe représente l'accès aux données pour l'entité News (Article).
 * Elle étend la classe abstraite Dao et implémente les opérations spécifiques à
 * News.
 *
 * @author Fabien
 */
public class DaoNews extends Dao<News> {

    /**
     * Constructeur par défaut.
     */
    public DaoNews() {
        super("newstable");
    }

    /**
     * Crée un objet News à partir d'un ResultSet.
     *
     * @param rs ResultSet contenant les données de l'article.
     * @return Un objet News.
     * @throws SQLException Si une erreur SQL survient.
     */
    @Override
    protected News createObject(ResultSet rs) throws SQLException {
        // Crée un nouvel objet News
        News bean = new News();

        // Récupère les données de l'article à partir du ResultSet et les attribue à l'objet News
        bean.setId_news(rs.getInt("id_news"));
        bean.setId_person(rs.getInt("id_person"));
        bean.setTitle_news(rs.getString("title_news"));
        bean.setContent_news(rs.getString("content_news"));
        bean.setCreated_news(rs.getDate("created_news"));

        // Retourne l'objet News créé
        return bean;
    }

    /**
     * Lit un article à partir de son identifiant.
     *
     * @param id Identifiant de l'article.
     * @return Un objet News s'il existe, sinon null.
     */
    @Override
    public News read(Integer id) {
        News bean = null;
        String sql = "SELECT * FROM " + tableConfigKey + " WHERE id_" + tableConfigKey + "=?";
        try {
            // Prépare la requête SQL avec un paramètre d'ID
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);

            // Exécute la requête et récupère le résultat
            ResultSet rs = pstmt.executeQuery();

            // Si le résultat contient une ligne, crée un objet News
            if (rs.first()) {
                bean = createObject(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoNews.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bean;
    }

    /**
     * Insère un nouvel article dans la base de données.
     *
     * @param obj Objet News à insérer.
     */
    @Override
    public void insert(News obj) {
        // Requête SQL pour l'insertion d'un nouvel article
        String sql = "INSERT INTO " + tableConfigKey + " (id_person, title_news, content_news) VALUES (?, ?, ?)";
        try {
            // Prépare la requête SQL et spécifie qu'on veut récupérer les clés générées
            PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            // Attribution des valeurs aux paramètres
            pstmt.setInt(1, obj.getId_person());
            pstmt.setString(2, obj.getTitle_news());
            pstmt.setString(3, obj.getContent_news());

            // Exécute la requête
            pstmt.executeUpdate();

            // Récupère les clés générées (ID de l'article)
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.first()) {
                obj.setId(rs.getInt(1)); // Affecte l'ID généré à l'objet News
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Création du bean a échoué", ex);
        }
    }

    /**
     * Met à jour un article dans la base de données.
     *
     * @param obj Objet News à mettre à jour.
     */
    @Override
    protected void update(News obj) {
        // À implémenter si nécessaire.
    }

    /**
     * Récupère une liste d'articles dans l'ordre de leur création (du plus
     * récent au plus ancien).
     *
     * @return Une collection d'articles.
     */
    @Override
    public Collection<News> list() {
        Collection allNews = new ArrayList(); // Collection pour stocker les articles
        String sql = "SELECT * FROM " + tableConfigKey + " ORDER BY created_news DESC"; // Requête SQL pour récupérer les articles
        try {
            // Prépare la requête SQL
            PreparedStatement pstmt = connection.prepareStatement(sql);

            // Exécute la requête et récupère le résultat
            ResultSet rs = pstmt.executeQuery();

            // Parcourt les résultats et crée des objets News pour chaque enregistrement
            while (rs.next()) {
                News news = createObject(rs);
                allNews.add(news);
            }

            // Ferme le ResultSet et le PreparedStatement
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(DaoPerson.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allNews; // Retourne la collection d'articles
    }

    /**
     * Récupère une liste d'articles créés par un utilisateur spécifié.
     *
     * @param id_person L'ID de l'utilisateur
     * @return Une collection d'articles créés par l'auteur.
     */
    public Collection<News> findByUser(int id_person) {
        Collection<News> list = new ArrayList<>(); // Collection pour stocker les articles créés par l'utilisateur
        String sql = "SELECT * FROM " + tableConfigKey + " WHERE id_person=?"; // Requête SQL pour récupérer les articles de l'utilisateur
        try {
            // Prépare la requête SQL avec un paramètre pour l'ID de l'auteur
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id_person); // Attribue la valeur de l'ID de l'utilisateur au paramètre

            // Exécute la requête et récupère le résultat
            ResultSet rs = pstmt.executeQuery();

            // Parcourt les résultats et crée des objets Article pour chaque enregistrement
            while (rs.next()) {
                list.add(createObject(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list; // Retourne la collection d'articles créés par l'utilisateur
    }

    /**
     * Supprime un article en fonction de son ID.
     *
     * @param id L'ID de l'article à supprimer.
     */
    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM " + tableConfigKey + " WHERE id_news=?"; // Requête SQL pour supprimer l'article par son ID
        PreparedStatement pstmt;
        try {
            // Prépare la requête SQL avec un paramètre pour l'ID de l'article à supprimer
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id); // Attribue la valeur de l'ID de l'article au paramètre

            // Exécute la requête pour supprimer l'article
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DaoPerson.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Date getCreationDate(Integer id_news) {
        Date creationDate = null;
        String sql = "SELECT created_news FROM " +tableConfigKey+ " WHERE id_" +tableConfigKey+ "=?";
        
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id_news);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // Récupérer la date de création depuis le résultat de la requête
                creationDate = rs.getTimestamp("created_news");
            }
        } catch (SQLException ex) {
            // Gérer les exceptions SQL ici
            ex.printStackTrace();
        }
        
        return creationDate;
    }

    public Collection<News> showLimitedChar(int qty) {
        // Collection pour stocker les News
        Collection<News> allNews = new ArrayList<>();

        // Requête SQL pour récupérer les 100 premiers caractères du contenu des articles
        String sql = "SELECT id_news, id_person, created_news, title_news, "
                + "CONCAT(SUBSTRING(content_news, 1, " + qty + "), '...') AS content_news F"
                + "ROM " + tableConfigKey + " "
                + "ORDER BY created_news DESC";

        try {
            // Prépare la requête SQL
            PreparedStatement pstmt = connection.prepareStatement(sql);

            // Exécute la requête et récupère le résultat
            ResultSet rs = pstmt.executeQuery();

            // Parcourt les résultats et crée des objets News pour chaque enregistrement
            while (rs.next()) {
                // Crée un objet News à partir des données de la ligne courante
                News news = createObject(rs);

                // Ajoute l'objet News à la collection
                allNews.add(news);
            }

            // Ferme le ResultSet et le PreparedStatement pour libérer les ressources
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            // Gère les exceptions liées à l'accès à la base de données
            Logger.getLogger(DaoPerson.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Retourne la collection d'articles avec les 100 premiers caractères du contenu
        return allNews;
    }

}
