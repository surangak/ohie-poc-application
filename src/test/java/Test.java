import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.PipeParser;
import org.ohie.pocdemo.form.model.ModifyXDSbMessage;
import org.ohie.pocdemo.form.model.Patient;
import org.ohie.pocdemo.form.model.Provider;
import org.ohie.pocdemo.form.util.NewClientRegistryUtil;
import org.regenstrief.util.Util;

import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

public class Test {

    public static void main(String args[]) throws XPathExpressionException {

         String LOCATION_INPUT = "/Users/snkasthu/Downloads/pocdemo/src/main/resources";


        /**try{

            Patient p = new Patient();
            p.setIdentifier("5555");
            p.setIdentifierType("TEST");
            p.setFirstName("first name");
            p.setLastName("last name");

            Provider pro = new Provider("1", "first", "last");

       ModifyXDSbMessage m = new ModifyXDSbMessage(p);
            m.modify(LOCATION_INPUT + "/xds/OHIE-XDS-01-30.xml");

        } catch (Exception e) {
            e.printStackTrace();
        }


**/







    }
}
