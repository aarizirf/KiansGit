package blobindex;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.nio.file.*;

public class blob {
	private String fileContent;
	public String sha1; 
	
	public blob (Path filePath) throws FileNotFoundException, IOException {
		fileContent = Files.readString(filePath);
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
	        digest.reset();
	        digest.update(fileContent.getBytes("utf8"));
	        sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
