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
import javafx.stage.Stage;
import movies.BLL.MoviesBLL;

/**
 * FXML Controller class
 *
 * @author Skomantas
 */
public class MoviesremoveController implements Initializable {
    FXMLDocumentController mwc;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void setMainViewCont(FXMLDocumentController mwc){
        this.mwc=mwc;
        }
    @FXML
    private void buttonCancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    
    
    @FXML
    private void buttonConfirm(ActionEvent event) {
           MoviesBLL bll = new MoviesBLL();
        bll.removeMovie(mwc.getSelectedMovieID());
        mwc.clearMovieList();
        mwc.loadMovie();
       Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
    
}
