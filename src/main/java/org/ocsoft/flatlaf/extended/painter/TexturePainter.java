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

package org.ocsoft.flatlaf.extended.painter;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import org.ocsoft.flatlaf.utils.ImageUtils;
import org.ocsoft.flatlaf.utils.graphics.GraphicsUtils;

/**
 * Texure painter. This painter fills component background with a texture based
 * on the specified image.
 *
 * @param <E>
 *            component type
 * @author Mikle Garin
 * @see TextureType
 * @see AbstractPainter
 * @see Painter
 */

public class TexturePainter<E extends JComponent> extends AbstractPainter<E> {
    /**
     * Cached texture paint.
     */
    protected TexturePaint paint = null;
    
    /**
     * Texture type.
     */
    protected TextureType textureType = null;
    
    /**
     * Constructs texture paint with the specified icon as a texture.
     *
     * @param icon
     *            texture icon
     */
    public TexturePainter(final ImageIcon icon) {
        this(icon.getImage());
    }
    
    /**
     * Constructs texture paint with the specified image as a texture.
     *
     * @param image
     *            texture image
     */
    public TexturePainter(final Image image) {
        this(ImageUtils.getBufferedImage(image));
    }
    
    /**
     * Constructs texture paint with the specified image as a texture.
     *
     * @param image
     *            texture image
     */
    public TexturePainter(final BufferedImage image) {
        super();
        updatePainter(image);
    }
    
    /**
     * Constructs texture paint with the specified texture type.
     *
     * @param textureType
     *            predefined texture type
     */
    public TexturePainter(final TextureType textureType) {
        super();
        setTextureType(textureType);
    }
    
    /**
     * Returns texture type.
     *
     * @return texture type
     * @see TextureType
     */
    public TextureType getTextureType() {
        return textureType;
    }
    
    /**
     * Sets texture type.
     *
     * @param textureType
     *            new texture type
     * @see TextureType
     */
    public void setTextureType(final TextureType textureType) {
        this.textureType = textureType;
        updatePainter(textureType == null ? null : textureType.getTexture());
    }
    
    /**
     * Returns texture image.
     *
     * @return texture image
     */
    public BufferedImage getImage() {
        return paint != null ? paint.getImage() : null;
    }
    
    /**
     * Sets texture image.
     *
     * @param image
     *            new texture image
     */
    public void setImage(final BufferedImage image) {
        updatePainter(image);
    }
    
    /**
     * Updates cached texture paint.
     *
     * @param image
     *            texture image
     */
    protected void updatePainter(final BufferedImage image) {
        this.paint = image != null ? new TexturePaint(image, new Rectangle(0,
                0, image.getWidth(), image.getHeight())) : null;
        repaint();
    }
    
    /**
     * Paints visual data onto the component graphics. Provided graphics and
     * component are taken directly from component UI paint method. Provided
     * bounds are usually fake (zero location, component size) but in some cases
     * it might be specified by componentUI.
     *
     * @param g2d
     *            component graphics
     * @param bounds
     *            bounds for painter visual data
     * @param c
     *            component to process
     */
    @Override
    public void paint(final Graphics2D g2d, final Rectangle bounds, final E c) {
        // Do not paint anything if texture paint is not set
        if (paint != null) {
            // Determining actual rect to be filled (we don't need to fill
            // invisible area)
            final Rectangle r = c.getVisibleRect().intersection(bounds);
            
            // If there is anything to fill we do it
            if (r.width > 0 && r.height > 0) {
                final Object old = GraphicsUtils.setupImageQuality(g2d);
                g2d.setPaint(paint);
                g2d.fillRect(r.x, r.y, r.width, r.height);
                GraphicsUtils.restoreImageQuality(g2d, old);
            }
        }
    }
}