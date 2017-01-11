package com.sptci.rwt.webui.tree;

import java.util.ArrayList;
import java.util.Collection;

import com.sptci.echo2.Application;
import com.sptci.echo2.Configuration;

import com.sptci.rwt.ConnectionManager;
import com.sptci.rwt.ForeignKeyMetaData;
import com.sptci.rwt.ForeignKeyAnalyser;
import com.sptci.rwt.TableMetaData;
import com.sptci.rwt.webui.MainController;

/**
 * A {@link echopointng.tree.TreeNode} that contains nodes that represent
 * the foreign key columns for the specified table.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-07
 * @version $Id: ForeignKeysNode.java 3648 2007-10-24 23:52:17Z rakesh $
 */
public class ForeignKeysNode extends ContainerNode<TableMetaData>
{
  /**
   * Create a new tree node using the specified metadata object.  The
   * object represents a table in the database.
   *
   * @param metadata The metadata object to use as the model for this node.
   */
  public ForeignKeysNode( final TableMetaData metadata )
  {
    super( metadata );
  }

  /**
   * Create the child nodes for this node.  Child nodes are instances of
   * {@link ContainerNode}.
   *
   * @see ForeignKeyNode
   */
  protected void createChildren()
  {
    initialised = true;

    try
    {
      Collection<ForeignKeyMetaData> collection = metadata.getForeignKeys();

      if ( collection.isEmpty() )
      {
        ConnectionManager manager = (ConnectionManager)
          Application.getApplication().getProperty(
            MainController.CONNECTION_MANAGER );
        ForeignKeyAnalyser analyser = new ForeignKeyAnalyser( manager );

        collection = analyser.analyse( metadata.getRoot(), metadata );
      }

      for ( ForeignKeyMetaData fkmd : collection )
      {
        add( new ForeignKeyNode( fkmd ) );
      }
    }
    catch ( Throwable t )
    {
      processFatalException( metadata.getName(), t );
    }
  }
}
