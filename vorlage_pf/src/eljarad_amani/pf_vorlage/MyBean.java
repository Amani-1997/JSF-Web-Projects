/*
 * MyBean.java
 * JSF 2.3 template für PrimeFaces
 */

package eljarad_amani.pf_vorlage;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Backing bean der JSF-Seite vorlage.xhtml
 *
 * @author Wolfgang Lang
 * @version 2.3.0, 2017-07-02
 */
@Named
@RequestScoped
public class MyBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
    
  public MyBean() {
    System.out.println( "MyBean.<init>..."  );
    System.out.println( (new Date()).toString() );
  }
  
  @PostConstruct
  public void init() { System.out.println( "@PostConstruct.MyBean" ); }
  
  public void preRenderAction()  { System.out.println( "MyBean.preRenderAction"  ); }  
  public void postRenderAction() { System.out.println( "MyBean.postRenderAction" ); } 
  
  public Date getDate() { return new Date(); }
}

