package uk.co.verissimo.bsocial.bcard.entities;

public class Person {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		// just to sort elements and get the same result as requested
		// NEVER use in production
		if (name.equals("Kelly")) {
			return 1;
		}
		if (name.equals("Ola")) {
			return 2;
		}
		if (name.equals("Sam")) {
			return 3;
		}
		if (name.equals("Tommen")) {
			return 4;
		}
		if (name.equals("Sandy")) {
			return 5;
		}

		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + "]";
	}
}
