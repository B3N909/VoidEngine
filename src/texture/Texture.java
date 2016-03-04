package texture;

public class Texture
{
	private int textureID;
	private int normalID;
	
	private int numberOfRows = 1;
	
	private float specularDamper = 1;
	private float specular = 0;
	
	private boolean hasTransparency = false;
	private boolean useFakeLighting = false;
	
	public Texture(int id)
	{
		this.textureID = id;
	}
	
	public int getID()
	{
		return textureID;
	}
	
	public int getNormalMap()
	{
		return normalID;
	}
	
	public void setNormalMap(int normalID)
	{
		this.normalID = normalID;
	}
	
	public float getSpecularDamper()
	{
		return specularDamper;
	}

	public void setSpecularDamper(float specularDamper)
	{
		this.specularDamper = specularDamper;
	}

	public float getSpecular()
	{
		return specular;
	}

	public void setSpecular(float specular)
	{
		this.specular = specular;
	}
	
	public boolean isTransparent()
	{
		return hasTransparency;
	}

	public void setTransparency(boolean hasTransparency)
	{
		this.hasTransparency = hasTransparency;
	}
	
	public boolean isFakeLighting()
	{
		return useFakeLighting;
	}
	
	public void setFakeLighting(boolean useFakeLighting)
	{
		this.useFakeLighting = useFakeLighting;
	}
	
	public int getNumberOfRows()
	{
		return numberOfRows;
	}
	
	public void setNumberOfRows(int numberOfRows)
	{
		this.numberOfRows = numberOfRows;
	}

}
