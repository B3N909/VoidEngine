package engineui;

import java.awt.Component;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

public class GuiListRenderer extends DefaultListCellRenderer
{
	private static final long serialVersionUID = 1L;
	public static Map<String, Icon> icons = new HashMap<String, Icon>();
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
	{
		JLabel label = (JLabel) super.getListCellRendererComponent(
		list, value, index, isSelected, cellHasFocus);
		label.setIcon(getIcon(label.getText()));
		label.setHorizontalTextPosition(JLabel.RIGHT);
		return label;
	}
	
	private Icon getIcon(String text)
	{
		return icons.get(text);
	}
	
	public static void resetIcons()
	{
		icons.clear();
	}
	
	public static void addIcons(Image image, String text, UUID uuid)
	{
		ImageIcon icon = new ImageIcon(image);
		icons.put(text + ", " + uuid.toString(), icon);
	}
}
