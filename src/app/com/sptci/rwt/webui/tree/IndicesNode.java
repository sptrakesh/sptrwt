package com.sptci.rwt.webui.tree;

import java.util.Collection;

import com.sptci.echo2.Application;
import com.sptci.echo2.Configuration;

import com.sptci.rwt.ConnectionManager;
import com.sptci.rwt.TableMetaData;
import com.sptci.rwt.IndexMetaData;
import com.sptci.rwt.IndexAnalyser;
import com.sptci.rwt.webui.MainController;

/**
 * A {@link echopointng.tree.TreeNode} that represents all the indices in
 * schema in the active database connection.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-28
 * @version $Id: IndicesNode.java 3648 2007-10-24 23:52:17Z rakesh $
 */
public class IndicesNode extends ContainerNode<TableMetaData>
{
  /**
   * Create a new tree node representing all the available indices in the
   * specified schema.
   *
   * @param metadata The metadata object representing the schema.
   */
  public IndicesNode( final TableMetaData metadata )
  {
    super( metadata );
  }

  /**
   * Create the child nodes for this node.  Child nodes are instances of
   * {@link IndicesNode}.
   *
   * @see IndexAnalyser
   * @see IndexNode
   */
  @Override
  protected void createChildren()
  {
    initialised = true;

    try
    {
      Collection<IndexMetaData> collection = metadata.getIndices();

      if ( collection.isEmpty() )
      {
        ConnectionManager manager = (ConnectionManager)
          Application.getApplication().getProperty(
            MainController.CONNECTION_MANAGER );
        IndexAnalyser analyser = new IndexAnalyser( manager );
        collection = analyser.analyse( metadata.getRoot(), metadata );
      }

      for ( IndexMetaData index : collection )
      {
        add( new IndexNode( index ) );
      }
    }
    catch ( Throwable t )
    {
      processFatalException( metadata.getName(), t );
    }
  }
}
