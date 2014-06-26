package com.eetac.pycto;

import org.apache.wicket.Session;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.WebPage;

import com.eetac.pycto.managers.ServerCACR;
import com.eetac.pycto.models.Ballot_Box;

public class profile extends WebPage {
	private static final long serialVersionUID = 1L;

	public profile(final PageParameters parameters) {
		super(parameters);

		ServerCACR s = new ServerCACR();

		Session sesio = getSession();

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
		if(sesio.getAttribute("user")==null)
		{
        	PageParameters pageParameters = new PageParameters();
			setResponsePage(index.class, pageParameters);	
		}
		
		String nom = (String) sesio.getAttribute("user");
		
		Label nombre = new Label("nombre",nom);
		
		Label dni = new Label("dni",s.obtain_dni(nom));
		
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
		
		final Link stats = new Link("stats")
		{
			@Override
			public void onClick()  //Cuan apretem el boto de facebook, fara aixo
			{
				PageParameters pageParameters = new PageParameters();
				setResponsePage(stats.class, pageParameters);
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
		
		final Link uploadphoto = new Link("uploadphoto")
		{
			@Override
			public void onClick()  //Cuan apretem el boto de facebook, fara aixo
			{
				PageParameters pageParameters = new PageParameters();
				setResponsePage(uploadphoto.class, pageParameters);
			}
		};
		
		final Link uploadphoto1 = new Link("uploadphoto1")
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
		add(nombre);
		add(dni);
		add(uploadphoto);
		add(uploadphoto1);
		add(gallery);
		add(stats);
		add(indexTerms);
		add(index);
		
    }
}
