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

/**
 * Custom runnable that provides information about DocumentData.
 *
 * @author Mikle Garin
 */

public interface DocumentDataCancellableRunnable<T extends DocumentData> {
    /**
     * Performs action according to provided event data. Returns whether event
     * should be cancelled or not according to performed actions.
     *
     * @param document
     *            document
     * @param pane
     *            pane data
     * @param index
     *            document index within pane
     * @return true if event should be cancelled, false otherwise
     */
    public boolean run(T document, PaneData<T> pane, int index);
}