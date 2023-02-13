package com.artostapyshyn.automarketplace.exceptions;

public class SellerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public SellerNotFoundException(String parameter) {
		 super("Couldn't find any sellers by - " + parameter);
	}
}
