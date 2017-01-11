package com.sptci.rwt.webui.tree;

import com.sptci.rwt.PrimaryKeyMetaData;

/**
 * A {@link echopointng.tree.TreeNode} that represents the primary key
 * columns for a table in the active database connection.  This node uses
 * a {@link PrimaryKeyMetaData} object as its model.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-29
 * @version $Id: PrimaryKeyNode.java 3563 2007-10-06 00:34:53Z rakesh $
 */
public class PrimaryKeyNode extends LeafNode<PrimaryKeyMetaData>
{
  /**
   * Create a new tree node using the specified metadata object.  The
   * object represents a column in the database.
   *
   * @param metadata The metadata object to use as the model for this node.
   */
  public PrimaryKeyNode( final PrimaryKeyMetaData metadata )
  {
    super( metadata );
  }
}
