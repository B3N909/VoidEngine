package file;

import com.aspose.imaging.Image;
import com.aspose.imaging.fileformats.png.PngColorType;
import com.aspose.imaging.fileformats.psd.PsdImage;
import com.aspose.imaging.imageoptions.PngOptions;

public class AsposeConverter
{
	public void convertPSD(String path, String fileName)
	{
		Image image = Image.load(path + "\\" + fileName + ".psd");
		PsdImage psdImage = (PsdImage) image;
		PngOptions pngOptions = new PngOptions();
		pngOptions.setColorType(PngColorType.TruecolorWithAlpha);
		psdImage.save(path + "\\" + fileName + ".png", pngOptions);
	}
}
