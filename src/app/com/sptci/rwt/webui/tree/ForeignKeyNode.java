package com.sptci.rwt.webui.tree;

import com.sptci.rwt.ForeignKeyMetaData;

/**
 * A {@link echopointng.tree.TreeNode} that represents the foreign key
 * columns for a table in the active database connection.  This node uses
 * a {@link ForeignKeyMetaData} object as its model.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-07
 * @version $Id: ForeignKeyNode.java 3576 2007-10-08 04:00:26Z rakesh $
 */
public class ForeignKeyNode extends LeafNode<ForeignKeyMetaData>
{
  /**
   * Create a new tree node using the specified metadata object.  The
   * object represents a column in the database.
   *
   * @param metadata The metadata object to use as the model for this node.
   */
  public ForeignKeyNode( final ForeignKeyMetaData metadata )
  {
    super( metadata );
  }
}
