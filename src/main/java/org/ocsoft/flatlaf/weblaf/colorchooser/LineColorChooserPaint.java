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

package org.ocsoft.flatlaf.weblaf.colorchooser;

import java.awt.Color;
import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.HashMap;
import java.util.Map;

import org.ocsoft.flatlaf.utils.ColorUtils;

/**
 * User: mgarin Date: 07.07.2010 Time: 17:30:17
 */

public class LineColorChooserPaint implements Paint {
    private boolean webSafe = false;
    
    private ColorModel model = ColorModel.getRGBdefault();
    
    private int y;
    private int height;
    
    public LineColorChooserPaint(int y, int height) {
        super();
        this.y = y;
        this.height = height;
    }
    
    @Override
    public PaintContext createContext(ColorModel cm, Rectangle deviceBounds,
            Rectangle2D userBounds, final AffineTransform xform,
            RenderingHints hints) {
        return new PaintContext() {
            private Map<Rectangle, WritableRaster> rastersCache = new HashMap<Rectangle, WritableRaster>();
            
            @Override
            public void dispose() {
                rastersCache.clear();
            }
            
            @Override
            public ColorModel getColorModel() {
                return model;
            }
            
            @Override
            public Raster getRaster(int x, int y, int w, int h) {
                Rectangle r = new Rectangle(x, y, w, h);
                if (rastersCache.containsKey(r)) {
                    return rastersCache.get(r);
                } else {
                    WritableRaster raster = model
                            .createCompatibleWritableRaster(w, h);
                    
                    y -= Math.round(xform.getTranslateY());
                    
                    int[] data = new int[w * h * 4];
                    for (int j = 0; j < h; j++) {
                        for (int i = 0; i < w; i++) {
                            Color color = new HSBColor(
                                    1f
                                            - (float) (y + j)
                                            / (LineColorChooserPaint.this.y * 2 + height),
                                    1f, 1f).getColor();
                            
                            int base = (j * w + i) * 4;
                            data[base] = getWebSafe(color.getRed());
                            data[base + 1] = getWebSafe(color.getGreen());
                            data[base + 2] = getWebSafe(color.getBlue());
                            data[base + 3] = 255;
                        }
                    }
                    raster.setPixels(0, 0, w, h, data);
                    
                    rastersCache.put(r, raster);
                    return raster;
                }
            }
        };
    }
    
    public Color getColor(int yCoord) {
        return new HSBColor(1f - Math.max(0,
                Math.min((float) (yCoord - y) / height, 1f)), 1f, 1f)
                .getColor();
    }
    
    private int getWebSafe(int color) {
        if (webSafe) {
            color = ColorUtils.getWebSafeValue(color);
        }
        if (color < 0) {
            color = 0;
        } else if (color > 255) {
            color = 255;
        }
        return color;
    }
    
    @Override
    public int getTransparency() {
        return OPAQUE;
    }
    
    public boolean isWebSafe() {
        return webSafe;
    }
    
    public void setWebSafe(boolean webSafe) {
        this.webSafe = webSafe;
    }
}
