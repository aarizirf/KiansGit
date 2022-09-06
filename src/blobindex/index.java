package blobindex;

import java.util.*;
import java.io.*;

public class Index {
	private HashMap<String, String> pairs; 
	private File indexFile;
	
	public Index() {
		pairs = new HashMap<String, String>();
	}
	
	public void init() {
		indexFile = new File ("test/index.txt");
		new File ("test/objects").mkdirs();
	}
	
	public void add(String fileName) throws FileNotFoundException, IOException {
		Blob blobby = new Blob("./test/" + fileName);
		pairs.put(fileName, blobby.getSha1());
		write();
	}
	
	public void remove(String fileName) throws FileNotFoundException {
		File myObj = new File("test/objects/" + pairs.get(fileName) + ".txt"); 
	    if (myObj.delete()) { 
	    	System.out.println("Deleted the file: " + myObj.getName());
	    } 
	    else {
	    	System.out.println("Failed to delete the file.");
	    } 
		pairs.remove(fileName);
		indexFile.delete();
		indexFile = new File ("test/index.txt");
		write();
	}
	
	private void write() throws FileNotFoundException {
		PrintWriter printWrite = new PrintWriter (indexFile);
		for (String key: pairs.keySet()) {
			printWrite.println (key + " : " + pairs.get(key));
		}
		printWrite.close();
	}
	
	public static void main (String [] args) throws FileNotFoundException, IOException {
		Index git = new Index();
		git.init();
		git.add("foo.txt");
		git.add("bar.txt");
		git.add("foobar.txt");
		git.remove("foo.txt");
	}
}
