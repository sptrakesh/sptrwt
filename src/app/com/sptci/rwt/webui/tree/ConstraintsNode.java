package com.sptci.rwt.webui.tree;

import com.sptci.rwt.TableMetaData;

/**
 * A {@link echopointng.tree.TreeNode} that contains nodes that represent
 * the various types of constraints defined on tables.
 * This node uses a {@link TableMetaData} object as its model.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-28
 * @version $Id: ConstraintsNode.java 3576 2007-10-08 04:00:26Z rakesh $
 */
public class ConstraintsNode extends ContainerNode<TableMetaData>
{
  /**
   * Create a new tree node using the specified metadata object.  The
   * object represents a table in the database.
   *
   * @param metadata The metadata object to use as the model for this node.
   */
  public ConstraintsNode( final TableMetaData metadata )
  {
    super( metadata );
  }

  /**
   * Create the child nodes for this node.  Child nodes are instances of
   * {@link ContainerNode}.
   *
   * @see PrimaryKeysNode
   * @see ForeignKeysNode
   */
  protected void createChildren()
  {
    initialised = true;
    add( new PrimaryKeysNode( metadata ) );
    add( new ForeignKeysNode( metadata ) );
  }
}
