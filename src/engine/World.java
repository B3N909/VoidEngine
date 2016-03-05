package engine;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector4f;

import particles.ParticleMaster;
import render.MasterRenderer;
import terrain.Terrain;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterTile;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import font.TextMaster;
import gui.GuiRenderer;
import gui.GuiTexture;

public class World
{
	GameEngine instance;
	
	List<Entity> entities = new ArrayList<Entity>();
	List<Terrain> terrains = new ArrayList<Terrain>();
	List<Light> lights = new ArrayList<Light>();
	List<GuiTexture> guis = new ArrayList<GuiTexture>();
	List<WaterTile> waters = new ArrayList<WaterTile>();
	
	MasterRenderer renderer;
	GuiRenderer guiRenderer;
	WaterRenderer waterRenderer;
	
	Camera camera;
	Light sun;
	WaterTile water;
	Player player;
	
	public Camera getCamera()
	{
		return camera;
	}
	
	public Light getSun()
	{
		return sun;
	}
	
	public void setCamera(Camera camera)
	{
		this.camera = camera;
	}
	
	public void setLight(Light sun)
	{
		this.sun = sun;
	}
	
	public WaterTile getWater()
	{
		return water;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public void setPlayer(Player player)
	{
		this.player = player;
	}
	
	public void setWater(WaterTile water)
	{
		this.water = water;
	}
	
	public World(MasterRenderer renderer, GuiRenderer guiRenderer, WaterRenderer waterRenderer)
	{
		this.renderer = renderer;
		this.guiRenderer = guiRenderer;
		this.waterRenderer = waterRenderer;
		instance = GameEngine.getInstance();
		fbos = instance.getWaterBuffer();
		GameScript.world = this;
	}
	
	WaterFrameBuffers fbos;
	
	public void update()
	{
		ParticleMaster.update(camera);
		//TODO: Change Water Reflections
		camera.move();
		player.move(getTerrain());

		GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
		
		//Render Reflection Texture
		fbos.bindReflectionFrameBuffer();
		float distance = 2 * (camera.getPosition().y - water.getHeight());
		camera.getPosition().y -= distance;
		camera.invertPitch();
		camera.invertRoll();
		renderScene(camera, new Vector4f(0, 1, 0, -water.getHeight() + 1.5f));
		camera.getPosition().y += distance;
		camera.invertPitch();
		camera.invertRoll();
		
		//Render Refraction Texture
		fbos.bindRefractionFrameBuffer();
		renderScene(camera, new Vector4f(0, -1, 0, water.getHeight() + 0.25f));
		
		//Render to Screen
		GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
		fbos.unbindCurrentFrameBuffer();
		render(camera, new Vector4f(0, -1, 0, 100000), sun);
	}
	
	private void render(Camera camera, Vector4f clippingPlane, Light sun)
	{
		renderer.renderScene(entities, terrains, lights, camera, clippingPlane);
		waterRenderer.render(waters, camera, sun);
		guiRenderer.render(guis);
		TextMaster.render();
		ParticleMaster.renderParticles(camera);
	}
	
	private void renderScene(Camera camera, Vector4f clippingPlane)
	{
		renderer.renderScene(entities, terrains, lights, camera, clippingPlane);
	}
	
	private void renderScene(Camera camera)
	{
		renderer.renderScene(entities, terrains, lights, camera, new Vector4f(0, 1, 0, 0));
	}
	
	public void instantiate(Entity entity)
	{
		entities.add(entity);
	}
	
	public void instantiate(Terrain terrain)
	{
		terrains.add(terrain);
	}
	
	public void instantiate(Light light)
	{
		lights.add(light);
	}
	
	public void instantiate(GuiTexture gui)
	{
		guis.add(gui);
	}
	
	public void instantiate(WaterTile water)
	{
		waters.add(water);
	}
	
	public Terrain getTerrain()
	{
		return terrains.get(0);
	}
	
}
