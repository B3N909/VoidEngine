package file;

import java.io.File;
import java.io.FilenameFilter;

public class FileFilter implements FilenameFilter
{
	private String name;
	private String extension;
	
	public FileFilter(String name, String extension)
	{
		this.name = name;
		this.extension = extension;
	}
	
	public boolean accept(File dir, String name)
	{
		return ((name.startsWith(this.name) && name.endsWith("." + this.extension)));
	}
	
}
