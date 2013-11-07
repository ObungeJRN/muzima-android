package com.muzima.view.forms;

import com.muzima.api.model.Patient;
import com.muzima.api.model.PatientIdentifier;
import com.muzima.utils.Constants;
import com.muzima.utils.DateUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class JSONPatientMapperTest{

    private PatientJSONMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new PatientJSONMapper(getJSONContent());
    }

    @Test
    public void shouldHaveFamilyNameForPatient() throws Exception {
        Patient patient = mapper.getPatient();
        assertThat(patient.getFamilyName(),is("FamilyName"));
    }

    @Test
    public void shouldHaveMiddleNameForPatient() throws Exception {
        Patient patient = mapper.getPatient();
        assertThat(patient.getMiddleName(),is("MiddleName"));
    }

    @Test
    public void shouldHaveGivenNameForPatient() throws Exception {
        Patient patient = mapper.getPatient();
        assertThat(patient.getGivenName(),is("GivenName"));
    }

    @Test
    public void shouldHaveSexForPatient() throws Exception {
        Patient patient = mapper.getPatient();
        assertThat(patient.getGender(),is("M"));
    }

    @Test
    public void shouldHaveDOBForPatient() throws Exception {
        Patient patient = mapper.getPatient();
        assertThat(patient.getBirthdate(),is(DateUtils.parse("2013-10-28")));
    }

    @Test
    public void shouldHaveRandomUUIDSetAsIdentifier() throws Exception {
        Patient patient = mapper.getPatient();
        List<PatientIdentifier> identifiers = patient.getIdentifiers();
        String uuid = null;
        for (PatientIdentifier identifier : identifiers) {
            if(identifier.getIdentifierType().getName().equals(Constants.LOCAL_PATIENT)){
                uuid = identifier.getIdentifier();
            }
        }
        assertThat(uuid,is(notNullValue()));
    }

    @Test
    public void shouldHaveMRSSetAsDefaultIdentifier() throws Exception {
        Patient patient = mapper.getPatient();
        assertThat(patient.getIdentifier(),is("78899-Z"));

    }

    public String getJSONContent() {
        InputStream fileStream = getClass().getClassLoader().getResourceAsStream("patient/form_with_data.json");
        Scanner s = new Scanner(fileStream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "{}";
    }
}
