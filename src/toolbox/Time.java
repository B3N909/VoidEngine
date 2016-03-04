package toolbox;

import org.lwjgl.Sys;

public class Time
{
	private static long previousTime;
	private static float frameTime;
	private static int frame;
	private static float fps;
	
	private static long lastFrameTime;
	private static float delta;
	
	public static final float GRAVITY = -50;
	
	public static void intialize()
	{
		previousTime = getTime();
		lastFrameTime = getCurrentTime();
	}
	
	public static void update()
	{
		frame++;
		int timeInterval = (int) (getTime() - previousTime);
		if(timeInterval > 1000)
		{
			fps = frame / (timeInterval / 1000f);
			previousTime = getTime();
			frame = 0;
			setFrameTime(0);
		}
		else
		{
			setFrameTime(getFrameTime() + timeInterval);
		}
	}
	
	public static float getDeltaFrameTimeSeconds()
	{
		return delta;
	}
	
	public static long getTime()
	{
		return Sys.getTime()*1000/Sys.getTimerResolution();
	}
	
	public static void updateLegacy()
	{
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime)/1000f;
		lastFrameTime = currentFrameTime;
	}
	
	public static long getCurrentTime()
	{
		return Sys.getTime()*1000/Sys.getTimerResolution();
	}
	
	public static int getFrame()
	{
		return frame;
	}
	
	public static float getFPS()
	{
		return fps;
	}

	public static float getFrameTime()
	{
		return frameTime;
	}

	public static void setFrameTime(float frameTime)
	{
		Time.frameTime = frameTime;
	}
	
}
