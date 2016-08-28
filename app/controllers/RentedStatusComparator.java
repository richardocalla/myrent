package controllers;

import java.util.Comparator;

import models.Residence;

public class RentedStatusComparator implements Comparator<Residence> {

	@Override
	public int compare(Residence m, Residence m1) {
		return m.vacancy.compareTo(m1.vacancy);
	}

}