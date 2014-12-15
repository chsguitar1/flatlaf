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

package org.ocsoft.flatlaf.laf.table;

import java.util.Date;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.LookAndFeel;
import javax.swing.event.AncestorEvent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTableUI;

import org.ocsoft.flatlaf.core.constants.FlatLafConstants;
import org.ocsoft.flatlaf.laf.table.editors.WebBooleanEditor;
import org.ocsoft.flatlaf.laf.table.editors.WebDateEditor;
import org.ocsoft.flatlaf.laf.table.editors.WebGenericEditor;
import org.ocsoft.flatlaf.laf.table.editors.WebNumberEditor;
import org.ocsoft.flatlaf.laf.table.renderers.WebBooleanRenderer;
import org.ocsoft.flatlaf.laf.table.renderers.WebDateRenderer;
import org.ocsoft.flatlaf.laf.table.renderers.WebDoubleRenderer;
import org.ocsoft.flatlaf.laf.table.renderers.WebIconRenderer;
import org.ocsoft.flatlaf.laf.table.renderers.WebNumberRenderer;
import org.ocsoft.flatlaf.laf.table.renderers.WebTableCellRenderer;
import org.ocsoft.flatlaf.utils.SwingUtils;
import org.ocsoft.flatlaf.utils.swing.AncestorAdapter;

/**
 * Custom UI for JTable component.
 *
 * @author Mikle Garin
 */

public class WebTableUI extends BasicTableUI {
    /**
     * Table listeners.
     */
    private AncestorAdapter ancestorAdapter;
    
    /**
     * Returns an instance of the WebTreeUI for the specified component. This
     * tricky method is used by UIManager to create component UIs when needed.
     *
     * @param c
     *            component that will use UI instance
     * @return instance of the WebTreeUI
     */
    @SuppressWarnings("UnusedParameters")
    public static ComponentUI createUI(final JComponent c) {
        return new WebTableUI();
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
        SwingUtils.setOrientation(table);
        LookAndFeel.installProperty(table, FlatLafConstants.OPAQUE_PROPERTY,
                Boolean.FALSE);
        table.setFillsViewportHeight(false);
        table.setBackground(WebTableStyle.background);
        table.setForeground(WebTableStyle.foreground);
        table.setSelectionBackground(WebTableStyle.selectionBackground);
        table.setSelectionForeground(WebTableStyle.selectionForeground);
        table.setRowHeight(WebTableStyle.rowHeight);
        table.setShowHorizontalLines(WebTableStyle.showHorizontalLines);
        table.setShowVerticalLines(WebTableStyle.showVerticalLines);
        table.setIntercellSpacing(WebTableStyle.cellsSpacing);
        
        // todo Save and restore old renderers/editors on uninstall
        // Configuring default renderers
        table.setDefaultRenderer(Object.class, new WebTableCellRenderer());
        table.setDefaultRenderer(Number.class, new WebNumberRenderer());
        table.setDefaultRenderer(Double.class, new WebDoubleRenderer());
        table.setDefaultRenderer(Float.class, new WebDoubleRenderer());
        table.setDefaultRenderer(Date.class, new WebDateRenderer());
        table.setDefaultRenderer(Icon.class, new WebIconRenderer());
        table.setDefaultRenderer(ImageIcon.class, new WebIconRenderer());
        table.setDefaultRenderer(Boolean.class, new WebBooleanRenderer());
        // todo Additional renderers:
        // table.setDefaultRenderer ( Dimension.class, );
        // table.setDefaultRenderer ( Point.class, );
        // table.setDefaultRenderer ( File.class, );
        // table.setDefaultRenderer ( Color.class, );
        // table.setDefaultRenderer ( List.class, );
        
        // Configuring default editors
        table.setDefaultEditor(Object.class, new WebGenericEditor());
        table.setDefaultEditor(Number.class, new WebNumberEditor());
        table.setDefaultEditor(Boolean.class, new WebBooleanEditor());
        table.setDefaultEditor(Date.class, new WebDateEditor());
        // todo Additional editors:
        // table.setDefaultEditor ( Dimension.class, );
        // table.setDefaultEditor ( Point.class, );
        // table.setDefaultEditor ( File.class, );
        // table.setDefaultEditor ( Color.class, );
        // table.setDefaultEditor ( List.class, );
        
        // Configuring scrollpane corner
        configureEnclosingScrollPaneUI(table);
        ancestorAdapter = new AncestorAdapter() {
            @Override
            public void ancestorAdded(final AncestorEvent event) {
                configureEnclosingScrollPaneUI(table);
            }
        };
        table.addAncestorListener(ancestorAdapter);
    }
    
    /**
     * Uninstalls UI from the specified component.
     *
     * @param c
     *            component with this UI
     */
    @Override
    public void uninstallUI(final JComponent c) {
        table.removeAncestorListener(ancestorAdapter);
        
        super.uninstallUI(c);
    }
    
    /**
     * Configures table scroll pane with UI specific settings.
     *
     * @param table
     *            table to process
     */
    protected void configureEnclosingScrollPaneUI(final JTable table) {
        // Retrieving table scroll pane if it has one
        final JScrollPane scrollPane = SwingUtils.getScrollPane(table);
        if (scrollPane != null) {
            // Make certain we are the viewPort's view and not, for
            // example, the rowHeaderView of the scrollPane -
            // an implementor of fixed columns might do this.
            final JViewport viewport = scrollPane.getViewport();
            if (viewport == null || viewport.getView() != table) {
                return;
            }
            
            // Adding both corners to the scroll pane for both orientation cases
            scrollPane.setCorner(JScrollPane.UPPER_LEADING_CORNER,
                    new WebTableCorner(false));
            scrollPane.setCorner(JScrollPane.UPPER_TRAILING_CORNER,
                    new WebTableCorner(true));
        }
    }
}