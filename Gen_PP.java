package dk.arbetsprov;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

public class Gen_PP extends Post {

    private String paymentReference;
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public Gen_PP(String data, HashMap<String, DataBlock> instructions) {
        super(data);

        amount = new BigDecimal("0.00");
        amount = amount.setScale(2, RoundingMode.HALF_EVEN);

        setDatablocks(instructions);
        RetrieveRequiredData();
    }

    @Override
    protected void RetrieveRequiredData() {
        boolean notDone = true;
        String amountString = rawData.substring(datablocks.get("amount").start, datablocks.get("amount").end);

        if (amountString.contains(" ") && notDone) {
            amountString = amountString.trim();
            if (amountString.contains(",")) {
                amountString = amountString.replace(',', '.');
            }
            notDone = false;
        }

        if (amountString.matches("^[0-9]+$") && notDone) {
            Double nr = Double.parseDouble(amountString);
            // TODO: The multiplication below is to make the number conform to 2 decimals, this is specified by the assignment.
            // TODO: However, this could perhaps be specified somewhere else as part of the instructions.
            nr = nr * 0.01;
            amountString = Double.toString(nr);

            notDone = false;
        }

        this.amount = BigDecimal.valueOf(Double.parseDouble(amountString));


        if (datablocks.containsKey("paymentRef")) {
            paymentReference = rawData.substring(datablocks.get("paymentRef").start, datablocks.get("paymentRef").end);
        } else {
            System.out.println("PP did not contain payment reference. This is not allowed.");
        }

    }

    public String toString() {
        String stp = "\n-------------------------------\n" + "Gen_PP variables" + "\n";

        stp += "Account number: " + paymentReference + "\n";
        stp += "amount: " + amount + "\n";

        StringBuilder stpBuilder = new StringBuilder(stp);
        stp = stpBuilder.toString();
        stp += "\n-------------------------------\n";

        return stp;
    }
}
