package uk.co.verissimo.bsocial.bcard.entities;

import java.util.ArrayList;
import java.util.List;

public class Spend {

	private String title;
	private Double value;
	private Person payer;
	private List<SpendDetail> details = new ArrayList<>();

	public Spend() {
		// empty constructor
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Person getPayer() {
		return payer;
	}

	public void setPayer(Person payer) {
		this.payer = payer;
	}

	public List<SpendDetail> getDetails() {
		return details;
	}

	public void setDetails(List<SpendDetail> details) {
		this.details = details;
	}

	public void setDetailsAdd(SpendDetail detail) {
		this.details.add(detail);
	}

	@Override
	public String toString() {
		return "Spend [title=" + title + ", value=" + value + "]";
	}

}
