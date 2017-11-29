package at.tdog;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class KVDiagram extends ScrollPane implements PaneInterface{

	private String[][] cha;
	public ArrayList<ArrayList<String>> vert, horz;
	public ArrayList<String> chars;
	public HashMap<String, String> resultMap;
	private GridPane grid, wrapperX, wrapperY, wrapper;
	private int offSetX,offSetY;
	private Result r;

	public Result getR() {
		return r;
	}

	public void setR(Result r) {
		this.r = r;
	}

	public KVDiagram(Result r) {

		this.r=r;
		this.r.hashCode();
		chars = r.vars;
		grid = new GridPane();
		wrapper = new GridPane();
		wrapperX = new GridPane();
		wrapperY = new GridPane();
		setContent(wrapper);

		cha = new String[(int) Math.pow(2, r.vars.size() / 2 + r.vars.size() % 2)][(int) Math.pow(2,
				r.vars.size() / 2)];

		horz = new ArrayList<ArrayList<String>>();
		vert = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < r.vars.size() / 2 + r.vars.size() % 2; i++)
			horz.add(new ArrayList<String>());
		for (int i = 0; i < r.vars.size() / 2; i++)
			vert.add(new ArrayList<String>());

		// //System.out.println(horz.size()+","+vert.size());
		// //System.out.println(cha.length+","+cha[0].length);

		initSets();
		hashIt(r);
		grid.setGridLinesVisible(true);
		for (int i = 0; i < cha.length; i++) {
			for (int k = 0; k < cha[0].length; k++) {
				grid.add(new Text(cha[i][k]), i, k);
			}
		}

		offSetX = vert.size() + 1;
		offSetY = horz.size() + 1;
		for (int i = 0; i < horz.size(); i++) {
			int k;
			for (k = 0; k < horz.get(i).size(); k++) {
				if (horz.get(i).get(k) == "T")
				{
					Text t = new Text(" " + horz.get(i).get(k) + " ");
					t.setFill(Color.GREEN);
					wrapperX.add(t, k + offSetX, i);
				}
				else
				{
					Text t = new Text(" " + horz.get(i).get(k) + " ");
					t.setFill(Color.RED);
					wrapperX.add(t, k + offSetX, i);
				}
			}
			wrapperX.add(new Text((" " + r.vars.get(i) + " ").toUpperCase()), k + 1 + offSetX, i);
		}

		for (int i = 0; i < vert.size(); i++) {
			int k;
			for (k = 0; k < vert.get(i).size(); k++) {
				// System.out.println(vert.get(i).get(k));

				if (vert.get(i).get(k) == "T") {
					Text t = new Text(" " + vert.get(i).get(k) + " ");
					t.setFill(Color.GREEN);
					wrapperY.add(t, i, k + offSetY);
				} else {
					Text t = new Text(" "+vert.get(i).get(k)+" ");
					t.setFill(Color.RED);
					wrapperY.add(t, i, k + offSetY);					
				}
			}
			wrapperY.add(new Text((" " + r.vars.get(i + horz.size()) + " ").toUpperCase()), i, k + 1 + offSetY);
		}
		wrapper.add(wrapperX, offSetX, 0);
		wrapper.add(wrapperY, 0, offSetY);
		wrapper.add(grid, offSetX, offSetY);
	}

	public void initSets() {

		for (int layer = 0; layer < horz.size(); layer++) {
			int pos = 0;
			for (int i = 0; i < cha.length / (Math.pow(2, layer + 1)); i++) {
				horz.get(layer).add("F");
				for (int p = 0; p < cha[0].length; p++)
					cha[pos][p] = new StringBuilder(cha[pos][p] + "").append("F ").toString().replace("null", "");

				pos++;
			}
			while (horz.get(layer).size() != cha.length) {
				for (int i = 0; i < cha.length / (Math.pow(2, Integer.max(layer, 1))); i++) {
					horz.get(layer).add("T");
					for (int p = 0; p < cha[0].length; p++) {
						// //System.out.println(pos +": "+cha.length +"\n" + p +": " +cha[0].length);
						cha[pos][p] = new StringBuilder(cha[pos][p] + "").append("T ").toString().replace("null", "");
					}
					pos++;
				}
				for (int i = 0; i < cha.length / (Math.pow(2, Integer.max(layer, 1))); i++) {
					if (pos != cha.length) {
						horz.get(layer).add("F");
						for (int p = 0; p < cha[0].length; p++) {
							cha[pos][p] = new StringBuilder(cha[pos][p] + "").append("F ").toString().replace("null",
									"");
						}
						pos++;
					} else
						break;
				}
			}
		}

		for (int layer = 0; layer < vert.size(); layer++) {
			int pos = 0;
			for (int i = 0; i < cha[0].length / (Math.pow(2, layer + 1)); i++) {
				vert.get(layer).add("F");
				for (int p = 0; p < cha.length; p++)
					cha[p][pos] = new StringBuilder(cha[p][pos] + "").append("F ").toString().replace("null", "");

				pos++;
			}
			while (vert.get(layer).size() != cha[0].length) {
				for (int i = 0; i < cha[0].length / (Math.pow(2, Integer.max(layer, 1))); i++) {
					vert.get(layer).add("T");
					for (int p = 0; p < cha.length; p++) {
						// //System.out.println(pos +": "+cha[0].length +"\n" + p +": " +cha.length);
						cha[p][pos] = new StringBuilder(cha[p][pos] + "").append("T ").toString().replace("null", "");
					}
					pos++;
				}
				for (int i = 0; i < cha[0].length / (Math.pow(2, Integer.max(layer, 1))); i++) {
					if (pos != cha[0].length) {
						vert.get(layer).add("F");
						for (int p = 0; p < cha.length; p++) {
							cha[p][pos] = new StringBuilder(cha[p][pos] + "").append("F ").toString().replace("null",
									"");
						}
						pos++;
					} else
						break;
				}
			}
		}
		/*
		 * horz.forEach(e -> { e.forEach(e1 -> { //System.out.print(e1.replace("F",
		 * " ").replace("T", "-"));
		 * 
		 * }); //System.out.println(); });
		 */
	}

	public void hashIt(Result r) {
		HashMap<String, String> hm = new HashMap<String, String>();
		for (int i = 0; i < r.resultSet.size(); i++) {
			hm.put(r.resultSet.get(i).substring(0, r.resultSet.get(i).length() - 1),
					r.resultSet.get(i).substring(r.resultSet.get(i).length() - 1));
			// //System.out.println(r.resultSet.get(i).substring(0,
			// r.resultSet.get(i).length()-1)+","+
			// r.resultSet.get(i).substring(r.resultSet.get(i).length()-1));
		}

		for (int i = 0; i < cha.length; i++)
			for (int k = 0; k < cha[0].length; k++)
				cha[i][k] = " " + hm.get(cha[i][k].toLowerCase()).toUpperCase() + " ";

	}

	public String toString()
	{
		String ret = "";
		ret+=new String(new char[(offSetX-1)*2]).replace("\0", " ");
		ret+=horz.toString().replace("], [", " öÜhG \n"+new String(new char[(offSetX-1)*2]).replace("\0", " ")).replace("[", "").replace("]", "").replace(",", "")+" öÜhG ";
		for(int i=0;i<chars.size();i++)
			ret=ret.replaceFirst(" öÜhG "," "+ chars.get(i).toUpperCase());
		for(int i=0;i<vert.get(0).size();i++)
		{
			ret+="\n";
			for(int k=0;k<vert.size();k++)
				ret+=vert.get(k).get(i)+" ";
			for(int k=0;k<cha.length;k++)
				ret+=cha[k][i].replace(" ", "")+" ";
		}
		ret+="\n";
		for(int i=chars.size()/2+chars.size()%2;i<chars.size();i++)
			ret+=chars.get(i).toUpperCase()+" ";
		return ret;
	}	
}
