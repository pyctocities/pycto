package com.eetac.pycto;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.WebPage;

import com.eetac.pycto.models.Ballot_Box;

public class terms extends WebPage {
	private static final long serialVersionUID = 1L;

	public terms(final PageParameters parameters) {
		super(parameters);

		final Link main = new Link("main")
		{
			@Override
			public void onClick()  //Cuan apretem el boto de facebook, fara aixo
			{
				PageParameters pageParameters = new PageParameters();
				setResponsePage(main.class, pageParameters);
			}
		};
		
		final Link contactUs = new Link("contactUs")
		{
			@Override
			public void onClick()  //Cuan apretem el boto de facebook, fara aixo
			{
				PageParameters pageParameters = new PageParameters();
				setResponsePage(contactUs.class, pageParameters);
			}
		};
		
		add(main);
		add(contactUs);
		
    }
}
