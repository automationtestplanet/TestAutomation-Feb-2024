Feature: Register Patient Details
  As a User I want to Register the patient details, so that the patient details can be tracked.

  @PositiveScenario
  Scenario: Register Patient with valid data
    Given user is on Register patient page
    When user enters patient name as "Ramesh Babu, H" gender as "Male" date of birth as "26, December, 2000" address as "Near HDFC bank\n S R Nagar,Hyderabad,Telanagana,India,500033" phone number as "9876543210"
    And clicks confirm button
    And patient name "Ramesh Babu, H" should be displayed
    And patient id must be generated

  @NegativeScenario
  Scenario: Register Patient with invalid data
    Given user is on Register patient page
    When user enters patient name as "Ramesh Babu, H" gender as "Male" date of birth as "26, December, 2000" address as "Near HDFC bank\n S R Nagar,Hyderabad,Telanagana,India,500033" phone number as "9876543210"
    And clicks confirm button
    And patient name "Ramesh Babu, H" should be displayed
    And patient id must be generated
    #When user enters patient detials
      #| Ramesh Babu, H | Male | 26, December, 2000 | Near HDFC bank\n S R Nagar,Hyderabad,Telanagana,India,500033 | 9876543210 |
