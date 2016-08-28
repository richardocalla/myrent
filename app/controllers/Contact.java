package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Contact extends Controller {

	public static void index() {
		Logger.info("Landed in Contact class");
		render();
	}

	/**
	 * Acknowledgement page renders on successful submission of message
	 */
	public static void acknowledge() {
		render();
	}

	private static void addMessage(String firstName, String lastName, String email, String messageTxt) {
		Message input = new Message(firstName, lastName, email, messageTxt);
		input.save();
	}

	public static void sendMessage(String firstName, String lastName, String email, String messageTxt) {
		addMessage(firstName, lastName, email, messageTxt);
		Contact.acknowledge();
	}

}