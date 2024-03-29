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

/*
 Copyright 2006 Jerry Huxtable

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package org.ocsoft.flatlaf.utils.filters;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * A filter which produces motion blur the faster, but lower-quality way.
 */

public class MotionBlurOp extends AbstractBufferedImageOp {
    private float alignX;
    private float alignY;
    private float distance;
    private float angle;
    private float rotation;
    private float zoom;
    
    public MotionBlurOp() {
        this(0f, 0f, 0f, 0f);
    }
    
    public MotionBlurOp(float distance, float angle, float rotation, float zoom) {
        this(distance, angle, rotation, zoom, 0.5f, 0.5f);
    }
    
    public MotionBlurOp(float distance, float angle, float rotation,
            float zoom, float alignX, float alignY) {
        this.distance = distance;
        this.angle = angle;
        this.rotation = rotation;
        this.zoom = zoom;
        this.alignX = alignX;
        this.alignY = alignY;
    }
    
    public void setAngle(float angle) {
        this.angle = angle;
    }
    
    public float getAngle() {
        return angle;
    }
    
    public void setDistance(float distance) {
        this.distance = distance;
    }
    
    public float getDistance() {
        return distance;
    }
    
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
    
    public float getRotation() {
        return rotation;
    }
    
    public void setZoom(float zoom) {
        this.zoom = zoom;
    }
    
    public float getZoom() {
        return zoom;
    }
    
    public void setAlignX(float alignX) {
        this.alignX = alignX;
    }
    
    public float getAlignX() {
        return alignX;
    }
    
    public void setAlignY(float alignY) {
        this.alignY = alignY;
    }
    
    public float getAlignY() {
        return alignY;
    }
    
    public void setAlign(Point2D align) {
        this.alignX = (float) align.getX();
        this.alignY = (float) align.getY();
    }
    
    public Point2D getAlign() {
        return new Point2D.Float(alignX, alignY);
    }
    
    private int log2(int n) {
        int m = 1;
        int log2n = 0;
        
        while (m < n) {
            m *= 2;
            log2n++;
        }
        return log2n;
    }
    
    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dst) {
        if (dst == null) {
            dst = createCompatibleDestImage(src, null);
        }
        BufferedImage tsrc = src;
        float cx = (float) src.getWidth() * alignX;
        float cy = (float) src.getHeight() * alignY;
        float imageRadius = (float) Math.sqrt(cx * cx + cy * cy);
        float translateX = (float) (distance * Math.cos(angle));
        float translateY = (float) (distance * -Math.sin(angle));
        float scale = zoom;
        float rotate = rotation;
        float maxDistance = distance + Math.abs(rotation * imageRadius) + zoom
                * imageRadius;
        int steps = log2((int) maxDistance);
        
        translateX /= maxDistance;
        translateY /= maxDistance;
        scale /= maxDistance;
        rotate /= maxDistance;
        
        if (steps == 0) {
            Graphics2D g = dst.createGraphics();
            g.drawRenderedImage(src, null);
            g.dispose();
            return dst;
        }
        
        BufferedImage tmp = createCompatibleDestImage(src, null);
        for (int i = 0; i < steps; i++) {
            Graphics2D g = tmp.createGraphics();
            g.drawImage(tsrc, null, null);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                    0.5f));
            
            g.translate(cx + translateX, cy + translateY);
            g.scale(1.0001 + scale, 1.0001 + scale); // The .0001 works round a
                                                     // bug on Windows where
                                                     // drawImage throws an
                                                     // ArrayIndexOutofBoundException
            if (rotation != 0) {
                g.rotate(rotate);
            }
            g.translate(-cx, -cy);
            
            g.drawImage(dst, null, null);
            g.dispose();
            BufferedImage ti = dst;
            dst = tmp;
            tmp = ti;
            tsrc = dst;
            
            translateX *= 2;
            translateY *= 2;
            scale *= 2;
            rotate *= 2;
        }
        return dst;
    }
}
