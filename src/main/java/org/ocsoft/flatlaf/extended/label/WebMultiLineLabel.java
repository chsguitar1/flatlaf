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

package org.ocsoft.flatlaf.extended.label;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import org.ocsoft.flatlaf.core.FlatLafSettings;
import org.ocsoft.flatlaf.managers.hotkey.HotkeyData;
import org.ocsoft.flatlaf.managers.language.LanguageManager;
import org.ocsoft.flatlaf.managers.language.LanguageMethods;
import org.ocsoft.flatlaf.managers.language.data.TooltipWay;
import org.ocsoft.flatlaf.managers.language.updaters.LanguageUpdater;
import org.ocsoft.flatlaf.managers.tooltip.ToolTipMethods;
import org.ocsoft.flatlaf.managers.tooltip.TooltipManager;
import org.ocsoft.flatlaf.managers.tooltip.WebCustomTooltip;
import org.ocsoft.flatlaf.utils.EventUtils;
import org.ocsoft.flatlaf.utils.SwingUtils;
import org.ocsoft.flatlaf.utils.reflection.ReflectUtils;
import org.ocsoft.flatlaf.utils.swing.EventMethods;
import org.ocsoft.flatlaf.utils.swing.FocusEventRunnable;
import org.ocsoft.flatlaf.utils.swing.FontMethods;
import org.ocsoft.flatlaf.utils.swing.KeyEventRunnable;
import org.ocsoft.flatlaf.utils.swing.MouseButton;
import org.ocsoft.flatlaf.utils.swing.MouseEventRunnable;
import org.ocsoft.flatlaf.utils.system.FlatLafLogger;

/**
 * @author Mikle Garin
 */

public class WebMultiLineLabel extends JLabel implements EventMethods,
        ToolTipMethods, LanguageMethods, FontMethods<WebMultiLineLabel> {
    /**
     * Unique UI class ID.
     *
     * @see #getUIClassID
     */
    private static final String uiClassID = "MultiLineLabelUI";
    
    public WebMultiLineLabel() {
        super();
    }
    
    public WebMultiLineLabel(final Icon image) {
        super(image);
    }
    
    public WebMultiLineLabel(final Icon image, final int horizontalAlignment) {
        super(image, horizontalAlignment);
    }
    
    public WebMultiLineLabel(final String text) {
        super(text);
    }
    
    public WebMultiLineLabel(final String text, final int horizontalAlignment) {
        super(text, horizontalAlignment);
    }
    
    public WebMultiLineLabel(final String text, final Icon icon) {
        super(text, icon, LEADING);
    }
    
    public WebMultiLineLabel(final String text, final Icon icon,
            final int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
    }
    
    public boolean isDrawShade() {
        return getWebUI().isDrawShade();
    }
    
    public void setDrawShade(final boolean drawShade) {
        getWebUI().setDrawShade(drawShade);
    }
    
    public Color getShadeColor() {
        return getWebUI().getShadeColor();
    }
    
    public void setShadeColor(final Color shadeColor) {
        getWebUI().setShadeColor(shadeColor);
    }
    
    public WebMultiLineLabelUI getWebUI() {
        return (WebMultiLineLabelUI) getUI();
    }
    
    @Override
    public void updateUI() {
        if (getUI() == null || !(getUI() instanceof WebMultiLineLabelUI)) {
            try {
                setUI((WebMultiLineLabelUI) ReflectUtils
                        .createInstance(FlatLafSettings.multiLineLabelUI));
            } catch (final Throwable e) {
                FlatLafLogger.error(this, e);
                setUI(new WebMultiLineLabelUI());
            }
        } else {
            setUI(getUI());
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getUIClassID() {
        return uiClassID;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public MouseAdapter onMousePress(final MouseEventRunnable runnable) {
        return EventUtils.onMousePress(this, runnable);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public MouseAdapter onMousePress(final MouseButton mouseButton,
            final MouseEventRunnable runnable) {
        return EventUtils.onMousePress(this, mouseButton, runnable);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public MouseAdapter onMouseEnter(final MouseEventRunnable runnable) {
        return EventUtils.onMouseEnter(this, runnable);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public MouseAdapter onMouseExit(final MouseEventRunnable runnable) {
        return EventUtils.onMouseExit(this, runnable);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public MouseAdapter onMouseDrag(final MouseEventRunnable runnable) {
        return EventUtils.onMouseDrag(this, runnable);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public MouseAdapter onMouseDrag(final MouseButton mouseButton,
            final MouseEventRunnable runnable) {
        return EventUtils.onMouseDrag(this, mouseButton, runnable);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public MouseAdapter onMouseClick(final MouseEventRunnable runnable) {
        return EventUtils.onMouseClick(this, runnable);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public MouseAdapter onMouseClick(final MouseButton mouseButton,
            final MouseEventRunnable runnable) {
        return EventUtils.onMouseClick(this, mouseButton, runnable);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public MouseAdapter onDoubleClick(final MouseEventRunnable runnable) {
        return EventUtils.onDoubleClick(this, runnable);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public MouseAdapter onMenuTrigger(final MouseEventRunnable runnable) {
        return EventUtils.onMenuTrigger(this, runnable);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public KeyAdapter onKeyType(final KeyEventRunnable runnable) {
        return EventUtils.onKeyType(this, runnable);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public KeyAdapter onKeyType(final HotkeyData hotkey,
            final KeyEventRunnable runnable) {
        return EventUtils.onKeyType(this, hotkey, runnable);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public KeyAdapter onKeyPress(final KeyEventRunnable runnable) {
        return EventUtils.onKeyPress(this, runnable);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public KeyAdapter onKeyPress(final HotkeyData hotkey,
            final KeyEventRunnable runnable) {
        return EventUtils.onKeyPress(this, hotkey, runnable);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public KeyAdapter onKeyRelease(final KeyEventRunnable runnable) {
        return EventUtils.onKeyRelease(this, runnable);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public KeyAdapter onKeyRelease(final HotkeyData hotkey,
            final KeyEventRunnable runnable) {
        return EventUtils.onKeyRelease(this, hotkey, runnable);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public FocusAdapter onFocusGain(final FocusEventRunnable runnable) {
        return EventUtils.onFocusGain(this, runnable);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public FocusAdapter onFocusLoss(final FocusEventRunnable runnable) {
        return EventUtils.onFocusLoss(this, runnable);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebCustomTooltip setToolTip(final String tooltip) {
        return TooltipManager.setTooltip(this, tooltip);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebCustomTooltip setToolTip(final Icon icon, final String tooltip) {
        return TooltipManager.setTooltip(this, icon, tooltip);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebCustomTooltip setToolTip(final String tooltip,
            final TooltipWay tooltipWay) {
        return TooltipManager.setTooltip(this, tooltip, tooltipWay);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebCustomTooltip setToolTip(final Icon icon, final String tooltip,
            final TooltipWay tooltipWay) {
        return TooltipManager.setTooltip(this, icon, tooltip, tooltipWay);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebCustomTooltip setToolTip(final String tooltip,
            final TooltipWay tooltipWay, final int delay) {
        return TooltipManager.setTooltip(this, tooltip, tooltipWay, delay);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebCustomTooltip setToolTip(final Icon icon, final String tooltip,
            final TooltipWay tooltipWay, final int delay) {
        return TooltipManager
                .setTooltip(this, icon, tooltip, tooltipWay, delay);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebCustomTooltip setToolTip(final JComponent tooltip) {
        return TooltipManager.setTooltip(this, tooltip);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebCustomTooltip setToolTip(final JComponent tooltip, final int delay) {
        return TooltipManager.setTooltip(this, tooltip, delay);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebCustomTooltip setToolTip(final JComponent tooltip,
            final TooltipWay tooltipWay) {
        return TooltipManager.setTooltip(this, tooltip, tooltipWay);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebCustomTooltip setToolTip(final JComponent tooltip,
            final TooltipWay tooltipWay, final int delay) {
        return TooltipManager.setTooltip(this, tooltip, tooltipWay, delay);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebCustomTooltip addToolTip(final String tooltip) {
        return TooltipManager.addTooltip(this, tooltip);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebCustomTooltip addToolTip(final Icon icon, final String tooltip) {
        return TooltipManager.addTooltip(this, icon, tooltip);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebCustomTooltip addToolTip(final String tooltip,
            final TooltipWay tooltipWay) {
        return TooltipManager.addTooltip(this, tooltip, tooltipWay);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebCustomTooltip addToolTip(final Icon icon, final String tooltip,
            final TooltipWay tooltipWay) {
        return TooltipManager.addTooltip(this, icon, tooltip, tooltipWay);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebCustomTooltip addToolTip(final String tooltip,
            final TooltipWay tooltipWay, final int delay) {
        return TooltipManager.addTooltip(this, tooltip, tooltipWay, delay);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebCustomTooltip addToolTip(final Icon icon, final String tooltip,
            final TooltipWay tooltipWay, final int delay) {
        return TooltipManager
                .addTooltip(this, icon, tooltip, tooltipWay, delay);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebCustomTooltip addToolTip(final JComponent tooltip) {
        return TooltipManager.addTooltip(this, tooltip);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebCustomTooltip addToolTip(final JComponent tooltip, final int delay) {
        return TooltipManager.addTooltip(this, tooltip, delay);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebCustomTooltip addToolTip(final JComponent tooltip,
            final TooltipWay tooltipWay) {
        return TooltipManager.addTooltip(this, tooltip, tooltipWay);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebCustomTooltip addToolTip(final JComponent tooltip,
            final TooltipWay tooltipWay, final int delay) {
        return TooltipManager.addTooltip(this, tooltip, tooltipWay, delay);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void removeToolTip(final WebCustomTooltip tooltip) {
        TooltipManager.removeTooltip(this, tooltip);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void removeToolTips() {
        TooltipManager.removeTooltips(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void removeToolTips(final WebCustomTooltip... tooltips) {
        TooltipManager.removeTooltips(this, tooltips);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void removeToolTips(final List<WebCustomTooltip> tooltips) {
        TooltipManager.removeTooltips(this, tooltips);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setLanguage(final String key, final Object... data) {
        LanguageManager.registerComponent(this, key, data);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void updateLanguage(final Object... data) {
        LanguageManager.updateComponent(this, data);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void updateLanguage(final String key, final Object... data) {
        LanguageManager.updateComponent(this, key, data);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void removeLanguage() {
        LanguageManager.unregisterComponent(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLanguageSet() {
        return LanguageManager.isRegisteredComponent(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setLanguageUpdater(final LanguageUpdater updater) {
        LanguageManager.registerLanguageUpdater(this, updater);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void removeLanguageUpdater() {
        LanguageManager.unregisterLanguageUpdater(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebMultiLineLabel setPlainFont() {
        return SwingUtils.setPlainFont(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebMultiLineLabel setPlainFont(final boolean apply) {
        return SwingUtils.setPlainFont(this, apply);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPlainFont() {
        return SwingUtils.isPlainFont(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebMultiLineLabel setBoldFont() {
        return SwingUtils.setBoldFont(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebMultiLineLabel setBoldFont(final boolean apply) {
        return SwingUtils.setBoldFont(this, apply);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBoldFont() {
        return SwingUtils.isBoldFont(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebMultiLineLabel setItalicFont() {
        return SwingUtils.setItalicFont(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebMultiLineLabel setItalicFont(final boolean apply) {
        return SwingUtils.setItalicFont(this, apply);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isItalicFont() {
        return SwingUtils.isItalicFont(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebMultiLineLabel setFontStyle(final boolean bold,
            final boolean italic) {
        return SwingUtils.setFontStyle(this, bold, italic);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebMultiLineLabel setFontStyle(final int style) {
        return SwingUtils.setFontStyle(this, style);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebMultiLineLabel setFontSize(final int fontSize) {
        return SwingUtils.setFontSize(this, fontSize);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebMultiLineLabel changeFontSize(final int change) {
        return SwingUtils.changeFontSize(this, change);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getFontSize() {
        return SwingUtils.getFontSize(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebMultiLineLabel setFontSizeAndStyle(final int fontSize,
            final boolean bold, final boolean italic) {
        return SwingUtils.setFontSizeAndStyle(this, fontSize, bold, italic);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebMultiLineLabel setFontSizeAndStyle(final int fontSize,
            final int style) {
        return SwingUtils.setFontSizeAndStyle(this, fontSize, style);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebMultiLineLabel setFontName(final String fontName) {
        return SwingUtils.setFontName(this, fontName);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getFontName() {
        return SwingUtils.getFontName(this);
    }
}