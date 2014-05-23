package com.eetac.pycto;

import static org.junit.Assert.*;

import org.junit.Test;

import com.eetac.pycto.managers.ServerBallotBox;
import com.eetac.pycto.managers.ServerCACR;
import com.eetac.pycto.models.CA_CR;

public class test_ballot_box_vote_not_repited {

	@Test
	public void test() {
		ServerBallotBox box=new ServerBallotBox();
		assertEquals("prueba voto no repetido", true, box.vote_not_repited("pseudo_jf"));
	}

}
