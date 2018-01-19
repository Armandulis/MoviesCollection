/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movies.DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import movies.BE.Category;
import movies.BE.Movie;
import movies.BLL.MoviesBLL;
import movies.ConnectionManager;

/**
 *
 * @author Skomantas
 */
public class MoviesDA {
    private static ConnectionManager cm = new ConnectionManager();
    
    public List<Category> loadCategories() { //Loads all of the playlists

        List<Category> allCategories = new ArrayList();

        try (Connection con = cm.getConnection()) {

            String query = "SELECT * FROM Categories";
            PreparedStatement pstmt= con.prepareStatement(query);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id")); 
                category.setCategory(rs.getString("name"));

                allCategories.add(category);
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(MoviesBLL.class.getName()).log( Level.SEVERE, null, ex);
        }
        return allCategories;

    }

    public void addCategory(Category category) {
  try (Connection con = cm.getConnection()) {
            String sql  = "INSERT INTO Categories (name) VALUES(?)";
            PreparedStatement pstmt = con.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, category.getCategory());
            
            int affected = pstmt.executeUpdate();
            if (affected<1)
                throw new SQLException("Movie could not be added");

        }
        catch (SQLException ex) {
            Logger.getLogger(MoviesDA.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }

   

    public void removeCategory(int selectedCategory) {
        try (Connection con = cm.getConnection()) {
            String sql
                    = "DELETE FROM Categories WHERE id = ?";
            PreparedStatement pstmt
                    = con.prepareStatement(sql);
            pstmt.setInt(1, selectedCategory);
            pstmt.execute();
            
            String query = "SELECT * FROM catMovie WHERE categoryID = ? ";
            PreparedStatement pstmt2 = con.prepareStatement(query);
            pstmt2.setInt(1, selectedCategory );

            ResultSet rs = pstmt2.executeQuery();
            
            while (rs.next()) {
            String sql1 = "DELETE FROM catMovie WHERE categoryID = ?";
            PreparedStatement pstmt1= con.prepareStatement(sql1);
            pstmt1.setInt(1, selectedCategory);
            pstmt1.execute();}
        }
        catch (SQLException ex) {
            Logger.getLogger(MoviesDA.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }
    
     

    public List<Movie> loadMovies(int selectedCategory) {
        
        List<Movie> listMovies = new ArrayList();

        try (Connection con = cm.getConnection()) {
            

            String query = "SELECT * FROM catMovie WHERE categoryID = ?";

            // Protect against SQL injection
            PreparedStatement pstmt= con.prepareStatement(query);
            
            pstmt.setInt(1, selectedCategory);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                
                int movieID = rs.getInt("movieID");
                
                String queryList = "SELECT * FROM Movie WHERE id = ?";
                
                PreparedStatement pstmtList= con.prepareStatement(queryList);
                pstmtList.setInt(1,movieID);
                
                ResultSet rsList = pstmtList.executeQuery();
                
                Movie movie = new Movie();
                rsList.next();
                movie.setId(rsList.getInt("id"));
                movie.setName(rsList.getString("name"));
                movie.setFilelink(rsList.getString("filelink"));
                movie.setPersonalScore(rsList.getString("personalscore"));
                movie.setRating(rsList.getString("rating"));
                
                listMovies.add(movie);

            }
        }
        catch (SQLException ex) {
            Logger.getLogger(MoviesBLL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listMovies;

    }

    public List<Movie> filterMovies(String filteredMovies) {
        List<Movie> filteredAllMovies = new ArrayList();

        try (Connection con = cm.getConnection()) {

            String query = "SELECT * FROM Movie WHERE name LIKE ? OR rating LIKE ? OR personalscore LIKE ?  ";

            // Protect against SQL injection
            PreparedStatement pstmt= con.prepareStatement(query);
            pstmt.setString(1, "%" + filteredMovies + "%");
            pstmt.setString(2, "%" + filteredMovies + "%");
            pstmt.setString(3, "%" + filteredMovies + "%");

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Movie movie = new Movie();
                movie.setId(rs.getInt("id"));
                movie.setName(rs.getString("name"));
                movie.setRating(rs.getString("rating"));
                movie.setFilelink(rs.getString("filelink"));
                movie.setLastview(rs.getString("lastview"));
                movie.setPersonalScore(rs.getString("personalscore"));

                filteredAllMovies.add(movie);
            }
            
            String sql = "SELECT * FROM Categories WHERE name LIKE ? ";
            PreparedStatement pstmtCategories= con.prepareStatement(sql);
            pstmtCategories.setString(1, "%" + filteredMovies + "%");
            ResultSet rsCategories = pstmtCategories.executeQuery();
            
            
            while(rsCategories.next()){
                
            int categoryID = rsCategories.getInt("id");
            
            String sqlCat = "SELECT * FROM catMovie WHERE categoryID = ? ";
            PreparedStatement pstmtCat= con.prepareStatement(sqlCat);
            pstmtCat.setInt(1, categoryID);
            ResultSet rsCatMovie = pstmtCat.executeQuery();
            while(rsCatMovie.next()){
            int movieID = rsCatMovie.getInt("movieID");
            String sqlMovieID = "SELECT * FROM Movie WHERE id = ? ";
            PreparedStatement pstmtMovieID= con.prepareStatement(sqlMovieID);
            pstmtMovieID.setInt(1, movieID);
            ResultSet rsMoviesID = pstmtMovieID.executeQuery();
            
            while(rsMoviesID.next()){
                
                 Movie movie = new Movie();
                movie.setId(rsMoviesID.getInt("id"));
                movie.setName(rsMoviesID.getString("name"));
                movie.setRating(rsMoviesID.getString("rating"));
                movie.setFilelink(rsMoviesID.getString("filelink"));
                movie.setLastview(rsMoviesID.getString("lastview"));
                movie.setPersonalScore(rsMoviesID.getString("personalscore"));

                filteredAllMovies.add(movie);
            
            }
            
            
        }
           
            
    }
            
            
        }
        catch (SQLException ex) {
            Logger.getLogger(MoviesBLL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filteredAllMovies;

        
    }
    
    //removes movie from the movie list and from the catmovie
    
    public void removeMovie(int selectedMovie) {
        try (Connection con = cm.getConnection()) {
            String sql = "DELETE FROM Movie WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, selectedMovie);
            pstmt.execute();
            
            String query = "SELECT * FROM catMovie WHERE movieID = ? ";
            PreparedStatement pstmt2 = con.prepareStatement(query);
            pstmt2.setInt(1, selectedMovie );

            ResultSet rs = pstmt2.executeQuery();
            
            while (rs.next()) {
            String sql1 = "DELETE FROM catMovie WHERE movieID = ?";
            PreparedStatement pstmt1= con.prepareStatement(sql1);
            pstmt1.setInt(1, selectedMovie);
            pstmt1.execute();}
            
            
            
        }
        catch (SQLException ex) {
            Logger.getLogger(MoviesDA.class.getName()).log(
                    Level.SEVERE, null, ex);
        }}

    public void addMovie(Movie movie, List<Category> categories) {
        
        try (Connection con = cm.getConnection()) {
           
            String sql  = "INSERT INTO Movie (name, rating, filelink, personalscore) VALUES(?, ?, ?,?)";
            PreparedStatement pstmt = con.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, movie.getName());
            pstmt.setString(2, movie.getRating());
            pstmt.setString(3, movie.getFilelink());
            pstmt.setString(4, movie.getPersonalScore());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
           
            
           rs.next();
            movie.setId(rs.getInt(1));
            addMovietoCategory(movie, categories);
            
        }
                

        
        catch (SQLException ex) {
            Logger.getLogger(MoviesDA.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        
    }
    
    
    public void addMovietoCategory(Movie movie, List<Category> categories) {
        try (Connection con = cm.getConnection()) {
            
            
            for (Category category : categories) {
                
            int categoryID = category.getId();
            int movieID = movie.getId();
            
            
            
            String sqlcatMovie  = "INSERT INTO catMovie (categoryID, movieID) VALUES(?, ?)";
            PreparedStatement prsql = con.prepareStatement(sqlcatMovie);
            prsql.setInt(1, categoryID);
            prsql.setInt(2, movieID);
            prsql.executeUpdate();
                 }

        }
        catch (SQLException ex) {
            Logger.getLogger(MoviesDA.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        
    }

    //looks where the spesific id is and sets the score to it
    public void setPersonalScore(Movie movie) {
        
        
          try (Connection con = cm.getConnection()) {
        
        String query = "UPDATE Movie SET personalscore=? WHERE id=?";
            PreparedStatement pstmt2 = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt2.setString(1, movie.getPersonalScore());
            pstmt2.setInt(2, movie.getId());
          pstmt2.execute();}
         
          catch (SQLException ex) {
            Logger.getLogger(MoviesDA.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
      
    }

    public void setLastView(Movie movie) {
        try (Connection con = cm.getConnection()) {
        
        String query = "UPDATE Movie SET lastview WHERE id=?";
            PreparedStatement pstmt2 = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt2.setString(1,movie.getLastview());
            pstmt2.setInt(2, movie.getId());
            pstmt2.execute();}
         
          catch (SQLException ex) {
            Logger.getLogger(MoviesDA.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        
    }

    
   //Checks if any of the movies has the same name
    public int checkForName(Movie movie) {
        int counter=0;
        try (Connection con = cm.getConnection()) {

            String query = "SELECT * FROM Movie WHERE name = ?";

            // Protect against SQL injection
            PreparedStatement pstmt= con.prepareStatement(query);
            pstmt.setString(1, movie.getName());

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                counter++;
                
            }
             }
        catch (SQLException ex) {
            Logger.getLogger(MoviesBLL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return counter;
        
       
    }
    public List<Movie> loadMovie() { //Loads all of the Movies

        List<Movie> allMovies = new ArrayList();

        try (Connection con = cm.getConnection()) {

            String query = "SELECT * FROM Movie";
            PreparedStatement pstmt= con.prepareStatement(query);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Movie movie = new Movie();
                movie.setId(rs.getInt("id"));
                movie.setName(rs.getString("name"));
                movie.setRating(rs.getString("rating"));
                movie.setFilelink(rs.getString("filelink"));
                movie.setLastview(rs.getString("lastview"));
                movie.setPersonalScore(rs.getString("personalscore"));

                allMovies.add(movie);
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(MoviesBLL.class.getName()).log( Level.SEVERE, null, ex);
        }
        return allMovies;

    }




  
    
    }

