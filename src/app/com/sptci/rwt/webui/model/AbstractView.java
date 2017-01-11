package com.sptci.rwt.webui.model;

import nextapp.echo2.app.Column;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;

import com.sptci.ReflectionUtility;
import com.sptci.echo2.Application;
import com.sptci.echo2.Configuration;
import com.sptci.echo2.Utilities;
import com.sptci.echo2.View;

import com.sptci.rwt.MetaData;
import com.sptci.rwt.webui.ConnectionDialogue;

/**
 * An abstract base class used to represent the various view components
 * used to represent the details in the associated {@link
 * com.sptci.rwt.MetaData} model objects.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-07
 * @version $Id: AbstractView.java 3684 2007-11-08 23:40:08Z rakesh $
 */
public abstract class AbstractView extends Column implements View
{
  /**
   * Create standard {@link nextapp.echo2.app.Label} components that
   * represent the name of the specified field and the value in the
   * specified model.
   *
   * @param name The name of the field.
   * @param metaData The model object.
   * @param component The container component to which the labels are to
   *   be added.
   */
  protected void createLabels( final String name, final MetaData metaData,
      final Component component )
  {
    final String method = "get" + name.substring( 0, 1 ).toUpperCase() +
      name.substring( 1 );

    try
    {
      component.add( Utilities.createLabel(
            getClass().getName(), name, "Title.Label" ) );
      component.add( new Label( String.valueOf(
            ReflectionUtility.execute( metaData, method ) ) ) );
    }
    catch ( Throwable t )
    {
      processFatalException( method, metaData.getClass().getName(), t );
    }
  }
  
  /**
   * Display an error message indicating the cause of the reflection error.
   * 
   * @param method The name of the method that failed.
   * @param cls The fully qualified name of the class on which the method
   *   was attempted.
   * @param throwable The exception that was raised.
   */
  protected void processFatalException( final String method,
      final String cls, final Throwable throwable )
  {
    String message = Configuration.getString(
        ConnectionDialogue.class, "methodError" );
    message = message.replaceAll( "\\$method\\$", method );
    message = message.replaceAll( "\\$class\\$", cls );
    Application.getApplication().processFatalException( message, throwable );
  }
}
