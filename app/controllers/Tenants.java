package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;

public class Tenants extends Controller {

	public static void index() {
		Tenant tenant = Tenants.getCurrentTenant();
		List<Residence> residenceAll = Residence.findAll();
		List<Residence> vacantResidence = vacantResidence();
		if (tenant == null) {
			Logger.info("Tenant Class : unable to getCurrentTenant");
			Tenants.login();
		}
		Logger.info("Landed in Tenant template");
		render(tenant, residenceAll, vacantResidence);
	}

	public static void signup() {
		render();
	}

	public static void register(Tenant tenant) {
		tenant.save();
		login();
	}

	public static void login() {
		render();
	}

	public static void logout() {
		session.remove("logged_in_tenantid");
		Welcome.index();
	}

	public static Tenant getCurrentTenant() {
		Tenant tenant = null;
		if (session.contains("logged_in_tenantid")) {
			String tenantId = session.get("logged_in_tenantid");
			tenant = Tenant.findById(Long.parseLong(tenantId));
		}
		return tenant;
	}

	public static void authenticate(String email, String password) {
		Logger.info("Attempting to authenticate with " + email + ":" + " " + password);

		Tenant tenant = Tenant.findByEmail(email);
		if ((tenant != null) && (tenant.checkPassword(password) == true)) {
			Logger.info("Authentication successful");
			session.put("logged_in_tenantid", tenant.id);
			session.put("logged_status", "logged_in");
			index();
		} else {
			Logger.info("Authentication failed");
			login();
		}
	}

	public static List<Residence> vacantResidence() {
		List<Residence> residenceAll = Residence.findAll();
		List<Residence> residences = new ArrayList();
		for (Residence res : residenceAll) {
			if (res.tenant == null) {
				residences.add(res);
			}
		}
		return residences;
	}

	/**
	 * Renders dynamic array list of residences with no tenant relationships
	 */
	public static void vacantResidences() {
		List<List<String>> jsonArray = new ArrayList<List<String>>();
		List<Residence> residences = Residence.findAll();
		for (Residence res : residences) {
			if (res.tenant == null) {
				Double position1 = res.getGeolocation().getLatitude();
				Double position2 = res.getGeolocation().getLongitude();
				jsonArray.add(0, Arrays.asList(" " + res.eircode + " : " + "No Tenant", position1.toString(),
						position2.toString()));
			}
		}
		renderJSON(jsonArray);
	}

	public static void changeTenancy(String eircode_vacancy) {
		Tenant tenant = Tenants.getCurrentTenant();

		Residence newResidence = Residence.findByEircode(eircode_vacancy);

		Logger.info("Residence" + " " + eircode_vacancy);
		tenant.residence = newResidence;

		newResidence.save();
		tenant.save();

		index();
	}

	public static void endTenancy() {
		Tenant tenant = Tenants.getCurrentTenant();

		tenant.residence = null;
		tenant.save();
		Logger.info("Current tenancy deleted");
		index();
	}

}