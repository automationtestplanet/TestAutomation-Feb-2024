Feature: Register Patient Details
  As a User I want to Register the patient details, so that the patient details can be tracked.
  
  Background:
		Given User verifies Home Page

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
  @MultiplePatients
  Scenario Outline: Register multiple Patients with valid data
    Given user is on Register patient page
    When user enters patient name as "<Name>" gender as "<Gender>" date of birth as "<DateOfBirth>" address as "<Address>" phone number as "<PhoneNumber>"
    And clicks confirm button
    And patient name "<Name>" should be displayed
    And patient id must be generated

    Examples: 
      | Name             | Gender | DateOfBirth        | Address                                                        | PhoneNumber |
      | Ramesh Babu, H   | Male   | 26, December, 2000 | Near HDFC bank\n S R Nagar,Hyderabad,Telanagana,India,500033   |  9876543210 |
      | Kishore Kumar, M | Male   | 10, November, 1998 | Near Icici bank\n HitechCity,Hyderabad,Telanagana,India,500033 |  9876543211 |
      | Kumar Raju, Ch   | Male   | 17, July, 2002     | Near SBI bank\n Kukatpally,Hyderabad,Telanagana,India,500033   |  9876543212 |
