package com.sptci.rwt.webui.tree;

import java.util.Collection;

import com.sptci.echo2.Application;

import com.sptci.rwt.ConnectionManager;
import com.sptci.rwt.ColumnAnalyser;
import com.sptci.rwt.ColumnMetaData;
import com.sptci.rwt.TableTypeMetaData;
import com.sptci.rwt.webui.MainController;

/**
 * A {@link echopointng.tree.TreeNode} that represents all columns in a
 * table.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-28
 * @version $Id: ColumnsNode.java 3648 2007-10-24 23:52:17Z rakesh $
 */
public class ColumnsNode extends ContainerNode<TableTypeMetaData>
{
  /**
   * Create a new tree node using the specified metadata object.  The
   * object represents the tables whose columns are to displayed as
   * children of this node.
   *
   * @param metadata The metadata object to use as the model for this node.
   */
  public ColumnsNode( final TableTypeMetaData metadata )
  {
    super( metadata );
  }

  /**
   * Create the child nodes for this node.  Child nodes are instances of
   * {@link ColumnNode}.
   *
   * @see com.sptci.rwt.ColumnAnalyser
   * @see ColumnNode
   */
  protected void createChildren()
  {
    initialised = true;

    try
    {
      Collection<ColumnMetaData> collection = metadata.getColumns();

      if ( collection.isEmpty() )
      {
        final ConnectionManager manager = (ConnectionManager)
          Application.getApplication().getProperty(
            MainController.CONNECTION_MANAGER );
        final ColumnAnalyser analyser = new ColumnAnalyser( manager );
        collection = analyser.analyse( metadata.getRoot(), metadata );
      }

      for ( ColumnMetaData column : collection )
      {
        add( new ColumnNode( column ) );
      }
    }
    catch ( Throwable t )
    {
      processFatalException( metadata.getName(), t );
    }
  }
}
