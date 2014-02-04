package com.javaworld.media.j2d;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Marbles {
	/**
	 * 
	 */
	private final static String MAINACTIVITY = "MAINACTIVITY";
	private final static String INTROACTIVITY = "INTROACTIVITY";
	private final static String ENDACTIVITY = "ENDACTIVITY";
	private final static String FINALACTIVITY = "FINALACTIVITY";
	private final static String RESULTS = "RESULTS";
	public int guess = 0;
	public int trueval = 0;
	public int deviation = 0;

	public static void main(String[] args) {
		new Marbles();
	}

	public Marbles() {
		JFrame main = new JFrame("Java 2D");
		main.setSize(400, 300);
		MarbleDrawingComponent marbles = new MarbleDrawingComponent();
		trueval = marbles.trueval;
		final CardLayout mainCards = new CardLayout();
		final JPanel mainPanel = new JPanel(mainCards);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel introPanel = new JPanel(new BorderLayout());
		JPanel finalPanel = new JPanel(new BorderLayout());
		JPanel resultPanel = new JPanel(new BorderLayout());
		introPanel.add(new JLabel("<html><center>Hello!<br/><br/>You will be presented a field of blue and orange circles for a random amount of time.<br/>Your task is to count how many orange circles there are and report your best guess.</center></html>", JLabel.CENTER),
				BorderLayout.CENTER);
		JPanel endPanel = new JPanel(new BorderLayout());
		endPanel.add(new JLabel("End", JLabel.CENTER), BorderLayout.CENTER);
		JLabel guessLabel = new JLabel(
				"Enter the number of orange circles you think there were");
		JLabel deviationLabel = new JLabel(
				"Enter how far off (in circles) you think you were");
		JLabel resultsLabel = new JLabel("Done! Your reseults are:");
		final JTextField deviationInput = new JTextField();
		final JTextField guessInput = new JTextField();
		final JTextArea finalOutput = new JTextArea();
		finalOutput.setEditable(false);
		JButton endButton = new JButton("Next");
		JButton finalButton = new JButton("Done");
		finalButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				guess = Integer.parseInt(guessInput.getText());
				deviation = Integer.parseInt(deviationInput.getText());
				finalOutput.setText("Guess: " + guess + "\nDeviation: "
						+ deviation + "\nOff by: " + (trueval - guess));
				mainCards.show(mainPanel, RESULTS);
			}
		});
		finalPanel.add(deviationLabel, BorderLayout.PAGE_START);
		finalPanel.add(deviationInput, BorderLayout.CENTER);
		finalPanel.add(finalButton, BorderLayout.PAGE_END);
		endButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mainCards.show(mainPanel, FINALACTIVITY);
			}

		});
		resultPanel.add(resultsLabel, BorderLayout.PAGE_START);
		resultPanel.add(finalOutput);
		endPanel.add(guessInput);
		endPanel.add(guessLabel, BorderLayout.PAGE_START);
		endPanel.add(endButton, BorderLayout.PAGE_END);
		JButton beginButton = new JButton("Begin!");
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
				Timer timer = new Timer(1000, null);
				ActionListener taskPerformer = new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						mainCards.show(mainPanel, ENDACTIVITY);
					}
				};
				timer.addActionListener(taskPerformer);
				timer.setRepeats(false);
				timer.start();

			}
		});
		introPanel.add(beginButton, BorderLayout.PAGE_END);
		mainPanel.add(introPanel, INTROACTIVITY);

		mainPanel.add(marbles, MAINACTIVITY);
		mainPanel.add(endPanel, ENDACTIVITY);
		mainPanel.add(finalPanel, FINALACTIVITY);
		mainPanel.add(resultPanel, RESULTS);
		main.add(mainPanel);
		main.setVisible(true);

	}

}

class MarbleDrawingComponent extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ArrayList<Ellipse2D> goodellipses = new ArrayList<Ellipse2D>();
	public ArrayList<Ellipse2D> badellipses = new ArrayList<Ellipse2D>();
	public int score = 0;
	public int trueval = 0;

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
		trueval = number2;
	}

	public MarbleDrawingComponent() {
		generateEllipses();
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
