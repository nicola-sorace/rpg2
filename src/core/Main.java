package core;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel implements KeyListener, MouseMotionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1250797138611754474L;
	
	public static final boolean DEBUG = false;
	
	JFrame window = new JFrame();
	public int width = 800;
	public int height = 600;
	
	public static final double R = 2; //Ratio between y difference and corresponding on-screen y difference.
	
	public static final int MAXFPS = 60;
	public static final long S = 1000000000; //One second
	public static final long TICK = S/MAXFPS;
	public long time = System.nanoTime();
	
	public GUI gui = new Game(800, 600);

	public static void main(String[] args) {
		new Main();
	}
	
	public Main(){
		
		setPreferredSize(new Dimension(width, height));
		
		Input.setDefaultBindings(); //TODO Customizable key bindings.
		
		window.add(this);
		window.addKeyListener(this);
		window.addMouseMotionListener(this);
		window.setTitle("RPG MK2");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);
		
		while(true){
			long d = System.nanoTime()-time;
			if(d>=TICK){
				for(int i=0; i<(int)((double)d/(double)TICK); i++){
					tick();
				}
				repaint();
				revalidate(); //Fixes lag!
				time = System.nanoTime();
			}
		}
		
	}
	
	@Override
	public void paint(Graphics g){
		gui.draw((Graphics2D)g);
	}
	
	public void tick(){
		gui.tick();
	}
	
	public void draw(Graphics2D g){
		gui.draw(g);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Input.press(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Input.release(e.getKeyCode());
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Input.setMouse(e.getX(), e.getY());
	}

}
