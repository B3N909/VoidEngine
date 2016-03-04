package entities;

import models.TexturedModel;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import render.DisplayManager;
import terrain.Terrain;
import toolbox.Settings;
import toolbox.Time;

public class Player extends Entity
{
	private static float RUN_SPEED = 20;
	private static float SPRINT_SPEED = 30;
	private static float STRAFE_SPEED = 12;
	private static float TURN_SPEED = 160;
	private static float JUMP_POWER = 30;
	
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;
	private float runSpeed = 0;
	
	private boolean isFirstPerson, fpRelease, inAir, isEscape, escapeRelease;
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale)
	{
		super(model, position, rotX, rotY, rotZ, scale);
		runSpeed = RUN_SPEED;
	}
	
	public void move(Terrain terrain)
	{
		checkInputs();
		if(!isFirstPerson)
		{
			//Third Person
			super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
			float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
			float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
			float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
			super.increasePosition(dx, 0, dz);
			super.increaseRotation(0, -Mouse.getDX() * DisplayManager.getFrameTimeSeconds() * Settings.sensitivity, 0);
		}
		else
		{
			//First Person
			
			float mouseDX = Mouse.getDX();
			super.increaseRotation(0, -mouseDX * DisplayManager.getFrameTimeSeconds() * Settings.sensitivity, 0);
			
			float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
			float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
			float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
			super.increasePosition(dx, 0, dz);
			
			float sideWaysDistance = currentTurnSpeed * DisplayManager.getFrameTimeSeconds();
			float dx2 = (float) (sideWaysDistance * Math.cos(Math.toRadians(super.getRotY())));
			float dz2 = (float) (sideWaysDistance * Math.sin(Math.toRadians(super.getRotY())));
			super.increasePosition(dx2, 0, -dz2);
		}
		
		//Global
		float terrainHeight = terrain.getTerrainHeight(super.getPosition().x, super.getPosition().z);
		upwardsSpeed += Time.GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		if(super.getPosition().y < terrainHeight)
		{
			upwardsSpeed = 0;
			inAir = false;
			super.getPosition().y = terrainHeight;
		}
		if(!isEscape)
			Mouse.setGrabbed(true);
		else
			Mouse.setGrabbed(false);
	}
	
	private void jump()
	{
		if(!inAir)
		{
			this.upwardsSpeed = JUMP_POWER;
			inAir = true;
		}
	}
	
	private void checkInputs()
	{
		
		//First Person Toggle Key
		if(Keyboard.isKeyDown(Keyboard.KEY_F5))
		{
			if(!fpRelease)
			{
				isFirstPerson = !isFirstPerson;
				fpRelease = true;
			}
		}
		else
		{
			fpRelease = false;
		}
		
		if(!isFirstPerson)
		{
			//Third Person
			if(Keyboard.isKeyDown(Keyboard.KEY_W))
			{
				currentSpeed = RUN_SPEED;
			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_S))
			{
				currentSpeed = -RUN_SPEED;
			}
			else
			{
				currentSpeed = 0;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_D))
			{
				currentTurnSpeed = -TURN_SPEED;
			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_A))
			{
				currentTurnSpeed = TURN_SPEED;
			}
			else
			{
				currentTurnSpeed = 0;
			}
		}
		else
		{
			//First Person
			if(Keyboard.isKeyDown(Keyboard.KEY_W))
			{
				currentSpeed = RUN_SPEED;
			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_S))
			{
				currentSpeed = -RUN_SPEED;
			}
			else
			{
				currentSpeed = 0;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_D))
			{
				currentTurnSpeed = -STRAFE_SPEED;
			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_A))
			{
				currentTurnSpeed = STRAFE_SPEED;
			}
			else
			{
				currentTurnSpeed = 0;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))
		{
			jump();
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
		{
			RUN_SPEED = SPRINT_SPEED;
		}
		else
		{
			RUN_SPEED = runSpeed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
		{
			if(!escapeRelease)
			{
				isEscape = !isEscape;
				escapeRelease = true;
			}
		}
		else
		{
			escapeRelease = false;
		}
	}
	
	public boolean isFirstPerson()
	{
		return isFirstPerson;
	}

}
