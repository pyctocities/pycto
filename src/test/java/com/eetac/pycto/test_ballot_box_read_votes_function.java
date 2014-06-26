package com.eetac.pycto;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.junit.Test;

import com.eetac.pycto.managers.ServerBallotBox;

public class test_ballot_box_read_votes_function {

	@Test
	public void test() {

		ServerBallotBox server =new ServerBallotBox();
		try {
			server.read_votes_from_a_file();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
