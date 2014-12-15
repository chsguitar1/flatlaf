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

package org.ocsoft.flatlaf.managers.style;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JRootPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JToolTip;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.table.JTableHeader;

import org.ocsoft.flatlaf.core.FlatLafSettings;
import org.ocsoft.flatlaf.extended.button.WebSplitButton;
import org.ocsoft.flatlaf.extended.checkbox.WebTristateCheckBox;
import org.ocsoft.flatlaf.extended.label.WebMultiLineLabel;
import org.ocsoft.flatlaf.extended.label.WebStyledLabel;
import org.ocsoft.flatlaf.extended.label.WebVerticalLabel;
import org.ocsoft.flatlaf.managers.style.data.ComponentStyleConverter;
import org.ocsoft.flatlaf.utils.LafUtils;
import org.ocsoft.flatlaf.utils.laf.Styleable;
import org.ocsoft.flatlaf.utils.reflection.ReflectUtils;

/**
 * This enumeration represents list of Swing and WebLaF components which support
 * WebLaF styling. It also contains some references and useful settings for each
 * component type.
 *
 * @author Mikle Garin
 * @see <a
 *      href="https://github.com/mgarin/weblaf/wiki/How-to-use-StyleManager">How
 *      to use StyleManager</a>
 * @see org.ocsoft.flatlaf.managers.style.StyleManager
 * @see org.ocsoft.flatlaf.core.FlatLookAndFeel
 */

public enum SupportedComponent {
    /**
     * Label-related components.
     */
    label(true, JLabel.class, "LabelUI", FlatLafSettings.labelUI),
    // linkLabel ( true, WebLinkLabel.class, "LinkLabelUI",
    // WebLookAndFeel.linkLabelUI ),
    verticalLabel(true, WebVerticalLabel.class, "VerticalLabelUI",
            FlatLafSettings.verticalLabelUI), multiLineLabel(false,
            WebMultiLineLabel.class, "MultiLineLabelUI",
            FlatLafSettings.multiLineLabelUI), styledLabel(true,
            WebStyledLabel.class, "StyledLabelUI",
            FlatLafSettings.styledLabelUI), toolTip(false, JToolTip.class,
            "ToolTipUI", FlatLafSettings.toolTipUI),
    
    /**
     * Button-related components.
     */
    button(false, JButton.class, "ButtonUI", FlatLafSettings.buttonUI), splitButton(
            false, WebSplitButton.class, "SplitButtonUI",
            FlatLafSettings.splitButtonUI), toggleButton(false,
            JToggleButton.class, "ToggleButtonUI",
            FlatLafSettings.toggleButtonUI), checkBox(false, JCheckBox.class,
            "CheckBoxUI", FlatLafSettings.checkBoxUI), tristateCheckBox(false,
            WebTristateCheckBox.class, "TristateCheckBoxUI",
            FlatLafSettings.tristateCheckBoxUI), radioButton(false,
            JRadioButton.class, "RadioButtonUI", FlatLafSettings.radioButtonUI),
    
    /**
     * Menu-related components.
     */
    menuBar(false, JMenuBar.class, "MenuBarUI", FlatLafSettings.menuBarUI), menu(
            false, JMenu.class, "MenuUI", FlatLafSettings.menuUI), popupMenu(
            true, JPopupMenu.class, "PopupMenuUI", FlatLafSettings.popupMenuUI), menuItem(
            false, JMenuItem.class, "MenuItemUI", FlatLafSettings.menuItemUI), checkBoxMenuItem(
            false, JCheckBoxMenuItem.class, "CheckBoxMenuItemUI",
            FlatLafSettings.checkBoxMenuItemUI), radioButtonMenuItem(false,
            JRadioButtonMenuItem.class, "RadioButtonMenuItemUI",
            FlatLafSettings.radioButtonMenuItemUI), popupMenuSeparator(false,
            JPopupMenu.Separator.class, "PopupMenuSeparatorUI",
            FlatLafSettings.popupMenuSeparatorUI),
    
    /**
     * Separator component.
     */
    separator(false, JSeparator.class, "SeparatorUI",
            FlatLafSettings.separatorUI),
    
    /**
     * Scroll-related components.
     */
    scrollBar(true, JScrollBar.class, "ScrollBarUI",
            FlatLafSettings.scrollBarUI), scrollPane(false, JScrollPane.class,
            "ScrollPaneUI", FlatLafSettings.scrollPaneUI), viewport(false,
            JViewport.class, "ViewportUI", FlatLafSettings.viewportUI),
    
    /**
     * Text-related components.
     */
    textField(false, JTextField.class, "TextFieldUI",
            FlatLafSettings.textFieldUI), passwordField(false,
            JPasswordField.class, "PasswordFieldUI",
            FlatLafSettings.passwordFieldUI), formattedTextField(false,
            JFormattedTextField.class, "FormattedTextFieldUI",
            FlatLafSettings.formattedTextFieldUI), textArea(false,
            JTextArea.class, "TextAreaUI", FlatLafSettings.textAreaUI), editorPane(
            false, JEditorPane.class, "EditorPaneUI",
            FlatLafSettings.editorPaneUI), textPane(false, JTextPane.class,
            "TextPaneUI", FlatLafSettings.textPaneUI),
    
    /**
     * Toolbar-related components.
     */
    toolBar(false, JToolBar.class, "ToolBarUI", FlatLafSettings.toolBarUI), toolBarSeparator(
            false, JToolBar.Separator.class, "ToolBarSeparatorUI",
            FlatLafSettings.toolBarSeparatorUI),
    
    /**
     * Table-related components.
     */
    table(false, JTable.class, "TableUI", FlatLafSettings.tableUI), tableHeader(
            false, JTableHeader.class, "TableHeaderUI",
            FlatLafSettings.tableHeaderUI),
    
    /**
     * Chooser components.
     */
    colorChooser(false, JColorChooser.class, "ColorChooserUI",
            FlatLafSettings.colorChooserUI), fileChooser(false,
            JFileChooser.class, "FileChooserUI", FlatLafSettings.fileChooserUI),
    
    /**
     * Container-related components.
     */
    panel(true, JPanel.class, "PanelUI", FlatLafSettings.panelUI), rootPane(
            false, JRootPane.class, "RootPaneUI", FlatLafSettings.rootPaneUI), tabbedPane(
            false, JTabbedPane.class, "TabbedPaneUI",
            FlatLafSettings.tabbedPaneUI), splitPane(false, JSplitPane.class,
            "SplitPaneUI", FlatLafSettings.splitPaneUI),
    
    /**
     * Other data-related components.
     */
    progressBar(false, JProgressBar.class, "ProgressBarUI",
            FlatLafSettings.progressBarUI), slider(false, JSlider.class,
            "SliderUI", FlatLafSettings.sliderUI), spinner(false,
            JSpinner.class, "SpinnerUI", FlatLafSettings.spinnerUI), tree(
            false, JTree.class, "TreeUI", FlatLafSettings.treeUI), list(false,
            JList.class, "ListUI", FlatLafSettings.listUI), comboBox(false,
            JComboBox.class, "ComboBoxUI", FlatLafSettings.comboBoxUI),
    
    /**
     * Desktop-pane-related components.
     */
    desktopPane(false, JDesktopPane.class, "DesktopPaneUI",
            FlatLafSettings.desktopPaneUI), desktopIcon(false,
            JInternalFrame.JDesktopIcon.class, "DesktopIconUI",
            FlatLafSettings.desktopIconUI), internalFrame(false,
            JInternalFrame.class, "InternalFrameUI",
            FlatLafSettings.internalFrameUI),
    
    /**
     * Option pane component.
     */
    optionPane(false, JOptionPane.class, "OptionPaneUI",
            FlatLafSettings.optionPaneUI);
    
    /**
     * Enum constant settings.
     */
    protected final boolean supportsPainters;
    protected final Class<? extends JComponent> componentClass;
    protected final String uiClassID;
    protected final String defaultUIClass;
    
    /**
     * Constructs a reference to component with specified settings.
     *
     * @param supportsPainters
     *            whether this component supports painters or not
     * @param componentClass
     *            component class for this component type
     * @param uiClassID
     *            UI class ID used by LookAndFeel to store various settings
     * @param defaultUIClass
     *            default UI class canonical name
     */
    private SupportedComponent(final boolean supportsPainters,
            final Class<? extends JComponent> componentClass,
            final String uiClassID, final String defaultUIClass) {
        this.supportsPainters = supportsPainters;
        this.componentClass = componentClass;
        this.uiClassID = uiClassID;
        this.defaultUIClass = defaultUIClass;
    }
    
    /**
     * Returns whether this component type supports painters or not.
     *
     * @return true if this component type supports painters, false otherwise
     */
    public boolean supportsPainters() {
        return supportsPainters;
    }
    
    /**
     * Returns component class for this component type.
     *
     * @return component class for this component type
     */
    public Class<? extends JComponent> getComponentClass() {
        return componentClass;
    }
    
    /**
     * Returns UI class ID used by LookAndFeel to store various settings.
     *
     * @return UI class ID
     */
    public String getUIClassID() {
        return uiClassID;
    }
    
    /**
     * Returns default UI class canonical name. This value is used in
     * WebLookAndFeel to provide default UI classes. However they can be
     * reassigned before WebLookAndFeel installation.
     *
     * @return default UI class canonical name
     */
    public String getDefaultUIClass() {
        return defaultUIClass;
    }
    
    /**
     * Returns style identifier for the specified component. This identifier
     * might be customized in component to force StyleManager provide another
     * style for that specific component.
     *
     * @return component identifier used within style in skin descriptor
     */
    public String getComponentStyleId(final JComponent component) {
        final Styleable styleable = LafUtils.getStyleable(component);
        final String styleId = styleable != null ? styleable.getStyleId()
                : ComponentStyleConverter.DEFAULT_STYLE_ID;
        return styleId != null ? styleId
                : ComponentStyleConverter.DEFAULT_STYLE_ID;
    }
    
    /**
     * Returns UI class for this component type. Result of this method is not
     * cached because UI classes might be changed in runtime.
     *
     * @return UI class for this component type
     */
    public Class<? extends ComponentUI> getUIClass() {
        final Class type = ReflectUtils.getClassSafely(UIManager
                .getString(getUIClassID()));
        final Class defaultType = ReflectUtils
                .getClassSafely(getDefaultUIClass());
        return ReflectUtils.isAssignable(defaultType, type) ? type
                : defaultType;
    }
    
    /**
     * Component type icons cache.
     */
    private static final Map<SupportedComponent, ImageIcon> componentIcons = new EnumMap<SupportedComponent, ImageIcon>(
            SupportedComponent.class);
    
    /**
     * Returns component type icon.
     *
     * @return component type icon
     */
    public ImageIcon getIcon() {
        ImageIcon icon = componentIcons.get(this);
        if (icon == null) {
            icon = new ImageIcon(
                    SupportedComponent.class.getResource("icons/component/"
                            + this + ".png"));
            componentIcons.put(this, icon);
        }
        return icon;
    }
    
    /**
     * Lazily initialized component types map by their UI class IDs.
     */
    private static final Map<String, SupportedComponent> componentByUIClassID = new HashMap<String, SupportedComponent>(
            values().length);
    
    /**
     * Returns supported component type by UI class ID.
     *
     * @param uiClassID
     *            UI class ID
     * @return supported component type by UI class ID
     */
    public static SupportedComponent getComponentTypeByUIClassID(
            final String uiClassID) {
        if (componentByUIClassID.size() == 0) {
            for (final SupportedComponent supportedComponent : values()) {
                componentByUIClassID.put(supportedComponent.getUIClassID(),
                        supportedComponent);
            }
        }
        return componentByUIClassID.get(uiClassID);
    }
    
    /**
     * Returns list of component types which supports painters.
     *
     * @return list of component types which supports painters
     */
    public static List<SupportedComponent> getPainterSupportedComponents() {
        final List<SupportedComponent> supportedComponents = new ArrayList<SupportedComponent>();
        for (final SupportedComponent sc : SupportedComponent.values()) {
            if (sc.supportsPainters()) {
                supportedComponents.add(sc);
            }
        }
        return supportedComponents;
    }
}