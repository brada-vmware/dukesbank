/*
 * Copyright (c) 2006 Sun Microsystems, Inc.  All rights reserved.  U.S.
 * Government Rights - Commercial software.  Government users are subject
 * to the Sun Microsystems, Inc. standard license agreement and
 * applicable provisions of the FAR and its supplements.  Use is subject
 * to license terms.
 *
 * This distribution may include materials developed by third parties.
 * Sun, Sun Microsystems, the Sun logo, Java and J2EE are trademarks
 * or registered trademarks of Sun Microsystems, Inc. in the U.S. and
 * other countries.
 *
 * Copyright (c) 2006 Sun Microsystems, Inc. Tous droits reserves.
 *
 * Droits du gouvernement americain, utilisateurs gouvernementaux - logiciel
 * commercial. Les utilisateurs gouvernementaux sont soumis au contrat de
 * licence standard de Sun Microsystems, Inc., ainsi qu'aux dispositions
 * en vigueur de la FAR (Federal Acquisition Regulations) et des
 * supplements a celles-ci.  Distribue par des licences qui en
 * restreignent l'utilisation.
 *
 * Cette distribution peut comprendre des composants developpes par des
 * tierces parties. Sun, Sun Microsystems, le logo Sun, Java et J2EE
 * sont des marques de fabrique ou des marques deposees de Sun
 * Microsystems, Inc. aux Etats-Unis et dans d'autres pays.
 */


package com.sun.cb;

import java.net.*;
import java.io.*;
import java.util.*;
import javax.xml.soap.*;


public class TestOrderRequest {
    public static void main(String[] args) {
        try {
            SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
            SOAPConnection con = scf.createConnection();
            SOAPFactory soapFactory = SOAPFactory.newInstance();

            MessageFactory mf = MessageFactory.newInstance();

            SOAPMessage msg = mf.createMessage();

            // Access the SOAPBody object
            SOAPBody body = msg.getSOAPBody();

            // Create the appropriate elements and add them
            Name bodyName =
                soapFactory.createName("coffee-order", "PO",
                    "http://sonata.coffeebreak.com");
            SOAPBodyElement order = body.addBodyElement(bodyName);

            // orderID
            Name orderIDName = soapFactory.createName("orderID");
            SOAPElement orderID = order.addChildElement(orderIDName);
            orderID.addTextNode("1234");

            // customer
            Name childName = soapFactory.createName("customer");
            SOAPElement customer = order.addChildElement(childName);

            childName = soapFactory.createName("last-name");

            SOAPElement lastName = customer.addChildElement(childName);
            lastName.addTextNode("Pental");

            childName = soapFactory.createName("first-name");

            SOAPElement firstName = customer.addChildElement(childName);
            firstName.addTextNode("Ragni");

            childName = soapFactory.createName("phone-number");

            SOAPElement phoneNumber = customer.addChildElement(childName);
            phoneNumber.addTextNode("908 983-6789");

            childName = soapFactory.createName("email-address");

            SOAPElement emailAddress = customer.addChildElement(childName);
            emailAddress.addTextNode("ragnip@aol.com");

            // address
            childName = soapFactory.createName("address");

            SOAPElement address = order.addChildElement(childName);

            childName = soapFactory.createName("street");

            SOAPElement street = address.addChildElement(childName);
            street.addTextNode("9876 Central Way");

            childName = soapFactory.createName("city");

            SOAPElement city = address.addChildElement(childName);
            city.addTextNode("Rainbow");

            childName = soapFactory.createName("state");

            SOAPElement state = address.addChildElement(childName);
            state.addTextNode("CA");

            childName = soapFactory.createName("zip");

            SOAPElement zip = address.addChildElement(childName);
            zip.addTextNode("99999");

            // line-item 1
            childName = soapFactory.createName("line-item");

            SOAPElement lineItem = order.addChildElement(childName);

            childName = soapFactory.createName("coffeeName");

            SOAPElement coffeeName = lineItem.addChildElement(childName);
            coffeeName.addTextNode("arabica");

            childName = soapFactory.createName("pounds");

            SOAPElement pounds = lineItem.addChildElement(childName);
            pounds.addTextNode("2");

            childName = soapFactory.createName("price");

            SOAPElement price = lineItem.addChildElement(childName);
            price.addTextNode("10.95");

            // line-item 2
            childName = soapFactory.createName("coffee-name");

            SOAPElement coffeeName2 = lineItem.addChildElement(childName);
            coffeeName2.addTextNode("espresso");

            childName = soapFactory.createName("pounds");

            SOAPElement pounds2 = lineItem.addChildElement(childName);
            pounds2.addTextNode("3");

            childName = soapFactory.createName("price");

            SOAPElement price2 = lineItem.addChildElement(childName);
            price2.addTextNode("10.95");

            // total
            childName = soapFactory.createName("total");

            SOAPElement total = order.addChildElement(childName);
            total.addTextNode("21.90");

            URL endpoint = new URL(URLHelper.getSaajURL() + "/orderCoffee");
            SOAPMessage reply = con.call(msg, endpoint);
            con.close();

            // extract content of reply
            // Extracting order ID and ship date
            SOAPBody sBody = reply.getSOAPBody();
            Iterator bodyIt = sBody.getChildElements();
            SOAPBodyElement sbEl = (SOAPBodyElement) bodyIt.next();
            Iterator bodyIt2 = sbEl.getChildElements();

            // get orderID
            SOAPElement ID = (SOAPElement) bodyIt2.next();
            String id = ID.getValue();

            // get ship date
            SOAPElement sDate = (SOAPElement) bodyIt2.next();
            String shippingDate = sDate.getValue();

            System.out.println("");
            System.out.println("");
            System.out.println("Confirmation for order #" + id);
            System.out.print("Your order will be shipped on ");
            System.out.println(shippingDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
