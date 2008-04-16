package gui;

import input.ButtonActuator;
import input.ButtonListener;
import input.MouseMoveListener;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.image.MemoryImageSource;

import renderer.RendererStatus;
import renderer.lwjglbinding.GLCanvas;
import timer.FrameRater;
import view.Camera;
import view.Display;

/*
 * SwingDealer.java
 *
 * Created on 23 de Fevereiro de 2006, 17:21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 * 
 * @author eu
 */
public class SwingDealer implements ComponentListener, ButtonActuator,
		java.awt.event.MouseListener {

	int border = 2;
	
	int x, y;

	int mx, my;

	boolean hiding = true;

	boolean shown = false;

	int[] pixels = new int[16 * 16];

	Image image = Toolkit.getDefaultToolkit().createImage(
			new MemoryImageSource(16, 16, pixels, 0, 16));

	Cursor transparentCursor = Toolkit.getDefaultToolkit().createCustomCursor(
			image, new Point(0, 0), "invisiblecursor");

	private MouseMoveListener mouseListener;

	private ButtonListener buttonListener;

	private GLCanvas canvas;

	private Cursor normal;

	
	RendererStatus rstatus;
	/** Creates a new instance of SwingDealer */
	public SwingDealer() {
	}

	private void showCursor() {
		mouseListener.setCapture(false);
		buttonListener.setCapture(false);
		canvas.setCursor(normal);
	}

	private void hideCursor() {
		//mouseListener.centerMouse();
		mouseListener.setCapture(true);
		buttonListener.setCapture(true);
		if (hiding)
		canvas.setCursor( transparentCursor );
		
	}

	public void setupDimensions(Component c) {
		x = c.getLocationOnScreen().x;
		y = c.getLocationOnScreen().y;

		mx = c.getWidth() / 2;
		my = c.getHeight() / 2;
		mouseListener.configureMouse(x, y, mx, my);

		canvas.setBounds(border, border, c.getWidth()-2*border, c.getHeight()-2*border);
		rstatus.getDisplay().setup(c.getWidth()-2*border,c.getHeight()-2*border);
	}

	
	
	// not working
	public void componentResized(ComponentEvent e) {
		setupDimensions(e.getComponent());
	}

	

	public void componentMoved(ComponentEvent e) {
		setupDimensions(e.getComponent());
	}

	public void componentShown(ComponentEvent e) {
		
	}

	public void componentHidden(ComponentEvent e) {
	}

	public void keyPressed(int key, FrameRater fr, Camera cr, Display d) {
		showCursor();
		buttonListener.reset();
	}

	public void keyReleased(int key, FrameRater fr, Camera cr, Display d) {
	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
		{
			hideCursor();
			buttonListener.reset();
		}
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public MouseMoveListener getMouseListener() {
		return mouseListener;
	}

	public void setMouseListener(MouseMoveListener mouseListener) {
		this.mouseListener = mouseListener;
	}

	public ButtonListener getButtonListener() {
		return buttonListener;
	}

	public void setButtonListener(ButtonListener actionListener) {
		this.buttonListener = actionListener;

	}

	public GLCanvas getCanvas() {
		return canvas;
	}

	public void setCanvas(GLCanvas canvas) {
		this.canvas = canvas;
		normal = canvas.getCursor();
	}

	public RendererStatus getRstatus() {
		return rstatus;
	}

	public void setRstatus(RendererStatus rstatus) {
		this.rstatus = rstatus;
	}

}
