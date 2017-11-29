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
		
		
		if(this instanceof Truthtable)
			g.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
		else
		{
			int i=0;
			String line ="";
			while(getR().vars.parallelStream().anyMatch((line = txt[i])::contains))
			{
				
			}
		}
		
		
		g.setColor(Color.BLACK);

		g.drawString(txt[0], 10, 15);
		g.drawLine(7, 17, bufferedImage.getWidth() - 7, 17);

		for (int i = 1; i < txt.length; i++)
			g.drawString(txt[i], 10, 15 + (i * 20));
		return bufferedImage;
	}
}
