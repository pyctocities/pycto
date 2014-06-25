package com.eetac.pycto;

import javax.servlet.http.HttpSession;

import org.apache.wicket.Session;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;

import com.eetac.pycto.managers.ServerCACR;
import com.eetac.pycto.models.Ballot_Box;


public class main extends WebPage {
	private static final long serialVersionUID = 1L;

	public main(final PageParameters parameters) {
		super(parameters);
		add(new org.apache.wicket.markup.html.panel.FeedbackPanel("feedback"));

		
		final TextField userlogin = new TextField("usuari_login", Model.of(""));
		final PasswordTextField passwordlogin = new PasswordTextField("password_login", Model.of(""));
	    final Session session = getSession();

		Form<?> formlogin = new Form<Void>("formlogin"){
			@Override
            public void onSubmit()
            {
			
				String user = userlogin.getValue();
				String pass = passwordlogin.getValue();
				System.out.println("nom:"+ user +" Passwrd:"+ pass);
				
				
				ServerCACR cacr=new ServerCACR();
				
				if(cacr.login(user,pass))
				{
			    	session.setAttribute("user", user);
			    	info("Login correcte");
			    	
	        /*    	PageParameters pageParameters = new PageParameters();
					setResponsePage(mainshop.class, pageParameters);
					*/
				}
				else
				{
					error("Usuari i/o contrasenya incorrectes");
				}
				

            }
			
		};
		formlogin.add(userlogin);
		formlogin.add(passwordlogin);
		add(formlogin);
		
		
    }
}
