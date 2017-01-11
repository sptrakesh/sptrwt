package com.sptci.rwt.webui.tree;

import java.util.Collection;

import com.sptci.echo2.Application;
import com.sptci.echo2.Configuration;

import com.sptci.rwt.ConnectionManager;
import com.sptci.rwt.RootMetaData;
import com.sptci.rwt.SequenceMetaData;
import com.sptci.rwt.SequenceAnalyser;
import com.sptci.rwt.webui.MainController;

/**
 * A {@link echopointng.tree.TreeNode} that represents all the sequences in
 * schema in the active database connection.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-28
 * @version $Id: SequencesNode.java 3648 2007-10-24 23:52:17Z rakesh $
 */
public class SequencesNode extends ContainerNode<RootMetaData>
{
  /**
   * Create a new tree node representing all the available sequences in the
   * specified schema.
   *
   * @param metadata The metadata object representing the schema.
   */
  public SequencesNode( final RootMetaData metadata )
  {
    super( metadata );
  }

  /**
   * Create the child nodes for this node.  Child nodes are instances of
   * {@link SequencesNode}.
   *
   * @see SequenceAnalyser
   * @see SequenceNode
   */
  @Override
  protected void createChildren()
  {
    initialised = true;

    try
    {
      Collection<SequenceMetaData> collection = metadata.getSequences();

      if ( collection.isEmpty() )
      {
        ConnectionManager manager = (ConnectionManager)
          Application.getApplication().getProperty(
            MainController.CONNECTION_MANAGER );
        SequenceAnalyser analyser = new SequenceAnalyser( manager );
        collection = analyser.analyse( metadata );
      }

      for ( SequenceMetaData sequence : collection )
      {
        add( new SequenceNode( sequence ) );
      }
    }
    catch ( Throwable t )
    {
      processFatalException( metadata.getName(), t );
    }
  }
}
