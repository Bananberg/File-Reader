package dk.arbetsprov;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.HashMap;

public class main {

    private static fileDefinition fileDef_betalningsservice;
    private static fileDefinition fileDef_inbetalningstjansten;

    private static HashMap<String, DataBlock> datablocks_betserv_GenOP;
    private static HashMap<String, DataBlock> datablocks_betserv_GenPP;

    private static HashMap<String, DataBlock> datablocks_inbet_GenOP;
    private static HashMap<String, DataBlock> datablocks_inbet_GenPP;
    private static HashMap<String, DataBlock> datablocks_inbet_GenEP;

    public static void main(String[] arg) {
        //Old Code
        PaymentService paymentService = new PaymentService();

        DefineDataBlocks();

        PaymentRequest pr_betal = new PaymentRequest();
        paymentService.OpenPaymentService(pr_betal, fileDef_betalningsservice.getFilePath(), fileDef_betalningsservice);
        System.out.println(paymentService.paymentBundle.toString());
        paymentService.resetService();

        PaymentRequest pr_inbet = new PaymentRequest();
        paymentService.OpenPaymentService(pr_inbet, fileDef_inbetalningstjansten.getFilePath(), fileDef_inbetalningstjansten);
        System.out.println(paymentService.paymentBundle.toString());
        paymentService.resetService();

    }

    /**
     * this function is simply a placeholder-thing, in a real program this would be done whereever
     * you would find it fitting to define this.
     */
    public static void DefineDataBlocks() {
        datablocks_betserv_GenOP = new HashMap<String, DataBlock>();
        datablocks_betserv_GenPP = new HashMap<String, DataBlock>();

        datablocks_betserv_GenOP.put("accountnr", new DataBlock(1, 16));
        datablocks_betserv_GenOP.put("sum", new DataBlock(16, 30));
        datablocks_betserv_GenOP.put("nrOfPaymentPosts", new DataBlock(30, 40));
        datablocks_betserv_GenOP.put("currency", new DataBlock(48, 51));
        datablocks_betserv_GenOP.put(("date"), new DataBlock(40, 48));

        datablocks_betserv_GenPP.put("amount", new DataBlock(1, 15));
        datablocks_betserv_GenPP.put("paymentRef", new DataBlock(15, 50));

        //These instructions could be read in the beginning of a new file.
        fileDef_betalningsservice = new fileDefinition("betalningsservice", 1);
        fileDef_betalningsservice.setFilePath("Exempelfil_betalningsservice.txt");
        fileDef_betalningsservice.setInstructions_GenOP(datablocks_betserv_GenOP);
        fileDef_betalningsservice.setInstructions_GenPP(datablocks_betserv_GenPP);


        datablocks_inbet_GenOP = new HashMap<String, DataBlock>();
        datablocks_inbet_GenPP = new HashMap<String, DataBlock>();
        datablocks_inbet_GenEP = new HashMap<String, DataBlock>();

        datablocks_inbet_GenOP.put("clearingnr", new DataBlock(10, 14));
        datablocks_inbet_GenOP.put("accountnr", new DataBlock(14, 24));

        datablocks_inbet_GenPP.put("amount", new DataBlock(2, 22));
        datablocks_inbet_GenPP.put("paymentRef", new DataBlock(40, 65));

        datablocks_inbet_GenEP.put("sum", new DataBlock(2, 22));

        fileDef_inbetalningstjansten = new fileDefinition("inbetalningstjansten", 2);
        fileDef_inbetalningstjansten.setFilePath("Exempelfil_inbetalningstjansten.txt");
        fileDef_inbetalningstjansten.setInstructions_GenOP(datablocks_inbet_GenOP);
        fileDef_inbetalningstjansten.setInstructions_GenPP(datablocks_inbet_GenPP);
        fileDef_inbetalningstjansten.setInstructions_GenEP(datablocks_inbet_GenEP);

    }

}
/**
 * HashMap<String, DataBlock> dataBlocks =  new HashMap<String, DataBlock> datablocks;
 * dataBlocks.put("variablename", new DataBlock(0, 2));
 * --||--
 * --||--
 * --||--
 * --||--
 * <p>
 * New PaymentBundle(1, PP_B, null, dataBlocks)  --1 char for OP-type, type for paymentpost, type for endpost,
 */

