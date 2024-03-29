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

package org.ocsoft.flatlaf.managers.drag;

import java.awt.Point;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.image.BufferedImage;
import java.lang.ref.WeakReference;

import javax.swing.JComponent;

import org.ocsoft.flatlaf.utils.SwingUtils;

/**
 * @author Mikle Garin
 */

public abstract class ComponentDragViewHandler<T extends JComponent> implements
        DragViewHandler<T> {
    /**
     * Initial mouse location on dragged component. Used to position component
     * snapshot correctly under the mouse.
     */
    protected Point location;
    
    /**
     * Dragged object reference.
     */
    protected WeakReference<T> reference;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public BufferedImage getView(final T object, final DragSourceDragEvent event) {
        // Saving initial mouse location
        if (reference == null || reference.get() != object) {
            final Point los = object.getLocationOnScreen();
            final Point eloc = event.getLocation();
            location = new Point(los.x - eloc.x, los.y - eloc.y);
            reference = new WeakReference<T>(object);
        }
        
        // Returning component snapshot
        return SwingUtils.createComponentSnapshot(object,
                getSnapshotTransparency());
    }
    
    /**
     * Returns component snapshot transparency. By default snapshot is
     * semi-transparent for usability purposes.
     *
     * @return component snapshot transparency
     */
    public float getSnapshotTransparency() {
        return 0.8f;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Point getViewRelativeLocation(final T object,
            final DragSourceDragEvent event) {
        return location;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void dragEnded(final T object, final DragSourceDropEvent event) {
        // Clearing initial mouse location
        location = null;
        reference = null;
    }
}