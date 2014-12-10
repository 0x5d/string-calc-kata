package com.talosdigital.calc;

import java.util.ArrayList;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author castillobg
 *
 */
public class StringCalculator {
	
	private final String DEFAULT_DELIMS_REGEX = "\n|,";
	private final String DEFINITION_SPARATOR = "//";
	
	/**
	 * 
	 * @param stringToProcess The string with the parameters:
	 * Custom delimiter(s) (optional) and the delimiter-separated list of 
	 * numbers to add.
	 * @return The sum's total.
	 * @throws NegativeNumberException
	 */
	public int add(String stringToProcess)
			throws IllegalArgumentException, NegativeNumberException{
		// Init. the result to its default value (0).
		int total = 0;
		// Check if the input string's empty.
		if(!StringUtils.isEmpty(stringToProcess)){
			// numbersList will hold the string of input numbers.
			String numbersList;
			// splitNumbers will hold the parsed array u¡of input numbers.
			String[] splitNumbers;
			// delRegex will hold the delimiter regex based on the
			// user-specified ones or the default (\n ,).
			String delimiterRegex;
			
			// parsedInput holds the parsed input string. [0] holds the 
			// delimiter regex, while [1] contains the string list of
			// delimiter-separated numbers.
			String[] parsedInput = parseInput(stringToProcess);
			delimiterRegex = parsedInput[0];
			numbersList = parsedInput[1];
			
			//Parse the string list of numbers.
			splitNumbers = numbersList.split(delimiterRegex);
			// negatives may hold the negative numbers if present within the
			// list.
			ArrayList<Integer> negatives = new ArrayList<Integer>();
			try{
				// Go over the array of string numbers and try parsing them
				// into ints.
				//System.out.println("Numbers: ");
				for(String number : splitNumbers){
					//System.out.println(number);
					int nextAdd = Integer.parseInt(number);
					// If the number's negative, get all the negatives input
					// and throw an exception.
					if(nextAdd < 0){
						throw new NegativeNumberException("Negatives not allowed: "
								+ getNegativesListString(splitNumbers));
					}
					// else, if the number's not greater than 1000 and no
					// negative numbers have been found, add it to the total.
					else if(nextAdd <= 1000){
						total += nextAdd;
					}
				}
			}
			// If the input is invalid, throw an exception.
			catch(NumberFormatException e){
				throw new IllegalArgumentException();
			}
		}
		return total;
	}
	
	/**
	 * 
	 * @param input the string that defines the optional delimiters and the
	 * number list.
	 * @return The string array of length 2 containing the delimiters regular
	 * expression in its first position and the string list of number in its
	 * second.
	 */
	private String[] parseInput(String input){
		// The array to return.
		String[] parsedInput = new String[2];
		// numbersList will hold the string list of numbers after the initial
		// input is split.
		String numbersList;
		// splitInput will hold the split input (delimiters and number list)
		// in position [1].
		String[] splitInput = input.split(DEFINITION_SPARATOR);
		// The delimiters regular expression that will be put in position [0]
		// of parsedInput.
		StringBuilder delimitersRegex = new StringBuilder();
		// Append the default delimiters.
		delimitersRegex.append(DEFAULT_DELIMS_REGEX);
		
		if(splitInput.length == 2){
			// splitArgs holds the the custom delimiters definition (if any)
			// and the string list of numbers (in [0] and [1], respectively).
			String[] splitArgs = splitInput[1].split("\n");
			String delimitersString = splitArgs[0];
			numbersList = splitArgs[1];
			
			// Because variable-length custom delimiter definition may begin
			// with "[", splitting on it with that character returns the
			// delimiter list (with "]" appended at the end of each).
			String[] delimiters = delimitersString.split(Pattern.quote("["));
			for(int i = 0; i < delimiters.length; i++){
				if(!StringUtils.isEmpty(delimiters[i])){
					// Remove theappended "]" and quote so special characters
					// are not considered in the resulting regular expression.
					delimiters[i] =
							Pattern.quote(delimiters[i].replace("]", ""));
					// append the resulting delimiter to the default one
					// (already present in delimitersRegex).
					delimitersRegex.append("|").append(delimiters[i]);
				}
			}
		}
		else{
			// The user didn't specify custom delimiters. Number list is in
			// splitInput[0].
			numbersList = splitInput[0];
		}
		// Populate the return array.
		parsedInput[0] = delimitersRegex.toString();
		parsedInput[1] = numbersList;
		
		return parsedInput;
	}
	
	/**
	 * 
	 * @param numbers the array containing the string numbers.
	 * @return The comma-separated list of negative numbers present in the
	 * input list.
	 * @throws NumberFormatException
	 */
	public String getNegativesListString(String[] numbers)
			throws NumberFormatException{
		ArrayList<String> negatives = new ArrayList<String>();
		for(String number : numbers){
			if(Integer.parseInt(number) < 0){
				negatives.add(number);
			}
		}
		return StringUtils.join(negatives, ", ");
	}

}
