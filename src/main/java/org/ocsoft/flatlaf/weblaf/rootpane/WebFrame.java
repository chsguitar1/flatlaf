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

package org.ocsoft.flatlaf.weblaf.rootpane;

import java.awt.Color;
import java.awt.Component;
import java.awt.GraphicsConfiguration;
import java.awt.event.WindowAdapter;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;

import org.ocsoft.flatlaf.extended.panel.WebButtonGroup;
import org.ocsoft.flatlaf.laf.rootpane.WebRootPaneUI;
import org.ocsoft.flatlaf.managers.focus.DefaultFocusTracker;
import org.ocsoft.flatlaf.managers.focus.FocusManager;
import org.ocsoft.flatlaf.managers.language.LanguageContainerMethods;
import org.ocsoft.flatlaf.managers.language.LanguageManager;
import org.ocsoft.flatlaf.managers.language.LanguageMethods;
import org.ocsoft.flatlaf.managers.language.LanguageUtils;
import org.ocsoft.flatlaf.managers.language.updaters.LanguageUpdater;
import org.ocsoft.flatlaf.managers.settings.DefaultValue;
import org.ocsoft.flatlaf.managers.settings.SettingsManager;
import org.ocsoft.flatlaf.managers.settings.SettingsMethods;
import org.ocsoft.flatlaf.managers.settings.SettingsProcessor;
import org.ocsoft.flatlaf.utils.EventUtils;
import org.ocsoft.flatlaf.utils.SwingUtils;
import org.ocsoft.flatlaf.utils.WindowUtils;
import org.ocsoft.flatlaf.utils.swing.ComponentEventRunnable;
import org.ocsoft.flatlaf.utils.swing.WindowCloseAdapter;
import org.ocsoft.flatlaf.utils.swing.WindowEventMethods;
import org.ocsoft.flatlaf.utils.swing.WindowEventRunnable;
import org.ocsoft.flatlaf.utils.swing.WindowMethods;

/**
 * This JFrame extenstion class provides some additional methods and options to
 * manipulate frame behavior.
 *
 * @author Mikle Garin
 */

public class WebFrame extends JFrame implements WindowEventMethods,
        LanguageMethods, LanguageContainerMethods, SettingsMethods,
        WindowMethods<WebFrame> {
    /**
     * Whether should close frame on focus loss or not.
     */
    protected boolean closeOnFocusLoss = false;
    
    /**
     * Window focus tracker.
     */
    protected DefaultFocusTracker focusTracker;
    
    public WebFrame() {
        super();
        initialize();
    }
    
    public WebFrame(final GraphicsConfiguration gc) {
        super(gc);
        initialize();
    }
    
    public WebFrame(final String title) {
        super(LanguageUtils.getInitialText(title));
        LanguageUtils.registerInitialLanguage(this, title);
        initialize();
    }
    
    public WebFrame(final String title, final GraphicsConfiguration gc) {
        super(LanguageUtils.getInitialText(title), gc);
        LanguageUtils.registerInitialLanguage(this, title);
        initialize();
    }
    
    /**
     * Additional initializtion of WebFrame settings.
     */
    protected void initialize() {
        SwingUtils.setOrientation(this);
        
        // Adding focus tracker for this frame
        // It is stored into a separate field to avoid its disposal from memory
        focusTracker = new DefaultFocusTracker(true) {
            @Override
            public boolean isTrackingEnabled() {
                return closeOnFocusLoss;
            }
            
            @Override
            public void focusChanged(final boolean focused) {
                if (closeOnFocusLoss && WebFrame.this.isShowing() && !focused) {
                    setVisible(false);
                }
            }
        };
        FocusManager.addFocusTracker(this, focusTracker);
    }
    
    /**
     * Returns whether should close frame on focus loss or not.
     *
     * @return true if should close frame on focus loss, false otherwise
     */
    public boolean isCloseOnFocusLoss() {
        return closeOnFocusLoss;
    }
    
    /**
     * Sets whether should close frame on focus loss or not.
     *
     * @param closeOnFocusLoss
     *            whether should close frame on focus loss or not
     */
    public void setCloseOnFocusLoss(final boolean closeOnFocusLoss) {
        this.closeOnFocusLoss = closeOnFocusLoss;
    }
    
    /**
     * Returns focusable childs that don't force frame to close even if it set
     * to close on focus loss.
     *
     * @return focusable childs that don't force frame to close even if it set
     *         to close on focus loss
     */
    public List<Component> getFocusableChilds() {
        return focusTracker.getCustomChildren();
    }
    
    /**
     * Adds focusable child that won't force frame to close even if it set to
     * close on focus loss.
     *
     * @param child
     *            focusable child that won't force frame to close even if it set
     *            to close on focus loss
     */
    public void addFocusableChild(final Component child) {
        focusTracker.addCustomChild(child);
    }
    
    /**
     * Removes focusable child that doesn't force frame to close even if it set
     * to close on focus loss.
     *
     * @param child
     *            focusable child that doesn't force frame to close even if it
     *            set to close on focus loss
     */
    public void removeFocusableChild(final Component child) {
        focusTracker.removeCustomChild(child);
    }
    
    public Color getTopBg() {
        return getWebRootPaneUI().getTopBg();
    }
    
    public void setTopBg(final Color topBg) {
        getWebRootPaneUI().setTopBg(topBg);
    }
    
    public Color getMiddleBg() {
        return getWebRootPaneUI().getMiddleBg();
    }
    
    public void setMiddleBg(final Color middleBg) {
        getWebRootPaneUI().setMiddleBg(middleBg);
    }
    
    public int getShadeWidth() {
        return getWebRootPaneUI().getShadeWidth();
    }
    
    public void setShadeWidth(final int shadeWidth) {
        getWebRootPaneUI().setShadeWidth(shadeWidth);
    }
    
    public int getInactiveShadeWidth() {
        return getWebRootPaneUI().getInactiveShadeWidth();
    }
    
    public void setInactiveShadeWidth(final int inactiveShadeWidth) {
        getWebRootPaneUI().setInactiveShadeWidth(inactiveShadeWidth);
    }
    
    public int getRound() {
        return getWebRootPaneUI().getRound();
    }
    
    public void setRound(final int round) {
        getWebRootPaneUI().setRound(round);
    }
    
    public boolean isDrawWatermark() {
        return getWebRootPaneUI().isDrawWatermark();
    }
    
    public void setDrawWatermark(final boolean drawWatermark) {
        getWebRootPaneUI().setDrawWatermark(drawWatermark);
    }
    
    public ImageIcon getWatermark() {
        return getWebRootPaneUI().getWatermark();
    }
    
    public void setWatermark(final ImageIcon watermark) {
        getWebRootPaneUI().setWatermark(watermark);
    }
    
    public int getMaxTitleWidth() {
        return getWebRootPaneUI().getMaxTitleWidth();
    }
    
    public void setMaxTitleWidth(final int width) {
        getWebRootPaneUI().setMaxTitleWidth(width);
    }
    
    public String getEmptyTitleText() {
        return getWebRootPaneUI().getEmptyTitleText();
    }
    
    public void setEmptyTitleText(final String text) {
        getWebRootPaneUI().setEmptyTitleText(text);
    }
    
    public JComponent getTitleComponent() {
        return getWebRootPaneUI().getTitleComponent();
    }
    
    public void setTitleComponent(final JComponent titleComponent) {
        getWebRootPaneUI().setTitleComponent(titleComponent);
    }
    
    public WebButtonGroup getWindowButtons() {
        return getWebRootPaneUI().getWindowButtons();
    }
    
    public WebResizeCorner getResizeCorner() {
        return getWebRootPaneUI().getResizeCorner();
    }
    
    public boolean isShowResizeCorner() {
        return getWebRootPaneUI().isShowResizeCorner();
    }
    
    public void setShowResizeCorner(final boolean showResizeCorner) {
        getWebRootPaneUI().setShowResizeCorner(showResizeCorner);
    }
    
    public boolean isShowTitleComponent() {
        return getWebRootPaneUI().isShowTitleComponent();
    }
    
    public void setShowTitleComponent(final boolean showTitleComponent) {
        getWebRootPaneUI().setShowTitleComponent(showTitleComponent);
    }
    
    public boolean isShowWindowButtons() {
        return getWebRootPaneUI().isShowWindowButtons();
    }
    
    public void setShowWindowButtons(final boolean showWindowButtons) {
        getWebRootPaneUI().setShowWindowButtons(showWindowButtons);
    }
    
    public boolean isShowMinimizeButton() {
        return getWebRootPaneUI().isShowMinimizeButton();
    }
    
    public void setShowMinimizeButton(final boolean showMinimizeButton) {
        getWebRootPaneUI().setShowMinimizeButton(showMinimizeButton);
    }
    
    public boolean isShowMaximizeButton() {
        return getWebRootPaneUI().isShowMaximizeButton();
    }
    
    public void setShowMaximizeButton(final boolean showMaximizeButton) {
        getWebRootPaneUI().setShowMaximizeButton(showMaximizeButton);
    }
    
    public boolean isShowCloseButton() {
        return getWebRootPaneUI().isShowCloseButton();
    }
    
    public void setShowCloseButton(final boolean showCloseButton) {
        getWebRootPaneUI().setShowCloseButton(showCloseButton);
    }
    
    public boolean isGroupButtons() {
        return getWebRootPaneUI().isGroupButtons();
    }
    
    public void setGroupButtons(final boolean groupButtons) {
        getWebRootPaneUI().setGroupButtons(groupButtons);
    }
    
    public boolean isAttachButtons() {
        return getWebRootPaneUI().isAttachButtons();
    }
    
    public void setAttachButtons(final boolean attachButtons) {
        getWebRootPaneUI().setAttachButtons(attachButtons);
    }
    
    public boolean isShowMenuBar() {
        return getWebRootPaneUI().isShowMenuBar();
    }
    
    public void setShowMenuBar(final boolean showMenuBar) {
        getWebRootPaneUI().setShowMenuBar(showMenuBar);
    }
    
    public WebRootPaneUI getWebRootPaneUI() {
        return (WebRootPaneUI) super.getRootPane().getUI();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WindowAdapter onClosing(final WindowEventRunnable runnable) {
        return EventUtils.onClosing(this, runnable);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WindowCloseAdapter onClose(final ComponentEventRunnable runnable) {
        return EventUtils.onClose(this, runnable);
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
     * Language container methods
     */
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setLanguageContainerKey(final String key) {
        LanguageManager.registerLanguageContainer(this, key);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void removeLanguageContainerKey() {
        LanguageManager.unregisterLanguageContainer(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getLanguageContainerKey() {
        return LanguageManager.getLanguageContainerKey(this);
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
     * Window methods.
     */
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebFrame setWindowOpaque(final boolean opaque) {
        return WindowUtils.setWindowOpaque(this, opaque);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWindowOpaque() {
        return WindowUtils.isWindowOpaque(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebFrame setWindowOpacity(final float opacity) {
        return WindowUtils.setWindowOpacity(this, opacity);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public float getWindowOpacity() {
        return WindowUtils.getWindowOpacity(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebFrame center() {
        return WindowUtils.center(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebFrame center(final Component relativeTo) {
        return WindowUtils.center(this, relativeTo);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebFrame center(final int width, final int height) {
        return WindowUtils.center(this, width, height);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebFrame center(final Component relativeTo, final int width,
            final int height) {
        return WindowUtils.center(this, relativeTo, width, height);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebFrame packToWidth(final int width) {
        return WindowUtils.packToWidth(this, width);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebFrame packToHeight(final int height) {
        return WindowUtils.packToHeight(this, height);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebFrame packAndCenter() {
        return WindowUtils.packAndCenter(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebFrame packAndCenter(final boolean animate) {
        return WindowUtils.packAndCenter(this, animate);
    }
}