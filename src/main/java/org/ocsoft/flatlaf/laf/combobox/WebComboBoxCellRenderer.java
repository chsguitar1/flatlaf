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

import javax.swing.*;

import org.ocsoft.flatlaf.core.FlatLookAndFeel;
import org.ocsoft.flatlaf.utils.swing.RendererListener;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom cell renderer for JComboBox value and popup list and some other
 * similar cases. It uses {@code WebComboBoxElement} as renderer which is being
 * styled by a custom LabelPainter.
 *
 * @author Mikle Garin
 * @see org.ocsoft.flatlaf.laf.combobox.WebComboBoxElement
 * @see org.ocsoft.flatlaf.managers.style.skin.web.WebComboBoxElementPainter
 */

public class WebComboBoxCellRenderer implements ListCellRenderer {
    /**
     * Renderer listeners.
     */
    protected List<RendererListener> rendererListeners = new ArrayList<RendererListener>(
            1);
    
    /**
     * Actual renderer components.
     */
    protected WebComboBoxElement boxRenderer;
    protected WebComboBoxElement elementRenderer;
    
    /**
     * Constructs new combo box renderer.
     */
    public WebComboBoxCellRenderer() {
        super();
        
        // Additional renderer for combo box selected value rendering
        this.boxRenderer = new WebComboBoxElement(ComboBoxElementType.box);
        
        // Elements renderer
        this.elementRenderer = new WebComboBoxElement(ComboBoxElementType.list);
        
        // Painter change listener
        final PropertyChangeListener listener = new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent evt) {
                fireRevalidate();
            }
        };
        this.boxRenderer.addPropertyChangeListener(
                FlatLookAndFeel.PAINTER_PROPERTY, listener);
        this.elementRenderer.addPropertyChangeListener(
                FlatLookAndFeel.PAINTER_PROPERTY, listener);
    }
    
    /**
     * Returns actual combo box value renderer.
     *
     * @return actual combo box value renderer
     */
    public WebComboBoxElement getBoxRenderer() {
        return boxRenderer;
    }
    
    /**
     * Returns actual elements renderer.
     *
     * @return actual elements renderer
     */
    public WebComboBoxElement getElementRenderer() {
        return elementRenderer;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Component getListCellRendererComponent(final JList list,
            final Object value, final int index, final boolean isSelected,
            final boolean cellHasFocus) {
        // Choosing actual renderer
        final WebComboBoxElement renderer = index == -1 ? boxRenderer
                : elementRenderer;
        
        // Updating runtime variables
        renderer.setIndex(index);
        renderer.setTotalElements(list.getModel().getSize());
        renderer.setSelected(isSelected);
        
        // Updating renderer visual settings
        renderer.setEnabled(list.isEnabled());
        renderer.setFont(list.getFont());
        renderer.setForeground(isSelected ? list.getSelectionForeground()
                : list.getForeground());
        renderer.setComponentOrientation(list.getComponentOrientation());
        
        // Updating icon and text
        if (value instanceof Icon) {
            renderer.setIcon((Icon) value);
            renderer.setText("");
        } else {
            renderer.setIcon(null);
            renderer.setText(value == null || value.toString().equals("") ? " "
                    : value.toString());
        }
        
        return renderer;
    }
    
    /**
     * Adds RendererListener to this renderer.
     *
     * @param listener
     *            RendererListener to add
     */
    public void addRendererListener(final RendererListener listener) {
        rendererListeners.add(listener);
    }
    
    /**
     * Removes RendererListener from this renderer.
     *
     * @param listener
     *            RendererListener to remove
     */
    public void removeRendererListener(final RendererListener listener) {
        rendererListeners.remove(listener);
    }
    
    /**
     * Fires repaint event.
     */
    public void fireRepaint() {
        for (final RendererListener listener : rendererListeners) {
            listener.repaint();
        }
    }
    
    /**
     * Fires revalidate event.
     */
    public void fireRevalidate() {
        for (final RendererListener listener : rendererListeners) {
            listener.revalidate();
        }
    }
}