package com.sptci.rwt.webui.style;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.layout.GridLayoutData;

import com.sptci.echo2.style.BoldLabel;

/**
 * The style class for {@link nextapp.echo2.app.Label} components that are
 * used to display title labels for meta data fields.
 *
 * <p>Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-07
 * @version $Id: GridTitleLabel.java 3578 2007-10-08 04:04:42Z rakesh $
 */
public class GridTitleLabel extends BoldLabel
{
  /**
   * Initialise the style properties.
   */
  @Override
  protected void init()
  {
    super.init();
    
    GridLayoutData layoutData = new GridLayoutData();
    layoutData.setAlignment( Alignment.ALIGN_RIGHT );
    setProperty( Label.PROPERTY_LAYOUT_DATA, layoutData );
  }
}
