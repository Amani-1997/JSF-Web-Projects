package net.lehre_online.luhn;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;



@Named
@SessionScoped
public class Mb implements Serializable{


	private static final long serialVersionUID = 1L;
	
	private String summary = " ";
	private String detail = " ";
	
	public Mb(){
		System.out.println( "Mb.<init>..." );
		System.out.println((new Date ()).toString());
	}
	
	@PostConstruct
	public void init () { System.out.println("@PostConstruct.Mb"); }
	
	public void preRenderAction()  {System.out.println("Mb.preRenderAction" );}
	public void postRenderAction() {System.out.println("Mb.postRenderAction");}
	
	public String getSummary() {return summary;}
	public String getDetail()  {return detail;}
	
	public void setSummary(String s) {summary = s;}
	public void setDetail(String d)  {detail = d;}
	

	public void aclWarning( ActionEvent ae) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, summary, detail));
	}
	
	public void aclError( ActionEvent ae) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
	}
	
	public void aclInfo( ActionEvent ae) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
	}
	
	public void aclFatal( ActionEvent ae) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_FATAL, summary, detail));
	}
}
