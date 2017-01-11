package com.sptci.rwt.webui.tree;

import java.util.Collection;

import com.sptci.echo2.Application;
import com.sptci.echo2.ErrorPane;

import com.sptci.rwt.CatalogueAnalyser;
import com.sptci.rwt.CatalogueMetaData;
import com.sptci.rwt.ConnectionManager;
import com.sptci.rwt.DBMSMetaData;
import com.sptci.rwt.SchemaAnalyser;
import com.sptci.rwt.SchemaMetaData;
import com.sptci.rwt.webui.MainController;

/**
 * A root {@link echopointng.tree.TreeNode} that represents the currently
 * active database connection.  This class uses the {@link
 * com.sptci.rwt.DBMSMetaData} value object as the model.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-27
 * @version $Id: DBMSNode.java 3648 2007-10-24 23:52:17Z rakesh $
 */
public class DBMSNode extends AbstractNode<DBMSMetaData>
{
  /**
   * Create a new root tree node using the specified metadata object.
   *
   * @param metadata The metadata object to use as the model for this node.
   */
  public DBMSNode( final DBMSMetaData metadata )
  {
    super( metadata );
  }

  /**
   * Create the child nodes for this node.  Child nodes are instances of
   * {@link SchemaNode}.
   *
   * @see SchemaAnalyser
   * @see SchemaNode
   */
  @Override
  protected void createChildren()
  {
    initialised = true;

    try
    {
      final DBMSMetaData dmd = getUserObject();
      
      Collection<CatalogueMetaData> catalogues = dmd.getCatalogues();
      
      if ( catalogues.isEmpty() )
      {
        final ConnectionManager manager = (ConnectionManager)
        Application.getApplication().getProperty(
            MainController.CONNECTION_MANAGER );
        final CatalogueAnalyser analyser = new CatalogueAnalyser( manager );
        catalogues = analyser.analyse( dmd );
      }
      
      for ( CatalogueMetaData catalogue : catalogues )
      {
        add( new CatalogueNode( catalogue ) );
      }

      Collection<SchemaMetaData> schemas = dmd.getSchemas();

      if ( schemas.isEmpty() )
      {
        final ConnectionManager manager = (ConnectionManager)
          Application.getApplication().getProperty(
            MainController.CONNECTION_MANAGER );
        final SchemaAnalyser analyser = new SchemaAnalyser( manager );
        schemas = analyser.analyse( dmd );
      }

      for ( SchemaMetaData schema : schemas )
      {
        add( new SchemaNode( schema ) );
      }
    }
    catch ( Throwable t )
    {
      processFatalException( t );
    }
  }
}
