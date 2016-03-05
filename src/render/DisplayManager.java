package render;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager
{
	public static final int WIDTH = 1080;
	public static final int HEIGHT = 720;
	public static final int FPS_CAP = 300;
	
	private static long lastFrameTime;
	private static float delta;
	
	private String gameName;
	
	public DisplayManager(String gameName)
	{
		this.gameName = gameName;
	}
	
	public void createDisplay()
	{
		ContextAttribs attribs = new ContextAttribs(4,0)
			.withForwardCompatible(true)
			.withProfileCore(true);
		
		try
		{
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle(gameName);
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, WIDTH, HEIGHT); //Where to render game inside Window
		lastFrameTime = getCurrentTime();
	}
	
	public void updateDisplay()
	{
		Display.sync(FPS_CAP);
		Display.update();
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime) / 1000f;
		lastFrameTime = currentFrameTime;
	}
	
	public static float getFrameTimeSeconds()
	{
		return delta;
	}
	
	public void closeDisplay()
	{
		Display.destroy();
	}
	
	private static long getCurrentTime()
	{
		return Sys.getTime() * 1000/ Sys.getTimerResolution();
	}
	
}
