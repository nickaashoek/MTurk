package com.javaworld.media.j2d;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
public class marbles extends Frame implements MouseListener{
	public ArrayList<Ellipse2D> goodellipses =  new ArrayList<Ellipse2D>();
	public static void main(String[] args){
		new marbles();
	}
	public marbles(){
		super("Java 2D");
		setSize(400,300);
		setVisible(true);
		this.addMouseListener(this);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				dispose(); System.exit(0);}
			});
		
	}
	public void mouseClicked(MouseEvent evt){
		Point2D point= evt.getPoint();
		System.out.println("Mouse clicked at "+point);
		for (Ellipse2D e:goodellipses){
			if(e.contains(point))
				System.out.println("Found in ellipse");
		}
	}
	public void paint(Graphics g){
		Random rand = new Random();
		g.setColor(Color.blue);
		Graphics2D g2da = (Graphics2D)g;
		g2da.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		int number = rand.nextInt(80)+50;
		for(int i=0;i<number;i++){
			int x = rand.nextInt(400);
			int y = rand.nextInt(300);
			g2da.fillOval(x, y, 10, 10);
		}
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);	    
		int number2 = rand.nextInt(20);
		g2d.setColor(Color.orange);
		for(int i=0;i<number2;i++){
			int x = rand.nextInt(400);
			int y = rand.nextInt(300);
			Ellipse2D e = new Ellipse2D.Double(x, y, 10,10);
			goodellipses.add(e);
		}
	    for (Ellipse2D e : goodellipses) {
	        g2d.draw(e);
	    }
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
