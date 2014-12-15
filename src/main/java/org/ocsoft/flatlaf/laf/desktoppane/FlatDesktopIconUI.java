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

package org.ocsoft.flatlaf.laf.desktoppane;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicDesktopIconUI;

import org.ocsoft.flatlaf.core.constants.FlatLafConstants;
import org.ocsoft.flatlaf.utils.LafUtils;
import org.ocsoft.flatlaf.utils.SwingUtils;
import org.ocsoft.flatlaf.weblaf.desktoppane.WebInternalFrameIconPane;

/**
 * User: mgarin Date: 17.08.11 Time: 23:14
 */

public class FlatDesktopIconUI extends BasicDesktopIconUI {
    public static ComponentUI createUI(final JComponent c) {
        return new FlatDesktopIconUI();
    }
    
    @Override
    public void installUI(final JComponent c) {
        super.installUI(c);
        
        // Default settings
        SwingUtils.setOrientation(c);
        c.setBorder(LafUtils.createWebBorder(0, 0, 0, 0));
        LookAndFeel.installProperty(c, FlatLafConstants.OPAQUE_PROPERTY,
                Boolean.FALSE);
    }
    
    @Override
    protected void installComponents() {
        iconPane = new WebInternalFrameIconPane(frame);
        desktopIcon.setLayout(new BorderLayout());
        desktopIcon.add(iconPane, BorderLayout.CENTER);
    }
}