package com.prototype.organisation.auth;

public class CrossOriginAuth {
	private static String[] originURLs = {"http://192.168.178.31:4200", "http://localhost:4200"};
	
	public static String[] getOriginURLs() {
		return originURLs;
	}
}
