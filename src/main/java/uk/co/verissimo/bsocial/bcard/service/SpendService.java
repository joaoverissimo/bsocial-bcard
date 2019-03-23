package uk.co.verissimo.bsocial.bcard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import uk.co.verissimo.bsocial.bcard.entities.Spend;

@Service
public class SpendService {

	private static List<Spend> list = new ArrayList<Spend>();
	
	public Spend insert(Spend spend) {
		// if the title is present on db, return exception
		Optional<Spend> filter = filterByTitle(spend.getTitle());
		if (filter.isPresent()) {
			throw new IllegalArgumentException("There is one Spend with this title on db");
		}

		// fake insert on db
		list.add(spend);

		return spend;
	}
	
	public Spend findByTitle(String title) {
		Optional<Spend> filter = filterByTitle(title);
		if (filter.isPresent()) {
			return filter.get();
		}

		return null;
	}

	private Optional<Spend> filterByTitle(String title) {
		return list.stream().filter(el -> el.getTitle().contentEquals(title)).findAny();
	}

}
