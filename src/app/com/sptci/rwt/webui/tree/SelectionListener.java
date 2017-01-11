package com.sptci.rwt.webui.tree;

import nextapp.echo2.app.Component;

import echopointng.tree.TreeSelectionEvent;
import echopointng.tree.TreeSelectionListener;

import com.sptci.ReflectionUtility;
import com.sptci.echo2.Configuration;
import com.sptci.rwt.webui.MainController;

/**
 * A {@link echopointng.tree.TreeSelectionListener} for selections on
 * {@link AbstractNode}s.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-07
 * @version $Id: SelectionListener.java 38 2007-11-22 00:48:04Z sptrakesh $
 */
public class SelectionListener implements TreeSelectionListener
{
  /**
   * The controller used to interact with the rest of the application.
   */
  protected final MainController controller;

  /**
   * Create a new instance of the listener using the specified controller.
   *
   * @param controller The {@link #controller} to use.
   */
  public SelectionListener( final MainController controller )
  {
    this.controller = controller;
  }

  /**
   * Update {@link com.sptci.rwt.webui.MainView#right} with meta data
   * relevant to the selected {@link AbstractNode}.
   *
   * @see #displayNode
   * @param event The selection event that was triggered.
   */
  public void valueChanged( final TreeSelectionEvent event )
  {
    Object object = event.getPath().getLastPathComponent();
    if ( object instanceof AbstractNode )
    {
      AbstractNode node = (AbstractNode) object;
      displayNode( node );
    }
  }

  /**
   * Display details for the node selected in the {@link
   * com.sptci.rwt.webui.MainView#right} component.
   *
   * @param node The node whose model information is to be displayed.
   */
  protected void displayNode( final AbstractNode node )
  {
    try
    {
      String className = node.getClass().getName();
      className = className.replaceAll( "\\.tree\\.", ".model." );
      className = className.replaceAll( "Node$", "View" );
      Component component = (Component)
        ReflectionUtility.newInstance( className, node.getUserObject() );
      controller.setContent( component );
    }
    catch ( Throwable t )
    {
      String message = Configuration.getString( this, "error" );
      message = message.replaceAll( "\\$node\\$", node.getClass().getName() );
      controller.processFatalException( message, t );
    }
  }
}
