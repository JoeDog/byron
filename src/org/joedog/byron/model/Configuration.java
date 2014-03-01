package org.joedog.byron.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Enumeration;

import org.joedog.util.*;

public class Configuration {
  private Properties conf        = null;
  private static String sep      = java.io.File.separator;
  private static String cfgdir   = System.getProperty("user.home")+sep+".byron";
  private static String cfgfile  = cfgdir+sep+"game.conf";
  private static String hsfile   = cfgdir+sep+"scores.data";
  private static Configuration   _instance = null;
  private static Object mutex    = new Object();

  private Configuration() {
    System.getProperties().put("byron.dir",    cfgdir);
    System.getProperties().put("byron.conf",   cfgfile);
    System.getProperties().put("byron.scores", hsfile);

    conf = new Properties();

    if (! FileUtils.exists(this.cfgdir)) {
      FileUtils.mkdirs(this.cfgdir);
    }

    try {
      FileInputStream fis = new FileInputStream(new File(this.cfgfile));
      conf.load(fis);
      /**
       * this is sloppy but we like turing this on for debugging */
      Enumeration e = conf.propertyNames();
      while (e.hasMoreElements()) {
        String key = (String) e.nextElement();
        //System.out.println(key + " -- " + conf.getProperty(key));
      }
       /**/
    } catch (Exception e) {
      // catch Configuration Exception right here
    }
  }

  public synchronized static Configuration getInstance() {
    if (_instance == null) {
      synchronized(mutex) {
        if (_instance == null) {
          _instance = new Configuration();
        }
      }
    }
    return _instance;
  }

  public Enumeration propertyNames() {
    return conf.propertyNames();
  }

  public void setProperty(String key, String val) {
    conf.setProperty(key, val);
  }

  // get property value by name
  public String getProperty(String key) {
    String value = null;
    if (conf.containsKey(key)) {
      value = (String) conf.get(key);
    } else {
      // the property is absent
    }
    return value;
  }

  public boolean isConfigured() {
    return (new File(this.cfgfile)).exists();
  }

  public void save() {
    try {
      conf.store(new FileOutputStream(this.cfgfile), null);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return;
  }
}
