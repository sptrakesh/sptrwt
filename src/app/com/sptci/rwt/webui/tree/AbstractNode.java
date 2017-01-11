package com.sptci.rwt.webui.tree;

import com.sptci.echo2.Application;
import com.sptci.echo2.Configuration;

import com.sptci.epng.TreeNode;
import com.sptci.rwt.MetaData;

/**
 * An abstract {@link echopointng.tree.TreeNode} that represents {@link
 * com.sptci.rwt.MetaData} objects.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-28
 * @version $Id: AbstractNode.java 38 2007-11-22 00:48:04Z sptrakesh $
 */
public abstract class AbstractNode<S extends MetaData> extends TreeNode<S>
{
  /**
   * Create a new tree node with no <code>userObject</code> specified.
   */
  protected AbstractNode()
  {
    super();
  }

  /**
   * Create a new tree node using the specified metadata object.  The
   * object represents a object in the database.
   *
   * @param userObject The model object for this node.
   */
  public AbstractNode( final S userObject )
  {
    super( userObject );
  }

  /**
   * Return the number of child nodes under this node.  Lazily loaded on
   * first request.
   *
   * @see #createChildren
   * @return The number of children for this node.
   */
  @Override
  public int getChildCount()
  {
    if ( ! initialised ) createChildren();
    return super.getChildCount();
  }

  /**
   * Determine whether this node holds children or not.  Over-ridden
   * to always indicate that there are children.
   *
   * @return Returns <code>true</code> if the receiver is a leaf.  Always
   *   returns <code>false</code>.
   */
  @Override
  public boolean isLeaf()
  {
    return false;
  }

  /**
   * Create the child nodes for this node.
   */
  protected abstract void createChildren();
  
  /**
   * Display an error message if fetching meta data fails.
   * 
   * @param throwable The exception that was raised.
   */
  protected void processFatalException( final Throwable throwable )
  {
    Application.getApplication().processFatalException(
        Configuration.getString( this, "error" ), throwable );
  }
  
  /**
   * Display an error message if fetching meta data fails.  Over-loaded
   * to replace a <code>$object$</code> place holder with the specified
   * <code>name</code>.
   * 
   * @param name The name of the database object that was being analysed.
   * @param throwable The exception that was raised.
   */
  protected void processFatalException( final String name,
      final Throwable throwable )
  {
    String message = Configuration.getString( this, "error" );
    message = message.replaceAll( "\\$object\\$", name );
    Application.getApplication().processFatalException( message, throwable );
  }
}
