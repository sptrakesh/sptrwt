package com.sptci.rwt.webui.model;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.Grid;

import echopointng.GroupBox;

import com.sptci.echo2.Configuration;

import com.sptci.rwt.IndexMetaData;
import com.sptci.rwt.KeyMetaData;

/**
 * A view component used to display the information contained in
 * {@link com.sptci.rwt.IndexMetaData}.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-07
 * @version $Id: IndexView.java 3577 2007-10-08 04:02:38Z rakesh $
 */
public class IndexView extends KeyView
{
  /** The meta data object whose details are to be displayed. */
  private final IndexMetaData metaData;

  /**
   * Create a new instance of the view using the specified model object.
   *
   * @param metaData The {@link #metaData} model object to use.
   */
  public IndexView( final IndexMetaData metaData )
  {
    this.metaData = metaData;
  }

  /**
   * Lifecycle method used to initialise component when added to a
   * container hierarchy.
   *
   * @see #createDetails
   * @see #createColumnDetails
   */
  @Override
  public void init()
  {
    removeAll();
    add( createDetails() );
    add( createColumnDetails() );
  }

  /**
   * Create the component used to display the details of the index.
   *
   * @see #createLabels
   * @return The component that displays the index information.
   */
  protected Component createDetails()
  {
    Grid grid = new Grid();

    createLabels( "name", metaData, grid );
    createLabels( "unique", metaData, grid );
    createLabels( "type", metaData, grid );
    createLabels( "sortSequence", metaData, grid );
    createLabels( "cardinality", metaData, grid );
    createLabels( "pages", metaData, grid );

    GroupBox box = new GroupBox( Configuration.getString( this, "title" ) );
    box.add( grid );
    return box;
  }

  /**
   * Method to return the model object for this view.
   *
   * @return The meta data object.
   */
  @Override
  protected KeyMetaData getMetaData()
  {
    return metaData;
  }
}
