package com.eetac.pycto;

import static org.junit.Assert.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import com.eetac.pycto.managers.ServerBallotBox;

public class test_ballot_box_vote {

	@Test
	public void test() {
		//ServerBallotBox ServerBallotBox = new ServerBallotBox();
		
		// creamos el ballot_vote para que sea correcto:
		// NO MIRAR MUCHO, ES PICO PALA
		// C O M I E N Z O
		String vote_previous_hash = "[{'id':'4'},{'id':'5'},{'id':'6'},{'id':'7'},{'id':'8'}]";
		String vote_post_hash = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(vote_previous_hash.getBytes());
			byte[] passbytes = md.digest();
			
			StringBuilder sb = new StringBuilder();
            for(int i=0; i< passbytes.length ;i++)
            {
                sb.append(Integer.toString((passbytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            vote_post_hash = sb.toString();
            
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		String vote = "[{'id':'4'},{'id':'5'},{'id':'6'},{'id':'7'},{'id':'8'}]..//.."+vote_post_hash+
				"00110011001100110011001100110011001100110011001100110011";
		System.out.println(vote);
		// F I N
		
		//comprobamos si va ok
		//assertEquals("prueba voto no repetido", true, ServerBallotBox.vote(vote));
	}

}
