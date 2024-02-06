/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.DaoNews;
import dao.DaoPerson;
import dao.DaoVote;
import java.util.Date;

/**
 *
 * @author Rene
 */
public class News extends Bean {

    private Integer id_news;
    private Integer id_person;
    private String title_news;
    private String content_news;
    private Date created_news;

    @Override
    public Integer getId() {
        return this.id_news;
    }

    @Override
    public void setId(Integer id_news) {
        this.id_news = id_news;
    }

    public Integer getId_news() {
        return id_news;
    }

    public void setId_news(Integer id_news) {
        this.id_news = id_news;
    }

    public Integer getId_person() {
        return id_person;
    }

    public void setId_person(Integer id_person) {
        this.id_person = id_person;
    }

    public String getTitle_news() {
        return title_news;
    }

    public void setTitle_news(String title_news) {
        this.title_news = title_news;
    }

    public String getContent_news() {
        return content_news;
    }

    public void setContent_news(String content_news) {
        this.content_news = content_news;
    }

    public Date getCreated_news() {
        return created_news;
    }

    public void setCreated_news(Date created_news) {
        this.created_news = created_news;
    }

    public Person getUser() {
        DaoPerson daop = new DaoPerson();
        return daop.read(id_person);
    }
    

    public int getPositive() {
        DaoVote daov = new DaoVote();
        return daov.countPositive(this.id_news);
    }

    public int getNegative() {
        DaoVote daov = new DaoVote();
        return daov.countNegative(this.id_news);
    }

}
