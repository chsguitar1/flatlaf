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

package org.ocsoft.flatlaf.managers.language.updaters;

import org.ocsoft.flatlaf.managers.language.data.Value;
import org.ocsoft.flatlaf.weblaf.text.WebTextField;

/**
 * This class provides language default updates for WebTextField component.
 *
 * @author Mikle Garin
 */

public class WebTextFieldLU extends DefaultLanguageUpdater<WebTextField> {
    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final WebTextField c, final String key,
            final Value value, final Object... data) {
        final String text = getDefaultText(value, data);
        if (text != null) {
            c.setText(text);
        }
        final String inputPrompt = getDefaultText(INPUT_PROMPT, value, data);
        if (inputPrompt != null) {
            c.setInputPrompt(inputPrompt);
        }
    }
}