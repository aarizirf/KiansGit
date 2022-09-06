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
		File objDir = new File ("test/objects");
	}
	
	public void add(String fileName) throws FileNotFoundException, IOException {
		Blob blobby = new Blob("./test/" + fileName);
		pairs.put(fileName, blobby.getSha1());
		PrintWriter printWrite = new PrintWriter (indexFile);
		printWrite.println(pairs);
	}
}
