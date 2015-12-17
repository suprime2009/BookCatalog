package com.softserveinc.model.persist.backing;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="resultBean")
@RequestScoped   //You can use @ViewScoped
public class ResultBean implements Serializable {
 
    private static final long serialVersionUID = 1L;
 
    public String result() {
 
        System.out.println("Inside Result Bean");
        return "success";
 
    }
 
}