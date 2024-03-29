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

package org.ocsoft.flatlaf.laf.combobox;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ComboBoxEditor;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.ListUI;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.ScrollPaneUI;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

import org.ocsoft.flatlaf.core.FlatLookAndFeel;
import org.ocsoft.flatlaf.core.constants.FlatLafConstants;
import org.ocsoft.flatlaf.extended.layout.AbstractLayoutManager;
import org.ocsoft.flatlaf.laf.list.WebListUI;
import org.ocsoft.flatlaf.laf.scroll.WebScrollBarUI;
import org.ocsoft.flatlaf.laf.scroll.WebScrollPaneUI;
import org.ocsoft.flatlaf.laf.text.WebTextFieldUI;
import org.ocsoft.flatlaf.utils.LafUtils;
import org.ocsoft.flatlaf.utils.SwingUtils;
import org.ocsoft.flatlaf.utils.laf.ShapeProvider;
import org.ocsoft.flatlaf.utils.swing.BorderMethods;
import org.ocsoft.flatlaf.utils.swing.RendererListener;
import org.ocsoft.flatlaf.utils.swing.WebDefaultCellEditor;
import org.ocsoft.flatlaf.weblaf.FlatLafStyleConstants;
import org.ocsoft.flatlaf.weblaf.button.WebButton;
import org.ocsoft.flatlaf.weblaf.combobox.WebComboBoxCellRenderer;
import org.ocsoft.flatlaf.weblaf.combobox.WebComboBoxStyle;

/**
 * Custom UI for JComboBox component.
 *
 * @author Mikle Garin
 */

public class FlatComboBoxUI extends BasicComboBoxUI implements ShapeProvider,
        BorderMethods {
    /**
     * Default combobox renderer.
     */
    protected static ListCellRenderer DEFAULT_RENDERER;
    
    private ImageIcon expandIcon = WebComboBoxStyle.expandIcon;
    private ImageIcon collapseIcon = WebComboBoxStyle.collapseIcon;
    private int iconSpacing = WebComboBoxStyle.iconSpacing;
    private boolean drawBorder = WebComboBoxStyle.drawBorder;
    private boolean webColoredBackground = WebComboBoxStyle.webColoredBackground;
    private Color expandedBgColor = WebComboBoxStyle.expandedBgColor;
    private int round = WebComboBoxStyle.round;
    private int shadeWidth = WebComboBoxStyle.shadeWidth;
    private boolean drawFocus = WebComboBoxStyle.drawFocus;
    private boolean mouseWheelScrollingEnabled = WebComboBoxStyle.mouseWheelScrollingEnabled;
    private boolean useFirstValueAsPrototype = false;
    
    private WebButton arrow = null;
    private MouseWheelListener mouseWheelListener = null;
    private RendererListener rendererListener = null;
    private PropertyChangeListener rendererChangeListener = null;
    private PropertyChangeListener enabledStateListener = null;
    
    
    public static ComponentUI createUI(final JComponent c) {
        return new FlatComboBoxUI();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void installUI(final JComponent c) {
        super.installUI(c);
        
        // Default settings
        SwingUtils.setOrientation(comboBox);
        comboBox.setFocusable(true);
        LookAndFeel.installProperty(comboBox, FlatLafConstants.OPAQUE_PROPERTY,
                Boolean.FALSE);
        
        // Updating border
        updateBorder();
        
        // Drfault renderer
        if (!(comboBox.getRenderer() instanceof WebComboBoxCellRenderer)) {
            comboBox.setRenderer(new WebComboBoxCellRenderer());
        }
        
        // Rollover scrolling listener
        mouseWheelListener = new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(final MouseWheelEvent e) {
                if (mouseWheelScrollingEnabled && comboBox.isEnabled()
                        && isFocused()) {
                    // Changing selection in case index actually changed
                    final int index = comboBox.getSelectedIndex();
                    final int newIndex = Math.min(Math.max(0,
                            index + e.getWheelRotation()), comboBox.getModel()
                            .getSize() - 1);
                    if (newIndex != index) {
                        comboBox.setSelectedIndex(newIndex);
                    }
                }
            }
        };
        comboBox.addMouseWheelListener(mouseWheelListener);
        
        // Renderer change listener
        // Used to provide feedback from the renderer
        rendererListener = new RendererListener() {
            @Override
            public void repaint() {
                comboBox.repaint();
                listBox.repaint();
            }
            
            @Override
            public void revalidate() {
                isMinimumSizeDirty = true;
                comboBox.revalidate();
                listBox.revalidate();
            }
        };
        installRendererListener(comboBox.getRenderer());
        rendererChangeListener = new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent e) {
                uninstallRendererListener(e.getOldValue());
                installRendererListener(e.getNewValue());
            }
        };
        comboBox.addPropertyChangeListener(FlatLafConstants.RENDERER_PROPERTY,
                rendererChangeListener);
        
        // Enabled property change listener
        // This is a workaround to allow box renderer properly inherit enabled
        // state
        enabledStateListener = new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent evt) {
                listBox.setEnabled(comboBox.isEnabled());
            }
        };
        comboBox.addPropertyChangeListener(FlatLafConstants.ENABLED_PROPERTY,
                enabledStateListener);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void uninstallUI(final JComponent c) {
        comboBox.removePropertyChangeListener(FlatLafConstants.ENABLED_PROPERTY,
                enabledStateListener);
        
        comboBox.removePropertyChangeListener(
                FlatLafConstants.RENDERER_PROPERTY, rendererChangeListener);
        uninstallRendererListener(comboBox.getRenderer());
        rendererListener = null;
        
        c.removeMouseWheelListener(mouseWheelListener);
        mouseWheelListener = null;
        
        arrow = null;
        
        super.uninstallUI(c);
    }
    
    @Override
    public void updateBorder() {
        // Preserve old borders
        if (SwingUtils.isPreserveBorders(comboBox)) {
            return;
        }
        
        final Insets m = new Insets(0, 0, 0, 0);
        if (drawBorder) {
            m.top += shadeWidth + 1;
            m.left += shadeWidth + 1;
            m.bottom += shadeWidth + 1;
            m.right += shadeWidth + 1;
        }
        comboBox.setBorder(LafUtils.createWebBorder(m));
    }
    
    /**
     * Installs RendererListener into specified renderer if possible.
     *
     * @param renderer
     *            RendererListener to install
     */
    protected void installRendererListener(final Object renderer) {
        if (renderer != null && renderer instanceof WebComboBoxCellRenderer) {
            ((WebComboBoxCellRenderer) renderer)
                    .addRendererListener(rendererListener);
        }
    }
    
    /**
     * Uninstalls RendererListener from specified renderer if possible.
     *
     * @param renderer
     *            RendererListener to uninstall
     */
    protected void uninstallRendererListener(final Object renderer) {
        if (renderer != null && renderer instanceof WebComboBoxCellRenderer) {
            ((WebComboBoxCellRenderer) renderer)
                    .removeRendererListener(rendererListener);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void installComponents() {
        comboBox.setLayout(createLayoutManager());
        
        arrowButton = createArrowButton();
        comboBox.add(arrowButton, "1,0");
        if (arrowButton != null) {
            configureArrowButton();
        }
        
        if (comboBox.isEditable()) {
            addEditor();
        }
        
        comboBox.add(currentValuePane, "0,0");
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected ComboBoxEditor createEditor() {
        final ComboBoxEditor editor = super.createEditor();
        final Component e = editor.getEditorComponent();
        e.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(final FocusEvent e) {
                comboBox.repaint();
            }
            
            @Override
            public void focusLost(final FocusEvent e) {
                comboBox.repaint();
            }
        });
        if (e instanceof JComponent) {
            ((JComponent) e).setOpaque(false);
        }
        if (e instanceof JTextField) {
            final JTextField textField = (JTextField) e;
            final WebTextFieldUI textFieldUI = new WebTextFieldUI();
            textFieldUI.setDrawBorder(false);
            textField.setUI(textFieldUI);
            textField.setMargin(new Insets(0, 1, 0, 1));
        }
        return editor;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected JButton createArrowButton() {
        arrow = new WebButton();
        arrow.setUndecorated(true);
        arrow.setDrawFocus(false);
        arrow.setMoveIconOnPress(false);
        arrow.setName("ComboBox.arrowButton");
        arrow.setIcon(expandIcon);
        arrow.setLeftRightSpacing(iconSpacing);
        return arrow;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void configureArrowButton() {
        super.configureArrowButton();
        if (arrowButton != null) {
            arrowButton.setFocusable(false);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected ComboPopup createPopup() {
        return new BasicComboPopup(comboBox) {
            @Override
            protected JScrollPane createScroller() {
                final JScrollPane scroll = super.createScroller();
                if (FlatLookAndFeel.isInstalled()) {
                    scroll.setOpaque(false);
                    scroll.getViewport().setOpaque(false);
                }
                
                final ScrollPaneUI scrollPaneUI = scroll.getUI();
                if (scrollPaneUI instanceof WebScrollPaneUI) {
                    final WebScrollPaneUI webScrollPaneUI = (WebScrollPaneUI) scrollPaneUI;
                    webScrollPaneUI.setDrawBorder(false);
                    
                    final ScrollBarUI scrollBarUI = scroll
                            .getVerticalScrollBar().getUI();
                    if (scrollBarUI instanceof WebScrollBarUI) {
                        final WebScrollBarUI webScrollBarUI = (WebScrollBarUI) scrollBarUI;
                        webScrollBarUI
                                .setMargin(WebComboBoxStyle.scrollBarMargin);
                        webScrollBarUI
                                .setPaintButtons(WebComboBoxStyle.scrollBarButtonsVisible);
                        webScrollBarUI
                                .setPaintTrack(WebComboBoxStyle.scrollBarTrackVisible);
                    }
                }
                
                LafUtils.setScrollBarStyleId(scroll, "combo-box");
                
                return scroll;
            }
            
            @Override
            protected JList createList() {
                final JList list = super.createList();
                // list.setOpaque ( false );
                
                final ListUI listUI = list.getUI();
                if (listUI instanceof WebListUI) {
                    final WebListUI webListUI = (WebListUI) listUI;
                    webListUI.setHighlightRolloverCell(false);
                    webListUI.setDecorateSelection(false);
                }
                
                return list;
            }
            
            @Override
            protected void configurePopup() {
                super.configurePopup();
                
                // Button updater
                addPopupMenuListener(new PopupMenuListener() {
                    @Override
                    public void popupMenuWillBecomeVisible(
                            final PopupMenuEvent e) {
                        arrow.setIcon(collapseIcon);
                        
                        // Fix for combobox repaint on popup opening
                        comboBox.repaint();
                    }
                    
                    @Override
                    public void popupMenuWillBecomeInvisible(
                            final PopupMenuEvent e) {
                        arrow.setIcon(expandIcon);
                    }
                    
                    @Override
                    public void popupMenuCanceled(final PopupMenuEvent e) {
                        arrow.setIcon(expandIcon);
                    }
                });
            }
            
            @Override
            public void show() {
                comboBox.firePopupMenuWillBecomeVisible();
                
                setListSelection(comboBox.getSelectedIndex());
                
                final Point location = getPopupLocation();
                show(comboBox, location.x, location.y);
            }
            
            private void setListSelection(final int selectedIndex) {
                if (selectedIndex == -1) {
                    list.clearSelection();
                } else {
                    list.setSelectedIndex(selectedIndex);
                    list.ensureIndexIsVisible(selectedIndex);
                }
            }
            
            private Point getPopupLocation() {
                final Dimension comboSize = comboBox.getSize();
                comboSize
                        .setSize(comboSize.width - 2,
                                getPopupHeightForRowCount(comboBox
                                        .getMaximumRowCount()));
                final Rectangle popupBounds = computePopupBounds(0,
                        comboBox.getBounds().height, comboSize.width,
                        comboSize.height);
                
                final Dimension scrollSize = popupBounds.getSize();
                scroller.setMaximumSize(scrollSize);
                scroller.setPreferredSize(scrollSize);
                scroller.setMinimumSize(scrollSize);
                list.revalidate();
                
                return popupBounds.getLocation();
            }
        };
    }
    
    public boolean isComboboxCellEditor() {
        if (comboBox != null) {
            final Object cellEditor = comboBox
                    .getClientProperty(WebDefaultCellEditor.COMBOBOX_CELL_EDITOR);
            return cellEditor != null && (Boolean) cellEditor;
        } else {
            return false;
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Shape provideShape() {
        if (drawBorder) {
            return LafUtils.getWebBorderShape(comboBox, shadeWidth, round);
        } else {
            return SwingUtils.size(comboBox);
        }
    }
    
    public void setEditorColumns(final int columns) {
        if (editor instanceof JTextField) {
            ((JTextField) editor).setColumns(columns);
        }
    }
    
    public boolean isUseFirstValueAsPrototype() {
        return useFirstValueAsPrototype;
    }
    
    public void setUseFirstValueAsPrototype(final boolean use) {
        this.useFirstValueAsPrototype = use;
        this.isMinimumSizeDirty = true;
        comboBox.revalidate();
    }
    
    public ImageIcon getExpandIcon() {
        return expandIcon;
    }
    
    public void setExpandIcon(final ImageIcon expandIcon) {
        this.expandIcon = expandIcon;
        if (arrow != null && !isPopupVisible(comboBox)) {
            arrow.setIcon(expandIcon);
        }
    }
    
    public ImageIcon getCollapseIcon() {
        return collapseIcon;
    }
    
    public void setCollapseIcon(final ImageIcon collapseIcon) {
        this.collapseIcon = collapseIcon;
        if (arrow != null && isPopupVisible(comboBox)) {
            arrow.setIcon(collapseIcon);
        }
    }
    
    public int getIconSpacing() {
        return iconSpacing;
    }
    
    public void setIconSpacing(final int iconSpacing) {
        this.iconSpacing = iconSpacing;
        if (arrow != null) {
            arrow.setLeftRightSpacing(iconSpacing);
        }
    }
    
    public boolean isDrawBorder() {
        return drawBorder;
    }
    
    public void setDrawBorder(final boolean drawBorder) {
        this.drawBorder = drawBorder;
        updateBorder();
    }
    
    public boolean isWebColoredBackground() {
        return webColoredBackground;
    }
    
    public void setWebColoredBackground(final boolean webColored) {
        this.webColoredBackground = webColored;
    }
    
    public Color getExpandedBgColor() {
        return expandedBgColor;
    }
    
    public void setExpandedBgColor(final Color color) {
        this.expandedBgColor = color;
    }
    
    public boolean isDrawFocus() {
        return drawFocus;
    }
    
    public void setDrawFocus(final boolean drawFocus) {
        this.drawFocus = drawFocus;
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
    
    public boolean isMouseWheelScrollingEnabled() {
        return mouseWheelScrollingEnabled;
    }
    
    public void setMouseWheelScrollingEnabled(final boolean enabled) {
        this.mouseWheelScrollingEnabled = enabled;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void paint(final Graphics g, final JComponent c) {
        hasFocus = comboBox.hasFocus();
        final Rectangle r = rectangleForCurrentValue();
        
        // Background
        paintCurrentValueBackground(g, r, hasFocus);
        
        // Selected uneditable value
        if (!comboBox.isEditable()) {
            paintCurrentValue(g, r, hasFocus);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void paintCurrentValueBackground(final Graphics g,
            final Rectangle bounds, final boolean hasFocus) {
        final Graphics2D g2d = (Graphics2D) g;
        
        if (drawBorder) {
            // Border and background
            final Color shadeColor = drawFocus && isFocused() ? FlatLafStyleConstants.fieldFocusColor
                    : FlatLafStyleConstants.shadeColor;
            final boolean popupVisible = isPopupVisible(comboBox);
            final Color backgroundColor = !popupVisible ? comboBox
                    .getBackground() : expandedBgColor;
            LafUtils.drawWebStyle(g2d, comboBox, shadeColor, shadeWidth, round,
                    true, webColoredBackground && !popupVisible,
                    FlatLafStyleConstants.darkBorderColor,
                    FlatLafStyleConstants.disabledBorderColor, backgroundColor,
                    1f);
        } else {
            // Simple background
            final boolean pressed = isPopupVisible(comboBox);
            final Rectangle cb = SwingUtils.size(comboBox);
            g2d.setPaint(new GradientPaint(0, shadeWidth,
                    pressed ? FlatLafStyleConstants.topSelectedBgColor
                            : FlatLafStyleConstants.topBgColor, 0, comboBox
                            .getHeight() - shadeWidth,
                    pressed ? FlatLafStyleConstants.bottomSelectedBgColor
                            : FlatLafStyleConstants.bottomBgColor));
            g2d.fillRect(cb.x, cb.y, cb.width, cb.height);
        }
        
        // Separator line
        if (comboBox.isEditable()) {
            final boolean ltr = comboBox.getComponentOrientation()
                    .isLeftToRight();
            final Insets insets = comboBox.getInsets();
            final int lx = ltr ? comboBox.getWidth() - insets.right
                    - arrow.getWidth() - 1 : insets.left + arrow.getWidth();
            
            g2d.setPaint(comboBox.isEnabled() ? FlatLafStyleConstants.borderColor
                    : FlatLafStyleConstants.disabledBorderColor);
            g2d.drawLine(lx, insets.top + 1, lx, comboBox.getHeight()
                    - insets.bottom - 2);
        }
    }
    
    /**
     * Returns whether combobox or one of its children is focused or not.
     *
     * @return true if combobox or one of its children is focused, false
     *         otherwise
     */
    protected boolean isFocused() {
        return SwingUtils.hasFocusOwner(comboBox);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void paintCurrentValue(final Graphics g, final Rectangle bounds,
            final boolean hasFocus) {
        final ListCellRenderer renderer = comboBox.getRenderer();
        final Component c;
        
        if (hasFocus && !isPopupVisible(comboBox)) {
            c = renderer.getListCellRendererComponent(listBox,
                    comboBox.getSelectedItem(), -1, true, false);
        } else {
            c = renderer.getListCellRendererComponent(listBox,
                    comboBox.getSelectedItem(), -1, false, false);
            c.setBackground(UIManager.getColor("ComboBox.background"));
        }
        c.setFont(comboBox.getFont());
        
        if (comboBox.isEnabled()) {
            c.setForeground(comboBox.getForeground());
            c.setBackground(comboBox.getBackground());
        } else {
            c.setForeground(UIManager.getColor("ComboBox.disabledForeground"));
            c.setBackground(UIManager.getColor("ComboBox.disabledBackground"));
        }
        
        boolean shouldValidate = false;
        if (c instanceof JPanel) {
            shouldValidate = true;
        }
        
        final int x = bounds.x;
        final int y = bounds.y;
        final int w = bounds.width;
        final int h = bounds.height;
        
        currentValuePane.paintComponent(g, c, comboBox, x, y, w, h,
                shouldValidate);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected LayoutManager createLayoutManager() {
        return new WebComboBoxLayout();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension getMinimumSize(final JComponent c) {
        if (!isMinimumSizeDirty) {
            return new Dimension(cachedMinimumSize);
        }
        
        final Dimension size = getDisplaySize();
        final Insets insets = getInsets();
        
        // Calculate the width the button
        final int buttonWidth = arrowButton.getPreferredSize().width;
        
        // Adjust the size based on the button width
        size.height += insets.top + insets.bottom;
        size.width += insets.left + insets.right + buttonWidth;
        
        cachedMinimumSize.setSize(size.width, size.height);
        isMinimumSizeDirty = false;
        
        return new Dimension(size);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Dimension getDisplaySize() {
        Dimension result = new Dimension();
        
        // Use default renderer
        ListCellRenderer renderer = comboBox.getRenderer();
        if (renderer == null) {
            renderer = new DefaultListCellRenderer();
        }
        
        final Object prototypeValue = comboBox.getPrototypeDisplayValue();
        if (prototypeValue != null) {
            // Calculates the dimension based on the prototype value
            result = getSizeForComponent(renderer.getListCellRendererComponent(
                    listBox, prototypeValue, -1, false, false));
        } else {
            final ComboBoxModel model = comboBox.getModel();
            final int modelSize = model.getSize();
            Dimension d;
            
            if (modelSize > 0) {
                if (useFirstValueAsPrototype) {
                    // Calculates the maximum height and width based on first
                    // element
                    final Object value = model.getElementAt(0);
                    final Component c = renderer.getListCellRendererComponent(
                            listBox, value, -1, false, false);
                    d = getSizeForComponent(c);
                    result.width = Math.max(result.width, d.width);
                    result.height = Math.max(result.height, d.height);
                } else {
                    // Calculate the dimension by iterating over all the
                    // elements in the combo box list
                    for (int i = 0; i < modelSize; i++) {
                        // Calculates the maximum height and width based on the
                        // largest element
                        final Object value = model.getElementAt(i);
                        final Component c = renderer
                                .getListCellRendererComponent(listBox, value,
                                        -1, false, false);
                        d = getSizeForComponent(c);
                        result.width = Math.max(result.width, d.width);
                        result.height = Math.max(result.height, d.height);
                    }
                }
            } else {
                // Calculates the maximum height and width based on default
                // renderer
                result = getDefaultSize();
                if (comboBox.isEditable()) {
                    result.width = 100;
                }
            }
        }
        
        if (comboBox.isEditable()) {
            final Dimension d = editor.getPreferredSize();
            result.width = Math.max(result.width, d.width);
            result.height = Math.max(result.height, d.height);
        }
        
        return result;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Dimension getDefaultSize() {
        // Calculates the height and width using the default text renderer
        return getSizeForComponent(getDefaultListCellRenderer()
                .getListCellRendererComponent(listBox, " ", -1, false, false));
    }
    
    /**
     * Returns default list cell renderer instance.
     *
     * @return default list cell renderer instance
     */
    protected static ListCellRenderer getDefaultListCellRenderer() {
        if (DEFAULT_RENDERER == null) {
            DEFAULT_RENDERER = new WebComboBoxCellRenderer();
        }
        return DEFAULT_RENDERER;
    }
    
    /**
     * Returns renderer component preferred size.
     *
     * @param c
     *            renderer component
     * @return renderer component preferred size
     */
    protected Dimension getSizeForComponent(final Component c) {
        currentValuePane.add(c);
        c.setFont(comboBox.getFont());
        final Dimension d = c.getPreferredSize();
        currentValuePane.remove(c);
        return d;
    }
    
    /**
     * Custom layout manager for WebComboBoxUI.
     */
    protected class WebComboBoxLayout extends AbstractLayoutManager {
        /**
         * {@inheritDoc}
         */
        @Override
        public Dimension preferredLayoutSize(final Container parent) {
            return parent.getPreferredSize();
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public Dimension minimumLayoutSize(final Container parent) {
            return parent.getMinimumSize();
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public void layoutContainer(final Container parent) {
            final JComboBox cb = (JComboBox) parent;
            final int width = cb.getWidth();
            final int height = cb.getHeight();
            
            if (arrowButton != null) {
                final Insets insets = getInsets();
                final int buttonHeight = height - (insets.top + insets.bottom);
                final int buttonWidth = arrowButton.getPreferredSize().width;
                if (cb.getComponentOrientation().isLeftToRight()) {
                    arrowButton.setBounds(width - (insets.right + buttonWidth),
                            insets.top, buttonWidth, buttonHeight);
                } else {
                    arrowButton.setBounds(insets.left, insets.top, buttonWidth,
                            buttonHeight);
                }
            }
            
            if (editor != null) {
                editor.setBounds(rectangleForCurrentValue());
            }
        }
    }
}