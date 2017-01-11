package com.sptci.rwt.webui.tree;

import java.util.Collection;

import com.sptci.echo2.Application;
import com.sptci.echo2.Configuration;

import com.sptci.rwt.ConnectionManager;
import com.sptci.rwt.MetaData;
import com.sptci.rwt.RootMetaData;
import com.sptci.rwt.TableMetaData;
import com.sptci.rwt.TriggerMetaData;
import com.sptci.rwt.TriggerAnalyser;
import com.sptci.rwt.webui.MainController;

/**
 * A {@link echopointng.tree.TreeNode} that represents all the triggers in
 * schema in the active database connection.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-28
 * @version $Id: TriggersNode.java 3684 2007-11-08 23:40:08Z rakesh $
 */
public class TriggersNode<S extends MetaData> extends ContainerNode<S>
{
  /**
   * Create a new tree node representing all the available triggers in the
   * specified schema.
   *
   * @param metadata The metadata object representing the schema.
   */
  public TriggersNode( final S metadata )
  {
    super( metadata );
  }

  /**
   * Create the child nodes for this node.  Child nodes are instances of
   * {@link TriggerNode}.
   */
  @Override
  protected void createChildren()
  {
    initialised = true;

    if ( metadata instanceof TableMetaData )
    {
      createChildren( (TableMetaData) metadata );
    }
    else
    {
      createChildren( (RootMetaData) metadata );
    }
  }

  /**
   * Create the child nodes for this node.  Child nodes are instances of
   * {@link TriggerNode} for the specified table.
   *
   * @see TriggerAnalyser
   * @see TriggerNode
   * @param metadata The metadata for the table whose triggers are to 
   *   be retrieved.
   */
  protected void createChildren( final TableMetaData metadata )
  {
    try
    {
      Collection<TriggerMetaData> collection = metadata.getTriggers();

      if ( collection.isEmpty() )
      {
        ConnectionManager manager = (ConnectionManager)
          Application.getApplication().getProperty(
            MainController.CONNECTION_MANAGER );
        TriggerAnalyser analyser = new TriggerAnalyser( manager );
        collection = analyser.analyse( metadata.getRoot(), metadata );
      }

      for ( TriggerMetaData trigger : collection )
      {
        add( new TriggerNode( trigger ) );
      }
    }
    catch ( Throwable t )
    {
      processFatalException( metadata.getName() , t );
    }
  }

  /**
   * Create the child nodes for this node.  Child nodes are instances of
   * {@link TriggerNode} for the specified schema.
   *
   * @see TriggerAnalyser
   * @see TriggerNode
   * @param metadata The metadata for the catalog/schema whose triggers
   *   are to be retrieved.
   */
  protected void createChildren( final RootMetaData metadata )
  {
    try
    {
      Collection<TriggerMetaData> collection = metadata.getTriggers();

      if ( collection.isEmpty() )
      {
        ConnectionManager manager = (ConnectionManager)
          Application.getApplication().getProperty(
              MainController.CONNECTION_MANAGER );
        TriggerAnalyser analyser = new TriggerAnalyser( manager );
        collection = analyser.analyse( metadata );
      }

      for ( TriggerMetaData trigger : collection )
      {
        add( new TriggerNode( trigger ) );
      }
    }
    catch ( Throwable t )
    {
      String message = Configuration.getString( this, "rootError" );
      message = message.replaceAll( "\\$object\\$", metadata.getName() );
      Application.getApplication().processFatalException( message , t );
    }
  }
}
