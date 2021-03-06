package org.ohie.pocdemo.form.model;

import java.util.List;

public class Form {

	private String name;

	private String email;

	private String gender;

	private String password;

	private String patient;

	private String provider;

	private String providerName;

	private String facilityName;

	private String hiddenMessage;

	private Exception exception;

	private String createPatientResult;

	private String query;

	private String showUrl;

	private String birthDate;


	private String queryPatientResult;

	private String queryDataResult;

	private String queryFacilityResult;

	private String queryFacilityRequest;


	private String queryProviderResult;

	private String queryProviderRequest;


	private int maxresponses;

	private int maxProviderresponses;

	private PatientQuery patientQuery;

	public String getQueryDataResult() {
		return queryDataResult;
	}

	public void setQueryDataResult(String queryDataResult) {
		this.queryDataResult = queryDataResult;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHiddenMessage() {
		return hiddenMessage;
	}

	public void setHiddenMessage(String hiddenMessage) {
		this.hiddenMessage = hiddenMessage;
	}

	public String getPatient() {
		return patient;
	}

	public void setPatient(String patient) {
		this.patient = patient;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public String getCreatePatientResult() {
		return createPatientResult;
	}

	public void setCreatePatientResult(String createPatientResult) {
		this.createPatientResult = createPatientResult;
	}

	public String getQueryPatientResult() {
		return queryPatientResult;
	}

	public void setQueryPatientResult(String queryPatientResult) {
		this.queryPatientResult = queryPatientResult;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getQueryFacilityResult() {
		return queryFacilityResult;
	}

	public void setQueryFacilityResult(String queryFacilityResult) {
		this.queryFacilityResult = queryFacilityResult;
	}

	public String getQueryProviderResult() {
		return queryProviderResult;
	}

	public void setQueryProviderResult(String queryProviderResult) {
		this.queryProviderResult = queryProviderResult;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public int getMaxresponses() {
		return maxresponses;
	}

	public void setMaxresponses(int maxresponses) {
		this.maxresponses = maxresponses;
	}

	public int getMaxProviderresponses() {
		return maxProviderresponses;
	}

	public void setMaxProviderresponses(int maxProviderresponses) {
		this.maxProviderresponses = maxProviderresponses;
	}


	public String getShowUrl() {
		return showUrl;
	}

	public void setShowUrl(String showUrl) {
		this.showUrl = showUrl;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public PatientQuery getPatientQuery() {
		return patientQuery;
	}

	public void setPatientQuery(PatientQuery patientQuery) {
		this.patientQuery = patientQuery;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getQueryFacilityRequest() {
		return queryFacilityRequest;
	}

	public void setQueryFacilityRequest(String queryFacilityRequest) {
		this.queryFacilityRequest = queryFacilityRequest;
	}

	public String getQueryProviderRequest() {
		return queryProviderRequest;
	}

	public void setQueryProviderRequest(String queryProviderRequest) {
		this.queryProviderRequest = queryProviderRequest;
	}
}
