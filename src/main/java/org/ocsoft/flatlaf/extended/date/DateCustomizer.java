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

package org.ocsoft.flatlaf.extended.date;

import java.util.Date;

import org.ocsoft.flatlaf.weblaf.button.WebToggleButton;

/**
 * This interface allows you to provide custom date buttons customizer into
 * WebCalendar component.
 *
 * @author Mikle Garin
 * @see WebCalendar
 */

public interface DateCustomizer {
    /**
     * Customize single date button.
     *
     * @param button
     *            button to customize
     * @param date
     *            date
     */
    public void customize(WebToggleButton button, Date date);
}