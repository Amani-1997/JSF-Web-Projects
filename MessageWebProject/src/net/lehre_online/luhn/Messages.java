/*
 * Messages.java
 */
 
package net.lehre_online.luhn;

import java.text.MessageFormat;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;


/**
 * Ausgabe mehrsprachiger Nachrichten und Fehlermeldungen.<br>
 * Die Klasse wurde übernommen aus <i>Core JavaServer Faces</i>
 * und für diese Anwendung angepasst.
 *
 * @author  Wolfgang Lang
 * @version 1.0.9, 2014-05-25
 * @see     "Geary/Horstmann, Core JavaServer Faces, 2010, S. 281 ff."
 * @see     "Foliensatz zur Vorlesung"
 */
public class Messages {
 
  /**
   * Eine Fehlermeldung besteht aus einer Kurz- und einer Langversion. Die 
   * Kurzversion wird im message file unter einem key abgelegt. Die Langversion
   * hat denselben key, ergänzt um DETAIL_SUFFIX. Beispiel:
   * <pre>
   * msgHallo=Hallo
   * msgHallo_detail=Ich bin der Hallo Langtext
   * </pre>
   */
  final static String DETAIL_SUFFIX = "_detail";
  
  /*--------------------------------------------------------------------------*/
  
  /**
   * Ausgabe einer Fehlermeldung ohne Parameter
   * @param bundle File mit Fehlermeldung
   * @param msgId  Id der Fehlermeldung
   */
  public static void addErrMsg( String bundle, String msgId ){                              

    FacesContext.getCurrentInstance().addMessage( null,
         getMessage( bundle, FacesMessage.SEVERITY_ERROR, msgId, null ));      
  }
  
  /*--------------------------------------------------------------------------*/
  
  /**
   * Ausgabe einer Meldung vom Typ Info ohne Parameter
   * @param bundle File mit Meldung
   * @param msgId  Id der Meldung
   */
  public static void addInfoMsg( String bundle, String msgId ){                              

    FacesContext.getCurrentInstance().addMessage( null,
         getMessage( bundle, FacesMessage.SEVERITY_INFO, msgId, null ));      
  }

  /*--------------------------------------------------------------------------*/
  
  /**
   * Ausgabe einer Fehlermeldung mit Parameter
   * @param bundle File mit Fehlermeldung
   * @param msgId  Id der Fehlermeldung
   * @param params Parameter
   */
  public static void addErrMsg( String bundle, String msgId, Object[] params ){                              

    FacesContext.getCurrentInstance().addMessage( null,
         getMessage( bundle, FacesMessage.SEVERITY_ERROR, msgId, params ));      
  }
  
  /*--------------------------------------------------------------------------*/
  
  /**
   * Ausgabe einer Meldung vom Typ Info mit Parameter
   * @param bundle File mit Meldung
   * @param msgId  Id der Meldung
   * @param params Parameter
   */
  public static void addInfoMsg( String bundle, String msgId, Object[] params ){                              

    FacesContext.getCurrentInstance().addMessage( null,
         getMessage( bundle, FacesMessage.SEVERITY_INFO, msgId, params ));      
  }
  
  /*--------------------------------------------------------------------------*/
  
  /**
   * Generiert eine Info- oder Fehlermeldung. Einfacher ist allerdings die 
   * Verwendung der Methoden addInfoMsg() und addErrMsg().
   * @return Nachricht oder Fehlermeldung
   */
  public static FacesMessage getMessage( String bundleName, 
                                         FacesMessage.Severity severity,
                                         String msgId,
                                         Object[] params ) {
                                         
    FacesContext context = FacesContext.getCurrentInstance();
    Application app = context.getApplication();
    String appBundle = app.getMessageBundle();
    Locale locale = getLocale( context );
    ClassLoader loader = getClassLoader();
    String summary = getString( appBundle, bundleName, msgId, 
                                locale, loader, params );
    if( summary == null ) summary = "???" + msgId + "???";
    String detail = getString( appBundle, bundleName, msgId + DETAIL_SUFFIX, 
                               locale, loader, params);
    
    return new FacesMessage( severity, summary, detail );
  }

  /*
  private static String getString( String bundle, String msgId, 
                                  Object[] params ) {
                                   
      FacesContext context = FacesContext.getCurrentInstance();
      Application app = context.getApplication();
      String appBundle = app.getMessageBundle();
      Locale locale = getLocale( context );
      ClassLoader loader = getClassLoader();
      
      return getString( appBundle, bundle, msgId, locale, loader, params );
  } */ 

  /*--------------------------------------------------------------------------*/
  
  private static String getString( String bundle1, String bundle2, 
                                  String msgId, Locale locale,
                                  ClassLoader loader, Object[] params ) {
      String resource = null;
      ResourceBundle bundle;
      
      if (bundle1 != null) {
         bundle = ResourceBundle.getBundle(bundle1, locale, loader);
         if (bundle != null)
            try {
               resource = bundle.getString( msgId );
            } catch (MissingResourceException ex) { 
              System.err.println( "MissingResourceException" );
              ex.toString();
            }
      }

      if (resource == null) {
         bundle = ResourceBundle.getBundle( bundle2, locale, loader );
         if (bundle != null)
            try {
               resource = bundle.getString( msgId );
            } catch (MissingResourceException ex) {
              ex.toString();
            }
      }

      if( resource == null ) return null; // no match
      if( params   == null ) return resource;
      
      MessageFormat formatter = new MessageFormat( resource, locale );      
      return formatter.format( params );
   }   

  /*--------------------------------------------------------------------------*/
  
  private static Locale getLocale( FacesContext context ) {
    
    Locale locale = null;
    UIViewRoot viewRoot = context.getViewRoot();
    if (viewRoot != null) locale = viewRoot.getLocale();
    if (locale == null) locale = Locale.getDefault();
      
    return locale;
  }
  
  /*--------------------------------------------------------------------------*/
  
  private static ClassLoader getClassLoader() {
     
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    if (loader == null) loader = ClassLoader.getSystemClassLoader();
    
    return loader;
  }
}