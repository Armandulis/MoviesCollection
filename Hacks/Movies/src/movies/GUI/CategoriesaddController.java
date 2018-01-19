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
import movies.BE.Category;
import movies.BLL.MoviesBLL;

/**
 * FXML Controller class
 *
 * @author Skomantas
 */
public class CategoriesaddController implements Initializable {
    
    FXMLDocumentController mwc;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;
    @FXML
    private TextField textfield;
    MoviesBLL bll = new MoviesBLL();
    @FXML
    private Label labelError;
    
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
    private void buttonSave(ActionEvent event) {
        if(!textfield.getText().isEmpty()){
    mwc.clearCategoryList();
    Category category = new Category();
    category.setCategory(textfield.getText());
    bll.addCategory(category);
    mwc.loadCategories();
    Stage stage = (Stage) btnCancel.getScene().getWindow();
    stage.close();}
    else{labelError.setText("Please name new category");}
    }
}

