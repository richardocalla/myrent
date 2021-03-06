package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Residence;
import models.Landlord;
import play.Logger;
import play.mvc.Before;
import play.mvc.Controller;
import utils.Circle;
import utils.Geodistance;
import utils.LatLng;

public class ReportController extends Controller {
	/**
	 * This method executed before each action call in the controller. Checks
	 * that a landlord has logged in. If no landlord logged in the landlord is
	 * presented with the log in screen.
	 */
	@Before
	static void checkAuthentification() {
		if (session.contains("logged_in_landlordid") == false)
			Landlords.login();
	}

	/**
	 * Generates a Report instance relating to all residences within circle
	 * 
	 * @param radius
	 *            The radius (metres) of the search area
	 * @param latcenter
	 *            The latitude of the centre of the search area
	 * @param lngcenter
	 *            The longtitude of the centre of the search area
	 */
	public static void generateReport(double radius, double latcenter, double lngcenter) {
		// All reported residences will fall within this circle
		Circle circle = new Circle(latcenter, lngcenter, radius);
		Landlord landlord = Landlords.getCurrentLandlord();
		List<Residence> residences = new ArrayList<Residence>();
		// Fetch all residences and filter out those within circle
		List<Residence> residencesAll = Residence.findAll();
		for (Residence res : residencesAll) {
			LatLng residenceLocation = res.getGeolocation();
			if (Geodistance.inCircle(residenceLocation, circle)) {
				residences.add(res);
			}
		}
		render("ReportController/renderReport.html", landlord, circle, residences);
	}

	/**
	 * Render the views/ReporController/index.html template This presents a map
	 * and resizable circle to indicate a search area for residences
	 */
	public static void index() {
		render();
	}

}