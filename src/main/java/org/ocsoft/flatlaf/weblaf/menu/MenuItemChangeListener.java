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

package org.ocsoft.flatlaf.weblaf.menu;

import java.awt.Container;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.ocsoft.flatlaf.extended.painter.Painter;
import org.ocsoft.flatlaf.laf.menu.WebPopupMenuUI;
import org.ocsoft.flatlaf.managers.style.skin.web.PopupStyle;
import org.ocsoft.flatlaf.managers.style.skin.web.WebPopupMenuPainter;

/**
 * Special menu item change listener required to update popup menu decoration
 * properly.
 *
 * @author Mikle Garin
 */

public class MenuItemChangeListener implements ChangeListener {
    /**
     * Listened menu item.
     */
    protected JMenuItem menuItem;
    
    /**
     * Installs menu item model change listener and returns it.
     *
     * @param menuItem
     *            menu item to install listener into
     * @return installed model change listener
     */
    public static MenuItemChangeListener install(final JMenuItem menuItem) {
        final MenuItemChangeListener listener = new MenuItemChangeListener(
                menuItem);
        menuItem.getModel().addChangeListener(listener);
        menuItem.addPropertyChangeListener(
                AbstractButton.MODEL_CHANGED_PROPERTY,
                new PropertyChangeListener() {
                    @Override
                    public void propertyChange(final PropertyChangeEvent evt) {
                        ((ButtonModel) evt.getOldValue())
                                .removeChangeListener(listener);
                        menuItem.getModel().addChangeListener(listener);
                    }
                });
        return listener;
    }
    
    /**
     * Uninstalls menu item model change listener from specified menu item.
     *
     * @param listener
     *            listener to uninstall
     * @param menuItem
     *            menu item to uninstall listener from
     */
    public static void uninstall(final MenuItemChangeListener listener,
            final JMenuItem menuItem) {
        menuItem.getModel().removeChangeListener(listener);
    }
    
    /**
     * Constructs new menu item change listener.
     *
     * @param menuItem
     *            menu item to listen
     */
    public MenuItemChangeListener(final JMenuItem menuItem) {
        super();
        this.menuItem = menuItem;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void stateChanged(final ChangeEvent e) {
        final Container parent = menuItem.getParent();
        if (parent instanceof JPopupMenu) {
            final JPopupMenu popupMenu = (JPopupMenu) parent;
            if (popupMenu.getUI() instanceof WebPopupMenuUI) {
                // Checking whether web-painter is used or not
                final WebPopupMenuUI ui = (WebPopupMenuUI) popupMenu.getUI();
                final Painter painter = ui.getPainter();
                if (painter instanceof WebPopupMenuPainter) {
                    // Checking painter sttyle
                    final WebPopupMenuPainter webPainter = (WebPopupMenuPainter) painter;
                    if (webPainter.getPopupStyle() == PopupStyle.dropdown) {
                        // Checking whether this item state change affect the
                        // corner
                        final int zOrder = popupMenu
                                .getComponentZOrder(menuItem);
                        if (webPainter.getCornerSide() == SwingConstants.NORTH
                                && zOrder == 0) {
                            // Repainting only corner bounds
                            popupMenu.repaint(0, 0, popupMenu.getWidth(),
                                    menuItem.getBounds().y);
                        } else if (webPainter.getCornerSide() == SwingConstants.SOUTH
                                && zOrder == popupMenu.getComponentCount() - 1) {
                            // Repainting only corner bounds
                            final Rectangle itemBounds = menuItem.getBounds();
                            final int y = itemBounds.y + itemBounds.height;
                            popupMenu.repaint(0, y, popupMenu.getWidth(),
                                    popupMenu.getHeight() - y);
                        }
                    }
                }
            }
        }
    }
}