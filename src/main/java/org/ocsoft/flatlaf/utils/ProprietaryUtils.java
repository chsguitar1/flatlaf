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

package org.ocsoft.flatlaf.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.UIDefaults;

import org.ocsoft.flatlaf.utils.reflection.ReflectUtils;
import org.ocsoft.flatlaf.utils.system.FlatLafLogger;
import org.ocsoft.flatlaf.utils.system.FlatLafSystemUtils;
import org.ocsoft.flatlaf.weblaf.FlatLafStyleConstants;

/**
 * This utility class operates only with proprietary API calls. Their usage is
 * inevitable, otherwise i would have replaced them with something else.
 *
 * @author Mikle Garin
 */

public final class ProprietaryUtils {
    /**
     * Key used to indicate a light weight popup should be used.
     */
    public static final int LIGHT_WEIGHT_POPUP = 0;
    
    /**
     * Key used to indicate a medium weight Popup should be used.
     */
    public static final int MEDIUM_WEIGHT_POPUP = 1;
    
    /*
     * Key used to indicate a heavy weight Popup should be used.
     */
    public static final int HEAVY_WEIGHT_POPUP = 2;
    
    /**
     * Whether or not window transparency is allowed globally or not.
     */
    private static boolean windowTransparencyAllowed = true;
    
    /**
     * Allow per-pixel transparent windows usage on Linux systems. This might be
     * an unstable feature so it is disabled by default.
     */
    private static boolean allowLinuxTransparency = false;
    
    /**
     * Returns whether per-pixel transparent windows usage is allowed on Linux
     * systems or not.
     *
     * @return true if per-pixel transparent windows usage is allowed on Linux
     *         systems, false otherwise
     */
    public static boolean isAllowLinuxTransparency() {
        return allowLinuxTransparency;
    }
    
    /**
     * Sets whether per-pixel transparent windows usage is allowed on Linux
     * systems or not. This might be an unstable feature so it is disabled by
     * default. Use it at your own risk.
     *
     * @param allow
     *            whether per-pixel transparent windows usage is allowed on
     *            Linux systems or not
     */
    public static void setAllowLinuxTransparency(final boolean allow) {
        ProprietaryUtils.allowLinuxTransparency = allow;
    }
    
    /**
     * Installs some proprietary L&amp;F defaults for proper text rendering.
     * Basically this method is a workaround for this simple call:
     * {@code
     * table.put ( sun.swing.SwingUtilities2.AA_TEXT_PROPERTY_KEY, sun.swing.SwingUtilities2.AATextInfo.getAATextInfo ( true ) );
     * } but it doesn't directly use any proprietary API.
     *
     * @param table
     *            defaults table
     */
    public static void setupUIDefaults(final UIDefaults table) {
        try {
            final Class su2 = ReflectUtils
                    .getClass("sun.swing.SwingUtilities2");
            final Object aaProperty = ReflectUtils.getStaticFieldValue(su2,
                    "AA_TEXT_PROPERTY_KEY");
            final Class aaTextInfo = ReflectUtils.getInnerClass(su2,
                    "AATextInfo");
            final Object aaValue = ReflectUtils.callStaticMethod(aaTextInfo,
                    "getAATextInfo", true);
            table.put(aaProperty, aaValue);
        } catch (final ClassNotFoundException e) {
            FlatLafLogger.error(ProprietaryUtils.class, e);
        } catch (final NoSuchFieldException e) {
            FlatLafLogger.error(ProprietaryUtils.class, e);
        } catch (final IllegalAccessException e) {
            FlatLafLogger.error(ProprietaryUtils.class, e);
        } catch (final NoSuchMethodException e) {
            FlatLafLogger.error(ProprietaryUtils.class, e);
        } catch (final InvocationTargetException e) {
            FlatLafLogger.error(ProprietaryUtils.class, e);
        }
    }
    
    /**
     * Returns whether window transparency is allowed globally or not. Whether
     * or not it is allowed depends on the settings and current OS type.
     *
     * @return true if window transparency is allowed globally, false otherwise
     */
    public static boolean isWindowTransparencyAllowed() {
        try {
            if (windowTransparencyAllowed) {
                // Replace when Unix-systems will have proper support for
                // transparency
                // Also on Windows systems fonts of all components on
                // transparent windows is not the same, which becomes a real
                // issue sometimes
                final Class au = ReflectUtils
                        .getClass("com.sun.awt.AWTUtilities");
                final Class t = ReflectUtils.getInnerClass(au, "Translucency");
                final Object ppt = ReflectUtils.getStaticFieldValue(t,
                        "PERPIXEL_TRANSPARENT");
                final Boolean wts = ReflectUtils.callStaticMethod(au,
                        "isWindowTranslucencySupported");
                final Boolean tc = ReflectUtils.callStaticMethod(au,
                        "isTranslucencyCapable",
                        FlatLafSystemUtils.getGraphicsConfiguration());
                final Boolean ppts = ReflectUtils.callStaticMethod(au,
                        "isTranslucencySupported", ppt);
                return wts
                        && tc
                        && ppts
                        && (FlatLafSystemUtils.isWindows()
                                || FlatLafSystemUtils.isMac()
                                || FlatLafSystemUtils.isSolaris() || FlatLafSystemUtils
                                .isUnix() && allowLinuxTransparency);
            } else {
                return false;
            }
        } catch (final Throwable e) {
            return FlatLafSystemUtils.isWindows() || FlatLafSystemUtils.isMac()
                    || FlatLafSystemUtils.isSolaris()
                    || FlatLafSystemUtils.isUnix() && allowLinuxTransparency;
        }
    }
    
    /**
     * Sets whether window transparency is allowed globally or not.
     *
     * @param allowed
     *            whether window transparency is allowed globally or not
     */
    public static void setWindowTransparencyAllowed(final boolean allowed) {
        windowTransparencyAllowed = allowed;
    }
    
    /**
     * Sets window opaque if that option is supported by the underlying system.
     *
     * @param window
     *            window to process
     * @param opaque
     *            whether should make window opaque or not
     */
    public static void setWindowOpaque(final Window window, final boolean opaque) {
        if (window != null && isWindowTransparencyAllowed()) {
            try {
                // Change system window opacity
                if (FlatLafSystemUtils.isJava7orAbove()) {
                    // In Java 7 and later we change window background color to
                    // make it opaque or non-opaque
                    setupOpacityBackgroundColor(opaque, window);
                } else {
                    // In Java 6 we use AWTUtilities method to change window
                    // opacity mode
                    ReflectUtils.callStaticMethod("com.sun.awt.AWTUtilities",
                            "setWindowOpaque", window, opaque);
                }
                
                // Changing opacity of root and content panes
                final JRootPane rootPane = SwingUtils.getRootPane(window);
                if (rootPane != null) {
                    // Changing root pane background color and opacity
                    setupOpacityBackgroundColor(opaque, rootPane);
                    rootPane.setOpaque(opaque);
                    
                    // Changing content pane color and opacity
                    final Container container = rootPane.getContentPane();
                    if (container != null) {
                        setupOpacityBackgroundColor(opaque, container);
                        if (container instanceof JComponent) {
                            ((JComponent) container).setOpaque(opaque);
                        }
                    }
                    
                    // Repaint root pane in case opacity changed
                    // Without this repaint it will not be properly displayed
                    rootPane.repaint();
                }
            } catch (final Throwable e) {
                // Ignore any exceptions this native feature might cause
                // Still, should inform that such actions cause an exception on
                // the underlying system
                FlatLafLogger.error(ProprietaryUtils.class, e);
            }
        }
    }
    
    /**
     * Changes component background color to match opacity.
     *
     * @param opaque
     *            whether component should should be opaque or not
     * @param component
     *            component to process
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    protected static void setupOpacityBackgroundColor(final boolean opaque,
            final Component component) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        final Color bg = opaque ? FlatLafStyleConstants.backgroundColor
                : FlatLafStyleConstants.transparent;
        ReflectUtils.callMethod(component, "setBackground", bg);
    }
    
    /**
     * Returns whether window is opaque or not.
     *
     * @param window
     *            window to process
     * @return whether window background is opaque or not
     */
    public static boolean isWindowOpaque(final Window window) {
        if (window != null && isWindowTransparencyAllowed()) {
            try {
                final Boolean isOpaque;
                if (FlatLafSystemUtils.isJava7orAbove()) {
                    // For Java 7 and later this will work just fine
                    final Color bg = ReflectUtils.callMethod(window,
                            "getBackground");
                    isOpaque = bg.getAlpha() == 255;
                } else {
                    // Workaround to allow this method usage on all possible
                    // Java versions
                    isOpaque = ReflectUtils.callStaticMethod(
                            "com.sun.awt.AWTUtilities", "isWindowOpaque",
                            window);
                }
                return isOpaque != null ? isOpaque : true;
            } catch (final Throwable e) {
                // Ignore any exceptions this native feature might cause
                // Still, should inform that such actions cause an exception on
                // the underlying system
                FlatLafLogger.error(ProprietaryUtils.class, e);
            }
        }
        return true;
    }
    
    /**
     * Sets window opacity if that option is supported by the underlying system.
     *
     * @param window
     *            window to process
     * @param opacity
     *            new window opacity
     */
    public static void setWindowOpacity(final Window window, final float opacity) {
        if (window != null && isWindowTransparencyAllowed()) {
            try {
                if (FlatLafSystemUtils.isJava7orAbove()) {
                    // For Java 7 and later this will work just fine
                    ReflectUtils.callMethod(window, "setOpacity", opacity);
                } else {
                    // Workaround to allow this method usage on all possible
                    // Java versions
                    ReflectUtils.callStaticMethod("com.sun.awt.AWTUtilities",
                            "setWindowOpacity", window, opacity);
                }
            } catch (final Throwable e) {
                // Ignore any exceptions this native feature might cause
                // Still, should inform that such actions cause an exception on
                // the underlying system
                FlatLafLogger.error(ProprietaryUtils.class, e);
            }
        }
    }
    
    /**
     * Returns window opacity.
     *
     * @param window
     *            window to process
     * @return window opacity
     */
    public static float getWindowOpacity(final Window window) {
        if (window != null && isWindowTransparencyAllowed()) {
            try {
                final Float opacity;
                if (FlatLafSystemUtils.isJava7orAbove()) {
                    // For Java 7 and later this will work just fine
                    opacity = ReflectUtils.callMethod(window, "getOpacity");
                } else {
                    // Workaround to allow this method usage on all possible
                    // Java versions
                    opacity = ReflectUtils.callStaticMethod(
                            "com.sun.awt.AWTUtilities", "getWindowOpacity",
                            window);
                }
                return opacity != null ? opacity : 1f;
            } catch (final Throwable e) {
                // Ignore any exceptions this native feature might cause
                // Still, should inform that such actions cause an exception on
                // the underlying system
                FlatLafLogger.error(ProprietaryUtils.class, e);
            }
        }
        return 1f;
    }
    
    /**
     * Returns heavyweight popup instance. By default Swing popups are
     * MEDIUM_WEIGHT_POPUP and there is no convenient way to create popups of
     * other types. Though this can be done by calling similar method on the
     * PopupFactory instance which simply takes the popup type. This method is
     * safe to use and should work on any JDK version.
     *
     * @param invoker
     *            invoker component
     * @param content
     *            popup content
     * @param x
     *            popup initial location X coordinate
     * @param y
     *            popup initial location Y coordinate
     * @return heavyweight popup instance
     */
    public static Popup createHeavyweightPopup(final Component invoker,
            final Component content, final int x, final int y) {
        return ReflectUtils.callMethodSafely(PopupFactory.getSharedInstance(),
                "getPopup", invoker, content, x, y, HEAVY_WEIGHT_POPUP);
    }
}