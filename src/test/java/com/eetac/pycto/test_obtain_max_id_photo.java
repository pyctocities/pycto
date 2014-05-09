package com.eetac.pycto;

import static org.junit.Assert.*;

import org.junit.Test;

import com.eetac.pycto.managers.PhotoVotes;
import com.eetac.pycto.managers.UserManager;

public class test_obtain_max_id_photo {

	@Test
	public void test() {
		PhotoVotes test= new PhotoVotes();
		assertEquals("prueba maxima id de photo", 9, test.obtain_max_photo_vote());
	}

}
