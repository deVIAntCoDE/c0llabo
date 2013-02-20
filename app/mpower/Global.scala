package mpower

import play.api._
import com.mpowerpayments.mpower.{MPowerOnsiteInvoice, MPowerCheckoutStore, MPowerSetup}

/**
 * Created with IntelliJ IDEA.
 * User: henry
 * Date: 19/02/13
 * Time: 23:58
 * To change this template use File | Settings | File Templates.
 */
object Global {
  val masterKey ="9b2a01ab-8d35-4fa4-bbf7-23aecb22281d"
  val publicKey = "test_public_8xhZebdcqmAgzQ5EOVvIWTXzpf8"
  val privateKey = "test_private_Ld74FulmZpU7Pd4P03kNX-eorXk"
  val token = "adbaa2be25b919426849"

}

import java.io._

 object OnsiteCheckoutExample extends App{

    // We will accept the Confirmation Token from the Command prompt
    val br = new BufferedReader(new InputStreamReader(System.in));
    var confirmToken = "";

    // Setup your API keys and mode
    val apiSetup = new MPowerSetup();
    apiSetup.setMasterKey(Global.masterKey);
    apiSetup.setPrivateKey(Global.privateKey);
    apiSetup.setPublicKey(Global.publicKey);
    apiSetup.setToken(Global.token);
    apiSetup.setMode("test");

    // Setup your store information
    val storeSetup = new MPowerCheckoutStore();
    storeSetup.setName("My Awesome Online storeSetup");
    storeSetup.setTagline("This is an awesome Java storeSetup.");
    storeSetup.setPhoneNumber("024000001");
    storeSetup.setPostalAddress("606 Memorylane Chokor no.1 Road.");
    storeSetup.setWebsiteUrl("http://my-awesome-long-website-url.com/");

    // Start creating an MPower Checkout
    val co = new MPowerOnsiteInvoice(apiSetup, storeSetup);
    // Add invoice items
    co.addItem("Crate of Apeteshi",2,10.00,20.00);
    co.addItem("50kg Bag of Sultana Rice",1,78.50,78.50);
    co.addItem("Book - Marriage of Anansewaa",1,10.00,10.00);

    // You will need to calculated the total amout yourself.
    co.setTotalAmount(108.50);

    // Create the invoice
    if (co.create("0244124660")) {
      System.out.println("OPR Token: "+co.getToken());
      System.out.println("Response Message: "+co.getResponseText());

      System.out.println("\nEnter Confirmation Code: ");
      try {
        confirmToken = br.readLine();
      } catch{
        case ioe:IOException =>
        System.out.println("Could not read confirmation token");
        System.exit(1);
      }

      // Issue a Charge using your OPR Token + the confirmation token
      if (co.charge(co.token, confirmToken)) {
        System.out.println("Status: "+co.getStatus());
        System.out.println("Receipt URL: "+co.getReceiptUrl());
        System.out.println("Customer Name: "+co.getCustomerInfo("name"));
        System.out.println("Response Message: "+co.getResponseText());
      }else{
        System.out.println("Status: "+co.getStatus());
        System.out.println("Response Message: "+co.getResponseText());
      }

    }else{
      System.out.println("Error Occured: "+ co.getResponseCode());
      System.out.println(co.getResponseText());
    }
  }
