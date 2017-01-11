package com.sptci.rwt.webui.tree;

import java.util.Collection;

import com.sptci.echo2.Application;
import com.sptci.echo2.Configuration;

import com.sptci.rwt.ConnectionManager;
import com.sptci.rwt.RootMetaData;
import com.sptci.rwt.ViewMetaData;
import com.sptci.rwt.ViewAnalyser;
import com.sptci.rwt.webui.MainController;

/**
 * A {@link echopointng.tree.TreeNode} that represents all the views in
 * schema in the active database connection.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-28
 * @version $Id: ViewsNode.java 3648 2007-10-24 23:52:17Z rakesh $
 */
public class ViewsNode extends ContainerNode<RootMetaData>
{
  /**
   * Create a new tree node representing all the available views in the
   * specified schema.
   *
   * @param metadata The metadata object representing the schema.
   */
  public ViewsNode( final RootMetaData metadata )
  {
    super( metadata );
  }

  /**
   * Create the child nodes for this node.  Child nodes are instances of
   * {@link ViewsNode}.
   *
   * @see ViewAnalyser
   * @see ViewNode
   */
  @Override
  protected void createChildren()
  {
    initialised = true;

    try
    {
      Collection<ViewMetaData> collection = metadata.getViews();

      if ( collection.isEmpty() )
      {
        ConnectionManager manager = (ConnectionManager)
          Application.getApplication().getProperty(
            MainController.CONNECTION_MANAGER );
        ViewAnalyser analyser = new ViewAnalyser( manager );
        collection = analyser.analyse( metadata );
      }

      for ( ViewMetaData view : collection )
      {
        add( new ViewNode( view ) );
      }
    }
    catch ( Throwable t )
    {
      processFatalException( metadata.getName(), t );
    }
  }
}
