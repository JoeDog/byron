/*
 * Copyright (c) Ian F. Darwin, http://www.darwinsys.com/, 1996-2002.
 * All rights reserved. Software written by Ian F. Darwin and others.
 * $Id: LICENSE,v 1.8 2004/02/09 03:33:38 ian Exp $
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS''
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * Java, the Duke mascot, and all variants of Sun's Java "steaming coffee
 * cup" logo are trademarks of Sun Microsystems. Sun's, and James Gosling's,
 * pioneering role in inventing and promulgating (and standardizing) the Java 
 * language and environment is gratefully acknowledged.
 * 
 * The pioneering role of Dennis Ritchie and Bjarne Stroustrup, of AT&T, for
 * inventing predecessor languages C and C++ is also gratefully acknowledged.
 */
package org.joedog.byron.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Document;

/**
 * Help Frame based on JFC JEditorPane.
 * <p>
 * May someday rewrite using JavaHelp API.
 * 
 * @version $Id: SimpleHelp.java,v 1.10 2003/05/30 13:16:07 ian Exp $
 */
public class Help extends JFrame implements HyperlinkListener {
  private   String      path;
  private   URL         url = null;
  protected Container   cp;
  protected JEditorPane help;

  public Help (String name, String file) {
    this(name, file, 500, 400); 
  }

  public Help (String name, String file, int width, int height) {
    super(name);
    cp = getContentPane();
    getAccessibleContext().setAccessibleName(name + " Help Window");
    getAccessibleContext().setAccessibleDescription(
      "A window for viewing the help for "+name+", which is somewhat hyperlinked."
    );
    path = "org/joedog/byron/files/"+file;

    try {
      url = getClass().getClassLoader().getResource(path);
      if (help == null) {
        help = new JEditorPane(url);
        help.setEditable(false);
        help.addHyperlinkListener(this);
        JScrollPane scroller = new JScrollPane();
        scroller.setBorder(BorderFactory.createTitledBorder(name));
        scroller.getViewport().add(help);
        cp.add(BorderLayout.CENTER, scroller);
        addWindowListener(new WindowAdapter() {
          public void windowClosing(WindowEvent e) {
            Help.this.setVisible(true);
            Help.this.dispose();
          }
        });
        this.setSize(width, height);
        Dimension dim  = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width-width)/2;
        int y = (dim.height-height)/2;
        this.setLocation(x, y);
        this.setVisible(true);
        this.show();
      }
    } catch (MalformedURLException e) {
      System.err.println(e.toString());
    } catch (IOException e) {
      System.err.println(e.toString());
    }
  }

  /**
   * Notification of a change relative to a hyperlink.
   */
  public void hyperlinkUpdate(HyperlinkEvent e) {
    if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
      URL target = e.getURL();

      Cursor oldCursor = help.getCursor();
      Cursor waitCursor = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
      help.setCursor(waitCursor);

      // Now arrange for the page to get loaded asynchronously,
      // and the cursor to be set back to what it was.
      SwingUtilities.invokeLater(new PageLoader(target, oldCursor));
    }
  }

  class PageLoader implements Runnable {
    URL url;

    Cursor cursor;

    PageLoader(URL u, Cursor c) {
      url = u;
      cursor = c;
    }

    public void run() {
      if (url == null) {
        help.setCursor(cursor);
        Container parent = help.getParent();
        parent.repaint();
      } else {
        Document doc = help.getDocument();
        try {
          help.setPage(url);
        } catch (Exception ioe) {
          help.setDocument(doc);
          getToolkit().beep();
          ioe.printStackTrace();
        } finally {
          url = null;
          SwingUtilities.invokeLater(this);
        }
      }
    }
  }
}
