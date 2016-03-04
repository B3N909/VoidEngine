package engineui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import file.FileManager;

public class TextureManager
{
	private static boolean isOpen = false;
	static JFrame frame;
	static JList list;
	static DefaultListModel model;
	public static FileManager fileManager;
	
	public static void open()
	{
		frame = new JFrame("Textures");
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
		
		JLabel searchLabel = new JLabel("Name: ");
		final JTextField searchText = new JTextField();
		searchText.getDocument().addDocumentListener(new DocumentListener()
		{
			public void changedUpdate(DocumentEvent e) { }
			
			public void removeUpdate(DocumentEvent e) { update(); }
			
			public void insertUpdate(DocumentEvent e) { update(); }
			
			void update()
			{
				List<String> searched = fileManager.updateTextures(searchText.getText());
				model.clear();
				for(String search : searched)
				{
					model.addElement(search);
				}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		
		model = new DefaultListModel();
		list = new JList(model);
		list.setVisibleRowCount(10);
		list.setCellRenderer(new TextureListRenderer());
		list.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		list.setVisibleRowCount(2);
		scrollPane.setViewportView(list);
		
		panel.add(searchLabel, left);
		panel.add(searchText, right);
		panel.add(scrollPane, fullBottom);
		
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		
		isOpen = true;
		
		List<String> searched = fileManager.getSearchedFiles();
		model.clear();
		for(String search : searched)
		{
			model.addElement(search);
		}
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
}
