package com.eetac.pycto;

import static org.junit.Assert.*;

import org.junit.Test;

import com.eetac.pycto.managers.PhotoVotes;
import com.eetac.pycto.models.User;

public class test_obtain_num_votes_by_user {

	@Test
	public void test() {
		PhotoVotes test= new PhotoVotes();
		User user= new User( 1, "Juan Francisco", "1234", "jotagarcia1992@gmail.com",0);	
		assertEquals("prueba voto no repetido", 4, test.obtain_num_votes_of_user(user));
	}

}
