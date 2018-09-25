package dk.arbetsprov;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dk.arbetsprov.payments.PaymentReceiver;

// PaymentService reads the file located at the filePath and creates a PaymentBundle based on the post-type in the first line.
// The bundle simply holds the different posts together and handles checking that they're correct.

public class PaymentService {
    public PaymentBundle paymentBundle;
    private List<String> postsInStrings;

    public PaymentService() {
    }

    public void resetService(){
        paymentBundle = null;
        postsInStrings = null;
    }

    public void OpenPaymentService(PaymentReceiver paymentReceiver, String filePath, fileDefinition fileDef){

        postsInStrings = new ArrayList<>();
        try {
            postsInStrings = Files.readAllLines(Paths.get(filePath), StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        paymentBundle = new PaymentBundle(postsInStrings, fileDef);
        paymentBundle.readOpeningPost();
        paymentBundle.readPaymentPosts();
        paymentBundle.readEndPost();

        if (paymentBundle.isOkay()) {
            paymentReceiver.startPaymentBundle(
                    paymentBundle.getOpeningPost().getAccountnr(),
                    paymentBundle.getOpeningPost().getDateOfPayment(),
                    paymentBundle.getOpeningPost().getCurrency());

            for (Gen_PP post : paymentBundle.getPaymentPosts()) {
                paymentReceiver.payment(post.getAmount(), post.getPaymentReference());
            }

            paymentReceiver.endPaymentBundle();
        }

    }
}
