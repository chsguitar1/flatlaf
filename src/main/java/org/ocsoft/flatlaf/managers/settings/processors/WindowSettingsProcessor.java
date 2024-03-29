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

package org.ocsoft.flatlaf.managers.settings.processors;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import org.ocsoft.flatlaf.managers.settings.SettingsProcessor;
import org.ocsoft.flatlaf.managers.settings.SettingsProcessorData;
import org.ocsoft.flatlaf.utils.CompareUtils;

/**
 * Custom SettingsProcessor for Window component.
 *
 * @author Mikle Garin
 * @see <a
 *      href="https://github.com/mgarin/weblaf/wiki/How-to-use-SettingsManager">How
 *      to use SettingsManager</a>
 * @see org.ocsoft.flatlaf.managers.settings.SettingsManager
 * @see org.ocsoft.flatlaf.managers.settings.SettingsProcessor
 */

public class WindowSettingsProcessor extends
        SettingsProcessor<Window, Rectangle> {
    /**
     * todo 1. Save window normal/maximized window states, iconified should be
     * converted into normal todo 2. Save screen where window was located?
     * Probably saved by coordinates but might be wrong if screen settings
     * changed
     */
    
    /**
     * Window move and resize listener.
     */
    private ComponentAdapter componentAdapter;
    
    /**
     * Constructs SettingsProcessor using the specified SettingsProcessorData.
     *
     * @param data
     *            SettingsProcessorData
     */
    public WindowSettingsProcessor(final SettingsProcessorData data) {
        super(data);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doInit(final Window window) {
        componentAdapter = new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                save();
            }
            
            @Override
            public void componentMoved(final ComponentEvent e) {
                save();
            }
        };
        window.addComponentListener(componentAdapter);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doDestroy(final Window window) {
        window.removeComponentListener(componentAdapter);
        componentAdapter = null;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doLoad(final Window window) {
        final Rectangle bounds = loadValue();
        if (bounds != null && !CompareUtils.equals(bounds, window.getBounds())) {
            final Dimension size = bounds.getSize();
            if (size.width > 0 && size.height > 0) {
                window.setSize(size);
            } else {
                window.pack();
            }
            
            final Point location = bounds.getLocation();
            if (location.x > 0 && location.y > 0) {
                window.setLocation(location);
            } else {
                window.setLocationRelativeTo(null);
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doSave(final Window window) {
        saveValue(window.getBounds());
    }
}