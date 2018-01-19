/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movies.GUI;

import java.io.File;
import movies.BE.Category;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import movies.BE.Movie;
import movies.BLL.MoviesBLL;
import movies.Movies;

/**
 *
 * @author Skomantas
 */
public class FXMLDocumentController implements Initializable {
    
    private ObservableList<Category>listCategories=FXCollections.observableArrayList();
     private ObservableList<Movie>listMovies=FXCollections.observableArrayList();
    MoviesBLL bll= new MoviesBLL();
            
    @FXML
    private Label label;

    @FXML
    private TextField srcBar;
    @FXML
    private TableColumn<Movies, String> collumTitle;
    @FXML
    private TableColumn<Movies, String> collumImdb;
    @FXML
    private TableColumn<Movies, String> collumPersonalsco;
    @FXML
    private TableView<Category> listTableCategories;
    @FXML
    private TableColumn<Category, String> collumCategories;
    @FXML
    private TableView<Movie> listTableMovies;
    @FXML
    private Button btnRemove2Category;
    @FXML
    private Button btnAddCategory;
    @FXML
    private Button btnAddMovie;
    @FXML
    private Button btnRemoveMovie;
    @FXML
    private Button btnFilter;
    
    
     int buttonFilterSwitch =0;
     
     MediaPlayer mediaplayer;
    @FXML
    private MediaView mv;
    @FXML
    private Button buttonMoviePause;
  
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        

            loadCategories();
            
            loadMovie();
            
            collumCategories.setCellValueFactory(new PropertyValueFactory("category"));
            
            collumImdb.setCellValueFactory(new PropertyValueFactory("rating"));
            collumPersonalsco.setCellValueFactory(new PropertyValueFactory("personalScore"));
            collumTitle.setCellValueFactory(new PropertyValueFactory("name"));
   
            getSelectedMovieListener();
            
    } 
    
    //loads filtered movies
    
    public void loadFilteredMovies(){
        listTableMovies.getItems().clear();
        listMovies.clear();
        listMovies.addAll(bll.filterMovies(srcBar.getText()));
        listTableMovies.getItems().addAll(listMovies);
    }
    
    // makes a place to view the movie
    private void makeMediaPlayer(String fileLink){
        try {
            File file = new File(fileLink);
            String filePath = file.toURI().toString();
            Media media = new Media(filePath);
            mediaplayer = new MediaPlayer(media);
            mv.setFitHeight(1000);
            mv.setFitWidth(500);
            mv.setMediaPlayer(mediaplayer);
        } catch (Exception ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    
    
   
    @FXML
    private void searchBar(ActionEvent event){
         
    }
    
   
    
    @FXML
    private void buttonRemoveCategory(ActionEvent event) throws IOException {
         
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Categoriesremove.fxml"));
        Parent root1 = loader.load();
        CategoriesremoveController contrl = loader.getController();
        contrl.setMainViewCont(this);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root1));
        stage.show();
       
    }
    

    @FXML
    private void buttonAddCategory(ActionEvent event) throws IOException {
        listTableMovies.getSelectionModel().clearSelection();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Categoriesadd.fxml"));
        Parent root1 = loader.load();
        CategoriesaddController contrl = loader.getController();
        contrl.setMainViewCont(this);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root1));
        stage.show();
    }

    @FXML
    private void buttonAddMovie(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Moviesadd.fxml"));
        Parent root1 = loader.load();
        MoviesaddController contrl = loader.getController();
        contrl.setMainViewCont(this);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root1));
        stage.show();
    }

    @FXML
    private void buttonRemoveMovie(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Moviesremove.fxml"));
        Parent root1 = loader.load();
        MoviesremoveController contrl = loader.getController();
        contrl.setMainViewCont(this);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root1));
        stage.show();
    }
    
    // looks for changes in the tableListCategories, if the change has been made
    //the listener activates and does the code below which loads movies from the category
     public void getSelectedMovieListener() {
         
            listTableCategories.getSelectionModel().selectedItemProperty().addListener((e) -> {
             clearMovieList();
             
             listMovies.addAll(bll.loadMovies( listTableCategories.getSelectionModel().getSelectedItem().getId()));
             
             listTableMovies.getItems().addAll(listMovies);
                
            });
         }
     
     //filters movies
     
    @FXML
    private void buttonFilter(ActionEvent event) {
        
        if (buttonFilterSwitch == 0){
            btnFilter.setText("Clear");
            buttonFilterSwitch ++;
        
        clearMovieList();
        listMovies.addAll(bll.filterMovies(srcBar.getText()));
        listTableMovies.getItems().addAll(listMovies);
        
        
        }
        else {
         btnFilter.setText("Filter");
        buttonFilterSwitch =0;
            clearMovieList();
            srcBar.setText("");
        }
        if(srcBar.getText().isEmpty()){
        loadMovie();}
    }
    
   
    
    public int getSelectedCategoryID() {
        return listTableCategories.getSelectionModel().getSelectedItem().getId();
    }
    
    public int getSelectedMovieID() {
        return listTableMovies.getSelectionModel().getSelectedItem().getId();
    }
    
    public Movie getSelectedMovie() {
        return listTableMovies.getSelectionModel().getSelectedItem();
    }
    
     public void loadCategories(){
        listCategories.addAll(bll.loadCategories());
        listTableCategories.getItems().addAll(listCategories);
    }
    public void clearCategoryList(){
    listCategories.clear();
    listTableCategories.getItems().clear();
    }
    
    public void clearMovieList(){
    listMovies.clear();
    listTableMovies.getItems().clear();
    }
    
    //plays the movie and sets lastView to it
     @FXML
    private void buttonMoviePlay(ActionEvent event) {
        
        if(listTableMovies.getSelectionModel().getSelectedItem() != null){
     Movie movie = listTableMovies.getSelectionModel().getSelectedItem();
     
     DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy ");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);
        movie.setLastview(date);
        bll.setLastView(movie);
     
     makeMediaPlayer(movie.getFilelink());  
     listTableMovies.getSelectionModel().clearSelection();
     mediaplayer.play();
          
         }
        else
     mediaplayer.play();
     
    }

    @FXML
    private void buttonMoviePause(ActionEvent event) {
        mediaplayer.pause();
    }

    @FXML
    private void buttonStop(ActionEvent event) {
        mediaplayer.stop();
    }
    public void loadMovie(){
        listMovies.clear();
        listTableMovies.getItems().clear();
        listMovies.addAll(bll.LoadMovie());
        listTableMovies.getItems().addAll(listMovies);
    
    }

    @FXML
    private void buttonScore(ActionEvent event) throws IOException {
        if(listTableMovies.getSelectionModel().getSelectedItem() !=null){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ScoreMovie.fxml"));
        Parent root1 = loader.load();
        ScoreMovieController contrl = loader.getController();
        contrl.setMainViewCont(this);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root1));
        stage.show();}
        
    }
    
    // takes and makes all lastview dates from movies and makes them to the same data type
    
    private void twoYearsNotification() {
        
        
       loadMovie();
        for (Movie movie : listMovies ){
           
           String dateString = movie.getLastview(); //get date in string form
           
           try {
               
              if (dateString != null){
                  DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy"); //date formator
                  DateTimeFormatter dateFormatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //actual date formator
                  
                  LocalDate localDate = LocalDate.now();
                  
                  String actualDateString = localDate.format(dateFormatter2);
 
                  Date actualDate = dateFormatter.parse(actualDateString);
                  dateFormatter.format(actualDate);
                  
                  Date date = dateFormatter.parse(dateString); //get date from string
                  dateFormatter.format(date);
                  
                  
                  if (getDateDifference(date,actualDate,TimeUnit.DAYS)>712.5 || Integer.valueOf(movie.getPersonalScore())<6) { 
                      
                Alert("DELETE OLD MOVIES", "The movie '" + movie.getName() + "' was either not watched for two years or it has low personal score, please consider deleting it");
                  }
              }            
           } 
           catch (ParseException ex) {
               Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
           }}}
           
           
        
        
        // Calculation between actualDate and date (lastView)
            public long getDateDifference(Date date1, Date date2, TimeUnit timeUnit) {
                    long diffInMillies = date2.getTime() - date1.getTime();
                    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
            }
            
            // Will show basic alert pop-up window
        private void Alert(String title,String text){
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setContentText(text);
            alert.showAndWait();
             }
        
                /* Show notification when there is old and bad movie. 
        Notification appears after mouse enter application and 
        this method is executed only once (trick with boolean) */
        private boolean alreadyExecuted=true;
    @FXML
    private void onMouseOver(javafx.scene.input.MouseEvent event) {
        if(alreadyExecuted){
                 twoYearsNotification();
             alreadyExecuted = false;
                }
    }



    
    
}     
      
        
 

