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

package org.ocsoft.flatlaf.weblaf.list.editor;

import java.awt.Component;

import javax.swing.JList;

import org.ocsoft.flatlaf.weblaf.list.WebListStyle;
import org.ocsoft.flatlaf.weblaf.text.WebTextField;

/**
 * Default list cell editor that is based on various components.
 *
 * @author Mikle Garin
 */

public class DefaultListCellEditor extends AbstractListCellEditor {
    // todo WebComboBox/WebCheckBox-based default editor
    
    /**
     * Creates list cell editor component for the cell nder specified index.
     *
     * @param list
     *            list to process
     * @param index
     *            cell index
     * @param value
     *            cell value
     * @return list cell editor created for the cell under specified index
     */
    @Override
    protected Component createCellEditor(final JList list, final int index,
            final Object value) {
        final WebTextField field = WebTextField.createWebTextField(true,
                WebListStyle.selectionRound, WebListStyle.selectionShadeWidth);
        field.setDrawFocus(false);
        field.setText(value != null ? value.toString() : "");
        field.selectAll();
        return field;
    }
    
    /**
     * Returns editor value that will replace the specified old value in the
     * model.
     *
     * @param list
     *            list to process
     * @param index
     *            cell index
     * @param oldValue
     *            old cell value
     * @return editor value
     */
    @Override
    public Object getCellEditorValue(final JList list, final int index,
            final Object oldValue) {
        return ((WebTextField) editor).getText();
    }
}