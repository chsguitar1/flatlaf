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

package org.ocsoft.flatlaf.managers.notification;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;

import org.ocsoft.flatlaf.extended.layout.AbstractLayoutManager;
import org.ocsoft.flatlaf.utils.SwingUtils;

/**
 * Custom layout for proper notifications placement on layered pane.
 *
 * @author Mikle Garin
 * @see org.ocsoft.flatlaf.managers.notification.NotificationManager
 * @see org.ocsoft.flatlaf.managers.notification.NotificationsLayoutUtils
 */

public class NotificationsLayout extends AbstractLayoutManager implements
        SwingConstants {
    /**
     * Cached notifications.
     */
    protected final List<WebInnerNotification> notifications = new ArrayList<WebInnerNotification>(
            2);
    
    /**
     * Notifications lock.
     */
    protected final Object lock = new Object();
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void addComponent(final Component component, final Object constraints) {
        synchronized (lock) {
            if (component instanceof WebInnerNotification) {
                notifications.add((WebInnerNotification) component);
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void removeComponent(final Component component) {
        synchronized (lock) {
            if (component instanceof WebInnerNotification) {
                notifications.remove(component);
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension preferredLayoutSize(final Container parent) {
        return null;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void layoutContainer(final Container parent) {
        synchronized (lock) {
            if (notifications.size() > 0) {
                // Container bounds
                final Rectangle bounds = SwingUtils.size(parent);
                
                // Layout notifications
                NotificationsLayoutUtils.layout(notifications, bounds);
            }
        }
    }
}