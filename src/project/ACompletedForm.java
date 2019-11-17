package project;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

// Not just an object creation class. Once the object is constructed a .csv is immediately created //

class ACompletedForm {

    private AllTheDealers allTheDealers = new AllTheDealers();
    private String date;

    private String securityID;
    private int securityIDType;
    private String tradeID;
    private String originalTradeID;
    private int transType;
    private String executionDate;
    private String executionTime;
    private String settlementDate;
    private String traderID;
    private String reportingDealerID;
    private int counterPartyType;
    private String counterPartyID;
    private int customerAccType;
    private String customerLEI;
    private String customerAccountID;
    private int introDCarry;
    private char electronicExecution;
    private String tradingVenueID;
    private int side;
    private int quantity;
    private float price;
    private String benchmarkSecurityID;
    private int benchmarkSecurityIDType;
    private float yield;
    private float commission;
    private int capacity;
    private char primaryMarket;
    private char relatedParty;
    private char nonResident;
    private char feeBasedAccount;

    // A second constructor for uploading a .csv into an ObservableList<ACompletedForm> //
    public ACompletedForm(String securityID, int securityIDType, String tradeID, String originalTradeID, int transType,
                          String executionDate, String executionTime, String settlementDate, String traderID, String reportingDealerID,
                          int counterPartyType, String counterPartyID, int customerAccType, String customerLEI,
                          String customerAccountID, int introDCarry, char electronicExecution, String tradingVenueID,
                          int side, int quantity, float price, String benchmarkSecurityID, int benchmarkSecurityIDType,
                          float yield, float commission, int capacity, char primaryMarket, char relatedParty, char nonResident,
                          char feeBasedAccount) {
        this.securityID = securityID;
        this.securityIDType = securityIDType;
        this.tradeID = tradeID;
        this.originalTradeID = originalTradeID;
        this.transType = transType;
        this.executionDate = executionDate;
        this.executionTime = executionTime;
        this.settlementDate = settlementDate;
        this.traderID = traderID;
        this.reportingDealerID = reportingDealerID;
        this.counterPartyType = counterPartyType;
        this.counterPartyID = counterPartyID;
        this.customerAccType = customerAccType;
        this.customerLEI = customerLEI;
        this.customerAccountID = customerAccountID;
        this.introDCarry = introDCarry;
        this.electronicExecution = electronicExecution;
        this.tradingVenueID = tradingVenueID;
        this.side = side;
        this.quantity = quantity;
        this.price = price;
        this.benchmarkSecurityID = benchmarkSecurityID;
        this.benchmarkSecurityIDType = benchmarkSecurityIDType;
        this.yield = yield;
        this.commission = commission;
        this.capacity = capacity;
        this.primaryMarket = primaryMarket;
        this.relatedParty = relatedParty;
        this.nonResident = nonResident;
        this.feeBasedAccount = feeBasedAccount;
    }

    // All (except transType) conversions to the appropriate format are done during construction //
    ACompletedForm(String securityID, String securityIDType, String tradeID, String originalTradeID, int transType,
                          String executionDate, String executionTime, String settlementDate, String traderID,
                          String reportingDealerID, String counterPartyType, String counterPartyID, String customerAccType,
                          String customerLEI, String customerAccountID, String introDCarry, String electronicExecution,
                          String tradingVenueID, String side, String quantity, String price, String benchmarkSecurityID,
                          String benchmarkSecurityIDType, String yield, String commission, String capacity, String primaryMarket,
                          String relatedParty, String nonResident, String feeBasedAccount) {
        this.securityID = securityID;
        this.securityIDType = formatToInt(securityIDType);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date currentdate = new Date();
        date = formatter.format(currentdate);
        this.tradeID = formatter.format(date) + tradeID;
        this.originalTradeID = originalTradeID;
        this.transType = transType;
        this.executionDate = executionDate;
        this.executionTime = executionTime;
        this.settlementDate = settlementDate;
        this.traderID = traderID;
        this.reportingDealerID = reportingDealerID;
        this.counterPartyType = formatToInt(counterPartyType);
        this.counterPartyID = getDealerCode(checkComboBoxNotSelectedOnNonRequired(counterPartyID));
        this.customerAccType = formatToInt(customerAccType);
        this.customerLEI = getDealerCode(checkComboBoxNotSelectedOnNonRequired(customerLEI));
        this.customerAccountID = customerAccountID;
        this.introDCarry = formatToInt(introDCarry);
        this.electronicExecution = electronicExecution.charAt(0);
        this.tradingVenueID = getDealerCode(checkComboBoxNotSelectedOnNonRequired(tradingVenueID));
        this.side = formatToInt(side);
        this.quantity = Integer.parseInt(quantity);
        this.price = Float.parseFloat(price);
        this.benchmarkSecurityID = benchmarkSecurityID;
        this.benchmarkSecurityIDType = formatToInt(benchmarkSecurityIDType);
        this.yield = Float.parseFloat(yield);
        this.commission = setCommission(commission);
        this.capacity = formatToInt(capacity);
        this.primaryMarket = primaryMarket.charAt(0);
        this.relatedParty = relatedParty.charAt(0);
        this.nonResident = nonResident.charAt(0);
        this.feeBasedAccount = feeBasedAccount.charAt(0);

        createCSVFile();
    }

    private String getDealerCode(String string){
        try{
            return allTheDealers.getADealerCode(string);
        } catch (NullPointerException e){
            e.printStackTrace();
            return "";
        }
    }

    private int formatToInt(String string){
        try {
            for (int i = 0; i < 8; i++) {
                if (string.contains(String.valueOf(i))) {
                    return i;
                }
            }
            return -4206969;
            // TODO: you can do better than this catching NPEs crap //
        } catch (NullPointerException e){
            return -4206969;
        }
    }

    private void createCSVFile(){
        String[] finalList = {getSecurityID(),String.valueOf(getSecurityIDType()), getTradeID(), getOriginalTradeID(),
                String.valueOf(getTransType()), getExecutionDate(), getExecutionTime(), getSettlementDate(), getTraderID(),
                getReportingDealerID(), String.valueOf(getCounterPartyType()), getCounterPartyID(), String.valueOf(getCustomerAccType()),
                getCustomerLEI(), getCustomerAccountID(), String.valueOf(getIntroDCarry()), String.valueOf(getElectronicExecution()), getTradingVenueID(),
                String.valueOf(getSide()), String.valueOf(getQuantity()), String.valueOf(getPrice()), getBenchmarkSecurityID(),
                String.valueOf(getBenchmarkSecurityIDType()), String.valueOf(getYield()), String.valueOf(getCommission()),
                String.valueOf(getCapacity()), String.valueOf(getPrimaryMarket()), String.valueOf(getRelatedParty()),
                String.valueOf(getNonResident()), String.valueOf(getFeeBasedAccount())};

        // this changes all of the unfilled combo/choice boxes to blanks //
        for(int i = 0; i < finalList.length; i++){
            if(finalList[i].equals("-4206969") || finalList[i].equals("-4206969.0")){
                finalList[i] = "";
            }
        }
        System.out.println(Arrays.toString(finalList));

        StringBuilder sb = new StringBuilder();
        for(String string : finalList){
            sb.append(string);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);

        // TODO: write this to \\documents PATH // // remember to use mkdirs. //
        try (PrintWriter writer = new PrintWriter(new File(date + "_" + getReportingDealerID() + "_KERN-MTRS_DEBT.csv"))){
            writer.write(sb.toString());
            System.out.println("done!");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public String checkComboBoxNotSelectedOnNonRequired(String string){
        if(string.contains("--")){
            return "";
        } return string;
    }

    public float setCommission(String commission) {
        if (commission.isEmpty()) {
            return -4206969;
        }
        try {
            return Float.parseFloat(commission);
        } catch (NumberFormatException e) {
            return -42069696;
        }
    }

    public String getSecurityID() {
        return securityID;
    }

    public int getSecurityIDType() {
        return securityIDType;
    }

    public String getTradeID() {
        return tradeID;
    }

    public String getOriginalTradeID() {
        if(originalTradeID.isEmpty()){
            return "";
        }
        return originalTradeID;
    }

    public int getTransType() {
        return transType;
    }

    public String getExecutionDate() {
        return executionDate;
    }

    public String getExecutionTime() {
        return executionTime;
    }

    public String getSettlementDate() {
        return settlementDate;
    }

    public String getTraderID() {
        return traderID;
    }

    public String getReportingDealerID() {
        return reportingDealerID;
    }

    public int getCounterPartyType() {
        return counterPartyType;
    }

    public String getCounterPartyID() {
        if(counterPartyID.isEmpty()){
            return "";
        }
        return counterPartyID;
    }

    public int getCustomerAccType() {
        return customerAccType;
    }

    public String getCustomerLEI() {
        if(customerLEI.isEmpty()){
            return "";
        }
        return customerLEI;
    }

    public String getCustomerAccountID() {
        if(customerAccountID.isEmpty()){
            return "";
        }
        return customerAccountID;
    }

    public int getIntroDCarry() {
        return introDCarry;
    }

    public char getElectronicExecution() {
        return electronicExecution;
    }

    public String getTradingVenueID() {
        if(tradingVenueID.isEmpty()){
            return "";
        }
        return tradingVenueID;
    }

    public int getSide() {
        return side;
    }

    public float getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public String getBenchmarkSecurityID() {
        if(benchmarkSecurityID.isEmpty()){
            return "";
        }
        return benchmarkSecurityID;
    }

    public int getBenchmarkSecurityIDType() {
        return benchmarkSecurityIDType;
    }

    public float getYield() {
        return yield;
    }

    public float getCommission() {
        return commission;
    }

    public int getCapacity() {
        return capacity;
    }

    public char getPrimaryMarket() {
        return primaryMarket;
    }

    public char getRelatedParty() {
        return relatedParty;
    }

    public char getNonResident() {
        return nonResident;
    }

    public char getFeeBasedAccount() {
        return feeBasedAccount;
    }
}

