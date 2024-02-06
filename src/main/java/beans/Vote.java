/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author Rene
 */
public class Vote extends Bean{

    private Integer id_vote;
    private Integer id_person;
    private Integer id_news;
    private boolean voted;
    private Integer positive;
    private Integer negative;
    
    @Override
    public Integer getId() {
        return id_vote;
    }

    @Override
    public void setId(Integer id) {
        this.id_vote = id_vote;
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

    public boolean isVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }

    public Integer getPositive() {
        return positive;
    }

    public void setPositive(Integer positive) {
        this.positive = positive;
    }

    public Integer getNegative() {
        return negative;
    }

    public void setNegative(Integer negative) {
        this.negative = negative;
    }

    public Integer getId_vote() {
        return id_vote;
    }

    public void setId_vote(Integer id_vote) {
        this.id_vote = id_vote;
    }
    
}
