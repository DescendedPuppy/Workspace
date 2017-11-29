package at.tdog;

import java.util.ArrayList;
import java.util.HashMap;

public class Result {
	
	public ArrayList<String> vars;
	private double length;
	private Term term;
	public ArrayList<String> resultSet;
	
	public Result(String input)
	{
		resultSet = new ArrayList<String>();
		term = new Term(input);
		vars = new ArrayList<String>();
		input = input.replaceAll("[^a-zA-EG-SU-Z]", " ");
		input = input.replaceAll("( )+", " ");
		String[] arr = input.split(" ");
		for(int i = 0 ; i < arr.length ; i++)
			if(!vars.contains(arr[i]))
				vars.add(arr[i]);
		vars.remove("");
		length = Math.pow(2, vars.size());
	}
	
	public void generateResults()
	{
		for (int i=0; i<length; i++) {
			int arrayIndex = 0;
			HashMap<String,String> hm = new HashMap<String,String>();
            for (int j=vars.size()-1; j>=0; j--) {
            	if((i/(int) Math.pow(2, j))%2==1)
            		hm.put(vars.get(arrayIndex), "true");
            	else if((i/(int) Math.pow(2, j))%2==0)
            		hm.put(vars.get(arrayIndex), "false");
                arrayIndex++;
            }
            String res ="";
            for(int k=0;k<vars.size();k++)
            	res+=hm.get(vars.get(k)).substring(0, 1)+" ";
            res+=String.valueOf(term.getResult(hm)).substring(0, 1);
            resultSet.add(res);
        }
		
	}
}
