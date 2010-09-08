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

public class runner_test {
	public static void main(String[] args) {
		boolean playtypeHor = false;
		Double speed = 6.0;
		int pongLength = 50;
		int pongWidth = 10;
		int windowHeight = 468;
		int windowWidth = 216;
		int ballDiameter = 10;
		PongResizable game = new PongResizable(
				playtypeHor, speed,
				pongLength, pongWidth,
				windowHeight, windowWidth,
				ballDiameter);
	}
}
