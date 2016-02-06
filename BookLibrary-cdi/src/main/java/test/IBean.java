package test;

import javax.ejb.Remote;

@Remote
public interface IBean {
	
	public String hello();

}
