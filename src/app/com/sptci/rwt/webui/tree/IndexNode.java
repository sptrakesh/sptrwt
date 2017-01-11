package com.sptci.rwt.webui.tree;

import com.sptci.rwt.IndexMetaData;

/**
 * A {@link echopointng.tree.TreeNode} that represents a index in the
 * active database connection.  This node uses a {@link IndexMetaData}
 * object as its model.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-28
 * @version $Id: IndexNode.java 3563 2007-10-06 00:34:53Z rakesh $
 */
public class IndexNode extends LeafNode<IndexMetaData>
{
  /**
   * Create a new tree node using the specified metadata object.  The
   * object represents a index in the database.
   *
   * @param metadata The metadata object to use as the model for this node.
   */
  public IndexNode( final IndexMetaData metadata )
  {
    super( metadata );
  }
}
