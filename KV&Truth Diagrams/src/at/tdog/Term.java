package at.tdog;

import java.util.HashMap;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

public class Term {

	//done: AND OR NAND XOR NOR biconditional implication
	
	
	private String term;

	public Term(String input) {

		input = input.replace("~", "!");
		input = input.replace("&", "&&");
		input = input.replace("|", "!=");
		input = input.replace("+", "||");
		

		input = replaceNor(input);
		input = replaceBi(input);
		input = replaceImpl(input);
		input = replaceXor(input);
				

		char[] chars = input.toCharArray();
		StringBuilder builder = new StringBuilder();
		for (char c : chars) {
			builder.append(c);
			if (Character.isAlphabetic(c)&&(c!='T'&&c!='F'))
				builder.append("VAR");
		}
		String result = builder.toString();
		result = result.replace("?}?", "Boolean.logicalXor");
		result = result.replace("T", "true");
		result = result.replace("F", "false");
		term = result;
	}
	
	public boolean getResult(HashMap<String, String> map) {
		String replaced = term;
		for (int i = 0; i < map.keySet().size(); i++) {
			if (!map.keySet().toArray()[i].equals(""))
				replaced = replaced.replace((String) map.keySet().toArray()[i] + "VAR",
						map.get(map.keySet().toArray()[i]));
		}
		Binding binding = new Binding();
		GroovyShell shell = new GroovyShell(binding);
		boolean value = (boolean) shell.evaluate("return " + replaced + ";");
		return value;
	}
	
	public String replaceNor(String input)
	{
		for (int index = input.indexOf("-"); index >= 0; index = input.indexOf("-", index + 1)) {

			int brackets = 0;
			String left = "";
			for (int leftInt = index-1; leftInt >= 0; leftInt--) {
				if (Character.isAlphabetic(input.charAt(leftInt))&&brackets==0)
				{
					left+=input.charAt(leftInt);
					break;
				}
				
				if(input.charAt(leftInt)=='(')
					brackets++;
				else if(input.charAt(leftInt)==')')
					brackets--;
				left += input.charAt(leftInt);
			}
			left = new StringBuilder(left).reverse().toString();

			String right = "";
			for (int leftInt = index+1; leftInt < input.length(); leftInt++) {
				
				if (Character.isAlphabetic(input.charAt(leftInt))&&brackets==0)
				{
					right+=input.charAt(leftInt);
					break;
				}
				
				if(input.charAt(leftInt)=='(')
					brackets++;
				else if(input.charAt(leftInt)==')')
					brackets--;
				right += input.charAt(leftInt);
				
			}
			
			input = input.replace(left+"-"+right, "!("+left+"||"+right+")");
			//input = input.replace(input.substring(index - 1, index + 2),"!(" + input.charAt(index - 1) + "||" + input.charAt(index + 1) + ")");
		}
		return input;
	}
	
	public String replaceBi(String input)
	{
		for (int index = input.indexOf("="); index >= 0; index = input.indexOf("=", index + 1)) {

			int brackets = 0;
			String left = "";
			for (int leftInt = index-1; leftInt >= 0; leftInt--) {
				
				if (Character.isAlphabetic(input.charAt(leftInt))&&brackets==0)
				{
					left+=input.charAt(leftInt);
					break;
				}
				
				if(input.charAt(leftInt)=='(')
					brackets++;
				else if(input.charAt(leftInt)==')')
					brackets--;
				left += input.charAt(leftInt);
				
			}
			left = new StringBuilder(left).reverse().toString();
			
			String right = "";
			for (int leftInt = index+1; leftInt < input.length(); leftInt++) {
				
				if (Character.isAlphabetic(input.charAt(leftInt))&&brackets==0)
				{
					right+=input.charAt(leftInt);
					break;
				}
				
				if(input.charAt(leftInt)=='(')
					brackets++;
				else if(input.charAt(leftInt)==')')
					brackets--;
				right += input.charAt(leftInt);
				
			}
			input = input.replace(left+"="+right, "!("+left+"&&"+right+")");
			//input = input.replace(input.substring(index - 1, index + 2),"!(" + input.charAt(index - 1) + "||" + input.charAt(index + 1) + ")");
		}
		return input;
	}

	public String replaceXor(String input)
	{
		for (int index = input.indexOf("#"); index >= 0; index = input.indexOf("#", index + 1)) {

			int brackets = 0;
			String left = "";
			for (int leftInt = index-1; leftInt >= 0; leftInt--) {
				
				if (Character.isAlphabetic(input.charAt(leftInt))&&brackets==0)
				{
					left+=input.charAt(leftInt);
					break;
				}
				
				if(input.charAt(leftInt)=='(')
					brackets++;
				else if(input.charAt(leftInt)==')')
					brackets--;
				left += input.charAt(leftInt);
				
			}
			left = new StringBuilder(left).reverse().toString();

			String right = "";
			for (int leftInt = index+1; leftInt < input.length(); leftInt++) {
				
				if (Character.isAlphabetic(input.charAt(leftInt))&&brackets==0)
				{
					right+=input.charAt(leftInt);
					break;
				}
				
				if(input.charAt(leftInt)=='(')
					brackets++;
				else if(input.charAt(leftInt)==')')
					brackets--;
				right += input.charAt(leftInt);
				
			}
			input = input.replace(left+"#"+right, "?}?("+left+","+right+")");
			//input = input.replace(input.substring(index - 1, index + 2),"!(" + input.charAt(index - 1) + "||" + input.charAt(index + 1) + ")");
		}
		return input;
	}

	public String replaceImpl(String input)
	{
		for (int index = input.indexOf(">"); index >= 0; index = input.indexOf(">", index + 1)) {

			int brackets = 0;
			String left = "";
			for (int leftInt = index-1; leftInt >= 0; leftInt--) {
				
				if (Character.isAlphabetic(input.charAt(leftInt))&&brackets==0)
				{
					left+=input.charAt(leftInt);
					break;
				}
				
				if(input.charAt(leftInt)=='(')
					brackets++;
				else if(input.charAt(leftInt)==')')
					brackets--;
				left += input.charAt(leftInt);
				
			}
			left = new StringBuilder(left).reverse().toString();
			
			String right = "";
			for (int leftInt = index+1; leftInt < input.length(); leftInt++) {
				
				if (Character.isAlphabetic(input.charAt(leftInt))&&brackets==0)
				{
					right+=input.charAt(leftInt);
					break;
				}
				
				if(input.charAt(leftInt)=='(')
					brackets++;
				else if(input.charAt(leftInt)==')')
					brackets--;
				right += input.charAt(leftInt);
				
			}
			input = input.replace(left+">"+right, "(!"+left+"||"+right+")");
			//input = input.replace(input.substring(index - 1, index + 2),"!(" + input.charAt(index - 1) + "||" + input.charAt(index + 1) + ")");
		}
		return input;
	}
	
}
