package com.test.kdb.model.utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;




public class ModelUtils implements Serializable {


    private static final String MODEL_BUNDLE_NAME = "com.test.kdb.model.ModelBundle";
    private static ResourceBundle bundle = ResourceBundle.getBundle( MODEL_BUNDLE_NAME );
    private static final String NO_RESOURCE_FOUND = "Missing resource: ";
    @SuppressWarnings("compatibility:6899623834803897217")
    private static final long serialVersionUID = 1L;

    public ModelUtils() {
        super();
    }

    public static String getStringFromBundle( String key ) {
        return getStringSafely( bundle, key, null );
    }

    private static String getStringSafely( ResourceBundle bundle, String key, String defaultValue ) {
        String resource = null;
        try {
            resource = bundle.getString( key );
        } catch ( MissingResourceException mrex ) {
            if ( defaultValue != null ) {
                resource = defaultValue;
            } else {
                resource = NO_RESOURCE_FOUND + key;
            }
        }
        return resource;
    }

//    public static void sacuvajPrilogNaDisk( PrilogDeklaracijiZaglavlje in, File out ) throws IOException {
//        int b;
//        InputStream is = in.getInputStream();
//        FileOutputStream fos = new FileOutputStream( out );
//        while ( ( b = is.read() ) != -1 ) {
//            fos.write( b );
//        }
//        fos.flush();
//        is.close();
//        fos.close();
//    }

    public static XMLGregorianCalendar convertToXMLGC( Calendar calendar ) {
        if ( calendar == null )
            return null;

        XMLGregorianCalendar xmlCalendar = null;
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis( calendar.getTimeInMillis() );
        try {
            xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar( gc );
        } catch ( DatatypeConfigurationException e ) {
            e.printStackTrace();
        }
        return xmlCalendar;
    }

    public static String vratiSlovo( String unos ) {
        String retVal = null;
        if ( unos != null ) {
            String[] nizKaraktera = unos.split( "[0-9]", 10 );
            for ( String deo : nizKaraktera ) {
                if ( ( deo != null ) && ( !deo.isEmpty() ) ) {
                    retVal = deo.trim().replace( "/", "" );
                    if ( retVal.length() > 1 )
                        retVal = null;
                    return retVal;
                }
            }
        }
        return retVal;
    }

    public static Integer vratiBroj( String unos ) {
        Integer cifre = null;
        String retVal = null;
        if ( unos != null ) {
            String[] nizKaraktera = unos.split( "[a-z A-Z đ Đ ž Ž ć Ć č Č š Š /]", 10 );
            for ( String deo : nizKaraktera ) {
                if ( ( deo != null ) && ( !deo.isEmpty() ) ) {
                    retVal = deo.trim();
                    cifre = Integer.parseInt( retVal );
                    return cifre;
                }
            }
        }
        return cifre;
    }

    public static String formirajPNBPoModulu97( String SO, String PIB_JMBG ) {
        String PNB = null;
        String KK = null;
        String OOO = SO;
        Long tmp = Long.parseLong( SO + PIB_JMBG );
        KK = String.format( "%02d", ( 98 - ( ( tmp * 100 ) % 97 ) ) );
        PNB = KK + "-" + OOO + "-" + PIB_JMBG;
        return PNB;
    }

    public static String formirajKontrolnuCifruPoModulu97( String SO ) {
        String KK = null;
        Long tmp = Long.parseLong( SO );
        KK = String.format( "%02d", ( 98 - ( ( tmp * 100 ) % 97 ) ) );
        return KK;
    }

    public static String formirajPNBZaZalbu( String SO ) {
        String PNB = null;
        if ( SO != null && !SO.isEmpty() ) {
            if ( "CVP".equals( SO ) ) {
                PNB = "97 44-018";
            } else {
                PNB = "97 " + ModelUtils.formirajKontrolnuCifruPoModulu97( SO ) + "-" + SO;
            }
        }
        return PNB;
    }



    public static String initStringParam( String param ) {
        if ( param != null && param.isEmpty() ) {
            param = null;
        } else if ( param != null && !param.isEmpty() ) {
            String neprihvatljiviKarakteri =
                "[^АБВГДЂЕЖЗИЈКЛЉМНЊОПРСТЋУФХЦЧЏШабвгдђежзијклљмнњопрстћуфхцчџш ABVGDĐEŽZIJKLMNOPRSTĆUFHCXYQWČŠabvgdđežzijklmnoprstćufhcčšxyqw& 0-9]+";
            param = param.replaceAll( neprihvatljiviKarakteri, "*" );
            if ( param.startsWith( "*" ) )
                param = param.substring( 1 );
            if ( param.endsWith( "*" ) )
                param = param.substring( 0, param.length() - 1 );
            if ( param.length() < 3 )
                param = null;
        }
        return param;
    }

    public static boolean istiIznosi( Double i1, Double i2 ) {
        boolean isti = false;
        if ( i1 == null && i2 == null )
            isti = true;
        else if ( i1 != null )
            isti = i1.equals( i2 );
        return isti;
    }
}