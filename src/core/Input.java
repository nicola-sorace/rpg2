package core;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class Input {
	
	public static int mouseX = 0;
	public static int mouseY = 0;
	
	//Key indexes:
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int JUMP = 4;
	
	public static boolean[] pressed = new boolean[5];	//Stores whether each input is active.
	//public static int[] code = new int[5];	//TODO Stores key-bindings.
	
	public static Map<Integer, Integer> code = new HashMap<Integer, Integer>();
	
	public static void setDefaultBindings(){
		code.put(KeyEvent.VK_W, UP);
		code.put(KeyEvent.VK_S, DOWN);
		code.put(KeyEvent.VK_A, LEFT);
		code.put(KeyEvent.VK_D, RIGHT);
		code.put(KeyEvent.VK_SPACE, JUMP);
	}
	
	public static void press(int i){ //Note that 'i' here is raw keycode.
		if(code.containsKey(i))pressed[code.get(i)] = true;
	}
	
	public static void release(int i){
		if(code.containsKey(i))pressed[code.get(i)] = false;
	}
	
	public static void setMouse(int x, int y){
		mouseX = x;
		mouseY = y;
	}
}
