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

package org.ocsoft.flatlaf.utils.laf;

import java.awt.Component;
import java.awt.Shape;

import org.ocsoft.flatlaf.utils.LafUtils;

/**
 * User: mgarin Date: 12/6/11 Time: 3:52 PM
 */

public class WebShapeProducer extends ShapeProducer {
    private ShapeProvider shapeProvider;
    
    public WebShapeProducer(final Component component) {
        super(component);
    }
    
    @Override
    public void setProduceFor(final Component produceFor) {
        super.setProduceFor(produceFor);
        
        this.shapeProvider = LafUtils.getShapeProvider(produceFor);
    }
    
    @Override
    public Shape produce() {
        if (shapeProvider != null) {
            return shapeProvider.provideShape();
        } else {
            return null;
        }
    }
}