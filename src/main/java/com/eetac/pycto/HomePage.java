package com.eetac.pycto;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;

import com.eetac.pycto.managers.UserManager;
import com.eetac.pycto.models.User;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		add(new Label("version", getApplication().getFrameworkSettings().getVersion()));

		// TODO Add your page's components here
		
		UserManager mng = new UserManager();
		
		User userRe = new User();
		
		userRe.setUser("jordi");
		userRe.setEmail("jordipalasole@gmail.com");
		userRe.setPassword("ola");
		
		mng.register(userRe);
		
    }
}
