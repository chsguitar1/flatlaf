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

package org.ocsoft.flatlaf.managers.style.skin;

import java.util.List;

import javax.swing.JComponent;

import org.ocsoft.flatlaf.managers.style.SupportedComponent;
import org.ocsoft.flatlaf.managers.style.data.ComponentStyle;
import org.ocsoft.flatlaf.managers.style.data.SkinInfo;
import org.ocsoft.flatlaf.utils.xml.XmlUtils;

/**
 * This theme is used by StyleManager for cases when theme settings are stored
 * within XML. To use it simply specify XML location relative to your skin class
 * or provide SkinInfo object. All settings and painters will be loaded and
 * applied by this skin automatically when it is used.
 *
 * @author Mikle Garin
 * @see <a
 *      href="https://github.com/mgarin/weblaf/wiki/How-to-use-StyleManager">How
 *      to use StyleManager</a>
 * @see org.ocsoft.flatlaf.managers.style.StyleManager
 * @see org.ocsoft.flatlaf.managers.style.skin.WebLafSkin
 * @see org.ocsoft.flatlaf.managers.style.data.SkinInfo
 */

public class CustomSkin extends WebLafSkin {
    /**
     * Theme information. Contains complete information about this theme.
     */
    protected SkinInfo skinInfo;
    
    /**
     * Constructs new custom theme.
     *
     * @param location
     *            skin info XML location relative to this class
     */
    public CustomSkin(final String location) {
        super();
        this.skinInfo = XmlUtils.fromXML(this.getClass().getResource(location));
    }
    
    /**
     * Constructs new custom theme.
     *
     * @param skinInfo
     *            theme information
     */
    public CustomSkin(final SkinInfo skinInfo) {
        super();
        this.skinInfo = skinInfo;
    }
    
    /**
     * Returns theme information.
     *
     * @return theme information
     */
    public SkinInfo getSkinInfo() {
        return skinInfo;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return skinInfo.getId();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return skinInfo.getName();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return skinInfo.getDescription();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getAuthor() {
        return skinInfo.getAuthor();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getSupportedSystems() {
        return skinInfo.getSupportedSystemsList();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getSkinClass() {
        return skinInfo.getSkinClass();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ComponentStyle getComponentStyle(final JComponent component,
            final SupportedComponent type) {
        return skinInfo.getStyle(component, type);
    }
}