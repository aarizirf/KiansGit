package blobindex;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BlobTest {
	private static File f;
	private static String ogContent;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		f = new File ("something.txt");
		PrintWriter writer = new PrintWriter(f);
		ogContent = "some content\n" + "what's up";
		writer.print(ogContent);
		writer.close();
	}
	
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		f.delete();
		File sha1File = new File ("test/objects/c09f382894b42abb22deaef2b26ca5b008334cf7");
		sha1File.delete();
		File objects = new File ("test/objects");
		objects.delete();
	}
	
	@Test
	void testBlob() throws IOException {
		Blob b = new Blob ("something.txt");
		File check = new File ("test/objects/c09f382894b42abb22deaef2b26ca5b008334cf7");
		Path filePath = Path.of("test/objects/c09f382894b42abb22deaef2b26ca5b008334cf7");
		String content = Files.readString(filePath);
		System.out.println(content);
		System.out.println(ogContent);
		System.out.println(content.equals(ogContent));
		assertTrue("Blob constructor doesn't work", check.exists()&&content.equals(ogContent));
	}

	@Test
	void testGetSha() throws IOException {
		Blob b = new Blob ("something.txt");
		assertTrue("sha code is not being generated correctly", b.getSha1().equals("c09f382894b42abb22deaef2b26ca5b008334cf7"));
	}

}
