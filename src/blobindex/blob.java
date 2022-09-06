package blobindex;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.nio.file.*;
import java.io.File;
import java.io.PrintWriter;

public class Blob {
	private String fileContent;
	private String sha1; 
	
	public Blob (String fileName) throws FileNotFoundException, IOException {
		Path filePath = Paths.get(fileName);
		fileContent = Files.readString(filePath);
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
	        digest.reset();
	        digest.update(fileContent.getBytes("utf8"));
	        sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
		} catch (Exception e){
			e.printStackTrace();
		}
		createFile();
	}
	
	public void createFile() throws IOException {
		PrintWriter pw = new PrintWriter ("test/objects/" + sha1 + ".txt");
		pw.println(fileContent);
		pw.close();
	}
	
	public String getSha1 () {
		return sha1;
	}
	
	public String getFileContent() {
		return fileContent;
	}
	
	public static void main (String [] args) throws FileNotFoundException, IOException {
		Blob blobby = new Blob ("./test/something.txt");
		System.out.println(blobby.getSha1());
	}
}
