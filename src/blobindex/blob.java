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
	
	public Blob (Path filePath) throws FileNotFoundException, IOException {
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
		String path = "/Users/kianchen/eclipse-workspace/blobindex/src/blobindex/test/objects/" + sha1 + ".txt";
		Path textFilePath = Paths.get(path);
		Files.createFile(textFilePath);
		PrintWriter pw = new PrintWriter (sha1 + ".txt");
		System.out.println(fileContent);
		pw.println(fileContent);
		pw.close();
	}
	
	public String getSha1 () {
		return sha1;
	}
	
	public static void main (String [] args) throws FileNotFoundException, IOException {
		Path filePath = Paths.get("/Users/kianchen/eclipse-workspace/blobindex/src/blobindex/test/something.txt");
		System.out.println (filePath);
		Blob blobby = new Blob (filePath);
		System.out.println(blobby.getSha1());
	}
}
