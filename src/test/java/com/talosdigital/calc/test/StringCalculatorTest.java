package com.talosdigital.calc.test;

import org.junit.Assert;
import org.junit.Test;
import com.talosdigital.calc.NegativeNumberException;
import com.talosdigital.calc.StringCalculator;

public class StringCalculatorTest {

	@Test
	public void testNoArgs() throws IllegalArgumentException{
		StringCalculator sc = new StringCalculator();
		int res;
		res = sc.add("");
		Assert.assertEquals(0, res);
	}
	
	@Test
	public void testOneArg() throws IllegalArgumentException{
		StringCalculator sc = new StringCalculator();
		int res;
		res = sc.add("1");
		Assert.assertEquals(1, res);
	}
	
	@Test
	public void testTwoArgs() throws IllegalArgumentException{
		StringCalculator sc = new StringCalculator();
		int res;
		res = sc.add("1,2");
		Assert.assertEquals(3, res);
	}
	
	@Test
	public void testMultipleArgs() throws IllegalArgumentException{
		StringCalculator sc = new StringCalculator();
		int res;
		res = sc.add("1,1,2,3,5");
		Assert.assertEquals(12, res);
	}
	
	@Test
	public void testNewLineSeparator() throws IllegalArgumentException{
		StringCalculator sc = new StringCalculator();
		int res;
		res = sc.add("1\n3");
		Assert.assertEquals(4, res);
	}
	
	@Test
	public void testCustomSeparator() throws IllegalArgumentException{
		StringCalculator sc = new StringCalculator();
		int res;
		res = sc.add("//s\n1s2");
		Assert.assertEquals(3, res);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArg() throws IllegalArgumentException{
		StringCalculator sc = new StringCalculator();
		sc.add("1,@");
		Assert.fail("No exception thrown when invalid argument was input.");
	}
	
	@Test
	public void testNegatives(){
		StringCalculator sc = new StringCalculator();
		try {
			sc.add("1\n3,-4,-6,9,0,-1");
			Assert.fail("No exception was thrown when negative numbers "
					+ "were input");
		}
		catch(NegativeNumberException nne) {
			Assert.assertEquals("Negatives not allowed: -4, -6, -1",
					nne.getMessage());
		}
	}
	
	@Test
	public void testBiggerThanThousand() throws IllegalArgumentException{
		StringCalculator sc = new StringCalculator();
		int res;
		res = sc.add("3,1001,2");
		Assert.assertEquals(5, res);
	}
	
	@Test
	public void testLongSeparators() throws IllegalArgumentException{
		StringCalculator sc = new StringCalculator();
		int res;
		res = sc.add("//[***]\n1***3***4");
		Assert.assertEquals(8, res);
	}
	
	@Test
	public void testMultipleCustomSeparators() throws IllegalArgumentException{
		StringCalculator sc = new StringCalculator();
		int res;
		res = sc.add("//[***][¿¿]\n1***3¿¿4");
		Assert.assertEquals(8, res);
	}
}
