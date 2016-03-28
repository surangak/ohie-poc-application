package org.ohie.pocdemo.form;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v25.message.RSP_K21;

import java.text.DateFormat;
import java.util.Calendar;


import ca.uhn.hl7v2.parser.*;
import ca.uhn.hl7v2.model.Message;
import org.dcm4chee.xds2.infoset.rim.AdhocQueryRequest;
import org.dcm4chee.xds2.infoset.rim.AdhocQueryResponse;
import org.ohie.pocdemo.form.model.*;
import org.ohie.pocdemo.form.model.ModifyXDSbMessage;
import org.dcm4chee.xds2.infoset.ihe.ProvideAndRegisterDocumentSetRequestType;
import org.dcm4chee.xds2.infoset.rim.RegistryResponseType;

import org.ohie.pocdemo.form.util.ClientRegistryUtil;

import org.ohie.pocdemo.form.util.TestInfoMan;
import org.ohie.pocdemo.form.util.XdsMessageUtil;
import org.regenstrief.util.Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.ohie.pocdemo.form.model.Form;

import javax.xml.bind.JAXBException;
import javax.xml.xpath.XPathExpressionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@Controller
@RequestMapping("/form.htm")
public class MainController {

    /**
     * @Autowired
     * @Qualifier("formValidator") private Validator validator;
     * @Qualifier("patientValidator")
     **/

    List<Patient> patients = new ArrayList<Patient>();
    List<Provider> providers = new ArrayList<Provider>();

    private final static String LOCATION_INPUT = "/Users/snkasthu/SourceCode/ohiedemo/pocdemo/src/main/resources";


    @RequestMapping(method = RequestMethod.GET)
    public String initForm(Model model) {
        Form form = new Form();

        model.addAttribute("form", form);
        initModelList(model);
        return "form";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submitForm(@ModelAttribute("form") Form form, Patient patient, @RequestParam String action) throws XPathExpressionException, HL7Exception {
        String responseString = null;
        try {
            if (action.equals("submit")) {

                String documentId = String.format("1.3.6.1.4.1.21367.2010.1.2.%s", new SimpleDateFormat("HHmmss").format(new Date()));
                patient = getPatient(patient);
                ModifyXDSbMessage modify = new ModifyXDSbMessage(patient);
                modify.modify(LOCATION_INPUT + "/xds/OHIE-XDS-01-30.xml");
                String path = LOCATION_INPUT + "/xds/OHIE-XDS-01-30.xml";

                try {
                    ProvideAndRegisterDocumentSetRequestType pnrRequest = XdsMessageUtil.loadMessage(path, ProvideAndRegisterDocumentSetRequestType.class);
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
                //XdsMessageUtil.assertSuccess(queryResponse);
                System.out.println(" oo " + queryResponse.getTotalResultCount());
                if (queryResponse.getTotalResultCount() == null) {
                    form.setQueryDataResult("No Results Found");

                } else {

                }

                System.out.println("ok result:" + queryResponse.toString());
                System.out.println("ok result:" + queryResponse);

                form.setQueryDataResult(queryResponse.toString());

                System.out.println(queryResponse);
                //XdsMessageUtil.assertHasDocumentId(queryResponse, "2.16.840.1.113883.3.72.5.9.1.0.5386503746339693382111111111");

                // STEP 50 - Retrieve
                /** RetrieveDocumentSetRequestType retrieveRequest = XdsMessageUtil.createRetrieveRequest("2.16.840.1.113883.3.72.5.9.1.0.5386503746339693382111111111", queryResponse);
                 RetrieveDocumentSetResponseType retrieveResponse = XdsMessageUtil.retrieveDocumentSet(retrieveRequest);
                 **/
                //form.setResult(retrieveResponse.getDocumentResponse().toString());

                return null;
            }


            if (action.equals("patient")) {
                String returnVal = "successForm";
                System.out.println("re : ");

                try {

                    System.out.println(" birthdate: " + patient.getDob());


                    String loc = LOCATION_INPUT + "/" + "INT01A-1full.hl7", in = Util.readFile(loc);

                    PipeParser pipeParser = new PipeParser();


                    System.out.println("re : " + in);

                    DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                    Calendar cal = Calendar.getInstance();
                    System.out.println(dateFormat.format(cal.getTime()));


                    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = sdf.parse(patient.getDob());
                    String d = dateFormat.format(date);


                    in = in.replace("RJ-417", patient.getIdentifier());
                    in = in.replace("TEST", patient.getIdentifierType());
                    in = in.replace("20141009103551", dateFormat.format(cal.getTime()));
                    in = in.replace("Murke", patient.getFirstName());
                    in = in.replace("FULL", patient.getMname());
                    in = in.replace("FOSTER", patient.getLastName());
                    in = in.replace("19700101", d);


                    System.out.print(in);


                    ClientRegistryUtil n1 = new ClientRegistryUtil();
                    //responseString = n1.send(in);
                    System.out.print("*********************");
                    System.out.print(responseString);
                    System.out.print("*********************");
                    form.setCreatePatientResult(responseString);
                    form.setShowUrl("http://crwin.test.ohie.org:8080/fhir/Patient?identifier=" + patient.getIdentifier());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (action.equals("search")) {
                System.out.println(" oopppppp ");
                //final String loc = LOCATION_INPUT + "/" + "INT01A-2query.hl7"; //, in = Util.readFile(loc);

                try {


                    PocReferenceService service = new PocReferenceService();
                    PatientQuery patientQuery = new PatientQuery();
                    patientQuery.setPatient(patient);

                    patientQuery = service.searchPatient(patientQuery);


                    ClientRegistryUtil n1 = new ClientRegistryUtil();
                    patientQuery = n1.send(patientQuery);

                    //patientQuery.setResponse(patientQuery.getResponse().replaceAll("\\s",""));
                    form.setQueryPatientResult(patientQuery.getResponse());
                    form.setPatientQuery(patientQuery);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            if (action.equals("searchFacility")) {
                TestInfoMan infoMan = new TestInfoMan();
                ReqResponsePair reqResponsePair = infoMan.testInfoMan(form.getFacilityName(), form.getMaxresponses());

                System.out.println(" response++ : " + reqResponsePair.getRequest());

                form.setQueryFacilityResult(reqResponsePair.getResponse());
                form.setQueryFacilityRequest(reqResponsePair.getRequest());

            }

            if (action.equals("searchProvider")) {
                TestInfoMan infoMan = new TestInfoMan();
                ReqResponsePair reqResponsePair = infoMan.testProvider(form.getProviderName(), form.getMaxresponses());

                form.setQueryProviderResult(reqResponsePair.getResponse());
                form.setQueryProviderRequest(reqResponsePair.getRequest());

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public Patient getPatient(Patient patient) {
        try {

            String loc = LOCATION_INPUT + "/" + "INT01A-2query.hl7", in = Util.readFile(loc);


            in = in.replace("RJ-442", patient.getIdentifier());
            in = in.replace("TEST", patient.getIdentifierType());


            // String r = MainUtil.queryPatient(b.toString(), patient);


            ClientRegistryUtil n1 = new ClientRegistryUtil();
            String responseString = null;

            //n1.send(in);

            PipeParser pipeParser = new PipeParser();

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


            } else {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return patient;
    }

    private void initModelList(Model model) {


    }
}
