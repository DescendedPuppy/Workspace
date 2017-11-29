package at.tdog;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class Truthtable extends ScrollPane implements PaneInterface {

	private GridPane gridpane;
	private Result r;
	
	public Result getR() {
		return r;
	}

	public void setR(Result r) {
		this.r = r;
	}
	
	public Truthtable(Result input) {
		this.r = input;
		gridpane = new GridPane();
		refresh();
		setContent(gridpane);
		gridpane.setGridLinesVisible(true);
	}

	public void refresh() {
		int i = 0;
		for (i = 0; i < r.vars.size(); i++) {
			Text t = new Text("  " + r.vars.get(i) + "  ");
			gridpane.add(t, i, 0);
		}
		gridpane.add(new Text("  Result  "), i++, 0);

		for (int k = 0; k < r.resultSet.size(); k++) {
			String kek = r.resultSet.get(k);
			for (int p = 0; p < r.vars.size() + 1; p++)
				gridpane.add(new Text("  " + kek.split(" ")[p].toUpperCase()), p, k + 1);

		}
	}

	public String toString() {
		String ret = "";
		int i = 0;
		for (i = 0; i < r.vars.size(); i++)
			ret += r.vars.get(i) + " ";
		ret += "Result\n";
		for (int k = 0; k < r.resultSet.size(); k++) {
			String kek = r.resultSet.get(k);
			for (int p = 0; p < r.vars.size() + 1; p++)
				ret += kek.split(" ")[p].toUpperCase() + " ";
			ret = ret.substring(0, ret.length() - 1);
			ret += "\n";
		}
		return ret;
	}
}
