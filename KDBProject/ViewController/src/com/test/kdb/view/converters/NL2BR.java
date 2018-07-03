package com.test.kdb.view.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class NL2BR implements Converter {
    public NL2BR() {
        super();
    }

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
      return value;
    }
    
    public String getAsString(FacesContext context, UIComponent component, Object value) {
      if (value==null) return "";
      if (value instanceof String) return ((String)value).replace("\n", "<br>");
      return value.toString();
    }
    
}
