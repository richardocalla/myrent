package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

import org.json.simple.JSONObject;

public class InputData extends Controller {

	public static void index() {
		if (session.get("logged_status") != null && session.get("logged_status").equals("logged_in")) {
			render();
		} else {
			Landlords.login();
		}
	}

	/**
	 * Update models with residential data and renders ajax call
	 */
	public static void dataCapture(Residence residence) {
		Landlord landlord = Landlords.getCurrentLandlord();
		residence.addUser(landlord);
		residence.dateResidence = new Date();
		residence.save();

		Logger.info("Residence data received and saved");
		Logger.info("Residence type: " + residence.residenceType);

		JSONObject obj = new JSONObject();
		String value = "Congratulations. You have successfully registered your " + residence.residenceType + ".";
		obj.put("inputdata", value);
		renderJSON(obj);
	}

}