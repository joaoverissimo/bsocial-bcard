package uk.co.verissimo.bsocial.bcard.entities;

public class BalanceRecord {

	private Person mainPerson;
	private Person actionPerson;
	private Spend spend;
	private Double value;

	public BalanceRecord() {

	}

	public Person getMainPerson() {
		return mainPerson;
	}

	public void setMainPerson(Person mainPerson) {
		this.mainPerson = mainPerson;
	}

	public Person getActionPerson() {
		return actionPerson;
	}

	public void setActionPerson(Person actionPerson) {
		this.actionPerson = actionPerson;
	}

	public Spend getSpend() {
		return spend;
	}

	public void setSpend(Spend spend) {
		this.spend = spend;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "BalanceRecord [mainPerson=" + mainPerson.getName() + ", actionPerson=" + actionPerson.getName()
				+ ", value=" + value + "]";
	}

}
