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

package org.ocsoft.flatlaf.extended.label;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.RoundRectangle2D;

import javax.swing.Icon;

import org.ocsoft.flatlaf.managers.language.LanguageMethods;
import org.ocsoft.flatlaf.utils.SwingUtils;
import org.ocsoft.flatlaf.utils.graphics.GraphicsUtils;
import org.ocsoft.flatlaf.utils.laf.ShapeProvider;
import org.ocsoft.flatlaf.weblaf.FlatLafStyleConstants;
import org.ocsoft.flatlaf.weblaf.label.WebLabel;

/**
 * This class provides a quick access to step-styled label which can be used to
 * visualize some process steps.
 *
 * @author Mikle Garin
 */

public class WebStepLabel extends WebLabel implements ShapeProvider,
        LanguageMethods {
    /**
     * todo 1. Move painting to a proper separate painter todo 2. Add custom UI
     * for that kind of component
     */
    
    private Color topBgColor = FlatLafStyleConstants.topBgColor;
    private Color bottomBgColor = FlatLafStyleConstants.bottomBgColor;
    private Color selectedBgColor = FlatLafStyleConstants.selectedBgColor;
    private Color borderColor = FlatLafStyleConstants.darkBorderColor;
    private Color disabledBorderColor = FlatLafStyleConstants.disabledBorderColor;
    
    private final Stroke stroke = new BasicStroke(2.5f, BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_ROUND, 1f);
    
    private boolean selected = false;
    
    public WebStepLabel() {
        super();
        setupSettings();
    }
    
    public WebStepLabel(final Icon image) {
        super(image);
        setupSettings();
    }
    
    public WebStepLabel(final Icon image, final int horizontalAlignment) {
        super(image, horizontalAlignment);
        setupSettings();
    }
    
    public WebStepLabel(final String text) {
        super(text);
        setupSettings();
    }
    
    public WebStepLabel(final String text, final int horizontalAlignment) {
        super(text, horizontalAlignment);
        setupSettings();
    }
    
    public WebStepLabel(final String text, final Icon icon,
            final int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
        setupSettings();
    }
    
    protected void setupSettings() {
        setOpaque(false);
        setDrawShade(true);
        setForeground(Color.DARK_GRAY);
        setShadeColor(Color.LIGHT_GRAY);
        setMargin(8);
        setHorizontalAlignment(WebStepLabel.CENTER);
        
        SwingUtils.setBoldFont(this);
        SwingUtils.setFontSize(this, 20);
    }
    
    public Color getTopBgColor() {
        return topBgColor;
    }
    
    public void setTopBgColor(final Color topBgColor) {
        this.topBgColor = topBgColor;
        this.repaint();
    }
    
    public Color getBottomBgColor() {
        return bottomBgColor;
    }
    
    public void setBottomBgColor(final Color bottomBgColor) {
        this.bottomBgColor = bottomBgColor;
        this.repaint();
    }
    
    public Color getSelectedBgColor() {
        return selectedBgColor;
    }
    
    public void setSelectedBgColor(final Color selectedBgColor) {
        this.selectedBgColor = selectedBgColor;
        this.repaint();
    }
    
    public Color getBorderColor() {
        return borderColor;
    }
    
    public void setBorderColor(final Color borderColor) {
        this.borderColor = borderColor;
        this.repaint();
    }
    
    public Color getDisabledBorderColor() {
        return disabledBorderColor;
    }
    
    public void setDisabledBorderColor(final Color disabledBorderColor) {
        this.disabledBorderColor = disabledBorderColor;
        this.repaint();
    }
    
    public boolean isSelected() {
        return selected;
    }
    
    public void setSelected(final boolean selected) {
        this.selected = selected;
        this.repaint();
    }
    
    @Override
    public Shape provideShape() {
        final int width = getWidth();
        final int height = getHeight();
        final int length = Math.min(width, height);
        return new RoundRectangle2D.Double(width / 2 - length / 2 + 1, height
                / 2 - length / 2 + 1, length - 3, length - 3, getWidth() - 4,
                getHeight() - 4);
    }
    
    @Override
    protected void paintComponent(final Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;
        final Object aa = GraphicsUtils.setupAntialias(g2d);
        
        final int width = getWidth();
        final int height = getHeight();
        final int length = Math.min(width, height);
        
        // Background
        if (getBackground() != null) {
            if (selected) {
                g2d.setPaint(selectedBgColor);
            } else {
                g2d.setPaint(new GradientPaint(0, 0, topBgColor, 0,
                        getHeight(), bottomBgColor));
            }
            g2d.fillRoundRect(width / 2 - length / 2 + 1, height / 2 - length
                    / 2 + 1, length - 2, length - 2, getWidth() - 4,
                    getHeight() - 4);
        }
        
        // Border
        if (getBorderColor() != null) {
            g2d.setStroke(stroke);
            g2d.setPaint(isEnabled() ? borderColor : disabledBorderColor);
            g2d.drawRoundRect(width / 2 - length / 2 + 1, height / 2 - length
                    / 2 + 1, length - 3, length - 3, getWidth() - 4,
                    getHeight() - 4);
        }
        
        GraphicsUtils.restoreAntialias(g2d, aa);
        
        super.paintComponent(g);
    }
    
    @Override
    public Dimension getPreferredSize() {
        final Dimension ps = super.getPreferredSize();
        final int max = Math.max(ps.width, ps.height);
        ps.width = max;
        ps.height = max;
        return ps;
    }
}