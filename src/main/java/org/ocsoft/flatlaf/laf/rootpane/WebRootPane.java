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

package org.ocsoft.flatlaf.laf.rootpane;

import javax.swing.JRootPane;

import org.ocsoft.flatlaf.core.FlatLafSettings;
import org.ocsoft.flatlaf.utils.reflection.ReflectUtils;
import org.ocsoft.flatlaf.utils.system.FlatLafLogger;

/**
 * User: mgarin Date: 01.11.11 Time: 13:31
 */

public class WebRootPane extends JRootPane {
    public WebRootPane() {
        super();
    }
    
    public WebRootPaneUI getWebUI() {
        return (WebRootPaneUI) getUI();
    }
    
    @Override
    public void updateUI() {
        if (getUI() == null || !(getUI() instanceof WebRootPaneUI)) {
            try {
                setUI((WebRootPaneUI) ReflectUtils
                        .createInstance(FlatLafSettings.rootPaneUI));
            } catch (final Throwable e) {
                FlatLafLogger.error(this, e);
                setUI(new WebRootPaneUI());
            }
        } else {
            setUI(getUI());
        }
    }
}
