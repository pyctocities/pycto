package com.eetac.pycto;

import java.util.List;

import org.apache.wicket.Session;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.WebPage;

import com.eetac.pycto.managers.ServerBallotBox;
import com.eetac.pycto.models.Ballot_Box;

public class gallery extends WebPage {
	private static final long serialVersionUID = 1L;

	public gallery(final PageParameters parameters) {
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
		
		final Link votebox = new Link("votebox")
		{
			@Override
			public void onClick()  //Cuan apretem el boto de facebook, fara aixo
			{
				PageParameters pageParameters = new PageParameters();
				setResponsePage(votebox.class, pageParameters);
			}
		};
		
		final Link stats = new Link("stats")
		{
			@Override
			public void onClick()  //Cuan apretem el boto de facebook, fara aixo
			{
				PageParameters pageParameters = new PageParameters();
				setResponsePage(stats.class, pageParameters);
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
		
		ServerBallotBox m = new ServerBallotBox();
		
		List<String> images = m.listimages();
		
		String url="";
		for(String file:images)
		{
			url = url+"<div class=\"col-lg-3 col-md-4 col-xs-6 thumb\">"+ 
		            "<a class=\"thumbnail\" href=\"#\">"+
		               " <img class=\"img-responsive\" src=\""+"http://localhost/images/"+file+"\" width=\"400\" height=\"300\">"+
		               "<p></p>"+
		                "<p><span>"+file+"</span></p>"+
		            "</a>"+
		        "</div>";
		}
		
		Label spanurl = new Label("gallery",url);
		spanurl.setEscapeModelStrings(false);

		add(aboutus);
		add(uploadphoto);
		add(profile);
		add(stats);
		add(indexTerms);
		add(index);
		add(spanurl);
		add(votebox);
    }
}
