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

package org.ocsoft.flatlaf.extended.tab;

import java.awt.FontMetrics;
import java.awt.datatransfer.DataFlavor;

import javax.swing.Icon;

import org.ocsoft.flatlaf.managers.drag.SimpleDragViewHandler;

/**
 * Custom DragViewHandler for WebDocumentPane document.
 *
 * @author Mikle Garin
 * @see <a
 *      href="https://github.com/mgarin/weblaf/wiki/How-to-use-WebDocumentPane">How
 *      to use WebDocumentPane</a>
 * @see org.ocsoft.flatlaf.extended.tab.WebDocumentPane
 * @see org.ocsoft.flatlaf.managers.drag.DragManager
 */

public class DocumentDragViewHandler<T extends DocumentData> extends
        SimpleDragViewHandler<T> {
    /**
     * Document pane which provides this DragViewHandler.
     */
    protected final WebDocumentPane documentPane;
    
    /**
     * Constructs custom DragViewHandler for DocumentData object.
     *
     * @param documentPane
     *            document pane which provides this DragViewHandler
     */
    public DocumentDragViewHandler(final WebDocumentPane documentPane) {
        super();
        this.documentPane = documentPane;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public DataFlavor getObjectFlavor() {
        return DocumentTransferable.flavor;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected FontMetrics getFontMetrics(final T document) {
        return documentPane.getFontMetrics(documentPane.getFont());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Icon getIcon(final T document) {
        return document.getIcon();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected String getText(final T document) {
        return document.getActualTitle();
    }
}