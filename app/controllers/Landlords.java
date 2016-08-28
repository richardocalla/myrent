package controllers;

import play.*;
import play.mvc.*;
import utils.Circle;
import utils.Geodistance;
import utils.LatLng;

import java.util.*;
import models.*;

public class Landlords extends Controller {

	public static void index() {
		Landlord landlord = Landlords.getCurrentLandlord();
		if (landlord == null) {
			Logger.info("Landlord Class : unable to getCurrentLandlord");
			Landlords.login();
		} else {
			List<Residence> residenceAll = Residence.findAll();
			List<Residence> residences = new ArrayList();
			for (Residence res : residenceAll) {
				if (landlord.id == res.landlord.id) {
					residences.add(res);
				}
			}
			Logger.info("Landed in Landlord template");
			render(landlord, residences);
		}
	}

	public static void signup() {
		render();
	}

	public static void register(Landlord landlord) {
		landlord.save();
		login();
	}

	public static void login() {
		render();
	}

	public static void logout() {
		session.remove("logged_in_landlordid");
		Welcome.index();
	}

	public static void editprofile() {
		Landlord landlord = Landlords.getCurrentLandlord();
		if (session.get("logged_status") != null && session.get("logged_status").equals("logged_in")) {
			render(landlord);
		} else {
			Landlords.login();
		}
	}

	public static void edit(String firstName, String lastName, String address1, String address2, String city,
			String county) {
		String landlordId = session.get("logged_in_landlordid");
		Landlord landlord = Landlord.findById(Long.parseLong(landlordId));

		landlord.firstName = (firstName.equals("")) ? landlord.firstName : firstName;
		landlord.lastName = (lastName.equals("")) ? landlord.lastName : lastName;
		landlord.address1 = (address1.equals("")) ? landlord.address1 : address1;
		landlord.address2 = (address2.equals("")) ? landlord.address2 : address2;
		landlord.city = (city.equals("")) ? landlord.city : city;
		landlord.county = (county.equals("")) ? landlord.county : county;

		landlord.save();
		Logger.info("Details changed to: " + "First name: " + firstName + " " + "Last Name: " + lastName + " "
				+ "Address: " + address1 + ", " + address2 + " " + "City: " + city + " " + "County: " + county);
		InputData.index();
	}

	public static void editResidence(String eircode, Long rent) {
		Landlord landlord = Landlords.getCurrentLandlord();
		List<Residence> residenceAll = Residence.findAll();
		Residence residence = Residence.findByEircode(eircode);

		render("Landlords/editresidence.html", landlord, residenceAll, residence);
	}

	public static void updateResidence(String eircode, Long rent) {
		Residence residence = Residence.findByEircode(eircode);

		residence.rent = (rent.equals("")) ? residence.rent : rent;

		residence.save();
		Logger.info("Details changed to: " + "Rent: " + rent);
		index();
	}

	/**
	 * Deletes residence model and breaks relationship between tenant and
	 * residence (otherwise constraint violation occurs)
	 * 
	 * @param eircode
	 *            unique id for residence
	 */
	public static void deleteResidence(String eircode) {
		Landlord landlord = Landlords.getCurrentLandlord();
		Residence residence = Residence.findByEircode(eircode);
		for (Residence res : landlord.residences) {
			Tenant tenant = res.tenant;
			if (tenant != null) {
				// break relationship between tenant and residence
				tenant.residence = null;
				tenant.save();
			}
		}
		landlord.residences.remove(eircode);
		residence.delete();
		index();
	}

	public static Landlord getCurrentLandlord() {
		Landlord landlord = null;
		if (session.contains("logged_in_landlordid")) {
			String landlordId = session.get("logged_in_landlordid");
			landlord = Landlord.findById(Long.parseLong(landlordId));
		}
		return landlord;
	}

	public static void authenticate(String email, String password) {
		Logger.info("Attempting to authenticate with " + email + ":" + " " + password);

		Landlord landlord = Landlord.findByEmail(email);
		if ((landlord != null) && (landlord.checkPassword(password) == true)) {
			Logger.info("Authentication successful");
			session.put("logged_in_landlordid", landlord.id);
			session.put("logged_status", "logged_in");
			index();
		} else {
			Logger.info("Authentication failed");
			login();
		}
	}

}