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

package org.ocsoft.flatlaf.extended.panel;

import static javax.swing.SwingConstants.EAST;
import static javax.swing.SwingConstants.NORTH;
import static javax.swing.SwingConstants.NORTH_EAST;
import static javax.swing.SwingConstants.NORTH_WEST;
import static javax.swing.SwingConstants.SOUTH;
import static javax.swing.SwingConstants.SOUTH_EAST;
import static javax.swing.SwingConstants.SOUTH_WEST;
import static javax.swing.SwingConstants.WEST;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import org.ocsoft.flatlaf.utils.LafUtils;
import org.ocsoft.flatlaf.weblaf.panel.WebPanel;

/**
 * User: mgarin Date: 23.12.11 Time: 17:28
 */

public class ResizablePanel extends WebPanel {
    public static final int SIZER = 12;
    
    private int widthChange = 0;
    private int heightChange = 0;
    
    private int resizeCorner = -1;
    private boolean resizing = false;
    private int startX = -1;
    private int startY = -1;
    
    private Insets innerSpacing = new Insets(SIZER, SIZER, SIZER, SIZER);
    
    public ResizablePanel() {
        this(null);
    }
    
    public ResizablePanel(Component component) {
        super();
        
        setOpaque(false);
        updateSpacing();
        
        if (component != null) {
            setLayout(new BorderLayout());
            add(component, BorderLayout.CENTER);
        }
        
        MouseAdapter mouseAdapter = new MouseAdapter() {
            private void updateResizeType(MouseEvent e) {
                if (new Rectangle(0, 0, SIZER, SIZER).contains(e.getPoint())) {
                    resizeCorner = NORTH_WEST;
                    setCursor(Cursor
                            .getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
                } else if (new Rectangle(getWidth() / 2 - SIZER / 2, 0, SIZER,
                        SIZER).contains(e.getPoint())) {
                    resizeCorner = NORTH;
                    setCursor(Cursor
                            .getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                } else if (new Rectangle(getWidth() - SIZER, 0, SIZER, SIZER)
                        .contains(e.getPoint())) {
                    resizeCorner = NORTH_EAST;
                    setCursor(Cursor
                            .getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
                } else if (new Rectangle(0, getHeight() / 2 - SIZER / 2, SIZER,
                        SIZER).contains(e.getPoint())) {
                    resizeCorner = WEST;
                    setCursor(Cursor
                            .getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
                } else if (new Rectangle(getWidth() - SIZER, getHeight() / 2
                        - SIZER / 2, SIZER, SIZER).contains(e.getPoint())) {
                    resizeCorner = EAST;
                    setCursor(Cursor
                            .getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                } else if (new Rectangle(0, getHeight() - SIZER, SIZER, SIZER)
                        .contains(e.getPoint())) {
                    resizeCorner = SOUTH_WEST;
                    setCursor(Cursor
                            .getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
                } else if (new Rectangle(getWidth() / 2 - SIZER / 2,
                        getHeight() - SIZER, SIZER, SIZER).contains(e
                        .getPoint())) {
                    resizeCorner = SOUTH;
                    setCursor(Cursor
                            .getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
                } else if (new Rectangle(getWidth() - SIZER, getHeight()
                        - SIZER, SIZER, SIZER).contains(e.getPoint())) {
                    resizeCorner = SOUTH_EAST;
                    setCursor(Cursor
                            .getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
                } else {
                    resizeCorner = -1;
                    setCursor(Cursor.getDefaultCursor());
                }
            }
            
            @Override
            public void mouseMoved(MouseEvent e) {
                updateResizeType(e);
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                updateResizeType(e);
                if (SwingUtilities.isLeftMouseButton(e) && resizeCorner != -1) {
                    resizing = true;
                }
                startX = e.getXOnScreen();
                startY = e.getYOnScreen();
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
                if (resizing && startX != -1 && startY != -1) {
                    widthChange += (startX - e.getXOnScreen())
                            * 2
                            * (resizeCorner == NORTH || resizeCorner == SOUTH ? 0
                                    : (resizeCorner == NORTH_WEST
                                            || resizeCorner == WEST
                                            || resizeCorner == SOUTH_WEST ? 1
                                            : -1));
                    widthChange = Math.max(widthChange, 0);
                    
                    heightChange += (startY - e.getYOnScreen())
                            * 2
                            * (resizeCorner == WEST || resizeCorner == EAST ? 0
                                    : (resizeCorner == NORTH_WEST
                                            || resizeCorner == NORTH
                                            || resizeCorner == NORTH_EAST ? 1
                                            : -1));
                    heightChange = Math.max(heightChange, 0);
                    
                    revalidate();
                }
                startX = e.getXOnScreen();
                startY = e.getYOnScreen();
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    resizing = false;
                }
                updateResizeType(e);
            }
            
        };
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }
    
    private void updateSpacing() {
        setMargin(innerSpacing.top, innerSpacing.left, innerSpacing.bottom,
                innerSpacing.right);
    }
    
    public Insets getInnerSpacing() {
        return innerSpacing;
    }
    
    public void setInnerSpacing(int innerSpacing) {
        setInnerSpacing(new Insets(innerSpacing, innerSpacing, innerSpacing,
                innerSpacing));
    }
    
    public void setInnerSpacing(Insets innerSpacing) {
        this.innerSpacing = innerSpacing;
        updateSpacing();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        LafUtils.drawWebIconedSelection((Graphics2D) g, new Rectangle(6, 6,
                getWidth() - 13, getHeight() - 13), true, true, true);
    }
    
    @Override
    public Dimension getPreferredSize() {
        Dimension ps = super.getPreferredSize();
        ps.width = ps.width + widthChange;
        ps.height = ps.height + heightChange;
        return ps;
    }
    
    public void reset() {
        widthChange = 0;
        heightChange = 0;
        revalidate();
    }
}
