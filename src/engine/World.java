package engine;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector4f;

import particles.ParticleMaster;
import render.MasterRenderer;
import shadows.ShadowMapMasterRenderer;
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
	
	MasterRenderer renderer;
	GuiRenderer guiRenderer;
	WaterRenderer waterRenderer;
	WaterFrameBuffers fbos;
	
	List<Entity> entities = new ArrayList<Entity>();
	List<Terrain> terrains = new ArrayList<Terrain>();
	List<Light> lights = new ArrayList<Light>();
	List<GuiTexture> guis = new ArrayList<GuiTexture>();
	List<WaterTile> waters = new ArrayList<WaterTile>();
	
	Camera camera;
	Player player;
	Light sun;
	
	public World(Camera camera, Player player, Light sun)
	{
		this.instance = GameEngine.getInstance();
		this.renderer = instance.getRenderer();
		this.guiRenderer = instance.getGuiRenderer();
		this.waterRenderer = instance.getWaterRenderer();
		this.fbos = instance.getWaterBuffer();
		GameScript.world = this;
		
		this.camera = camera;
		this.player = player;
		this.sun = sun;
		this.lights.add(sun);
		this.entities.add(player);
	}
	
	public void createShadowRenderer()
	{
		this.renderer.shadowRenderer = new ShadowMapMasterRenderer(camera);
	}
	
	public void update()
	{
		//Render Particles
		ParticleMaster.update(camera);
		
		//Move Player & Camera
		camera.move();
		player.move(terrains.get(0));
		
		//Render Shadows
		renderer.renderShadowMap(entities, sun);
		
		//Render Waters
		for(WaterTile water : waters)
		{
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
			GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
			fbos.unbindCurrentFrameBuffer();
		}
		//Render to Screen
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
	
	public void spawn(Entity entity)
	{
		entities.add(entity);
	}
	
	public void spawn(Terrain terrain)
	{
		terrains.add(terrain);
	}
	
	public void spawn(Light light)
	{
		lights.add(light);
	}
	
	public void spawn(GuiTexture gui)
	{
		guis.add(gui);
	}
	
	public void spawn(WaterTile water)
	{
		waters.add(water);
	}
}
