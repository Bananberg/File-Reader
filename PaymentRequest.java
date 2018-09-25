package dk.arbetsprov;

// A simple placeholder class representing anything that implements the PaymentReciever Interface

import java.math.BigDecimal;
import java.util.Date;

import dk.arbetsprov.payments.PaymentReceiver;

public class PaymentRequest implements PaymentReceiver {



    public PaymentRequest(){

    }

    @Override
    public void startPaymentBundle(String accountNumber, Date paymentDate, String currency) {
        System.out.println("Called startPaymentBundle() with following info: \naccountnr: " + accountNumber + "\nPayment Date:" + paymentDate +"\nCurrency: " + currency);
    }

    @Override
    public void payment(BigDecimal amount, String reference) {
        //Well - here's the data! do something with it! Guess the cool thing is that the data is
        //from any filetype - it's decoded.
        System.out.println("Called payment() with following info: \namount: " + amount + "\nReference Nr:" + reference);

    }

    @Override
    public void endPaymentBundle() {
        // Maybe use to check calculations between opening posts and payposts
        // nr of each and sums of payments?
        System.out.println("endPaymentBundle() was called");
    }
}
