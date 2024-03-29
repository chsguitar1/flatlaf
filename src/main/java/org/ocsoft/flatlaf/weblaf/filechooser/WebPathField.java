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

package org.ocsoft.flatlaf.weblaf.filechooser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JWindow;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.filechooser.FileSystemView;

import org.ocsoft.flatlaf.ResourceLoader;
import org.ocsoft.flatlaf.core.constants.FlatLafFileFilters;
import org.ocsoft.flatlaf.extended.drag.FileDragAndDropHandler;
import org.ocsoft.flatlaf.extended.layout.HorizontalFlowLayout;
import org.ocsoft.flatlaf.managers.focus.DefaultFocusTracker;
import org.ocsoft.flatlaf.managers.focus.FocusManager;
import org.ocsoft.flatlaf.managers.hotkey.Hotkey;
import org.ocsoft.flatlaf.managers.hotkey.HotkeyManager;
import org.ocsoft.flatlaf.managers.hotkey.HotkeyRunnable;
import org.ocsoft.flatlaf.utils.SwingUtils;
import org.ocsoft.flatlaf.utils.collection.CollectionUtils;
import org.ocsoft.flatlaf.utils.file.FileUtils;
import org.ocsoft.flatlaf.utils.filefilter.AbstractFileFilter;
import org.ocsoft.flatlaf.utils.system.FlatLafSystemUtils;
import org.ocsoft.flatlaf.weblaf.FlatLafStyleConstants;
import org.ocsoft.flatlaf.weblaf.button.WebButton;
import org.ocsoft.flatlaf.weblaf.button.WebToggleButton;
import org.ocsoft.flatlaf.weblaf.list.WebList;
import org.ocsoft.flatlaf.weblaf.list.WebListCellRenderer;
import org.ocsoft.flatlaf.weblaf.menu.WebMenuItem;
import org.ocsoft.flatlaf.weblaf.menu.WebPopupMenu;
import org.ocsoft.flatlaf.weblaf.panel.WebPanel;
import org.ocsoft.flatlaf.weblaf.scroll.WebScrollPane;
import org.ocsoft.flatlaf.weblaf.text.WebTextField;

/**
 * User: mgarin Date: 05.07.11 Time: 18:20
 */

public class WebPathField extends WebPanel {
    /**
     * Used icons.
     */
    protected static final ImageIcon down = ResourceLoader.loadIcon("filechooser/down.png");
    protected static final ImageIcon left = ResourceLoader.loadIcon("filechooser/left.png");
    protected static final ImageIcon right = ResourceLoader.loadIcon("filechooser/right.png");
    
    protected static final String FILE_ICON = "fileIcon";
    
    protected List<PathFieldListener> listeners = new ArrayList<PathFieldListener>(
            1);
    
    protected boolean focusOwner = false;
    
    protected static FileSystemView fsv = FileSystemView.getFileSystemView();
    
    protected AbstractFileFilter fileFilter = FlatLafFileFilters.DIRECTORIES_FILTER;
    
    protected int preferredWidth = -1;
    protected boolean filesDropEnabled = true;
    
    protected File selectedPath;
    
    protected boolean autocompleteEnabled = true;
    protected JWindow autocompleteDialog = null;
    
    protected WebPanel contentPanel;
    
    protected WebTextField pathField;
    protected FocusAdapter pathFocusListener;
    
    protected WebButton myComputer = null;
    
    protected int rootsMenuItemsCount = 0;
    protected WebPopupMenu rootsMenu = null;
    protected WebToggleButton rootsArrowButton = null;
    
    protected final DefaultFocusTracker focusTracker;
    
    public WebPathField() {
        this(FileUtils.getDiskRoots()[0]);
    }
    
    public WebPathField(final String path) {
        this(new File(path));
    }
    
    public WebPathField(final File path) {
        super(true);
        
        // Default settings
        setMargin(-1);
        setOpaque(false);
        setPaintFocus(true);
        setWebColoredBackground(false);
        setBackground(Color.WHITE);
        
        // Files TransferHandler
        setTransferHandler(new FileDragAndDropHandler() {
            @Override
            public boolean isDropEnabled() {
                return filesDropEnabled;
            }
            
            @Override
            public boolean filesDropped(final List<File> files) {
                // Setting dragged files
                final FileFilter filter = getFileFilter();
                for (final File file : files) {
                    final File actualFile = file.isDirectory() ? file : file
                            .getParentFile();
                    if (filter == null || filter.accept(actualFile)) {
                        folderSelected(actualFile);
                        return true;
                    }
                }
                return false;
            }
        });
        
        contentPanel = new WebPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new HorizontalFlowLayout(0, true));
        add(contentPanel, BorderLayout.CENTER);
        
        // WebImage editImage = new WebImage ( WebPathField.class,
        // "icons/edit.png" );
        // editImage.setCursor ( Cursor.getPredefinedCursor ( Cursor.TEXT_CURSOR
        // ) );
        // editImage.addMouseListener ( new MouseAdapter ()
        // {
        // public void mousePressed ( MouseEvent e )
        // {
        // if ( SwingUtilities.isLeftMouseButton ( e ) )
        // {
        // startEditing ();
        // }
        // }
        // } );
        // add ( editImage,BorderLayout.EAST );
        
        pathField = WebTextField.createWebTextField(false);
        pathField.setMargin(2);
        pathField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (autocompleteDialog == null
                        || !autocompleteDialog.isVisible()) {
                    if (pathField.getText().trim().equals("")) {
                        folderSelected(null);
                    } else {
                        final File choosenPath = new File(pathField.getText());
                        if (choosenPath.exists() && choosenPath.isDirectory()) {
                            folderSelected(choosenPath);
                        } else {
                            updatePath();
                        }
                    }
                    WebPathField.this.transferFocus();
                }
            }
        });
        pathField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                if (autocompleteDialog == null
                        || !autocompleteDialog.isVisible()) {
                    if (Hotkey.ESCAPE.isTriggered(e)) {
                        if (selectedPath == null
                                && pathField.getText().trim().equals("")
                                || selectedPath != null
                                && getProperSelectedPath().equals(
                                        pathField.getText())) {
                            updatePath();
                            WebPathField.this.transferFocus();
                        } else {
                            pathField.setText(getProperSelectedPath());
                        }
                    }
                }
            }
        });
        
        pathFocusListener = new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent e) {
                if (selectedPath == null
                        && pathField.getText().trim().equals("")
                        || selectedPath != null
                        && getProperSelectedPath().equals(pathField.getText())) {
                    updatePath();
                }
            }
        };
        
        // Autocomplete dialog listener
        pathField.addCaretListener(new CaretListener() {
            private WebList list = null;
            private WebScrollPane listScroll;
            
            @Override
            public void caretUpdate(final CaretEvent e) {
                if (!autocompleteEnabled || !pathField.isVisible()
                        || !pathField.isShowing()) {
                    if (autocompleteDialog != null
                            && autocompleteDialog.isVisible()) {
                        hideDialog();
                    }
                    return;
                }
                
                if (autocompleteDialog == null) {
                    autocompleteDialog = new JWindow(SwingUtils
                            .getWindowAncestor(WebPathField.this));
                    autocompleteDialog.getContentPane().setLayout(
                            new BorderLayout());
                    autocompleteDialog.setFocusable(false);
                    
                    SwingUtils.getWindowAncestor(WebPathField.this)
                            .addComponentListener(new ComponentAdapter() {
                                @Override
                                public void componentMoved(
                                        final ComponentEvent e) {
                                    hideDialog();
                                }
                                
                                @Override
                                public void componentResized(
                                        final ComponentEvent e) {
                                    hideDialog();
                                }
                            });
                    
                    list = new WebList();
                    list.setFocusable(false);
                    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    list.setRolloverSelectionEnabled(true);
                    list.setCellRenderer(new WebListCellRenderer() {
                        @Override
                        public Component getListCellRendererComponent(
                                final JList list, final Object value,
                                final int index, final boolean isSelected,
                                final boolean cellHasFocus) {
                            final JLabel renderer = (JLabel) super
                                    .getListCellRendererComponent(list, value,
                                            index, isSelected, cellHasFocus);
                            renderer.setIcon(FileUtils
                                    .getFileIcon((File) value));
                            renderer.setText(FileUtils
                                    .getDisplayFileName((File) value));
                            return renderer;
                        }
                    });
                    list.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(final MouseEvent e) {
                            final int index = list.getUI().locationToIndex(
                                    list, e.getPoint());
                            if (SwingUtilities.isLeftMouseButton(e)
                                    && index != -1) {
                                setSelectedPath((File) list.getModel()
                                        .getElementAt(index));
                            }
                        }
                    });
                    list.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(final KeyEvent e) {
                            if (Hotkey.ENTER.isTriggered(e)) {
                                setSelectedPath((File) list.getSelectedValue());
                            }
                        }
                    });
                    listScroll = new WebScrollPane(list);
                    listScroll.setShadeWidth(0);
                    // listScroll.setHorizontalScrollBarPolicy (
                    // WebScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
                    autocompleteDialog.getContentPane().add(listScroll,
                            BorderLayout.CENTER);
                    
                    pathField.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(final KeyEvent e) {
                            if (autocompleteDialog.isShowing()
                                    && list.getModel().getSize() > 0) {
                                if (list.getSelectedIndex() != -1) {
                                    if (Hotkey.ESCAPE.isTriggered(e)
                                            || Hotkey.F2.isTriggered(e)
                                            || Hotkey.CTRL_ENTER.isTriggered(e)) {
                                        hideDialog();
                                    } else if (Hotkey.ENTER.isTriggered(e)) {
                                        setSelectedPath((File) list
                                                .getSelectedValue());
                                    } else if (Hotkey.UP.isTriggered(e)) {
                                        if (list.getSelectedIndex() == 0) {
                                            list.setSelectedIndex(list
                                                    .getModel().getSize() - 1);
                                            scrollToSelected();
                                        } else {
                                            list.setSelectedIndex(list
                                                    .getSelectedIndex() - 1);
                                            scrollToSelected();
                                        }
                                    } else if (Hotkey.DOWN.isTriggered(e)) {
                                        if (list.getSelectedIndex() == list
                                                .getModel().getSize() - 1) {
                                            list.setSelectedIndex(0);
                                            scrollToSelected();
                                        } else {
                                            list.setSelectedIndex(list
                                                    .getSelectedIndex() + 1);
                                            scrollToSelected();
                                        }
                                    }
                                } else {
                                    list.setSelectedIndex(0);
                                    scrollToSelected();
                                }
                            }
                        }
                        
                        private void scrollToSelected() {
                            list.scrollRectToVisible(list.getUI()
                                    .getCellBounds(list,
                                            list.getSelectedIndex(),
                                            list.getSelectedIndex()));
                        }
                    });
                    
                    pathField.addFocusListener(new FocusAdapter() {
                        @Override
                        public void focusLost(final FocusEvent e) {
                            hideDialog();
                        }
                    });
                }
                
                // new Thread ( new Runnable ()
                // {
                // public void run ()
                // {
                // Taking only the part till the caret
                final String t = pathField.getText().substring(0,
                        pathField.getCaretPosition());
                
                // Retrieving parent path
                int beginIndex = t.lastIndexOf(File.separator);
                beginIndex = beginIndex != -1 ? beginIndex + 1 : 0;
                
                // Parent file
                final String parentPath = t.substring(0, beginIndex);
                final File parent = parentPath.trim().equals("") ? null
                        : new File(parentPath);
                
                final List<File> similar = getSimilarFileChilds(parent,
                        t.substring(beginIndex));
                if (similar != null && similar.size() > 0) {
                    updateList(similar);
                } else {
                    hideDialog();
                }
                // }
                // } ).start ();
            }
            
            private void updateList(final List<File> similar) {
                SwingUtils.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        list.setModel(new AbstractListModel() {
                            @Override
                            public int getSize() {
                                return similar.size();
                            }
                            
                            @Override
                            public Object getElementAt(final int i) {
                                return similar.get(i);
                            }
                        });
                        list.setVisibleRowCount(Math.min(similar.size(), 6));
                        list.updateUI();
                        if (similar.size() > 0) {
                            list.setSelectedIndex(0);
                        }
                        
                        // Fixing window bounds
                        final Point los = pathField.getLocationOnScreen();
                        autocompleteDialog.setSize(pathField.getWidth(),
                                listScroll.getPreferredSize().height);
                        autocompleteDialog.setLocation(
                                pathField.getComponentOrientation()
                                        .isLeftToRight() ? los.x : los.x
                                        + pathField.getWidth()
                                        - autocompleteDialog.getWidth(), los.y
                                        + pathField.getHeight());
                        
                        // Showing dialog if needed
                        if (!autocompleteDialog.isShowing()) {
                            autocompleteDialog.setVisible(true);
                            WebPathField.this.transferFocus();
                        }
                    }
                });
            }
            
            private void hideDialog() {
                SwingUtils.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        autocompleteDialog.setVisible(false);
                    }
                });
            }
            
            private void setSelectedPath(final File path) {
                String text = path.getAbsolutePath();
                text = text.endsWith(File.separator) ? text : text
                        + File.separator;
                pathField.setText(text);
                pathField.setCaretPosition(text.length());
            }
        });
        
        // Edit start listeners
        contentPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(final MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    startEditing();
                }
            }
        });
        HotkeyManager.registerHotkey(WebPathField.this, WebPathField.this,
                Hotkey.F2, new HotkeyRunnable() {
                    @Override
                    public void run(final KeyEvent e) {
                        startEditing();
                    }
                }, true);
        
        // Resize listener
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                if (!pathField.isShowing()) {
                    updatePath();
                }
            }
        });
        
        // Focus listener
        focusTracker = new DefaultFocusTracker() {
            @Override
            public void focusChanged(final boolean focused) {
                focusOwner = focused;
                WebPathField.this.repaint();
            }
        };
        FocusManager.addFocusTracker(WebPathField.this, focusTracker);
        
        // Updatin initial path
        updatePath(path);
    }
    
    protected void startEditing() {
        if (!pathField.isFocusOwner()) {
            // Updating path field preferred size
            pathField.setPreferredSize(new Dimension(1, contentPanel
                    .getHeight()));
            
            // Clearing content
            contentPanel.removeAll();
            
            // Updating path text
            if (selectedPath != null) {
                pathField.setText(getProperSelectedPath());
            } else {
                pathField.setText("");
            }
            pathField.selectAll();
            
            // Adding field as main component
            contentPanel.add(pathField);
            contentPanel.revalidate();
            contentPanel.repaint();
            
            // Focusing field
            WebPathField.this.transferFocus();
            pathField.addFocusListener(pathFocusListener);
        }
    }
    
    protected String getProperSelectedPath() {
        String path = selectedPath.getAbsolutePath();
        path = path.endsWith(File.separator) ? path : path + File.separator;
        return path;
    }
    
    public boolean isEditing() {
        return pathField.isFocusOwner();
    }
    
    public boolean isAutocompleteEnabled() {
        return autocompleteEnabled;
    }
    
    public void setAutocompleteEnabled(final boolean autocompleteEnabled) {
        this.autocompleteEnabled = autocompleteEnabled;
    }
    
    public AbstractFileFilter getFileFilter() {
        return fileFilter;
    }
    
    public void setFileFilter(final AbstractFileFilter fileFilter) {
        setFileFilter(fileFilter, true);
    }
    
    public void setFileFilter(final AbstractFileFilter fileFilter,
            final boolean updatePath) {
        this.fileFilter = fileFilter;
        if (updatePath) {
            updatePath();
        }
    }
    
    public boolean isFilesDropEnabled() {
        return filesDropEnabled;
    }
    
    public void setFilesDropEnabled(final boolean filesDropEnabled) {
        this.filesDropEnabled = filesDropEnabled;
    }
    
    public File getSelectedPath() {
        return selectedPath;
    }
    
    public void setSelectedPath(final File selectedPath) {
        updatePath(selectedPath);
    }
    
    public WebTextField getPathField() {
        return pathField;
    }
    
    public void updatePath() {
        updatePath(selectedPath);
    }
    
    protected synchronized void updatePath(final File path) {
        // todo check if path is proper (filter/hidden)
        
        // todo Save focused state properly
        // Saving focus state
        // boolean hadFocus = focusOwner;
        
        // Saving new path
        selectedPath = path;
        
        // Clearing old path components
        pathField.removeFocusListener(pathFocusListener);
        contentPanel.removeAll();
        
        // Determining oriention
        final boolean ltr = WebPathField.this.getComponentOrientation()
                .isLeftToRight();
        
        // Determining root
        if (FlatLafSystemUtils.isWindows()) {
            final WebButton computerButton = getMyComputer();
            contentPanel.add(computerButton);
            contentPanel.add(getRootsArrowButton(ltr));
        }
        
        if (selectedPath != null) {
            // Creating parents list
            File folder = new File(selectedPath.getAbsolutePath());
            final List<File> parents = new ArrayList<File>();
            parents.add(0, folder);
            while (folder.getParent() != null) {
                folder = folder.getParentFile();
                parents.add(0, folder);
            }
            
            // Adding path buttons
            boolean first = true;
            for (final File file : parents) {
                final WebButton wb = new WebButton();
                wb.setRound(!FlatLafSystemUtils.isWindows() && first ? FlatLafStyleConstants.smallRound
                        : 0);
                wb.setShadeWidth(0);
                wb.setLeftRightSpacing(0);
                wb.setRolloverDecoratedOnly(true);
                wb.setRolloverDarkBorderOnly(false);
                wb.setFocusable(false);
                if (!FlatLafSystemUtils.isWindows() && first) {
                    wb.setIcon(FileUtils.getMyComputerIcon());
                    wb.putClientProperty(FILE_ICON,
                            FileUtils.getMyComputerIcon());
                } else {
                    wb.setText(fsv.getSystemDisplayName(file));
                    wb.putClientProperty(FILE_ICON,
                            FileUtils.getFileIcon(file, false));
                }
                wb.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        folderSelected(file);
                    }
                });
                contentPanel.add(wb);
                
                int childsCount = 0;
                final WebPopupMenu menu = new WebPopupMenu();
                final File[] files = FileUtils.sortFiles(getFileChilds(file));
                if (files != null) {
                    for (final File root : files) {
                        if (root.isDirectory()) {
                            final WebMenuItem menuItem = new WebMenuItem(
                                    FileUtils.getDisplayFileName(root));
                            menuItem.setIcon(FileUtils.getFileIcon(root, false));
                            menuItem.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(final ActionEvent e) {
                                    folderSelected(root);
                                }
                            });
                            menu.add(menuItem);
                            childsCount++;
                        }
                    }
                }
                if (!FlatLafSystemUtils.isWindows() && first) {
                    setRootsMenu(menu, childsCount);
                }
                
                final WebToggleButton childs = new WebToggleButton();
                childs.setIcon(ltr ? right : left);
                childs.setSelectedIcon(down);
                childs.setShadeToggleIcon(false);
                childs.setRound(0);
                childs.setShadeWidth(0);
                childs.setRolloverDecoratedOnly(true);
                childs.setRolloverDarkBorderOnly(false);
                childs.setFocusable(false);
                childs.setComponentPopupMenu(menu);
                childs.setMargin(0);
                childs.setLeftRightSpacing(0);
                childs.setEnabled(childsCount > 0);
                childs.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        // todo Apply orientation globally on change, not here
                        WebPathField.this.transferFocus();
                        SwingUtils.applyOrientation(menu);
                        menu.showBelowMiddle(childs);
                    }
                });
                contentPanel.add(childs);
                
                menu.addPopupMenuListener(new PopupMenuListener() {
                    @Override
                    public void popupMenuWillBecomeVisible(
                            final PopupMenuEvent e) {
                        //
                    }
                    
                    @Override
                    public void popupMenuWillBecomeInvisible(
                            final PopupMenuEvent e) {
                        childs.setSelected(false);
                    }
                    
                    @Override
                    public void popupMenuCanceled(final PopupMenuEvent e) {
                        childs.setSelected(false);
                    }
                });
                
                first = false;
            }
        }
        
        // Filling space
        contentPanel.add(new JLabel());
        
        // Shortening long elemets
        if (!FlatLafSystemUtils.isWindows()) {
            while (getRootsMenu().getComponentCount() > getRootsMenuItemsCount()) {
                getRootsMenu().remove(0);
            }
        }
        if (canShortenPath()) {
            getRootsMenu().addSeparator(0);
        }
        while (canShortenPath()) {
            // Andding menu element
            final WebButton wb = (WebButton) contentPanel.getComponent(2);
            final WebMenuItem menuItem = new WebMenuItem();
            menuItem.setIcon((Icon) wb.getClientProperty(FILE_ICON));
            menuItem.setText(wb.getText());
            menuItem.addActionListener(wb.getActionListeners()[0]);
            getRootsMenu().add(menuItem, 0);
            
            // Removing hidden path and menu buttons from panel
            contentPanel.remove(2);
            contentPanel.remove(2);
        }
        
        // Updating pane
        revalidate();
        repaint();
    }
    
    protected List<File> getSimilarFileChilds(final File file,
            final String namePart) {
        final String searchText = namePart.toLowerCase();
        final File[] childs = getFileChilds(file);
        final List<File> similar = new ArrayList<File>();
        if (childs != null) {
            for (final File child : childs) {
                if (child.getName().toLowerCase().contains(searchText)) {
                    similar.add(child);
                }
            }
        }
        return similar;
    }
    
    protected File[] getFileChilds(final File file) {
        return file != null ? file.listFiles(fileFilter) : FileUtils
                .getDiskRoots();
    }
    
    protected boolean canShortenPath() {
        return contentPanel.getPreferredSize().width > contentPanel.getWidth()
                && contentPanel.getComponentCount() > 5;
    }
    
    protected WebButton getMyComputer() {
        if (myComputer == null) {
            myComputer = WebButton.createIconWebButton(FileUtils
                    .getMyComputerIcon());
            myComputer.setRound(getRound());
            myComputer.setShadeWidth(0);
            myComputer.setLeftRightSpacing(0);
            myComputer.setRolloverDecoratedOnly(true);
            myComputer.setRolloverDarkBorderOnly(false);
            myComputer.setDrawFocus(false);
            myComputer.setDrawRight(false);
            myComputer.setDrawRightLine(true);
            myComputer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    folderSelected(null);
                }
            });
        }
        return myComputer;
    }
    
    public WebPopupMenu getRootsMenu() {
        return rootsMenu;
    }
    
    public int getRootsMenuItemsCount() {
        return rootsMenuItemsCount;
    }
    
    public void setRootsMenu(final WebPopupMenu rootsMenu, final int childsCount) {
        this.rootsMenu = rootsMenu;
        this.rootsMenuItemsCount = childsCount;
    }
    
    protected WebToggleButton getRootsArrowButton(final boolean ltr) {
        if (rootsArrowButton == null) {
            rootsMenu = new WebPopupMenu();
            
            final File[] rootFiles = FileUtils.getDiskRoots();
            for (final File root : FileUtils.sortFiles(rootFiles)) {
                final WebMenuItem menuItem = new WebMenuItem(
                        FileUtils.getDisplayFileName(root));
                menuItem.setIcon(FileUtils.getFileIcon(root, false));
                menuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        folderSelected(root);
                    }
                });
                rootsMenu.add(menuItem);
                rootsMenuItemsCount++;
            }
            
            rootsArrowButton = new WebToggleButton();
            rootsArrowButton.setIcon(ltr ? right : left);
            rootsArrowButton.setSelectedIcon(down);
            rootsArrowButton.setShadeToggleIcon(false);
            rootsArrowButton.setRound(0);
            rootsArrowButton.setShadeWidth(0);
            rootsArrowButton.setRolloverDecoratedOnly(true);
            rootsArrowButton.setRolloverDarkBorderOnly(false);
            rootsArrowButton.setFocusable(false);
            rootsArrowButton.setMargin(0);
            rootsArrowButton.setLeftRightSpacing(0);
            rootsArrowButton.setComponentPopupMenu(rootsMenu);
            rootsArrowButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    WebPathField.this.transferFocus();
                    SwingUtils.applyOrientation(rootsMenu);
                    rootsMenu.showBelowMiddle(rootsArrowButton);
                }
            });
            
            rootsMenu.addPopupMenuListener(new PopupMenuListener() {
                @Override
                public void popupMenuWillBecomeVisible(final PopupMenuEvent e) {
                    
                }
                
                @Override
                public void popupMenuWillBecomeInvisible(final PopupMenuEvent e) {
                    rootsArrowButton.setSelected(false);
                }
                
                @Override
                public void popupMenuCanceled(final PopupMenuEvent e) {
                    rootsArrowButton.setSelected(false);
                }
            });
        } else {
            rootsArrowButton.setIcon(ltr ? right : left);
        }
        while (rootsMenu.getComponentCount() > rootsMenuItemsCount) {
            rootsMenu.remove(0);
        }
        return rootsArrowButton;
    }
    
    protected void folderSelected(File folder) {
        // Normalize file
        folder = FileUtils.normalize(folder);
        
        // Update visual path
        updatePath(folder);
        
        // Notify about selection change
        fireDirectoryChanged(folder);
        
        // Requesting focus as it is internal change from internal event
        WebPathField.this.transferFocus();
    }
    
    public void addPathFieldListener(final PathFieldListener listener) {
        listeners.add(listener);
    }
    
    public void removePathFieldListener(final PathFieldListener listener) {
        listeners.remove(listener);
    }
    
    protected void fireDirectoryChanged(final File newDirectory) {
        for (final PathFieldListener listener : CollectionUtils.copy(listeners)) {
            listener.directoryChanged(newDirectory);
        }
    }
    
    @Override
    public void applyComponentOrientation(final ComponentOrientation o) {
        super.applyComponentOrientation(o);
        updatePath();
    }
}
