package com.sptci.rwt.webui;

import com.sptci.echo2.Configuration;
import com.sptci.epng.Tree;
import com.sptci.rwt.webui.tree.DBMSNode;
import com.sptci.rwt.webui.tree.SelectionListener;

/**
 * The {@link com.sptci.epng.Tree} component used to display the database
 * metadata objects in hierarchical format.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-29
 * @version $Id: MetaDataTree.java 3578 2007-10-08 04:04:42Z rakesh $
 */
public class MetaDataTree extends Tree
{
  /** The controller for interacting with the application. */
  private final MainController controller;

  /**
   * Create a new instance using the specified controller.
   *
   * @param controller The {@link #controller} to use.
   */
  public MetaDataTree( final MainController controller )
  {
    super( new DBMSNode( controller.getDBMSMetaData() ) );
    this.controller = controller;
    getSelectionModel().addTreeSelectionListener(
        new SelectionListener( controller ) );
  }
}
