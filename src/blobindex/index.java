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
}
