package uk.co.verissimo.bsocial.bcard.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.verissimo.bsocial.bcard.entities.BalanceRecord;
import uk.co.verissimo.bsocial.bcard.entities.Person;
import uk.co.verissimo.bsocial.bcard.entities.Spend;

@Service
public class SpendService {

	private static final int ONE_NEGATIVE = -1;
	private static List<Spend> list = new ArrayList<Spend>();
	private static List<BalanceRecord> listBlnc = new ArrayList<BalanceRecord>();

	@Autowired
	private PersonService personService;
	
	public Spend insert(Spend spend) {
		// if the title is present on db, return exception
		Optional<Spend> filter = filterByTitle(spend.getTitle());
		if (filter.isPresent()) {
			throw new IllegalArgumentException("There is one Spend with this title on db");
		}

		// fake insert on db
		list.add(spend);
		splitSpendIntoBalance(spend);

		return spend;
	}
	
	public Spend findByTitle(String title) {
		Optional<Spend> filter = filterByTitle(title);
		if (filter.isPresent()) {
			return filter.get();
		}

		return null;
	}

	public List<BalanceRecord> findOwedRecords(Person person) {
		// 1) remove records which the payer is the same of main
		// 2) filter the records by main person
		// 3) return the list
		return listBlnc.stream() //
				.filter(bln -> !bln.getActionPerson().getName().equals(bln.getMainPerson().getName())) //
				.filter(bln -> bln.getMainPerson().getName().equals(person.getName())) //
				.collect(Collectors.toList());
	}

	public Double findOwedValue(Person person) {
		return findOwedRecords(person).stream() //
				.mapToDouble(bln -> bln.getValue()) //
				.sum();
	}

	public List<BalanceRecord> findHowMuchOweRecords(Person mainPerson, Person actionPerson) {
		// 1) find records from the main person
		// 2) filter the records by action person
		// 3) return the list
		return findOwedRecords(mainPerson).stream() //
				.filter(bln -> bln.getActionPerson().getName().equals(actionPerson.getName())) //
				.collect(Collectors.toList());

	}

	public Double findHowMuchOweValues(Person mainPerson, Person actionPerson) {
		Double value = findHowMuchOweRecords(mainPerson, actionPerson).stream() //
				.mapToDouble(bln -> bln.getValue()) //
				.sum();

		if (value < 0) {
			return value * ONE_NEGATIVE;
		}

		return 0.0;
	}

	public List<BalanceRecord> outstandingBalances() {
		List<BalanceRecord> returnArray = new ArrayList<>();

		Map<Person, Double> lenders = new HashMap<Person, Double>();
		Map<Person, Double> borrowers = new HashMap<Person, Double>();

		findLendersAndBorrowers(lenders, borrowers);

		outStandingByFullPayments(lenders, borrowers, returnArray);
		outStandingByParcialPayments(lenders, borrowers, returnArray);

		return returnArray;
	}

	private void outStandingByParcialPayments(Map<Person, Double> lenders, Map<Person, Double> borrowers,
			List<BalanceRecord> list) {
		for (Map.Entry<Person, Double> borrower : borrowers.entrySet()) {
			if (borrower.getValue() != null) {
				while (borrower.getValue() < 0) {
					for (Map.Entry<Person, Double> lender : lenders.entrySet()) {
						if (lender.getValue() != null) {
							BalanceRecord record = new BalanceRecord();
							record.setMainPerson(lender.getKey());
							record.setActionPerson(borrower.getKey());
							record.setValue(lender.getValue());
							list.add(record);

							borrowers.put(borrower.getKey(), borrower.getValue() + lender.getValue());
							lenders.put(lender.getKey(), null);

						}
					}
				}
			}
		}
	}

	private void outStandingByFullPayments(Map<Person, Double> lenders, Map<Person, Double> borrowers,
			List<BalanceRecord> list) {
		for (Map.Entry<Person, Double> lender : lenders.entrySet()) {
			for (Map.Entry<Person, Double> borrower : borrowers.entrySet()) {
				if (borrower.getValue() != null && (borrower.getValue() * ONE_NEGATIVE) < lender.getValue()) {
					BalanceRecord record = new BalanceRecord();
					record.setMainPerson(lender.getKey());
					record.setActionPerson(borrower.getKey());
					record.setValue(borrower.getValue() * ONE_NEGATIVE);
					list.add(record);

					lenders.put(lender.getKey(), lender.getValue() + borrower.getValue());
					borrowers.put(borrower.getKey(), null);
				}
			}

		}
	}

	private void findLendersAndBorrowers(Map<Person, Double> lenders, Map<Person, Double> borrowers) {
		personService.findAll().forEach(person -> {
			Double result = findOwedValue(person);
			if (result >= 0.0) {
				lenders.put(person, result);
			} else {
				borrowers.put(person, result);
			}
		});

		// lenders => Tommen=25.5 | Kelly=34.5
		// borrowers => Ola= 25.0 | Sandy= 15.0 | Sam= 20.0
	}

	public void clearDB() {
		list = new ArrayList<Spend>();
		listBlnc = new ArrayList<BalanceRecord>();
	}

	private Optional<Spend> filterByTitle(String title) {
		return list.stream().filter(el -> el.getTitle().contentEquals(title)).findAny();
	}

	private void splitSpendIntoBalance(Spend spend) {
		spend.getDetails().stream().forEach(detail -> {
			// insert payer vs person
			insertBalanceRecord(spend.getPayer(), detail.getPerson(), spend, detail.getValue());

			// insert person vs payer
			Double value = detail.getValue() * ONE_NEGATIVE;
			insertBalanceRecord(detail.getPerson(), spend.getPayer(), spend, value);
		});
	}

	private void insertBalanceRecord(Person mainPerson, Person actionPerson, Spend spend, Double value) {
		BalanceRecord bl = new BalanceRecord();
		bl.setMainPerson(mainPerson);
		bl.setActionPerson(actionPerson);
		bl.setSpend(spend);
		bl.setValue(value);

		listBlnc.add(bl);
	}


}
