package controllers;

import play.*;
import play.mvc.*;
import utils.Circle;
import utils.Geodistance;
import utils.LatLng;

import java.util.*;

import javax.persistence.Entity;

import models.*;

public class Administrators extends Controller {

	public static void index() {
		Administrator administrator = Administrators.getCurrentAdministrator();
		if (administrator == null) {
			Logger.info("Administrator Class : unable to getCurrentAdministrator");
			Administrators.login();
		} else {
			List<Landlord> landlords = Landlord.findAll();
			List<Tenant> tenants = Tenant.findAll();

			Logger.info("Landed in Administrator template");
			render(administrator, landlords, tenants);
		}
	}

	public static void signup() {
		render();
	}

	public static void register(Administrator administrator) {
		administrator.save();
		login();
	}

	public static void login() {
		render();
	}

	public static void logout() {
		session.remove("logged_in_administratorid");
		Welcome.index();
	}

	public static void reports() {
		Landlord landlord = Landlords.getCurrentLandlord();
		Tenant tenant = Tenants.getCurrentTenant();
		List<Residence> residences = Residence.findAll();

		render(landlord, tenant, residences);
	}

	/**
	 * Utilises comparator to sort by tenant (ascending)
	 */
	public static void byRented() {
		Landlord landlord = Landlords.getCurrentLandlord();
		Tenant tenant = Tenants.getCurrentTenant();
		List<Residence> residences = Residence.findAll();

		RentedStatusComparator md = new RentedStatusComparator();

		Collections.sort(residences, md);
		render(landlord, tenant, residences);
	}

	/**
	 * Utilises comparator to sort by tenant (descending)
	 */
	public static void byRentedDesc() {
		Landlord landlord = Landlords.getCurrentLandlord();
		Tenant tenant = Tenants.getCurrentTenant();
		List<Residence> residences = Residence.findAll();

		RentedStatusComparatorDesc md = new RentedStatusComparatorDesc();

		Collections.sort(residences, md);
		render(landlord, tenant, residences);
	}

	/**
	 * Utilises comparator to sort by residence type (ascending)
	 */
	public static void byResidenceType() {
		Landlord landlord = Landlords.getCurrentLandlord();
		Tenant tenant = Tenants.getCurrentTenant();
		List<Residence> residences = Residence.findAll();
		ResidenceTypeComparator md = new ResidenceTypeComparator();

		Collections.sort(residences, md);
		render(landlord, tenant, residences);
	}

	/**
	 * Utilises comparator to sort by residence type (descending)
	 */
	public static void byResidenceTypeDesc() {
		Landlord landlord = Landlords.getCurrentLandlord();
		Tenant tenant = Tenants.getCurrentTenant();
		List<Residence> residences = Residence.findAll();
		ResidenceTypeComparatorDesc md = new ResidenceTypeComparatorDesc();

		Collections.sort(residences, md);
		render(landlord, tenant, residences);
	}

	/**
	 * Utilises comparator to sort by rent amount (ascending)
	 */
	public static void bySortedRent() {
		Landlord landlord = Landlords.getCurrentLandlord();
		Tenant tenant = Tenants.getCurrentTenant();
		List<Residence> residences = Residence.findAll();
		AmountRentComparator md = new AmountRentComparator();

		Collections.sort(residences, md);
		render(landlord, tenant, residences);
	}

	/**
	 * Utilises comparator to sort by rent amount (descending)
	 */
	public static void bySortedRentDesc() {
		Landlord landlord = Landlords.getCurrentLandlord();
		Tenant tenant = Tenants.getCurrentTenant();
		List<Residence> residences = Residence.findAll();
		AmountRentComparatorDesc md = new AmountRentComparatorDesc();

		Collections.sort(residences, md);
		render(landlord, tenant, residences);
	}

	/**
	 * Utilises comparator to sort by eircode (ascending)
	 */
	public static void filter() {
		Landlord landlord = Landlords.getCurrentLandlord();
		Tenant tenant = Tenants.getCurrentTenant();
		List<Residence> residences = Residence.findAll();
		UnfilteredListComparator md = new UnfilteredListComparator();

		Collections.sort(residences, md);
		render(landlord, tenant, residences, md);
	}

	/**
	 * Utilises comparator to sort by eircode (descending)
	 */
	public static void filterDesc() {
		Landlord landlord = Landlords.getCurrentLandlord();
		Tenant tenant = Tenants.getCurrentTenant();
		List<Residence> residences = Residence.findAll();
		UnfilteredListComparatorDesc md = new UnfilteredListComparatorDesc();

		Collections.sort(residences, md);
		render(landlord, tenant, residences, md);
	}

	public static void charts() {
		Administrator administrator = Administrators.getCurrentAdministrator();
		List<Residence> residences = Residence.findAll();
		List<Residence> landlords = Landlord.findAll();
		if (administrator == null) {
			Logger.info("Administrator class : Unable to getCurrentAdministrator");
			Administrators.login();
		}
		render(administrator, residences, landlords);
	}

	/**
	 * Presents list of dynamic landlord rent data to render on pie chart
	 */
	public static void chartData() {
		List<List<String>> jsonArray = new ArrayList<List<String>>();

		List<Landlord> landlordsAll = Landlord.findAll();
		for (Landlord landlords : landlordsAll) {
			int rent = landlords.getRentPercentage();
			jsonArray.add(0, Arrays.asList(landlords.firstName + " " + landlords.lastName, "" + rent));
		}
		renderJSON(jsonArray);
	}

	public static void authenticate(String email, String password) {
		Logger.info("Attempting to authenticate with " + email + ":" + " " + password);

		Administrator administrator = Administrator.findByEmail(email);
		if ((administrator != null) && (administrator.checkPassword(password) == true)) {
			Logger.info("Authentication successful");
			session.put("logged_in_administratorid", administrator.id);
			session.put("logged_status", "logged_in");
			index();
		} else {
			Logger.info("Authentication failed");
			login();
		}
	}

	public static Administrator getCurrentAdministrator() {
		Administrator administrator = null;
		if (session.contains("logged_in_administratorid")) {
			String administratorId = session.get("logged_in_administratorid");
			administrator = Administrator.findById(Long.parseLong(administratorId));
		}
		return administrator;
	}

	/**
	 * Renders dynamic array list of all residences and separates tenants by
	 * vacancy status
	 */
	public static void geoLocations() {
		List<List<String>> jsonArray = new ArrayList<List<String>>();

		List<Residence> residences = Residence.findAll();
		for (Residence res : residences) {
			if (res.tenant == null) {
				Double position1 = res.getGeolocation().getLatitude();
				Double position2 = res.getGeolocation().getLongitude();
				jsonArray.add(0, Arrays.asList(" " + res.eircode + " : " + "No Tenant", position1.toString(),
						position2.toString()));
			} else {
				Double position1 = res.getGeolocation().getLatitude();
				Double position2 = res.getGeolocation().getLongitude();
				jsonArray.add(0, Arrays.asList(
						" " + res.eircode + " : " + "Tenant is " + res.tenant.firstName + " " + res.tenant.lastName,
						position1.toString(), position2.toString()));
			}
		}
		renderJSON(jsonArray);
	}

	/**
	 * Deletes tenant model from databae
	 * 
	 * @param email_tenant
	 *            unique if for tenant
	 */
	public static void deleteTenant(String email_tenant) {
		Tenant tenant = Tenant.findByEmail(email_tenant);
		Logger.info("Email: " + email_tenant);

		tenant.delete();
		index();
	}

	/**
	 * Deletes landlord model and any relationship with tenant
	 * 
	 * @param email_landlord
	 *            unique id for landlord
	 */
	public static void deleteLandlord(String email_landlord) {
		Landlord landlord = Landlord.findByEmail(email_landlord);
		Logger.info("Email: " + email_landlord);

		for (Residence res : landlord.residences) {
			Tenant tenant = res.tenant;
			if (tenant != null) {
				// break relationship between tenant and residence
				tenant.residence = null;
				tenant.save();
			}
		}
		landlord.delete();
		index();
	}

}