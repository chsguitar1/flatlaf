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

package org.ocsoft.flatlaf.utils.filefilter;

import java.io.File;
import java.io.FileFilter;

import javax.swing.ImageIcon;

import org.ocsoft.flatlaf.utils.general.Filter;

/**
 * Default file filter for WebLaF file chooser that provides additional filter
 * information. This class overrides IO, Swing and utils filter classes.
 *
 * @author Mikle Garin
 */

public abstract class AbstractFileFilter extends
        javax.swing.filechooser.FileFilter implements FileFilter, Filter<File> {
    /**
     * Returns file filter icon.
     *
     * @return file filter icon
     */
    public abstract ImageIcon getIcon();
    
    /**
     * Returns short file filter description.
     *
     * @return short file filter description
     */
    @Override
    public abstract String getDescription();
    
    /**
     * Returns whether the given file is accepted by this filter or not.
     *
     * @param file
     *            file to process
     * @return true if the given file is accepted by this filter, false
     *         otherwise
     */
    @Override
    public abstract boolean accept(File file);
}