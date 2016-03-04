package engine;

import gui.GuiRenderer;
import render.DisplayManager;
import render.Loader;
import render.MasterRenderer;
import water.WaterFrameBuffers;
import water.WaterRenderer;

public abstract class GameEngine
{
	private DisplayManager display;
	private Universe universe;
	
	private String gameName;
	
	private static GameEngine instance;
	
	public static GameEngine getInstance()
	{
		return instance;
	}
	
	public GameEngine(String gameName)
	{
		instance = this;
		
		this.gameName = gameName;
		
		display = new DisplayManager(gameName);
		universe = new Universe(display, this);
	}
	
	public String getGameName()
	{
		return gameName;
	}
	
	public DisplayManager getDisplay()
	{
		return display;
	}
	
	public Universe getUniverse()
	{
		return universe;
	}
	
	public Loader getLoader()
	{
		return universe.loader;
	}
	
	public MasterRenderer getRenderer()
	{
		return universe.renderer;
	}
	
	public GuiRenderer getGuiRenderer()
	{
		return universe.guiRenderer;
	}
	
	public WaterRenderer getWaterRenderer()
	{
		return universe.waterRenderer;
	}
	
	public WaterFrameBuffers getWaterBuffer()
	{
		return universe.waterBuffer;
	}
	
	public abstract void update();
	public abstract void start();
	public abstract void cleanUp();
}
