package engine;

import file.FileManager;
import font.FontShader;
import font.TextMaster;
import gui.GuiRenderer;
import gui.GuiShader;
import gui.GuiTexture;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import particles.ParticleMaster;
import particles.ParticleShader;
import render.DisplayManager;
import render.Loader;
import render.MasterRenderer;
import shader.NormalMappingShader;
import shader.StaticShader;
import shader.TerrainShader;
import shadows.ShadowShader;
import skybox.SkyboxShader;
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
		GameScript.universe = this;
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
		loader = new Loader();
		loadShaders();
		guiRenderer = new GuiRenderer(loader);
		renderer = new MasterRenderer(loader);
		waterBuffer = new WaterFrameBuffers();
		WaterShader waterShader = new WaterShader();
		waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), waterBuffer);
		TextMaster.init(loader);
		ParticleMaster.init(loader, renderer.getProjectionMatrix());
		
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		loadingTexture = new GuiTexture(loader, "res/loadingScreen_x2.png", new Vector2f(0f, 0f), new Vector2f(1f, 1f));
		guis.add(loadingTexture);
		
		System.out.println("[Loading] Rendered Loading Screen");
		guiRenderer.render(guis);
		display.updateDisplay();
	}
	
	private void loadShaders()
	{
		//Setup Shader Locations
		StaticShader.vertexFile = "src/shaders/vertexShader.txt";
		StaticShader.fragmentFile = "src/shaders/fragmentShader.txt";
		
		TerrainShader.vertexFile = "src/shaders/terrainVertexShader.txt";
		TerrainShader.fragmentFile = "src/shaders/terrainFragmentShader.txt";
		
		GuiShader.vertexFile = "src/shaders/guiVertexShader.txt";
		GuiShader.fragmentFile = "src/shaders/guiFragmentShader.txt";
		
		SkyboxShader.vertexShader = "src/shaders/skyboxVertexShader.txt";
		SkyboxShader.fragmentShader = "src/shaders/skyboxFragmentShader.txt";
		
		WaterShader.vertexShader = "src/shaders/waterVertex.txt";
		WaterShader.fragmentShader = "src/shaders/waterFragment.txt";
		
		NormalMappingShader.vertexFile = "src/shaders/vertexNormalShader.txt";
		NormalMappingShader.fragmentFile = "src/shaders/fragmentNormalShader.txt";
		
		FontShader.vertexShader = "src/shaders/fontVertex.txt";
		FontShader.fragmentShader = "src/shaders/fontFragment.txt";
		
		ParticleShader.vertexShader = "src/shaders/particleVertex.txt";
		ParticleShader.fragmentShader = "src/shaders/particleFragment.txt";
		
		ShadowShader.vertexShader = "src/shaders/shadowVertexShader.txt";
		ShadowShader.fragmentShader = "src/shaders/shadowFragmentShader.txt";
		
		//Setup Shader Textures
		WaterRenderer.DUDV_MAP = "res/dudvWaterMap.png";
		WaterRenderer.NORMAL_MAP = "res/normalWaterMap.png";
		
		//Setup Variables
		MasterRenderer.FOV = 90;
		MasterRenderer.NEAR_PLANE = 0.1f;
		MasterRenderer.FAR_PLANE = 1000f;
	}
	
	public void start()
	{
		System.out.println("[Loading] Finished Loading Assets");
		loadingTexture.setEnabled(false);
		gameEngine.start();
		GameScript.hardStart();
		while(!Display.isCloseRequested())
		{
			GameScript.hardUpdate();
			display.updateDisplay();
			GameScript.hardLateUpdate();
			gameEngine.update();
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
