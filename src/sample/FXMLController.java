package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class FXMLController implements Initializable {

    @FXML private TextField securityID;
    @FXML private TextField tradeID;
    @FXML private ChoiceBox<String> securityIDType;

    @FXML private Button createReportButton;
    @FXML private Text errorText;

    private TextField[] allTheTextFields = new TextField[2];
    private ChoiceBox[] allTheChoiceBoxes = new ChoiceBox[1];
    private String[] allTheText = new String[2];
    private ObservableList<String> securityIDTypeOptions = FXCollections.observableArrayList("1 - CUSIP", "2 - ISIN");

    public void createLogForm(ActionEvent actionEvent){
        allTheTextFields[0] = securityID;
        allTheTextFields[1] = tradeID;

        allTheChoiceBoxes[0] = securityIDType;

        boolean errorsPresent = false;

        for (ChoiceBox choiceBox : allTheChoiceBoxes){
            if(!isChoiceBoxSelected(choiceBox)){
                System.out.println(choiceBox.toString() + " is wrong.");
                errorsPresent = true;
                createErrorText();
                break;
            }
        }

        for (TextField aTextField : allTheTextFields) {
            System.out.println(aTextField.getText());
            if (!isCorrectInputTypeTextFields(aTextField)) {
                System.out.println(aTextField.getText() + " is wrong.");
                highlightIncorrectField(aTextField);
                errorsPresent = true;
                createErrorText();
                break;
            }
        }

        if(!errorsPresent) {
            // TODO: Maybe unnecessary line below //
            allTheText = createArrayOfTextFromTextFields();
            System.out.printf("Form has been created. Thank you %s.", securityID.getText());

            // CompletedForm completedForm = new CompletedForm();
        }
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        securityIDType.setItems(securityIDTypeOptions);
    }

    private boolean isCorrectInputTypeTextFields(TextField textField){
        String theField = textField.getId();
        switch (theField){
            case "securityIDFXML":
                if(securityID.getText().length() != 12 || !isAlphanumeric(securityID.getText())){
                    return false;
                } break;
            case "tradeIDFXML":
                //TODO: change this. just for testing //
                if(tradeID.getText().length() != 5){
                    return false;
                } break;
        }
        return true;
    }

    private void createErrorText(){
        errorText.setVisible(true);
    }

    private boolean isChoiceBoxSelected(ChoiceBox choiceBox){
        return choiceBox.getValue() != null;
    }

    private void highlightIncorrectField(TextField textField){
        //TODO: highlight it in red (the requirements will be already next to it)//
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
        System.out.println(Arrays.toString(allTheText));
        return allTheText;
    }
}
