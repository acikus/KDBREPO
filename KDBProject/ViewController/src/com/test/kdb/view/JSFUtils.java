package com.test.kdb.view;



import java.net.MalformedURLException;
import java.net.URL;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import oracle.adf.model.bean.DCDataRow;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adfinternal.view.faces.model.binding.FacesCtrlListBinding;

import oracle.jbo.JboException;
import oracle.jbo.uicli.binding.JUCtrlActionBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


/**
 * General useful static utilies for working with JSF.
 * NOTE: Updated to use JSF 1.2 ExpressionFactory.
 *
 * @author Duncan Mills
 * @author Steve Muench
 * @author Ric Smith
 *
 * $Id: JSFUtils.java 1496 2007-05-03 22:40:49Z lmunsing $
 */
public class JSFUtils {
    private static final String NO_RESOURCE_FOUND = "Missing resource: ";

    /**
     * Method for taking a reference to a JSF binding expression and returning
     * the matching object (or creating it).
     * @param expression EL expression
     * @return Managed object
     */
    public static Object resolveExpression(String expression) {
        FacesContext facesContext = getFacesContext();
        return resolveExpressionInContext(expression, facesContext);
    }

    public static Object resolveExpressionInContext(String expression, 
                                                    FacesContext facesContext) {
        Application app = facesContext.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        ValueExpression valueExp = 
            elFactory.createValueExpression(elContext, expression, 
                                            Object.class);
        return valueExp.getValue(elContext);

    }

    public static Object resolveMethodExpression(String expression, 
                                                 Class returnType, 
                                                 Class[] argTypes, 
                                                 Object[] argValues) {
        FacesContext facesContext = getFacesContext();
        Application app = facesContext.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        MethodExpression methodExpression = 
            elFactory.createMethodExpression(elContext, expression, returnType, 
                                             argTypes);
        return methodExpression.invoke(elContext, argValues);
    }

    /**
     * Method for executing action binding by name, e.g. executeAction("someAction") will invoke "#{bindings.someAction.execute}"
     * @param actionName
     * @return
     */
    public static Object executeAction(String actionName) {
        JUCtrlActionBinding a = 
            (JUCtrlActionBinding)resolveExpression("#{bindings." + actionName + 
                                                   "}");
        return a.execute();
    }

    public static Object executeActionReturnErrors(String actionName) throws JboException {
        JUCtrlActionBinding a = 
            (JUCtrlActionBinding)resolveExpression("#{bindings." + actionName + 
                                                   "}");
        List<JboException> list = a.getErrors();
        for (JboException ex : list)
            throw ex;

        return a.execute();
    }

    /**
     * Method for taking a reference to a JSF binding expression and returning
     * the matching Boolean.
     * @param expression EL expression
     * @return Managed object
     */
    public static Boolean resolveExpressionAsBoolean(String expression) {
        return (Boolean)resolveExpression(expression);
    }

    /**
     * Method for taking a reference to a JSF binding expression and returning
     * the matching String (or creating it).
     * @param expression EL expression
     * @return Managed object
     */
    public static String resolveExpressionAsString(String expression) {
        return (String)resolveExpression(expression);
    }

    /**
     * Convenience method for resolving a reference to a managed bean by name
     * rather than by expression.
     * @param beanName name of managed bean
     * @return Managed object
     */
    public static Object getManagedBeanValue(String beanName) {
        StringBuffer buff = new StringBuffer("#{");
        buff.append(beanName);
        buff.append("}");
        return resolveExpression(buff.toString());
    }

    /**
     * Method for setting a new object into a JSF managed bean
     * Note: will fail silently if the supplied object does
     * not match the type of the managed bean.
     * @param expression EL expression
     * @param newValue new value to set
     */
    public static void setExpressionValue(String expression, Object newValue) {
        FacesContext facesContext = getFacesContext();
        Application app = facesContext.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        ValueExpression valueExp = 
            elFactory.createValueExpression(elContext, expression, 
                                            Object.class);

        //Check that the input newValue can be cast to the property type
        //expected by the managed bean.
        //If the managed Bean expects a primitive we rely on Auto-Unboxing
        Class bindClass = valueExp.getType(elContext);
        if (newValue == null || bindClass.isPrimitive() || 
            bindClass.isInstance(newValue)) {
                valueExp.setValue(elContext, newValue);
        }
    }

    /**
     * Convenience method for setting the value of a managed bean by name
     * rather than by expression.
     * @param beanName name of managed bean
     * @param newValue new value to set
     */
    public static void setManagedBeanValue(String beanName, Object newValue) {
        StringBuffer buff = new StringBuffer("#{");
        buff.append(beanName);
        buff.append("}");
        setExpressionValue(buff.toString(), newValue);
    }


    /**
     * Convenience method for setting Session variables.
     * @param key object key
     * @param object value to store
     */

    public static void storeOnSession(String key, Object object) {
        FacesContext ctx = getFacesContext();
        Map sessionState = ctx.getExternalContext().getSessionMap();
        sessionState.put(key, object);
    }

    public static Object getFromPageFlowScope(String key) {
        return AdfFacesContext.getCurrentInstance().getPageFlowScope().get(key);
    }
    
     public static Object getFromViewScope(String key) {
        return AdfFacesContext.getCurrentInstance().getViewScope().get(key);
    }
    
     public static Object getFromRequestScope(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(key);
    }
     
     public static void putInPageFlowScope(String key, Object value) {
         AdfFacesContext.getCurrentInstance().getPageFlowScope().put(key, value);
     }

    public static void putInViewScope(String key, Object value) {
        AdfFacesContext.getCurrentInstance().getViewScope().put(key, value);
    }

    public static void putInRequestScope(String key, Object value) {
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put(key, value);
    }
     
    /**
     * Pulls a String resource from the property bundle that
     * is defined under the application &lt;message-bundle&gt; element in
     * the faces config. Respects Locale
     * @param key string message key
     * @return Resource value or placeholder error String
     */
    public static String getStringFromBundle(String key) {
        ResourceBundle bundle = getBundle();
        return getStringSafely(bundle, key, null);
    }
    /*
    * Internal method to proxy for resource keys that don't exist
    */

    private static String getStringSafely(ResourceBundle bundle, String key, 
                                          String defaultValue) {
        String resource = null;
        try {
            resource = bundle.getString(key);
        } catch (MissingResourceException mrex) {
            if (defaultValue != null) {
                resource = defaultValue;
            } else {
                resource = NO_RESOURCE_FOUND + key;
            }
        }
        return resource;
    }

    /**
     * Convenience method to construct a <code>FacesMesssage</code>
     * from a defined error key and severity
     * This assumes that the error keys follow the convention of
     * using <b>_detail</b> for the detailed part of the
     * message, otherwise the main message is returned for the
     * detail as well.
     * @param key for the error message in the resource bundle
     * @param severity severity of message
     * @return Faces Message object
     */
    public static FacesMessage getMessageFromBundle(String key, 
                                                    FacesMessage.Severity severity) {
        ResourceBundle bundle = getBundle();
        String summary = getStringSafely(bundle, key, null);
        String detail = getStringSafely(bundle, key + "_Detail", summary);
        return new FacesMessage(severity, summary, detail);
    }

    /**
     * Dodaje se unapred pripremljena(globalna) poruka na stek
     * @param message
     */
    public static void addFacesMessage(FacesMessage message) {
        System.out.println("test utils");
        FacesContext ctx = getFacesContext();
        ctx.addMessage(null, message);
    }

    /**
     * Dodaje se unapred pripremljena poruka, za odredejnu komponentu na strani, na stek
     * @param compId
     * @param message
     */
    public static void addFacesMessageOnComponent(String compId, 
                                                  FacesMessage message) {
        FacesContext ctx = getFacesContext();
        ctx.addMessage(compId, message);
    }

    /**Dodaje se unapred pripremljena poruka, za odredejnu komponentu na strani, na stek
     * @param uiComponent
     * @param message
     */
    public static void addFacesMessageOnComponent(UIComponent uiComponent, 
                                                  FacesMessage message) {
        FacesContext ctx = getFacesContext();
        ctx.addMessage(uiComponent.getClientId(ctx), message);
    }

    /**
     * Dodaje globalnu Info poruku = nije vezana za komponentu
     * @param msg info message string
     */
    public static void addFacesInformationMessage(String msg) {
        FacesContext ctx = getFacesContext();
        FacesMessage fm = 
            new FacesMessage(FacesMessage.SEVERITY_INFO, msg, "");
        //  ctx.addMessage(getRootViewComponentId(), fm);
        // TODO: treba implementirati novu funkciju za globalne poruke jer ovo je za odredjenu komponentu
        ctx.addMessage(null, fm);
    }


    /**
     * Dodaje Info poruku za odredjenu kontrolu
     * @param msg error message string
     */
    public static void addFacesInformationMessageOnComponent(String compId, 
                                                             String msg) {
        FacesContext ctx = getFacesContext();
        FacesMessage fm = 
            new FacesMessage(FacesMessage.SEVERITY_INFO, msg, "");
        ctx.addMessage(compId, fm);
    }


    /**
     * Dodaje Info poruku za odredjenu kontrolu, sa odgovarajucim 'detail' i 'summary'
     * @param compId
     * @param detail
     * @param summary
     */
    public static void addFacesInformationMessageOnComponent(String compId, 
                                                             String detail, 
                                                             String summary) {
        FacesContext ctx = getFacesContext();
        FacesMessage fm = new FacesMessage();
        fm.setSummary(summary);
        fm.setDetail(detail);
        fm.setSeverity(FacesMessage.SEVERITY_INFO);
        ctx.addMessage(compId, fm);
    }

    /**
     * Dodaje globalnu error poruku
     * @param msg error message string
     */
    public static void addFacesErrorMessage(String msg) {
        FacesContext ctx = getFacesContext();
        FacesMessage fm = 
            new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, "");
        ctx.addMessage(null, fm);
    }

    /**
     * Dodaje Error poruku za odredjenu kontrolu
     * @param msg error message string
     */
    public static void addFacesErrorMessageOnComponent(String compId, 
                                                       String msg) {
        FacesContext ctx = getFacesContext();
        FacesMessage fm = 
            new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, "");
        ctx.addMessage(compId, fm);
    }


    /**
     * Dodaje Error poruku za odredjenu kontrolu, sa odgovarajucim 'detail' i 'summary'
     * @param compId
     * @param detail
     * @param summary
     */
    public static void addFacesErrorMessageOnComponent(String compId, 
                                                       String detail, 
                                                       String summary) {
        FacesContext ctx = getFacesContext();
        FacesMessage fm = new FacesMessage();
        fm.setSummary(summary);
        fm.setDetail(detail);
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        ctx.addMessage(compId, fm);
    }


    // Informational getters

    /**
     * Get view id of the view root.
     * @return view id of the view root
     */
    public static String getRootViewId() {
        return getFacesContext().getViewRoot().getViewId();
    }

    /**
     * Get component id of the view root.
     * @return component id of the view root
     */
    public static String getRootViewComponentId() {
        return getFacesContext().getViewRoot().getId();
    }

    /**
     * Get FacesContext.
     * @return FacesContext
     */
    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }
    /*
   * Internal method to pull out the correct local
   * message bundle
   */

    private static ResourceBundle getBundle() {
        return (ResourceBundle)resolveExpression("#{bundle}");

    }

    private static ResourceBundle getBundle(String bundleName) {
        FacesContext ctx = getFacesContext();
        UIViewRoot uiRoot = ctx.getViewRoot();
        Locale locale = uiRoot.getLocale();
        ClassLoader ldr = Thread.currentThread().getContextClassLoader();

        return ResourceBundle.getBundle(bundleName, locale, ldr);
    }

    /**
     * Get an HTTP Request attribute.
     * @param name attribute name
     * @return attribute value
     */
    public static Object getRequestAttribute(String name) {
        return getFacesContext().getExternalContext().getRequestMap().get(name);
    }

    /**
     * Set an HTTP Request attribute.
     * @param name attribute name
     * @param value attribute value
     */
    public static void setRequestAttribute(String name, Object value) {
        getFacesContext().getExternalContext().getRequestMap().put(name, 
                                                                   value);
    }


    /**
     * Locate an UIComponent in view root with its component id. Use a recursive way to achieve this.
     * Taken from http://www.jroller.com/page/mert?entry=how_to_find_a_uicomponent
     * @param id UIComponent id
     * @return UIComponent object
     */
    public static UIComponent findComponentInRoot(String id) {
        UIComponent component = null;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            UIComponent root = facesContext.getViewRoot();
            component = findComponent(root, id);
        }
        return component;
    }

    /**
     * Locate an UIComponent from its root component.
     * Taken from http://www.jroller.com/page/mert?entry=how_to_find_a_uicomponent
     * @param base root Component (parent)
     * @param id UIComponent id
     * @return UIComponent object
     */
    public static UIComponent findComponent(UIComponent base, String id) {
        if (id.equals(base.getId()))
            return base;

        UIComponent children = null;
        UIComponent result = null;
        Iterator childrens = base.getFacetsAndChildren();
        while (childrens.hasNext() && (result == null)) {
            children = (UIComponent)childrens.next();
            if (id.equals(children.getId())) {
                result = children;
                break;
            }
            result = findComponent(children, id);
            if (result != null) {
                break;
            }
        }
        return result;
    }

    /**
     * Method to create a redirect URL. The assumption is that the JSF servlet mapping is
     * "faces", which is the default
     * 
     * @param view the JSP or JSPX page to redirect to
     * @return a URL to redirect to
     */
    public static String getPageURL(String view) {
        FacesContext facesContext = getFacesContext();
        ExternalContext externalContext = facesContext.getExternalContext();
        String url = 
            ((HttpServletRequest)externalContext.getRequest()).getRequestURL().toString();
        StringBuffer newUrlBuffer = new StringBuffer();
        newUrlBuffer.append(url.substring(0, url.lastIndexOf("faces/")));
        newUrlBuffer.append("faces");
        String targetPageUrl = view.startsWith("/") ? view : "/" + view;
        newUrlBuffer.append(targetPageUrl);
        return newUrlBuffer.toString();
    }

    //  UIXSelectOne helpers

    /**
     * Returns selected object from list binding
     * @param componentId - the fully qualified id selector of UIXSelectOne component on the page (e.g. "form1:template1:socExamples")
     * @param listBindning - binding EL expression for this list's ListItems value (e.g. "{bindings.someList}")
     * @return selected value from binding list
     */
    public static final Object getSelectedValue(String componentId, 
                                                String listBindning) {
        org.apache.myfaces.trinidad.component.UIXSelectOne soc = 
            (org.apache.myfaces.trinidad.component.UIXSelectOne)FacesContext.getCurrentInstance().getViewRoot().findComponent(componentId);
        FacesCtrlListBinding lb = 
            (FacesCtrlListBinding)resolveExpression(listBindning);

        assert (lb != null) : "Invalid binding!";
        DCDataRow r = 
            (DCDataRow)lb.getValueFromList(((Integer)soc.getValue()).intValue());

        return r.getDataProvider();
    }

    /**
     * Returns selected object from list binding
     * @param componentId - the fully qualified id selector of UIXSelectOne component on the page (e.g. "form1:template1:socExamples")
     * @param listBindning - binding EL expression for this list's ListItems value (e.g. "{bindings.someList}")
     * @param index - index of ListItem
     * @return selected value from binding list
     */
    public static final Object getSelectedValue(String componentId, 
                                                String listBindning, 
                                                Integer index) {
        org.apache.myfaces.trinidad.component.UIXSelectOne soc = 
            (org.apache.myfaces.trinidad.component.UIXSelectOne)FacesContext.getCurrentInstance().getViewRoot().findComponent(componentId);
        FacesCtrlListBinding lb = 
            (FacesCtrlListBinding)resolveExpression(listBindning);

        assert (lb != null) : "Invalid binding!";
        DCDataRow r = (DCDataRow)lb.getValueFromList(index);

        return r.getDataProvider();
    }

    /**
     * Returns selected object from list binding
     * @param component - UIXSelectOne component
     * @param listBindning - binding EL expression for this list's ListItems value (e.g. "{bindings.someList}")
     * @return
     */
    public static final Object getSelectedValue(UIComponent component, 
                                                String listBindning) {
        org.apache.myfaces.trinidad.component.UIXSelectOne soc = 
            (org.apache.myfaces.trinidad.component.UIXSelectOne)component;
        FacesCtrlListBinding lb = 
            (FacesCtrlListBinding)resolveExpression(listBindning);

        assert (lb != null) : "Invalid binding!";
        DCDataRow r = 
            (DCDataRow)lb.getValueFromList(((Integer)soc.getValue()).intValue());

        return r.getDataProvider();
    }

    /**
     * Returns selected object from list binding
     * @param component - UIXSelectOne component
     * @param listBindning - binding EL expression for this list's ListItems value (e.g. "{bindings.someList}")
     * @param index - index of ListItem
     * @return
     */
    public static final Object getSelectedValue(UIComponent component, 
                                                String listBindning, 
                                                Integer index) {
        org.apache.myfaces.trinidad.component.UIXSelectOne soc = 
            (org.apache.myfaces.trinidad.component.UIXSelectOne)component;
        FacesCtrlListBinding lb = 
            (FacesCtrlListBinding)resolveExpression(listBindning);

        assert (lb != null) : "Invalid binding!";
        DCDataRow r = (DCDataRow)lb.getValueFromList(index);

        return r.getDataProvider();
    }


    /**
     * Returns a value of the component at this phase of page lifecycle:
     *  - submittedValue, if null
     *      - localValue, if null
     *          - value
     * @param uiComponent
     * @return value of component available at this phase
     */
    public static Object getEarlyValue(EditableValueHolder uiComponent) {
        Object value = uiComponent.getSubmittedValue();
        if (value == null)
            value = uiComponent.getLocalValue();
        if (value == null)
            value = uiComponent.getValue();

        return (value == null ? "" : value);
    }

    public static void selectRowOnBinding(String binding, 
                                          SelectionEvent selectionEvent) {
        oracle.adfinternal.view.faces.model.binding.FacesCtrlHierBinding.FacesModel cm = 
            (oracle.adfinternal.view.faces.model.binding.FacesCtrlHierBinding.FacesModel)JSFUtils.resolveExpression("#{bindings." + 
                                                                                                                    binding + 
                                                                                                                    ".collectionModel}");
        cm.makeCurrent(selectionEvent);
    }

    public static void addClientScript(String s) {
        ExtendedRenderKitService service = 
            (ExtendedRenderKitService)Service.getRenderKitService(FacesContext.getCurrentInstance(), 
                                                                  ExtendedRenderKitService.class);
        service.addScript(FacesContext.getCurrentInstance(), s);
    }

    public static String getAbsolutePath(String relativePath) {
        //TODO: prepraviti da koristi parametar relativePath - sada radi sa tekucim request-om!!!
        HttpServletRequest request = 
            (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String file = request.getRequestURI();
        if (request.getQueryString() != null) {
            file += '?' + request.getQueryString();
        }
        try {
            URL reconstructedURL = 
                new URL(request.getScheme(), request.getServerName(), 
                        request.getServerPort(), file);
            return reconstructedURL.toString();
        } catch (MalformedURLException e) {
            return null;
        }
    }
    
}
