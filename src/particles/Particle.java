package particles;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import render.DisplayManager;
import toolbox.Time;
import entities.Camera;
import entities.Player;

/**
 * @author Luke Warlow
 *
 */

public class Particle
{
	private Vector3f position;
	private Vector3f velocity;
	private float gravityEffect;
	private float lifeLength;
	private float rotation;
	private float scale;

	private ParticleTexture texture;

	private Vector2f textureOffset1 = new Vector2f();
	private Vector2f textureOffset2 = new Vector2f();
	private float blend;

	private float elapsedTime = 0;
	private float distance;

	// global for performance reasons
	private Vector3f reusableChange = new Vector3f();

	public Particle(ParticleTexture texture, Vector3f position, Vector3f velocity, float gravityEffect, float lifeLength, float rotation, float scale)
	{
		this.texture = texture;
		this.position = position;
		this.velocity = velocity;
		this.gravityEffect = gravityEffect;
		this.lifeLength = lifeLength;
		this.rotation = rotation;
		this.scale = scale;
		ParticleMaster.addParticle(this);
	}
	
	/**
	 * @return the textureOffset1
	 */
	public Vector2f getTextureOffset1()
	{
		return textureOffset1;
	}

	/**
	 * @return the textureOffset2
	 */
	public Vector2f getTextureOffset2()
	{
		return textureOffset2;
	}

	/**
	 * @return the blend
	 */
	public float getBlend()
	{
		return blend;
	}

	/**
	 * @return the position
	 */
	public Vector3f getPosition()
	{
		return position;
	}

	/**
	 * @return the rotation
	 */
	public float getRotation()
	{
		return rotation;
	}

	/**
	 * @return the scale
	 */
	public float getScale()
	{
		return scale;
	}

	/**
	 * @return the texture
	 */
	public ParticleTexture getTexture()
	{
		return texture;
	}

	/**
	 * @return the distance
	 */
	public float getDistance()
	{
		return distance;
	}

	protected boolean update(Camera camera)
	{
		velocity.y += Time.GRAVITY * gravityEffect * DisplayManager.getFrameTimeSeconds();
		reusableChange.set(velocity);
		reusableChange.scale(DisplayManager.getFrameTimeSeconds());
		Vector3f.add(reusableChange, position, position);
		distance = Vector3f.sub(camera.getPosition(), position, null).lengthSquared();
		updateTextureCoordInfo();
		elapsedTime += DisplayManager.getFrameTimeSeconds();
		return elapsedTime < lifeLength;
	}

	private void updateTextureCoordInfo()
	{
		float lifeFactor = elapsedTime / lifeLength;
		int stageCount = texture.getNumberOfRows() * texture.getNumberOfRows();
		float atlasProgression = lifeFactor * stageCount;
		int index1 = (int) Math.floor(atlasProgression);
		int index2 = index1 < stageCount - 1 ? index1 + 1 : index1;
		this.blend = atlasProgression % 1;
		setTextureOffset(textureOffset1, index1);
		setTextureOffset(textureOffset2, index2);
	}

	private void setTextureOffset(Vector2f offset, int index)
	{
		int coloumn = index % texture.getNumberOfRows();
		int row = index % texture.getNumberOfRows();
		offset.x = (float) coloumn / texture.getNumberOfRows();
		offset.y = (float) row / texture.getNumberOfRows();
	}
}