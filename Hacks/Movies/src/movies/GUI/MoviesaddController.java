/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movies.GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import movies.BE.Category;
import movies.BE.Movie;
import movies.BLL.MoviesBLL;

/**
 * FXML Controller class
 *
 * @author Skomantas
 */
public class MoviesaddController implements Initializable {
    FXMLDocumentController mwc;
    @FXML
    private Button btnCancel1;
    @FXML
    private Button btnSave1;
    @FXML
    private TextField textFieldImdb;
    @FXML
    private TextField textFieldPersonal;
    @FXML
    private TextField textFieldFile;
    @FXML
   private Button btnChoose;
    
    @FXML
    private TextField textFieldTitle;
    @FXML
    private TableView<Category> tableCategories;
    @FXML
    private TableView<Category> tableListCategoriesToMovie;
    @FXML
    private TableColumn<Category, String> columnCategories;
    private TextField textFiedCategory;
    
    MoviesBLL bll = new MoviesBLL();
    
     private ObservableList<Category>listCategory=FXCollections.observableArrayList();
      private ObservableList<Category>listCategoryToMovie=FXCollections.observableArrayList();
    
    
    @FXML
    private TableColumn<Category, String> columnCategoriesDB;
    @FXML
    private Label labelEmptyFields;
    @FXML
    private Label labelError2;
    
    
    
    
    
    
    
    
        @Override
        public void initialize(URL url, ResourceBundle rb) {
        columnCategoriesDB.setCellValueFactory(new PropertyValueFactory("category"));
         columnCategories.setCellValueFactory(new PropertyValueFactory("category"));
        listCategory.addAll(bll.loadCategories());
        tableCategories.getItems().addAll(listCategory);
       
    }    
    
    
    
    public void setMainViewCont(FXMLDocumentController mwc){
        this.mwc=mwc;
        }

    @FXML
    private void buttonCancel1(ActionEvent event) {
        Stage stage = (Stage) btnCancel1.getScene().getWindow();
        stage.close();
    }
    
    
    // save users input  to the movie class, makes sure if all of the text fields are filled
    @FXML
    private void buttonSave1(ActionEvent event) {
        if(!textFieldFile.getText().isEmpty() && !textFieldTitle.getText().isEmpty() && !textFieldImdb.getText().isEmpty() && !tableListCategoriesToMovie.getItems().isEmpty()){
            
        
        Movie movie = new Movie();
        movie.setFilelink(textFieldFile.getText());
        movie.setName(textFieldTitle.getText());
        movie.setPersonalScore(textFieldPersonal.getText());
        movie.setRating(textFieldImdb.getText());
        if(bll.checkForName(movie) ==0){
        bll.addMovie(movie, listCategoryToMovie);
        mwc.loadMovie();
        
        Stage stage = (Stage) btnCancel1.getScene().getWindow();
        stage.close();}
        
        else{labelEmptyFields.setText("Please fill out all of the fields!");
            labelError2.setText("Be sure that you don't already have this move");}}
    }

    @FXML
    private void buttonChoose(ActionEvent event) {
         JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter( "Select From MP4", "mp4");
        FileNameExtensionFilter filter2 = new FileNameExtensionFilter( "Select From MPEG4", "mpeg4");
        chooser.setFileFilter(filter);
        chooser.setFileFilter(filter2);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            String songPath = chooser.getSelectedFile().getAbsolutePath();
            textFieldFile.setText(songPath);
        }
      }

    // adds category list to the new list which we will use for database
    @FXML
    private void addCategorie(ActionEvent event) {
        if(tableCategories.getSelectionModel().getSelectedItem() !=null){
        Category category = tableCategories.getSelectionModel().getSelectedItem();
        
        
        listCategoryToMovie.addAll(category);
        tableListCategoriesToMovie.getItems().clear();
        tableListCategoriesToMovie.getItems().addAll(listCategoryToMovie);
        
        }
    }
    
   
    
}
