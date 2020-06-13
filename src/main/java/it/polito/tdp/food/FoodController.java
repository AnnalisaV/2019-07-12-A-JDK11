/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.FoodCal;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtPorzioni"
    private TextField txtPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalorie"
    private Button btnCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="boxFood"
    private ComboBox<Food> boxFood; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
   

    	int portion=-1; 
    	if(this.txtPorzioni.getText().length()==0) {
    		txtResult.appendText("ERRORE : Inserire un valore \n" );
    		return; 
    	}
    	try {
    		portion= Integer.parseInt(txtPorzioni.getText()); 
    	}catch(NumberFormatException nfe) {
    		txtResult.appendText("ERRORE : Inserire un valore numerico \n" );
    		return; 
    		
    	}
    	model.creaGrafo(portion);

    	txtResult.appendText("Grafo creato con "+model.nVertex()+" vertex and "+model.nArchi()+" edges \n");
    	
    	//pulisco e popolo tendina
    	this.boxFood.getItems().removeAll(this.boxFood.getItems()); 
    	this.boxFood.getItems().addAll(this.model.getFoodVertex()); 
    	
    	this.btnCalorie.setDisable(false);
    }
    
    @FXML
    void doCalorie(ActionEvent event) {
    	txtResult.clear();
    	

    	if (boxFood.getValue()==null) {
    		txtResult.appendText("ERRORE : selezionare un Food \n");
    		return; 
    	}
    	
    	for (FoodCal f : this.model.getCalorieCongiunteMax(this.boxFood.getValue())) {
    		txtResult.appendText(f+"\n");
    	}
    	
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	

    	int k= -1; 
    	if(this.txtK.getText().length()==0) {
    		txtResult.appendText("ERRORE : Inserire un valore per le stazioni di Lavoro \n" );
    		return; 
    	}
    	try {
    		k= Integer.parseInt(txtK.getText()); 
    	}catch(NumberFormatException nfe) {
    		txtResult.appendText("ERRORE : Inserire un valore numerico \n" );
    		return; 
    		
    	}
    	if (boxFood.getValue()==null) {
    		txtResult.appendText("ERRORE : selezionare un Food \n");
    		return; 
    	}
    	
    	this.model.simula(k, this.boxFood.getValue());
    	txtResult.appendText(" Simulazione : \n\n");
    	txtResult.appendText("Cibi preparati : "+this.model.cibiPreparati()+ "\n"
    			+ "Tempo di preparazione : "+model.tempoPreparazione());
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtPorzioni != null : "fx:id=\"txtPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCalorie != null : "fx:id=\"btnCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxFood != null : "fx:id=\"boxFood\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.btnCalorie.setDisable(true);
    }
}
