package blobindex;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TreeTester {
	static ArrayList <String> arr; 
	static String arrContent;
	static Tree test;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		arr = new ArrayList<String>();
		arr.add("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f");
		arr.add("blob : 01d82591292494afd1602d175e165f94992f6f5f");
		arr.add("blob : f1d82236ab908c86ed095023b1d2e6ddf78a6d83");
		arr.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
		arr.add("tree : e7d79898d3342fd15daf6ec36f4cb095b52fd976");
		arrContent="";
		for (String obj: arr) {
			arrContent=arrContent+obj+"\n";
		}
		arrContent = arrContent.substring(0, arrContent.length()-1);
		test = new Tree (arr);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		File tree = new File ("test/objects/tree");
		tree.delete();
	}
	
	@Test
	void testSha1() {
		File check = new File ("test/objects/dd4840f48a74c1f97437b515101c66834b59b1be");
		assertTrue("Sha-1 code is incorrect", check.exists());
	}

	@Test
	void testTree() throws IOException {
		Path filePath = Path.of("test/objects/dd4840f48a74c1f97437b515101c66834b59b1be");
		String fileContent = Files.readString(filePath);
		assertTrue("Tree object does not write correctly to file", arrContent.equals(fileContent));
	}
}
