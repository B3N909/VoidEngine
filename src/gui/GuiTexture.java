package gui;

import java.awt.Image;
import java.io.File;
import java.util.UUID;

import org.lwjgl.util.vector.Vector2f;

import render.Loader;
import engineui.GuiListRenderer;
import engineui.GuiManager;
import file.ImageReader;

public class GuiTexture
{
	private int texture;
	private Image thumbnail;
	private Vector2f position;
	private Vector2f scale;
	private boolean enabled = true;
	private String textureName;
	private UUID uuid;
	
	public GuiTexture(Loader loader, String textureName, Vector2f position, Vector2f scale)
	{
		this.texture = loader.loadTexture(textureName);
		this.textureName = textureName;
		this.thumbnail = ImageReader.readThumbnail(new File(textureName), 100, 100);
		this.position = position;
		this.scale = scale;
		this.uuid = UUID.randomUUID();
		GuiListRenderer.addIcons(thumbnail, textureName, uuid);
		GuiManager.addEntry(this);
	}
	
	public GuiTexture(int texture, Vector2f position, Vector2f scale)
	{
		this.texture = texture;
		this.textureName = "internal file";
		this.thumbnail = ImageReader.readThumbnail(new File("res/Engine/notFound.png"), 100, 100);
		this.position = position;
		this.scale = scale;
		this.uuid = UUID.randomUUID();
		GuiListRenderer.addIcons(thumbnail, textureName, uuid);
		GuiManager.addEntry(this);
	}
	
	public void setTexture(int texture)
	{
		this.texture = texture;
	}
	
	public UUID getUUID()
	{
		return uuid;
	}
	
	public String getTextureName()
	{
		return textureName;
	}

	public int getTexture()
	{
		return texture;
	}

	public Vector2f getPosition()
	{
		return position;
	}

	public Vector2f getScale()
	{
		return scale;
	}
	
	public void setPosition(Vector2f position)
	{
		this.position = position;
	}
	
	public void setScale(Vector2f scale)
	{
		this.scale = scale;
	}
	
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
	
	public boolean isEnabled()
	{
		return enabled;
	}
}
