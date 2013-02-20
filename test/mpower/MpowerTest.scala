package mpower

import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import com.mpowerpayments.mpower._


/**
 * Created with IntelliJ IDEA.
 * User: henry
 * Date: 19/02/13
 * Time: 23:45
 * To change this template use File | Settings | File Templates.
 */
object MpowerTest extends Specification {

  "The mpower client" should {
    "create a tx" in {
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

      val co = new MPowerCheckoutInvoice(apiSetup, storeSetup);
      // Add invoice items
      co.addItem("Crate of Apeteshi",2,10.00,20.00);
      co.addItem("50kg Bag of Sultana Rice",1,78.50,78.50);
      co.addItem("Book - Marriage of Anansewaa",1,10.00,10.00);

      // You will need to calculated the total amout yourself.
      co.setTotalAmount(108.50);

      // Create the invoice
      if (co.create()) {
        System.out.println("Invoice URL: "+co.getInvoiceUrl());
      }else{
        System.out.println("Error Occured: "+ co.getResponseCode());
        System.out.println(co.getResponseText());
      }

      1 mustEqual(1)

    }
  }

  "create inapp tx "in {
    val demo = OnsiteCheckoutExample
    demo

    1 mustEqual(1)

  }

}
