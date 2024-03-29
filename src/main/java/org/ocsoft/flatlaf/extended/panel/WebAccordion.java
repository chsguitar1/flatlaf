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

package org.ocsoft.flatlaf.extended.panel;

import java.awt.Component;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import org.ocsoft.flatlaf.extended.layout.AccordionLayout;
import org.ocsoft.flatlaf.managers.settings.DefaultValue;
import org.ocsoft.flatlaf.managers.settings.SettingsManager;
import org.ocsoft.flatlaf.managers.settings.SettingsMethods;
import org.ocsoft.flatlaf.managers.settings.SettingsProcessor;
import org.ocsoft.flatlaf.utils.collection.CollectionUtils;
import org.ocsoft.flatlaf.utils.swing.DataProvider;
import org.ocsoft.flatlaf.weblaf.panel.WebPanel;
import org.ocsoft.flatlaf.weblaf.panel.WebPanelStyle;

/**
 * Accordion groups separate collapsible panes into a single component. It also
 * has a few useful features like unified styling and selection mode.
 *
 * @author Mikle Garin
 */

public class WebAccordion extends WebPanel implements SwingConstants,
        SettingsMethods {
    /**
     * Whether animate transition between states or not.
     */
    protected boolean animate = WebAccordionStyle.animate;
    
    /**
     * Accordion style.
     */
    protected AccordionStyle accordionStyle = WebAccordionStyle.accordionStyle;
    
    /**
     * Accordion orientation.
     */
    protected int orientation = WebAccordionStyle.orientation;
    
    /**
     * Collapsed state icon.
     */
    protected ImageIcon expandIcon = WebAccordionStyle.expandIcon;
    
    /**
     * Expanded state icon.
     */
    protected ImageIcon collapseIcon = WebAccordionStyle.collapseIcon;
    
    /**
     * Whether accordion must fill all available space with expanded panes or
     * not.
     */
    protected boolean fillSpace = WebAccordionStyle.fillSpace;
    
    /**
     * Whether multiply expanded panes are allowed or not.
     */
    protected boolean multiplySelectionAllowed = WebAccordionStyle.multiplySelectionAllowed;
    
    /**
     * Gap between panes for separated accordion style.
     */
    protected int gap = WebAccordionStyle.gap;
    
    /**
     * Accordion collapsible pane listeners.
     */
    protected List<AccordionListener> listeners = new ArrayList<AccordionListener>(
            1);
    
    /**
     * Accordion collapsible panes.
     */
    protected List<WebCollapsiblePane> panes = new ArrayList<WebCollapsiblePane>();
    
    /**
     * Accordion collapsible pane state listeners. These listeners required for
     * some of accordion features.
     */
    protected List<CollapsiblePaneListener> stateListeners = new ArrayList<CollapsiblePaneListener>();
    
    /**
     * Index of last expanded collapsible pane.
     */
    protected WebCollapsiblePane lastExpanded = null;
    
    /**
     * Constructs empty accordion with default style.
     */
    public WebAccordion() {
        super();
        initializeDefaultSettings(WebAccordionStyle.accordionStyle);
    }
    
    /**
     * Constructs empty accordion with the specified style.
     *
     * @param style
     *            accordion style
     */
    public WebAccordion(final AccordionStyle style) {
        super();
        initializeDefaultSettings(style);
    }
    
    /**
     * Initializes default accordion settings.
     */
    protected void initializeDefaultSettings(final AccordionStyle style) {
        setPaintFocus(true);
        setWebColoredBackground(false);
        setLayout(new AccordionLayout(this));
        setAccordionStyle(style);
    }
    
    /**
     * Returns whether animate transition between states or not.
     *
     * @return true if transitions between states should be animated, false
     *         otherwise
     */
    public boolean isAnimate() {
        return animate;
    }
    
    /**
     * Sets whether animate transition between states or not.
     *
     * @param animate
     *            whether animate transition between states or not
     */
    public void setAnimate(final boolean animate) {
        this.animate = animate;
        updatePanesAnimation();
    }
    
    /**
     * Updates collapsible panes animation property.
     */
    protected void updatePanesAnimation() {
        for (final WebCollapsiblePane pane : panes) {
            pane.setAnimate(animate);
        }
    }
    
    /**
     * Returns accordion orientation.
     *
     * @return accordion orientation
     */
    public int getOrientation() {
        return orientation;
    }
    
    /**
     * Sets accordion orientation.
     *
     * @param orientation
     *            new accordion orientation
     */
    public void setOrientation(final int orientation) {
        this.orientation = orientation;
        updatePanesBorderStyling();
    }
    
    /**
     * Returns collapsed state icon.
     *
     * @return collapsed state icon
     */
    public ImageIcon getExpandIcon() {
        return expandIcon;
    }
    
    /**
     * Sets collapsed state icon.
     *
     * @param expandIcon
     *            new collapsed state icon
     */
    public void setExpandIcon(final ImageIcon expandIcon) {
        this.expandIcon = expandIcon;
        updatePaneIcons();
    }
    
    /**
     * Returns expanded state icon.
     *
     * @return expanded state icon
     */
    public ImageIcon getCollapseIcon() {
        return collapseIcon;
    }
    
    /**
     * Sets expanded state icon.
     *
     * @param collapseIcon
     *            new expanded state icon
     */
    public void setCollapseIcon(final ImageIcon collapseIcon) {
        this.collapseIcon = collapseIcon;
        updatePaneIcons();
    }
    
    /**
     * Updates collapsible pane icons.
     */
    protected void updatePaneIcons() {
        for (final WebCollapsiblePane pane : panes) {
            pane.setExpandIcon(expandIcon);
            pane.setCollapseIcon(collapseIcon);
        }
    }
    
    /**
     * Returns accordion style.
     *
     * @return accordion style
     */
    public AccordionStyle getAccordionStyle() {
        return accordionStyle;
    }
    
    /**
     * Sets accordion style.
     *
     * @param accordionStyle
     *            new accordion style
     */
    public void setAccordionStyle(final AccordionStyle accordionStyle) {
        this.accordionStyle = accordionStyle;
        updatePanesBorderStyling();
    }
    
    /**
     * Returns whether accordion must fill all available space with expanded
     * panes or not.
     *
     * @return whether accordion must fill all available space with expanded
     *         panes or not
     */
    public boolean isFillSpace() {
        return fillSpace;
    }
    
    /**
     * Sets whether accordion must fill all available space with expanded panes
     * or not.
     *
     * @param fillSpace
     *            whether accordion must fill all available space with expanded
     *            panes or not
     */
    public void setFillSpace(final boolean fillSpace) {
        this.fillSpace = fillSpace;
        revalidate();
    }
    
    /**
     * Returns whether multiply expanded panes are allowed or not.
     *
     * @return whether multiply expanded panes are allowed or not
     */
    public boolean isMultiplySelectionAllowed() {
        return multiplySelectionAllowed;
    }
    
    /**
     * Sets whether multiply expanded panes are allowed or not.
     *
     * @param multiplySelectionAllowed
     *            whether multiply expanded panes are allowed or not
     */
    public void setMultiplySelectionAllowed(
            final boolean multiplySelectionAllowed) {
        this.multiplySelectionAllowed = multiplySelectionAllowed;
        updateSelections(-1, true);
    }
    
    /**
     * Updates panes selection states.
     *
     * @param index
     *            index of the pane that will be left expanded in case multiply
     *            expanded panes are not allowed
     * @param collapse
     *            whether allow to collapse panes or not
     */
    protected void updateSelections(int index, final boolean collapse) {
        boolean changed = false;
        if (collapse) {
            if (!multiplySelectionAllowed) {
                for (int i = 0; i < panes.size(); i++) {
                    final WebCollapsiblePane pane = panes.get(i);
                    if (index == -1 && pane.isExpanded()) {
                        index = i;
                    }
                    if (index != -1 && i != index && pane.isExpanded()) {
                        changed = true;
                        pane.setExpanded(false);
                    }
                }
            }
        } else {
            if (getSelectionCount() == 0) {
                lastExpanded.setExpanded(true);
            }
        }
        
        // Notify about selection change
        if (index != -1 || changed) {
            fireSelectionChanged();
        }
    }
    
    /**
     * Returns gap between panes for separated accordion style.
     *
     * @return gap between panes for separated accordion style
     */
    public int getGap() {
        return gap;
    }
    
    /**
     * Sets gap between panes for separated accordion style.
     *
     * @param gap
     *            new gap between panes for separated accordion style
     */
    public void setGap(final int gap) {
        this.gap = gap;
        revalidate();
    }
    
    /**
     * Adds new collapsible pane into accordion with the specified title and
     * content.
     *
     * @param title
     *            collapsible pane title
     * @param content
     *            collapsible pane content
     * @return new collapsible pane
     */
    public WebCollapsiblePane addPane(final String title,
            final Component content) {
        return addPane(panes.size(), title, content);
    }
    
    /**
     * Adds new collapsible pane into accordion with the specified title and
     * content at the specified index.
     *
     * @param index
     *            collapsible pane index
     * @param title
     *            collapsible pane title
     * @param content
     *            collapsible pane content
     * @return new collapsible pane
     */
    public WebCollapsiblePane addPane(final int index, final String title,
            final Component content) {
        return addPane(index, new WebCollapsiblePane(title, content));
    }
    
    /**
     * Adds new collapsible pane into accordion with the specified icon, title
     * and content.
     *
     * @param icon
     *            collapsible pane icon
     * @param title
     *            collapsible pane title
     * @param content
     *            collapsible pane content
     * @return new collapsible pane
     */
    public WebCollapsiblePane addPane(final Icon icon, final String title,
            final Component content) {
        return addPane(panes.size(), icon, title, content);
    }
    
    /**
     * Adds new collapsible pane into accordion with the specified icon, title
     * and content at the specified index.
     *
     * @param index
     *            collapsible pane index
     * @param icon
     *            collapsible pane icon
     * @param title
     *            collapsible pane title
     * @param content
     *            collapsible pane content
     * @return new collapsible pane
     */
    public WebCollapsiblePane addPane(final int index, final Icon icon,
            final String title, final Component content) {
        return addPane(index, new WebCollapsiblePane(icon, title, content));
    }
    
    /**
     * Adds new collapsible pane into accordion with the specified title
     * component and content.
     *
     * @param title
     *            collapsible pane title component
     * @param content
     *            collapsible pane content
     * @return new collapsible pane
     */
    public WebCollapsiblePane addPane(final Component title,
            final Component content) {
        return addPane(panes.size(), title, content);
    }
    
    /**
     * Adds new collapsible pane into accordion with the specified title
     * component and content at the specified index.
     *
     * @param index
     *            collapsible pane index
     * @param title
     *            collapsible pane title component
     * @param content
     *            collapsible pane content
     * @return new collapsible pane
     */
    public WebCollapsiblePane addPane(final int index, final Component title,
            final Component content) {
        final WebCollapsiblePane pane = new WebCollapsiblePane("", content);
        pane.setTitleComponent(title);
        return addPane(index, pane);
    }
    
    /**
     * Adds collapsible pane into accordion at the specified index.
     *
     * @param index
     *            collapsible pane index
     * @param pane
     *            collapsible pane to add
     * @return added collapsible pane
     */
    protected WebCollapsiblePane addPane(final int index,
            final WebCollapsiblePane pane) {
        // Animation
        pane.setAnimate(animate);
        
        // Proper icons
        pane.setExpandIcon(expandIcon);
        pane.setCollapseIcon(collapseIcon);
        
        // Collapsing new pane if needed
        if (!multiplySelectionAllowed && isAnySelected()) {
            pane.setExpanded(false, false);
        }
        
        // State change enabler
        pane.setStateChangeHandler(new DataProvider<Boolean>() {
            @Override
            public Boolean provide() {
                // Allow action if we are expanding pane
                if (!pane.isExpanded()) {
                    return true;
                }
                
                // Allow collapse action if accordion does not fill space
                if (!fillSpace) {
                    return true;
                }
                
                // Allow collapse action if there are other available expanded
                // panes
                final int selectionCount = getSelectionCount();
                if (selectionCount > 1) {
                    return true;
                }
                
                // Allow collapse action if we can expand previously collapsed
                // pane instead of this one
                return selectionCount == 1 && lastExpanded != null
                        && lastExpanded != pane;
            }
        });
        
        // Adding new listener
        final CollapsiblePaneListener cpl = new CollapsiblePaneAdapter() {
            @Override
            public void expanding(final WebCollapsiblePane pane) {
                // Update selected panes
                updateSelections(panes.indexOf(pane), true);
            }
            
            @Override
            public void collapsing(final WebCollapsiblePane pane) {
                // This hold additional events from firing when panes collapse
                // due to panes selection mode
                if (multiplySelectionAllowed || getSelectionCount() == 0) {
                    // Update selected panes
                    updateSelections(panes.indexOf(pane), false);
                }
                
                // Update last selected
                lastExpanded = pane;
            }
        };
        pane.addCollapsiblePaneListener(cpl);
        stateListeners.add(cpl);
        
        // Adding new pane
        add(index, pane);
        panes.add(index, pane);
        
        // Updating accordion
        updatePanesBorderStyling();
        
        // Notify about selection change
        fireSelectionChanged();
        
        return pane;
    }
    
    /**
     * Removes collapsible pane from the specified index from accordion.
     *
     * @param index
     *            collapsible pane index
     */
    public void removePane(final int index) {
        removePane(panes.get(index));
    }
    
    /**
     * Removes collapsible pane from accordion.
     *
     * @param pane
     *            collapsible pane to remove
     */
    protected void removePane(final WebCollapsiblePane pane) {
        final int index = panes.indexOf(pane);
        if (index == -1) {
            return;
        }
        
        // State change enabler
        pane.setStateChangeHandler(null);
        
        // Removing pane listener
        pane.removeCollapsiblePaneListener(stateListeners.get(index));
        stateListeners.remove(index);
        
        // Removing pane
        remove(pane);
        panes.remove(index);
        
        // Updating last expanded pane
        if (pane == lastExpanded) {
            lastExpanded = null;
        }
        
        // Updating accordion
        updatePanesBorderStyling();
    }
    
    /**
     * Returns list of available collapsible panes.
     *
     * @return list of available collapsible panes
     */
    public List<WebCollapsiblePane> getPanes() {
        return CollectionUtils.copy(panes);
    }
    
    /**
     * Returns actual list of available collapsible panes. Be aware that
     * accordion might be corrupted if you modify this list directly.
     *
     * @return actual list of available collapsible panes
     */
    public List<WebCollapsiblePane> getActualPanesList() {
        return panes;
    }
    
    /**
     * Returns collapsible pane at the specified index.
     *
     * @param index
     *            collapsible pane index
     * @return collapsible pane at the specified index
     */
    public WebCollapsiblePane getPane(final int index) {
        return panes.get(index);
    }
    
    /**
     * Updates panes styling according to accordion settings.
     */
    protected void updatePanesBorderStyling() {
        final boolean united = accordionStyle.equals(AccordionStyle.united);
        final boolean separated = accordionStyle
                .equals(AccordionStyle.separated);
        final boolean hor = orientation == HORIZONTAL;
        
        // Accordion decoration
        setUndecorated(!united);
        
        // Panes decoration
        for (int i = 0; i < panes.size(); i++) {
            final WebCollapsiblePane pane = panes.get(i);
            pane.setTitlePanePostion(hor ? LEFT : TOP);
            if (separated) {
                pane.setShadeWidth(WebPanelStyle.shadeWidth);
                pane.setPaintSides(separated, separated, separated, separated);
            } else {
                pane.setShadeWidth(0);
                pane.setPaintSides(!hor && i > 0, hor && i > 0, false, false);
            }
        }
        
        // Updating accordion
        revalidate();
        repaint();
    }
    
    /**
     * Returns whether any collapsible pane is expanded or not.
     *
     * @return true if any collapsible pane is expanded, false otherwise
     */
    public boolean isAnySelected() {
        for (final WebCollapsiblePane pane : panes) {
            if (pane.isExpanded()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Returns index of the first expanded collapsible pane or -1 if none
     * expanded.
     *
     * @return index of the first expanded collapsible pane or -1 if none
     *         expanded
     */
    public int getFirstSelectedIndex() {
        for (final WebCollapsiblePane pane : panes) {
            if (pane.isExpanded()) {
                return panes.indexOf(pane);
            }
        }
        return -1;
    }
    
    /**
     * Returns amount of expanded collapsible panes.
     *
     * @return amount of expanded collapsible panes
     */
    public int getSelectionCount() {
        int count = 0;
        for (final WebCollapsiblePane pane : panes) {
            if (pane.isExpanded()) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Returns collapsible pane icon at the specified index.
     *
     * @param index
     *            collapsible pane index
     * @return collapsible pane icon at the specified index
     */
    public Icon getIconAt(final int index) {
        return panes.get(index).getIcon();
    }
    
    /**
     * Sets collapsible pane icon at the specified index.
     *
     * @param index
     *            collapsible pane index
     * @param icon
     *            new collapsible pane icon
     */
    public void setIconAt(final int index, final Icon icon) {
        panes.get(index).setIcon(icon);
    }
    
    /**
     * Returns collapsible pane title at the specified index.
     *
     * @param index
     *            collapsible pane index
     * @return collapsible pane title at the specified index
     */
    public String getTitleAt(final int index) {
        return panes.get(index).getTitle();
    }
    
    /**
     * Sets collapsible pane title at the specified index.
     *
     * @param index
     *            collapsible pane index
     * @param title
     *            new collapsible pane title
     */
    public void setTitleAt(final int index, final String title) {
        panes.get(index).setTitle(title);
    }
    
    /**
     * Returns collapsible pane title component at the specified index.
     *
     * @param index
     *            collapsible pane index
     * @return collapsible pane title component at the specified index
     */
    public Component getTitleComponentAt(final int index) {
        return panes.get(index).getTitleComponent();
    }
    
    /**
     * Sets collapsible pane title component at the specified index.
     *
     * @param index
     *            collapsible pane index
     * @param titleComponent
     *            new collapsible pane title component
     */
    public void setTitleComponentAt(final int index,
            final Component titleComponent) {
        panes.get(index).setTitleComponent(titleComponent);
    }
    
    /**
     * Returns collapsible pane content at the specified index.
     *
     * @param index
     *            collapsible pane index
     * @return collapsible pane content at the specified index
     */
    public Component getContentAt(final int index) {
        return panes.get(index).getContent();
    }
    
    /**
     * Sets collapsible pane content at the specified index.
     *
     * @param index
     *            collapsible pane index
     * @param content
     *            new collapsible pane content
     */
    public void setContentAt(final int index, final Component content) {
        panes.get(index).setContent(content);
    }
    
    /**
     * Returns collapsible pane margin at the specified index.
     *
     * @param index
     *            collapsible pane index
     * @return collapsible pane margin at the specified index
     */
    public Insets getContentMarginAt(final int index) {
        return panes.get(index).getContentMargin();
    }
    
    /**
     * Sets collapsible pane margin at the specified index.
     *
     * @param index
     *            collapsible pane index
     * @param margin
     *            new collapsible pane margin
     */
    public void setContentMarginAt(final int index, final Insets margin) {
        panes.get(index).setContentMargin(margin);
    }
    
    /**
     * Sets collapsible pane margin at the specified index.
     *
     * @param index
     *            collapsible pane index
     * @param top
     *            new collapsible pane top margin
     * @param left
     *            new collapsible pane left margin
     * @param bottom
     *            new collapsible pane bottom margin
     * @param right
     *            new collapsible pane right margin
     */
    public void setContentMarginAt(final int index, final int top,
            final int left, final int bottom, final int right) {
        setContentMarginAt(index, new Insets(top, left, bottom, right));
    }
    
    /**
     * Sets collapsible pane margin at the specified index.
     *
     * @param index
     *            collapsible pane index
     * @param margin
     *            new collapsible pane margin
     */
    public void setContentMarginAt(final int index, final int margin) {
        setContentMarginAt(index, margin, margin, margin, margin);
    }
    
    /**
     * Returns selected collapsible panes.
     *
     * @return selected collapsible panes
     */
    public List<WebCollapsiblePane> getSelectedPanes() {
        final List<WebCollapsiblePane> selectedPanes = new ArrayList<WebCollapsiblePane>();
        for (final WebCollapsiblePane pane : panes) {
            if (pane.isExpanded()) {
                selectedPanes.add(pane);
            }
        }
        return selectedPanes;
    }
    
    /**
     * Sets selected collapsible panes.
     *
     * @param selectedPanes
     *            selected collapsible panes
     */
    public void setSelectedPanes(final List<WebCollapsiblePane> selectedPanes) {
        for (final WebCollapsiblePane pane : panes) {
            pane.setExpanded(selectedPanes != null
                    && selectedPanes.contains(pane));
        }
    }
    
    /**
     * Returns selected collapsible pane indices.
     *
     * @return selected collapsible pane indices
     */
    public List<Integer> getSelectedIndices() {
        final List<Integer> selectedPanes = new ArrayList<Integer>();
        for (int i = 0; i < panes.size(); i++) {
            if (panes.get(i).isExpanded()) {
                selectedPanes.add(i);
            }
        }
        return selectedPanes;
    }
    
    /**
     * Sets selected collapsible pane indices.
     *
     * @param indices
     *            selected collapsible pane indices
     */
    public void setSelectedIndices(final List<Integer> indices) {
        for (int i = 0; i < panes.size(); i++) {
            panes.get(i).setExpanded(indices != null && indices.contains(i));
        }
    }
    
    /**
     * Returns accordion listeners.
     *
     * @return accordion listeners
     */
    public List<AccordionListener> getAccordionListeners() {
        return CollectionUtils.copy(listeners);
    }
    
    /**
     * Sets accordion listeners.
     *
     * @param listeners
     *            accordion listeners
     */
    public void setAccordionListeners(final List<AccordionListener> listeners) {
        this.listeners = listeners;
    }
    
    /**
     * Adds accordion listener.
     *
     * @param listener
     *            accordion listener to add
     */
    public void addAccordionListener(final AccordionListener listener) {
        listeners.add(listener);
    }
    
    /**
     * Removes collapsible pane listener.
     *
     * @param listener
     *            collapsible pane listener to remove
     */
    public void removeAccordionListener(final AccordionListener listener) {
        listeners.remove(listener);
    }
    
    /**
     * Notifies when collapsible pane starts to expand.
     */
    protected void fireSelectionChanged() {
        for (final AccordionListener listener : CollectionUtils.copy(listeners)) {
            listener.selectionChanged();
        }
    }
    
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
}