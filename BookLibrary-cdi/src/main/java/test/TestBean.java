package test;

import javax.ejb.Stateless;

@Stateless
public class TestBean implements ILocal, IBean{


	public String hello() {
	
		return "Hello bean";
	}
	


}
