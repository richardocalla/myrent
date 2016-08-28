package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Residence;
import models.Tenant;
import models.Landlord;
import play.Logger;
import play.mvc.Before;
import play.mvc.Controller;
import utils.Circle;
import utils.Geodistance;
import utils.LatLng;

/**
 * Searches for vacant residences by filtering out 
 *
 */
public class Search extends Controller {
	public static void index(double radius, double latcenter, double lngcenter) {
		// All reported residences will fall within this circle
		Circle circle = new Circle(latcenter, lngcenter, radius);
		Tenant tenant = Tenants.getCurrentTenant();
		List<Residence> residences = new ArrayList<Residence>();
		// Fetch all residences and filter out those within circle
		List<Residence> residencesAll = Residence.findAll();
		for (Residence res : residencesAll) {
			LatLng residenceLocation = res.getGeolocation();
			if ((Geodistance.inCircle(residenceLocation, circle)) && (res.tenant == null)) {
				residences.add(res);
			}
		}
		render(circle, tenant, residences);
	}

}