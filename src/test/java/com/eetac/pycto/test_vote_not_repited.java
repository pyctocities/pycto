package com.eetac.pycto;

import static org.junit.Assert.*;

import org.junit.Test;

import com.eetac.pycto.managers.PhotoVotes;
import com.eetac.pycto.managers.UserManager;
import com.eetac.pycto.models.Photo;
import com.eetac.pycto.models.User;

public class test_vote_not_repited {

	@Test
	public void test() {
		PhotoVotes test= new PhotoVotes();
		User user= new User( 1, "Juan Francisco", "1234", "jotagarcia1992@gmail.com",0);	
		assertEquals("prueba voto no repetido", true, test.vote_not_repited(user, "foto_a"));
	}

}
