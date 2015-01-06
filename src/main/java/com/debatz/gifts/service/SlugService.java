package com.debatz.gifts.service;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Locale;
import java.util.regex.Pattern;


public class SlugService 
{

	private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
	private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
	
	
	
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
}