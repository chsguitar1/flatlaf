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

package org.ocsoft.flatlaf.weblaf.table.editors;

import org.ocsoft.flatlaf.weblaf.text.WebTextField;

/**
 * User: mgarin Date: 28.11.12 Time: 14:19
 */

public class GenericCellEditor extends WebTextField {
    private boolean invalidValue = false;
    
    public GenericCellEditor() {
        super(false);
        setMargin(0);
        setFieldMargin(0);
        setPainter(new GenericCellEditorPainter());
    }
    
    public boolean isInvalidValue() {
        return invalidValue;
    }
    
    public void setInvalidValue(final boolean invalidValue) {
        this.invalidValue = invalidValue;
        repaint();
    }
}
