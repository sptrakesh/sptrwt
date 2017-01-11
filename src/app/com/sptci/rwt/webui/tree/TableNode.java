package com.sptci.rwt.webui.tree;

import com.sptci.rwt.TableMetaData;

/**
 * A {@link echopointng.tree.TreeNode} that represents a table in the
 * active database connection.  This node uses a {@link TableMetaData}
 * object as its model.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-28
 * @version $Id: TableNode.java 3563 2007-10-06 00:34:53Z rakesh $
 */
public class TableNode extends AbstractNode<TableMetaData>
{
  /**
   * Create a new tree node using the specified metadata object.  The
   * object represents a table in the database.
   *
   * @param metadata The metadata object to use as the model for this node.
   */
  public TableNode( final TableMetaData metadata )
  {
    super( metadata );
  }

  /**
   * Create the child nodes for this node.  Child nodes are instances of
   * {@link ContainerNode}.
   *
   * @see ColumnsNode
   * @see ConstraintsNode
   * @see IndicesNode
   * @see TriggersNode
   */
  protected void createChildren()
  {
    initialised = true;
    add( new ColumnsNode( getUserObject() ) );
    add( new ConstraintsNode( getUserObject() ) );
    add( new IndicesNode( getUserObject() ) );
    add( new TriggersNode<TableMetaData>( getUserObject() ) );
  }
}
