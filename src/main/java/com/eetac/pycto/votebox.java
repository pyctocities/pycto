package com.eetac.pycto;

import java.io.IOException;
import java.util.List;

import org.apache.wicket.Session;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.WebPage;

import com.eetac.pycto.managers.ServerBallotBox;
import com.eetac.pycto.models.Ballot_Box;


public class votebox extends WebPage {
	private static final long serialVersionUID = 1L;

	public votebox(final PageParameters parameters) {
		super(parameters);

		
		
		Session sesio = getSession();
		
		if(sesio.getAttribute("user")==null)
		{
        	PageParameters pageParameters = new PageParameters();
			setResponsePage(votebox.class, pageParameters);	
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
		String url = "";
		try {
			List<String> ola = m.getvotes();
			int i =0;
			for(String linea:ola)
			{
					if(i==0)
					{
						url = "<tr>"+
								"<td>"+linea+"</td>"+
								
								"</tr>";
						
					}
					else
					{
						url = url+"<tr>"+
								"<td>"+linea+"</td>"+
								"</tr>";
					}
					
					i++;
				
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Label votebox = new Label("votebox",url);
		votebox.setEscapeModelStrings(false);

		add(index);
		add(aboutus);
		add(votebox);
		add(uploadphoto);
		add(profile);
		add(stats);
		add(gallery);
		add(indexTerms);


    }
}
