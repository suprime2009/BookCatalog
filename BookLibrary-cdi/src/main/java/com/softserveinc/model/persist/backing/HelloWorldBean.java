package com.softserveinc.model.persist.backing;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean(name="helloWorldBean")
//@RequestScoped
@SessionScoped
public class HelloWorldBean implements Serializable {


  /**
	 * 
	 */
	private static final long serialVersionUID = -8479335788017119473L;
private String name;

  public String hello() {

      System.out.println("Calling hellworld.xhtml");

      return "success";

  }

  public String getCurrentTime() {

      return new SimpleDateFormat("dd MMM yyyy:HH:mm:SS").format(new java.util.Date().getTime()); // Older version, SimpleDateFormat is not thread safe
      //return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM-yyyy HH:mm:ss"));  // Requires Java 8
  }
  public String getName() {
      return name;
  }
  public void setName(String name) {
      this.name = name;
  }
}
