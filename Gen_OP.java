package dk.arbetsprov;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;

public class Gen_OP extends Post {

    private String accountnr;
    private Date dateOfPayment;
    private String currency;
    private BigDecimal reportedSum;

    public BigDecimal getReportedSum(){return reportedSum;}
    public String getAccountnr(){return accountnr;}
    public Date getDateOfPayment(){return dateOfPayment;}
    public String getCurrency(){return currency;}

    private int nrOfPaymentPosts;
    public int getNrOfPaymentPosts(){ return nrOfPaymentPosts; }

    private int nrOfCharsForPostType;

    public Gen_OP(String data, int nrOfCharsForPostType, HashMap<String, DataBlock> instructions){
        super(data);
        this.nrOfCharsForPostType = nrOfCharsForPostType;
        reportedSum = new BigDecimal("0.00");
        reportedSum = reportedSum.setScale(2, RoundingMode.HALF_EVEN);
        //using -1 as default to check for errors since 0 might be usable sometime?
        nrOfPaymentPosts = -1;

        setDatablocks(instructions);

        RetrieveRequiredData();

        //Default values for date and currency specified by client in the assignment.
        if(dateOfPayment == null)
        {
            dateOfPayment = new Date();
        }
        if(currency == null){
            this.currency = "SEK";
        }

    }

    @Override
    protected void RetrieveRequiredData() {
        //Assuming posttype to be defined by the first characters in the data.
        postType = rawData.substring(0, nrOfCharsForPostType);

        /**
         * Since we can't know that posts will actually contain all required information we have to
         * check that they are present in the datablocks before trying to retrieve them.
         *
         */

        if(datablocks.containsKey("accountnr")){
            accountnr = rawData.substring(datablocks.get("accountnr").start, datablocks.get("accountnr").end);
        }

        if(datablocks.containsKey("date")){
            String sDate = rawData.substring(datablocks.get("date").start, datablocks.get("date").end);
            String year = sDate.substring(0,4);
            String month = sDate.substring(4,6);
            String day = sDate.substring(6,8);
            LocalDate date = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
            dateOfPayment = java.sql.Date.valueOf(date.toString());
        }

        if(datablocks.containsKey("currency")){
            currency = rawData.substring(datablocks.get("currency").start, datablocks.get("currency").end);
        }

        String stringToParse;
        if(datablocks.containsKey("nrOfPaymentPosts")){
            nrOfPaymentPosts = Integer.parseInt(rawData.substring(datablocks.get("nrOfPaymentPosts").start, datablocks.get("nrOfPaymentPosts").end).trim());
        }
        if(datablocks.containsKey("sum")){
            stringToParse = rawData.substring(datablocks.get("sum").start, datablocks.get("sum").end).trim();
            stringToParse = stringToParse.replace(',', '.');
            reportedSum = BigDecimal.valueOf(Double.parseDouble(stringToParse));
        }

    }

    public String toString(){
        String stp = "\n-------------------------------\n" + "Gen_OP variables" + "\n";

        //Print OP

        //Print payments

        //print EP if any.
        stp += "Account number: " + accountnr + "\n";
        stp += "Nr of PPs: " + nrOfPaymentPosts +"\n";
        stp += "Total amount transferred: " + reportedSum + "\n";
        stp += "Date: " + dateOfPayment +"\n";

        StringBuilder stpBuilder = new StringBuilder(stp);
        stp = stpBuilder.toString();
        stp += "\n-------------------------------\n";

        return stp;
    }
}
