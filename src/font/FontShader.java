package font;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import shader.ShaderProgram;

public class FontShader extends ShaderProgram
{

	public static String vertexShader;
	public static String fragmentShader;
	
	private int location_color;
	private int location_translation;
	private int location_hasDropshadow;
	
	
	public FontShader()
	{
		super(vertexShader, fragmentShader);
	}

	@Override
	protected void getAllUniformLocations()
	{
		location_color = super.getUniformLocation("color");
		location_translation = super.getUniformLocation("translation");
		location_hasDropshadow = super.getUniformLocation("hasDropshadow");
	}

	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}
	
	protected void loadColor(Vector3f color)
	{
		super.loadVector(location_color, color);
	}
	
	protected void loadTranslation(Vector2f translation)
	{
		super.loadVector(location_translation, translation);
	}
	
	protected void setDropshadow(boolean value)
	{
		super.loadBoolean(location_hasDropshadow, value);
	}


}
