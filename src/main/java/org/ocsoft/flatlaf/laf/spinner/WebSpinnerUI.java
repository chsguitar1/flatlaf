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

package org.ocsoft.flatlaf.laf.spinner;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.LookAndFeel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSpinnerUI;
import javax.swing.text.JTextComponent;

import org.ocsoft.flatlaf.ResourceLoader;
import org.ocsoft.flatlaf.core.constants.FlatLafConstants;
import org.ocsoft.flatlaf.laf.text.WebTextFieldUI;
import org.ocsoft.flatlaf.utils.LafUtils;
import org.ocsoft.flatlaf.utils.SwingUtils;
import org.ocsoft.flatlaf.utils.laf.ShapeProvider;
import org.ocsoft.flatlaf.utils.swing.BorderMethods;
import org.ocsoft.flatlaf.weblaf.FlatLafStyleConstants;
import org.ocsoft.flatlaf.weblaf.button.WebButton;
import org.ocsoft.flatlaf.weblaf.spinner.WebSpinnerStyle;

/**
 * @author Mikle Garin
 */

public class WebSpinnerUI extends BasicSpinnerUI implements ShapeProvider,
        BorderMethods {
    private static final ImageIcon UP_ICON = ResourceLoader.loadIcon("spinner/up.png");
    private static final ImageIcon DOWN_ICON = ResourceLoader.loadIcon("spinner/down.png");
    
    private boolean drawBorder = WebSpinnerStyle.drawBorder;
    private boolean drawFocus = WebSpinnerStyle.drawFocus;
    private int round = WebSpinnerStyle.round;
    private int shadeWidth = WebSpinnerStyle.shadeWidth;
    
    
    public static ComponentUI createUI(final JComponent c) {
        return new WebSpinnerUI();
    }
    
    @Override
    public void installUI(final JComponent c) {
        super.installUI(c);
        
        // Default settings
        SwingUtils.setOrientation(spinner);
        LookAndFeel.installProperty(spinner, FlatLafConstants.OPAQUE_PROPERTY,
                Boolean.FALSE);
        spinner.setBackground(Color.WHITE);
        
        // Updating border
        updateBorder();
    }
    
    @Override
    public Shape provideShape() {
        return LafUtils.getWebBorderShape(spinner, getShadeWidth(), getRound());
    }
    
    @Override
    public void updateBorder() {
        if (spinner != null) {
            // Preserve old borders
            if (SwingUtils.isPreserveBorders(spinner)) {
                return;
            }
            
            if (drawBorder) {
                spinner.setBorder(BorderFactory.createEmptyBorder(
                        shadeWidth + 2, shadeWidth + 2, shadeWidth + 2,
                        shadeWidth + 2));
            } else {
                spinner.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
            }
        }
    }
    
    public int getShadeWidth() {
        return shadeWidth;
    }
    
    public void setShadeWidth(final int shadeWidth) {
        this.shadeWidth = shadeWidth;
        updateBorder();
    }
    
    public int getRound() {
        return round;
    }
    
    public void setRound(final int round) {
        this.round = round;
        updateBorder();
    }
    
    public boolean isDrawBorder() {
        return drawBorder;
    }
    
    public void setDrawBorder(final boolean drawBorder) {
        this.drawBorder = drawBorder;
        updateBorder();
    }
    
    public boolean isDrawFocus() {
        return drawFocus;
    }
    
    public void setDrawFocus(final boolean drawFocus) {
        this.drawFocus = drawFocus;
    }
    
    @Override
    public void paint(final Graphics g, final JComponent c) {
        // Border, background and shade
        LafUtils.drawWebStyle(
                (Graphics2D) g,
                c,
                drawFocus && SwingUtils.hasFocusOwner(spinner) ? FlatLafStyleConstants.fieldFocusColor
                        : FlatLafStyleConstants.shadeColor, shadeWidth, round);
        
        super.paint(g, c);
    }
    
    @Override
    protected Component createNextButton() {
        final WebButton nextButton = WebButton.createIconWebButton(UP_ICON,
                FlatLafStyleConstants.smallRound, 1, 2);
        nextButton.setLeftRightSpacing(1);
        nextButton.setDrawFocus(false);
        nextButton.setFocusable(false);
        
        nextButton.setName("Spinner.nextButton");
        installNextButtonListeners(nextButton);
        
        return nextButton;
    }
    
    @Override
    protected Component createPreviousButton() {
        final WebButton previousButton = WebButton.createIconWebButton(
                DOWN_ICON, FlatLafStyleConstants.smallRound, 1, 2);
        previousButton.setLeftRightSpacing(1);
        previousButton.setDrawFocus(false);
        previousButton.setFocusable(false);
        
        previousButton.setName("Spinner.previousButton");
        installPreviousButtonListeners(previousButton);
        
        return previousButton;
    }
    
    @Override
    protected JComponent createEditor() {
        final JComponent editor = super.createEditor();
        if (editor instanceof JTextComponent) {
            installFieldUI((JTextComponent) editor, spinner);
        } else {
            installFieldUI(((JSpinner.DefaultEditor) editor).getTextField(),
                    spinner);
        }
        return editor;
    }
    
    public static void installFieldUI(final JTextComponent field,
            final JSpinner spinner) {
        field.setMargin(new Insets(0, 0, 0, 0));
        field.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        final WebTextFieldUI textFieldUI = new WebTextFieldUI();
        textFieldUI.setDrawBorder(false);
        field.setUI(textFieldUI);
        
        field.setOpaque(true);
        field.setBackground(Color.WHITE);
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(final FocusEvent e) {
                spinner.repaint();
            }
            
            @Override
            public void focusLost(final FocusEvent e) {
                spinner.repaint();
            }
        });
    }
}