package blobindex;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;

public class Tree {
	String content;
	String mySha;
	
	public Tree (ArrayList<String> keyVal) throws FileNotFoundException {
		content="";
		for (String obj: keyVal) {
			content=content+obj+"\n";
		}
		content = content.substring(0, content.length()-1);
		mySha = encrypt(content);
		PrintWriter writer = new PrintWriter ("test/objects/" + mySha);
		writer.print(content);
		writer.close();
	}

	private String encrypt(String fileContent) {
		String sha1 = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
	        digest.reset();
	        digest.update(fileContent.getBytes("utf8"));
	        sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
		} catch (Exception e){
			e.printStackTrace();
		}
		return sha1;
	}
}
