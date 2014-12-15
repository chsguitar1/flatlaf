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

package org.ocsoft.flatlaf.laf.list;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicListUI;

import org.ocsoft.flatlaf.core.FlatLookAndFeel;
import org.ocsoft.flatlaf.laf.FlatLafStyleConstants;
import org.ocsoft.flatlaf.managers.tooltip.TooltipManager;
import org.ocsoft.flatlaf.managers.tooltip.WebCustomTooltip;
import org.ocsoft.flatlaf.utils.GeometryUtils;
import org.ocsoft.flatlaf.utils.LafUtils;
import org.ocsoft.flatlaf.utils.SwingUtils;
import org.ocsoft.flatlaf.utils.graphics.GraphicsUtils;
import org.ocsoft.flatlaf.utils.swing.WebTimer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * Custom UI for JList component.
 *
 * @author Mikle Garin
 */

public class WebListUI extends BasicListUI
{
    /**
     * todo 1. Visual shade effect on sides when scrollable
     * todo 2. Even-odd cells highlight
     */

    /**
     * Style settings.
     */
    protected boolean decorateSelection = WebListStyle.decorateSelection;
    protected boolean highlightRolloverCell = WebListStyle.highlightRolloverCell;
    protected int selectionRound = WebListStyle.selectionRound;
    protected int selectionShadeWidth = WebListStyle.selectionShadeWidth;
    protected boolean webColoredSelection = WebListStyle.webColoredSelection;
    protected Color selectionBorderColor = WebListStyle.selectionBorderColor;
    protected Color selectionBackgroundColor = WebListStyle.selectionBackgroundColor;
    protected boolean autoScrollToSelection = WebListStyle.autoScrollToSelection;

    /**
     * List listeners.
     */
    protected MouseAdapter mouseoverAdapter;
    protected ListSelectionListener selectionListener;

    /**
     * Runtime variables.
     */
    protected int rolloverIndex = -1;

    /**
     * Returns an instance of the WebListUI for the specified component.
     * This tricky method is used by UIManager to create component UIs when needed.
     *
     * @param c component that will use UI instance
     * @return instance of the WebListUI
     */
    @SuppressWarnings ( "UnusedParameters" )
    public static ComponentUI createUI ( final JComponent c )
    {
        return new WebListUI ();
    }

    /**
     * Installs UI in the specified component.
     *
     * @param c component for this UI
     */
    @Override
    public void installUI ( final JComponent c )
    {
        super.installUI ( c );

        // Default settings
        SwingUtils.setOrientation ( list );
        LookAndFeel.installProperty ( list, FlatLookAndFeel.OPAQUE_PROPERTY, Boolean.TRUE );
        list.setBackground ( new ColorUIResource ( WebListStyle.background ) );
        list.setForeground ( new ColorUIResource ( WebListStyle.foreground ) );

        // Rollover listener
        mouseoverAdapter = new MouseAdapter ()
        {
            private WebCustomTooltip tooltip = null;
            private WebTimer delayTimer = null;

            @Override
            public void mouseMoved ( final MouseEvent e )
            {
                updateMouseover ( e );
            }

            @Override
            public void mouseDragged ( final MouseEvent e )
            {
                updateMouseover ( e );
            }

            private void updateMouseover ( final MouseEvent e )
            {
                // Determining highlighted cell index
                int index = list.locationToIndex ( e.getPoint () );
                final Rectangle bounds = list.getCellBounds ( index, index );
                if ( bounds == null || !bounds.contains ( e.getPoint () ) || !list.isEnabled () )
                {
                    index = -1;
                }

                // Updating rollover cell
                if ( rolloverIndex != index )
                {
                    final int oldIndex = rolloverIndex;
                    rolloverIndex = index;
                    rolloverIndexChanged ( oldIndex, rolloverIndex );
                }
            }

            @Override
            public void mouseExited ( final MouseEvent e )
            {
                // Cleaning up rollover index
                if ( rolloverIndex != -1 )
                {
                    final int oldIndex = rolloverIndex;
                    rolloverIndex = -1;
                    rolloverIndexChanged ( oldIndex, rolloverIndex );
                }
            }

            private void rolloverIndexChanged ( final int oldIndex, final int newIndex )
            {
                // Destroy old tooltip
                if ( oldIndex != -1 )
                {
                    if ( tooltip != null )
                    {
                        tooltip.closeTooltip ();
                    }
                }

                // Repaint list only if rollover index is used
                if ( decorateSelection && highlightRolloverCell )
                {
                    final Rectangle oldBounds = list.getCellBounds ( oldIndex, oldIndex );
                    final Rectangle newBounds = list.getCellBounds ( newIndex, newIndex );
                    final Rectangle rect = GeometryUtils.getContainingRect ( oldBounds, newBounds );
                    if ( rect != null )
                    {
                        list.repaint ( rect );
                    }
                }

                // Close previously displayed tooltip
                // Display or delay new tooltip if needed
                if ( delayTimer != null )
                {
                    delayTimer.stop ();
                }
                if ( tooltip != null )
                {
                    tooltip.closeTooltip ();
                }
                if ( newIndex != -1 )
                {
                    final ListToolTipProvider provider = getToolTipProvider ();
                    if ( provider != null )
                    {
                        final long delay = provider.getDelay ();
                        if ( delay <= 0 )
                        {
                            showTooltip ( newIndex, provider );
                        }
                        else
                        {
                            delayTimer = WebTimer.delay ( delay, new ActionListener ()
                            {
                                @Override
                                public void actionPerformed ( final ActionEvent e )
                                {
                                    showTooltip ( newIndex, provider );
                                }
                            } );
                        }
                    }
                }
            }

            private void showTooltip ( final int index, final ListToolTipProvider provider )
            {
                final Object value = list.getModel ().getElementAt ( index );
                final boolean selected = list.isSelectedIndex ( index );
                tooltip = provider.getListCellToolTip ( list, value, index, selected );
                tooltip.setRelativeToBounds ( provider.getTooltipSourceBounds ( list, value, index, selected ) );
                TooltipManager.showOneTimeTooltip ( tooltip );
            }
        };
        list.addMouseListener ( mouseoverAdapter );
        list.addMouseMotionListener ( mouseoverAdapter );

        // Selection listener
        selectionListener = new ListSelectionListener ()
        {
            @Override
            public void valueChanged ( final ListSelectionEvent e )
            {
                if ( autoScrollToSelection )
                {
                    if ( list.getSelectedIndex () != -1 )
                    {
                        final int index = list.getLeadSelectionIndex ();
                        final Rectangle selection = getCellBounds ( list, index, index );
                        if ( selection != null && !selection.intersects ( list.getVisibleRect () ) )
                        {
                            list.scrollRectToVisible ( selection );
                        }
                    }
                }
            }
        };
        list.addListSelectionListener ( selectionListener );
    }

    /**
     * Returns custom WebLaF tooltip provider.
     *
     * @return custom WebLaF tooltip provider
     */
    protected ListToolTipProvider getToolTipProvider ()
    {
        return list != null && list instanceof WebList ? ( ( WebList ) list ).getToolTipProvider () : null;
    }

    /**
     * Uninstalls UI from the specified component.
     *
     * @param c component with this UI
     */
    @Override
    public void uninstallUI ( final JComponent c )
    {
        list.removeMouseListener ( mouseoverAdapter );
        list.removeMouseMotionListener ( mouseoverAdapter );
        list.removeListSelectionListener ( selectionListener );
        super.uninstallUI ( c );
    }

    /**
     * Returns whether should decorate selected and rollover cells or not.
     *
     * @return true if should decorate selected and rollover cells, false otherwise
     */
    public boolean isDecorateSelection ()
    {
        return decorateSelection;
    }

    /**
     * Sets whether should decorate selected and rollover cells or not.
     *
     * @param decorateSelection whether should decorate selected and rollover cells or not
     */
    public void setDecorateSelection ( final boolean decorateSelection )
    {
        this.decorateSelection = decorateSelection;
    }

    /**
     * Returns whether should highlight rollover cell or not.
     *
     * @return true if rollover cell is being highlighted, false otherwise
     */
    public boolean isHighlightRolloverCell ()
    {
        return highlightRolloverCell;
    }

    /**
     * Sets whether should highlight rollover cell or not.
     *
     * @param highlightRolloverCell whether should highlight rollover cell or not
     */
    public void setHighlightRolloverCell ( final boolean highlightRolloverCell )
    {
        this.highlightRolloverCell = highlightRolloverCell;
    }

    /**
     * Returns cells selection rounding.
     *
     * @return cells selection rounding
     */
    public int getSelectionRound ()
    {
        return selectionRound;
    }

    /**
     * Sets cells selection rounding.
     *
     * @param selectionRound new cells selection rounding
     */
    public void setSelectionRound ( final int selectionRound )
    {
        this.selectionRound = selectionRound;
    }

    /**
     * Returns cells selection shade width.
     *
     * @return cells selection shade width
     */
    public int getSelectionShadeWidth ()
    {
        return selectionShadeWidth;
    }

    /**
     * Sets cells selection shade width.
     *
     * @param selectionShadeWidth new cells selection shade width
     */
    public void setSelectionShadeWidth ( final int selectionShadeWidth )
    {
        this.selectionShadeWidth = selectionShadeWidth;
    }

    /**
     * Returns whether selection should be web-colored or not.
     * In case it is not web-colored selectionBackgroundColor value will be used as background color.
     *
     * @return true if selection should be web-colored, false otherwise
     */
    public boolean isWebColoredSelection ()
    {
        return webColoredSelection;
    }

    /**
     * Sets whether selection should be web-colored or not.
     * In case it is not web-colored selectionBackgroundColor value will be used as background color.
     *
     * @param webColored whether selection should be web-colored or not
     */
    public void setWebColoredSelection ( final boolean webColored )
    {
        this.webColoredSelection = webColored;
    }

    /**
     * Returns selection border color.
     *
     * @return selection border color
     */
    public Color getSelectionBorderColor ()
    {
        return selectionBorderColor;
    }

    /**
     * Sets selection border color.
     *
     * @param color selection border color
     */
    public void setSelectionBorderColor ( final Color color )
    {
        this.selectionBorderColor = color;
    }

    /**
     * Returns selection background color.
     * It is used only when webColoredSelection is set to false.
     *
     * @return selection background color
     */
    public Color getSelectionBackgroundColor ()
    {
        return selectionBackgroundColor;
    }

    /**
     * Sets selection background color.
     * It is used only when webColoredSelection is set to false.
     *
     * @param color selection background color
     */
    public void setSelectionBackgroundColor ( final Color color )
    {
        this.selectionBackgroundColor = color;
    }

    /**
     * Returns whether to scroll list down to selection automatically or not.
     *
     * @return true if list is being automatically scrolled to selection, false otherwise
     */
    public boolean isAutoScrollToSelection ()
    {
        return autoScrollToSelection;
    }

    /**
     * Sets whether to scroll list down to selection automatically or not.
     *
     * @param autoScrollToSelection whether to scroll list down to selection automatically or not
     */
    public void setAutoScrollToSelection ( final boolean autoScrollToSelection )
    {
        this.autoScrollToSelection = autoScrollToSelection;
    }

    //    /**
    //     * Paints list content.
    //     *
    //     * @param g graphics context
    //     * @param c painted component
    //     */
    //    @Override
    //    public void paint ( final Graphics g, final JComponent c )
    //    {
    //        super.paint ( g, c );
    //
    //        Rectangle vr = c.getVisibleRect ();
    //        if ( vr.y > 0 )
    //        {
    //            final int shadeWidth = 10;
    //            float opacity = shadeWidth > vr.y ? 1f - ( float ) ( shadeWidth - vr.y ) / shadeWidth : 1f;
    //            NinePatchIcon npi = NinePatchUtils.getShadeIcon ( shadeWidth, 0, opacity );
    //            Dimension ps = npi.getPreferredSize ();
    //            npi.paintIcon ( ( Graphics2D ) g, vr.x - shadeWidth, vr.y + shadeWidth - ps.height, vr.width + shadeWidth * 2, ps.height );
    //        }
    //    }

    /**
     * Paint one List cell: compute the relevant state, get the "rubber stamp" cell renderer component, and then use the CellRendererPane
     * to paint it. Subclasses may want to override this method rather than paint().
     *
     * @param g            graphics context
     * @param index        cell index
     * @param rowBounds    cell bounds
     * @param cellRenderer cell renderer
     * @param dataModel    list model
     * @param selModel     list selection model
     * @param leadIndex    lead cell index
     * @see #paint
     */
    @Override
    protected void paintCell ( final Graphics g, final int index, final Rectangle rowBounds, final ListCellRenderer cellRenderer,
                               final ListModel dataModel, final ListSelectionModel selModel, final int leadIndex )
    {
        //        if ( list.getLayoutOrientation () == WebList.VERTICAL && ( evenLineColor != null || oddLineColor != null ) )
        //        {
        //            boolean even = index % 2 == 0;
        //            if ( even && evenLineColor != null )
        //            {
        //                g.setColor ( evenLineColor );
        //                g.fillRect ( rowBounds.x, rowBounds.y, rowBounds.width, rowBounds.height );
        //            }
        //            if ( !even && oddLineColor != null )
        //            {
        //                g.setColor ( oddLineColor );
        //                g.fillRect ( rowBounds.x, rowBounds.y, rowBounds.width, rowBounds.height );
        //            }
        //        }

        final Object value = dataModel.getElementAt ( index );
        final boolean isSelected = selModel.isSelectedIndex ( index );

        if ( decorateSelection && ( isSelected || index == rolloverIndex ) )
        {
            final Graphics2D g2d = ( Graphics2D ) g;
            final Composite oc = GraphicsUtils.setupAlphaComposite ( g2d, 0.35f, !isSelected );

            final Rectangle rect = new Rectangle ( rowBounds );
            rect.x += selectionShadeWidth;
            rect.y += selectionShadeWidth;
            rect.width -= selectionShadeWidth * 2 + ( selectionBorderColor != null ? 1 : 0 );
            rect.height -= selectionShadeWidth * 2 + ( selectionBorderColor != null ? 1 : 0 );

            LafUtils.drawCustomWebBorder ( g2d, list,
                    new RoundRectangle2D.Double ( rect.x, rect.y, rect.width, rect.height, selectionRound * 2, selectionRound * 2 ),
                    FlatLafStyleConstants.shadeColor, selectionShadeWidth, true, webColoredSelection, selectionBorderColor, selectionBorderColor,
                    selectionBackgroundColor );

            GraphicsUtils.restoreComposite ( g2d, oc, !isSelected );
        }

        final boolean cellHasFocus = list.hasFocus () && ( index == leadIndex );
        final Component rendererComponent = cellRenderer.getListCellRendererComponent ( list, value, index, isSelected, cellHasFocus );
        rendererPane.paintComponent ( g, rendererComponent, list, rowBounds.x, rowBounds.y, rowBounds.width, rowBounds.height, true );
    }
}