package com.artostapyshyn.automarketplace.exceptions;

public class InvalidPasswordException extends RuntimeException {
	 
	private static final long serialVersionUID = 1L;

		public InvalidPasswordException() {
			 super("Wrong email or password, try again.");
		}
	       
}
