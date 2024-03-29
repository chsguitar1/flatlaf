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

package org.ocsoft.flatlaf.utils.swing;

/**
 * @author Mikle Garin
 */

public interface RendererListener {
    /**
     * Forces component that uses this renderer to repaint. It is useful if
     * renderer might change in runtime and requires component to repaint after.
     */
    public void repaint();
    
    /**
     * Forces component that uses this renderer to revalidate its size. It is
     * useful if renderer might change its size in runtime and requires
     * component to update its size after.
     */
    public void revalidate();
}