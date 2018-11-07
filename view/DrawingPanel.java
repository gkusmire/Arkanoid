package view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.ObjectPos;
import model.GameBoardElements;
import model.Points;


public class DrawingPanel extends JPanel{
	private static final long serialVersionUID = 6606365086509234376L;
	public DrawingPanel(String blockName, GameBoardElements m) throws IOException 
	{
		super();
		model = m;
		loadBlock(blockName);
		setDimension(model.mapSize.getWidth(), model.mapSize.getHeight());
	}
	public void drawWindow(GameBoardElements m)
	{
		model = m;
		this.repaint();
	}
	@Override
	public void paintComponent(Graphics g) {
		paintBlocks(g);
		paintBalls(g);
		paintPlatform(g);
		writeScores(g);
		paintVerticalLine(g);
		paintRunningPoints(g);
		paintLifes(g);
	}
	public void setNewDataReference(GameBoardElements objPos) {
		model = objPos;
	}
	private	void loadBlock(String blockName) throws IOException
	{
		block = ImageIO.read(new File(blockName));
	}
	private void paintLifes(Graphics g) {
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		g.setColor(Color.GREEN);
		g.drawString("Lifes: " + model.lifes.getLifes() + " ", model.mapSize.getWidth()*50+100, 20);
	}
	private void paintBlocks(Graphics g)
	{
		for(ObjectPos e : model.blocks)
		{
			paintBlock(e.getXpos(), e.getYpos(), g);
		}
	}
	private void paintBlock(int x, int y, Graphics g)
	{
		g.drawImage(block, x*50, y*50, null);
	}
	private void paintBalls(Graphics g)
	{
		g.setColor(Color.white);
		for(ObjectPos e : model.balls)
		{
			paintBall(e.getXpos(), e.getYpos(), model.ballSize, g);
		}
	}
	private void paintRunningPoints(Graphics g){
		g.setColor(Color.LIGHT_GRAY);
		for(Points p : model.points){
			paintPoints(p.getXpos(), p.getYpos(), p.getValue(), g);
		}
	}
	private void paintPoints(int x, int y, int v, Graphics g){
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		g.drawString(" " + v + " ", x, y);
	}
	private void paintBall(int x, int y, int ballSize, Graphics g)
	{
		g.fillOval(x, y, ballSize, ballSize);
	}
	private void paintPlatform(Graphics g)
	{
		g.setColor(Color.YELLOW);
		g.drawLine(model.platform.getXpos(), model.mapSize.getHeight()*50, 
				model.platform.getXpos()+model.platform.getLength(), model.mapSize.getHeight()*50);
	}
	private void paintVerticalLine(Graphics g)
	{
		g.drawLine(model.mapSize.getWidth()*50, 0, model.mapSize.getWidth()*50, model.mapSize.getHeight()*50+20);
	}
	private void writeScores(Graphics g)
	{
		g.setColor(Color.RED);
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		g.drawString("Scores:" + model.scores, model.mapSize.getWidth()*50+20, 
				model.mapSize.getHeight()*50 - 20);
		g.setColor(Color.BLACK);
	}
	private void setDimension(int x, int y)
	{
		Dimension dim = new Dimension(x*50 + 200, y*50+20);
		setPreferredSize(dim);
	}
	private BufferedImage block;
	private GameBoardElements model;
}