package com.sptci.rwt.webui.style;

import com.sptci.echo2.Dimensions;
import com.sptci.echo2.style.TextComponent;
import com.sptci.rwt.webui.ExecutorView;

/**
 * The default style to use for {@link nextapp.echo2.app.text.TextComponent}
 * used to input SQL statements.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-10
 * @version $Id: Query.java 3637 2007-10-21 02:11:18Z rakesh $
 */
public class Query extends TextComponent
{
  /** Over-ridden to change dimensions. */
  @Override
  protected void init()
  {
    super.init();
    setProperty( nextapp.echo2.app.text.TextComponent.PROPERTY_WIDTH,
        Dimensions.getExtent( ExecutorView.class, "query.width" ) );
  }
}