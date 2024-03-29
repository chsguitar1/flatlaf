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

package org.ocsoft.flatlaf.extended.checkbox;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import org.ocsoft.flatlaf.laf.checkbox.FlatCheckBoxUI;
import org.ocsoft.flatlaf.weblaf.checkbox.CheckIcon;

/**
 * Custom UI for WebTristateCheckBox component.
 *
 * @author Mikle Garin
 */

public class WebTristateCheckBoxUI extends FlatCheckBoxUI {
    /**
     * Returns an instance of the WebTristateCheckBoxUI for the specified
     * component. This tricky method is used by UIManager to create component
     * UIs when needed.
     *
     * @param c
     *            component that will use UI instance
     * @return instance of the WebTristateCheckBoxUI
     */
    
    public static ComponentUI createUI(final JComponent c) {
        return new WebTristateCheckBoxUI();
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
        
        // Initial check state
        checkIcon.setState(getTristateCheckbox().getState());
    }
    
    /**
     * Returns tristate checkbox which uses this UI.
     *
     * @return tristate checkbox which uses this UI
     */
    protected WebTristateCheckBox getTristateCheckbox() {
        return (WebTristateCheckBox) checkBox;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected CheckIcon createCheckStateIcon() {
        return new TristateCheckIcon(getTristateCheckbox());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void performStateChanged() {
        final WebTristateCheckBox tcb = getTristateCheckbox();
        if (isAnimationAllowed() && isAnimated() && tcb.isEnabled()) {
            checkIcon.setNextState(tcb.getState());
            checkTimer.start();
        } else {
            checkTimer.stop();
            checkIcon.setState(tcb.getState());
            tcb.repaint();
        }
    }
}