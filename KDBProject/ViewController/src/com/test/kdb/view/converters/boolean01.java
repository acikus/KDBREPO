package com.test.kdb.view.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class boolean01 implements Converter {
    public boolean01() {
        super();
    }

    public Object getAsObject(FacesContext facesContext,
                              UIComponent uIComponent, String string) {
        if (string.equals("true"))
            return (Long)1L;
        else
            return (Long)0L;
    }

    public String getAsString(FacesContext facesContext,
                              UIComponent uIComponent, Object object) {
        if (object.equals(1))
            return "true";
        else
            return "false";
    }
}
