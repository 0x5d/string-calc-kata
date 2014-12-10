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
					// If the number's negative, add it to negatives.
					if(nextAdd < 0){
						negatives.add(nextAdd);
					}
					// else, if the number's not greater than 1000 and no
					// negative numbers have been found, add it to the total.
					else if(negatives.size() == 0 && nextAdd <= 1000){
						total += nextAdd;
					}
				}
				// If at least one negative number was input, throw an
				// exception describing the problem.
				if(negatives.size() > 0){
					String negativeNumbers = StringUtils.join(negatives, ", ");
					throw new NegativeNumberException("Negatives not allowed: "
							+ negativeNumbers.toString());
				}
			}
			// If the input is invalid, throw an exception.
			catch(NumberFormatException e){
				throw new IllegalArgumentException();
			}
		}
		return total;
	}
	
	private String[] parseInput(String input){
		String[] parsedInput = new String[2];
		String numbersList;
		StringBuilder delimitersRegex = new StringBuilder();
		String[] splitInput = input.split("//");
		delimitersRegex.append("\n|,");
		
		if(splitInput.length == 2){
			String[] splitArgs = splitInput[1].split("\n");
			String delimitersString = splitArgs[0];
			numbersList = splitArgs[1];
			
			String[] delimiters = delimitersString.split(Pattern.quote("["));
			for(int i = 0; i < delimiters.length; i++){
				if(!StringUtils.isEmpty(delimiters[i])){
					delimiters[i] =
							Pattern.quote(delimiters[i].replace("]", ""));
					delimitersRegex.append("|").append(delimiters[i]);
				}
			}
		}
		else{
			// The user didn't specify custom delimiters. Use default.
			numbersList = splitInput[0];
		}
		parsedInput[0] = delimitersRegex.toString();
		parsedInput[1] = numbersList;
		
		return parsedInput;
	}

}
