package com.sptci.rwt.webui.tree;

import java.util.Collection;

import com.sptci.echo2.Application;
import com.sptci.echo2.Configuration;
import com.sptci.epng.TreeNode;

import com.sptci.rwt.ConnectionManager;
import com.sptci.rwt.RootMetaData;
import com.sptci.rwt.TableMetaData;
import com.sptci.rwt.TableAnalyser;
import com.sptci.rwt.webui.MainController;

/**
 * A {@link echopointng.tree.TreeNode} that represents all the tables in
 * schema in the active database connection.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-28
 * @version $Id: TablesNode.java 3652 2007-10-26 01:31:14Z rakesh $
 */
public class TablesNode extends ContainerNode<RootMetaData>
{
  /**
   * Create a new tree node representing all the available tables in the
   * specified schema.
   *
   * @param metadata The metadata object representing the schema.
   */
  public TablesNode( final RootMetaData metadata )
  {
    super( metadata );
  }

  /**
   * Create the child nodes for this node.  Child nodes are instances of
   * {@link TablesNode}.
   *
   * @see TableAnalyser
   * @see TableNode
   */
  @Override
  protected void createChildren()
  {
    initialised = true;

    try
    {
      Collection<TableMetaData> collection = metadata.getTables();

      if ( collection.isEmpty() )
      {
        ConnectionManager manager = (ConnectionManager)
          Application.getApplication().getProperty(
            MainController.CONNECTION_MANAGER );
        TableAnalyser analyser = new TableAnalyser( manager );
        collection = analyser.analyse( metadata );
      }

      int count = 0;
      TreeNode parent = null;
      for ( TableMetaData table : collection )
      {
        if ( collection.size() > 25 )
        {
          if ( ( count % 25 ) == 0 )
          {
            int start = count + 1;
            int end = Math.min( start + 24, collection.size() );
            parent = new TreeNode( start + " - " + end );
            add( parent );
          }
          
          parent.add( new TableNode( table ) );
        }
        else
        {
          add( new TableNode( table ) );
        }

        ++count;
      }
    }
    catch ( Throwable t )
    {
      processFatalException( metadata.getName(), t );
    }
  }
}
