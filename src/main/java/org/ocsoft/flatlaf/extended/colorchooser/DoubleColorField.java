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

package org.ocsoft.flatlaf.extended.colorchooser;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import org.ocsoft.flatlaf.managers.language.LanguageAdapter;
import org.ocsoft.flatlaf.managers.language.LanguageManager;
import org.ocsoft.flatlaf.utils.LafUtils;
import org.ocsoft.flatlaf.utils.SwingUtils;
import org.ocsoft.flatlaf.utils.collection.CollectionUtils;
import org.ocsoft.flatlaf.weblaf.colorchooser.HSBColor;
import org.ocsoft.flatlaf.weblaf.panel.WebPanel;

/**
 * User: mgarin Date: 08.07.2010 Time: 14:12:13
 */

public class DoubleColorField extends WebPanel {
    private List<DoubleColorFieldListener> listeners = new ArrayList<DoubleColorFieldListener>(
            1);
    
    private Color newColor;
    private Color oldColor;
    
    private HSBColor newHSBColor;
    private HSBColor oldHSBColor;
    
    private String newText = "new";
    private String currentText = "current";
    
    public DoubleColorField() {
        super();
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (e.getY() <= getHeight() / 2) {
                        newColorPressed();
                    } else {
                        oldColorPressed();
                    }
                }
            }
        });
        
        LanguageManager.addLanguageListener(new LanguageAdapter() {
            @Override
            public void languageUpdated() {
                repaint();
                revalidate();
            }
        });
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        Graphics2D g2d = (Graphics2D) g;
        FontMetrics fm = g2d.getFontMetrics();
        
        Map hints = SwingUtils.setupTextAntialias(g2d);
        
        g2d.setPaint(Color.GRAY);
        g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        g2d.setPaint(Color.WHITE);
        g2d.drawRect(1, 1, getWidth() - 3, getHeight() - 3);
        
        g2d.setPaint(newColor);
        g2d.fillRect(2, 2, getWidth() - 4, getHeight() / 2 - 2);
        
        final String newText = LanguageManager
                .get("weblaf.colorchooser.color.new");
        Point nts = LafUtils.getTextCenterShear(fm, newText);
        g2d.setPaint(newHSBColor.getBrightness() >= 0.7f
                && newHSBColor.getSaturation() < 0.7f ? Color.BLACK
                : Color.WHITE);
        g2d.drawString(newText, getWidth() / 2 + nts.x, 2 + (getHeight() - 4)
                / 4 + nts.y);
        
        g2d.setPaint(oldColor);
        g2d.fillRect(2, getHeight() / 2, getWidth() - 4, getHeight()
                - getHeight() / 2 - 2);
        
        final String currentText = LanguageManager
                .get("weblaf.colorchooser.color.current");
        Point cts = LafUtils.getTextCenterShear(fm, currentText);
        g2d.setPaint(oldHSBColor.getBrightness() >= 0.7f
                && oldHSBColor.getSaturation() < 0.7f ? Color.BLACK
                : Color.WHITE);
        g2d.drawString(currentText, getWidth() / 2 + cts.x, 2
                + (getHeight() - 4) * 3 / 4 + cts.y);
        
        SwingUtils.restoreTextAntialias(g2d, hints);
    }
    
    public Color getNewColor() {
        return newColor;
    }
    
    public void setNewColor(Color newColor) {
        this.newColor = newColor;
        this.newHSBColor = new HSBColor(newColor);
        this.repaint();
    }
    
    public Color getOldColor() {
        return oldColor;
    }
    
    public void setOldColor(Color oldColor) {
        this.oldColor = oldColor;
        this.oldHSBColor = new HSBColor(oldColor);
        this.repaint();
    }
    
    public void addDoubleColorFieldListener(DoubleColorFieldListener listener) {
        listeners.add(listener);
    }
    
    public void removeDoubleColorFieldListener(DoubleColorFieldListener listener) {
        listeners.remove(listener);
    }
    
    private void newColorPressed() {
        for (DoubleColorFieldListener listener : CollectionUtils
                .copy(listeners)) {
            listener.newColorPressed(newColor);
        }
    }
    
    private void oldColorPressed() {
        for (DoubleColorFieldListener listener : CollectionUtils
                .copy(listeners)) {
            listener.oldColorPressed(oldColor);
        }
    }
}