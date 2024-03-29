package org.ocsoft.flatlaf.core;

import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import org.ocsoft.flatlaf.core.constants.FlatLafConstants;
import org.ocsoft.flatlaf.extended.colorchooser.GradientColorData;
import org.ocsoft.flatlaf.extended.colorchooser.GradientData;
import org.ocsoft.flatlaf.extended.tab.DocumentPaneState;
import org.ocsoft.flatlaf.managers.drag.DragManager;
import org.ocsoft.flatlaf.managers.focus.FocusManager;
import org.ocsoft.flatlaf.managers.hotkey.HotkeyManager;
import org.ocsoft.flatlaf.managers.language.WebLanguageManager;
import org.ocsoft.flatlaf.managers.proxy.WebProxyManager;
import org.ocsoft.flatlaf.managers.settings.WebSettingsManager;
import org.ocsoft.flatlaf.managers.style.StyleManager;
import org.ocsoft.flatlaf.managers.tooltip.TooltipManager;
import org.ocsoft.flatlaf.utils.ColorUtils;
import org.ocsoft.flatlaf.utils.swing.SwingLazyValue;
import org.ocsoft.flatlaf.utils.system.FlatLafLogger;
import org.ocsoft.flatlaf.utils.xml.XmlUtils;
import org.ocsoft.flatlaf.weblaf.AltProcessor;
import org.ocsoft.flatlaf.weblaf.FlatLafStyleConstants;
import org.ocsoft.flatlaf.weblaf.colorchooser.HSBColor;
import org.ocsoft.flatlaf.weblaf.tree.NodeState;
import org.ocsoft.flatlaf.weblaf.tree.TreeState;

public class FlatLafInitializer {
    
    private enum FontGroup {
        CONTROL, TEXT, TITLE, MENU, ACCELERATOR, ALERT, TOOLTIP
    }
    
    /**
     * Alt hotkey processor for application windows with menu.
     */
    private static final AltProcessor altProcessor = new AltProcessor();
    
    static void initialize() {

        // Listening to ALT key for menubar quick focusing
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventPostProcessor(altProcessor);
        
        // Initialize managers only when L&F was changed
        UIManager.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(FlatLafConstants.LOOK_AND_FEEL_PROPERTY)) {
                    // Initializing managers if WebLaF was installed
                    if (evt.getNewValue() instanceof FlatLookAndFeel) {
                        // Web decoration for frames and dialogs
                        JFrame.setDefaultLookAndFeelDecorated(FlatLafSettings.isDecorateFrames());
                        JDialog.setDefaultLookAndFeelDecorated(FlatLafSettings.isDecorateDialogs());
                        
                        // Custom WebLaF data aliases
                        XmlUtils.processAnnotations(DocumentPaneState.class);
                        XmlUtils.processAnnotations(TreeState.class);
                        XmlUtils.processAnnotations(NodeState.class);
                        XmlUtils.processAnnotations(GradientData.class);
                        XmlUtils.processAnnotations(GradientColorData.class);
                        XmlUtils.processAnnotations(HSBColor.class);
                        
                        // Initializing WebLaF managers
                        initializeManagers();
                        
                        // todo Temporary workaround for JSpinner ENTER update
                        // issue when created after JTextField
                        new JSpinner();
                    }
                    
                    // Remove listener in any case
                    UIManager.removePropertyChangeListener(this);
                }
            }
        });
    }
    
    static void uninitialize() {
       // Removing alt processor
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .removeKeyEventPostProcessor(altProcessor);
    }
    
    /**
     * Initializes library managers. Initialization order is strict since some
     * managers require other managers to be loaded.
     */
    static synchronized void initializeManagers() {
        FlatLafLogger.initialize();
        WebLanguageManager.initialize();
        WebSettingsManager.initialize();
        HotkeyManager.initialize();
        FocusManager.initialize();
        TooltipManager.initialize();
        StyleManager.initialize();
        WebProxyManager.initialize();
        DragManager.initialize();
    }
    
    static String[] createSystemColorDefaults(UIDefaults table) {
        
        String textColor = ColorUtils
                .getHexColor(FlatLafStyleConstants.textColor);
        String textHighlightColor = ColorUtils
                .getHexColor(FlatLafStyleConstants.textSelectionColor);
        String inactiveTextColor = ColorUtils
                .getHexColor(FlatLafStyleConstants.disabledTextColor);
        
        return new String[] { "menu", "#ffffff", "menuText", textColor,
                "textHighlight", textHighlightColor, "textHighlightText",
                textColor, "textInactiveText", inactiveTextColor,
                "controlText", textColor, };
    }
    
    static void initializeFonts(UIDefaults table) {
        initializeFont(table, "Button.font", FlatLafSettings.buttonFont,
                FontGroup.CONTROL);
        initializeFont(table, "ToggleButton.font",
                FlatLafSettings.toggleButtonFont, FontGroup.CONTROL);
        initializeFont(table, "RadioButton.font",
                FlatLafSettings.radioButtonFont, FontGroup.CONTROL);
        initializeFont(table, "CheckBox.font", FlatLafSettings.checkBoxFont,
                FontGroup.CONTROL);
        initializeFont(table, "ColorChooser.font",
                FlatLafSettings.colorChooserFont, FontGroup.CONTROL);
        initializeFont(table, "ComboBox.font", FlatLafSettings.comboBoxFont,
                FontGroup.TEXT);
        initializeFont(table, "InternalFrame.titleFont",
                FlatLafSettings.internalFrameFont, FontGroup.TITLE);
        initializeFont(table, "Label.font", FlatLafSettings.labelFont,
                FontGroup.CONTROL);
        initializeFont(table, "List.font", FlatLafSettings.listFont,
                FontGroup.CONTROL);
        initializeFont(table, "MenuBar.font", FlatLafSettings.menuBarFont,
                FontGroup.MENU);
        initializeFont(table, "MenuItem.font", FlatLafSettings.menuItemFont,
                FontGroup.MENU);
        initializeFont(table, "MenuItem.acceleratorFont",
                FlatLafSettings.menuItemAcceleratorFont, FontGroup.ACCELERATOR);
        initializeFont(table, "RadioButtonMenuItem.font",
                FlatLafSettings.radioButtonMenuItemFont, FontGroup.MENU);
        initializeFont(table, "RadioButtonMenuItem.acceleratorFont",
                FlatLafSettings.radioButtonMenuItemAcceleratorFont,
                FontGroup.ACCELERATOR);
        initializeFont(table, "CheckBoxMenuItem.font",
                FlatLafSettings.checkBoxMenuItemFont, FontGroup.MENU);
        initializeFont(table, "CheckBoxMenuItem.acceleratorFont",
                FlatLafSettings.checkBoxMenuItemAcceleratorFont,
                FontGroup.ACCELERATOR);
        initializeFont(table, "Menu.font", FlatLafSettings.menuFont,
                FontGroup.MENU);
        initializeFont(table, "Menu.acceleratorFont",
                FlatLafSettings.menuAcceleratorFont, FontGroup.ACCELERATOR);
        initializeFont(table, "PopupMenu.font", FlatLafSettings.popupMenuFont,
                FontGroup.MENU);
        initializeFont(table, "OptionPane.font",
                FlatLafSettings.optionPaneFont, FontGroup.ALERT);
        initializeFont(table, "Panel.font", FlatLafSettings.panelFont,
                FontGroup.CONTROL);
        initializeFont(table, "ProgressBar.font",
                FlatLafSettings.progressBarFont, FontGroup.CONTROL);
        initializeFont(table, "ScrollPane.font",
                FlatLafSettings.scrollPaneFont, FontGroup.CONTROL);
        initializeFont(table, "Viewport.font", FlatLafSettings.viewportFont,
                FontGroup.CONTROL);
        initializeFont(table, "Slider.font", FlatLafSettings.sliderFont,
                FontGroup.CONTROL);
        initializeFont(table, "Spinner.font", FlatLafSettings.spinnerFont,
                FontGroup.TEXT);
        initializeFont(table, "TabbedPane.font",
                FlatLafSettings.tabbedPaneFont, FontGroup.CONTROL);
        initializeFont(table, "Table.font", FlatLafSettings.tableFont,
                FontGroup.CONTROL);
        initializeFont(table, "TableHeader.font",
                FlatLafSettings.tableHeaderFont, FontGroup.CONTROL);
        initializeFont(table, "TextField.font", FlatLafSettings.textFieldFont,
                FontGroup.TEXT);
        initializeFont(table, "FormattedTextField.font",
                FlatLafSettings.formattedTextFieldFont, FontGroup.TEXT);
        initializeFont(table, "PasswordField.font",
                FlatLafSettings.passwordFieldFont, FontGroup.TEXT);
        initializeFont(table, "TextArea.font", FlatLafSettings.textAreaFont,
                FontGroup.TEXT);
        initializeFont(table, "TextPane.font", FlatLafSettings.textPaneFont,
                FontGroup.TEXT);
        initializeFont(table, "EditorPane.font",
                FlatLafSettings.editorPaneFont, FontGroup.TEXT);
        initializeFont(table, "TitledBorder.font",
                FlatLafSettings.titledBorderFont, FontGroup.CONTROL);
        initializeFont(table, "ToolBar.font", FlatLafSettings.toolBarFont,
                FontGroup.CONTROL);
        initializeFont(table, "ToolTip.font", FlatLafSettings.toolTipFont,
                FontGroup.TOOLTIP);
        initializeFont(table, "Tree.font", FlatLafSettings.treeFont,
                FontGroup.CONTROL);
    }
    
    /**
     * Initializes single component font.
     *
     * @param table
     *            UIDefaults table
     * @param key
     *            component font key
     * @param font
     *            custom font
     * @param globalFont
     *            global font
     */
    private static void initializeFont(UIDefaults table, String key, Font font,
            FontGroup group) {
        Font globalFont = getGlobalFont(group);
        
        table.put(key, createLazyFont(font != null ? font : globalFont));
    }
    
    private static Font getGlobalFont(FontGroup group) {
        switch (group) {
        case CONTROL:
            return FlatLafSettings.globalControlFont;
        case MENU:
            return FlatLafSettings.globalMenuFont;
        case TEXT:
            return FlatLafSettings.globalTextFont;
        case TITLE:
            return FlatLafSettings.globalTitleFont;
        case ACCELERATOR:
            return FlatLafSettings.globalAcceleratorFont;
        case ALERT:
            return FlatLafSettings.globalAlertFont;
        case TOOLTIP:
            return FlatLafSettings.globalTooltipFont;
        }
        return null;
    }
    
    /**
     * Returns SwingLazyValue for specified font.
     *
     * @param font
     *            font
     * @return SwingLazyValue for specified font
     */
    protected static SwingLazyValue createLazyFont(final Font font) {
        return new SwingLazyValue(
                "javax.swing.plaf.FontUIResource",
                null,
                new Object[] { font.getName(), font.getStyle(), font.getSize() });
    }
    
    public static void initClassDefaults(UIDefaults table) {
        // Label
        table.put("LabelUI", FlatLafSettings.labelUI);
        // table.put ( "LinkLabelUI", FlatLafSettings.linkLabelUI );
        table.put("VerticalLabelUI", FlatLafSettings.verticalLabelUI);
        table.put("MultiLineLabelUI", FlatLafSettings.multiLineLabelUI);
        table.put("StyledLabelUI", FlatLafSettings.styledLabelUI);
        table.put("ToolTipUI", FlatLafSettings.toolTipUI);
        
        // Button
        table.put("ButtonUI", FlatLafSettings.buttonUI);
        table.put("SplitButtonUI", FlatLafSettings.splitButtonUI);
        table.put("ToggleButtonUI", FlatLafSettings.toggleButtonUI);
        table.put("CheckBoxUI", FlatLafSettings.checkBoxUI);
        table.put("TristateCheckBoxUI", FlatLafSettings.tristateCheckBoxUI);
        table.put("RadioButtonUI", FlatLafSettings.radioButtonUI);
        
        // Menu
        table.put("MenuBarUI", FlatLafSettings.menuBarUI);
        table.put("MenuUI", FlatLafSettings.menuUI);
        table.put("PopupMenuUI", FlatLafSettings.popupMenuUI);
        table.put("MenuItemUI", FlatLafSettings.menuItemUI);
        table.put("CheckBoxMenuItemUI", FlatLafSettings.checkBoxMenuItemUI);
        table.put("RadioButtonMenuItemUI",
                FlatLafSettings.radioButtonMenuItemUI);
        table.put("PopupMenuSeparatorUI", FlatLafSettings.popupMenuSeparatorUI);
        
        // Separator
        table.put("SeparatorUI", FlatLafSettings.separatorUI);
        
        // Scroll
        table.put("ScrollBarUI", FlatLafSettings.scrollBarUI);
        table.put("ScrollPaneUI", FlatLafSettings.scrollPaneUI);
        table.put("ViewportUI", FlatLafSettings.viewportUI);
        
        // Text
        table.put("TextFieldUI", FlatLafSettings.textFieldUI);
        table.put("PasswordFieldUI", FlatLafSettings.passwordFieldUI);
        table.put("FormattedTextFieldUI", FlatLafSettings.formattedTextFieldUI);
        table.put("TextAreaUI", FlatLafSettings.textAreaUI);
        table.put("EditorPaneUI", FlatLafSettings.editorPaneUI);
        table.put("TextPaneUI", FlatLafSettings.textPaneUI);
        
        // Toolbar
        table.put("ToolBarUI", FlatLafSettings.toolBarUI);
        table.put("ToolBarSeparatorUI", FlatLafSettings.toolBarSeparatorUI);
        
        // Table
        table.put("TableUI", FlatLafSettings.tableUI);
        table.put("TableHeaderUI", FlatLafSettings.tableHeaderUI);
        
        // Chooser
        table.put("ColorChooserUI", FlatLafSettings.colorChooserUI);
        table.put("FileChooserUI", FlatLafSettings.fileChooserUI);
        
        // Container
        table.put("PanelUI", FlatLafSettings.panelUI);
        table.put("RootPaneUI", FlatLafSettings.rootPaneUI);
        table.put("TabbedPaneUI", FlatLafSettings.tabbedPaneUI);
        table.put("SplitPaneUI", FlatLafSettings.splitPaneUI);
        
        // Complex components
        table.put("ProgressBarUI", FlatLafSettings.progressBarUI);
        table.put("SliderUI", FlatLafSettings.sliderUI);
        table.put("SpinnerUI", FlatLafSettings.spinnerUI);
        table.put("TreeUI", FlatLafSettings.treeUI);
        table.put("ListUI", FlatLafSettings.listUI);
        table.put("ComboBoxUI", FlatLafSettings.comboBoxUI);
        
        // Desktop pane
        table.put("DesktopPaneUI", FlatLafSettings.desktopPaneUI);
        table.put("DesktopIconUI", FlatLafSettings.desktopIconUI);
        table.put("InternalFrameUI", FlatLafSettings.internalFrameUI);
        
        // Option pane
        table.put("OptionPaneUI", FlatLafSettings.optionPaneUI);
    }
    
}
