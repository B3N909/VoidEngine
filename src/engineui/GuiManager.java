package engineui;

import gui.GuiTexture;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.lwjgl.util.vector.Vector2f;

public class GuiManager
{
	static JFrame frame;
	static JList list; 
	static DefaultListModel model;
	static boolean isOpen = false;
	static JTextField scaleX, scaleY;
	static JTextField positionX, positionY;
	
	public static void open()
	{
		frame = new JFrame("Guis");
		frame.setSize(300, 600);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		frame.getContentPane().add(panel);
		
		GridBagConstraints left = new GridBagConstraints();
		left.anchor = GridBagConstraints.EAST;
		
		GridBagConstraints right = new GridBagConstraints();
		right.weightx = 2.0;
		right.fill = GridBagConstraints.HORIZONTAL;
		right.gridwidth = GridBagConstraints.REMAINDER;
		
		GridBagConstraints fullBottom = new GridBagConstraints();
		fullBottom.fill = GridBagConstraints.BOTH;
		fullBottom.gridwidth = GridBagConstraints.REMAINDER;
		fullBottom.weightx = 1;
		fullBottom.weighty = 1;
		
		positionX = new JTextField();
		positionY = new JTextField();
		
		scaleX = new JTextField();
		scaleY = new JTextField();
		
		positionX.getDocument().addDocumentListener(new DocumentListener()
		{
			public void insertUpdate(DocumentEvent paramDocumentEvent) { save(); }

			public void removeUpdate(DocumentEvent paramDocumentEvent) { save(); }

			public void changedUpdate(DocumentEvent paramDocumentEvent) { save(); }
			
			void save()
			{
				if(positionX.getText().equalsIgnoreCase(""))
					return;
				try
				{
					float value = Float.parseFloat(positionX.getText());
					GuiTexture gui = getGui(list.getSelectedValue().toString());
					gui.setPosition(new Vector2f(value, gui.getPosition().y));
				}
				catch (NumberFormatException e)
				{
					
				}
			}
		});
		
		positionY.getDocument().addDocumentListener(new DocumentListener()
		{
			public void insertUpdate(DocumentEvent paramDocumentEvent) { save(); }

			public void removeUpdate(DocumentEvent paramDocumentEvent) { save(); }

			public void changedUpdate(DocumentEvent paramDocumentEvent) { save(); }
			
			void save()
			{
				if(positionY.getText().equalsIgnoreCase(""))
					return;
				try
				{
					float value = Float.parseFloat(positionY.getText());
					GuiTexture gui = getGui(list.getSelectedValue().toString());
					gui.setPosition(new Vector2f(gui.getPosition().x, value));
				}
				catch (NumberFormatException e)
				{
					
				}
			}
		});
		
		scaleX.getDocument().addDocumentListener(new DocumentListener()
		{
			public void insertUpdate(DocumentEvent paramDocumentEvent) { save(); }

			public void removeUpdate(DocumentEvent paramDocumentEvent) { save(); }

			public void changedUpdate(DocumentEvent paramDocumentEvent) { save(); }
			
			void save()
			{
				if(scaleX.getText().equalsIgnoreCase(""))
					return;
				try
				{
					float value = Float.parseFloat(scaleX.getText());
					GuiTexture gui = getGui(list.getSelectedValue().toString());
					gui.setScale(new Vector2f(value, gui.getScale().y));
				}
				catch (NumberFormatException e)
				{
					
				}
			}
		});
		
		scaleY.getDocument().addDocumentListener(new DocumentListener()
		{
			public void insertUpdate(DocumentEvent paramDocumentEvent) { save(); }

			public void removeUpdate(DocumentEvent paramDocumentEvent) { save(); }

			public void changedUpdate(DocumentEvent paramDocumentEvent) { save(); }
			
			void save()
			{
				if(scaleY.getText().equalsIgnoreCase(""))
					return;
				try
				{
					float value = Float.parseFloat(scaleY.getText());
					GuiTexture gui = getGui(list.getSelectedValue().toString());
					gui.setScale(new Vector2f(gui.getScale().x, value));
				}
				catch (NumberFormatException e)
				{
					
				}
			}
		});
		
		final JCheckBox enabled = new JCheckBox();
		enabled.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(list.getSelectedValue() != null)
				{
					GuiTexture gui = getGui(list.getSelectedValue().toString());
					gui.setEnabled(enabled.isSelected());
				}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		model = new DefaultListModel();
		list = new JList(model);
		list.setCellRenderer(new GuiListRenderer());
		list.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		list.setVisibleRowCount(2);
		scrollPane.setViewportView(list);
		
		list.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e)
			{
				String selected = list.getSelectedValue().toString();
				GuiTexture gui = getGui(selected);
				positionX.setText(gui.getPosition().x + "");
				positionY.setText(gui.getPosition().y + "");
				scaleX.setText(gui.getScale().x + "");
				scaleY.setText(gui.getScale().y + "");
				enabled.setSelected(gui.isEnabled());
			}
		});
		
		JLabel positionX_text = new JLabel("Position X: ");
		JLabel positionY_text = new JLabel("Position Y: ");
		JLabel scaleX_text = new JLabel("Scale X: ");
		JLabel scaleY_text = new JLabel("Scale Y: ");
		
		JLabel enabled_text = new JLabel("Enabled: ");
		
		panel.add(scrollPane, fullBottom);
		
		panel.add(positionX_text, left);
		panel.add(positionX, right);
		
		panel.add(positionY_text, left);
		panel.add(positionY, right);
		
		panel.add(scaleX_text, left);
		panel.add(scaleX, right);
		
		panel.add(scaleY_text, left);
		panel.add(scaleY, right);
		
		panel.add(enabled_text, left);
		panel.add(enabled, right);
		
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		
		refresh();
		
		isOpen = true;
		
		frame.setVisible(true);
	}
	
	public static boolean isOpen()
	{
		if(frame != null && !frame.isFocused())
		{
			frame.dispose();
			open();
		}
		return isOpen;
	}
	
	static List<GuiTexture> entries = new ArrayList<GuiTexture>();
	
	public static void addEntry(GuiTexture gui)
	{
		entries.add(gui);
		if(isOpen)
			refresh();
	}
	
	private static void refresh()
	{
		model.clear();
		for(GuiTexture gui : entries)
		{
			model.addElement(gui.getTextureName() + ", " + gui.getUUID().toString());
		}
	}
	
	private static GuiTexture getGui(String name)
	{
		String realName = name.split(", ")[0];
		String uuid = name.split(", ")[1];
		for(GuiTexture gui : entries)
		{
			if(gui.getTextureName().equalsIgnoreCase(realName))
			{
				if(gui.getUUID().toString().equalsIgnoreCase(uuid))
				{
					return gui;
				}
			}
		}
		return null;
	}
}
