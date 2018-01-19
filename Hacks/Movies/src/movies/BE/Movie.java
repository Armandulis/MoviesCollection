/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movies.BE;
/**
 *
 * @author Skomantas
 */
public class Movie {
    
    private int id;

    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(int id) {
        this.id = id;
    }
    
    private String name;

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    private String rating;

    /**
     * Get the value of rating
     *
     * @return the value of rating
     */
    public String getRating() {
        return rating;
    }

    /**
     * Set the value of rating
     *
     * @param rating new value of rating
     */
    public void setRating(String rating) {
        this.rating = rating;
    }
    
    private String filelink;

    /**
     * Get the value of filelink
     *
     * @return the value of filelink
     */
    public String getFilelink() {
        return filelink;
    }

    /**
     * Set the value of filelink
     *
     * @param filelink new value of filelink
     */
    public void setFilelink(String filelink) {
        this.filelink = filelink;
    }
    
    private String lastview;

    /**
     * Get the value of lastview
     *
     * @return the value of lastview
     */
    public String getLastview() {
        return lastview;
    }

    /**
     * Set the value of lastview
     *
     * @param lastview new value of lastview
     */
    public void setLastview(String lastview) {
        this.lastview = lastview;
    }

       private String personalScore;

    public String getPersonalScore() {
        return personalScore;
    }

    public void setPersonalScore(String personalScore) {
        this.personalScore = personalScore;
    }

    
}

