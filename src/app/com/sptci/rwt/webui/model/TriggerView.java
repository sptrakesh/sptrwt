package com.sptci.rwt.webui.model;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;

import echopointng.DirectHtml;
import echopointng.GroupBox;

import com.sptci.echo2.Configuration;

import com.sptci.rwt.MetaData;
import com.sptci.rwt.TriggerMetaData;

/**
 * A view component used to display the information contained in
 * {@link com.sptci.rwt.TriggerMetaData}.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-07
 * @version $Id: TriggerView.java 3684 2007-11-08 23:40:08Z rakesh $
 */
public class TriggerView extends SourceView
{
  /** The meta data object whose details are to be displayed. */
  private final TriggerMetaData metaData;

  /**
   * Create a new instance of the view using the specified model object.
   *
   * @param metaData The {@link #metaData} model object to use.
   */
  public TriggerView( final TriggerMetaData metaData )
  {
    this.metaData = metaData;
  }

  /**
   * Lifecycle method used to initialise component when added to a
   * container hierarchy.
   *
   * @see #createDetails
   * @see #createStatementDetails
   */
  @Override
  public void init()
  {
    removeAll();
    add( createDetails() );
    add( createStatementDetails() );
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
    createLabels( "table", metaData, grid );
    createLabels( "tableSchema", metaData, grid );
    createLabels( "event", metaData, grid );
    createLabels( "orientation", metaData, grid );
    createLabels( "timing", metaData, grid );

    GroupBox box = new GroupBox( Configuration.getString( this, "title" ) );
    box.add( grid );
    return box;
  }

  /**
   * Create the component used to display the SQL statement that is executed
   * by the trigger.
   *
   * @see #toHtml
   * @see #syntaxHighlight
   * @return The component that displays the SQL statement
   */
  protected Component createStatementDetails()
  {
    TriggerMetaData tmd = (TriggerMetaData) metaData;
    String statement = tmd.getStatement();

    if ( statement == null ) return new Label( "" );

    statement = syntaxHighlight( toHtml( statement ) );

    GroupBox box = new GroupBox(
        Configuration.getString( this, "statement.title" ) );
    box.add( new DirectHtml( statement ) );
    return box;
  }
}
