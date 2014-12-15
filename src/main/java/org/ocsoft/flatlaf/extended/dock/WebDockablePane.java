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

package org.ocsoft.flatlaf.extended.dock;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import org.ocsoft.flatlaf.laf.FlatLafStyleConstants;
import org.ocsoft.flatlaf.laf.label.WebLabel;
import org.ocsoft.flatlaf.laf.panel.WebPanel;
import org.ocsoft.flatlaf.laf.tabbedpane.TabbedPaneStyle;
import org.ocsoft.flatlaf.laf.tabbedpane.WebTabbedPane;
import org.ocsoft.flatlaf.laf.text.WebTextArea;

/**
 * User: mgarin Date: 28.06.12 Time: 13:31
 */

public class WebDockablePane extends WebPanel
{
    private static final Color buttonsPaneBackground = new Color ( 240, 240, 240 );

    public WebDockablePane ()
    {
        super ( new DockingPaneLayout () );
    }

    public DockingPaneLayout getActualLayout ()
    {
        return ( DockingPaneLayout ) getLayout ();
    }

    //    public void addDockableFrame ( final WebDockableFrame dockableFrame, final String position )
    //    {
    //        // todo
    //    }

    @Override
    protected void paintComponent ( final Graphics g )
    {
        super.paintComponent ( g );

        final Graphics2D g2d = ( Graphics2D ) g;
        final DockingPaneLayout layout = getActualLayout ();
        final DockingPaneInfo info = layout.getDockingPaneInfo ();

        final int x = info.hasLeftButtons ? info.leftButtonsPaneBounds.x + info.leftButtonsPaneBounds.width : info.rect.x;
        final int y = info.hasTopButtons ? info.topButtonsPaneBounds.y + info.topButtonsPaneBounds.height : info.rect.y;
        final int x2 = info.hasRightButtons ? info.rightButtonsPaneBounds.x - 1 : info.rect.x + info.rect.width - 1;
        final int y2 = info.hasBottomButtons ? info.bottomButtonsPaneBounds.y - 1 : info.rect.y + info.rect.height - 1;

        if ( info.hasTopButtons )
        {
            g2d.setPaint ( buttonsPaneBackground );
            g2d.fill ( info.topButtonsPaneBounds );
            g2d.setPaint ( FlatLafStyleConstants.darkBorderColor );
            g2d.drawLine ( x, y, x2, y );
        }
        if ( info.hasLeftButtons )
        {
            g2d.setPaint ( buttonsPaneBackground );
            g2d.fill ( info.leftButtonsPaneBounds );
            g2d.setPaint ( FlatLafStyleConstants.darkBorderColor );
            g2d.drawLine ( x, y, x, y2 );
        }
        if ( info.hasRightButtons )
        {
            g2d.setPaint ( buttonsPaneBackground );
            g2d.fill ( info.rightButtonsPaneBounds );
            g2d.setPaint ( FlatLafStyleConstants.darkBorderColor );
            g2d.drawLine ( x2, y, x2, y2 );
        }
        if ( info.hasBottomButtons )
        {
            g2d.setPaint ( buttonsPaneBackground );
            g2d.fill ( info.bottomButtonsPaneBounds );
            g2d.setPaint ( FlatLafStyleConstants.darkBorderColor );
            g2d.drawLine ( x, y2, x2, y2 );
        }
    }

    private static final ImageIcon top = new ImageIcon ( WebDockablePane.class.getResource ( "icons/dock_top_.png" ) );
    private static final ImageIcon left = new ImageIcon ( WebDockablePane.class.getResource ( "icons/dock_left_.png" ) );
    private static final ImageIcon right = new ImageIcon ( WebDockablePane.class.getResource ( "icons/dock_right_.png" ) );
    // private static ImageIcon bottom = new ImageIcon ( WebDockablePane.class.getResource ( "icons/dock_bottom_.png" ) );

    private static Component createLeftFrame ()
    {
        final WebDockableFrame frame = new WebDockableFrame ( left, "Left frame" );
        frame.setFrameType ( FrameType.left );
        frame.add ( new WebTextArea ( "123" ) );
        //        frame.setPainter ( PopupManager.getPopupPainter ( PopupStyle.lightSmall ) );
        return frame;
    }

    private static Component createTopFrame ()
    {
        final WebDockableFrame frame = new WebDockableFrame ( top, "Top frame" );
        frame.setFrameType ( FrameType.top );
        frame.add ( new WebTextArea ( "123", 3, 0 ) );
        //        frame.setPainter ( PopupManager.getPopupPainter ( PopupStyle.lightSmall ) );
        return frame;
    }

    private static Component createContent ()
    {
        final WebTabbedPane content = new WebTabbedPane ( WebTabbedPane.TOP );
        content.setTabbedPaneStyle ( TabbedPaneStyle.attached );
        for ( int i = 0; i < 20; i++ )
        {
            content.addTab ( "Tab " + i, new WebLabel () );
        }
        return new WebPanel ( true, content ).setShadeWidth ( 3 );
        //        return new WebPanel ( PopupManager.getPopupPainter ( PopupStyle.lightSmall ), content );
    }
}