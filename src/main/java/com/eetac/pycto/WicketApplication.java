package com.eetac.pycto;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 * 
 * @see com.eetac.pycto.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return main.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();
        getRequestCycleSettings().setResponseRequestEncoding("UTF-8"); 
        getMarkupSettings().setDefaultMarkupEncoding("UTF-8"); 
        
		mountPackage("main",main.class);
		mountPackage("terms",terms.class);
		mountPackage("contactUs",contactUs.class);
		mountPackage("index",index.class);


		// add your configuration here
	}
}
