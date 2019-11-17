package project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class FXMLController implements Initializable {

    private static final int TEXT_FIELDTYPE = 1;
    private static final int CHOICE_FIELDTYPE = 2;
    private static final int COMBO_FIELDTYPE = 3;

    @FXML private TextField originalID;
    @FXML private TextField securityID;
    @FXML private TextField tradeID;
    @FXML private TextField reportingDealerID;
    @FXML private TextField executionDate;
    @FXML private TextField executionTime;
    @FXML private TextField settlementDate;
    @FXML private TextField quantity;
    @FXML private TextField price;
    @FXML private TextField customerAccountID;
    @FXML private TextField benchmarkSecurityID;
    @FXML private TextField yield;
    @FXML private TextField commission;

    private ToggleGroup toggleGroup;
    @FXML private RadioButton transNew;
    @FXML private RadioButton transCancel;
    @FXML private RadioButton transCorrection;

    @FXML private ChoiceBox<String> customerAccountType;
    @FXML private ChoiceBox<String> side;
    @FXML private ChoiceBox<String> capacity;
    @FXML private ChoiceBox<String> primaryMarket;
    @FXML private ChoiceBox<String> relatedPTY;
    @FXML private ChoiceBox<String> nonResident;
    @FXML private ChoiceBox<String> feeBasedAccount;

    @FXML private ComboBox<String> electronicExecution;
    @FXML private ComboBox<String> securityIDType;
    @FXML private ComboBox<String> counterPartyType;
    @FXML private ComboBox<String> counterPartyID;
    @FXML private ComboBox<String> benchmarkSecurityIDType;
    @FXML private ComboBox<String> customerAccountLEI;
    @FXML private ComboBox<String> traderID;
    @FXML private ComboBox<String> introDCarry;
    @FXML private ComboBox<String> tradingVenueID;

    @FXML private Text errorText;

    private TextField[] allTheTextFields = new TextField[11];
    private ChoiceBox[] allTheChoiceBoxes = new ChoiceBox[6];
    private ComboBox[] allTheComboBoxes = new ComboBox[5];
    private AllTheDealers allTheDealers = new AllTheDealers();

    private TestingInputs testingInputs = new TestingInputs();

    private ObservableList<String> secIDTypeOptions = FXCollections.observableArrayList("--Security ID Type-- *","1 - CUSIP", "2 - ISIN");
    private ObservableList<String> benchSecIDTypeOptions = FXCollections.observableArrayList("--Bench Security ID Type--","1 - CUSIP", "2 - ISIN");
    private ObservableList<String> traderIDOptions = FXCollections.observableArrayList("--Trader ID-- *","AARONLAIDLAW");
    private ObservableList<String> custAccountTypeOptions = FXCollections.observableArrayList("1 - Retail", "2 - Institutional");
    private ObservableList<String> counterPartyTypeOptions = FXCollections.observableArrayList("--CounterParty Type-- *","1 - Client", "2 - Non-client", "3 - Dealer", "4 - IDBB", "5 - ATS", "6 - Bank", "7 - Issuer");
    private ObservableList<String> introDCarryOptions = FXCollections.observableArrayList("--IntroD Carry-- *", "1 - Introducing", "2 - Carrying", "3 - N/A");
    private ObservableList<String> sideOptions = FXCollections.observableArrayList("1 - Buy", "2 - Sell");
    private ObservableList<String> capacityOptions = FXCollections.observableArrayList("1 - Agency", "2 - Principal");
    private ObservableList<String> YNOptions = FXCollections.observableArrayList("Y", "N");
    private ObservableList<String> electronicOptions = FXCollections.observableArrayList("--Electronic Execution-- *","Y", "N");

    public void createLogForm(ActionEvent actionEvent) {
        gatherDataFromFieldsAndCreateArraysForRequiredChecks();
        boolean errorsPresent = false;

        // Before creating the form, all fields are error checked here //
        for (ChoiceBox choiceBox : allTheChoiceBoxes) {
//            choiceBox.setStyle("-fx-text-fill: black;");
            if (!testingInputs.isChoiceBoxSelected(choiceBox)) {
                System.out.println(choiceBox.toString() + " is wrong.");
                errorsPresent = true;
                createErrorResponse(choiceBox, CHOICE_FIELDTYPE);
                break;
            }
        }
        for (TextField aTextField : allTheTextFields) {
            aTextField.setStyle("-fx-text-inner-color: black;");
            if (!isCorrectInputTypeTextFields(aTextField)) {
                System.out.println(aTextField.getText() + " is wrong.");
                errorsPresent = true;
                createErrorResponse(aTextField, TEXT_FIELDTYPE);
                break;
            }
        }
        for (ComboBox aComboBox : allTheComboBoxes) {
            // aComboBox.setStyle("-fx-text-fill: black;");
            if (!testingInputs.isComboBoxSelected(aComboBox)) {
                System.out.println(aComboBox.toString() + " is wrong.");
                errorsPresent = true;
                createErrorResponse(aComboBox, COMBO_FIELDTYPE);
                break;
        }
    }
        if(!errorsPresent) {
            ACompletedForm aCompletedForm = new ACompletedForm
                    (securityID.getText(), securityIDType.getValue(), tradeID.getText(), originalID.getText(),
                            whichRadioButton(), executionDate.getText(), executionTime.getText(), settlementDate.getText(),
                            traderID.getValue(), reportingDealerID.getText(), counterPartyType.getValue(),
                            counterPartyID.getValue(), customerAccountType.getValue(), customerAccountLEI.getValue(),
                            customerAccountID.getText(), introDCarry.getValue(), electronicExecution.getValue(), tradingVenueID
                            .getValue(), side.getValue(), quantity.getText(), price.getText(), benchmarkSecurityID.getText(),
                            benchmarkSecurityIDType.getValue(), yield.getText(), commission.getText(), capacity.getValue(),
                            primaryMarket.getValue(), relatedPTY.getValue(), nonResident.getValue(), feeBasedAccount.getValue());

            refreshAllFields();
        }
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        securityIDType.setItems(secIDTypeOptions);
        traderID.setItems(traderIDOptions);
        counterPartyID.setItems(FXCollections.observableArrayList(allTheDealers.getAllDealerNames("--CounterParty ID--")));
        counterPartyType.setItems(counterPartyTypeOptions);
        customerAccountLEI.setItems(FXCollections.observableArrayList(allTheDealers.getAllDealerNames("--Customer Account LEI--")));
        customerAccountType.setItems(custAccountTypeOptions);
        introDCarry.setItems(introDCarryOptions);
        electronicExecution.setItems(electronicOptions);
        tradingVenueID.setItems(FXCollections.observableArrayList(allTheDealers.getAllDealerNames("--Trading Venue ID--")));
        side.setItems(sideOptions);
        benchmarkSecurityIDType.setItems(benchSecIDTypeOptions);
        capacity.setItems(capacityOptions);
        primaryMarket.setItems(YNOptions);
        relatedPTY.setItems(YNOptions);
        nonResident.setItems(YNOptions);
        feeBasedAccount.setItems(YNOptions);

        toggleGroup = new ToggleGroup();
        transNew.setToggleGroup(toggleGroup);
        transCancel.setToggleGroup(toggleGroup);
        transCorrection.setToggleGroup(toggleGroup);
        transNew.setSelected(true);

        setCompGeneratedFields();
    }

    private void setCompGeneratedFields(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        executionDate.setText(formatter.format(date));
        reportingDealerID.setText("IIROCOOOOOOOOOOOKERN");
        introDCarry.getSelectionModel().select(1);
        traderID.getSelectionModel().select(1);
        electronicExecution.getSelectionModel().select(2);
    }

    private void gatherDataFromFieldsAndCreateArraysForRequiredChecks(){
        // Initialization had to be done later on for some reason so it's all moved into this verbose function //
        allTheTextFields[0] = securityID;
        allTheTextFields[1] = tradeID;
        allTheTextFields[2] = executionDate;
        allTheTextFields[3] = executionTime;
        allTheTextFields[4] = settlementDate;
        allTheTextFields[5] = customerAccountID;
        allTheTextFields[6] = quantity;
        allTheTextFields[7] = price;
        allTheTextFields[8] = benchmarkSecurityID;
        allTheTextFields[9] = yield;
        allTheTextFields[10] = commission;

        allTheChoiceBoxes[0] = side;
        allTheChoiceBoxes[1] = capacity;
        allTheChoiceBoxes[2] = primaryMarket;
        allTheChoiceBoxes[3] = relatedPTY;
        allTheChoiceBoxes[4] = nonResident;
        allTheChoiceBoxes[5] = feeBasedAccount;

        allTheComboBoxes[0] = securityIDType;
        allTheComboBoxes[1] = electronicExecution;
        allTheComboBoxes[2] = counterPartyType;
        allTheComboBoxes[3] = traderID;
        allTheComboBoxes[4] = introDCarry;
    }

    private boolean isCorrectInputTypeTextFields(TextField textField){
        String theField = textField.getId();
        switch (theField){
            case "securityIDFXML":
                if(securityID.getText().length() != 12 || !testingInputs.isAlphanumeric(securityID.getText())){
                    return false;
                } break;
            case "tradeIDFXML":
                if(tradeID.getText().length() != 5){
                    return false;
                } break;
            case "executionDateFXML":
                if(executionDate.getText().length() != 8 || !testingInputs.isExistingDate(executionDate.getText())){
                    return false;
                } break;
            case "executionTimeFXML":
                if(!testingInputs.isValidTime(executionTime.getText())){
                    return false;
                } break;
            case "settlementDateFXML":
                if(settlementDate.getText().length() != 8 || !testingInputs.isExistingDate(settlementDate.getText())){
                    return false;
                } break;
            case "customerAccountIDFXML":
                if(customerAccountID.getText().isEmpty()){
                    return true;
                } else if (!testingInputs.isAlphanumeric(customerAccountID.getText())){
                    return false;
                } break;
            case "quantityFXML":
                if(!testingInputs.isNumerical(quantity.getText())){
                    return false;
                } break;
            case "priceFXML":
                if(!testingInputs.isFloat(price.getText())){
                    return false;
                } break;
            case "benchmarkSecurityIDFXML":
                if(benchmarkSecurityID.getText().isEmpty()){
                    return true;
                }else if(benchmarkSecurityID.getText().length() != 12 || !testingInputs.isAlphanumeric(benchmarkSecurityID.getText())){
                    return false;
                } break;
            case "yieldFXML":
                if(!testingInputs.isFloat(yield.getText())){
                    return false;
                } break;
            case "commissionFXML":
                if(commission.getText().isEmpty()){
                    return true;
                }else if(!testingInputs.isFloat(commission.getText())){
                    return false;
                } break;
        }
        return true;
    }

    private void createErrorResponse(Control theField, int fieldType){
        errorText.setVisible(true);
        switch (fieldType){
            case 1:
                TextField theText = (TextField) theField;
                theText.setStyle("-fx-text-inner-color: red;");
                break;
            case 2:
                ChoiceBox theChoiceBox = (ChoiceBox) theField;
                // below needs to access the label of the choicebox //
                // TODO: instead of stressing over the text, maybe just make the background red or some shit. Anything that you can easily access on a box //
                // theChoiceBox.setStyle("-fx-text-fill: red;");
                break;
            case 3:
                ComboBox theComboBox = (ComboBox) theField;
                // theComboBox.setStyle("-fx-text-fill: red");
                break;
        }
    }

    @FXML
    private void openFileExplorer(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if(file != null){
            Desktop.getDesktop().open(file);
        }
    }

    @FXML
    private void refreshAllFields(){
        gatherDataFromFieldsAndCreateArraysForRequiredChecks();
        errorText.setVisible(false);
        for(ComboBox comboBox : allTheComboBoxes){
            comboBox.getSelectionModel().select(0);
        }
        for(TextField textField : allTheTextFields){
            textField.setText("");
        }
        for(ChoiceBox choiceBox : allTheChoiceBoxes){
            choiceBox.getSelectionModel().clearSelection();
        }
        counterPartyID.getSelectionModel().select(0);
        customerAccountLEI.getSelectionModel().select(0);
        tradingVenueID.getSelectionModel().select(0);
        setCompGeneratedFields();
    }

    private int whichRadioButton(){
        if (toggleGroup.getSelectedToggle().toString().contains("transNew")){
            return 0;
        } else if (toggleGroup.getSelectedToggle().toString().contains("transCancel")){
            return 1;
        } else {
            return 2;
        }
    }

    public void setVisibility(ActionEvent actionEvent) {
        if(actionEvent.getSource().toString().contains("transNew")){
            originalID.setVisible(false);
        } else {
            originalID.setVisible(true);
        }
    }
}
