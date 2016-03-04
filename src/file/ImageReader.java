package file;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.stream.ImageInputStream;
public class ImageReader
{
	/**
	 * @param file the file convert
	 * @param savePath the directory to save to
	 * @param saveName the file name to save to
	 * @param saveExtension the extension to save as without the period
	 */
	public static void convert(File file, String savePath, String saveName, String saveExtension)
	{
		File saveFile = new File(savePath + "\\" + saveName + "." + saveExtension);
		try
		{
			ImageIO.write(read(file), saveExtension, saveFile);
		}
		catch (IOException e)
		{
			System.err.println("[ImageReader] Failed to convert (save) file (" + file.getAbsolutePath() + ")");
			e.printStackTrace();
		}
	}
	
	/**
	 * @param file the file to read from
	 * @return BufferedImage the output
	 */
	public static BufferedImage read(File file)
	{
		ImageInputStream input = null;
		BufferedImage image = null;
		try
		{
			input = ImageIO.createImageInputStream(file);
		}
		catch (IOException e)
		{
			System.err.println("[ImageReader] Cannot find Image (" + file.getAbsolutePath() + ")");
			e.printStackTrace();
		}
		try
		{
			Iterator<javax.imageio.ImageReader> readers = ImageIO.getImageReaders(input);
			
			if(!readers.hasNext())
			{
				throw new IllegalArgumentException("[ImageReader] No reader found for " + file.getAbsolutePath().split("\\.")[1]);
			}
			
			javax.imageio.ImageReader reader = readers.next();
			
			try
			{
				reader.setInput(input);
				ImageReadParam param = reader.getDefaultReadParam();
				image = reader.read(0, param);
			}
			catch (IOException e)
			{
				System.err.println("[ImageReader] Cannot read Image (" + file.getAbsolutePath() + ")");
				e.printStackTrace();
			}
			finally
			{
				reader.dispose();
			}
		}
		finally
		{
			try
			{
				input.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return image;
	}
	
	public static Image readThumbnail(File file, int width, int height)
	{
		ImageInputStream input = null;
		Image image = null;
		try
		{
			input = ImageIO.createImageInputStream(file);
		}
		catch (IOException e)
		{
			System.err.println("[ImageReader] Cannot find Image (" + file.getAbsolutePath() + ")");
			e.printStackTrace();
		}
		try
		{
			Iterator<javax.imageio.ImageReader> readers = ImageIO.getImageReaders(input);
			
			if(!readers.hasNext())
			{
				throw new IllegalArgumentException("[ImageReader] No reader found for " + file.getAbsolutePath().split("\\.")[1]);
			}
			
			javax.imageio.ImageReader reader = readers.next();
			
			try
			{
				reader.setInput(input);
				ImageReadParam param = reader.getDefaultReadParam();
				image = reader.read(0, param).getScaledInstance(100, 100, BufferedImage.SCALE_SMOOTH);
			}
			catch (IOException e)
			{
				System.err.println("[ImageReader] Cannot read Image (" + file.getAbsolutePath() + ")");
				e.printStackTrace();
			}
			finally
			{
				reader.dispose();
			}
		}
		finally
		{
			try
			{
				input.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return image;
	}
}
