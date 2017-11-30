package at.tdog;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public interface PaneInterface {
	public String toString();

	public Result getR();
	
	public default BufferedImage toImage() {
		String[] txt = toString().split("\n");
		BufferedImage bufferedImage = new BufferedImage(getR().vars.size() * 20 + 6 * 10, txt.length * 20,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = bufferedImage.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
		g.setColor(Color.BLACK);
		if(this instanceof Truthtable)
			g.drawLine(7, 17, bufferedImage.getWidth() - 7, 17);
		else
		{
			txt[0] = " "+txt[0];
			g.drawLine(7, ((int)(getR().vars.size()/2+getR().vars.size()%2))*20, bufferedImage.getWidth() - 7, ((int)(getR().vars.size()/2+getR().vars.size()%2))*20);
			g.drawLine(((int)(getR().vars.size()/2))*10 +8, 7,((int)(getR().vars.size()/2))*10 +8, bufferedImage.getHeight() - 5);
		}
		for (int i = 0; i < txt.length; i++)
			g.drawString(txt[i], 10, 15 + (i * 20));
		return bufferedImage;
	}
}
