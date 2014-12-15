/*
 * This file is part of WebLookAndFeel library.
 *
 * WebLookAndFeel library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WebLookAndFeel library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WebLookAndFeel library.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.ocsoft.flatlaf.extended.window;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.ocsoft.flatlaf.core.FlatLookAndFeel;
import org.ocsoft.flatlaf.extended.image.WebImage;
import org.ocsoft.flatlaf.extended.painter.AlphaLayerPainter;
import org.ocsoft.flatlaf.laf.panel.WebPanel;
import org.ocsoft.flatlaf.laf.rootpane.WebFrame;

/**
 * @author Mikle Garin
 */

public class ImagePreviewFrame extends WebFrame {
    public ImagePreviewFrame(final String src) {
        super();
        initializeUI(new WebImage(src));
    }
    
    public ImagePreviewFrame(final Class nearClass, final String src) {
        super();
        initializeUI(new WebImage(nearClass, src));
    }
    
    public ImagePreviewFrame(final URL url) {
        super();
        initializeUI(new WebImage(url));
    }
    
    public ImagePreviewFrame(final Icon image) {
        super();
        initializeUI(new WebImage(image));
    }
    
    public ImagePreviewFrame(final ImageIcon image) {
        super();
        initializeUI(new WebImage(image));
    }
    
    public ImagePreviewFrame(final Image image) {
        super();
        initializeUI(new WebImage(image));
    }
    
    public ImagePreviewFrame(final BufferedImage image) {
        super();
        initializeUI(new WebImage(image));
    }
    
    private void initializeUI(final WebImage image) {
        setIconImages(FlatLookAndFeel.getImages());
        
        setLayout(new BorderLayout());
        
        final WebPanel area = new WebPanel(new AlphaLayerPainter());
        area.setMargin(Math.max(5, 80 - image.getWidth()));
        add(area);
        
        area.add(image);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        pack();
        center();
        setVisible(true);
    }
}