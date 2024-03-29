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

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import org.ocsoft.flatlaf.core.constants.FlatLafConstants;
import org.ocsoft.flatlaf.extended.image.WebImage;
import org.ocsoft.flatlaf.extended.painter.AbstractPainter;
import org.ocsoft.flatlaf.extended.window.PopOverAlignment;
import org.ocsoft.flatlaf.extended.window.PopOverDirection;
import org.ocsoft.flatlaf.extended.window.WebPopOver;
import org.ocsoft.flatlaf.managers.hotkey.Hotkey;
import org.ocsoft.flatlaf.managers.hotkey.HotkeyManager;
import org.ocsoft.flatlaf.managers.hotkey.HotkeyRunnable;
import org.ocsoft.flatlaf.utils.ColorUtils;
import org.ocsoft.flatlaf.utils.ImageUtils;
import org.ocsoft.flatlaf.utils.SwingUtils;
import org.ocsoft.flatlaf.utils.swing.ChooserListener;
import org.ocsoft.flatlaf.utils.system.FlatLafLogger;
import org.ocsoft.flatlaf.weblaf.button.WebButton;
import org.ocsoft.flatlaf.weblaf.colorchooser.ColorChooserListener;
import org.ocsoft.flatlaf.weblaf.colorchooser.WebColorChooserPanel;
import org.ocsoft.flatlaf.weblaf.label.WebLabel;
import org.ocsoft.flatlaf.weblaf.panel.WebPanel;
import org.ocsoft.flatlaf.weblaf.rootpane.WebWindow;
import org.ocsoft.flatlaf.weblaf.text.WebTextField;

/**
 * Custom field that offers various ways to select Color. Color can be typed,
 * selected from popup palette or using eyedropper tool.
 *
 * @author Mikle Garin
 */

public class WebColorChooserField extends WebTextField {
    /**
     * todo 1. Make possible color's alpha channel selection
     */
    
    /**
     * Used icons.
     */
    public static final ImageIcon eyedropperIcon = new ImageIcon(
            WebColorChooserField.class.getResource("icons/eyedropper.png"));
    
    /**
     * Color display type.
     *
     * @see org.ocsoft.flatlaf.extended.colorchooser.ColorDisplayType
     */
    protected ColorDisplayType colorDisplayType;
    
    /**
     * Whether should display eyedropper tool or not.
     */
    protected boolean displayEyedropper = true;
    
    /**
     * Size of the eyedropper image side displayed within popup in pixels. Each
     * of these pixels will also be enlarged according to
     * eyedropperImagePixelSize value.
     */
    protected final int eyedropperImageSide = 9;
    
    /**
     * Size of each pixel of eyedropper image displayed within popup.
     */
    protected final int eyedropperImagePixelSize = 11;
    
    /**
     * Most recent valid color text.
     */
    protected String lastCorrectColorText = "";
    
    /**
     * Currently selected color.
     */
    protected Color color;
    
    /**
     * UI elements.
     */
    protected final WebButton colorButton;
    protected Robot robot;
    protected WebImage eyedropperPicker;
    protected WebPopOver popup;
    protected WebColorChooserPanel colorChooserPanel;
    
    public WebColorChooserField() {
        this(Color.WHITE);
    }
    
    public WebColorChooserField(final Color color) {
        super();
        
        // Field settings
        updateMargin();
        setHorizontalAlignment(CENTER);
        
        // Eyedropper tool
        updateEyedropper();
        
        // Trailing color choose button
        colorButton = new WebButton(ImageUtils.createColorChooserIcon(color));
        colorButton.setFocusable(false);
        colorButton.setShadeWidth(0);
        colorButton.setMoveIconOnPress(false);
        colorButton.setRolloverDecoratedOnly(true);
        colorButton.setCursor(Cursor.getDefaultCursor());
        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                showColorChooserPopup();
            }
        });
        setTrailingComponent(colorButton);
        
        // Color update lsiteners
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                updateColorFromField();
            }
        });
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent e) {
                updateColorFromField();
            }
        });
        
        // Popup display listeners
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(final MouseEvent e) {
                if (isEnabled() && SwingUtilities.isRightMouseButton(e)) {
                    showColorChooserPopup();
                }
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(final KeyEvent e) {
                if (isEnabled()) {
                    if (Hotkey.ESCAPE.isTriggered(e)) {
                        updateViewFromColor();
                    } else if (Hotkey.DOWN.isTriggered(e)) {
                        showColorChooserPopup();
                    }
                }
            }
        });
        
        // Initial data
        setColorDisplayType(ColorDisplayType.rgb);
        setColorImpl(color);
    }
    
    public boolean isDisplayEyedropper() {
        return displayEyedropper;
    }
    
    public void setDisplayEyedropper(final boolean displayEyedropper) {
        this.displayEyedropper = displayEyedropper;
        updateEyedropper();
    }
    
    public Color getColor() {
        return color;
    }
    
    public void setColor(final Color color) {
        setColorImpl(color);
    }
    
    protected void setColorImpl(final Color color) {
        final Color oldColor = this.color;
        this.color = color;
        updateViewFromColor();
        fireColorSelected(oldColor, color);
    }
    
    public ColorDisplayType getColorDisplayType() {
        return colorDisplayType;
    }
    
    public void setColorDisplayType(final ColorDisplayType colorDisplayType) {
        this.colorDisplayType = colorDisplayType;
        updateFieldType();
    }
    
    @Override
    public void setDrawBorder(final boolean drawBorder) {
        super.setDrawBorder(drawBorder);
        updateMargin();
    }
    
    /**
     * protected update methods
     */
    
    protected void updateViewFromColor() {
        colorButton.setIcon(ImageUtils.createColorChooserIcon(color));
        updateText();
    }
    
    protected void updateColorFromField() {
        final String current = getText();
        if (!current.equals(lastCorrectColorText)) {
            try {
                final boolean hex = colorDisplayType
                        .equals(ColorDisplayType.hex);
                Color newColor = hex ? ColorUtils.parseHexColor(current)
                        : ColorUtils.parseRgbColor(current);
                if (newColor != null) {
                    // todo Ignoring alpha for now
                    newColor = ColorUtils.removeAlpha(newColor);
                    
                    // Apply new value
                    setColorImpl(newColor);
                } else {
                    // Restore old value
                    updateViewFromColor();
                }
            } catch (final Throwable e) {
                // Restore old value
                updateViewFromColor();
            }
        }
    }
    
    protected void updateFieldType() {
        if (colorDisplayType != null) {
            final boolean hex = colorDisplayType.equals(ColorDisplayType.hex);
            setColumns(hex ? 6 : 9);
            updateText();
        }
    }
    
    protected void updateText() {
        if (color != null) {
            final String text = getColorText(color);
            setText(text);
            lastCorrectColorText = text;
        }
    }
    
    protected String getColorText(final Color color) {
        final boolean hex = colorDisplayType.equals(ColorDisplayType.hex);
        return hex ? ColorUtils.getHexColor(color) : color.getRed() + ","
                + color.getGreen() + "," + color.getBlue();
    }
    
    protected void updateMargin() {
        setMargin(isDrawBorder() ? new Insets(-1, 0, -1, -1) : new Insets(0, 0,
                0, 0));
    }
    
    /**
     * Eyedropper chooser
     */
    
    protected void updateEyedropper() {
        if (displayEyedropper) {
            installEyedropper();
        } else {
            uninstallEyedropper();
        }
        revalidate();
    }
    
    protected void installEyedropper() {
        if (eyedropperPicker == null) {
            // Eyedropper picker icon
            eyedropperPicker = new WebImage(eyedropperIcon);
            // eyedropperPicker.setMargin ( 0, 2, 0, 2 );
            
            // Eyedropper picker actions
            try {
                robot = new Robot();
            } catch (final AWTException e) {
                FlatLafLogger.error(this, e);
            }
            if (robot != null) {
                final MouseAdapter mouseAdapter = new MouseAdapter() {
                    private boolean shouldUpdateColor;
                    
                    private WebWindow window;
                    private WebPanel screen;
                    private WebLabel info;
                    
                    private boolean updating = false;
                    private BufferedImage screenshot;
                    private Color color;
                    
                    @Override
                    public void mousePressed(final MouseEvent e) {
                        if (displayEyedropper
                                && SwingUtils.isLeftMouseButton(e)) {
                            // Resetting color update mark
                            shouldUpdateColor = true;
                            
                            // Creating preview window
                            createPreviewWindow();
                            updateWindowLocation();
                            
                            // Displaying preview window
                            window.pack();
                            window.setVisible(true);
                            
                            // Transferring focus to preview panel
                            screen.requestFocus();
                            
                            // Updating preview screenshot
                            updateScreenshot();
                        }
                    }
                    
                    @Override
                    public void mouseDragged(final MouseEvent e) {
                        if (displayEyedropper
                                && SwingUtils.isLeftMouseButton(e)
                                && window != null) {
                            // Updating preview window location
                            updateWindowLocation();
                            
                            // Updating preview screenshot
                            updateScreenshot();
                        }
                    }
                    
                    @Override
                    public void mouseReleased(final MouseEvent e) {
                        if (displayEyedropper
                                && SwingUtils.isLeftMouseButton(e)
                                && window != null) {
                            // Closing preview window
                            window.dispose();
                        }
                    }
                    
                    private void updateScreenshot() {
                        // Simply ignore update if an old one is still running
                        if (!updating) {
                            // Updating image in a separate thread to avoid UI
                            // freezing
                            updating = true;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    if (screen != null) {
                                        final Point p = MouseInfo
                                                .getPointerInfo().getLocation();
                                        screenshot = robot
                                                .createScreenCapture(new Rectangle(
                                                        p.x
                                                                - eyedropperImageSide
                                                                / 2,
                                                        p.y
                                                                - eyedropperImageSide
                                                                / 2,
                                                        eyedropperImageSide,
                                                        eyedropperImageSide));
                                        color = new Color(screenshot.getRGB(
                                                eyedropperImageSide / 2,
                                                eyedropperImageSide / 2));
                                        if (screen != null) {
                                            screen.repaint();
                                            info.setText(getColorText(color));
                                        } else {
                                            screenshot.flush();
                                            screenshot = null;
                                            color = null;
                                        }
                                    }
                                    updating = false;
                                }
                            }).start();
                        }
                    }
                    
                    private void createPreviewWindow() {
                        window = new WebWindow(eyedropperPicker);
                        window.setLayout(new BorderLayout());
                        window.setAlwaysOnTop(true);
                        window.setFocusableWindowState(false);
                        
                        window.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(final WindowEvent e) {
                                if (screenshot != null) {
                                    if (shouldUpdateColor) {
                                        setColorImpl(color);
                                    }
                                    screenshot.flush();
                                    screenshot = null;
                                }
                                HotkeyManager.unregisterHotkeys(screen);
                                window = null;
                                screen = null;
                            }
                        });
                        
                        final AbstractPainter<WebPanel> screenPainter = new AbstractPainter<WebPanel>() {
                            /**
                             * {@inheritDoc}
                             */
                            @Override
                            public void paint(final Graphics2D g2d,
                                    final Rectangle bounds, final WebPanel c) {
                                if (window.isShowing() && robot != null) {
                                    // Screen
                                    g2d.drawImage(screenshot, bounds.x + 2,
                                            bounds.y + 2, bounds.width - 4,
                                            bounds.height - 4, null);
                                    
                                    // Border
                                    g2d.setPaint(Color.BLACK);
                                    g2d.drawRect(0, 0, bounds.width - 1,
                                            bounds.height - 1);
                                    g2d.setPaint(Color.WHITE);
                                    g2d.drawRect(1, 1, bounds.width - 3,
                                            bounds.height - 3);
                                    
                                    // Cursor
                                    final int mx = bounds.x + bounds.width / 2;
                                    final int my = bounds.y + bounds.height / 2;
                                    g2d.setPaint(Color.WHITE);
                                    g2d.drawLine(mx - 1, my - 7, mx - 1, my + 7);
                                    g2d.drawLine(mx + 1, my - 7, mx + 1, my + 7);
                                    g2d.drawLine(mx - 7, my - 1, mx + 7, my - 1);
                                    g2d.drawLine(mx - 7, my + 1, mx + 7, my + 1);
                                    g2d.setPaint(Color.BLACK);
                                    g2d.drawLine(mx, my - 7, mx, my + 7);
                                    g2d.drawLine(mx - 7, my, mx + 7, my);
                                }
                            }
                        };
                        
                        screen = new WebPanel(screenPainter);
                        screen.setFocusable(true);
                        screen.setPreferredSize(new Dimension(
                                eyedropperImageSide * eyedropperImagePixelSize
                                        + 4, eyedropperImageSide
                                        * eyedropperImagePixelSize + 4));
                        window.add(screen, BorderLayout.CENTER);
                        
                        info = new WebLabel(WebLabel.LEADING);
                        info.setMargin(4);
                        info.setIcon(new Icon() {
                            @Override
                            public void paintIcon(final Component c,
                                    final Graphics g, final int x, final int y) {
                                if (color != null) {
                                    final Graphics2D g2d = (Graphics2D) g;
                                    g2d.setPaint(Color.BLACK);
                                    g2d.drawRect(x, y, 15, 15);
                                    g2d.setPaint(Color.WHITE);
                                    g2d.drawRect(x + 1, y + 1, 13, 13);
                                    g2d.setPaint(color);
                                    g2d.fillRect(x + 2, y + 2, 12, 12);
                                }
                            }
                            
                            @Override
                            public int getIconWidth() {
                                return 16;
                            }
                            
                            @Override
                            public int getIconHeight() {
                                return 16;
                            }
                        });
                        info.setPainter(new AbstractPainter<WebLabel>() {
                            /**
                             * {@inheritDoc}
                             */
                            @Override
                            public Insets getMargin(final WebLabel c) {
                                return new Insets(0, 2, 2, 2);
                            }
                            
                            /**
                             * {@inheritDoc}
                             */
                            @Override
                            public void paint(final Graphics2D g2d,
                                    final Rectangle bounds, final WebLabel c) {
                                g2d.setPaint(Color.BLACK);
                                g2d.drawRect(bounds.x, bounds.y - 1,
                                        bounds.width - 1, bounds.height);
                            }
                        });
                        window.add(info, BorderLayout.SOUTH);
                        
                        HotkeyManager.registerHotkey(screen, Hotkey.ESCAPE,
                                new HotkeyRunnable() {
                                    @Override
                                    public void run(final KeyEvent e) {
                                        if (window != null) {
                                            shouldUpdateColor = false;
                                            window.dispose();
                                        }
                                    }
                                });
                    }
                    
                    private void updateWindowLocation() {
                        final Point p = MouseInfo.getPointerInfo()
                                .getLocation();
                        final Rectangle b = window.getGraphicsConfiguration()
                                .getDevice().getDefaultConfiguration()
                                .getBounds();
                        final int ww = window.getWidth();
                        final int wh = window.getHeight();
                        final int x = p.x + 20 + ww < b.x + b.width ? p.x + 20
                                : p.x - 20 - ww;
                        final int y = p.y + 20 + wh < b.y + b.height ? p.y + 20
                                : p.y - 20 - wh;
                        window.setLocation(x, y);
                    }
                };
                eyedropperPicker.addMouseListener(mouseAdapter);
                eyedropperPicker.addMouseMotionListener(mouseAdapter);
                eyedropperPicker.setCursor(Cursor.getDefaultCursor());
            }
        }
        
        // Adding field leading component
        setLeadingComponent(eyedropperPicker);
    }
    
    protected void uninstallEyedropper() {
        // Removing field leading component
        setLeadingComponent(null);
    }
    
    /**
     * Color chooser popup
     */
    
    protected void showColorChooserPopup() {
        // Checking that component is eligable for focus request
        if (!requestFocusInWindow() && !isFocusOwner()) {
            // Cancel operation if component is not eligable for focus yet
            // This might occur if some other component input verifier holds the
            // focus or in some other rare cases
            return;
        }
        
        // Update date from field if it was changed
        updateColorFromField();
        
        // Create popup if it doesn't exist
        if (popup == null || colorChooserPanel == null) {
            final Window ancestor = SwingUtils.getWindowAncestor(this);
            
            // Color chooser
            colorChooserPanel = new WebColorChooserPanel(true);
            colorChooserPanel.setColor(color);
            
            // Popup window
            popup = new WebPopOver(ancestor);
            popup.setMargin(5);
            popup.setCloseOnFocusLoss(true);
            popup.add(colorChooserPanel);
            
            // Correct popup positioning
            updatePopupLocation();
            ancestor.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentMoved(final ComponentEvent e) {
                    if (popup.isShowing()) {
                        updatePopupLocation();
                    }
                }
                
                @Override
                public void componentResized(final ComponentEvent e) {
                    if (popup.isShowing()) {
                        updatePopupLocation();
                    }
                }
            });
            ancestor.addPropertyChangeListener(
                    FlatLafConstants.ORIENTATION_PROPERTY,
                    new PropertyChangeListener() {
                        @Override
                        public void propertyChange(final PropertyChangeEvent evt) {
                            if (popup.isShowing()) {
                                updatePopupLocation();
                            }
                        }
                    });
            
            colorChooserPanel
                    .addColorChooserListener(new ColorChooserListener() {
                        @Override
                        public void okPressed(final ActionEvent e) {
                            setColorImpl(colorChooserPanel.getColor());
                            popup.setVisible(false);
                        }
                        
                        @Override
                        public void resetPressed(final ActionEvent e) {
                            
                        }
                        
                        @Override
                        public void cancelPressed(final ActionEvent e) {
                            popup.setVisible(false);
                        }
                    });
        } else {
            // Updating window location
            updatePopupLocation();
            
            // Updating color
            colorChooserPanel.setColor(color);
        }
        
        // Applying orientation to popup
        SwingUtils.copyOrientation(WebColorChooserField.this, popup);
        
        // Showing popup and changing focus
        // popup.setVisible ( true );
        popup.show(this, PopOverDirection.down, PopOverAlignment.centered);
        colorChooserPanel.requestFocusInWindow();
    }
    
    protected void updatePopupLocation() {
        final Point los = WebColorChooserField.this.getLocationOnScreen();
        final Rectangle gb = popup.getGraphicsConfiguration().getBounds();
        final int shadeWidth = isDrawBorder() ? getShadeWidth() : 0;
        final boolean ltr = WebColorChooserField.this.getComponentOrientation()
                .isLeftToRight();
        final int w = WebColorChooserField.this.getWidth();
        final int h = WebColorChooserField.this.getHeight();
        
        final int x;
        if (ltr) {
            if (los.x + shadeWidth + popup.getWidth() <= gb.x + gb.width) {
                x = los.x + shadeWidth;
            } else {
                x = los.x + w - shadeWidth - popup.getWidth();
            }
        } else {
            if (los.x + w - shadeWidth - popup.getWidth() >= gb.x) {
                x = los.x + w - shadeWidth - popup.getWidth();
            } else {
                x = los.x + shadeWidth;
            }
        }
        
        final int y;
        if (los.y + h + popup.getHeight() <= gb.y + gb.height) {
            y = los.y + h + (isDrawBorder() ? 0 : 1);
        } else {
            y = los.y - popup.getHeight() - (isDrawBorder() ? 0 : 1);
        }
        
        popup.setLocation(x, y);
    }
    
    /**
     * Adds new color selection listener.
     *
     * @param listener
     *            new color selection listener
     */
    public void addColorListener(final ChooserListener<Color> listener) {
        listenerList.add(ChooserListener.class, listener);
    }
    
    /**
     * Removes color selection listener.
     *
     * @param listener
     *            color selection listener to remove
     */
    public void removeColorListener(final ChooserListener<Color> listener) {
        listenerList.remove(ChooserListener.class, listener);
    }
    
    /**
     * Informs about color selection.
     *
     * @param oldColor
     *            previously selected Color
     * @param newColor
     *            new selected Color
     */
    public void fireColorSelected(final Color oldColor, final Color newColor) {
        for (final ChooserListener listener : listenerList
                .getListeners(ChooserListener.class)) {
            listener.selected(oldColor, newColor);
        }
    }
}