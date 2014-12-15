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

package org.ocsoft.flatlaf.laf.text;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicEditorPaneUI;
import javax.swing.text.JTextComponent;

import org.ocsoft.flatlaf.core.constants.FlatLafConstants;
import org.ocsoft.flatlaf.utils.SwingUtils;
import org.ocsoft.flatlaf.weblaf.FlatLafStyleConstants;

/**
 * User: mgarin Date: 17.08.11 Time: 23:03
 */

public class WebEditorPaneUI extends BasicEditorPaneUI {
    public static ComponentUI createUI(final JComponent c) {
        return new WebEditorPaneUI();
    }
    
    @Override
    public void installUI(final JComponent c) {
        super.installUI(c);
        
        final JTextComponent textComponent = getComponent();
        
        // Default settings
        SwingUtils.setOrientation(textComponent);
        LookAndFeel.installProperty(textComponent,
                FlatLafConstants.OPAQUE_PROPERTY, Boolean.TRUE);
        textComponent.setMargin(new Insets(2, 2, 2, 2));
        textComponent.setFocusable(true);
        textComponent.setBackground(Color.WHITE);
        textComponent
                .setSelectionColor(FlatLafStyleConstants.textSelectionColor);
        textComponent.setForeground(Color.BLACK);
        textComponent.setSelectedTextColor(Color.BLACK);
        textComponent.setCaretColor(Color.GRAY);
    }
    
    @Override
    protected void paintSafely(final Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;
        final Map hints = SwingUtils.setupTextAntialias(g2d);
        super.paintSafely(g);
        SwingUtils.restoreTextAntialias(g2d, hints);
    }
}