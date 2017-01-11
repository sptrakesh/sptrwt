package com.sptci.rwt.webui.tree;

import com.sptci.rwt.TriggerMetaData;

/**
 * A {@link echopointng.tree.TreeNode} that represents a trigger in the
 * active database connection.  This node uses a {@link TriggerMetaData}
 * object as its model.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-28
 * @version $Id: TriggerNode.java 3563 2007-10-06 00:34:53Z rakesh $
 */
public class TriggerNode extends LeafNode<TriggerMetaData>
{
  /**
   * Create a new tree node using the specified metadata object.  The
   * object represents a trigger in the database.
   *
   * @param metadata The metadata object to use as the model for this node.
   */
  public TriggerNode( final TriggerMetaData metadata )
  {
    super( metadata );
  }
}
