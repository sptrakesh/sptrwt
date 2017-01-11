package com.sptci.rwt.webui.tree;

import com.sptci.rwt.ViewMetaData;

/**
 * A {@link echopointng.tree.TreeNode} that represents a view in the
 * active database connection.  This node uses a {@link ViewMetaData}
 * object as its model.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-28
 * @version $Id: ViewNode.java 3576 2007-10-08 04:00:26Z rakesh $
 */
public class ViewNode extends AbstractNode<ViewMetaData>
{
  /**
   * Create a new tree node using the specified metadata object.  The
   * object represents a view in the database.
   *
   * @param metadata The metadata object to use as the model for this node.
   */
  public ViewNode( final ViewMetaData metadata )
  {
    super( metadata );
  }

  /**
   * Create the child nodes for this node.  Child nodes are instances of
   * {@link ContainerNode}.
   *
   * @see ColumnsNode
   */
  protected void createChildren()
  {
    initialised = true;
    add( new ColumnsNode( getUserObject() ) );
  }
}
