package entities;

import models.TexturedModel;

import org.lwjgl.util.vector.Vector3f;

public class GameObject extends Entity
{
	public GameObject(TexturedModel model, int textureIndex, Vector3f position, float rotX, float rotY, float rotZ, float scale)
	{
		super(model, textureIndex, position, rotX, rotY, rotZ, scale);
	}
	
	public GameObject(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale)
	{
		super(model, position, rotX, rotY, rotZ, scale);
	}

}
