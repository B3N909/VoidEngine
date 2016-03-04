package entities;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import render.DisplayManager;
import toolbox.Maths;
import toolbox.Settings;

public class Camera
{
	private float distanceFromPlayer = 50;
	private float angleAroundPlayer = 0;
	
	private Vector3f position = new Vector3f(10, 5, 0);
	private float pitch, yaw, roll;
	
	private Player player;
	
	public Camera(Player player)
	{
		this.player = player;
	}
	
	public void move()
	{
		if(player.isFirstPerson())
		{
			//First Person
			position = new Vector3f(player.getPosition().x, player.getPosition().y + 10, player.getPosition().z);
			this.yaw = 180 - player.getRotY();
			this.pitch -= Mouse.getDY() * DisplayManager.getFrameTimeSeconds() * Settings.sensitivity;
		}
		else
		{
			//Third Person
			calculateZoom();
			calculateAngles();
			float horizontalDistance = calculateHorizontalDistance();
			float verticalDistance = calculateVerticalDistance();
			calculateCamearPosition(horizontalDistance, verticalDistance);
			this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
		}
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public float getPitch()
	{
		return pitch;
	}

	public float getYaw()
	{
		return yaw;
	}

	public float getRoll()
	{
		return roll;
	}
	
	
	public void invertPitch()
	{
		pitch = -pitch;
	}
	
	public void invertRoll()
	{
		roll = -roll;
	}
	
	private void calculateZoom()
	{
		float zoomLevel = Mouse.getDWheel() * 0.025f;
		distanceFromPlayer += zoomLevel;
		distanceFromPlayer = Maths.clamp((int)distanceFromPlayer, 15, 25);
	}
	
	private void calculateAngles()
	{
		float pitchChange = Mouse.getDY() * DisplayManager.getFrameTimeSeconds() * Settings.sensitivity;
		pitch -= pitchChange;
	}
	
	private float calculateHorizontalDistance()
	{
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	private float calculateVerticalDistance()
	{
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	private void calculateCamearPosition(float horizontalDistance, float verticalDistance)
	{
		float theta = player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = (player.getPosition().y + 10) + verticalDistance;
	}
	
}
