package engine;

import file.FileManager;
import font.TextMaster;
import gui.GuiRenderer;
import gui.GuiTexture;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import particles.ParticleMaster;
import render.DisplayManager;
import render.Loader;
import render.MasterRenderer;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;

public class Universe
{
	private DisplayManager display;
	private GameEngine gameEngine;
	
	public Universe(DisplayManager display, GameEngine gameEngine)
	{
		this.display = display;
		this.gameEngine = gameEngine;
	}
	
	public Loader loader; 
	public GuiRenderer guiRenderer;
	public MasterRenderer renderer;
	public WaterRenderer waterRenderer;
	public WaterFrameBuffers waterBuffer;
	public FileManager converter;
		
	GuiTexture loadingTexture;
	public void ready()
	{
		GameScript.hardStart();
		loader = new Loader();
		guiRenderer = new GuiRenderer(loader);
		renderer = new MasterRenderer(loader);
		waterBuffer = new WaterFrameBuffers();
		WaterShader waterShader = new WaterShader();
		waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), waterBuffer);
		TextMaster.init(loader);
		ParticleMaster.init(loader, renderer.getProjectionMatrix());
		
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		loadingTexture = new GuiTexture(loader.loadTexture("res/loadingScreen.png"), new Vector2f(0f, 0f), new Vector2f(1f, 1f));
		guis.add(loadingTexture);
		
		System.out.println("[Loading] Rendered Loading Screen");
		guiRenderer.render(guis);
		display.updateDisplay();
	}
	
	public void start()
	{
		System.out.println("[Loading] Finished Loading Assets");
		loadingTexture.setEnabled(false);
		gameEngine.start();					
		while(!Display.isCloseRequested())
		{
			GameScript.hardUpdate();
			gameEngine.update();
			display.updateDisplay();
			GameScript.hardLateUpdate();
		}
		ParticleMaster.cleanUp();
		TextMaster.cleanUp();
		gameEngine.cleanUp();
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		display.closeDisplay();
		GameScript.hardStop();
	}
	
	public void syncData()
	{
		if(converter == null)
			converter = new FileManager();
		converter.check();
	}
	
	public void setFileManager(FileManager converter)
	{
		this.converter = converter;
	}
}
