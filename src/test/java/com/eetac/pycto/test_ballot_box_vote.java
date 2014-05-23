package com.eetac.pycto;

import static org.junit.Assert.*;

import org.junit.Test;

import com.eetac.pycto.managers.ServerBallotBox;
import com.eetac.pycto.models.Ballot_Box;

public class test_ballot_box_vote {

	@Test
	public void test() {
		ServerBallotBox box=new ServerBallotBox();
		Ballot_Box user = new Ballot_Box("1", "2","JSON");
		assertEquals("prueba voto ", "ok", box.vote(user, "JSON"));
	}

}
