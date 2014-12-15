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

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.table.JTableHeader;

import org.ocsoft.flatlaf.extended.button.WebSplitButton;
import org.ocsoft.flatlaf.extended.checkbox.WebTristateCheckBox;
import org.ocsoft.flatlaf.extended.label.WebMultiLineLabel;
import org.ocsoft.flatlaf.extended.label.WebStyledLabel;
import org.ocsoft.flatlaf.extended.label.WebVerticalLabel;
import org.ocsoft.flatlaf.laf.FlatLookAndFeel;
import org.ocsoft.flatlaf.managers.style.data.ComponentStyleConverter;
import org.ocsoft.flatlaf.utils.LafUtils;
import org.ocsoft.flatlaf.utils.ReflectUtils;
import org.ocsoft.flatlaf.utils.laf.Styleable;

import java.util.*;

/**
 * This enumeration represents list of Swing and WebLaF components which support WebLaF styling.
 * It also contains some references and useful settings for each component type.
 *
 * @author Mikle Garin
 * @see <a href="https://github.com/mgarin/weblaf/wiki/How-to-use-StyleManager">How to use StyleManager</a>
 * @see org.ocsoft.flatlaf.managers.style.StyleManager
 * @see org.ocsoft.flatlaf.laf.FlatLookAndFeel
 */

public enum SupportedComponent
{
    /**
     * Label-related components.
     */
    label ( true, JLabel.class, "LabelUI", FlatLookAndFeel.labelUI ),
    // linkLabel ( true, WebLinkLabel.class, "LinkLabelUI", WebLookAndFeel.linkLabelUI ),
    verticalLabel ( true, WebVerticalLabel.class, "VerticalLabelUI", FlatLookAndFeel.verticalLabelUI ),
    multiLineLabel ( false, WebMultiLineLabel.class, "MultiLineLabelUI", FlatLookAndFeel.multiLineLabelUI ),
    styledLabel ( true, WebStyledLabel.class, "StyledLabelUI", FlatLookAndFeel.styledLabelUI ),
    toolTip ( false, JToolTip.class, "ToolTipUI", FlatLookAndFeel.toolTipUI ),

    /**
     * Button-related components.
     */
    button ( false, JButton.class, "ButtonUI", FlatLookAndFeel.buttonUI ),
    splitButton ( false, WebSplitButton.class, "SplitButtonUI", FlatLookAndFeel.splitButtonUI ),
    toggleButton ( false, JToggleButton.class, "ToggleButtonUI", FlatLookAndFeel.toggleButtonUI ),
    checkBox ( false, JCheckBox.class, "CheckBoxUI", FlatLookAndFeel.checkBoxUI ),
    tristateCheckBox ( false, WebTristateCheckBox.class, "TristateCheckBoxUI", FlatLookAndFeel.tristateCheckBoxUI ),
    radioButton ( false, JRadioButton.class, "RadioButtonUI", FlatLookAndFeel.radioButtonUI ),

    /**
     * Menu-related components.
     */
    menuBar ( false, JMenuBar.class, "MenuBarUI", FlatLookAndFeel.menuBarUI ),
    menu ( false, JMenu.class, "MenuUI", FlatLookAndFeel.menuUI ),
    popupMenu ( true, JPopupMenu.class, "PopupMenuUI", FlatLookAndFeel.popupMenuUI ),
    menuItem ( false, JMenuItem.class, "MenuItemUI", FlatLookAndFeel.menuItemUI ),
    checkBoxMenuItem ( false, JCheckBoxMenuItem.class, "CheckBoxMenuItemUI", FlatLookAndFeel.checkBoxMenuItemUI ),
    radioButtonMenuItem ( false, JRadioButtonMenuItem.class, "RadioButtonMenuItemUI", FlatLookAndFeel.radioButtonMenuItemUI ),
    popupMenuSeparator ( false, JPopupMenu.Separator.class, "PopupMenuSeparatorUI", FlatLookAndFeel.popupMenuSeparatorUI ),

    /**
     * Separator component.
     */
    separator ( false, JSeparator.class, "SeparatorUI", FlatLookAndFeel.separatorUI ),

    /**
     * Scroll-related components.
     */
    scrollBar ( true, JScrollBar.class, "ScrollBarUI", FlatLookAndFeel.scrollBarUI ),
    scrollPane ( false, JScrollPane.class, "ScrollPaneUI", FlatLookAndFeel.scrollPaneUI ),
    viewport ( false, JViewport.class, "ViewportUI", FlatLookAndFeel.viewportUI ),

    /**
     * Text-related components.
     */
    textField ( false, JTextField.class, "TextFieldUI", FlatLookAndFeel.textFieldUI ),
    passwordField ( false, JPasswordField.class, "PasswordFieldUI", FlatLookAndFeel.passwordFieldUI ),
    formattedTextField ( false, JFormattedTextField.class, "FormattedTextFieldUI", FlatLookAndFeel.formattedTextFieldUI ),
    textArea ( false, JTextArea.class, "TextAreaUI", FlatLookAndFeel.textAreaUI ),
    editorPane ( false, JEditorPane.class, "EditorPaneUI", FlatLookAndFeel.editorPaneUI ),
    textPane ( false, JTextPane.class, "TextPaneUI", FlatLookAndFeel.textPaneUI ),

    /**
     * Toolbar-related components.
     */
    toolBar ( false, JToolBar.class, "ToolBarUI", FlatLookAndFeel.toolBarUI ),
    toolBarSeparator ( false, JToolBar.Separator.class, "ToolBarSeparatorUI", FlatLookAndFeel.toolBarSeparatorUI ),

    /**
     * Table-related components.
     */
    table ( false, JTable.class, "TableUI", FlatLookAndFeel.tableUI ),
    tableHeader ( false, JTableHeader.class, "TableHeaderUI", FlatLookAndFeel.tableHeaderUI ),

    /**
     * Chooser components.
     */
    colorChooser ( false, JColorChooser.class, "ColorChooserUI", FlatLookAndFeel.colorChooserUI ),
    fileChooser ( false, JFileChooser.class, "FileChooserUI", FlatLookAndFeel.fileChooserUI ),

    /**
     * Container-related components.
     */
    panel ( true, JPanel.class, "PanelUI", FlatLookAndFeel.panelUI ),
    rootPane ( false, JRootPane.class, "RootPaneUI", FlatLookAndFeel.rootPaneUI ),
    tabbedPane ( false, JTabbedPane.class, "TabbedPaneUI", FlatLookAndFeel.tabbedPaneUI ),
    splitPane ( false, JSplitPane.class, "SplitPaneUI", FlatLookAndFeel.splitPaneUI ),

    /**
     * Other data-related components.
     */
    progressBar ( false, JProgressBar.class, "ProgressBarUI", FlatLookAndFeel.progressBarUI ),
    slider ( false, JSlider.class, "SliderUI", FlatLookAndFeel.sliderUI ),
    spinner ( false, JSpinner.class, "SpinnerUI", FlatLookAndFeel.spinnerUI ),
    tree ( false, JTree.class, "TreeUI", FlatLookAndFeel.treeUI ),
    list ( false, JList.class, "ListUI", FlatLookAndFeel.listUI ),
    comboBox ( false, JComboBox.class, "ComboBoxUI", FlatLookAndFeel.comboBoxUI ),

    /**
     * Desktop-pane-related components.
     */
    desktopPane ( false, JDesktopPane.class, "DesktopPaneUI", FlatLookAndFeel.desktopPaneUI ),
    desktopIcon ( false, JInternalFrame.JDesktopIcon.class, "DesktopIconUI", FlatLookAndFeel.desktopIconUI ),
    internalFrame ( false, JInternalFrame.class, "InternalFrameUI", FlatLookAndFeel.internalFrameUI ),

    /**
     * Option pane component.
     */
    optionPane ( false, JOptionPane.class, "OptionPaneUI", FlatLookAndFeel.optionPaneUI );

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
     * @param supportsPainters whether this component supports painters or not
     * @param componentClass   component class for this component type
     * @param uiClassID        UI class ID used by LookAndFeel to store various settings
     * @param defaultUIClass   default UI class canonical name
     */
    private SupportedComponent ( final boolean supportsPainters, final Class<? extends JComponent> componentClass, final String uiClassID,
                                 final String defaultUIClass )
    {
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
    public boolean supportsPainters ()
    {
        return supportsPainters;
    }

    /**
     * Returns component class for this component type.
     *
     * @return component class for this component type
     */
    public Class<? extends JComponent> getComponentClass ()
    {
        return componentClass;
    }

    /**
     * Returns UI class ID used by LookAndFeel to store various settings.
     *
     * @return UI class ID
     */
    public String getUIClassID ()
    {
        return uiClassID;
    }

    /**
     * Returns default UI class canonical name.
     * This value is used in WebLookAndFeel to provide default UI classes.
     * However they can be reassigned before WebLookAndFeel installation.
     *
     * @return default UI class canonical name
     */
    public String getDefaultUIClass ()
    {
        return defaultUIClass;
    }

    /**
     * Returns style identifier for the specified component.
     * This identifier might be customized in component to force StyleManager provide another style for that specific component.
     *
     * @return component identifier used within style in skin descriptor
     */
    public String getComponentStyleId ( final JComponent component )
    {
        final Styleable styleable = LafUtils.getStyleable ( component );
        final String styleId = styleable != null ? styleable.getStyleId () : ComponentStyleConverter.DEFAULT_STYLE_ID;
        return styleId != null ? styleId : ComponentStyleConverter.DEFAULT_STYLE_ID;
    }

    /**
     * Returns UI class for this component type.
     * Result of this method is not cached because UI classes might be changed in runtime.
     *
     * @return UI class for this component type
     */
    public Class<? extends ComponentUI> getUIClass ()
    {
        final Class type = ReflectUtils.getClassSafely ( UIManager.getString ( getUIClassID () ) );
        final Class defaultType = ReflectUtils.getClassSafely ( getDefaultUIClass () );
        return ReflectUtils.isAssignable ( defaultType, type ) ? type : defaultType;
    }

    /**
     * Component type icons cache.
     */
    private static final Map<SupportedComponent, ImageIcon> componentIcons =
            new EnumMap<SupportedComponent, ImageIcon> ( SupportedComponent.class );

    /**
     * Returns component type icon.
     *
     * @return component type icon
     */
    public ImageIcon getIcon ()
    {
        ImageIcon icon = componentIcons.get ( this );
        if ( icon == null )
        {
            icon = new ImageIcon ( SupportedComponent.class.getResource ( "icons/component/" + this + ".png" ) );
            componentIcons.put ( this, icon );
        }
        return icon;
    }

    /**
     * Lazily initialized component types map by their UI class IDs.
     */
    private static final Map<String, SupportedComponent> componentByUIClassID =
            new HashMap<String, SupportedComponent> ( values ().length );

    /**
     * Returns supported component type by UI class ID.
     *
     * @param uiClassID UI class ID
     * @return supported component type by UI class ID
     */
    public static SupportedComponent getComponentTypeByUIClassID ( final String uiClassID )
    {
        if ( componentByUIClassID.size () == 0 )
        {
            for ( final SupportedComponent supportedComponent : values () )
            {
                componentByUIClassID.put ( supportedComponent.getUIClassID (), supportedComponent );
            }
        }
        return componentByUIClassID.get ( uiClassID );
    }

    /**
     * Returns list of component types which supports painters.
     *
     * @return list of component types which supports painters
     */
    public static List<SupportedComponent> getPainterSupportedComponents ()
    {
        final List<SupportedComponent> supportedComponents = new ArrayList<SupportedComponent> ();
        for ( final SupportedComponent sc : SupportedComponent.values () )
        {
            if ( sc.supportsPainters () )
            {
                supportedComponents.add ( sc );
            }
        }
        return supportedComponents;
    }
}