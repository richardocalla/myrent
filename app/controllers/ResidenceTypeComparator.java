package controllers;

import java.util.Comparator;

import models.Residence;

public class ResidenceTypeComparator implements Comparator<Residence> {

	@Override
	public int compare(Residence m, Residence m1) {
		return m.residenceType.compareTo(m1.residenceType);
	}

}