package com.javaworld.media.j2d;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Marbles {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6806601217744283298L;
	private final static String MAINACTIVITY = "MAINACTIVITY";
	private final static String INTROACTIVITY = "INTROACTIVITY";

	public static void main(String[] args) {
		new Marbles();
	}

	public Marbles() {
		JFrame main = new JFrame("Java 2D");
		main.setSize(400, 300);
		final CardLayout mainCards = new CardLayout();
		final JPanel mainPanel = new JPanel(mainCards);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel introPanel=new JPanel(new BorderLayout());
		introPanel.add(new JLabel("INTRO goes here.",JLabel.CENTER),BorderLayout.CENTER);
		JButton beginButton=new JButton("Begin!");
		beginButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				mainCards.show(mainPanel, MAINACTIVITY);
			}
		});
		introPanel.add(beginButton,BorderLayout.PAGE_END);
		mainPanel.add(introPanel, INTROACTIVITY);
		mainPanel.add(new MarbleDrawingComponent(), MAINACTIVITY);
		main.add(mainPanel);
		main.setVisible(true);

	}

}

class MarbleDrawingComponent extends JComponent {
	public ArrayList<Ellipse2D> goodellipses = new ArrayList<Ellipse2D>();
	public ArrayList<Ellipse2D> badellipses = new ArrayList<Ellipse2D>();
	public int score = 0;

	private void generateEllipses() {
		Random rand = new Random();
		int number = rand.nextInt(80) + 50;
		for (int i = 0; i < number; i++) {
			int x = rand.nextInt(400);
			int y = rand.nextInt(300);
			Ellipse2D e = new Ellipse2D.Double(x, y, 10, 10);
			badellipses.add(e);
		}
		int number2 = rand.nextInt(20);
		for (int i = 0; i < number2; i++) {
			int x = rand.nextInt(400);
			int y = rand.nextInt(300);
			Ellipse2D e = new Ellipse2D.Double(x, y, 10, 10);
			goodellipses.add(e);
		}

	}

	public MarbleDrawingComponent() {
		generateEllipses();
		this.addMouseListener(new MouseListener() {

			@Override
			public void mousePressed(MouseEvent evt) {
				Point2D point = evt.getPoint();
				System.out.println("Mouse clicked at " + point);
				for (Ellipse2D e : goodellipses) {
					if (e.contains(point)) {
						score++;
						System.out.println("Found in ellipse, total score is "
								+ score);
						int index = goodellipses.indexOf(e);
						goodellipses.remove(index);
						if (goodellipses.size() == 0) {
							System.out.println("YOU WIN!!!!!");
						}
						break;
					}
				}
				repaint();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.blue);
		for (Ellipse2D e : badellipses) {
			g2d.fill(e);
		}
		g2d.setColor(Color.orange);
		for (Ellipse2D e : goodellipses) {
			g2d.draw(e);
		}
	}
}
