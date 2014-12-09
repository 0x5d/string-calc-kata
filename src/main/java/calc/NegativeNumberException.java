package calc;

/**
 * 
 * @author castillobg
 * Thrown when negative numbers are input into the String Calculator.
 */
public class NegativeNumberException extends IllegalArgumentException {

	public NegativeNumberException(String message) {
		super(message);
	}

}
