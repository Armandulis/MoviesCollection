/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movies.GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import movies.BE.Movie;
import movies.BLL.MoviesBLL;

/**
 * FXML Controller class
 *
 * @author Arman
 */
public class ScoreMovieController implements Initializable {
        
        FXMLDocumentController mwc;
    @FXML
    private Label labelMovieTitle;
    @FXML
    private TextField textFieldScore;
    @FXML
    private Button buttonCancel;
    
    MoviesBLL bll = new MoviesBLL();
    
     public void setMainViewCont(FXMLDocumentController mwc){
        this.mwc=mwc;
        }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void buttonCancel(ActionEvent event) {
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void buttonSave(ActionEvent event) {
        Movie movie = mwc.getSelectedMovie();
        movie.setPersonalScore(textFieldScore.getText());
        bll.setPersonalScore(movie);
        mwc.loadMovie();
        
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }
    
}
