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

package org.ocsoft.flatlaf.laf.menu;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.geom.Line2D;

import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuBarUI;

import org.ocsoft.flatlaf.core.constants.FlatLafConstants;
import org.ocsoft.flatlaf.extended.layout.ToolbarLayout;
import org.ocsoft.flatlaf.utils.LafUtils;
import org.ocsoft.flatlaf.utils.SwingUtils;
import org.ocsoft.flatlaf.utils.graphics.GraphicsUtils;
import org.ocsoft.flatlaf.utils.laf.ShapeProvider;
import org.ocsoft.flatlaf.utils.swing.BorderMethods;
import org.ocsoft.flatlaf.weblaf.FlatLafStyleConstants;
import org.ocsoft.flatlaf.weblaf.menu.MenuBarStyle;
import org.ocsoft.flatlaf.weblaf.menu.WebMenuBarStyle;

/**
 * User: mgarin Date: 15.08.11 Time: 20:24
 */

public class WebMenuBarUI extends BasicMenuBarUI implements ShapeProvider,
        BorderMethods {
    private boolean undecorated = WebMenuBarStyle.undecorated;
    private int round = WebMenuBarStyle.round;
    private int shadeWidth = WebMenuBarStyle.shadeWidth;
    private MenuBarStyle menuBarStyle = WebMenuBarStyle.menuBarStyle;
    private Color borderColor = WebMenuBarStyle.borderColor;
    
    public static ComponentUI createUI(final JComponent c) {
        return new WebMenuBarUI();
    }
    
    @Override
    public void installUI(final JComponent c) {
        super.installUI(c);
        
        // Default settings
        SwingUtils.setOrientation(menuBar);
        LookAndFeel.installProperty(menuBar, FlatLafConstants.OPAQUE_PROPERTY,
                Boolean.FALSE);
        menuBar.setLayout(new ToolbarLayout(0));
        
        // Updating border
        updateBorder();
    }
    
    @Override
    public Shape provideShape() {
        return LafUtils.getWebBorderShape(menuBar, getShadeWidth(), getRound());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void updateBorder() {
        // Preserve old borders
        if (SwingUtils.isPreserveBorders(menuBar)) {
            return;
        }
        
        final Insets insets;
        if (!undecorated) {
            if (menuBarStyle.equals(MenuBarStyle.attached)) {
                insets = new Insets(0, 0, 1 + shadeWidth, 0);
            } else {
                insets = new Insets(1 + shadeWidth, 1 + shadeWidth,
                        1 + shadeWidth, 1 + shadeWidth);
            }
        } else {
            insets = new Insets(0, 0, 0, 0);
        }
        menuBar.setBorder(LafUtils.createWebBorder(insets));
    }
    
    public boolean isUndecorated() {
        return undecorated;
    }
    
    public void setUndecorated(final boolean undecorated) {
        this.undecorated = undecorated;
        updateBorder();
    }
    
    public MenuBarStyle getMenuBarStyle() {
        return menuBarStyle;
    }
    
    public void setMenuBarStyle(final MenuBarStyle menuBarStyle) {
        this.menuBarStyle = menuBarStyle;
        updateBorder();
    }
    
    public Color getBorderColor() {
        return borderColor;
    }
    
    public void setBorderColor(final Color borderColor) {
        this.borderColor = borderColor;
    }
    
    public int getRound() {
        return round;
    }
    
    public void setRound(final int round) {
        this.round = round;
    }
    
    public int getShadeWidth() {
        return shadeWidth;
    }
    
    public void setShadeWidth(final int shadeWidth) {
        this.shadeWidth = shadeWidth;
        updateBorder();
    }
    
    @Override
    public void paint(final Graphics g, final JComponent c) {
        if (!undecorated) {
            final Graphics2D g2d = (Graphics2D) g;
            
            if (menuBarStyle == MenuBarStyle.attached) {
                final Shape border = new Line2D.Double(0, c.getHeight() - 1
                        - shadeWidth, c.getWidth() - 1, c.getHeight() - 1
                        - shadeWidth);
                
                GraphicsUtils.drawShade(g2d, border,
                        FlatLafStyleConstants.shadeColor, shadeWidth);
                
                g2d.setPaint(new GradientPaint(0, 0,
                        FlatLafStyleConstants.topBgColor, 0, c.getHeight(),
                        new Color(235, 235, 235)));
                g2d.fillRect(0, 0, c.getWidth(), c.getHeight() - shadeWidth);
                
                g2d.setPaint(borderColor);
                g2d.draw(border);
            } else {
                LafUtils.drawWebStyle(g2d, c, FlatLafStyleConstants.shadeColor,
                        shadeWidth, round, true, true, borderColor);
            }
        }
    }
}
