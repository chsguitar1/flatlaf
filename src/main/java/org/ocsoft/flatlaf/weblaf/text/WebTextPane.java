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

package org.ocsoft.flatlaf.weblaf.text;

import java.awt.event.FocusAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.beans.PropertyChangeListener;

import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;

import org.ocsoft.flatlaf.core.FlatLafSettings;
import org.ocsoft.flatlaf.laf.text.WebTextPaneUI;
import org.ocsoft.flatlaf.managers.hotkey.HotkeyData;
import org.ocsoft.flatlaf.managers.language.LanguageManager;
import org.ocsoft.flatlaf.managers.language.LanguageMethods;
import org.ocsoft.flatlaf.managers.language.updaters.LanguageUpdater;
import org.ocsoft.flatlaf.managers.settings.DefaultValue;
import org.ocsoft.flatlaf.managers.settings.SettingsManager;
import org.ocsoft.flatlaf.managers.settings.SettingsMethods;
import org.ocsoft.flatlaf.managers.settings.SettingsProcessor;
import org.ocsoft.flatlaf.utils.EventUtils;
import org.ocsoft.flatlaf.utils.SwingUtils;
import org.ocsoft.flatlaf.utils.general.Pair;
import org.ocsoft.flatlaf.utils.reflection.ReflectUtils;
import org.ocsoft.flatlaf.utils.swing.DocumentChangeListener;
import org.ocsoft.flatlaf.utils.swing.DocumentEventMethods;
import org.ocsoft.flatlaf.utils.swing.DocumentEventRunnable;
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

public class WebTextPane extends JTextPane implements DocumentEventMethods,
        EventMethods, LanguageMethods, SettingsMethods,
        FontMethods<WebTextPane> {
    public WebTextPane() {
        super();
    }
    
    public WebTextPane(final StyledDocument doc) {
        super(doc);
    }
    
    /**
     * Additional component methods
     */
    
    public void clear() {
        setText("");
    }
    
    /**
     * UI methods
     */
    
    public WebTextPaneUI getWebUI() {
        return (WebTextPaneUI) getUI();
    }
    
    @Override
    public void updateUI() {
        if (getUI() == null || !(getUI() instanceof WebTextPaneUI)) {
            try {
                setUI((WebTextPaneUI) ReflectUtils
                        .createInstance(FlatLafSettings.textPaneUI));
            } catch (final Throwable e) {
                FlatLafLogger.error(this, e);
                setUI(new WebTextPaneUI());
            }
        } else {
            setUI(getUI());
        }
        invalidate();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<DocumentChangeListener, PropertyChangeListener> onChange(
            final DocumentEventRunnable runnable) {
        return EventUtils.onChange(this, runnable);
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
     * Language methods
     */
    
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
     * Settings methods
     */
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void registerSettings(final String key) {
        SettingsManager.registerComponent(this, key);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends DefaultValue> void registerSettings(final String key,
            final Class<T> defaultValueClass) {
        SettingsManager.registerComponent(this, key, defaultValueClass);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void registerSettings(final String key, final Object defaultValue) {
        SettingsManager.registerComponent(this, key, defaultValue);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void registerSettings(final String group, final String key) {
        SettingsManager.registerComponent(this, group, key);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends DefaultValue> void registerSettings(final String group,
            final String key, final Class<T> defaultValueClass) {
        SettingsManager.registerComponent(this, group, key, defaultValueClass);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void registerSettings(final String group, final String key,
            final Object defaultValue) {
        SettingsManager.registerComponent(this, group, key, defaultValue);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void registerSettings(final String key,
            final boolean loadInitialSettings,
            final boolean applySettingsChanges) {
        SettingsManager.registerComponent(this, key, loadInitialSettings,
                applySettingsChanges);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends DefaultValue> void registerSettings(final String key,
            final Class<T> defaultValueClass,
            final boolean loadInitialSettings,
            final boolean applySettingsChanges) {
        SettingsManager.registerComponent(this, key, defaultValueClass,
                loadInitialSettings, applySettingsChanges);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void registerSettings(final String key, final Object defaultValue,
            final boolean loadInitialSettings,
            final boolean applySettingsChanges) {
        SettingsManager.registerComponent(this, key, defaultValue,
                loadInitialSettings, applySettingsChanges);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends DefaultValue> void registerSettings(final String group,
            final String key, final Class<T> defaultValueClass,
            final boolean loadInitialSettings,
            final boolean applySettingsChanges) {
        SettingsManager.registerComponent(this, group, key, defaultValueClass,
                loadInitialSettings, applySettingsChanges);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void registerSettings(final String group, final String key,
            final Object defaultValue, final boolean loadInitialSettings,
            final boolean applySettingsChanges) {
        SettingsManager.registerComponent(this, group, key, defaultValue,
                loadInitialSettings, applySettingsChanges);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void registerSettings(final SettingsProcessor settingsProcessor) {
        SettingsManager.registerComponent(this, settingsProcessor);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void unregisterSettings() {
        SettingsManager.unregisterComponent(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void loadSettings() {
        SettingsManager.loadComponentSettings(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void saveSettings() {
        SettingsManager.saveComponentSettings(this);
    }
    
    /**
     * Font methods
     */
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebTextPane setPlainFont() {
        return SwingUtils.setPlainFont(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebTextPane setPlainFont(final boolean apply) {
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
    public WebTextPane setBoldFont() {
        return SwingUtils.setBoldFont(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebTextPane setBoldFont(final boolean apply) {
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
    public WebTextPane setItalicFont() {
        return SwingUtils.setItalicFont(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebTextPane setItalicFont(final boolean apply) {
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
    public WebTextPane setFontStyle(final boolean bold, final boolean italic) {
        return SwingUtils.setFontStyle(this, bold, italic);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebTextPane setFontStyle(final int style) {
        return SwingUtils.setFontStyle(this, style);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebTextPane setFontSize(final int fontSize) {
        return SwingUtils.setFontSize(this, fontSize);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebTextPane changeFontSize(final int change) {
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
    public WebTextPane setFontSizeAndStyle(final int fontSize,
            final boolean bold, final boolean italic) {
        return SwingUtils.setFontSizeAndStyle(this, fontSize, bold, italic);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebTextPane setFontSizeAndStyle(final int fontSize, final int style) {
        return SwingUtils.setFontSizeAndStyle(this, fontSize, style);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebTextPane setFontName(final String fontName) {
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