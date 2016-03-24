package render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import shader.Shader;
import shader.StaticShader;
import shader.TerrainShader;
import shadows.ShadowMapMasterRenderer;
import skybox.SkyboxRenderer;
import terrain.Terrain;
import water.WaterRenderer;
import entities.Camera;
import entities.Entity;
import entities.Light;

public class MasterRenderer
{
	private StaticShader shader = new StaticShader();
	private EntityRenderer renderer;
	
	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	
	public static float FOV = 70;
	public static float NEAR_PLANE = 0.2f;
	public static float FAR_PLANE = 250;
	
	//0.0, 0.3, 0.5   OLD: 0.5, 0.5, 0.5
	public static final float RED = 0.85f;
	public static final float GREEN = 0.85f;
	public static final float BLUE = 0.85f;
	
	private Matrix4f projectionMatrix = null;
		
	private TerrainRenderer terrainRenderer;
	private TerrainShader terrainShader = new TerrainShader();
	private SkyboxRenderer skyboxRenderer;
	private NormalMappingRenderer normalRenderer;
	public ShadowMapMasterRenderer shadowRenderer;
	
	public MasterRenderer(Loader loader)
	{
		setCulling(true);
		createProjectionMatrix();
		renderer = new EntityRenderer(shader, projectionMatrix);
		terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
		skyboxRenderer = new SkyboxRenderer(loader, projectionMatrix);
		normalRenderer = new NormalMappingRenderer(projectionMatrix);
	}
	
	public static void setCulling(boolean value)
	{
		if(value)
		{
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glCullFace(GL11.GL_BACK);
		}
		else
		{
			GL11.glDisable(GL11.GL_CULL_FACE);
		}
	}
	
	public void renderScene(List<Entity> entities, List<Terrain> terrains, List<Light> lights, Camera camera, Vector4f clipPlane)
	{
		for(Terrain terrain : terrains)
		{
			processTerrain(terrain);
		}
		for(Entity entity : entities)
		{
			processEntity(entity);
		}
		render(lights, camera, clipPlane);
	}
	
	public void render(List<Light> lights, Camera camera, Vector4f clipPlane)
	{
		Map<TexturedModel, List<Entity>> defaultEntities = new HashMap<TexturedModel, List<Entity>>();
		Map<TexturedModel, List<Entity>> normalEntities = new HashMap<TexturedModel, List<Entity>>();
		
		int i = 0;
		for(List<Entity> entitys : entities.values())
		{
			Entity entity = entitys.get(0);
			if(entity.getShader() == Shader.DEFAULT)
			{
				defaultEntities.put(entities.keySet().toArray(new TexturedModel[entities.size()])[i], entitys);
			}
			if(entity.getShader() == Shader.NORMAL)
			{
				normalEntities.put(entity.getModel(), entitys);
			}
			i++;
		}
		
		prepare();
		
		//Default Shader
		shader.start();
		shader.loadClipPlane(clipPlane);
		shader.loadSkyColor(RED, GREEN, BLUE);
		shader.loadLights(lights);
		shader.loadViewMatrix(camera);
		renderer.render(defaultEntities);
		shader.stop();
		
		normalRenderer.render(normalEntities, clipPlane, lights, camera);
		
		terrainShader.start();
		terrainShader.loadClipPlane(clipPlane);
		terrainShader.loadSkyColor(RED, GREEN, BLUE);
		terrainShader.loadLights(lights);
		terrainShader.loadViewMatrix(camera);
		terrainRenderer.render(terrains);
		terrainShader.stop();
		skyboxRenderer.render(camera, RED, GREEN, BLUE);
		terrains.clear();
				
		entities.clear();
	}
	
	public void processTerrain(Terrain terrain)
	{
		terrains.add(terrain);
	}
	
	public void processEntity(Entity entity)
	{
		TexturedModel model = entity.getModel();
		List<Entity> batch = entities.get(model);
		if(batch != null)
		{
			batch.add(entity);
		}
		else
		{
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(model, newBatch);
		}
	}
	
	public void prepare()
	{
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(RED, GREEN, BLUE, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public void renderShadowMap(List<Entity> entityList, Light sun)
	{
		if(shadowRenderer == null)
			return;
		for(Entity entity : entityList)
		{
			processEntity(entity);
		}
		shadowRenderer.render(entities, sun);
		entities.clear();
	}
	
	public int getShadowMapTexture()
	{
		return shadowRenderer.getShadowMap();
	}
	
	public void cleanUp()
	{
		shader.cleanUp();
		terrainShader.cleanUp();
		normalRenderer.cleanUp();
		shadowRenderer.cleanUp();
	}
	
	public Matrix4f getProjectionMatrix()
	{
		if(projectionMatrix == null)
			createProjectionMatrix();
		return projectionMatrix;
	}
	
	private void createProjectionMatrix()
	{
		projectionMatrix = new Matrix4f();
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;
		
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
	}
}
