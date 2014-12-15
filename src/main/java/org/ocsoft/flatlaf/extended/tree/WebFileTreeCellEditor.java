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

package org.ocsoft.flatlaf.extended.tree;

import java.io.File;

import org.ocsoft.flatlaf.extended.image.WebImage;
import org.ocsoft.flatlaf.laf.text.WebTextField;
import org.ocsoft.flatlaf.laf.tree.WebTreeCellEditor;
import org.ocsoft.flatlaf.utils.file.FileUtils;

/**
 * Custom cell editor for WebFileTree.
 *
 * @author Mikle Garin
 */

public class WebFileTreeCellEditor extends WebTreeCellEditor<WebTextField> {
    /**
     * Constructs new file tree cell editor.
     */
    public WebFileTreeCellEditor() {
        super();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize(final WebTextField editor) {
        this.editorComponent = editor;
        this.clickCountToStart = 3;
        this.autoUpdateLeadingIcon = false;
        this.delegate = new FileNameEditorDelegate();
        editor.addActionListener(delegate);
    }
    
    /**
     * Custom editor delegate for WebFileTableCellEditor.
     */
    protected class FileNameEditorDelegate extends EditorDelegate<FileTreeNode> {
        /**
         * {@inheritDoc}
         */
        @Override
        public void setValue(final FileTreeNode value) {
            final File file = value.getFile();
            editorComponent.setLeadingComponent(new WebImage(FileUtils
                    .getFileIcon(file)));
            FileUtils.displayFileName(editorComponent, file);
            super.setValue(value);
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public boolean stopCellEditing() {
            final File file = value.getFile();
            final String newName = editorComponent.getText();
            if (!newName.equals(file.getName())) {
                final File renamed = new File(file.getParent(), newName);
                if (file.renameTo(renamed)) {
                    value.setFile(renamed);
                }
                return super.stopCellEditing();
            } else {
                super.cancelCellEditing();
                return true;
            }
        }
    }
}