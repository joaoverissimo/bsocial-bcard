package uk.co.verissimo.bsocial.bcard.cucumber;

import static org.junit.Assert.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import uk.co.verissimo.bsocial.bcard.entities.Person;
import uk.co.verissimo.bsocial.bcard.service.PersonService;
import uk.co.verissimo.bsocial.bcard.service.SpendService;

public class BalanceSteps {
	@Autowired
	private PersonService personService;

	@Autowired
	private SpendService spendService;

	private Double innerResult;

	@When("find the total of {string} owed")
	public void find_the_total_of_owed(String name) {
		Person person = personService.findByName(name);
		innerResult = spendService.findOwedValue(person);
	}

	@When("find how much {string} owe {string}")
	public void find_how_much_owe(String mainPersonStr, String actionPersonStr) {
		Person mainPerson = personService.findByName(mainPersonStr);
		Person actionPerson = personService.findByName(actionPersonStr);

		innerResult = spendService.findHowMuchOweValues(mainPerson, actionPerson);
	}

	@When("try outstanding balances")
	public void try_outstanding_balances() {
		spendService.outstandingBalances();
	}

	@Then("the result is £ {double}")
	public void the_result_is_£(Double result) {
		assertEquals(innerResult, result);
	}

}
