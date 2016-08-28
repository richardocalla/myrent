package controllers;

import java.util.Comparator;

import models.Residence;

public class UnfilteredListComparator implements Comparator<Residence> {

	@Override
	public int compare(Residence m, Residence m1) {
		return m.eircode.compareTo(m1.eircode);
	}

}