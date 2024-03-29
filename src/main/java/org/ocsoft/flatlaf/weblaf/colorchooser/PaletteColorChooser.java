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

package org.ocsoft.flatlaf.weblaf.colorchooser;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.ocsoft.flatlaf.extended.layout.TableLayout;
import org.ocsoft.flatlaf.utils.ImageUtils;
import org.ocsoft.flatlaf.utils.collection.CollectionUtils;
import org.ocsoft.flatlaf.weblaf.panel.WebPanel;

/**
 * User: mgarin Date: 07.07.2010 Time: 17:21:44
 */

public class PaletteColorChooser extends WebPanel {
    public static final ImageIcon LOOP_ICON = new ImageIcon(
            PaletteColorChooser.class.getResource("icons/loop.png"));
    
    private List<ChangeListener> changeListeners = new ArrayList<ChangeListener>(
            1);
    
    private boolean adjusting = false;
    
    private boolean webOnlyColors = false;
    
    private Color sideColor = Color.RED;
    private Color color = Color.WHITE;
    
    private PaletteColorChooserPaint paletteColorChooserPaint;
    private BufferedImage image;
    private Point coordinate;
    
    private JComponent colorChooser;
    
    public PaletteColorChooser() {
        super();
        
        paletteColorChooserPaint = new PaletteColorChooserPaint(0, 0, 256, 256,
                sideColor);
        image = ImageUtils.createCompatibleImage(256, 256,
                Transparency.TRANSLUCENT);
        coordinate = new Point(2, 2);
        repaintImage();
        
        setLayout(new TableLayout(new double[][] { { TableLayout.PREFERRED },
                { 3, TableLayout.PREFERRED, 3 } }));
        
        colorChooser = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                Graphics2D g2d = (Graphics2D) g;
                
                Shape old = g2d.getClip();
                Area clip = new Area(new Rectangle2D.Double(2, 2,
                        getWidth() - 4, getHeight() - 4));
                clip.intersect(new Area(old));
                g2d.setClip(clip);
                
                g2d.drawImage(image, 2, 2, null);
                g2d.drawImage(LOOP_ICON.getImage(),
                        coordinate.x - LOOP_ICON.getIconWidth() / 2,
                        coordinate.y - LOOP_ICON.getIconHeight() / 2,
                        LOOP_ICON.getImageObserver());
                
                g2d.setClip(old);
            }
        };
        colorChooser.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createLineBorder(Color.WHITE, 1)));
        // colorChooser.setBorder ( BorderFactory
        // .createBevelBorder ( BevelBorder.LOWERED, Color.WHITE, Color.WHITE,
        // new Color ( 160, 160, 160 ), new Color ( 178, 178, 178 ) ) );
        colorChooser.setPreferredSize(new Dimension(260, 260));
        
        ColorChooserMouseAdapter adapter = new ColorChooserMouseAdapter();
        colorChooser.addMouseListener(adapter);
        colorChooser.addMouseMotionListener(adapter);
        
        add(colorChooser, "0,1");
    }
    
    public JComponent getColorChooser() {
        return colorChooser;
    }
    
    private void repaintImage() {
        Graphics2D g2d = image.createGraphics();
        g2d.setPaint(paletteColorChooserPaint);
        g2d.fillRect(0, 0, 256, 256);
        g2d.dispose();
    }
    
    private class ColorChooserMouseAdapter extends MouseAdapter {
        private boolean dragging = false;
        
        @Override
        public void mousePressed(MouseEvent e) {
            dragging = true;
            adjusting = true;
            updateCoordinate(e.getPoint());
        }
        
        @Override
        public void mouseDragged(MouseEvent e) {
            updateCoordinate(e.getPoint());
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
            dragging = false;
            adjusting = false;
            if (!colorChooser.getVisibleRect().contains(e.getPoint())) {
                setCursor(Cursor.getDefaultCursor());
            }
        }
        
        private void updateCoordinate(Point point) {
            coordinate = point;
            if (coordinate.x < 2) {
                coordinate.x = 2;
            } else if (coordinate.x > 256 + 2) {
                coordinate.x = 256 + 2;
            }
            if (coordinate.y < 2) {
                coordinate.y = 2;
            } else if (coordinate.y > 256 + 2) {
                coordinate.y = 256 + 2;
            }
            setColor(paletteColorChooserPaint.getColor(coordinate.x,
                    coordinate.y));
        }
        
        @Override
        public void mouseEntered(MouseEvent e) {
            setCursor(createLoopCursor());
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            if (!dragging) {
                setCursor(Cursor.getDefaultCursor());
            }
        }
    }
    
    private Cursor createLoopCursor() {
        Dimension dimension = Toolkit.getDefaultToolkit().getBestCursorSize(14,
                14);
        
        BufferedImage bufferedImage = ImageUtils.createCompatibleImage(
                dimension.width, dimension.height, Transparency.TRANSLUCENT);
        
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(LOOP_ICON.getImage(), 0, 0, LOOP_ICON.getImageObserver());
        g2d.dispose();
        
        return Toolkit.getDefaultToolkit().createCustomCursor(bufferedImage,
                new Point(7, 7), "Loop Cursor");
    }
    
    public Color getSideColor() {
        return sideColor;
    }
    
    public void setSideColor(Color sideColor) {
        adjusting = true;
        
        // Settings corner color
        this.sideColor = sideColor;
        paletteColorChooserPaint = new PaletteColorChooserPaint(1, 1, 256, 256,
                sideColor);
        paletteColorChooserPaint.setWebSafe(webOnlyColors);
        
        // Updating cached image
        repaintImage();
        
        // Settings selected color
        setColor(paletteColorChooserPaint.getColor(coordinate.x, coordinate.y));
        
        adjusting = false;
    }
    
    public Color getColor() {
        return color;
    }
    
    public void setColor(Color color) {
        this.color = color;
        
        if (!adjusting) {
            // Updating cursor position
            updateSelectionByColor();
        }
        
        // Informing about color change
        firePropertyChanged();
        
        // Updating area
        colorChooser.repaint();
    }
    
    public boolean isWebOnlyColors() {
        return webOnlyColors;
    }
    
    public void setWebOnlyColors(boolean webOnlyColors) {
        this.webOnlyColors = webOnlyColors;
        paletteColorChooserPaint.setWebSafe(webOnlyColors);
        repaintImage();
        colorChooser.repaint();
        firePropertyChanged();
    }
    
    private void updateSelectionByColor() {
        HSBColor hsbColor = new HSBColor(color);
        coordinate.x = 2 + Math.round(256 * hsbColor.getSaturation());
        coordinate.y = 2 + Math.round(256 - 256 * hsbColor.getBrightness());
    }
    
    public void addChangeListener(ChangeListener listener) {
        changeListeners.add(listener);
    }
    
    public void removeChangeListener(ChangeListener listener) {
        changeListeners.remove(listener);
    }
    
    private void firePropertyChanged() {
        ChangeEvent changeEvent = new ChangeEvent(PaletteColorChooser.this);
        for (ChangeListener changeListener : CollectionUtils
                .copy(changeListeners)) {
            changeListener.stateChanged(changeEvent);
        }
    }
}