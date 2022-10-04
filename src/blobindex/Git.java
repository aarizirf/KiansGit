package blobindex;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class Git {
	public static void main(String[] args) throws IOException {
		System.out.println("Starting code");
		Git g = new Git();
		
		g.addFile("foo.txt");
//		g.updateFile("foobar.txt");
//		g.deleteFile("bar.txt");
		
		g.commitChanges("First commit!", "aarizirf");
		
		g.addFile("foobar.txt");
		
		g.commitChanges("Second commit", "aarizirf");
	}

//	ACTUAL CLASS BEGINS
	Commit current;
	Index i;
	
 	public Git() {
 		current = null;
 		i = new Index();
 		i.init();
 	}
 	
 	public void commitChanges(String summary, String author) throws IOException {
 		Commit c = new Commit(summary, author, null);
 		i.clearFile();
 		if(current == null) {
 			current = c;
 		} else {
 			c.setParent(current.sha1);
 		}
 		writeToHead(c.sha1);
 	}
 	
 	public void writeToHead(String hash) throws FileNotFoundException, UnsupportedEncodingException {
 		PrintWriter writer = new PrintWriter("test/head", "UTF-8");
 		writer.println(hash);
 		writer.close();
 	}
 	
 	public void addFile(String fileName) {
 		try {
			i.add(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	}
 	
 	public void deleteFile(String filename) {
 		i.delete(filename);
 	}
 	
 	public void updateFile(String filename) {
 		i.update(filename);
 	}
 	
 	public void initRepo() {
 		
 	}

	
}
