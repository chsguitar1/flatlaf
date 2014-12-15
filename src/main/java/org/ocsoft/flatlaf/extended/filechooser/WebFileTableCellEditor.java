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

package org.ocsoft.flatlaf.extended.filechooser;

import java.io.File;

import org.ocsoft.flatlaf.extended.image.WebImage;
import org.ocsoft.flatlaf.utils.file.FileUtils;
import org.ocsoft.flatlaf.utils.swing.WebDefaultCellEditor;
import org.ocsoft.flatlaf.weblaf.text.WebTextField;

/**
 * File table cell editor.
 *
 * @author Mikle Garin
 */

public class WebFileTableCellEditor extends WebDefaultCellEditor<WebTextField> {
    /**
     * Constructs new WebFileTableCellEditor.
     */
    public WebFileTableCellEditor() {
        super();
        clickCountToStart = 3;
        
        editorComponent = new WebTextField(false);
        editorComponent.setMargin(2);
        editorComponent.setFieldMargin(0, 4, 0, 0);
        
        delegate = new FileNameEditorDelegate();
        editorComponent.addActionListener(delegate);
    }
    
    /**
     * Custom editor delegate for WebFileTableCellEditor.
     */
    protected class FileNameEditorDelegate extends EditorDelegate<File> {
        /**
         * {@inheritDoc}
         */
        @Override
        public void setValue(final File file) {
            editorComponent.setLeadingComponent(new WebImage(FileUtils
                    .getFileIcon(file)));
            FileUtils.displayFileName(editorComponent, file);
            super.setValue(file);
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public boolean stopCellEditing() {
            final String newName = editorComponent.getText();
            if (!newName.equals(value.getName())) {
                final File renamed = new File(value.getParent(), newName);
                if (value.renameTo(renamed)) {
                    value = renamed;
                }
                return super.stopCellEditing();
            } else {
                super.cancelCellEditing();
                return true;
            }
        }
    }
}