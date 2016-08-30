package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.Logger;
import play.db.jpa.Model;
import utils.LatLng;

@Entity
public class Residence extends Model {
	public String geoLocation;
	public Long rent;
	public int numberBedrooms;
	public String residenceType;
	public Date dateResidence;
	public int numberBathrooms;
	public int area; // the area of the residence in square metres
	public String eircode;

	@ManyToOne
	public Landlord landlord;

	@OneToOne(mappedBy = "residence")
	public Tenant tenant;

	public Residence(String geoLocation, Long rent, int numberBedrooms, String residenceType,
			int numberBathrooms, int area, String eircode) {
		this.geoLocation = geoLocation;
		this.rent = rent;
		this.numberBedrooms = numberBedrooms;
		this.residenceType = residenceType;
		this.numberBathrooms = numberBathrooms;
		this.area = area;
		this.eircode = eircode;
		dateResidence = new Date();
	}

	public void addUser(Landlord landlord) {
		this.landlord = landlord;
		this.save();
	}

	/**
	 * Converts latitude/longitude into geolocation
	 * 
	 * @return the geolocation of residence
	 */
	public LatLng getGeolocation() {
		return LatLng.toLatLng(geoLocation);
	}

	public static Residence findByEircode(String eircode) {
		return find("eircode", eircode).first();
	}

	public static List<Residence> findVacantResidences() {
		List<Residence> ResidenceAll = Residence.findAll();
		List<Residence> vacant = new ArrayList();
		for (Residence res : ResidenceAll) {
			if (res.tenant == null) {
				vacant.add(res);
			}
		}
		return vacant;
	}

	public static List<Residence> findRentedResidences() {
		List<Residence> ResidenceAll = Residence.findAll();
		List<Residence> rented = new ArrayList();
		for (Residence res : ResidenceAll) {
			if (res.tenant != null) {
				rented.add(res);
			}
		}
		return rented;
	}

}