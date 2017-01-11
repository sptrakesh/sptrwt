package com.sptci.rwt.webui.model;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.Grid;

import echopointng.GroupBox;

import com.sptci.echo2.Configuration;

import com.sptci.rwt.SequenceMetaData;

/**
 * A view component used to display the information contained in
 * {@link com.sptci.rwt.SequenceMetaData}.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-07
 * @version $Id: SequenceView.java 3577 2007-10-08 04:02:38Z rakesh $
 */
public class SequenceView extends AbstractView
{
  /** The meta data object whose details are to be displayed. */
  private final SequenceMetaData metaData;

  /**
   * Create a new instance of the view using the specified model object.
   *
   * @param metaData The {@link #metaData} model object to use.
   */
  public SequenceView( final SequenceMetaData metaData )
  {
    this.metaData = metaData;
  }

  /**
   * Lifecycle method used to initialise component when added to a
   * container hierarchy.
   *
   * @see #createDetails
   */
  @Override
  public void init()
  {
    removeAll();
    add( createDetails() );
  }

  /**
   * Create the component used to display the default limits enforced by
   * the database engine.
   *
   * @see #createLabels
   * @return The component that displays the limits information.
   */
  protected Component createDetails()
  {
    Grid grid = new Grid();

    createLabels( "name", metaData, grid );
    createLabels( "dataType", metaData, grid );
    createLabels( "minimum", metaData, grid );
    createLabels( "maximum", metaData, grid );
    createLabels( "increment", metaData, grid );
    createLabels( "cyclePolicy", metaData, grid );
    createLabels( "comment", metaData, grid );

    GroupBox box = new GroupBox( Configuration.getString( this, "title" ) );
    box.add( grid );
    return box;
  }
}
