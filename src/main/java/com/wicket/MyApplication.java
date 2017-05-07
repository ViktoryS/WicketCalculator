package com.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import com.wicket.calculator.Calculator;

public class MyApplication extends WebApplication {

	@Override
	public Class<? extends Page> getHomePage() {
		return Calculator.class; //return default page
	}

}
