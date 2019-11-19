package project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class FXMLController implements Initializable {

    private static final int TEXT_FIELDTYPE = 1;
    private static final int CHOICE_FIELDTYPE = 2;
    private static final int COMBO_FIELDTYPE = 3;

    private Stage stage;
    private ACompletedForm selectedForm;

    @FXML private TextField cancelTradeID;
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
    @FXML private TextField selectedFileText;

    @FXML private TableView<ACompletedForm> tableView;

    private TextField[] allTheTextFields = new TextField[12];
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

    public void createLogForm(ActionEvent actionEvent) throws FileNotFoundException {
        gatherDataFromFieldsAndCreateArraysForRequiredChecks();
        boolean errorsPresent = false;

        // Before creating the form, all fields are error-checked here //
        for (ChoiceBox choiceBox : allTheChoiceBoxes) {
            choiceBox.setStyle(null);
            if (!testingInputs.isChoiceBoxSelected(choiceBox)) {
                errorsPresent = true;
                createErrorResponse(choiceBox, CHOICE_FIELDTYPE);
                break;
            }
        }
        for (TextField aTextField : allTheTextFields) {
            aTextField.setStyle("-fx-text-inner-color: black;");
            if (!isCorrectInputTypeTextFields(aTextField)) {
                errorsPresent = true;
                createErrorResponse(aTextField, TEXT_FIELDTYPE);
                break;
            }
        }
        for (ComboBox aComboBox : allTheComboBoxes) {
            aComboBox.setStyle(null);
            if (!testingInputs.isComboBoxSelected(aComboBox)) {
                errorsPresent = true;
                createErrorResponse(aComboBox, COMBO_FIELDTYPE);
                break;
        }
    }
        if(!errorsPresent) {
            ACompletedForm aCompletedForm = new ACompletedForm
                    (securityID.getText(), securityIDType.getValue(), setAppropriateTradeID(whichRadioButton()), originalID.getText(),
                            whichRadioButton(), executionDate.getText(), executionTime.getText(), settlementDate.getText(),
                            traderID.getValue(), reportingDealerID.getText(), counterPartyType.getValue(),
                            counterPartyID.getValue(), customerAccountType.getValue(), customerAccountLEI.getValue(),
                            customerAccountID.getText(), introDCarry.getValue(), electronicExecution.getValue(), tradingVenueID
                            .getValue(), side.getValue(), quantity.getText(), price.getText(), benchmarkSecurityID.getText(),
                            benchmarkSecurityIDType.getValue(), yield.getText(), commission.getText(), capacity.getValue(),
                            primaryMarket.getValue(), relatedPTY.getValue(), nonResident.getValue(), feeBasedAccount.getValue());

            if(whichRadioButton() == 0 || whichRadioButton() == 1) {
                String aFile = aCompletedForm.createCSVFormat();
                aCompletedForm.writeToFile(aFile);
            } else if (whichRadioButton() == 2){
                // instead of having corrections, instead it will produce a cancelled trade line and then a new trade line afterwards //
                selectedForm.setTransType(1);
                selectedForm.setOriginalTradeID(originalID.getText());
                selectedForm.setTradeID(cancelTradeID.getText()); // the cancelID field becomes the new tradeID //
                String firstLineOfFile = selectedForm.createCSVFormat(); // a cancel trade //
                aCompletedForm.setTransType(0);
                aCompletedForm.setOriginalTradeID("");
                String secondLineOfFile = aCompletedForm.createCSVFormat(); // a new trade //
                aCompletedForm.writeToFile(firstLineOfFile, secondLineOfFile);
            }
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
        // Initialization had to be done later on for some reason so it's all moved into this function //
        allTheTextFields[0] = securityID;
        allTheTextFields[1] = tradeID;
        allTheTextFields[2] = cancelTradeID;
        allTheTextFields[3] = executionDate;
        allTheTextFields[4] = executionTime;
        allTheTextFields[5] = settlementDate;
        allTheTextFields[6] = customerAccountID;
        allTheTextFields[7] = quantity;
        allTheTextFields[8] = price;
        allTheTextFields[9] = benchmarkSecurityID;
        allTheTextFields[10] = yield;
        allTheTextFields[11] = commission;


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
                if(whichRadioButton() == 0 || whichRadioButton() == 2) {
                    if (tradeID.getText().length() != 5) {
                        return false;
                    }
                }break;
            case "cancelTradeIDFXML":
                if(whichRadioButton() == 1 || whichRadioButton() == 2){
                if(cancelTradeID.getText().length() != 5) {
                    return false;
                }
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
                if(!testingInputs.isNumerical(quantity.getText()) || quantity.getText().isEmpty()){
                    return false;
                } break;
            case "priceFXML":
                if(!testingInputs.isFloat(price.getText()) || price.getText().isEmpty()){
                    return false;
                } break;
            case "benchmarkSecurityIDFXML":
                if(benchmarkSecurityID.getText().isEmpty()){
                    return true;
                }else if(benchmarkSecurityID.getText().length() != 12 || !testingInputs.isAlphanumeric(benchmarkSecurityID.getText())){
                    return false;
                } break;
            case "yieldFXML":
                if(!testingInputs.isFloat(yield.getText()) || yield.getText().isEmpty()){
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
                theChoiceBox.setStyle("-fx-base: #f54a32");
                break;
            case 3:
                ComboBox theComboBox = (ComboBox) theField;
                theComboBox.setStyle("-fx-base: #f54a32");
                break;
        }
    }

    @FXML
    private void openFileExplorer(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                fillTableView(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void fillTableView(File file) throws IOException {
        selectedFileText.setText(file.getAbsolutePath());
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String lines = buffer.readLine();
        Field[] fields = ACompletedForm.class.getDeclaredFields();
        String[] fieldVariables = new String[30];
        for(int i = 0; i < fields.length; i++){
            fieldVariables[i] = fields[i].getName();
        }
        for (String fieldVariable : fieldVariables) {
            TableColumn<ACompletedForm, String> tableColumn = new TableColumn<>(fieldVariable);
            tableColumn.setCellValueFactory(new PropertyValueFactory<>(fieldVariable));
            tableView.getColumns().add(tableColumn);
        }
        ObservableList<ACompletedForm> theDataList = FXCollections.observableArrayList();
        while (lines != null) {
            String[] anotherLine = lines.split(",");
            // The constructor automatically does the dealerCode conversion. Since this is reversed - some magic here is here to put the dealerCode in,
            // change it to a dealerName, and then the constructor will switch it back again to the dealerCode //
            ACompletedForm aCompletedForm = new ACompletedForm(anotherLine[0], anotherLine[1], anotherLine[2], anotherLine[3],
                    Integer.parseInt(anotherLine[4]), anotherLine[5], anotherLine[6], anotherLine[7], anotherLine[8], anotherLine[9], anotherLine[10],
                    allTheDealers.getADealerName(anotherLine[11]), anotherLine[12], allTheDealers.getADealerName(anotherLine[13]),
                    anotherLine[14], anotherLine[15], anotherLine[16], allTheDealers.getADealerName(anotherLine[17]), anotherLine[18],
                    anotherLine[19], anotherLine[20], anotherLine[21], anotherLine[22], anotherLine[23], anotherLine[24],
                    anotherLine[25], anotherLine[26], anotherLine[27], anotherLine[28], anotherLine[29]);

            theDataList.add(aCompletedForm);
            lines = buffer.readLine();
        }
        tableView.setItems(theDataList);
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

    private String setAppropriateTradeID(int transType) {
        if (transType == 1) {
            return cancelTradeID.getText();
        }
        return tradeID.getText();
    }

    public void setVisibility(ActionEvent actionEvent) {
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        if(actionEvent.getSource().toString().contains("transNew")){
            originalID.setVisible(false);
            tradeID.setVisible(true);
            cancelTradeID.setVisible(false);
            stageTheEventSourceNodeBelongs.setWidth(670);
        } else if (actionEvent.getSource().toString().contains("transCancel")) {
            originalID.setVisible(true);
            tradeID.setVisible(false);
            cancelTradeID.setVisible(true);
            stageTheEventSourceNodeBelongs.setWidth(1000);
        } else if (actionEvent.getSource().toString().contains("transCorrection")){
            originalID.setVisible(true);
            tradeID.setVisible(true);
            cancelTradeID.setVisible(true);
            stageTheEventSourceNodeBelongs.setWidth(1000);
        }
    }

    public void moveData(ActionEvent actionEvent) {
        try {
            selectedForm = tableView.getSelectionModel().getSelectedItem();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        securityID.setText(selectedForm.getSecurityID());
        securityIDType.getSelectionModel().select(Integer.parseInt(selectedForm.getSecurityIDType()));
        tradeID.setText(""); // the tradeID will always have to be a new input //
        originalID.setText(selectedForm.getTradeID());
        executionDate.setText(selectedForm.getExecutionDate());
        executionTime.setText(selectedForm.getExecutionTime());
        settlementDate.setText(selectedForm.getSettlementDate());
        traderID.getSelectionModel().select(selectedForm.getTraderID());
        reportingDealerID.setText(selectedForm.getReportingDealerID());
        counterPartyType.getSelectionModel().select(Integer.parseInt(selectedForm.getCounterPartyType()));
        counterPartyID.getSelectionModel().select(allTheDealers.getADealerName(selectedForm.getCounterPartyID()));
        try {
            customerAccountType.getSelectionModel().select(Integer.parseInt(selectedForm.getCustomerAccType()) - 1);
        } catch (NumberFormatException e){
            customerAccountType.getSelectionModel().select(0);
        }
        customerAccountLEI.getSelectionModel().select(allTheDealers.getADealerName(selectedForm.getCustomerLEI()));
        customerAccountID.setText(selectedForm.getCustomerAccountID());
        introDCarry.getSelectionModel().select(Integer.parseInt(selectedForm.getIntroDCarry()));
        electronicExecution.getSelectionModel().select(selectedForm.getElectronicExecution());
        tradingVenueID.getSelectionModel().select(allTheDealers.getADealerName(selectedForm.getTradingVenueID()));
        side.getSelectionModel().select(Integer.parseInt(selectedForm.getSide())-1);
        quantity.setText(String.valueOf(selectedForm.getQuantity()));
        price.setText(String.valueOf(selectedForm.getPrice()));
        benchmarkSecurityID.setText(selectedForm.getBenchmarkSecurityID());
        try {
            benchmarkSecurityIDType.getSelectionModel().select(Integer.parseInt(selectedForm.getBenchmarkSecurityIDType()) - 1);
        } catch (NumberFormatException f){
            benchmarkSecurityIDType.getSelectionModel().select(0);
        }
        yield.setText(String.valueOf(selectedForm.getYield()));
        commission.setText(selectedForm.getCommission());
        capacity.getSelectionModel().select(Integer.parseInt(selectedForm.getCapacity())-1);
        primaryMarket.getSelectionModel().select(stupidWtvrPants(String.valueOf(selectedForm.getPrimaryMarket())));
        relatedPTY.getSelectionModel().select(stupidWtvrPants(String.valueOf(selectedForm.getRelatedParty())));
        nonResident.getSelectionModel().select(stupidWtvrPants(String.valueOf(selectedForm.getNonResident())));
        feeBasedAccount.getSelectionModel().select(stupidWtvrPants(String.valueOf(selectedForm.getFeeBasedAccount())));
    }

    public int stupidWtvrPants(String string){
        if(string.equals("Y")){
            return 0;
        } else return 1;
    }

    @FXML
    public void openHelpDialog(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("How each Transaction Type works: ");
        alert.setContentText("New: Fill in the required fields below and click the \"Create Report\" button. \n\n" +
                            "Cancel: Follow the directions in the added frame and select the desired trade to be cancelled. " +
                            "Fill in the Cancellation Trade ID field (will become the new Trade ID) and click \"Create Report\". \n" +
                            "DO NOT change any of the other fields.\n \n" +
                            "Cancel and Correct: Follow the directions in the added frame and select the desired trade to be cancelled. " +
                            "This will automatically set it up to become a cancelled trade entry. Now, change the desired field(s). " +
                            "TradeID will be the ID of the new trade and Cancellation Trade ID will be the TradeID of the trade to be cancelled. \n\n" +
                            "All files will be created in the User/IIROC_Reports directory.");
        alert.showAndWait();
    }
}
