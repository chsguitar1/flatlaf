package org.ocsoft.flatlaf.core;

import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.ocsoft.flatlaf.core.defaults.FlatLafFontDefaults;
import org.ocsoft.flatlaf.extended.button.WebSplitButtonUI;
import org.ocsoft.flatlaf.extended.checkbox.WebTristateCheckBoxUI;
import org.ocsoft.flatlaf.extended.label.WebMultiLineLabelUI;
import org.ocsoft.flatlaf.extended.label.WebStyledLabelUI;
import org.ocsoft.flatlaf.extended.label.WebVerticalLabelUI;
import org.ocsoft.flatlaf.laf.button.FlatButtonUI;
import org.ocsoft.flatlaf.laf.button.FlatToggleButtonUI;
import org.ocsoft.flatlaf.laf.checkbox.FlatCheckBoxUI;
import org.ocsoft.flatlaf.laf.colorchooser.FlatColorChooserUI;
import org.ocsoft.flatlaf.laf.combobox.FlatComboBoxUI;
import org.ocsoft.flatlaf.laf.desktoppane.FlatDesktopIconUI;
import org.ocsoft.flatlaf.laf.desktoppane.FlatDesktopPaneUI;
import org.ocsoft.flatlaf.laf.desktoppane.FlatInternalFrameUI;
import org.ocsoft.flatlaf.laf.filechooser.WebFileChooserUI;
import org.ocsoft.flatlaf.laf.label.WebLabelUI;
import org.ocsoft.flatlaf.laf.list.WebListUI;
import org.ocsoft.flatlaf.laf.menu.WebCheckBoxMenuItemUI;
import org.ocsoft.flatlaf.laf.menu.WebMenuBarUI;
import org.ocsoft.flatlaf.laf.menu.WebMenuItemUI;
import org.ocsoft.flatlaf.laf.menu.WebMenuUI;
import org.ocsoft.flatlaf.laf.menu.WebPopupMenuSeparatorUI;
import org.ocsoft.flatlaf.laf.menu.WebPopupMenuUI;
import org.ocsoft.flatlaf.laf.menu.WebRadioButtonMenuItemUI;
import org.ocsoft.flatlaf.laf.optionpane.WebOptionPaneUI;
import org.ocsoft.flatlaf.laf.panel.WebPanelUI;
import org.ocsoft.flatlaf.laf.progressbar.WebProgressBarUI;
import org.ocsoft.flatlaf.laf.radiobutton.WebRadioButtonUI;
import org.ocsoft.flatlaf.laf.rootpane.WebRootPaneUI;
import org.ocsoft.flatlaf.laf.scroll.WebScrollBarUI;
import org.ocsoft.flatlaf.laf.scroll.WebScrollPaneUI;
import org.ocsoft.flatlaf.laf.separator.WebSeparatorUI;
import org.ocsoft.flatlaf.laf.slider.WebSliderUI;
import org.ocsoft.flatlaf.laf.spinner.WebSpinnerUI;
import org.ocsoft.flatlaf.laf.splitpane.WebSplitPaneUI;
import org.ocsoft.flatlaf.laf.tabbedpane.WebTabbedPaneUI;
import org.ocsoft.flatlaf.laf.table.WebTableHeaderUI;
import org.ocsoft.flatlaf.laf.table.WebTableUI;
import org.ocsoft.flatlaf.laf.text.WebEditorPaneUI;
import org.ocsoft.flatlaf.laf.text.WebFormattedTextFieldUI;
import org.ocsoft.flatlaf.laf.text.WebPasswordFieldUI;
import org.ocsoft.flatlaf.laf.text.WebTextAreaUI;
import org.ocsoft.flatlaf.laf.text.WebTextFieldUI;
import org.ocsoft.flatlaf.laf.text.WebTextPaneUI;
import org.ocsoft.flatlaf.laf.toolbar.WebToolBarSeparatorUI;
import org.ocsoft.flatlaf.laf.toolbar.WebToolBarUI;
import org.ocsoft.flatlaf.laf.tooltip.WebToolTipUI;
import org.ocsoft.flatlaf.laf.tree.WebTreeUI;
import org.ocsoft.flatlaf.laf.viewport.WebViewportUI;

public class FlatLafSettings {
    
    /**
     * Whether all frames should be decorated using WebLaF styling by default or
     * not.
     */
    private static boolean decorateFrames = false;
    
    /**
     * Whether all dialogs should be decorated using WebLaF styling by default
     * or not.
     */
    private static boolean decorateDialogs = false;
    
    /**
     * Returns whether look and feel uses custom decoration for newly created
     * frames or not.
     *
     * @return true if look and feel uses custom decoration for newly created
     *         frames, false otherwise
     */
    public static boolean isDecorateFrames() {
        return decorateFrames;
    }
    
    /**
     * Sets whether look and feel should use custom decoration for newly created
     * frames or not.
     *
     * @param decorateFrames
     *            whether look and feel should use custom decoration for newly
     *            created frames or not
     */
    public static void setDecorateFrames(final boolean decorateFrames) {
        FlatLafSettings.decorateFrames = decorateFrames;
        JFrame.setDefaultLookAndFeelDecorated(decorateFrames);
    }
    
    /**
     * Returns whether look and feel uses custom decoration for newly created
     * dialogs or not.
     *
     * @return true if look and feel uses custom decoration for newly created
     *         dialogs, false otherwise
     */
    public static boolean isDecorateDialogs() {
        return decorateDialogs;
    }
    
    /**
     * Sets whether look and feel should use custom decoration for newly created
     * dialogs or not.
     *
     * @param decorateDialogs
     *            whether look and feel should use custom decoration for newly
     *            created dialogs or not
     */
    public static void setDecorateDialogs(final boolean decorateDialogs) {
        FlatLafSettings.decorateDialogs = decorateDialogs;
        JDialog.setDefaultLookAndFeelDecorated(decorateDialogs);
    }
    
    /**
     * Sets whether look and feel should use custom decoration for newly created
     * frames and dialogs or not.
     *
     * @param decorateAllWindows
     *            whether look and feel should use custom decoration for newly
     *            created frames and dialogs or not
     */
    public static void setDecorateAllWindows(final boolean decorateAllWindows) {
        setDecorateFrames(decorateAllWindows);
        setDecorateDialogs(decorateAllWindows);
    }
    
    /*
     * Label-related components.
     */
    public static String labelUI = WebLabelUI.class.getCanonicalName();
    // public static String linkLabelUI = WebLabelUI.class.getCanonicalName ();
    public static String verticalLabelUI = WebVerticalLabelUI.class
            .getCanonicalName();
    public static String multiLineLabelUI = WebMultiLineLabelUI.class
            .getCanonicalName();
    public static String styledLabelUI = WebStyledLabelUI.class
            .getCanonicalName();
    public static String toolTipUI = WebToolTipUI.class.getCanonicalName();
    
    /*
     * Button-related components.
     */
    public static String buttonUI = FlatButtonUI.class.getCanonicalName();
    public static String splitButtonUI = WebSplitButtonUI.class
            .getCanonicalName();
    public static String toggleButtonUI = FlatToggleButtonUI.class
            .getCanonicalName();
    public static String checkBoxUI = FlatCheckBoxUI.class.getCanonicalName();
    public static String tristateCheckBoxUI = WebTristateCheckBoxUI.class
            .getCanonicalName();
    public static String radioButtonUI = WebRadioButtonUI.class
            .getCanonicalName();
    
    /*
     * Menu-related components.
     */
    public static String menuBarUI = WebMenuBarUI.class.getCanonicalName();
    public static String menuUI = WebMenuUI.class.getCanonicalName();
    public static String popupMenuUI = WebPopupMenuUI.class.getCanonicalName();
    public static String menuItemUI = WebMenuItemUI.class.getCanonicalName();
    public static String checkBoxMenuItemUI = WebCheckBoxMenuItemUI.class
            .getCanonicalName();
    public static String radioButtonMenuItemUI = WebRadioButtonMenuItemUI.class
            .getCanonicalName();
    public static String popupMenuSeparatorUI = WebPopupMenuSeparatorUI.class
            .getCanonicalName();
    
    /*
     * Separator component.
     */
    public static String separatorUI = WebSeparatorUI.class.getCanonicalName();
    
    /*
     * Scroll-related components.
     */
    public static String scrollBarUI = WebScrollBarUI.class.getCanonicalName();
    public static String scrollPaneUI = WebScrollPaneUI.class
            .getCanonicalName();
    public static String viewportUI = WebViewportUI.class.getCanonicalName();
    
    /*
     * Text-related components.
     */
    public static String textFieldUI = WebTextFieldUI.class.getCanonicalName();
    public static String passwordFieldUI = WebPasswordFieldUI.class
            .getCanonicalName();
    public static String formattedTextFieldUI = WebFormattedTextFieldUI.class
            .getCanonicalName();
    public static String textAreaUI = WebTextAreaUI.class.getCanonicalName();
    public static String editorPaneUI = WebEditorPaneUI.class
            .getCanonicalName();
    public static String textPaneUI = WebTextPaneUI.class.getCanonicalName();
    
    /*
     * Toolbar-related components.
     */
    public static String toolBarUI = WebToolBarUI.class.getCanonicalName();
    public static String toolBarSeparatorUI = WebToolBarSeparatorUI.class
            .getCanonicalName();
    
    /*
     * Table-related components.
     */
    public static String tableUI = WebTableUI.class.getCanonicalName();
    public static String tableHeaderUI = WebTableHeaderUI.class
            .getCanonicalName();
    
    /*
     * Chooser components.
     */
    public static String colorChooserUI = FlatColorChooserUI.class
            .getCanonicalName();
    public static String fileChooserUI = WebFileChooserUI.class
            .getCanonicalName();
    
    /*
     * Container-related components.
     */
    public static String panelUI = WebPanelUI.class.getCanonicalName();
    public static String rootPaneUI = WebRootPaneUI.class.getCanonicalName();
    public static String tabbedPaneUI = WebTabbedPaneUI.class
            .getCanonicalName();
    public static String splitPaneUI = WebSplitPaneUI.class.getCanonicalName();
    
    /*
     * Other data-related components.
     */
    public static String progressBarUI = WebProgressBarUI.class
            .getCanonicalName();
    public static String sliderUI = WebSliderUI.class.getCanonicalName();
    public static String spinnerUI = WebSpinnerUI.class.getCanonicalName();
    public static String treeUI = WebTreeUI.class.getCanonicalName();
    public static String listUI = WebListUI.class.getCanonicalName();
    public static String comboBoxUI = FlatComboBoxUI.class.getCanonicalName();
    
    /*
     * Desktop-pane-related components.
     */
    public static String desktopPaneUI = FlatDesktopPaneUI.class
            .getCanonicalName();
    public static String desktopIconUI = FlatDesktopIconUI.class
            .getCanonicalName();
    public static String internalFrameUI = FlatInternalFrameUI.class
            .getCanonicalName();
    
    /*
     * Option pane component.
     */
    public static String optionPaneUI = WebOptionPaneUI.class
            .getCanonicalName();
    
    /**
     * Reassignable LookAndFeel fonts.
     */
    
    // Text components fonts
    public static Font globalControlFont = FlatLafFontDefaults.getSystemControlFont();
    public static Font buttonFont;
    public static Font toggleButtonFont;
    public static Font radioButtonFont;
    public static Font checkBoxFont;
    public static Font colorChooserFont;
    public static Font labelFont;
    public static Font listFont;
    public static Font panelFont;
    public static Font progressBarFont;
    public static Font scrollPaneFont;
    public static Font viewportFont;
    public static Font sliderFont;
    public static Font tabbedPaneFont;
    public static Font tableFont;
    public static Font tableHeaderFont;
    public static Font titledBorderFont;
    public static Font toolBarFont;
    public static Font treeFont;
    
    public static Font globalTooltipFont = FlatLafFontDefaults.getSystemTooltipFont();
    public static Font toolTipFont;
    
    // Option pane font
    public static Font globalAlertFont = FlatLafFontDefaults.getSystemAlertFont();
    public static Font optionPaneFont;
    
    // Menu font
    public static Font globalMenuFont = FlatLafFontDefaults.getSystemMenuFont();
    public static Font menuBarFont;
    public static Font menuFont;
    public static Font menuItemFont;
    public static Font radioButtonMenuItemFont;
    public static Font checkBoxMenuItemFont;
    public static Font popupMenuFont;
    
    // Component's accelerators fonts
    public static Font globalAcceleratorFont = FlatLafFontDefaults
            .getSystemAcceleratorFont();
    public static Font menuItemAcceleratorFont;
    public static Font radioButtonMenuItemAcceleratorFont;
    public static Font checkBoxMenuItemAcceleratorFont;
    public static Font menuAcceleratorFont;
    
    // Title components fonts
    public static Font globalTitleFont = FlatLafFontDefaults.getSystemTitleFont();
    public static Font internalFrameFont;
    
    // Editor components fonts
    public static Font globalTextFont = FlatLafFontDefaults.getSystemTextFont();
    public static Font comboBoxFont;
    public static Font spinnerFont;
    public static Font textFieldFont;
    public static Font formattedTextFieldFont;
    public static Font passwordFieldFont;
    public static Font textAreaFont;
    public static Font textPaneFont;
    public static Font editorPaneFont;
    
    
    private FlatLafSettings() {}
    
}
