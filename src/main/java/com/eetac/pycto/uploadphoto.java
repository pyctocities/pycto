package com.eetac.pycto;

import java.io.File;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.lang.Bytes;

public class uploadphoto extends WebPage {
	private static final long serialVersionUID = 1L;
	
	private FileUploadField fileUpload;
	private String UPLOAD_FOLDER = "C:\\xampp\\htdocs\\images\\";

	public uploadphoto(final PageParameters parameters) {
		super(parameters);

		Session sesio = getSession();
		info(UPLOAD_FOLDER);
		
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
		
		final Link stats = new Link("stats")
		{
			@Override
			public void onClick()  //Cuan apretem el boto de facebook, fara aixo
			{
				PageParameters pageParameters = new PageParameters();
				setResponsePage(stats.class, pageParameters);
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
		final Link profile2 = new Link("profile2")
		{
			@Override
			public void onClick()  //Cuan apretem el boto de facebook, fara aixo
			{
				PageParameters pageParameters = new PageParameters();
				setResponsePage(profile.class, pageParameters);
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
		add(profile2);
		add(gallery);
		add(profile);
		add(stats);
		add(indexTerms);
		add(index);
		add(votebox);
		
		
		add(new FeedbackPanel("feedback"));
		 
		Form<?> form = new Form<Void>("form") {
		 @Override
		 protected void onSubmit() {
 
			final FileUpload uploadedFile = fileUpload.getFileUpload();
			if (uploadedFile != null) {
 
				// write to a new file
				File newFile = new File(UPLOAD_FOLDER
					+ uploadedFile.getClientFileName());
 
				if (newFile.exists()) {
					newFile.delete();
				}
 
				try {
					newFile.createNewFile();
					uploadedFile.writeTo(newFile);
 
					info("saved file: " + uploadedFile.getClientFileName());
				} catch (Exception e) {
					throw new IllegalStateException("Error");
				}
			 }
 
			}
 
		};
 
		// Enable multipart mode (need for uploads file)
		form.setMultiPart(true);
 
		// max upload size, 10k
		form.setMaxSize(Bytes.kilobytes(10000));
 
		form.add(fileUpload = new FileUploadField("fileUpload"));
 
		add(form);
		
		
    }
	
	
}
