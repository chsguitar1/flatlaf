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

package org.ocsoft.flatlaf.laf.viewport;

import javax.swing.JComponent;
import javax.swing.JViewport;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicViewportUI;

import org.ocsoft.flatlaf.core.FlatLookAndFeel;
import org.ocsoft.flatlaf.utils.SwingUtils;

/**
 * Custom UI for JViewport component.
 *
 * @author Mikle Garin
 */

public class WebViewportUI extends BasicViewportUI {
    /**
     * Returns an instance of the WebViewportUI for the specified component.
     * This tricky method is used by UIManager to create component UIs when
     * needed.
     *
     * @param c
     *            component that will use UI instance
     * @return instance of the WebViewportUI
     */
    
    public static ComponentUI createUI(final JComponent c) {
        return new WebViewportUI();
    }
    
    /**
     * Installs UI in the specified component.
     *
     * @param c
     *            component for this UI
     */
    @Override
    public void installUI(final JComponent c) {
        super.installUI(c);
        
        // Default settings
        final JViewport viewport = (JViewport) c;
        viewport.setScrollMode(FlatLookAndFeel.getScrollMode());
        SwingUtils.setOrientation(c);
    }
}