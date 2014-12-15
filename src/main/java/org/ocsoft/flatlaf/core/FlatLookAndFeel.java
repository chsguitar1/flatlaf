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

package org.ocsoft.flatlaf.core;

import javax.swing.*;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.basic.BasicLookAndFeel;
import javax.swing.text.DefaultEditorKit;

import org.ocsoft.flatlaf.extended.button.WebSplitButtonUI;
import org.ocsoft.flatlaf.extended.checkbox.WebTristateCheckBoxUI;
import org.ocsoft.flatlaf.extended.colorchooser.GradientColorData;
import org.ocsoft.flatlaf.extended.colorchooser.GradientData;
import org.ocsoft.flatlaf.extended.label.WebMultiLineLabelUI;
import org.ocsoft.flatlaf.extended.label.WebStyledLabelUI;
import org.ocsoft.flatlaf.extended.label.WebVerticalLabelUI;
import org.ocsoft.flatlaf.extended.tab.DocumentPaneState;
import org.ocsoft.flatlaf.laf.AltProcessor;
import org.ocsoft.flatlaf.laf.FlatLafStyleConstants;
import org.ocsoft.flatlaf.laf.WebLayoutStyle;
import org.ocsoft.flatlaf.laf.button.WebButtonUI;
import org.ocsoft.flatlaf.laf.button.WebToggleButtonUI;
import org.ocsoft.flatlaf.laf.checkbox.WebCheckBoxUI;
import org.ocsoft.flatlaf.laf.colorchooser.HSBColor;
import org.ocsoft.flatlaf.laf.colorchooser.WebColorChooserUI;
import org.ocsoft.flatlaf.laf.combobox.WebComboBoxUI;
import org.ocsoft.flatlaf.laf.desktoppane.WebDesktopIconUI;
import org.ocsoft.flatlaf.laf.desktoppane.WebDesktopPaneUI;
import org.ocsoft.flatlaf.laf.desktoppane.WebInternalFrameUI;
import org.ocsoft.flatlaf.laf.filechooser.WebFileChooserUI;
import org.ocsoft.flatlaf.laf.label.WebLabelUI;
import org.ocsoft.flatlaf.laf.list.WebListCellRenderer;
import org.ocsoft.flatlaf.laf.list.WebListStyle;
import org.ocsoft.flatlaf.laf.list.WebListUI;
import org.ocsoft.flatlaf.laf.menu.*;
import org.ocsoft.flatlaf.laf.optionpane.WebOptionPaneUI;
import org.ocsoft.flatlaf.laf.panel.WebPanelUI;
import org.ocsoft.flatlaf.laf.progressbar.WebProgressBarUI;
import org.ocsoft.flatlaf.laf.radiobutton.WebRadioButtonUI;
import org.ocsoft.flatlaf.laf.rootpane.WebRootPaneUI;
import org.ocsoft.flatlaf.laf.scroll.WebScrollBarStyle;
import org.ocsoft.flatlaf.laf.scroll.WebScrollBarUI;
import org.ocsoft.flatlaf.laf.scroll.WebScrollPaneUI;
import org.ocsoft.flatlaf.laf.separator.WebSeparatorUI;
import org.ocsoft.flatlaf.laf.slider.WebSliderUI;
import org.ocsoft.flatlaf.laf.spinner.WebSpinnerUI;
import org.ocsoft.flatlaf.laf.splitpane.WebSplitPaneUI;
import org.ocsoft.flatlaf.laf.tabbedpane.WebTabbedPaneUI;
import org.ocsoft.flatlaf.laf.table.WebTableHeaderUI;
import org.ocsoft.flatlaf.laf.table.WebTableStyle;
import org.ocsoft.flatlaf.laf.table.WebTableUI;
import org.ocsoft.flatlaf.laf.text.*;
import org.ocsoft.flatlaf.laf.toolbar.WebToolBarSeparatorUI;
import org.ocsoft.flatlaf.laf.toolbar.WebToolBarUI;
import org.ocsoft.flatlaf.laf.tooltip.WebToolTipUI;
import org.ocsoft.flatlaf.laf.tree.NodeState;
import org.ocsoft.flatlaf.laf.tree.TreeState;
import org.ocsoft.flatlaf.laf.tree.WebTreeUI;
import org.ocsoft.flatlaf.laf.viewport.WebViewportStyle;
import org.ocsoft.flatlaf.laf.viewport.WebViewportUI;
import org.ocsoft.flatlaf.managers.FlatLafManagers;
import org.ocsoft.flatlaf.utils.*;
import org.ocsoft.flatlaf.utils.collection.CollectionUtils;
import org.ocsoft.flatlaf.utils.swing.SwingLazyValue;
import org.ocsoft.flatlaf.utils.system.FlatLafSystemUtils;
import org.ocsoft.flatlaf.utils.xml.XmlUtils;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * This core class contains methods to install, configure and uninstall WebLookAndFeel.
 *
 * @author Mikle Garin
 * @see <a href="https://github.com/mgarin/weblaf/wiki/How-to-use-WebLaF">How to use WebLaF</a>
 * @see <a href="https://github.com/mgarin/weblaf/wiki/How-to-build-WebLaF-from-sources">How to build WebLaF from sources</a>
 */

public class FlatLookAndFeel extends BasicLookAndFeel
{
    /**
     * todo 1. Install default UI classes automatically (not manually) using SupportedComponent enumeration
     */

    /**
     * If this client property is set to {@link Boolean#TRUE} on a component, UI delegates should follow the typical Swing behavior of not
     * overriding a user-defined border on it.
     */
    public static final String PROPERTY_HONOR_USER_BORDER = "WebLookAndFeel.honorUserBorder";

    /**
     * If this system property is set to {@code true}, UI delegates should follow the typical Swing behavior of not overriding a
     * user-defined border if one is installed on components.
     */
    public static final String PROPERTY_HONOR_USER_BORDERS = "WebLookAndFeel.honorUserBorders";

    /**
     * Some known UI constants.
     */
    public static final String LOOK_AND_FEEL_PROPERTY = "lookAndFeel";
    public static final String ORIENTATION_PROPERTY = "componentOrientation";
    public static final String MARGIN_PROPERTY = "margin";
    public static final String ENABLED_PROPERTY = "enabled";
    public static final String MODEL_PROPERTY = "model";
    public static final String TOOLBAR_FLOATABLE_PROPERTY = "floatable";
    public static final String WINDOW_DECORATION_STYLE_PROPERTY = "windowDecorationStyle";
    public static final String WINDOW_RESIZABLE_PROPERTY = "resizable";
    public static final String WINDOW_ICON_PROPERTY = "iconImage";
    public static final String WINDOW_TITLE_PROPERTY = "title";
    public static final String VISIBLE_PROPERTY = "visible";
    public static final String DOCUMENT_PROPERTY = "document";
    public static final String OPAQUE_PROPERTY = "opaque";
    public static final String PAINTER_PROPERTY = "painter";
    public static final String RENDERER_PROPERTY = "renderer";
    public static final String DROP_LOCATION = "dropLocation";
    
    /**
     * List of WebLookAndFeel icons.
     */
    private static List<ImageIcon> icons = null;

    /**
     * Disabled icons cache.
     */
    private static final Map<Icon, ImageIcon> disabledIcons = new WeakHashMap<Icon, ImageIcon> ( 50 );

    /**
     * Alt hotkey processor for application windows with menu.
     */
    public static final AltProcessor altProcessor = new AltProcessor ();

    /**
     * Whether to hide component mnemonics by default or not.
     */
    private static boolean isMnemonicHidden = true;

    /**
     * Whether all frames should be decorated using WebLaF styling by default or not.
     */
    private static boolean decorateFrames = false;

    /**
     * Whether all dialogs should be decorated using WebLaF styling by default or not.
     */
    private static boolean decorateDialogs = false;

    /**
     * Default scroll mode used by JViewportUI to handle scrolling repaints.
     * It is different in WebLaF by default due to issues in other scroll mode on some OS.
     * <p/>
     * Some information about scroll modes:
     * BLIT_SCROLL_MODE - handles all cases pretty well, but has some issues on Win 8 earlier versions
     * BACKINGSTORE_SCROLL_MODE - doesn't allow viewport transparency
     * SIMPLE_SCROLL_MODE - pretty slow since it fully repaints scrolled area with each move
     */
    private static int scrollMode = JViewport.BLIT_SCROLL_MODE;

    /**
     * Globally applied orientation.
     * LanguageManager controls this property because usually it is the language that affects default component orientation.
     *
     * @see #getOrientation()
     * @see #setOrientation(java.awt.ComponentOrientation)
     * @see #setOrientation(boolean)
     */
    private static ComponentOrientation orientation;
    
    
    @Override
    public String getName() {
        return "FlatLookAndFeel";
    }
    
    @Override
    public String getID() {
        return "FlatLookAndFeel";
    }
    
    @Override
    public String getDescription() {
        return "Cross-platform flat Look and Feel";
    }
    
    /**
     * Always returns false since FlatLookAndFeel is not native for any platform.
     * @return false
     */
    @Override
    public boolean isNativeLookAndFeel() {
        return false;
    }
    
    /**
     * Always returns true since FlatLookAndFeel supports 
     * any platform which can run Java applications.
     * @return true
     */
    @Override
    public boolean isSupportedLookAndFeel() {
        return true;
    }
    
    @Override
    public boolean getSupportsWindowDecorations() {
        return true;
    }
    
    @Override
    protected void initClassDefaults(UIDefaults table) {
        FlatLafInitializer.initClassDefaults(table);
    }
    
    /**
     * Adds some default colors to the {@code UIDefaults} 
     * that are not used by WebLookAndFeel directly, but will help custom
     * components that assume BasicLookAndFeel conventions.
     *
     * @param table UIDefaults table
     */
    @Override
    protected void initSystemColorDefaults (UIDefaults table) {
        super.initSystemColorDefaults ( table );
        
        String[] defaultSystemColors = FlatLafInitializer.createSystemColorDefaults(table);
        
        loadSystemColors(table, defaultSystemColors, isNativeLookAndFeel());
    }

    /**
     * Initializes WebLookAndFeel defaults (like default renderers, component borders and such).
     * This method will be called only in case WebLookAndFeel is installed through UIManager as current application LookAndFeel.
     *
     * @param table UI defaults table
     */
    @Override
    protected void initComponentDefaults ( final UIDefaults table )
    {
        super.initComponentDefaults ( table );

        // Global text antialiasing
        ProprietaryUtils.setupUIDefaults ( table );

        // Fonts
        initializeFonts ( table );

        // Mnemonics
        table.put ( "Button.showMnemonics", Boolean.TRUE );

        // Whether focused button should become default in frame or not
        table.put ( "Button.defaultButtonFollowsFocus", Boolean.FALSE );

        // JLabels
        final Color controlText = table.getColor ( "controlText" );
        table.put ( "Label.foreground", controlText );
        table.put ( "Label.disabledForeground", FlatLafStyleConstants.disabledTextColor );

        // JTextFields
        final Object textComponentBorder =
                new SwingLazyValue ( "javax.swing.plaf.BorderUIResource.LineBorderUIResource", new Object[]{ FlatLafStyleConstants.shadeColor } );
        table.put ( "TextField.border", textComponentBorder );

        // JTextAreas
        table.put ( "TextArea.border", textComponentBorder );

        // JEditorPanes
        table.put ( "EditorPane.border", textComponentBorder );

        // JTextPanes
        table.put ( "TextPane.border", textComponentBorder );

        // Option pane
        table.put ( "OptionPane.messageAreaBorder",
                new SwingLazyValue ( "javax.swing.plaf.BorderUIResource$EmptyBorderUIResource", new Object[]{ 0, 0, 5, 0 } ) );
        table.put ( "OptionPane.isYesLast", FlatLafSystemUtils.isMac () ? Boolean.TRUE : Boolean.FALSE );

        // HTML image icons
        table.put ( "html.pendingImage", FlatLafStyleConstants.htmlPendingIcon );
        table.put ( "html.missingImage", FlatLafStyleConstants.htmlMissingIcon );

        // Scroll bars minimum size
        table.put ( "ScrollBar.minimumThumbSize", new Dimension ( WebScrollBarStyle.minThumbWidth, WebScrollBarStyle.minThumbHeight ) );
        table.put ( "ScrollBar.width", new Integer ( 10 ) );

        // Tree icons
        table.put ( "Tree.openIcon", WebTreeUI.OPEN_ICON );
        table.put ( "Tree.closedIcon", WebTreeUI.CLOSED_ICON );
        table.put ( "Tree.leafIcon", WebTreeUI.LEAF_ICON );
        table.put ( "Tree.collapsedIcon", WebTreeUI.EXPAND_ICON );
        table.put ( "Tree.expandedIcon", WebTreeUI.COLLAPSE_ICON );
        // Tree default selection style
        table.put ( "Tree.textForeground", Color.BLACK );
        table.put ( "Tree.textBackground", FlatLafStyleConstants.transparent );
        table.put ( "Tree.selectionForeground", Color.BLACK );
        table.put ( "Tree.selectionBackground", FlatLafStyleConstants.transparent );
        table.put ( "Tree.selectionBorderColor", FlatLafStyleConstants.transparent );
        table.put ( "Tree.dropCellBackground", FlatLafStyleConstants.transparent );
        // Tree default renderer content margins
        table.put ( "Tree.rendererMargins", new InsetsUIResource ( 4, 4, 4, 6 ) );
        table.put ( "Tree.rendererFillBackground", Boolean.FALSE );
        table.put ( "Tree.drawsFocusBorderAroundIcon", Boolean.FALSE );
        table.put ( "Tree.drawDashedFocusIndicator", Boolean.FALSE );
        // Tree lines indent
        table.put ( "Tree.leftChildIndent", new Integer ( 12 ) );
        table.put ( "Tree.rightChildIndent", new Integer ( 12 ) );
        table.put ( "Tree.lineTypeDashed", Boolean.TRUE );

        // JMenu expand spacing
        // Up-down menu expand
        table.put ( "Menu.menuPopupOffsetX", new Integer ( 0 ) );
        table.put ( "Menu.menuPopupOffsetY", new Integer ( 0 ) );
        // Left-right menu expand
        table.put ( "Menu.submenuPopupOffsetX", new Integer ( 0 ) );
        table.put ( "Menu.submenuPopupOffsetY", new Integer ( 0 ) );

        // JViewport
        table.put ( "Viewport.background", WebViewportStyle.background );

        // Table defaults
        table.put ( "Table.cellNoFocusBorder", LafUtils.createWebBorder ( 1, 1, 1, 1 ) );
        table.put ( "Table.focusSelectedCellHighlightBorder", LafUtils.createWebBorder ( 1, 1, 1, 1 ) );
        table.put ( "Table.focusCellHighlightBorder", LafUtils.createWebBorder ( 1, 1, 1, 1 ) );
        table.put ( "Table.foreground", WebTableStyle.foreground );
        table.put ( "Table.background", WebTableStyle.background );
        table.put ( "Table.selectionForeground", WebTableStyle.selectionForeground );
        table.put ( "Table.selectionBackground", WebTableStyle.selectionBackground );
        table.put ( "Table.scrollPaneBorder", null );
        // Table header defaults
        table.put ( "TableHeader.cellBorder", LafUtils.createWebBorder ( WebTableStyle.headerMargin ) );
        table.put ( "TableHeader.focusCellBorder", LafUtils.createWebBorder ( WebTableStyle.headerMargin ) );

        // Default list renderer
        table.put ( "List.cellRenderer", new UIDefaults.ActiveValue ()
        {
            @Override
            public Object createValue ( final UIDefaults table )
            {
                return new WebListCellRenderer.UIResource ();
            }
        } );
        // List selection foreground
        table.put ( "List.selectionForeground", WebListStyle.foreground );

        // Combobox selection foregrounds
        table.put ( "ComboBox.selectionForeground", Color.BLACK );
        // Combobox non-square arrow
        table.put ( "ComboBox.squareButton", false );
        // Combobox empty padding
        table.put ( "ComboBox.padding", new InsetsUIResource ( 0, 0, 0, 0 ) );

        // Default components borders
        table.put ( "ProgressBar.border", new SwingLazyValue ( "org.ocsoft.flatlaf.laf.WebBorders", "getProgressBarBorder" ) );
        table.put ( "Button.border", new SwingLazyValue ( "org.ocsoft.flatlaf.laf.WebBorders", "getButtonBorder" ) );

        // WebTextField actions
        table.put ( "TextField.focusInputMap", new UIDefaults.LazyInputMap (
                new Object[]{ "control C", DefaultEditorKit.copyAction, "control V", DefaultEditorKit.pasteAction, "control X",
                        DefaultEditorKit.cutAction, "COPY", DefaultEditorKit.copyAction, "PASTE", DefaultEditorKit.pasteAction, "CUT",
                        DefaultEditorKit.cutAction, "control INSERT", DefaultEditorKit.copyAction, "shift INSERT",
                        DefaultEditorKit.pasteAction, "shift DELETE", DefaultEditorKit.cutAction, "control A",
                        DefaultEditorKit.selectAllAction, "control BACK_SLASH", "unselect"
                        /*DefaultEditorKit.unselectAction*/, "shift LEFT", DefaultEditorKit.selectionBackwardAction, "shift RIGHT",
                        DefaultEditorKit.selectionForwardAction, "control LEFT", DefaultEditorKit.previousWordAction, "control RIGHT",
                        DefaultEditorKit.nextWordAction, "control shift LEFT", DefaultEditorKit.selectionPreviousWordAction,
                        "control shift RIGHT", DefaultEditorKit.selectionNextWordAction, "HOME", DefaultEditorKit.beginLineAction, "END",
                        DefaultEditorKit.endLineAction, "shift HOME", DefaultEditorKit.selectionBeginLineAction, "shift END",
                        DefaultEditorKit.selectionEndLineAction, "BACK_SPACE", DefaultEditorKit.deletePrevCharAction, "shift BACK_SPACE",
                        DefaultEditorKit.deletePrevCharAction, "ctrl H", DefaultEditorKit.deletePrevCharAction, "DELETE",
                        DefaultEditorKit.deleteNextCharAction, "ctrl DELETE", DefaultEditorKit.deleteNextWordAction, "ctrl BACK_SPACE",
                        DefaultEditorKit.deletePrevWordAction, "RIGHT", DefaultEditorKit.forwardAction, "LEFT",
                        DefaultEditorKit.backwardAction, "KP_RIGHT", DefaultEditorKit.forwardAction, "KP_LEFT",
                        DefaultEditorKit.backwardAction, "ENTER", JTextField.notifyAction, "control shift O", "toggle-componentOrientation"
                        /*DefaultEditorKit.toggleComponentOrientation*/ } ) );

        // WebPasswordField actions
        table.put ( "PasswordField.focusInputMap", new UIDefaults.LazyInputMap (
                new Object[]{ "control C", DefaultEditorKit.copyAction, "control V", DefaultEditorKit.pasteAction, "control X",
                        DefaultEditorKit.cutAction, "COPY", DefaultEditorKit.copyAction, "PASTE", DefaultEditorKit.pasteAction, "CUT",
                        DefaultEditorKit.cutAction, "control INSERT", DefaultEditorKit.copyAction, "shift INSERT",
                        DefaultEditorKit.pasteAction, "shift DELETE", DefaultEditorKit.cutAction, "control A",
                        DefaultEditorKit.selectAllAction, "control BACK_SLASH", "unselect"
                        /*DefaultEditorKit.unselectAction*/, "shift LEFT", DefaultEditorKit.selectionBackwardAction, "shift RIGHT",
                        DefaultEditorKit.selectionForwardAction, "control LEFT", DefaultEditorKit.beginLineAction, "control RIGHT",
                        DefaultEditorKit.endLineAction, "control shift LEFT", DefaultEditorKit.selectionBeginLineAction,
                        "control shift RIGHT", DefaultEditorKit.selectionEndLineAction, "HOME", DefaultEditorKit.beginLineAction, "END",
                        DefaultEditorKit.endLineAction, "shift HOME", DefaultEditorKit.selectionBeginLineAction, "shift END",
                        DefaultEditorKit.selectionEndLineAction, "BACK_SPACE", DefaultEditorKit.deletePrevCharAction, "shift BACK_SPACE",
                        DefaultEditorKit.deletePrevCharAction, "ctrl H", DefaultEditorKit.deletePrevCharAction, "DELETE",
                        DefaultEditorKit.deleteNextCharAction, "RIGHT", DefaultEditorKit.forwardAction, "LEFT",
                        DefaultEditorKit.backwardAction, "KP_RIGHT", DefaultEditorKit.forwardAction, "KP_LEFT",
                        DefaultEditorKit.backwardAction, "ENTER", JTextField.notifyAction, "control shift O", "toggle-componentOrientation"
                        /*DefaultEditorKit.toggleComponentOrientation*/ } ) );

        // WebFormattedTextField actions
        table.put ( "FormattedTextField.focusInputMap", new UIDefaults.LazyInputMap (
                new Object[]{ "ctrl C", DefaultEditorKit.copyAction, "ctrl V", DefaultEditorKit.pasteAction, "ctrl X",
                        DefaultEditorKit.cutAction, "COPY", DefaultEditorKit.copyAction, "PASTE", DefaultEditorKit.pasteAction, "CUT",
                        DefaultEditorKit.cutAction, "control INSERT", DefaultEditorKit.copyAction, "shift INSERT",
                        DefaultEditorKit.pasteAction, "shift DELETE", DefaultEditorKit.cutAction, "shift LEFT",
                        DefaultEditorKit.selectionBackwardAction, "shift KP_LEFT", DefaultEditorKit.selectionBackwardAction, "shift RIGHT",
                        DefaultEditorKit.selectionForwardAction, "shift KP_RIGHT", DefaultEditorKit.selectionForwardAction, "ctrl LEFT",
                        DefaultEditorKit.previousWordAction, "ctrl KP_LEFT", DefaultEditorKit.previousWordAction, "ctrl RIGHT",
                        DefaultEditorKit.nextWordAction, "ctrl KP_RIGHT", DefaultEditorKit.nextWordAction, "ctrl shift LEFT",
                        DefaultEditorKit.selectionPreviousWordAction, "ctrl shift KP_LEFT", DefaultEditorKit.selectionPreviousWordAction,
                        "ctrl shift RIGHT", DefaultEditorKit.selectionNextWordAction, "ctrl shift KP_RIGHT",
                        DefaultEditorKit.selectionNextWordAction, "ctrl A", DefaultEditorKit.selectAllAction, "HOME",
                        DefaultEditorKit.beginLineAction, "END", DefaultEditorKit.endLineAction, "shift HOME",
                        DefaultEditorKit.selectionBeginLineAction, "shift END", DefaultEditorKit.selectionEndLineAction, "BACK_SPACE",
                        DefaultEditorKit.deletePrevCharAction, "shift BACK_SPACE", DefaultEditorKit.deletePrevCharAction, "ctrl H",
                        DefaultEditorKit.deletePrevCharAction, "DELETE", DefaultEditorKit.deleteNextCharAction, "ctrl DELETE",
                        DefaultEditorKit.deleteNextWordAction, "ctrl BACK_SPACE", DefaultEditorKit.deletePrevWordAction, "RIGHT",
                        DefaultEditorKit.forwardAction, "LEFT", DefaultEditorKit.backwardAction, "KP_RIGHT", DefaultEditorKit.forwardAction,
                        "KP_LEFT", DefaultEditorKit.backwardAction, "ENTER", JTextField.notifyAction, "ctrl BACK_SLASH", "unselect",
                        "control shift O", "toggle-componentOrientation", "ESCAPE", "reset-field-edit", "UP", "increment", "KP_UP",
                        "increment", "DOWN", "decrement", "KP_DOWN", "decrement", } ) );

        // Multiline areas actions
        final Object multilineInputMap = new UIDefaults.LazyInputMap (
                new Object[]{ "control C", DefaultEditorKit.copyAction, "control V", DefaultEditorKit.pasteAction, "control X",
                        DefaultEditorKit.cutAction, "COPY", DefaultEditorKit.copyAction, "PASTE", DefaultEditorKit.pasteAction, "CUT",
                        DefaultEditorKit.cutAction, "control INSERT", DefaultEditorKit.copyAction, "shift INSERT",
                        DefaultEditorKit.pasteAction, "shift DELETE", DefaultEditorKit.cutAction, "shift LEFT",
                        DefaultEditorKit.selectionBackwardAction, "shift RIGHT", DefaultEditorKit.selectionForwardAction, "control LEFT",
                        DefaultEditorKit.previousWordAction, "control RIGHT", DefaultEditorKit.nextWordAction, "control shift LEFT",
                        DefaultEditorKit.selectionPreviousWordAction, "control shift RIGHT", DefaultEditorKit.selectionNextWordAction,
                        "control A", DefaultEditorKit.selectAllAction, "control BACK_SLASH", "unselect"
                        /*DefaultEditorKit.unselectAction*/, "HOME", DefaultEditorKit.beginLineAction, "END",
                        DefaultEditorKit.endLineAction, "shift HOME", DefaultEditorKit.selectionBeginLineAction, "shift END",
                        DefaultEditorKit.selectionEndLineAction, "control HOME", DefaultEditorKit.beginAction, "control END",
                        DefaultEditorKit.endAction, "control shift HOME", DefaultEditorKit.selectionBeginAction, "control shift END",
                        DefaultEditorKit.selectionEndAction, "UP", DefaultEditorKit.upAction, "DOWN", DefaultEditorKit.downAction,
                        "BACK_SPACE", DefaultEditorKit.deletePrevCharAction, "shift BACK_SPACE", DefaultEditorKit.deletePrevCharAction,
                        "ctrl H", DefaultEditorKit.deletePrevCharAction, "DELETE", DefaultEditorKit.deleteNextCharAction, "ctrl DELETE",
                        DefaultEditorKit.deleteNextWordAction, "ctrl BACK_SPACE", DefaultEditorKit.deletePrevWordAction, "RIGHT",
                        DefaultEditorKit.forwardAction, "LEFT", DefaultEditorKit.backwardAction, "KP_RIGHT", DefaultEditorKit.forwardAction,
                        "KP_LEFT", DefaultEditorKit.backwardAction, "PAGE_UP", DefaultEditorKit.pageUpAction, "PAGE_DOWN",
                        DefaultEditorKit.pageDownAction, "shift PAGE_UP", "selection-page-up", "shift PAGE_DOWN", "selection-page-down",
                        "ctrl shift PAGE_UP", "selection-page-left", "ctrl shift PAGE_DOWN", "selection-page-right", "shift UP",
                        DefaultEditorKit.selectionUpAction, "shift DOWN", DefaultEditorKit.selectionDownAction, "ENTER",
                        DefaultEditorKit.insertBreakAction, "TAB", DefaultEditorKit.insertTabAction, "control T", "next-link-action",
                        "control shift T", "previous-link-action", "control SPACE", "activate-link-action", "control shift O",
                        "toggle-componentOrientation"
                        /*DefaultEditorKit.toggleComponentOrientation*/ } );
        table.put ( "TextArea.focusInputMap", multilineInputMap );
        table.put ( "TextPane.focusInputMap", multilineInputMap );
        table.put ( "EditorPane.focusInputMap", multilineInputMap );

        // WebComboBox actions
        table.put ( "ComboBox.ancestorInputMap", new UIDefaults.LazyInputMap (
                new Object[]{ "ESCAPE", "hidePopup", "PAGE_UP", "pageUpPassThrough", "PAGE_DOWN", "pageDownPassThrough", "HOME",
                        "homePassThrough", "END", "endPassThrough", "DOWN", "selectNext", "KP_DOWN", "selectNext", "alt DOWN",
                        "togglePopup", "alt KP_DOWN", "togglePopup", "alt UP", "togglePopup", "alt KP_UP", "togglePopup", "SPACE",
                        "spacePopup", "ENTER", "enterPressed", "UP", "selectPrevious", "KP_UP", "selectPrevious" } ) );

        // WebFileChooser actions
        table.put ( "FileChooser.ancestorInputMap", new UIDefaults.LazyInputMap (
                new Object[]{ "ESCAPE", "cancelSelection", "F2", "editFileName", "F5", "refresh", "BACK_SPACE", "Go Up", "ENTER",
                        "approveSelection", "ctrl ENTER", "approveSelection" } ) );
    }
    
    /**
     * Initializes all default component fonts.
     *
     * @param table UIDefaults table
     */
    private static void initializeFonts (UIDefaults table) {
        FlatLafInitializer.initializeFonts(table);
    }
    
    /**
     * Initializes custom WebLookAndFeel features.
     */
    @Override
    public void initialize ()
    {
        super.initialize ();

        // Listening to ALT key for menubar quick focusing
        KeyboardFocusManager.getCurrentKeyboardFocusManager ().addKeyEventPostProcessor ( altProcessor );

        // Initialize managers only when L&F was changed
        UIManager.addPropertyChangeListener ( new PropertyChangeListener ()
        {
            @Override
            public void propertyChange ( final PropertyChangeEvent evt )
            {
                if ( evt.getPropertyName ().equals ( LOOK_AND_FEEL_PROPERTY ) )
                {
                    // Initializing managers if WebLaF was installed
                    if ( evt.getNewValue () instanceof FlatLookAndFeel )
                    {
                        // Web decoration for frames and dialogs
                        JFrame.setDefaultLookAndFeelDecorated ( decorateFrames );
                        JDialog.setDefaultLookAndFeelDecorated ( decorateDialogs );

                        // Custom WebLaF data aliases
                        XmlUtils.processAnnotations ( DocumentPaneState.class );
                        XmlUtils.processAnnotations ( TreeState.class );
                        XmlUtils.processAnnotations ( NodeState.class );
                        XmlUtils.processAnnotations ( GradientData.class );
                        XmlUtils.processAnnotations ( GradientColorData.class );
                        XmlUtils.processAnnotations ( HSBColor.class );

                        // Initializing WebLaF managers
                        initializeManagers ();

                        // todo Temporary workaround for JSpinner ENTER update issue when created after JTextField
                        new JSpinner ();
                    }

                    // Remove listener in any case
                    UIManager.removePropertyChangeListener ( this );
                }
            }
        } );
    }

    /**
     * Uninitializes custom WebLookAndFeel features.
     */
    @Override
    public void uninitialize ()
    {
        super.uninitialize ();

        // Removing alt processor
        KeyboardFocusManager.getCurrentKeyboardFocusManager ().removeKeyEventPostProcessor ( altProcessor );
    }

    /**
     * Hides or displays button mnemonics.
     *
     * @param hide whether hide button mnemonics or not
     */
    public static void setMnemonicHidden ( final boolean hide )
    {
        isMnemonicHidden = !UIManager.getBoolean ( "Button.showMnemonics" ) && hide;
    }

    /**
     * Returns whether button mnemonics are hidden or not.
     *
     * @return true if button mnemonics are hidden, false otherwise
     */
    public static boolean isMnemonicHidden ()
    {
        if ( UIManager.getBoolean ( "Button.showMnemonics" ) )
        {
            isMnemonicHidden = false;
        }
        return isMnemonicHidden;
    }

    /**
     * Installs WebLookAndFeel in one simple call.
     *
     * @return true if WebLookAndFeel was successfuly installed, false otherwise
     */
    public static boolean install ()
    {
        return install ( false );
    }

    /**
     * Installs WebLookAndFeel in one simple call and updates all existing components if requested.
     *
     * @param updateExistingComponents whether update all existing components or not
     * @return true if WebLookAndFeel was successfuly installed, false otherwise
     */
    public static boolean install ( final boolean updateExistingComponents )
    {
        // Installing LookAndFeel
        if ( LafUtils.setupLookAndFeelSafely ( FlatLookAndFeel.class ) )
        {
            // Updating already created components tree
            if ( updateExistingComponents )
            {
                updateAllComponentUIs();
            }
            
            // LookAndFeel installed sucessfully
            return true;
        }
        else
        {
            // LookAndFeel installation failed
            return false;
        }
    }

    /**
     * Returns whether WebLookAndFeel is installed or not.
     *
     * @return true if WebLookAndFeel is installed, false otherwise
     */
    public static boolean isInstalled ()
    {
        return UIManager.getLookAndFeel ().getClass ().getCanonicalName ().equals ( FlatLookAndFeel.class.getCanonicalName () );
    }

    /**
     * Initializes library managers.
     * Initialization order is strict since some managers require other managers to be loaded.
     */
    public static void initializeManagers ()
    {
        FlatLafManagers.initialize ();
    }

    /**
     * Returns a list of square WebLookAndFeel images that can be used as window icons on any OS.
     *
     * @return list of square WebLookAndFeel images
     */
    public static List<Image> getImages ()
    {
        loadIcons ();
        return ImageUtils.toImagesList ( icons );
    }

    /**
     * Returns a list of square WebLookAndFeel icons that can be used as window icons on any OS.
     *
     * @return list of square WebLookAndFeel icons
     */
    public static List<ImageIcon> getIcons ()
    {
        loadIcons ();
        return CollectionUtils.copy ( icons );
    }

    /**
     * Returns square WebLookAndFeel image of specified size.
     *
     * @param size square WebLookAndFeel image size
     * @return square WebLookAndFeel image
     */
    public static Image getImage ( final int size )
    {
        return getIcon ( size ).getImage ();
    }

    /**
     * Returns square WebLookAndFeel icon of specified size.
     *
     * @param size square WebLookAndFeel icon size
     * @return square WebLookAndFeel icon
     */
    public static ImageIcon getIcon ( final int size )
    {
        loadIcons ();
        for ( final ImageIcon icon : icons )
        {
            if ( icon.getIconWidth () == size )
            {
                return icon;
            }
        }
        return null;
    }

    /**
     * Loads square WebLookAndFeel icons listed in icons.xml file in resources folder.
     * Loaded icons will have the same order they have in that xml file.
     */
    protected static void loadIcons ()
    {
        if ( icons == null )
        {
            icons = XmlUtils.loadImagesList ( FlatLookAndFeel.class.getResource ( "resources/icons.xml" ) );
        }
    }

    /**
     * Returns a beter disabled icon than BasicLookAndFeel offers.
     * Generated disabled icons are cached within a weak hash map under icon key.
     *
     * @param component component that requests disabled icon
     * @param icon      normal icon
     * @return disabled icon
     */
    @Override
    public Icon getDisabledIcon ( final JComponent component, final Icon icon )
    {
        if ( disabledIcons.containsKey ( icon ) )
        {
            return disabledIcons.get ( icon );
        }
        else
        {
            final ImageIcon disabledIcon = icon instanceof ImageIcon ? ImageUtils.createDisabledCopy ( ( ImageIcon ) icon ) : null;
            disabledIcons.put ( icon, disabledIcon );
            return disabledIcon;
        }
    }

    /**
     * Forces global components UI update in all existing application windows.
     */
    public static void updateAllComponentUIs ()
    {
        for ( final Window window : Window.getWindows () )
        {
            SwingUtilities.updateComponentTreeUI ( window );
        }
    }

    /**
     * Returns custom WebLookAndFeel layout style.
     *
     * @return custom WebLookAndFeel layout style
     */
    @Override
    public LayoutStyle getLayoutStyle ()
    {
        return WebLayoutStyle.getInstance ();
    }

    /**
     * Returns whether look and feel uses custom decoration for newly created frames or not.
     *
     * @return true if look and feel uses custom decoration for newly created frames, false otherwise
     */
    public static boolean isDecorateFrames ()
    {
        return decorateFrames;
    }

    /**
     * Sets whether look and feel should use custom decoration for newly created frames or not.
     *
     * @param decorateFrames whether look and feel should use custom decoration for newly created frames or not
     */
    public static void setDecorateFrames ( final boolean decorateFrames )
    {
        FlatLookAndFeel.decorateFrames = decorateFrames;
        JFrame.setDefaultLookAndFeelDecorated ( decorateFrames );
    }

    /**
     * Returns whether look and feel uses custom decoration for newly created dialogs or not.
     *
     * @return true if look and feel uses custom decoration for newly created dialogs, false otherwise
     */
    public static boolean isDecorateDialogs ()
    {
        return decorateDialogs;
    }

    /**
     * Sets whether look and feel should use custom decoration for newly created dialogs or not.
     *
     * @param decorateDialogs whether look and feel should use custom decoration for newly created dialogs or not
     */
    public static void setDecorateDialogs ( final boolean decorateDialogs )
    {
        FlatLookAndFeel.decorateDialogs = decorateDialogs;
        JDialog.setDefaultLookAndFeelDecorated ( decorateDialogs );
    }

    /**
     * Returns whether per-pixel transparent windows usage is allowed on Linux systems or not.
     *
     * @return true if per-pixel transparent windows usage is allowed on Linux systems, false otherwise
     */
    public static boolean isAllowLinuxTransparency ()
    {
        return ProprietaryUtils.isAllowLinuxTransparency ();
    }

    /**
     * Sets whether per-pixel transparent windows usage is allowed on Linux systems or not.
     * This might be an unstable feature so it is disabled by default. Use it at your own risk.
     *
     * @param allow whether per-pixel transparent windows usage is allowed on Linux systems or not
     */
    public static void setAllowLinuxTransparency ( final boolean allow )
    {
        ProprietaryUtils.setAllowLinuxTransparency ( allow );
    }

    /**
     * Returns default scroll mode used by JViewportUI to handle scrolling repaints.
     *
     * @return default scroll mode used by JViewportUI to handle scrolling repaints
     */
    public static int getScrollMode ()
    {
        return scrollMode;
    }

    /**
     * Sets default scroll mode used by JViewportUI to handle scrolling repaints.
     *
     * @param scrollMode new default scroll mode
     */
    public static void setScrollMode ( final int scrollMode )
    {
        FlatLookAndFeel.scrollMode = scrollMode;
    }

    /**
     * Sets whether look and feel should use custom decoration for newly created frames and dialogs or not.
     *
     * @param decorateAllWindows whether look and feel should use custom decoration for newly created frames and dialogs or not
     */
    public static void setDecorateAllWindows ( final boolean decorateAllWindows )
    {
        setDecorateFrames ( decorateAllWindows );
        setDecorateDialogs ( decorateAllWindows );
    }

    /**
     * Returns whether LTR is current global component orientation or not.
     *
     * @return true if LTR is current global component orientation, false otherwise
     */
    public static boolean isLeftToRight ()
    {
        return getOrientation ().isLeftToRight ();
    }

    /**
     * Returns current global component orientation.
     *
     * @return current global component orientation
     */
    public static ComponentOrientation getOrientation ()
    {
        if ( orientation != null )
        {
            return orientation;
        }
        else
        {
            return ComponentOrientation.getOrientation ( Locale.getDefault () );
        }
    }

    /**
     * Returns orientation opposite to current global component orientation.
     *
     * @return orientation opposite to current global component orientation
     */
    public static ComponentOrientation getOppositeOrientation ()
    {
        return isLeftToRight () ? ComponentOrientation.RIGHT_TO_LEFT : ComponentOrientation.LEFT_TO_RIGHT;
    }

    /**
     * Sets current global component orientation.
     *
     * @param leftToRight whether should set LTR orientation or RTL one
     */
    public static void setOrientation ( final boolean leftToRight )
    {
        setOrientation ( leftToRight ? ComponentOrientation.LEFT_TO_RIGHT : ComponentOrientation.RIGHT_TO_LEFT );
    }

    /**
     * Sets current global component orientation.
     *
     * @param orientation new global component orientation
     */
    public static void setOrientation ( final ComponentOrientation orientation )
    {
        FlatLookAndFeel.orientation = orientation;
        SwingUtils.updateGlobalOrientations ();
    }

    /**
     * Changes current global component orientation to opposite one.
     */
    public static void changeOrientation ()
    {
        setOrientation ( !getOrientation ().isLeftToRight () );
    }
    
}