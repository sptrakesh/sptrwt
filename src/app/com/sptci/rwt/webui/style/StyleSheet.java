package com.sptci.rwt.webui.style;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.text.TextComponent;

import com.sptci.echo2.style.button.No;
import com.sptci.echo2.style.button.Yes;

import com.sptci.rwt.webui.BatchQueryExecutorView;
import com.sptci.rwt.webui.MainView;
import com.sptci.rwt.webui.QueryExecutorView;

/**
 * The {@link nextapp.echo2.app.StyleSheet} for the application.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-30
 * @version $Id: StyleSheet.java 3646 2007-10-24 00:23:41Z rakesh $
 */
public class StyleSheet extends com.sptci.epng.style.StyleSheet
{
  /** Over-ridden to add additional methods. */
  @Override
  protected void init()
  {
    super.init();
    addSplitPaneStyles();
  }

  /** Over-ridden to add additional styles for buttons. */
  @Override
  protected void addButtonStyles()
  {
    super.addButtonStyles();
    addStyle( Button.class, "Accept.Button", new Yes() );
    addStyle( Button.class, "Cancel.Button", new No() );
  }

  /** Over-ridden to add application specific styles. */
  @Override
  protected void addLabelStyles()
  {
    super.addLabelStyles();
    addStyle( Label.class, "Title.Label", new GridTitleLabel() );
    addStyle( Label.class, "Save.Label", new SaveLabel() );
  }

  /**
   * Add styles for the split panes used to layout the application content pane.
   */
  protected void addSplitPaneStyles()
  {
    addStyle( SplitPane.class, MainView.class.getName() + ".mainSplitPane",
        new MainSplitPane() );
    addStyle( SplitPane.class, MainView.class.getName() + ".displayArea",
        new DisplayArea() );
    
    addStyle( SplitPane.class,
        BatchQueryExecutorView.class.getName() + ".splitPane",
        new BatchQueryExecutorPane() );
    addStyle( SplitPane.class,
        QueryExecutorView.class.getName() + ".splitPane",
        new QueryExecutorPane() );
  }
  
  /** Add styles for text components specific for the application. */
  @Override
  protected void addTextStyles()
  {
    super.addTextStyles();
    addStyle( TextComponent.class, "Query.TextComponent", new Query() );
  }
}