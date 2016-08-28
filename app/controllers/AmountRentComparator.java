package controllers;

import java.util.Comparator;

import models.Residence;

public class AmountRentComparator implements Comparator<Residence> {

	@Override
	public int compare(Residence m, Residence m1) {
		return m.rent.compareTo(m1.rent);
	}

}