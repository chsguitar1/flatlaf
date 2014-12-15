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

import java.util.Comparator;

import org.ocsoft.flatlaf.global.FlatLafConstants;

/**
 * Custom comparator for file tree nodes.
 *
 * @author Mikle Garin
 */

public class FileTreeNodeComparator implements Comparator<FileTreeNode>
{
    /**
     * {@inheritDoc}
     */
    @Override
    public int compare ( final FileTreeNode o1, final FileTreeNode o2 )
    {
        return FlatLafConstants.FILE_COMPARATOR.compare ( o1.getFile (), o2.getFile () );
    }
}