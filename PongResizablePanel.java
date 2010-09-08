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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class PongResizablePanel extends JPanel {
	
	boolean playtypeHor;
	Double speed;
	int pongLength;
	int pongWidth;
	int windowHeight;
	int windowWidth;
	int ballDiameter;
	
	public PongResizablePanel(
			boolean playtypeHor, Double speed, 
			int pongLength, int pongWidth, 
			int windowHeight, int windowWidth, 
			int ballDiameter) {

		this();
		this.playtypeHor	= playtypeHor;
		this.speed			= speed;
		this.pongLength		= pongLength;
		this.pongWidth		= pongWidth;
		this.windowHeight	= windowHeight;
		this.windowWidth	= windowWidth;
		this.ballDiameter	= ballDiameter;
		
		this.startUp();
	}
	
	public PongResizablePanel() {
		super();
	}
	
	public void startUp () {
		this.setOpaque(true); // we don't paint all our bits
		this.setLayout(new BorderLayout());
		this.setBackground(Color.black);
		//this.setBorder(BorderFactory.createLineBorder(Color.black));
		
	}
	
	public Dimension getPreferredSize() {
		// Figure out what the layout manager needs and
		// then add 100 to the largest of the dimensions
		// in order to enforce a 'round' bullseye 
		//Dimension layoutSize = super.getPreferredSize();
		//int max = Math.max(layoutSize.width,layoutSize.height);
		//return new Dimension(max+100,max+100);
		
		return new Dimension (this.windowWidth, this.windowHeight);
	}
	
}
