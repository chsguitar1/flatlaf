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

package org.ocsoft.flatlaf.extended.tree;

import javax.swing.*;

import org.ocsoft.flatlaf.laf.tree.UniqueNode;

import java.util.List;

/**
 * Custom TransferHandler for WebAsyncTree that provides a quick and convenient way to implement nodes DnD.
 *
 * @author Mikle Garin
 */

public abstract class ExTreeTransferHandler<N extends UniqueNode, T extends WebExTree<N>>
        extends AbstractTreeTransferHandler<N, T, ExTreeModel<N>>
{
    /**
     * {@inheritDoc}
     */
    @Override
    protected void removeTreeNodes ( final T tree, final List<N> nodesToRemove )
    {
        tree.removeNodes ( nodesToRemove );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean prepareDropOperation ( final TransferSupport support, final List<N> nodes, final int dropIndex, final N parent,
                                             final T tree, final ExTreeModel<N> model )
    {
        return performDropOperation ( nodes, parent, tree, model, getAdjustedDropIndex ( dropIndex, support.getDropAction (), parent ) );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean performDropOperation ( final List<N> nodes, final N parent, final T tree, final ExTreeModel<N> model,
                                             final int index )
    {
        // This operation should be performed in EDT later to allow drop operation get completed in source TransferHandler first
        // Otherwise new nodes will be added into the tree before old ones are removed which is bad if it is the same tree
        // This is meaningful for D&D opearation within one tree, for other situations its meaningless but doesn't cause any problems
        SwingUtilities.invokeLater ( new Runnable ()
        {
            @Override
            public void run ()
            {
                // Adding data to model
                model.insertNodesInto ( nodes, parent, index );

                // Expanding nodes after drop operation
                if ( expandSingleNode && nodes.size () == 1 )
                {
                    tree.expandNode ( nodes.get ( 0 ) );
                }
                else if ( expandMultiplyNodes )
                {
                    for ( final N node : nodes )
                    {
                        tree.expandNode ( node );
                    }
                }

                // Selecting inserted nodes
                tree.setSelectedNodes ( nodes );

                // Informing about drop
                nodesDropped ( nodes, parent, tree, model, index );
            }
        } );
        return true;
    }
}