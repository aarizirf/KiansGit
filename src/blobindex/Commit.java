package blobindex;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Calendar;

public class Commit {
	private String pTree;
	private String summary;
	private String author;
	private String parent;
	private String child; 
	private String date;
	private String sha1;
	
	public Commit (String pTree, String summary, String author, String parent) throws FileNotFoundException {
		this.pTree = pTree;
		this.date = getDate();
		this.parent = parent;
		this.child = null;
		this.summary = summary;
		this.author = author;
		String content = summary + date + author + parent;
		this.sha1 = encrypt(content);
		writeToFile();
	}

	private void writeToFile() throws FileNotFoundException {
		PrintWriter printer = new PrintWriter("test/objects/" + sha1);
		if(pTree==null) {
			printer.println();
		}
		else {
			printer.println(pTree);
		}
		if(parent==null) {
			printer.println();
		}
		else {
			printer.println(parent);
		}
		if(child==null) {
			printer.println();
		}
		else {
			printer.println(child);
		}
		printer.println(author);
		printer.println(date);
		printer.println(summary);
		printer.close();
	}

	private String getDate() {
		Calendar c = Calendar.getInstance();
		int mYear = c.get(Calendar.YEAR);
		int mMonth = c.get(Calendar.MONTH);
		int mDay = c.get(Calendar.DAY_OF_MONTH);
		date = mYear +"/"+ mMonth +"/"+ mDay;
		return date;
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
	
	public static void main (String [] args) throws FileNotFoundException {
		Commit c = new Commit ("test/objects/dd4840f48a74c1f97437b515101c66834b59b1be", "The commit works!", "Kian Chen", null);
	}
}
