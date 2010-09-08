/*
 * Resizable ping pong game
 * 
 * Made by Hallvard Nygård, hnygard.no
 * Code repository: http://github.com/HNygard/PongResizable
 * 
 * License: Creative Commons Attribution-Share Alike 3.0 Norway License
 *          http://creativecommons.org/licenses/by-sa/3.0/no/
 */

package pongresizable;

import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

public class PongResizable extends JApplet {
	
	boolean playtypeHor;
	Double speed;
	int pongLength;
	int pongWidth;
	int windowHeight;
	int windowWidth;
	int ballDiameter;
	
	public static PongResizablePanel panel;

	private TheGame thegame;
	
	public PongResizable (
			boolean playtypeHor, Double speed, 
			int pongLength, int pongWidth, 
			int windowHeight, int windowWidth, 
			int ballDiameter) {
		
		this.playtypeHor	= playtypeHor;
		this.speed			= speed;
		this.pongLength		= pongLength;
		this.pongWidth		= pongWidth;
		this.windowHeight	= windowHeight;
		this.windowWidth	= windowWidth;
		this.ballDiameter	= ballDiameter;
		
		JFrame f = new JFrame("PongResizable, laget av Hallvard Nygård");
		f.setSize(this.windowHeight, this.windowWidth);
		f.setUndecorated(true);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		PongResizablePanel panel = new PongResizablePanel(
				this.playtypeHor, this.speed,
				this.pongLength, this.pongWidth,
				this.windowHeight, this.windowWidth,
				this.ballDiameter);

		thegame = new TheGame(
				this.playtypeHor, this.speed,
				this.pongLength, this.pongWidth,
				this.windowHeight, this.windowWidth,
				this.ballDiameter);
		/*thegame.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println("abbbc");
			}
			public void keyReleased(KeyEvent e) {};
			public void keyTyped(KeyEvent e) { }
		});*/
		panel.add(thegame, BorderLayout.CENTER);
        
		Thread counterThread = new Thread(thegame, "Counter");
		counterThread.start();
		
		f.setContentPane(panel);
		
		
		//panel.add(new JLabel("Get ready!", SwingConstants.CENTER), BorderLayout.CENTER);
		//f.getContentPane().add(panel, BorderLayout.CENTER);
		f.pack();
		f.show();
	}
	/*
	public void paint(Graphics g)
	{
		g.drawString("i = "+i,10,20);
	}
	
	public boolean keyDown(Event e, int key)
	{
		System.out.println("key = " + key);
		
		return true;
	}*/
}
