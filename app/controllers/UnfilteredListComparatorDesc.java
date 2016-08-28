package controllers;

import java.util.Comparator;

import models.Residence;

public class UnfilteredListComparatorDesc implements Comparator<Residence> {

	@Override
	public int compare(Residence m, Residence m1) {
		return m1.eircode.compareTo(m.eircode);
	}

}