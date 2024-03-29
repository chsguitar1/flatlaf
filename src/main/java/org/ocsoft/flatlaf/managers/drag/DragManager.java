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

package org.ocsoft.flatlaf.managers.drag;

import java.awt.Component;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceAdapter;
import java.awt.dnd.DragSourceContext;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import org.ocsoft.flatlaf.managers.glasspane.GlassPaneManager;
import org.ocsoft.flatlaf.managers.glasspane.WebGlassPane;
import org.ocsoft.flatlaf.utils.SwingUtils;
import org.ocsoft.flatlaf.utils.system.FlatLafLogger;

/**
 * This manager simplifies dragged items visual representation creation. You can
 * add customized representation support for DataFlavor by registering new
 * DragViewHandler. So far custom DataFlavor view will be displayed only within
 * application window bounds.
 *
 * @author Mikle Garin
 */

public class DragManager {
    /**
     * todo 1. Move dragged object display to a separate transparent
     * non-focusable window
     */
    
    /**
     * Drag view handlers map.
     */
    protected static Map<DataFlavor, DragViewHandler> viewHandlers;
    
    /**
     * Dragged object representation variables.
     */
    protected static WebGlassPane glassPane;
    protected static Object data;
    protected static BufferedImage view;
    protected static Component dropLocation;
    protected static DragViewHandler dragViewHandler;
    
    /**
     * Whether manager is initialized or not.
     */
    protected static boolean initialized = false;
    
    /**
     * Initializes manager if it wasn't already initialized.
     */
    public static synchronized void initialize() {
        // To avoid more than one initialization
        if (!initialized) {
            // Remember that initialization happened
            initialized = true;
            
            // View handlers map
            viewHandlers = new HashMap<DataFlavor, DragViewHandler>();
            
            final DragSourceAdapter dsa = new DragSourceAdapter() {
                @Override
                public void dragEnter(final DragSourceDragEvent dsde) {
                    actualDragEnter(dsde);
                }
                
                protected void actualDragEnter(final DragSourceDragEvent dsde) {
                    // todo Do not recreate view few times while dragging
                    
                    // Save drop location component
                    final DragSourceContext dsc = dsde.getDragSourceContext();
                    dropLocation = dsc.getComponent();
                    
                    // Deciding on enter what to display for this kind of data
                    final Transferable transferable = dsc.getTransferable();
                    final DataFlavor[] flavors = transferable
                            .getTransferDataFlavors();
                    for (final DataFlavor flavor : flavors) {
                        if (viewHandlers.containsKey(flavor)) {
                            try {
                                data = transferable.getTransferData(flavor);
                                dragViewHandler = viewHandlers.get(flavor);
                                view = dragViewHandler.getView(data, dsde);
                                
                                glassPane = GlassPaneManager.getGlassPane(dsc
                                        .getComponent());
                                glassPane.setPaintedImage(view,
                                        getLocation(glassPane, dsde));
                                
                                break;
                            } catch (final Throwable e) {
                                FlatLafLogger.error(DragManager.class, e);
                            }
                        }
                    }
                }
                
                @Override
                public void dragMouseMoved(final DragSourceDragEvent dsde) {
                    final DragSourceContext dsc = dsde.getDragSourceContext();
                    if (dsc.getComponent() != dropLocation) {
                        actualDragEnter(dsde);
                    }
                    
                    // Move displayed data
                    if (view != null) {
                        final WebGlassPane gp = GlassPaneManager
                                .getGlassPane(dsde.getDragSourceContext()
                                        .getComponent());
                        if (gp != glassPane) {
                            glassPane.clearPaintedImage();
                            glassPane = gp;
                        }
                        glassPane.setPaintedImage(view,
                                getLocation(glassPane, dsde));
                    }
                }
                
                public Point getLocation(final WebGlassPane gp,
                        final DragSourceDragEvent dsde) {
                    final Point mp = SwingUtils.getMousePoint(gp);
                    final Point vp = dragViewHandler.getViewRelativeLocation(
                            data, dsde);
                    return new Point(mp.x + vp.x, mp.y + vp.y);
                }
                
                @Override
                public void dragDropEnd(final DragSourceDropEvent dsde) {
                    // Cleanup drop location component
                    dropLocation = null;
                    
                    // Cleanup displayed data
                    if (view != null) {
                        dragViewHandler.dragEnded(data, dsde);
                        glassPane.clearPaintedImage();
                        glassPane = null;
                        data = null;
                        view = null;
                        dragViewHandler = null;
                    }
                }
            };
            DragSource.getDefaultDragSource().addDragSourceListener(dsa);
            DragSource.getDefaultDragSource().addDragSourceMotionListener(dsa);
        }
    }
    
    /**
     * Registers new DragViewHandler.
     *
     * @param dragViewHandler
     *            DragViewHandler to register
     */
    public static void registerViewHandler(final DragViewHandler dragViewHandler) {
        viewHandlers.put(dragViewHandler.getObjectFlavor(), dragViewHandler);
    }
    
    /**
     * Unregisters new DragViewHandler.
     *
     * @param dragViewHandler
     *            DragViewHandler to unregister
     */
    public static void unregisterViewHandler(
            final DragViewHandler dragViewHandler) {
        viewHandlers.remove(dragViewHandler.getObjectFlavor());
    }
}