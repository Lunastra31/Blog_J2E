/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.DaoPerson;
import java.util.Date;

/**
 *
 * @author Rene
 */
public class Comment extends Bean{
    private Integer id_comment;


    private Integer id_person;
    private Integer id_news;
    private String content_comment;
    private Date created_comment;
    
    @Override
    public Integer getId() {
        return this.id_comment;
    }

    @Override
    public void setId(Integer id) {
        this.id_comment = id_comment;
    }

    public Integer getId_comment() {
        return id_comment;
    }

    public void setId_comment(Integer id_comment) {
        this.id_comment = id_comment;
    }

    public Integer getId_person() {
        return id_person;
    }

    public void setId_person(Integer id_person) {
        this.id_person = id_person;
    }

    public Integer getId_news() {
        return id_news;
    }

    public void setId_news(Integer id_news) {
        this.id_news = id_news;
    }

    public String getContent_comment() {
        return content_comment;
    }

    public void setContent_comment(String content_comment) {
        this.content_comment = content_comment;
    }

    public Date getCreated_comment() {
        return created_comment;
    }

    public void setCreated_comment(Date created_comment) {
        this.created_comment = created_comment;
    }
    
        public Person getUser() {
        DaoPerson daop = new DaoPerson();
        return daop.read(id_person);
    }
    
}
