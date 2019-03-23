package uk.co.verissimo.bsocial.bcard.cucumber;

import static org.junit.Assert.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import uk.co.verissimo.bsocial.bcard.config.TestConfig;
import uk.co.verissimo.bsocial.bcard.entities.Person;
import uk.co.verissimo.bsocial.bcard.service.PersonService;

@SpringBootTest(classes = TestConfig.class)
public class PersonSteps {

	@Autowired
	private PersonService personService;

	private String name;
	private Person person;

	@Given("the person name {string}")
	public void the_person_name(String name) {
		this.name = name;
	}

	@When("Insert person")
	public void insert_person() {
		person = personService.insert(name);
	}

	@Then("the person saved")
	public void the_person_saved() {
		assertEquals(person.getName(), name);
	}

}
