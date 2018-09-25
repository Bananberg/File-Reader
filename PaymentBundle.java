package dk.arbetsprov;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PaymentBundle {

    private List<String> postsInStrings;

    fileDefinition fileDef;



    private Gen_OP openingPost;
    private List<Gen_PP> paymentPosts;
    private Gen_EP endPost;

    private BigDecimal sumOfPayments;

    private BigDecimal reportedSum;
    private int reportedNrOfPPs;


    public PaymentBundle(List<String> postsInStrings,fileDefinition fileDef ){
        this.postsInStrings = postsInStrings;

        this.fileDef = fileDef;

        sumOfPayments = new BigDecimal("0.0");
        sumOfPayments = sumOfPayments.setScale(2, RoundingMode.HALF_EVEN);
        reportedSum = new BigDecimal("0.0");
        reportedSum = reportedSum.setScale(2, RoundingMode.HALF_EVEN);

    }

    public void readOpeningPost(){
        openingPost = new Gen_OP(postsInStrings.remove(0), fileDef.getPostType(), fileDef.getInstructions("Gen_OP"));

    }
    public void readPaymentPosts(){
      paymentPosts = new ArrayList<>();



        for (String payment : postsInStrings){
            //Using REGEX to check that we are not reading an -line or a -line.
            // TODO: Formultate REGEX somewhere easily modified to add new identifiers for differnet posttypes.
            // TODO: Could have
            if(payment.matches("(B|30).*")){
                Gen_PP post = new Gen_PP(payment, fileDef.getInstructions("Gen_PP"));
                paymentPosts.add(post);

            }
        }


    }
    public void readEndPost(){
        for(int i = 0; i < postsInStrings.size(); i++){
            //Using REGEX to check that we are not reading an -line or a -line.
            // TODO: Formultate REGEX somewhere easily modified to add new identifiers for differnet posttypes.
            // TODO: Could have "OpeningPosts, PaymentPosts, Endposts" regexes
            if(postsInStrings.get(i).matches("(99).*")){
                endPost = new Gen_EP(postsInStrings.get(i), fileDef.getInstructions("Gen_EP"));
            }
        }
    }

    public boolean isOkay() {
        boolean OK = true;

        for (Gen_PP post : paymentPosts) {

            sumOfPayments = sumOfPayments.add(post.getAmount());


            for (Gen_PP other : paymentPosts) {
                //Checks if any reference number is a duplicate in the paymentposts
                if (!post.equals(other) && post.getPaymentReference().equals(other.getPaymentReference())) {
                    System.out.println("Found duplicated reference number in PaymentBundle" + "\npost: "
                            + post.getPaymentReference() + "\nother: " + other.getPaymentReference());
                    OK = false;
                }
            }
        }

        /**
         * since the reported sum ofthe payment can't be in both the OP and the EP we add both.
         * The one without will default to 0.0
         */
        if(openingPost != null){
            reportedSum = openingPost.getReportedSum();
        }
        if(endPost != null){
            reportedSum = endPost.getReportedSum().setScale(2);
        }

        if (!reportedSum.equals(sumOfPayments)) {
            System.out.println("discrepancy in reportedSum and total reportedSum of all payments\n" +
                    "reportedSum: " + reportedSum +
                    "\nsumOfPayments: " + sumOfPayments);
            OK = false;
        }

        if (openingPost.getNrOfPaymentPosts() != -1) {
            reportedNrOfPPs = openingPost.getNrOfPaymentPosts();

            //check that nr of paymentposts is the same as the reported number by the
            if (reportedNrOfPPs != paymentPosts.size()) {
                //some form of logging here
                System.out.println("Wrong number of PaymentPosts compared to OpeningPosts.");
                OK = false;
            }
        } else {
            //Case for any type that doesn't give a expected nr of paymentposts
            reportedNrOfPPs = paymentPosts.size();
        }


        return OK;
    }

    public List<Gen_PP> getPaymentPosts() {
        return paymentPosts;
    }

    public Gen_OP getOpeningPost() {
        return openingPost;
    }

    @Override
    public String toString() {
        String stp = "\n-------------------------------\n" + "PaymentBundle Information:" + "\n";

        stp += "Account number: " + openingPost.getAccountnr() + "\n";
        stp += "Nr of Paymentposts: " + reportedNrOfPPs + "\n";
        stp += "Total amount transferred: " + reportedSum + "\n";
        StringBuilder stpBuilder = new StringBuilder(stp);
        for (Gen_PP p : paymentPosts) {
            stpBuilder.append("PayPost: ").append(p.getPaymentReference()).append("   Amount: ").append(p.getAmount()).append("\n");
        }
        stp = stpBuilder.toString();
        stp += "Expected Total: " + reportedSum + "   Total sum in PPs " + sumOfPayments;
        stp += "\n-------------------------------\n";

        return stp;
    }


}
