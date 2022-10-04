package blobindex;

import java.util.*;
import java.io.*;

public class Index {
	private HashMap<String, String> indexPairs; 
	private File indexFile;
	
	public Index() {
		indexPairs = new HashMap<String, String>();
	}
	
	public void init() {
		indexFile = new File ("test/index");
		new File ("test/objects").mkdirs();
	}
	
	public void add(String fileName) throws FileNotFoundException, IOException {
		Blob blobby = new Blob("./test/" + fileName);
		Writer writer = new BufferedWriter(new OutputStreamWriter(
        new FileOutputStream(indexFile, true), "UTF-8"));
		writer.write(fileName + " : " + blobby.getSha1());
		writer.write(System.getProperty( "line.separator" ));
		writer.close();
	}
	
	public void delete(String fn) {
		try {
			Writer writer = new BufferedWriter(new OutputStreamWriter(
			        new FileOutputStream(indexFile, true), "UTF-8"));
			writer.write("*" + fn);
			writer.write(System.getProperty( "line.separator" ));

			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update(String fn) {
		try {
			Writer writer = new BufferedWriter(new OutputStreamWriter(
			        new FileOutputStream(indexFile, true), "UTF-8"));
			writer.write("#" + fn);
			writer.write(System.getProperty( "line.separator" ));
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void remove(String fileName) throws FileNotFoundException {
		File myObj = new File("test/objects/" + indexPairs.get(fileName)); 
	    if (myObj.delete()) { 
	    	System.out.println("Deleted the file: " + myObj.getName());
	    } 
	    else {
	    	System.out.println("Failed to delete the file.");
	    } 
		indexPairs.remove(fileName);
		write();
	}
	
	private void write() throws FileNotFoundException {
		PrintWriter printWrite = new PrintWriter (indexFile);
		for (String key: indexPairs.keySet()) {
			printWrite.println (key + " : " + indexPairs.get(key));
		}
		printWrite.close();
	}
	
	public void clearFile() throws IOException {
		FileWriter fwOb = new FileWriter(indexFile); 
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();
        fwOb.close();
	}
	
	/**public static void main (String [] args) throws FileNotFoundException, IOException {
		Index git = new Index();
		git.init();
		git.add("foo.txt");
		git.add("bar.txt");
		git.add("foobar.txt");
		git.add("something.txt");
		git.remove("foo.txt");
	}**/
}
