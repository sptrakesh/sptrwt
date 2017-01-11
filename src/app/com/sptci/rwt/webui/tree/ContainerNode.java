package com.sptci.rwt.webui.tree;

import com.sptci.echo2.Configuration;
import com.sptci.rwt.MetaData;

/**
 * An abstract {@link echopointng.tree.TreeNode} that represents {@link
 * com.sptci.rwt.MetaData} objects.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-28
 * @version $Id: ContainerNode.java 3563 2007-10-06 00:34:53Z rakesh $
 */
public abstract class ContainerNode<S extends MetaData> extends AbstractNode<S>
{
  /** The {@link com.sptci.rwt.MetaData} object associated with this node. */
  protected final S metadata;

  /**
   * Create a new tree node using the specified metadata object.  The
   * object represents a table in the database.
   *
   * @param metadata The model object for this node.
   */
  public ContainerNode( final S metadata )
  {
    super();
    setAllowsChildren( true );
    setUserObject( toString() );
    this.metadata = metadata;
  }

  /**
   * Return the display title for this node.  Over-ridden to return the
   * localised title for this node.
   *
   * @return The display title for this node.
   */
  @Override
  public String toString()
  {
    return Configuration.getString( this, "title" );
  }
}
