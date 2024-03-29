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

package org.ocsoft.flatlaf.laf.desktoppane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.LookAndFeel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import org.ocsoft.flatlaf.core.constants.FlatLafConstants;
import org.ocsoft.flatlaf.managers.focus.DefaultFocusTracker;
import org.ocsoft.flatlaf.managers.focus.FocusManager;
import org.ocsoft.flatlaf.managers.focus.FocusTracker;
import org.ocsoft.flatlaf.utils.LafUtils;
import org.ocsoft.flatlaf.utils.SwingUtils;
import org.ocsoft.flatlaf.utils.graphics.GraphicsUtils;
import org.ocsoft.flatlaf.weblaf.FlatLafStyleConstants;
import org.ocsoft.flatlaf.weblaf.desktoppane.WebInternalFrameTitlePane;

/**
 * Custom UI for JInternalFrame component.
 *
 * @author Mikle Garin
 */

public class FlatInternalFrameUI extends BasicInternalFrameUI {
    /**
     * Style settings.
     */
    protected int sideSpacing = 1;
    
    /**
     * Panel focus tracker.
     */
    protected FocusTracker focusTracker;
    
    /**
     * Whether internal frame is focused or owns focused component or not.
     */
    protected boolean focused = false;
    
    /**
     * Constructs new internal frame UI.
     *
     * @param b
     *            internal frame to which this UI will be applied
     */
    public FlatInternalFrameUI(final JInternalFrame b) {
        super(b);
    }
    
    /**
     * Returns an instance of the WebInternalFrameUI for the specified
     * component. This tricky method is used by UIManager to create component
     * UIs when needed.
     *
     * @param c
     *            component that will use UI instance
     * @return instance of the WebInternalFrameUI
     */
    public static ComponentUI createUI(final JComponent c) {
        return new FlatInternalFrameUI((JInternalFrame) c);
    }
    
    /**
     * Installs UI in the specified component.
     *
     * @param c
     *            component for this UI
     */
    @Override
    public void installUI(final JComponent c) {
        super.installUI(c);
        
        // Default settings
        SwingUtils.setOrientation(frame);
        LookAndFeel.installProperty(frame, FlatLafConstants.OPAQUE_PROPERTY,
                Boolean.FALSE);
        frame.setBackground(new Color(90, 90, 90, 220));
        frame.setBorder(LafUtils.createWebBorder(0, 0, 0, 0));
        
        // Focus tracker for the panel content
        focusTracker = new DefaultFocusTracker() {
            @Override
            public void focusChanged(final boolean focused) {
                FlatInternalFrameUI.this.focused = focused;
                frame.repaint();
            }
        };
        FocusManager.addFocusTracker(frame, focusTracker);
    }
    
    /**
     * Uninstalls UI from the specified component.
     *
     * @param c
     *            component with this UI
     */
    @Override
    public void uninstallUI(final JComponent c) {
        // Removing focus tracker
        FocusManager.removeFocusTracker(focusTracker);
        
        super.uninstallUI(c);
    }
    
    /**
     * Creates and returns internal pane north panel.
     *
     * @param w
     *            internal pane to process
     * @return north panel for specified internal frame
     */
    @Override
    protected JComponent createNorthPane(final JInternalFrame w) {
        titlePane = new WebInternalFrameTitlePane(w);
        return titlePane;
    }
    
    /**
     * Creates and returns internal pane west panel.
     *
     * @param w
     *            internal pane to process
     * @return west panel for specified internal frame
     */
    @Override
    protected JComponent createWestPane(final JInternalFrame w) {
        // todo Proper internal frame resize
        return new JComponent() {
            {
                setOpaque(false);
            }
            
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(4 + sideSpacing, 0);
            }
        };
    }
    
    /**
     * Creates and returns internal pane east panel.
     *
     * @param w
     *            internal pane to process
     * @return east panel for specified internal frame
     */
    @Override
    protected JComponent createEastPane(final JInternalFrame w) {
        // todo Proper internal frame resize
        return new JComponent() {
            {
                setOpaque(false);
            }
            
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(4 + sideSpacing, 0);
            }
        };
    }
    
    /**
     * Creates and returns internal pane south panel.
     *
     * @param w
     *            internal pane to process
     * @return south panel for specified internal frame
     */
    @Override
    protected JComponent createSouthPane(final JInternalFrame w) {
        // todo Proper internal frame resize
        return new JComponent() {
            {
                setOpaque(false);
            }
            
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(0, 4 + sideSpacing);
            }
        };
    }
    
    /**
     * Paints internal frame.
     *
     * @param g
     *            graphics
     * @param c
     *            component
     */
    @Override
    public void paint(final Graphics g, final JComponent c) {
        final Graphics2D g2d = (Graphics2D) g;
        final Object aa = GraphicsUtils.setupAntialias(g2d);
        
        // Shape border = LafUtils.getWebBorderShape ( c,
        // StyleConstants.shadeWidth, StyleConstants.round );
        
        final Insets insets = c.getInsets();
        final RoundRectangle2D innerBorder = new RoundRectangle2D.Double(
                insets.left + 3 + sideSpacing, insets.top
                        + titlePane.getHeight() - 1, c.getWidth() - 1
                        - insets.left - 3 - sideSpacing - insets.right - 3
                        - sideSpacing, c.getHeight() - 1 - insets.top
                        - titlePane.getHeight() + 1 - insets.bottom - 3
                        - sideSpacing,
                (FlatLafStyleConstants.bigRound - 1) * 2,
                (FlatLafStyleConstants.bigRound - 1) * 2);
        
        // Border and background
        LafUtils.drawWebStyle(
                g2d,
                c,
                c.isEnabled() && focused ? FlatLafStyleConstants.fieldFocusColor
                        : FlatLafStyleConstants.shadeColor,
                FlatLafStyleConstants.shadeWidth,
                FlatLafStyleConstants.bigRound, true, false);
        
        // Inner border
        g2d.setPaint(Color.GRAY);
        g2d.draw(innerBorder);
        
        GraphicsUtils.restoreAntialias(g2d, aa);
    }
}
