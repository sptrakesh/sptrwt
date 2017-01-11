package com.sptci.rwt.webui.tree;

import java.util.Collection;

import com.sptci.echo2.Application;
import com.sptci.echo2.Configuration;

import com.sptci.rwt.CatalogueMetaData;
import com.sptci.rwt.ConnectionManager;
import com.sptci.rwt.SchemaAnalyser;
import com.sptci.rwt.SchemaMetaData;
import com.sptci.rwt.webui.MainController;

/**
 * A {@link echopointng.tree.TreeNode} that represents all the schemas in
 * catalogue in the active database connection.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-23
 * @version $Id: SchemasNode.java 3649 2007-10-25 00:15:24Z rakesh $
 * @since Version 1.1
 */
public class SchemasNode extends ContainerNode<CatalogueMetaData>
{
  /**
   * Create a new tree node representing all the available schemas in the
   * specified catalogue.
   *
   * @param metadata The metadata object representing the schema.
   */
  public SchemasNode( final CatalogueMetaData metadata )
  {
    super( metadata );
  }
  
  /**
   * Create the child nodes for this node.
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
      Collection<SchemaMetaData> collection = metadata.getSchemas();
      
      if ( collection.isEmpty() )
      {
        ConnectionManager manager = (ConnectionManager)
        Application.getApplication().getProperty(
            MainController.CONNECTION_MANAGER );
        SchemaAnalyser analyser = new SchemaAnalyser( manager );
        collection = analyser.analyse(
            metadata.getDbmsMetaData(), metadata );
      }
      
      for ( SchemaMetaData schema : collection )
      {
        add( new SchemaNode( schema ) );
      }
    }
    catch ( Throwable t )
    {
      processFatalException( metadata.getName(), t );
    }
  }
}