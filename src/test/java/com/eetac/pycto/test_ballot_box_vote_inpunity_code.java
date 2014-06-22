package com.eetac.pycto;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;

import org.junit.Test;

import com.eetac.pycto.models.Ballot_Box_photo;
import com.eetac.pycto.models.CA_CR_keys;
import com.google.gson.Gson;

public class test_ballot_box_vote_inpunity_code {

	@Test
	public void test() {

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
		
		
		//SOLO VOTOS
		String vote = "[{'id':'4'},{'id':'5'},{'id':'6'},{'id':'7'},{'id':'8'}]..//.."+vote_post_hash;
		
		System.out.println("COMPARACIÓN VOTO EN CLARO Y VOTO HASHEADO");
		System.out.println("- voto entero: "+ vote);
		
		String[] vote_parts = vote.split("..//..");
        System.out.println("- - votos en claro: "+vote_parts[0]); 
        
        //parte de información de la foto y extracción de datos con json
        //COMIENZO
        String replace=vote_parts[0].replace("[", "");
        String replace_2=replace.replace("]", "");
        String JSON_String = replace_2;
        System.out.println("- - - JSON para parsear: "+JSON_String);
        
        //obtenemos el objeto
        String[] JSON_Strings = JSON_String.split(",");
		for(int i=0; i<5; i++){ 
			Gson g = new Gson();
			Ballot_Box_photo foto = new Gson().fromJson(JSON_Strings[i],
				Ballot_Box_photo.class);
			System.out.println("- - - - objeto foto "+ i+": "+ foto.getId());
		}
		//FIN
		
        System.out.println("- - encriptado de votos hasheados: "+vote_parts[1]);
        
       
		//hacemos hash del voto en claro
		String clear_vote_post_hash = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(vote_parts[0].getBytes());
			byte[] passbytes = md.digest();
			
			StringBuilder sb = new StringBuilder();
            for(int i=0; i< passbytes.length ;i++)
            {
                sb.append(Integer.toString((passbytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            clear_vote_post_hash = sb.toString();
            
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		System.out.println("- voto en claro hasheado: " + clear_vote_post_hash);
		System.out.println("- voto hasheado des de cliente: "+vote_post_hash);
		if(clear_vote_post_hash.equals(vote_post_hash)){
			System.out.println("+ + + + los votos son iguales, inpunidad confirmada + + + +");
		}else{
			System.out.println("+ + + +los votos no son iguales, no-inpunidad confirmada + + + +");
		}
		
		System.out.println("captura a falta de desencriptar lo que me llegue por el JSON, en la función real esta puesto.");

	}

}
