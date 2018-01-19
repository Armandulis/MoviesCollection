/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movies.BLL;

import java.util.List;
import movies.BE.Category;
import movies.BE.Movie;
import movies.DAL.MoviesDA;

/**
 *
 * @author Skomantas
 */
public class MoviesBLL {
    
    MoviesDA dal = new MoviesDA();
    public int checkForName;

    public List<Category> loadCategories() {
        return dal.loadCategories();
    }
    
    public List<Movie> LoadMovie() {
        return dal.loadMovie();
    }

    public void addCategory(Category category) {
        dal.addCategory(category);
    }

    public void removeCategory(int selectedCategory) {
       dal.removeCategory(selectedCategory);
    }

   

    public List<Movie> loadMovies(int selectedCategory) {
         return dal.loadMovies(selectedCategory);
    }

    public List<Movie> filterMovies(String filteredMovies) {
        return dal.filterMovies(filteredMovies);
    }

    public void removeMovie(int selectedMovie) {
        dal.removeMovie(selectedMovie);}

    public void addMovie(Movie movie, List<Category> category) {
       dal.addMovie(movie, category);
    }

    public void setPersonalScore(Movie movie) {
        dal.setPersonalScore(movie);
    }

    public void setLastView(Movie movie) {
        dal.setLastView(movie);
    }

    public int checkForName(Movie movie) {
        return dal.checkForName(movie);
    }



    
    
}
