package org.ohie.pocdemo.form;

import org.regenstrief.util.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.ohie.pocdemo.form.model.PatientQuery;

/**
 * Created by snkasthu on 2/1/16.
 */
public class PocReferenceService {

    private final static String LOCATION_INPUT = "/Users/snkasthu/SourceCode/ohiedemo/pocdemo/src/main/resources";

    public PatientQuery searchPatient(PatientQuery patientQuery){

        try {
            String loc = LOCATION_INPUT + "/" + "INT01A-2query.hl7", in = Util.readFile(loc);

            System.out.println("re : " + in);

            DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Calendar cal = Calendar.getInstance();
            System.out.println(dateFormat.format(cal.getTime()));

            in = in.replace("RJ-442", patientQuery.getPatient().getIdentifier());
            in = in.replace("TEST", patientQuery.getPatient().getIdentifierType());

            patientQuery.setQuery(in);


        }catch(Exception e){
            e.printStackTrace();
        }


        return patientQuery;
    }
}
