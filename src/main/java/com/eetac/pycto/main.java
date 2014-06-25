package com.eetac.pycto;

import java.util.Scanner;

import javax.servlet.http.HttpSession;

import org.apache.wicket.Session;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import org.apache.wicket.markup.html.link.Link;

import com.eetac.pycto.managers.ServerCACR;
import com.eetac.pycto.models.Ballot_Box;
import com.eetac.pycto.models.CA_CR;



public class main extends WebPage {
	private static final long serialVersionUID = 1L;

	public main(final PageParameters parameters) {
		super(parameters);
		add(new org.apache.wicket.markup.html.panel.FeedbackPanel("feedback"));

		final Session session = getSession();
		
		final TextField userlogin = new TextField("usuari_login", Model.of(""));
		final PasswordTextField passwordlogin = new PasswordTextField("password_login", Model.of(""));
	   

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
		
		final TextField correuregister = new TextField("correu_register", Model.of(""));
		final TextField dniregister = new TextField("dni_register", Model.of(""));
		final PasswordTextField passregister = new PasswordTextField("password_register", Model.of(""));
		final PasswordTextField passregister2 = new PasswordTextField("password_register2", Model.of(""));


		Form<?> formregister = new Form<Void>("formregister"){
			@Override
            public void onSubmit()
            {
				ServerCACR cacr=new ServerCACR();

			
				String correu = correuregister.getValue();
				String dni = dniregister.getValue();
				String pass = passregister.getValue();
				String pass2 = passregister2.getValue();

				
				if(pass.equals(pass2))  //mira si los passwords son iguales
				{
					CA_CR user=new CA_CR(dni, correu, pass);

					if(cacr.find_user(user))
					{
						error("El usuario ya existe");

					}
					else
					{
						cacr.register(user);
						info("El usuario se ha registrado correctamente");
					}

				}
				else
				{
					error("Los passwords no son iguales");

				}

            }
			
		};
		formregister.add(correuregister);
		formregister.add(dniregister);
		formregister.add(passregister);
		formregister.add(passregister2);

		add(formregister);
		
		
		final Link terms = new Link("terms")
		{
			@Override
			public void onClick()  //Cuan apretem el boto de facebook, fara aixo
			{
				PageParameters pageParameters = new PageParameters();
				setResponsePage(terms.class, pageParameters);
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
		
		add(terms);
		add(contactUs);

		
    }
}
