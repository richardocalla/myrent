package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Landlord extends Model {
	public String firstName;
	public String lastName;
	public String email;
	public String password;
	public String address1;
	public String address2;
	public String city;
	public String county;

	@OneToMany(mappedBy = "landlord", cascade = CascadeType.ALL)
	public List<Residence> residences = new ArrayList<Residence>();

	public Landlord(String firstName, String lastName, String email, String password, String address1, String address2,
			String city, String county) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.county = county;
	}

	public static Landlord findByEmail(String email) {
		return find("email", email).first();
	}

	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}

	/**
	 * Retrieves rent for an individual landlord
	 * 
	 * @return total rent for individual landlord
	 */
	public int getIndividualRent() {
		int total = 0;
		for (Residence res : this.residences) {
			total += res.rent;
		}
		return total;
	}

	/**
	 * Retrieves total rent for all landlords
	 * 
	 * @return total rent for all landlords
	 */
	public static int getOverallRent() {
		int total = 0;
		List<Landlord> landlordAll = Landlord.findAll();
		for (Landlord l : landlordAll) {
			total += l.getIndividualRent();
		}
		return total;
	}

	/**
	 * Retrieves individual landlord total rent as a percentage of overall rent
	 * for all landlords
	 * 
	 * @return individual landlord rent*100/overall rent
	 */
	public int getRentPercentage() {
		return getIndividualRent() * 100 / getOverallRent();

	}

}