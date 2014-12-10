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
	public int add(String stringToProcess) throws NegativeNumberException{
		// Init. the result to its default value (0).
		int total = 0;
		// Check if the input string's empty.
		if(!StringUtils.isEmpty(stringToProcess)){
			// numbersList will hold the string of input numbers.
			String numbersList;
			// splitArgs will hold the arguments. If the user specified a
			// custom delimiter, split("//") will return an array of length 2.
			String[] splitArgs = stringToProcess.split("//");
			// splitNumbers will hold the parsed array u¡of input numbers.
			String[] splitNumbers;
			// delRegex will hold the delimiter regex based on the
			// user-specified ones or the default (\n ,).
			String delimiterRegex;
			// if splitArgs has a length of 2, the user has specified custom
			// delimiters. splitArgs must be processed accordingly.
			if(splitArgs.length == 2){
				// Doing a split on splitArgs[1] will return an array of length
				// 2. Position [0] will hold the custom delimiter(s) and
				// position [1] holds the string list of numbers to add.
				numbersList = splitArgs[1].split("[\\n]")[1];
				// Process the delimiter(s).
				delimiterRegex = Pattern.quote(splitArgs[1].split("[\\n]")[0]);
				delimiterRegex = delimiterRegex.replace("[","").replace("]","");
				StringBuilder regexBuilder = new StringBuilder();
				regexBuilder.append("[");
				regexBuilder.append(delimiterRegex);
				regexBuilder.append("]+");
				delimiterRegex = regexBuilder.toString();
			}
			else{
				// The user didn't specify custom delimiters. Use default.
				delimiterRegex = "[\\n,]";
				numbersList = splitArgs[0];
			}
			//Parse the string list of numbers.
			splitNumbers = numbersList.split(delimiterRegex);
			// negatives may hold the negative numbers if present within the
			// list.
			ArrayList<Integer> negatives = new ArrayList<Integer>();
			try{
				// Go over the array of string numbers and try parsing them
				// into ints.
				for(String number : splitNumbers){
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
					StringBuilder negs = new StringBuilder();
					negs.append(negatives.get(0));
					for(int i = 1; i < negatives.size(); i++){
						negs.append(", ");
						negs.append(negatives.get(i));
					}
					throw new NegativeNumberException("Negatives not allowed: "
							+ negs.toString());
				}
			}
			// If the input is invalid, throw an exception.
			catch(NumberFormatException e){
				return 0;
			}
		}
		return total;
	}

}
