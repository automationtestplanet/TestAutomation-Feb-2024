$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("file:src/test/resources/Features/RegisterPatient.feature");
formatter.feature({
  "name": "Register Patient Details",
  "description": "  As a User I want to Register the patient details, so that the patient details can be tracked.",
  "keyword": "Feature"
});
formatter.scenario({
  "name": "Register Patient with valid data",
  "description": "",
  "keyword": "Scenario",
  "tags": [
    {
      "name": "@PositiveScenario"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "user is on Register patient page",
  "keyword": "Given "
});
formatter.match({
  "location": "org.openmrs.demo.stepdefs.RegisterPatientStepDefs.user_is_on_Register_patient_page()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "user enters patient name as \"Ramesh Babu, H\" gender as \"Male\" date of birth as \"26, December, 2000\" address as \"Near HDFC bank\\n S R Nagar,Hyderabad,Telanagana,India,500033\" phone number as \"9876543210\"",
  "keyword": "When "
});
formatter.match({
  "location": "org.openmrs.demo.stepdefs.RegisterPatientStepDefs.user_enters_patient_name_as_gender_as_date_of_birth_as_address_as_phone_number_as(java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "clicks confirm button",
  "keyword": "And "
});
formatter.match({
  "location": "org.openmrs.demo.stepdefs.RegisterPatientStepDefs.clicks_confirm_button()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "patient name \"Ramesh Babu, H\" should be displayed",
  "keyword": "And "
});
formatter.match({
  "location": "org.openmrs.demo.stepdefs.RegisterPatientStepDefs.patient_name_should_be_displayed(java.lang.String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "patient id must be generated",
  "keyword": "And "
});
formatter.match({
  "location": "org.openmrs.demo.stepdefs.RegisterPatientStepDefs.patient_id_must_be_generated()"
});
formatter.result({
  "status": "passed"
});
formatter.after({
  "status": "passed"
});
});