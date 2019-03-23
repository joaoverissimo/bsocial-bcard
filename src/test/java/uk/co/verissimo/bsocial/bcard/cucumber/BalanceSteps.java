package uk.co.verissimo.bsocial.bcard.cucumber;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import uk.co.verissimo.bsocial.bcard.entities.BalanceRecord;
import uk.co.verissimo.bsocial.bcard.entities.Person;
import uk.co.verissimo.bsocial.bcard.service.PersonService;
import uk.co.verissimo.bsocial.bcard.service.SpendService;

public class BalanceSteps {
	@Autowired
	private PersonService personService;

	@Autowired
	private SpendService spendService;

	// variables for steps
	private Double innerResult;
	List<BalanceRecord> returnArray;

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
		returnArray = spendService.outstandingBalances();
	}

	@Then("the result is £ {double}")
	public void the_result_is_£(Double result) {
		assertEquals(innerResult, result);
	}

	@Then("the result of payments:")
	public void the_result_of_payments(List<List<String>> dataTable) {
		System.out.println(dataTable);
		dataTable.remove(0);
		
		for (List<String> dataItem : dataTable) {
			String dataName = dataItem.get(0);
			String dataPayer = dataItem.get(1);
			Double dataValue = Double.parseDouble(dataItem.get(2));
			
			BalanceRecord valueFromArray = returnArray.stream() //
					.filter(hist -> hist.getActionPerson().getName().equals(dataName))//
					.filter(hist -> hist.getMainPerson().getName().equals(dataPayer))//
					.findFirst().get();

			assertEquals(dataName, valueFromArray.getActionPerson().getName());
			assertEquals(dataPayer, valueFromArray.getMainPerson().getName());
			assertEquals(dataValue, valueFromArray.getValue());
		}
	}

}
