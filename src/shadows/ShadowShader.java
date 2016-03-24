package shadows;

import org.lwjgl.util.vector.Matrix4f;

public class ShadowShader extends shader.ShaderProgram
{
	public static String vertexShader;
	public static String fragmentShader;
	
	private int location_mvpMatrix;

	protected ShadowShader()
	{
		super(vertexShader, fragmentShader);
	}

	@Override
	protected void getAllUniformLocations()
	{
		location_mvpMatrix = super.getUniformLocation("mvpMatrix");
		
	}
	
	protected void loadMvpMatrix(Matrix4f mvpMatrix){
		super.loadMatrix(location_mvpMatrix, mvpMatrix);
	}

	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "in_position");
	}
}
