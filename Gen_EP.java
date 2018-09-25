package dk.arbetsprov;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

public class Gen_EP extends Post {

    private BigDecimal reportedSum;

    public BigDecimal getReportedSum() {
        return reportedSum;
    }

    public Gen_EP(String data, HashMap<String, DataBlock> instructions) {
        super(data);

        reportedSum = new BigDecimal("0.00");
        reportedSum = reportedSum.setScale(2, RoundingMode.HALF_EVEN);

        setDatablocks(instructions);

        RetrieveRequiredData();
    }


    @Override
    protected void RetrieveRequiredData() {

        String wholenr = rawData.substring(datablocks.get("sum").start, datablocks.get("sum").end);
        Double nr = Double.parseDouble(wholenr);
        nr = nr * 0.01;
        reportedSum = BigDecimal.valueOf(nr);

    }
}
