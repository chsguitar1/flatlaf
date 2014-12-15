package org.ocsoft.flatlaf;

import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.ocsoft.flatlaf.core.FlatLookAndFeel;

public class TestWindow extends JFrame {
    
    public TestWindow() {
        JPanel panel = new JPanel();
        BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layout);
        JScrollPane scroll = new JScrollPane(panel);
        
        panel.add(new JButton());
        panel.add(new JCheckBox());
        panel.add(new JColorChooser());
        panel.add(new JComboBox<String>());
        panel.add(new JDesktopPane());
        panel.add(new JFileChooser(new File(".")));
        panel.add(new JLabel("foobar"));
        panel.add(new JList<String>());
        
        this.add(scroll);
        this.pack();
        this.setVisible(true);
    }
    
    public static void main(String[] args) {
        FlatLookAndFeel.install();
        new TestWindow();
    }
    
}
