package particles;

/**
 * @author Luke Warlow
 *
 */
public class ParticleTexture
{
	private int textureID;
	private int numberOfRows;
	private boolean additive;
	
	public ParticleTexture(int textureID, int numberOfRows, boolean additive)
	{
		this.textureID = textureID;
		this.numberOfRows = numberOfRows;
		this.additive = additive;
	}
	
	public boolean usesAdditiveBlending()
	{
		return additive;
	}

	/**
	 * @return the textureID
	 */
	public int getTextureID()
	{
		return textureID;
	}

	/**
	 * @return the numberOfRows
	 */
	public int getNumberOfRows()
	{
		return numberOfRows;
	}
}