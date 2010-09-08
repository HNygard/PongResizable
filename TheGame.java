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

import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;

public class TheGame extends JComponent implements Runnable{

	Thread t;
	int i = 0;
	
	boolean gameWaiting;
	boolean playtypeHor;
	double speed;
	int pongLength;
	int pongWidth;
	int windowHeight;
	int windowWidth;
	
	int ballX, ballY;
	int ballDiameter;
	double ballMovementX, ballMovementY;
	
	int score1, score2;
	
	int pongOneX, pongOneY;
	int pongTwoX, pongTwoY;
	int pongLengthX, pongLengthY;
	int pongMovement = 10;
	
	
	public TheGame(
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
		
		if(this.playtypeHor) {
			this.pongLengthX = this.pongWidth;
			this.pongLengthY = this.pongLength;
		} else {
			this.pongLengthX = this.pongLength;
			this.pongLengthY = this.pongWidth;
		}
		//addKeyListener(new TheGameListener());
		this.addKeyListener(new TheGameListener());
		this.setFocusable(true);
		
		if(this.playtypeHor) {
			this.pongOneX = 2;
			this.pongTwoX = this.windowWidth - 2 - this.pongLengthX;
			this.pongTwoY = this.pongOneY = ((int)(this.windowHeight / 2)) - ((int)(this.pongLengthY/2));
		} else {

			this.pongOneY = 2;
			this.pongTwoY = this.windowHeight - 2 - this.pongLengthY;
			this.pongTwoX = this.pongOneX = ((int)(this.windowWidth / 2)) - ((int)(this.pongLengthX/2));
		}
	}
	
	public void run()
	{
		init();
		while(true)
		{
			i++;
			
			if(!gameWaiting) {
				moveObjects();
			}
			repaint();
			
			try {
				Thread.sleep(1000/30);
			} catch (InterruptedException e) { ; }
		}
	}
	
	public void moveObjects()
	{
		// Ball
		ballX = (int)(ballX + (ballMovementX * speed));
		ballY = (int)(ballY + (ballMovementY * speed));
		
		// Kollisjon - pong
		if(playtypeHor) {
			if(ballX < pongOneX + pongLengthX &&
				(
						(ballY > pongOneY && ballY < pongOneY + pongLengthY) ||
						(ballY + ballDiameter > pongOneY && ballY + ballDiameter < pongOneY + pongLengthY)
				)) {
				ballX = pongOneX + pongLengthX;
				ballMovementX = - ballMovementX;
			}
			if(ballX + ballDiameter > pongTwoX &&
				(
					(ballY > pongTwoY && ballY < pongTwoY + pongLengthY) ||
					(ballY + ballDiameter > pongTwoY && ballY + ballDiameter < pongTwoY + pongLengthY)
				)) {
				ballX = pongTwoX - ballDiameter;
				ballMovementX = - ballMovementX;
			}
		} else {
			if(ballY < pongOneY + pongLengthY &&
				(
						(ballX > pongOneX && ballX < pongOneX + pongLengthX) ||
						(ballX + ballDiameter > pongOneX && ballX + ballDiameter < pongOneX + pongLengthX)
				)) {
				ballY = pongOneY + pongLengthY;
				ballMovementY = - ballMovementY;
			}
			if(ballY + ballDiameter > pongTwoY &&
				(
					(ballX > pongTwoX && ballY < pongTwoX + pongLengthX) ||
					(ballX + ballDiameter > pongTwoX && ballX + ballDiameter < pongTwoX + pongLengthX)
				)) {
				ballY = pongTwoY - ballDiameter;
				ballMovementY = - ballMovementY;
			}
		}
		
		// Kollisjon - vegg
		if(ballX <= 0) {
			// Utenfor venstre
			ballMovementX = - ballMovementX;
			if(playtypeHor)
				gameWon(2);
		}
		if((ballX + ballDiameter) >= windowWidth) {
			// Utenfor høyre
			ballMovementX = - ballMovementX;
			if(playtypeHor)
				gameWon(1);
		}
		if(ballY <= 0) {
			// Utenfor topp
			ballMovementY = - ballMovementY;
			if(!playtypeHor)
				gameWon(2);
		}
		if((ballY + ballDiameter) >= windowHeight) {
			// Utenfor nede
			ballMovementY = - ballMovementY;
			if(!playtypeHor)
				gameWon(1);
		}
		
		if(ballX < 0) ballX = 0;
		if(ballX + ballDiameter > windowWidth) 
			ballX = windowWidth - ballDiameter;

		if(ballY < 0) ballY = 0;
		if(ballY + ballDiameter > windowHeight) 
			ballY = windowHeight - ballDiameter;
		
		// Pong utenfor bane?
		if(playtypeHor) {
			if(pongOneY < 0)
				pongOneY = 0;
			if(pongOneY + pongLengthY > windowHeight)
				pongOneY = windowHeight - pongLengthY;
			if(pongTwoY < 0)
				pongTwoY = 0;
			if(pongTwoY + pongLengthY > windowHeight)
				pongTwoY = windowHeight - pongLengthY;
		} else {
			if(pongOneX + pongLengthX > windowWidth)
				pongOneX = windowWidth - pongLengthX;
			if(pongOneX < 0)
				pongOneX = 0;
			if(pongTwoX + pongLengthX > windowWidth)
				pongTwoX = windowWidth - pongLengthX;
			if(pongTwoX < 0)
				pongTwoX = 0;
		}
	}
	
	public void init()
	{
		// Start positions:
		this.ballX = ((int)(this.windowWidth / 2)) - ((int)(this.ballDiameter/2));
		this.ballY = ((int)(this.windowHeight / 2)) - ((int)(this.ballDiameter/2));
		
		if(playtypeHor) {
			if(Math.round(Math.random()) == 1)
				this.ballMovementX = 1;
			else
				this.ballMovementX = -1;
			this.ballMovementY = Math.random();
		} else {
			this.ballMovementX = Math.random();
			if(Math.round(Math.random()) == 1)
				this.ballMovementY = 1;
			else
				this.ballMovementY = -1;
		}
		
		gameWaiting = true;
		
	}
	
	public void paint(Graphics g) {
		//g.drawString("i = "+i,10,20);
		g.setColor(Color.LIGHT_GRAY);
		g.drawString(this.score1 + "-" + this.score2, 10, 20);
		
		// Ball
		g.setColor(Color.white);
		//System.out.println("" + this.ballX +", " + this.ballY+", " + this.ballDiameter);
		g.fillOval(this.ballX, this.ballY, this.ballDiameter, this.ballDiameter);
		
		// Pong
		g.fillRect(this.pongOneX, this.pongOneY, this.pongLengthX, this.pongLengthY);
		g.fillRect(this.pongTwoX, this.pongTwoY, this.pongLengthX, this.pongLengthY);
		
		if(gameWaiting) {
			g.drawString("Trykk space for å starte", 50, 30);
		}
		
		//g.setColor(Color.black);
		//g.setFont(paintFont);
		//g.drawString(timeMsg, 0, 15);
		
		//g.fillOval(0, 20, 100, 100);
		
		//g.setColor(Color.white);
		//g.fillOval(3, 23, 94, 94);
		
		//g.setColor(Color.red);
		//g.fillArc(2, 22, 96, 96, 90, -arcLen);
	}
	
	public void gameWon (int player) {
		if(player == 1) {
			this.score1++;
		} else if (player == 2) {
			this.score2++;
		}
		this.init();
	}
	
	

	private class TheGameListener implements KeyListener {

		public void keyPressed(KeyEvent e) {
			if (playtypeHor && e.getKeyCode() == 87) {
				// W = opp, player 1
				pongOneY -= pongMovement;
			} else if (playtypeHor && e.getKeyCode() == 83) {
				// S = ned, player 1
				pongOneY += pongMovement;
			} else if (!playtypeHor && e.getKeyCode() == 65) {
				// A = ventre, player 1
				pongOneX -= pongMovement;
			} else if (!playtypeHor && e.getKeyCode() == 68) {
				// D = høyre, player 1
				pongOneX += pongMovement;
			} else if (playtypeHor && e.getKeyCode() == 73) {
				// I = opp, player 2
				pongTwoY -= pongMovement;
			} else if (playtypeHor && e.getKeyCode() == 75) {
				// K = ned, player 2
				pongTwoY += pongMovement;
			} else if (!playtypeHor && e.getKeyCode() == 74) {
				// J = ventre, player 2
				pongTwoX -= pongMovement;
			} else if (!playtypeHor && e.getKeyCode() == 76) {
				// L = høyre, player 2
				pongTwoX += pongMovement;
			}
			
			if(e.getKeyCode() == 32)
				gameWaiting = false;
			
			
			if (e.getKeyCode() == 27) {
				// Escape, exit
				System.exit(1);
			}
			//System.out.println("key-thegame: " + e + " " + e.getKeyCode());
		}
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
}
