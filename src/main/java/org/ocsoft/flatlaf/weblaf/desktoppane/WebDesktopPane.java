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

package org.ocsoft.flatlaf.weblaf.desktoppane;

import javax.swing.JDesktopPane;

import org.ocsoft.flatlaf.core.FlatLafSettings;
import org.ocsoft.flatlaf.laf.desktoppane.FlatDesktopPaneUI;
import org.ocsoft.flatlaf.utils.reflection.ReflectUtils;
import org.ocsoft.flatlaf.utils.system.FlatLafLogger;

/**
 * User: mgarin Date: 24.08.11 Time: 17:46
 */

public class WebDesktopPane extends JDesktopPane {
    public WebDesktopPane() {
        super();
    }
    
    public FlatDesktopPaneUI getWebUI() {
        return (FlatDesktopPaneUI) getUI();
    }
    
    @Override
    public void updateUI() {
        if (getUI() == null || !(getUI() instanceof FlatDesktopPaneUI)) {
            try {
                setUI((FlatDesktopPaneUI) ReflectUtils
                        .createInstance(FlatLafSettings.desktopPaneUI));
            } catch (final Throwable e) {
                FlatLafLogger.error(this, e);
                setUI(new FlatDesktopPaneUI());
            }
        } else {
            setUI(getUI());
        }
    }
}
