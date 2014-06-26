package com.eetac.pycto;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.junit.Test;

public class test_ballot_box_count_votes {

	@Test
	public void test() {

		File archivo = new File("..//votes.txt");
		FileReader fr = null;
		BufferedReader br = null;

		try {
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			String linea;
			while ((linea = br.readLine()) != null) {
				linea = br.readLine();
				System.out.println(linea);

				for (int i = 0; linea != null; i++) {
					String[] vote_parts = linea.split(":");

					for (int k = 0; k<4; k++) {
						System.out.println(k+" - - - "+vote_parts[k]);
					}
					linea = br.readLine();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

}
