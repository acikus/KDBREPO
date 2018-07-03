package com.test.kdb.view.helpers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.context.FacesContext;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.myfaces.trinidad.model.UploadedFile;

import com.test.kdb.model.DocumentsSessionEJBLocal;
import com.test.kdb.model.entity.Document;


//import java.io.FileInputStream;
//import java.io.OutputStreamWriter;

//import javax.ejb.EJB;

//import javax.el.ELContext;
//import javax.faces.application.FacesMessage;
//import javax.faces.el.ValueBinding;
//import javax.faces.event.ValueChangeEvent;


//import javax.servlet.http.HttpServletResponse;
//import oracle.adf.model.BindingContext;

//import com.test.kdb.model.entity.DocExtension;

//import weblogic.jndi.Environment;

//import weblogic.rmi.extensions.PortableRemoteObject;


public class UpDownLoadDocument {
    
    private UploadedFile uploadedFile;
    private DocumentsSessionEJBLocal documentsSessionEJB;
    
    public UpDownLoadDocument() 
    {
        try {
            final Context ctx = new InitialContext();
            documentsSessionEJB = (DocumentsSessionEJBLocal)
                ctx.lookup("java:comp/env/ejb/local/DocumentsSessionEJB");
        } catch (NamingException e) {
            System.out.println("Naming exception occurred: " + e.getMessage());
        }
    }
    
    // get a value
    private Object getValueExpression(String name) 
    {  
        FacesContext ctx = FacesContext.getCurrentInstance();  
        ExpressionFactory ef = ctx.getApplication().getExpressionFactory();  
        ValueExpression ve = ef.createValueExpression(ctx.getELContext(), 
            name, Object.class);
        return ve.getValue(ctx.getELContext());
    }

    /**
     * @return
     */
    public UploadedFile getUploadedFile() 
    {
            return uploadedFile;
    }

    /**
     * @param uploadedFile
     */
    public void setUploadedFile(UploadedFile uploadedFile) 
    {
        Document document = (Document)getValueExpression("#{bindings.findOrCreate.result}");
        String filename;
        
        filename = uploadedFile.getFilename().toLowerCase();
        document.setFilename(filename);
        if (uploadedFile != null && uploadedFile.getLength() > 0) {
            try {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buff = new byte[1024];

                InputStream in = uploadedFile.getInputStream();
                long len = in.read(buff);
                while (len > 0) {
                    out.write(buff, 0, (int)len);
                    len = in.read(buff);
                }
                document.setObject(out.toByteArray());
                in.close();
                out.close();

            } catch (IOException e) { 
                e.printStackTrace();

            }
        }
        return;
    }

    // download BLOB to file

    /**
     * @param fctx
     * @param out
     * @throws IOException
     */
    public void FileDownload(FacesContext fctx, OutputStream out)  throws IOException
    {
        Document document;
        
        // lazy fetch Document object
        document = documentsSessionEJB.lazyFetchDocumentObject(
            (Document)getValueExpression("#{pageFlowScope.selectedDocument}"));
        
        if (document.getName() != null) {
            byte[] blobObject = document.getObject();
            if (blobObject == null)
                return;
            try {
                // output document body and increment counter
                out.write(blobObject, 0, blobObject.length);
                out.flush();
                documentsSessionEJB.incrementDocumentCounter(document);
            } catch (IOException e) {
                    // TODO
                    e.printStackTrace();
                    // no error message shown to the user. Consider adding a Faces
                    // Message
                    return;
            }
        }
        return;
    }
}
