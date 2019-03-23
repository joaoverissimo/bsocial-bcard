package uk.co.verissimo.bsocial.bcard.cucumber;

import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import uk.co.verissimo.bsocial.bcard.entities.Person;
import uk.co.verissimo.bsocial.bcard.entities.Spend;
import uk.co.verissimo.bsocial.bcard.entities.SpendDetail;
import uk.co.verissimo.bsocial.bcard.service.PersonService;
import uk.co.verissimo.bsocial.bcard.service.SpendService;

public class SpendSteps {

	private Spend spend;
	private Exception exception;

	@Autowired
	private PersonService personService;

	@Autowired
	private SpendService spendService;

	@Given("the spend {string} which payer was {string} and the spends:")
	public void the_spend_which_payer_was_and_the_spends(String spendTitle, String spendPayer,
			List<Map<String, String>> dataTable) {

		try {
			spend = new Spend();
			spend.setTitle(spendTitle);
			spend.setPayer(personService.insert(spendPayer));
			spend.setValue(dataTable.stream() //
					.mapToDouble(el -> Double.parseDouble(el.get("value"))).sum());

			dataTable.forEach(el -> {
				Person person = personService.insert(el.get("name"));
				Double value = Double.parseDouble(el.get("value"));
				spend.setDetailsAdd(new SpendDetail(person, value));
			});

			spendService.insert(spend);
		} catch (Exception e) {
			spend = null;
			exception = e;
		}
	}

	@When("Insert a Spend")
	public void insert_a_Spend() {
		// just insert
	}

	@Then("the spend was saved")
	public void the_spend_was_saved() {
		String title = spend.getTitle();
		Spend spendDb = spendService.findByTitle(title);
		assertNotNull(spendDb);
	}

	@Then("an exception is throw")
	public void an_exception_is_throw() {
		assertNotNull(exception);
	}

}
