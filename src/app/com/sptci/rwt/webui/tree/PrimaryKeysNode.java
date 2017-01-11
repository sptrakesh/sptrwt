package com.sptci.rwt.webui.tree;

import com.sptci.echo2.Application;
import com.sptci.echo2.Configuration;

import com.sptci.rwt.ConnectionManager;
import com.sptci.rwt.PrimaryKeyMetaData;
import com.sptci.rwt.PrimaryKeyAnalyser;
import com.sptci.rwt.TableMetaData;
import com.sptci.rwt.webui.MainController;

/**
 * A {@link echopointng.tree.TreeNode} that contains nodes that represent
 * the primary key columns for the specified table.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-28
 * @version $Id: PrimaryKeysNode.java 3648 2007-10-24 23:52:17Z rakesh $
 */
public class PrimaryKeysNode extends ContainerNode<TableMetaData>
{
  /**
   * Create a new tree node using the specified metadata object.  The
   * object represents a table in the database.
   *
   * @param metadata The metadata object to use as the model for this node.
   */
  public PrimaryKeysNode( final TableMetaData metadata )
  {
    super( metadata );
  }

  /**
   * Create the child nodes for this node.  Child nodes are instances of
   * {@link ContainerNode}.
   *
   * @see PrimaryKeyNode
   */
  protected void createChildren()
  {
    initialised = true;

    try
    {
      PrimaryKeyMetaData pkmd = metadata.getPrimaryKey();

      if ( pkmd == null )
      {
        ConnectionManager manager = (ConnectionManager)
          Application.getApplication().getProperty(
            MainController.CONNECTION_MANAGER );
        PrimaryKeyAnalyser analyser = new PrimaryKeyAnalyser( manager );

        for ( PrimaryKeyMetaData md :
            analyser.analyse( metadata.getRoot(), metadata ) )
        {
          pkmd = md;
        }
      }

      if ( pkmd != null ) add( new PrimaryKeyNode( pkmd ) );
    }
    catch ( Throwable t )
    {
      processFatalException( metadata.getName(), t );
    }
  }
}
