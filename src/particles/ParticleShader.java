package particles;

import org.lwjgl.util.vector.Matrix4f;

import shader.ShaderProgram;

/**
 * @author Luke Warlow
 * Edited by _Savant
 */
public class ParticleShader extends ShaderProgram
{
	public static String vertexShader;
	public static String fragmentShader;
	
	private int location_numberOfRows;
	private int location_projectionMatrix;

	public ParticleShader()
	{
		super(vertexShader, fragmentShader);
	}

	@Override
	protected void getAllUniformLocations()
	{
		location_numberOfRows = super.getUniformLocation("numberOfRows");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
	}

	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "modelViewMatrix");
		super.bindAttribute(5, "textureOffset");
		super.bindAttribute(6, "blendFactor");
	}

	protected void loadNumberOfRows(float numberOfRows)
	{
		super.loadFloat(location_numberOfRows, numberOfRows);
	}

	protected void loadProjectionMatrix(Matrix4f projectionMatrix)
	{
		super.loadMatrix(location_projectionMatrix, projectionMatrix);
	}
}