package sample;

public class CompletedForm {

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
    private float quantity;
    private float price;
    private String benchmarkSecurityID;
    private int benchmarkSecurityIDType;
    private float yield; // percentage needed here as the type //
    private float commission;
    private int capacity;
    private char primaryMarket;
    private char relatedParty;
    private char nonResident;
    private char feeBasedAccount;

    // TODO: how to make some of these fields nullable //
    public CompletedForm(String securityID, int securityIDType, String tradeID, String originalTradeID, int transType,
                         String executionDate, String executionTime, String settlementDate, String traderID,
                         String reportingDealerID, int counterPartyType, String counterPartyID, int customerAccType,
                         String customerLEI, String customerAccountID, int introDCarry, char electronicExecution,
                         String tradingVenueID, int side, float quantity, float price, String benchmarkSecurityID,
                         int benchmarkSecurityIDType, float yield, float commission, int capacity, char primaryMarket,
                         char relatedParty, char nonResident, char feeBasedAccount) {
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

    // TODO: make some methods here that take all this data and generate a .csv file //


}
