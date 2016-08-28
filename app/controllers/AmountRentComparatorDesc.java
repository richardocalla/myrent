package controllers;

import java.util.Comparator;

import models.Residence;

public class AmountRentComparatorDesc implements Comparator<Residence> {

	@Override
	public int compare(Residence m, Residence m1) {
		return m1.rent.compareTo(m.rent);
	}

}