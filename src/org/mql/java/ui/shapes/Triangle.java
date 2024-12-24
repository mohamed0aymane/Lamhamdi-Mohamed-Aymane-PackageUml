package org.mql.java.ui.shapes;



import java.awt.Color;
import java.awt.Graphics;

public class Triangle {
	private int x;
	private int y;
	private int width;
	private int height;

	public Triangle(int x, int width, int height) {
		super();
		this.x = x;
		this.y = x;
		this.width = width;
		this.height = height;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}

	public void draw(Graphics g) {
		int x [] = {1,2,3};
		int y [] = {1,2,3};
		int height = 3;

		g.drawPolygon(x, y,3);
		g.fillPolygon(x, y, height);
	}
	
	public void drawTriangle(Graphics g) {
		g.drawRect(x, y, width, height);
		g.setColor(Color.BLACK);
	}

}
