package blobindex;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;

public class Commit {
	Tree tree;
	private String summary;
	private String author;
	private String parent;
	private String child; 
	private String date;
	String sha1;
	
	public Commit (String summary, String author, String parent) throws IOException {
		
		this.date = getDate();
		this.parent = parent;
		this.child = null;
		this.summary = summary;
		this.author = author;
		String content = summary + date + author + parent;
		this.sha1 = encrypt(content);
		this.tree = generateTreeFromIndex();
		writeToFile();
	}
	
	private Tree generateTreeFromIndex() throws IOException {
		ArrayList<String> treeLines = new ArrayList<>();
		if(parent != null) {
			treeLines.add("tree : " + getTreeFromCommit(parent));
		}
		File index = new File("test/index");
		
		BufferedReader br = new BufferedReader(new FileReader(index));
	    String line;
	    while ((line = br.readLine()) != null) {
	    	switch(line.charAt(0)) {
	    	case '*': //deleted
	    		String lastTree = getTreeFromCommit(parent);
	    		String treeWithFile = getTreeWithFile(lastTree, line.substring(1));
	    		
	    		break;
	    	case '#': //edited
	    		break;
	    	default: //new blob
	    		String hash = line.substring(line.length() - 40);
	    		String fn = line.substring(0, line.length() - 43);
	    		treeLines.add("blob : " + hash + " " + fn);
	    	}
	    }

	    return new Tree(treeLines);
	}
	
	public String getTreeFromCommit(String hash) throws IOException {
		File file = new File("test/objects/" + hash);
		BufferedReader br = new BufferedReader(new FileReader(file));
	    String line = br.readLine();
	    return line;
	}
	
	public String getTreeWithFile(String hash, String filename) throws IOException {
		File file = new File("test/objects/" + hash);
		BufferedReader br = new BufferedReader(new FileReader(file));
	    String line;
	    while ((line = br.readLine()) != null) {
	       // process the line.
	    	if(line.charAt(0) == 't') {
	    		return getTreeWithFile(line.substring(line.length()-40), filename);
	    	} else {
	    		String blobFn = line.substring(line.length() - filename.length());
	    		System.out.println("BLOBFN " + blobFn);
	    		if(blobFn.equals(filename)) return hash;
	    	}
	    }
		return null;
	
	}
	
	
	public void setParent(String sha) throws FileNotFoundException {
		parent = sha;
		writeToFile();
	}
	
	public void writeToFile() throws FileNotFoundException {
		PrintWriter printer = new PrintWriter("test/objects/" + sha1);
		
		printer.println(tree.mySha);
		
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
}
