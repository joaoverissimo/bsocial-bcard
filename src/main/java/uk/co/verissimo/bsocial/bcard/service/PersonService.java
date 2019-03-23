package uk.co.verissimo.bsocial.bcard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import uk.co.verissimo.bsocial.bcard.entities.Person;

@Service
public class PersonService {

	private static List<Person> list = new ArrayList<Person>();
	
	public Person insert(String name) {
		// if is present, then return the existing object
		Optional<Person> filter = applyFilter(name);
		if (filter.isPresent()) {
			return filter.get();
		}

		// fake insert on db
		Person person = new Person();
		person.setName(name);
		list.add(person);

		return person;
	}

	public Person findByName(String name) {
		Optional<Person> filter = applyFilter(name);
		if (filter.isPresent()) {
			return filter.get();
		}

		return null;
	}

	public List<Person> findAll() {
		return list;
	}

	private Optional<Person> applyFilter(String name) {
		Optional<Person> filter = list.stream().filter(el -> el.getName().contentEquals(name)).findAny();
		return filter;
	}

	
}
