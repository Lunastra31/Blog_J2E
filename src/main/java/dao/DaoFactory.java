/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author Fabien La classe DaoFactory fournit des instances des DAO pour les
 * objets Person et Article.
 */
public final class DaoFactory {

    private static DaoPerson daoPerson = null; // Instance du DAO pour les objets Person
    private static DaoNews daoNews = null; // Instance du DAO pour les objets News
    private static DaoComment daoComment = null; // Instance du DAO pour les objets Comment

    /**
     * Récupère une instance du DAO pour les objets Person.
     *
     * @return L'instance du DAO pour les objets Person.
     */
    public static DaoPerson getPersonDao() {
        if (daoPerson == null) {
            daoPerson = new DaoPerson();
        }
        return daoPerson;
    }

    /**
     * Récupère une instance du DAO pour les objets Article.
     *
     * @return L'instance du DAO pour les objets Article.
     */
    public static DaoNews getNewsDao() {
        if (daoNews == null) {
            daoNews = new DaoNews();
        }
        return daoNews;
    }

    /**
     * Récupère une instance du DAO pour les objets Article.
     *
     * @return L'instance du DAO pour les objets Article.
     */
    public static DaoComment getCommentDao() {
        if (daoComment == null) {
            daoComment = new DaoComment();
        }
        return daoComment;
    }
}
