package file;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import engineui.TextureListRenderer;

public class FileManager
{	
	private String res;
	
	Map<String, Image> savedImages = new HashMap<String, Image>();
	
	public FileManager()
	{
		System.out.println("[FileManager] Caching Local Files");
		initSave();
	}
	
	public void check()
	{
		res = System.getProperty("user.dir") + "\\res";
		
		List<File> files = new ArrayList<File>();
		listFiles(res, files);
		
		for(File file : files)
		{
			File upDirectory = new File(getContainingDirectory(file.getAbsolutePath()));
			if(hasFile(upDirectory, getFileName(file.getAbsolutePath()), "png"))
			{
				
			}
			else
			{
				if(getExtension(file.getAbsolutePath()).equalsIgnoreCase("psd"))
				{
					System.out.println("Converting " + getFileName(file.getAbsolutePath()) + ".psd to " + getFileName(file.getAbsolutePath()) + ".png");
					ImageReader.convert(file, getContainingDirectory(file.getAbsolutePath()), getFileName(file.getAbsolutePath()), "png");
				}
				if(getExtension(file.getAbsolutePath()).equalsIgnoreCase("tif"))
				{
					System.out.println("Converting " + getFileName(file.getAbsolutePath()) + ".tif to " + getFileName(file.getAbsolutePath()) + ".png");
					ImageReader.convert(file, getContainingDirectory(file.getAbsolutePath()), getFileName(file.getAbsolutePath()), "png");
				}
				if(getExtension(file.getAbsolutePath()).equalsIgnoreCase("jpg"))
				{
					System.out.println("Converting " + getFileName(file.getAbsolutePath()) + ".jpg to " + getFileName(file.getAbsolutePath()) + ".png");
					ImageReader.convert(file, getContainingDirectory(file.getAbsolutePath()), getFileName(file.getAbsolutePath()), "png");
				}
				if(getExtension(file.getAbsolutePath()).equalsIgnoreCase("jpeg"))
				{
					System.out.println("Converting " + getFileName(file.getAbsolutePath()) + ".jpeg to " + getFileName(file.getAbsolutePath()) + ".png");
					ImageReader.convert(file, getContainingDirectory(file.getAbsolutePath()), getFileName(file.getAbsolutePath()), "png");
				}
			}
		}
	}
	
	public void initSave()
	{
		res = System.getProperty("user.dir") + "\\res";
		List<File> files = new ArrayList<File>();
		listFiles(res, files);
		for(File file : files)
		{
			if(getExtension(file.getAbsolutePath()).equalsIgnoreCase("png"))
			{
				searchedFiles.add(getFileName(file.getAbsolutePath()) + "." + getExtension(file.getAbsolutePath()));
				savedImages.put(getFileName(file.getAbsolutePath()) + "." + getExtension(file.getAbsolutePath()), ImageReader.readThumbnail(file, 100, 100));
			}
		}
		System.out.println("[FileManager] Finished");
	}
	
	List<String> searchedFiles = new ArrayList<String>();
	public List<String> getSearchedFiles()
	{
		return searchedFiles;
	}
	
	public List<String> updateTextures(String search)
	{
		searchedFiles.clear();
		TextureListRenderer.resetIcons();
		for(Map.Entry<String, Image> entry : savedImages.entrySet())
		{
			String name = entry.getKey();
			if(name.matches(".*" + search + ".*"))
			{
				searchedFiles.add(name);
				TextureListRenderer.addIcons(entry.getValue(), name);
			}
		}
		return searchedFiles;
	}
	
	public List<String> updateTextures()
	{
		searchedFiles.clear();
		TextureListRenderer.resetIcons();
		for(Map.Entry<String, Image> entry : savedImages.entrySet())
		{
			String name = entry.getKey();
			searchedFiles.add(name);
			TextureListRenderer.addIcons(entry.getValue(), name);
		}
		return searchedFiles;
	}
	
	private boolean hasFile(File directory, String fileName, String fileExtension)
	{
		String[] files = directory.list(new FileFilter(fileName, fileExtension));
		if(files == null)
		{
			return false;
		}
		for(String file : files)
		{
			return true;
		}
		return false;
	}
	
	private String getExtension(String absolutePath)
	{
		return absolutePath.split("\\.")[1];
	}
	
	private String getFileName(String absolutePath)
	{
		String[] splits = absolutePath.split("\\\\");
		return splits[splits.length - 1].split("\\.")[0];
	}
	
	private String getContainingDirectory(String absolutePath)
	{
		return absolutePath.replace("\\" + getFileName(absolutePath) + "." + getExtension(absolutePath), "");
	}
	
	private void listFiles(String directory, List<File> files)
	{
		File dir = new File(directory);
		
		File[] fileList = dir.listFiles();
		for(File file : fileList)
		{
			if(file.isFile())
			{
				files.add(file);
			}
			else if(file.isDirectory())
			{
				listFiles(file.getAbsolutePath(), files);
			}
		}
	}
}