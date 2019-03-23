package uk.co.verissimo.bsocial.bcard.service;

import org.springframework.stereotype.Service;

import uk.co.verissimo.bsocial.bcard.entities.Person;

@Service
public class PersonService {

	public Person insert(String name) {
		// fake insert on db
		
		Person person = new Person();
		person.setName(name);
		return person;
	}
	
}
