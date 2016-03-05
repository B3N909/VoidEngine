package gui;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import shader.ShaderProgram;

public class GuiShader extends ShaderProgram
{
	public static String vertexFile;
    public static String fragmentFile;
     
    private int location_transformationMatrix;
    private int location_scale;
    
    public GuiShader()
    {
        super(vertexFile, fragmentFile);
    }
     
    public void loadTransformation(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }
 
    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_scale = super.getUniformLocation("scale");
    }
 
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
    
    public void loadScale(Vector2f scale)
    {
    	super.loadVector(location_scale, scale);
    }
}
