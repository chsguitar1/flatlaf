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

package org.ocsoft.flatlaf.laf.scroll;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Shape;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.LookAndFeel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.basic.BasicScrollPaneUI;

import org.ocsoft.flatlaf.core.constants.FlatLafConstants;
import org.ocsoft.flatlaf.managers.focus.DefaultFocusTracker;
import org.ocsoft.flatlaf.managers.focus.FocusManager;
import org.ocsoft.flatlaf.managers.focus.FocusTracker;
import org.ocsoft.flatlaf.utils.LafUtils;
import org.ocsoft.flatlaf.utils.SwingUtils;
import org.ocsoft.flatlaf.utils.laf.ShapeProvider;
import org.ocsoft.flatlaf.weblaf.FlatLafStyleConstants;
import org.ocsoft.flatlaf.weblaf.scroll.WebScrollPaneCorner;
import org.ocsoft.flatlaf.weblaf.scroll.WebScrollPaneStyle;

/**
 * User: mgarin Date: 29.04.11 Time: 15:34
 */

public class WebScrollPaneUI extends BasicScrollPaneUI implements ShapeProvider {
    /**
     * todo 1. Implement optional shade layer
     */
    
    private boolean drawBorder = WebScrollPaneStyle.drawBorder;
    private Color borderColor = WebScrollPaneStyle.borderColor;
    private Color darkBorder = WebScrollPaneStyle.darkBorder;
    
    private int round = WebScrollPaneStyle.round;
    private int shadeWidth = WebScrollPaneStyle.shadeWidth;
    private Insets margin = WebScrollPaneStyle.margin;
    
    private boolean drawFocus = WebScrollPaneStyle.drawFocus;
    private boolean drawBackground = WebScrollPaneStyle.drawBackground;
    
    private WebScrollPaneCorner lowerTrailing;
    private WebScrollPaneCorner lowerLeading;
    private WebScrollPaneCorner upperTrailing;
    private PropertyChangeListener propertyChangeListener;
    
    /**
     * Scroll pane focus tracker.
     */
    protected FocusTracker focusTracker;
    
    private boolean focused = false;
    
    
    public static ComponentUI createUI(final JComponent c) {
        return new WebScrollPaneUI();
    }
    
    @Override
    public void installUI(final JComponent c) {
        super.installUI(c);
        
        // Default settings
        SwingUtils.setOrientation(scrollpane);
        LookAndFeel.installProperty(scrollpane,
                FlatLafConstants.OPAQUE_PROPERTY, Boolean.FALSE);
        scrollpane.setBackground(FlatLafStyleConstants.backgroundColor);
        
        // Updating scroll bars
        // todo Remove these when scroll pane painter will be completed
        final ScrollBarUI vui = scrollpane.getVerticalScrollBar().getUI();
        if (vui instanceof WebScrollBarUI) {
            final WebScrollBarUI ui = (WebScrollBarUI) vui;
            ui.setPaintTrack(drawBorder);
        }
        final ScrollBarUI hui = scrollpane.getHorizontalScrollBar().getUI();
        if (hui instanceof WebScrollBarUI) {
            final WebScrollBarUI ui = (WebScrollBarUI) hui;
            ui.setPaintTrack(drawBorder);
        }
        
        // Special
        LafUtils.setScrollBarStyleId(scrollpane, "scroll-pane");
        
        // // Shade layer
        // final WebPanel shadeLayer = new WebPanel ( new AbstractPainter ()
        // {
        // final int shadeWidth = 15;
        // final float transparency = 0.7f;
        //
        // @Override
        // public void paint ( final Graphics2D g2d, final Rectangle bounds,
        // final Component c )
        // {
        // final JViewport viewport = scrollpane.getViewport ();
        // final Component vc = viewport.getView ();
        // if ( vc != null && vc instanceof JComponent )
        // {
        // final JComponent view = ( JComponent ) vc;
        // final Rectangle vr = view.getVisibleRect ();
        //
        // final int topY = vr.y;
        // if ( topY > 0 )
        // {
        // final float max = topY / 2;
        // final float opacity = ( shadeWidth < max ? 1f : ( 1f - ( shadeWidth -
        // max ) / shadeWidth ) ) * transparency;
        // final NinePatchIcon npi = NinePatchUtils.getShadeIcon ( shadeWidth,
        // 0, opacity );
        // final Dimension ps = npi.getPreferredSize ();
        // npi.paintIcon ( g2d, -shadeWidth * 2, shadeWidth - ps.height,
        // vr.width + shadeWidth * 4, ps.height );
        // }
        //
        // final int bottomY = vr.y + vr.height;
        // final int height = view.getHeight ();
        // if ( bottomY < height )
        // {
        // final float max = ( height - bottomY ) / 2;
        // final float opacity = ( shadeWidth < max ? 1f : ( 1f - ( shadeWidth -
        // max ) / shadeWidth ) ) * transparency;
        // final NinePatchIcon npi = NinePatchUtils.getShadeIcon ( shadeWidth,
        // 0, opacity );
        // final Dimension ps = npi.getPreferredSize ();
        // npi.paintIcon ( g2d, -shadeWidth * 2, vr.height - shadeWidth,
        // vr.width + shadeWidth * 4, ps.height );
        // }
        // }
        // }
        // } );
        // scrollpane.add ( shadeLayer, scrollpane.getComponentCount () );
        // scrollpane.setLayout ( new WebScrollPaneLayout.UIResource (
        // shadeLayer ) );
        
        // Border
        updateBorder();
        
        // Styled scroll pane corner
        scrollpane.setCorner(JScrollPane.LOWER_LEADING_CORNER,
                getLowerLeadingCorner());
        scrollpane.setCorner(JScrollPane.LOWER_TRAILING_CORNER,
                getLowerTrailingCorner());
        scrollpane.setCorner(JScrollPane.UPPER_TRAILING_CORNER,
                getUpperTrailing());
        propertyChangeListener = new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent evt) {
                scrollpane.setCorner(JScrollPane.LOWER_LEADING_CORNER,
                        getLowerLeadingCorner());
                scrollpane.setCorner(JScrollPane.LOWER_TRAILING_CORNER,
                        getLowerTrailingCorner());
                scrollpane.setCorner(JScrollPane.UPPER_TRAILING_CORNER,
                        getUpperTrailing());
            }
        };
        scrollpane.addPropertyChangeListener(
                FlatLafConstants.ORIENTATION_PROPERTY, propertyChangeListener);
        
        // Focus tracker for the scroll pane content
        focusTracker = new DefaultFocusTracker() {
            @Override
            public boolean isTrackingEnabled() {
                return drawBorder && drawFocus;
            }
            
            @Override
            public void focusChanged(final boolean focused) {
                WebScrollPaneUI.this.focused = focused;
                scrollpane.repaint();
            }
        };
        FocusManager.addFocusTracker(scrollpane, focusTracker);
    }
    
    @Override
    public void uninstallUI(final JComponent c) {
        scrollpane.removePropertyChangeListener(
                FlatLafConstants.ORIENTATION_PROPERTY, propertyChangeListener);
        scrollpane.remove(getLowerLeadingCorner());
        scrollpane.remove(getLowerTrailingCorner());
        scrollpane.remove(getUpperTrailing());
        
        FocusManager.removeFocusTracker(focusTracker);
        
        super.uninstallUI(c);
    }
    
    private WebScrollPaneCorner getLowerLeadingCorner() {
        if (lowerLeading == null) {
            lowerLeading = new WebScrollPaneCorner(
                    JScrollPane.LOWER_LEADING_CORNER);
        }
        return lowerLeading;
    }
    
    private WebScrollPaneCorner getLowerTrailingCorner() {
        if (lowerTrailing == null) {
            lowerTrailing = new WebScrollPaneCorner(
                    JScrollPane.LOWER_TRAILING_CORNER);
        }
        return lowerTrailing;
    }
    
    private WebScrollPaneCorner getUpperTrailing() {
        if (upperTrailing == null) {
            upperTrailing = new WebScrollPaneCorner(
                    JScrollPane.UPPER_TRAILING_CORNER);
        }
        return upperTrailing;
    }
    
    @Override
    public Shape provideShape() {
        return LafUtils.getWebBorderShape(scrollpane, getShadeWidth(),
                getRound());
    }
    
    private void updateBorder() {
        if (scrollpane != null) {
            // Preserve old borders
            if (SwingUtils.isPreserveBorders(scrollpane)) {
                return;
            }
            
            final Insets insets;
            if (drawBorder) {
                insets = new Insets(shadeWidth + 1 + margin.top, shadeWidth + 1
                        + margin.left, shadeWidth + 1 + margin.bottom,
                        shadeWidth + 1 + margin.right);
            } else {
                insets = new Insets(margin.top, margin.left, margin.bottom,
                        margin.right);
            }
            scrollpane.setBorder(LafUtils.createWebBorder(insets));
        }
    }
    
    public boolean isDrawBorder() {
        return drawBorder;
    }
    
    public void setDrawBorder(final boolean drawBorder) {
        this.drawBorder = drawBorder;
        updateBorder();
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
    
    public Insets getMargin() {
        return margin;
    }
    
    public void setMargin(final Insets margin) {
        this.margin = margin;
        updateBorder();
    }
    
    public boolean isDrawFocus() {
        return drawFocus;
    }
    
    public void setDrawFocus(final boolean drawFocus) {
        this.drawFocus = drawFocus;
    }
    
    public boolean isDrawBackground() {
        return drawBackground;
    }
    
    public void setDrawBackground(final boolean drawBackground) {
        this.drawBackground = drawBackground;
    }
    
    public Color getBorderColor() {
        return borderColor;
    }
    
    public void setBorderColor(final Color borderColor) {
        this.borderColor = borderColor;
    }
    
    public Color getDarkBorder() {
        return darkBorder;
    }
    
    public void setDarkBorder(final Color darkBorder) {
        this.darkBorder = darkBorder;
    }
    
    @Override
    public void paint(final Graphics g, final JComponent c) {
        if (drawBorder) {
            // Border, background and shade
            LafUtils.drawWebStyle(
                    (Graphics2D) g,
                    c,
                    drawFocus && focused ? FlatLafStyleConstants.fieldFocusColor
                            : FlatLafStyleConstants.shadeColor, shadeWidth,
                    round, drawBackground, false);
        }
        
        super.paint(g, c);
    }
}