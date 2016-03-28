package org.ohie.pocdemo.form.model;

/**
 * Created by snkasthu on 2/1/16.
 */
public class PatientQuery {

    private Patient patient;
    private String query;
    private String response;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
