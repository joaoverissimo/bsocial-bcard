package uk.co.verissimo.bsocial.bcard.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.co.verissimo.bsocial.bcard.entities.Person;
import uk.co.verissimo.bsocial.bcard.service.PersonService;

@RestController
@RequestMapping("api/person")
public class PersonController {

	private Logger LOG = LoggerFactory.getLogger(PersonController.class);
	
	@Autowired
	private PersonService personService;
	
	@GetMapping
	public Person getPerson() {
		return personService.insert("João Verissimo");
	}
	
}
