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

package org.ocsoft.flatlaf.extended.painter;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JToolBar;

import org.ocsoft.flatlaf.utils.SwingUtils;
import org.ocsoft.flatlaf.utils.ninepatch.NinePatchIcon;
import org.ocsoft.flatlaf.weblaf.toolbar.WebToolBar;

/**
 * This painter class allows you to specify multiply 9-patch images for
 * different component states. By default there is support for some standard
 * Swing component states like buttons.
 *
 * @param <E>
 *            component type
 * @author Mikle Garin
 * @see ComponentState
 * @see NinePatchIcon
 * @see org.ocsoft.flatlaf.extended.painter.NinePatchIconPainter
 * @see AbstractPainter
 * @see Painter
 */

public class NinePatchStatePainter<E extends JComponent> extends
        AbstractPainter<E> {
    /**
     * todo 1. Move each component support to separate classes
     */
    
    /**
     * Map containing separate 9-patch icons for different component states.
     */
    protected Map<String, NinePatchIcon> stateIcons;
    
    /**
     * Constructs new 9-patch state painter with empty states.
     */
    public NinePatchStatePainter() {
        super();
        this.stateIcons = new HashMap<String, NinePatchIcon>();
    }
    
    /**
     * Constructs new 9-patch state painter with specified states map.
     */
    public NinePatchStatePainter(final Map<String, NinePatchIcon> stateIcons) {
        super();
        this.stateIcons = stateIcons;
    }
    
    /**
     * Returns states map.
     *
     * @return states map
     */
    public Map<String, NinePatchIcon> getStateIcons() {
        return stateIcons;
    }
    
    /**
     * Sets states map.
     *
     * @param stateIcons
     *            states map
     */
    public void setStateIcons(final Map<String, NinePatchIcon> stateIcons) {
        this.stateIcons = stateIcons;
        updateAll();
    }
    
    /**
     * Adds painter state.
     *
     * @param state
     *            state to add
     * @param ninePatchIcon
     *            9-patch icon
     */
    public void addStateIcon(final String state,
            final NinePatchIcon ninePatchIcon) {
        stateIcons.put(state, ninePatchIcon);
        updateAll();
    }
    
    /**
     * Removes painter state.
     *
     * @param state
     *            state to remove
     */
    public void removeStateIcon(final String state) {
        stateIcons.remove(state);
        updateAll();
    }
    
    /**
     * Returns whether atleast one state icon is available or not.
     *
     * @return true if atleast one state icon is available, false otherwise
     */
    public boolean hasStateIcons() {
        return stateIcons != null && stateIcons.size() > 0;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void paint(final Graphics2D g2d, final Rectangle bounds, final E c) {
        if (hasStateIcons() && c != null) {
            // Current state icon retrieval
            final NinePatchIcon stateIcon;
            if (c instanceof AbstractButton) {
                // Button component
                stateIcon = getButtonBackground((AbstractButton) c);
            } else if (c instanceof JToolBar) {
                // Toolbar component
                stateIcon = getToolBarBackground((JToolBar) c);
            } else {
                // Any component
                stateIcon = getComponentBackground(c);
            }
            
            // Draw background
            if (stateIcon != null) {
                stateIcon.setComponent(c);
                stateIcon.paintIcon(g2d, bounds);
            }
            
            // Draw focus
            if (isFocused(c)) {
                final NinePatchIcon focusIcon = getExactStateIcon(ComponentState.focused);
                if (focusIcon != null) {
                    focusIcon.setComponent(c);
                    focusIcon.paintIcon(g2d, bounds);
                }
            }
        }
    }
    
    /**
     * Returns whether component is in focused state or not.
     *
     * @param component
     *            component to process
     * @return true if component is in focused state, false otherwise
     */
    protected boolean isFocused(final E component) {
        return component.isFocusOwner();
    }
    
    /**
     * Returns current state icon for the specified component.
     *
     * @param component
     *            component to process
     * @return current state icon
     */
    protected NinePatchIcon getComponentBackground(final E component) {
        return getStateIcon(component.isEnabled() ? ComponentState.normal
                : ComponentState.disabled);
    }
    
    /**
     * Returns current state icon for the specified button.
     *
     * @param button
     *            button to process
     * @return current state icon
     */
    protected NinePatchIcon getButtonBackground(final AbstractButton button) {
        final ButtonModel bm = button.getModel();
        if (bm.isPressed()) {
            return getStateIcon(bm.isSelected() ? ComponentState.selectedPressed
                    : ComponentState.pressed);
        } else if (bm.isSelected()) {
            if (bm.isEnabled()) {
                return getStateIcon(bm.isRollover() ? ComponentState.selectedRollover
                        : ComponentState.selected);
            } else {
                return getStateIcon(ComponentState.selectedDisabled);
            }
        } else {
            if (bm.isEnabled()) {
                return getStateIcon(bm.isRollover() ? ComponentState.rollover
                        : ComponentState.normal);
            } else {
                return getStateIcon(ComponentState.disabled);
            }
        }
    }
    
    /**
     * Returns current state icon for the specified toolbar.
     *
     * @param toolbar
     *            toolbar to process
     * @return current state icon
     */
    protected NinePatchIcon getToolBarBackground(final JToolBar toolbar) {
        if (toolbar instanceof WebToolBar
                && ((WebToolBar) toolbar).isFloating()) {
            return getStateIcon(toolbar.isEnabled() ? ComponentState.floating
                    : ComponentState.floatingDisabled);
        } else {
            return getStateIcon(toolbar.isEnabled() ? ComponentState.normal
                    : ComponentState.disabled);
        }
    }
    
    /**
     * Returns exact state icon or null if it is not specified.
     *
     * @param state
     *            component state
     * @return exact state icon or null if it is not specified
     */
    public NinePatchIcon getExactStateIcon(final String state) {
        return stateIcons.get(state);
    }
    
    /**
     * Returns state icon or possible replacement for it.
     *
     * @param state
     *            component state
     * @return state icon or possible replacement for it
     */
    public NinePatchIcon getStateIcon(final String state) {
        if (state.equals(ComponentState.normal)) {
            return stateIcons.containsKey(state) ? stateIcons.get(state) : null;
        } else if (state.equals(ComponentState.rollover)) {
            return stateIcons.containsKey(state) ? stateIcons.get(state)
                    : getStateIcon(ComponentState.normal);
        } else if (state.equals(ComponentState.disabled)) {
            return stateIcons.containsKey(state) ? stateIcons.get(state)
                    : getStateIcon(ComponentState.normal);
        } else if (state.equals(ComponentState.pressed)) {
            return stateIcons.containsKey(state) ? stateIcons.get(state)
                    : getStateIcon(ComponentState.selected);
        } else if (state.equals(ComponentState.selected)) {
            return stateIcons.containsKey(state) ? stateIcons.get(state)
                    : getStateIcon(ComponentState.normal);
        } else if (state.equals(ComponentState.selectedRollover)) {
            return stateIcons.containsKey(state) ? stateIcons.get(state)
                    : getStateIcon(ComponentState.selected);
        } else if (state.equals(ComponentState.selectedDisabled)) {
            return stateIcons.containsKey(state) ? stateIcons.get(state)
                    : getStateIcon(ComponentState.selected);
        } else if (state.equals(ComponentState.selectedPressed)) {
            return stateIcons.containsKey(state) ? stateIcons.get(state)
                    : getStateIcon(ComponentState.pressed);
        } else if (state.equals(ComponentState.focused)) {
            return stateIcons.containsKey(state) ? stateIcons.get(state) : null;
        } else if (state.equals(ComponentState.floating)) {
            return stateIcons.containsKey(state) ? stateIcons.get(state)
                    : getStateIcon(ComponentState.normal);
        } else if (state.equals(ComponentState.floatingDisabled)) {
            return stateIcons.containsKey(state) ? stateIcons.get(state)
                    : getStateIcon(ComponentState.floating);
        } else {
            return null;
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension getPreferredSize(final E c) {
        if (hasStateIcons()) {
            Dimension maxDimension = new Dimension(0, 0);
            for (final NinePatchIcon npi : stateIcons.values()) {
                npi.setComponent(c);
                maxDimension = SwingUtils.max(maxDimension,
                        npi.getPreferredSize());
            }
            return maxDimension;
        } else {
            return super.getPreferredSize(c);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Insets getMargin(final E c) {
        final Insets margin = super.getMargin(c);
        if (hasStateIcons()) {
            Insets maxInsets = new Insets(0, 0, 0, 0);
            for (final NinePatchIcon npi : stateIcons.values()) {
                npi.setComponent(c);
                maxInsets = SwingUtils.max(maxInsets, npi.getMargin());
            }
            return SwingUtils.max(margin, maxInsets);
        } else {
            return margin;
        }
    }
}