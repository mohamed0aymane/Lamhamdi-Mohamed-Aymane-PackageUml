package org.mql.java.ui.shapes;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle implements Shape{
	private int x;
	private int y;
	private int width;
	private int height;
	public Rectangle(int x, int y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
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
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.black);
	}
//	Si value est faux, c'est une aggrégation. Sinon, c'est une composition.
	public void draw(Graphics g,boolean fillshape) {
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
		if (fillshape == true) {
			g.fillRect(x, y, width, height);
		}
	}
	
}
