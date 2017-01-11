package com.sptci.rwt.webui.tree;

import java.util.Collection;

import com.sptci.echo2.Application;
import com.sptci.echo2.Configuration;
import com.sptci.epng.TreeNode;

import com.sptci.rwt.ConnectionManager;
import com.sptci.rwt.RootMetaData;
import com.sptci.rwt.ProcedureMetaData;
import com.sptci.rwt.ProcedureAnalyser;
import com.sptci.rwt.webui.MainController;

/**
 * A {@link echopointng.tree.TreeNode} that represents all the procedures in
 * schema in the active database connection.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-28
 * @version $Id: ProceduresNode.java 3650 2007-10-25 03:07:05Z rakesh $
 */
public class ProceduresNode extends ContainerNode<RootMetaData>
{
  /**
   * Create a new tree node representing all the available procedures in the
   * specified schema.
   *
   * @param metadata The metadata object representing the schema.
   */
  public ProceduresNode( final RootMetaData metadata )
  {
    super( metadata );
  }

  /**
   * Create the child nodes for this node.  Child nodes are instances of
   * {@link ProceduresNode}.
   *
   * @see ProcedureAnalyser
   * @see ProcedureNode
   */
  @Override
  protected void createChildren()
  {
    initialised = true;

    try
    {
      Collection<ProcedureMetaData> collection = metadata.getProcedures();

      if ( collection.isEmpty() )
      {
        ConnectionManager manager = (ConnectionManager)
          Application.getApplication().getProperty(
            MainController.CONNECTION_MANAGER );
        ProcedureAnalyser analyser = new ProcedureAnalyser( manager );
        collection = analyser.analyse( metadata );
      }
      
      int count = 0;
      TreeNode parent = null;
      for ( ProcedureMetaData procedure : collection )
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
          
          parent.add( new ProcedureNode( procedure ) );
        }
        else
        {
          add( new ProcedureNode( procedure ) );
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
