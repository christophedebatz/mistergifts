package com.debatz.gifts.service;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Locale;
import java.util.regex.Pattern;


public class SlugService 
{

	private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
	private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

	/**
	 *
	 * @param input
	 * @return
     */
	public static String getSlug(String input) 
	{
	    if (input == null) {
	        throw new IllegalArgumentException();
	    }
	    
	    String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
	    String normalized = Normalizer.normalize(nowhitespace, Form.NFD);
	    String slug = NONLATIN.matcher(normalized).replaceAll("");
	    
	    return slug.toLowerCase(Locale.FRANCE);
	}

	/**
	 *
	 * @param id
	 * @param name
	 * @param brand
     * @return
     */
	public static String getSlug(int id, String name, String brand) {
		StringBuilder slugInput = new StringBuilder();
		slugInput.append(id);
		slugInput.append(" ");

		if (brand != null && brand.length() > 0) {
			slugInput.append(brand);
			slugInput.append(" ");
		}

		slugInput.append(name);

		return getSlug(slugInput.toString());
	}
}