package com.artostapyshyn.automarketplace.exceptions;

public class AdvertisementNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AdvertisementNotFoundException(String parameter) {
		 super("Couldn't find any sale advertisements by - " + parameter);
	}
}
