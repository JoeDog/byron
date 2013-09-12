package org.joedog.byron.controller;

import org.joedog.byron.view.AbstractView;
import org.joedog.byron.model.AbstractModel;

import java.util.ArrayList;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;

public abstract class AbstractController implements PropertyChangeListener {
 
  private ArrayList <AbstractView>  registeredViews;
  private ArrayList <AbstractModel> registeredModels;
 
  public AbstractController () {
    this.registeredViews  = new ArrayList<AbstractView>();
    this.registeredModels = new ArrayList<AbstractModel>();
  } 

  public void addView (AbstractView view) {
    this.registeredViews.add(view);
  }

  public void addModel (AbstractModel model) {
    this.registeredModels.add(model);
    model.addPropertyChangeListener(this);
  }

  public void removeView (AbstractView view) {
    registeredViews.remove(view);
  }

  public void removeModel (AbstractModel model) {
    registeredModels.remove(model);
  }

  public void propertyChange(PropertyChangeEvent evt) {
    for (AbstractView view: registeredViews) {
      view.modelPropertyChange(evt);
    }
  }

  protected Object getModelProperty(String propertyName) {
    for (AbstractModel model: registeredModels) {
      try {
        Method method = model.getClass().getMethod("get"+propertyName);
        return method.invoke(model);
      } catch (Exception ex) {
        // No warning; some models won't have the requested method
      }
    }
    return null;
  }

  protected Object getModelProperty (String propertyName, Object param) {
    for (AbstractModel model: registeredModels) {
      try {
        Method method = model.getClass().getMethod("get"+propertyName, new Class[] {
          param.getClass()
        });
        return method.invoke(model, param);
      } catch (Exception ex) {
        // No warning; some models won't have the requested method
      }
    }
    return null;
  }

  protected void setModelProperty (String propertyName, Object newValue) {
    for (AbstractModel model: registeredModels) {
      try {
        Method method = model.getClass().getMethod("set"+propertyName, new Class[] {
          newValue.getClass()
        });
        method.invoke(model, newValue);
      } catch (Exception ex) {
        // No warning; some models won't have the requested method
      }
    }
  }

  protected Object getViewProperty(String propertyName) {
    for (AbstractView view: registeredViews) {
      try {
        Method method = view.getClass().getMethod("get"+propertyName);
        return method.invoke(view);
      } catch (Exception ex) {
        // No warning; some views won't have the requested method
      }
    }
    return null;
  }

  protected Object getViewProperty (String propertyName, Object param) {
    for (AbstractView view: registeredViews) {
      try {
        Method method = view.getClass().getMethod("get"+propertyName, new Class[] {
          param.getClass()
        });
        return method.invoke(view, param);
      } catch (Exception ex) {
        // No warning; some views won't have the requested method
      }
    }
    return null;
  }

  protected void setViewProperty (String propertyName, Object newValue) {
    for (AbstractView view: registeredViews) {
      try {
        Method method = view.getClass().getMethod("set"+propertyName, new Class[] {
          newValue.getClass()
        }); 
        method.invoke(view, newValue);
      } catch (Exception ex) {
        // No warning; some views won't have the requested method
      }
    }
  }

  protected void runModelMethod (String name) {
    for (AbstractModel model: registeredModels) {
      try {
        Method method = model.getClass().getMethod(name);
        method.invoke(model);
      } catch (Exception ex) {
        // No warning; some models won't have the requested method
      }
    }
  }

  protected void runViewMethod (String name) {
    for (AbstractView view: registeredViews) {
      try {
        Method method = view.getClass().getMethod(name);
        method.invoke(view);
      } catch (Exception ex) {
        // No warning; some views won't have the requested method
      }
    }
  }
}
