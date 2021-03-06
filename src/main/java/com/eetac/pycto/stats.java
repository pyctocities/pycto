package com.eetac.pycto;

import org.apache.wicket.Session;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.WebPage;

import com.eetac.pycto.models.Ballot_Box;

public class stats extends WebPage {
	private static final long serialVersionUID = 1L;

	public stats(final PageParameters parameters) {
		super(parameters);
		Session sesio = getSession();
		
		if(sesio.getAttribute("user")==null)
		{
        	PageParameters pageParameters = new PageParameters();
			setResponsePage(index.class, pageParameters);	
		}
		
		final Link<?> logout = new Link<Object>("logout")
		        {
		            @Override
		            public void onClick()  //Quan apretem el boto de facebook, fara aixo
		            {
					  Session session = this.getSession();
					  session.invalidateNow();
					  
					  PageParameters pageParameters = new PageParameters();
					  setResponsePage(main.class, pageParameters);
					  
							
		            }
		        };
		
		add(logout);
		final Link index = new Link("index")
		{
			@Override
			public void onClick()  //Cuan apretem el boto de facebook, fara aixo
			{
				PageParameters pageParameters = new PageParameters();
				setResponsePage(index.class, pageParameters);
			}
		};
		
		final Link indexTerms = new Link("indexTerms")
		{
			@Override
			public void onClick()  //Cuan apretem el boto de facebook, fara aixo
			{
				PageParameters pageParameters = new PageParameters();
				setResponsePage(indexTerms.class, pageParameters);
			}
		};
		
		final Link gallery = new Link("gallery")
		{
			@Override
			public void onClick()  //Cuan apretem el boto de facebook, fara aixo
			{
				PageParameters pageParameters = new PageParameters();
				setResponsePage(gallery.class, pageParameters);
			}
		};
		
		final Link votebox = new Link("votebox")
		{
			@Override
			public void onClick()  //Cuan apretem el boto de facebook, fara aixo
			{
				PageParameters pageParameters = new PageParameters();
				setResponsePage(votebox.class, pageParameters);
			}
		};
		
		final Link profile = new Link("profile")
		{
			@Override
			public void onClick()  //Cuan apretem el boto de facebook, fara aixo
			{
				PageParameters pageParameters = new PageParameters();
				setResponsePage(profile.class, pageParameters);
			}
		};
		
		final Link uploadphoto = new Link("uploadphoto")
		{
			@Override
			public void onClick()  //Cuan apretem el boto de facebook, fara aixo
			{
				PageParameters pageParameters = new PageParameters();
				setResponsePage(uploadphoto.class, pageParameters);
			}
		};
		
		final Link aboutus = new Link("aboutus")
		{
			@Override
			public void onClick()  //Cuan apretem el boto de facebook, fara aixo
			{
				PageParameters pageParameters = new PageParameters();
				setResponsePage(aboutus.class, pageParameters);
			}
		};
		
		add(aboutus);
		add(uploadphoto);
		add(profile);
		add(gallery);
		add(indexTerms);
		add(index);
		add(votebox);
		
    }
}
