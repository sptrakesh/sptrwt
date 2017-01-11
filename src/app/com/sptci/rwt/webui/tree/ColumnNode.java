package com.sptci.rwt.webui.tree;

import com.sptci.rwt.ColumnMetaData;

/**
 * A {@link echopointng.tree.TreeNode} that represents a column in the
 * active database connection.  This node uses a {@link ColumnMetaData}
 * object as its model.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-28
 * @version $Id: ColumnNode.java 3563 2007-10-06 00:34:53Z rakesh $
 */
public class ColumnNode extends LeafNode<ColumnMetaData>
{
  /**
   * Create a new tree node using the specified metadata object.  The
   * object represents a column in the database.
   *
   * @param metadata The metadata object to use as the model for this node.
   */
  public ColumnNode( final ColumnMetaData metadata )
  {
    super( metadata );
  }
}
