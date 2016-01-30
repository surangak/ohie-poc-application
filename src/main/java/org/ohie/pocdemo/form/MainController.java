package org.ohie.pocdemo.form;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v25.message.RSP_K21;



import ca.uhn.hl7v2.parser.*;
import ca.uhn.hl7v2.model.Message;
import org.dcm4chee.xds2.infoset.ihe.RetrieveDocumentSetRequestType;
import org.dcm4chee.xds2.infoset.ihe.RetrieveDocumentSetResponseType;
import org.dcm4chee.xds2.infoset.rim.AdhocQueryRequest;
import org.dcm4chee.xds2.infoset.rim.AdhocQueryResponse;
import org.ohie.pocdemo.form.model.*;
import org.ohie.pocdemo.form.model.ModifyXDSbMessage;
import org.dcm4chee.xds2.infoset.ihe.ProvideAndRegisterDocumentSetRequestType;
import org.dcm4chee.xds2.infoset.rim.RegistryResponseType;


import org.ohie.pocdemo.form.util.MainUtil;
import org.ohie.pocdemo.form.util.NewClientRegistryUtil;

import org.ohie.pocdemo.form.util.TestInfoMan;
import org.ohie.pocdemo.form.util.XdsMessageUtil;
import org.regenstrief.util.Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import org.ohie.pocdemo.form.model.AphpDocument;
import javax.xml.bind.JAXBException;
import javax.xml.xpath.XPathExpressionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@Controller
@RequestMapping("/form.htm")
public class MainController {

   /** @Autowired
    @Qualifier("formValidator")
    private Validator validator;

    @Qualifier("patientValidator")**/

    List<Patient> patients = new ArrayList<Patient>();
    List<Provider> providers = new ArrayList<Provider>();

    private final static String LOCATION_INPUT = "/Users/snkasthu/SourceCode/ohiedemo/pocdemo/src/main/resources";


    @RequestMapping(method = RequestMethod.GET)
	public String initForm(Model model) {
        AphpDocument form = new AphpDocument();

		model.addAttribute("form", form);
		initModelList(model);
		return "form";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submitForm(@ModelAttribute("form") AphpDocument form, Patient patient, @RequestParam String action) throws XPathExpressionException, HL7Exception {
        String responseString = null;
        try {
            if (action.equals("submit")) {

                String documentId = String.format("1.3.6.1.4.1.21367.2010.1.2.%s", new SimpleDateFormat("HHmmss").format(new Date()));
                patient = getPatient(patient);
                ModifyXDSbMessage modify = new ModifyXDSbMessage(patient);
                modify.modify(LOCATION_INPUT + "/xds/OHIE-XDS-01-30.xml");

                try {
                    ProvideAndRegisterDocumentSetRequestType pnrRequest = XdsMessageUtil.loadMessage("OHIE-XDS-01-30", ProvideAndRegisterDocumentSetRequestType.class);
                    RegistryResponseType xdsResponse = XdsMessageUtil.provideAndRegister(pnrRequest);

                } catch (JAXBException e) {
                    e.printStackTrace();
                    form.setException(e);
                } catch (IOException e) {
                    e.printStackTrace();
                    form.setException(e);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                return null;
            }

            if (action.equals("query")) {
                String returnVal = "successForm";
                AdhocQueryRequest queryRequest = null;

                patient = getPatient(patient);
                ModifyXDSbMessage modify = new ModifyXDSbMessage(patient);
                //modify.modify(LOCATION_INPUT + "/xds/OHIE-XDS-01-20.xml");

                try {
                    queryRequest = XdsMessageUtil.loadMessage("OHIE-XDS-01-20", AdhocQueryRequest.class);

                } catch (JAXBException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                AdhocQueryResponse queryResponse = XdsMessageUtil.registryStoredQuery(queryRequest);
                XdsMessageUtil.assertSuccess(queryResponse);
                System.out.println(" oo " + queryResponse.getTotalResultCount());
                if(queryResponse.getTotalResultCount() == null){
                    form.setQueryDataResult("No Results Found");

                } else{

                }

                System.out.println(queryResponse);
                //XdsMessageUtil.assertHasDocumentId(queryResponse, "2.16.840.1.113883.3.72.5.9.1.0.5386503746339693382111111111");

                // STEP 50 - Retrieve
                RetrieveDocumentSetRequestType retrieveRequest = XdsMessageUtil.createRetrieveRequest("2.16.840.1.113883.3.72.5.9.1.0.5386503746339693382111111111", queryResponse);
                RetrieveDocumentSetResponseType retrieveResponse = XdsMessageUtil.retrieveDocumentSet(retrieveRequest);

                //form.setResult(retrieveResponse.getDocumentResponse().toString());

                return null;
            }


            if (action.equals("patient")) {
                String returnVal = "successForm";
                System.out.println("re : " );


                final String loc = LOCATION_INPUT + "/" + "INT01A-1full.hl7", in = Util.readFile(loc);

                PipeParser pipeParser = new PipeParser();

                try {
                    System.out.println("re : " + in);
                    Message message = pipeParser.parse(in);
                    //ADT_A01 ack = null;

                    //if (message instanceof ADT_A01) {

                       // ack = (ADT_A01) message;
                        /** ack.getPID().getPatientName(0).getGivenName().setValue(patient.getFirstName());
                        ack.getPID().getPatientName(0).getFamilyName().getSurname().setValue(patient.getLastName());

                        ack.getPID().getPatientIdentifierList(0).getIdentifierTypeCode().setValue(patient.getIdentifierType());
                        ack.getPID().getPatientIdentifierList(0).getIDNumber().setValue(patient.getIdentifier()); **/

                    //}


                    NewClientRegistryUtil n1 = new NewClientRegistryUtil();
                    responseString = n1.send(in);
                    System.out.print("*********************");
                    System.out.print(responseString);
                    System.out.print("*********************");
                    form.setCreatePatientResult(responseString);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (action.equals("search")) {
                System.out.println(" oopppppp ");
                final String loc = LOCATION_INPUT + "/" + "INT01A-2query.hl7", in = Util.readFile(loc);
                System.out.println(" oopppppp 2 ");

                String result = MainUtil.queryPatient(in, patient);

                System.out.println(" resultx _" + result);


                        NewClientRegistryUtil n1 = new NewClientRegistryUtil();
                        responseString = n1.send(result);
                        System.out.print("*********************");
                        System.out.print(responseString);
                        System.out.print("*********************");
                        form.setQueryPatientResult(responseString);


            }

            if (action.equals("searchFacility")){
                TestInfoMan infoMan = new TestInfoMan();
                String result = infoMan.testInfoMan(form.getFacilityName(), form.getMaxresponses());
                form.setQueryFacilityResult(result);
                /**String facilityQuery = infoMan.queryFacilityByName(form.getFacilityName());
                System.out.println("controller :" + facilityQuery);
                form.setQueryFacilityResult(facilityQuery); **/
            }

            if (action.equals("searchProvider")){
                TestInfoMan infoMan = new TestInfoMan();
                String result = infoMan.testProvider(form.getProviderName(), form.getMaxresponses());
                form.setQueryProviderResult(result);
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

	}

    public Patient getPatient(Patient patient){
        try {
        System.out.println(" Here 5");
        final String loc = LOCATION_INPUT + "/" + "INT01A-2query.hl7", in = Util.readFile(loc);
        PipeParser pipeParser = new PipeParser();

            String firstDelim = "QPD|Q22^Find Candidates^HL7|Q0740|";
            int p1 = in.indexOf(firstDelim);
            String lastDelim = "RCP";
            int p2 = in.indexOf(lastDelim, p1);   // look after start delimiter
            String replacement = "@PID.3.1^" + patient.getIdentifier() + "~@PID.3.4.1^" + patient.getIdentifierType() + "\n";
            String res = null;
            if (p1 >= 0 && p2 > p1) {
                res = in.substring(0, p1+firstDelim.length())
                        + replacement
                        + in.substring(p2);
                System.out.println(res);
            }

            NewClientRegistryUtil n1 = new NewClientRegistryUtil();
            String responseString = n1.send(res);


                Message message = pipeParser.parse(responseString);

            System.out.println(" try " + message.getName());
            RSP_K21 ack = null;

                if (message instanceof RSP_K21) {

                    ack = (RSP_K21) message;
                    patient.setFirstName(ack.getQUERY_RESPONSE(0).getPID().getPatientName(0).getGivenName().getValue());
                    patient.setLastName(ack.getQUERY_RESPONSE(0).getPID().getPatientName(0).getFamilyName().getSurname().getValue());

                    //patient.setIdentifier(ack.getQUERY_RESPONSE().getPID().getPatientIdentifierList(0).getIDNumber().toString());
                    //patient.setIdentifierType(ack.getQUERY_RESPONSE().getPID().getPatientIdentifierList(0).getIDNumber().get);
                    //patient.setIdentifierType(ack.getQUERY_RESPONSE().getPID().getPatientIdentifierList(0).getAssigningAuthority().getComponent(0).toString());


                }else{
                    System.out.println(" eeh " + message.getName());

                }

            }catch (Exception e){
            e.printStackTrace();
        }

        return patient;
    }

	private void initModelList(Model model) {


	}
}
