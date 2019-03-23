package uk.co.verissimo.bsocial.bcard.entities;

public class SpendDetail {
	private Person person;
	private Double value;

	public SpendDetail() {
		// empty constructor
	}

	public SpendDetail(Person person, Double value) {
		this.person = person;
		this.value = value;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "SpendDetail [person=" + person + ", value=" + value + "]";
	}

}
