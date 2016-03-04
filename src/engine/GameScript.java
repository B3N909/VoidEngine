package engine;

import java.util.ArrayList;
import java.util.List;

import render.Loader;

public abstract class GameScript
{
	private static List<GameScript> scripts = new ArrayList<GameScript>();
	public static Universe universe;
	
	public GameScript()
	{
		scripts.add(this);
	}
	
	//Updated First
	public static void hardUpdate()
	{
		for(GameScript script : scripts)
		{
			script.update();
		}
	}
	
	//Updated Last
	public static void hardLateUpdate()
	{
		for(GameScript script : scripts)
		{
			script.lateUpdate();
		}
	}
	
	//Start
	public static void hardStart()
	{
		for(GameScript script : scripts)
		{
			script.start();
		}
	}
	
	//Last Stop
	public static void hardStop()
	{
		for(GameScript script : scripts)
		{
			script.stop();
		}
	}
	
	public Loader getLoader()
	{
		return universe.loader;
	}
	
	public abstract void update();
	public abstract void lateUpdate();
	public abstract void start();
	public abstract void stop();
}
