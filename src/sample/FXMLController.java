package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLController implements Initializable {

    @FXML private TextField securityID;
    @FXML private TextField tradeID;
    @FXML private Button createReportButton;

    private TextField[] allTheTextFields = {securityID};

    public void createLogForm(ActionEvent actionEvent){
        String[] allTheText = createArrayOfTextFromTextFields();

        for (TextField aTextField : allTheTextFields){
            if(!isCorrectInputType(aTextField)){
                System.out.println(aTextField.getText() + "is wrong.");
                highlightIncorrectField(aTextField);
                break;
            }
            System.out.printf("Form has been created. Thank you %s.", securityID.getText());
        }


        // TODO: make a fucktillion confirmations that fields are correct. Don't allow create form until all return true. Do this in a for loop //

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private boolean isCorrectInputType(TextField textField){
        switch (textField.getId()){
            // TODO: this isn't working because it needs to be compile time ready. Right now it only gets text WAY later. Change it to actual text or some shit. Or learn how to enum //
            case securityID.getId():
                if(securityID.getText().length() != 12 || !isAlphanumeric(securityID.getText())){
                    return false;
                } break;
            case tradeID.getId():
                //TODO: change this. just for testing //
                if(tradeID.getId().length() != 30){
                    return false;
                } break;
        }
        return true;
    }

    private void highlightIncorrectField(TextField textField){
        //TODO: highlight it in red (the requirements will be already next to it)//
        // TODO: scroll to this field //
    }

    private boolean isAlphanumeric(String theString){
        for (int i = 0; i < theString.length(); i++){
            char c = theString.charAt(i);
            if(!Character.isLetterOrDigit(c)){
                return false;
            }
        } return true;
    }


    private String[] createArrayOfTextFromTextFields(){
        String[] allTheText = new String[allTheTextFields.length];
        for(int i = 0; i < allTheTextFields.length; i++){
            allTheText[i] = allTheTextFields[i].getText();
        }
        return allTheText;
    }

}
