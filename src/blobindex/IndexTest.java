package blobindex;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class IndexTest {
	private static File f;
	private static Index indy;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		indy = new Index();
		indy.init();
		f = new File ("test/something.txt");
		PrintWriter writer = new PrintWriter(f);
		writer.println("some content\nwhat's up");
		writer.close();
		File foo = new File ("test/foo.txt");
		PrintWriter writer2 = new PrintWriter(foo);
		writer2.println("this is foo");
		writer2.close();
		File foobar = new File ("test/foobar.txt");
		PrintWriter writer3 = new PrintWriter(foobar);
		writer3.println("this is foobar");
		writer3.close();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		f.delete();
		new File("foo.txt").delete();
		new File("foobar.txt").delete();
		File sha1File = new File ("test/objects/b66399e65f956699e7ece173e73ab2b4021ff1ab");
		sha1File.delete();
		File sha1File2 = new File ("test/objects/7c8b7013903e3f6800bc34db8acfef88143e1eb5");
		sha1File2.delete();
		File sha1File3 = new File ("test/objects/3f0b692b963a9a3cc402e74f8b4374538b1125f7");
		sha1File3.delete();
		File objects = new File ("test/objects");
		objects.delete();
		File index = new File ("test/index");
		index.delete();
	}

	@Test
	void testInit() throws IOException {
		Index indy = new Index();
		File check = new File ("test/objects");
		File check2 = new File ("test/index");
		assertTrue("Index initializer doesn't work", check.exists()&&check2.exists());
	}
	
	@Test
	void testAdd() throws IOException {
		File check = new File ("test/objects/b66399e65f956699e7ece173e73ab2b4021ff1ab");
		File check2 = new File ("test/objects/7c8b7013903e3f6800bc34db8acfef88143e1eb5");
		File check3 = new File ("test/objects/3f0b692b963a9a3cc402e74f8b4374538b1125f7");
		indy.add("something.txt");
		indy.add("foo.txt");
		indy.add("foobar.txt");
		boolean exists = check.exists()&&check2.exists()&&check3.exists();
		Path filePath = Path.of("test/index");
		String content = Files.readString(filePath);
		assertTrue("Index add method doesn't work", exists&&content.contains((CharSequence)"foo.txt : b66399e65f956699e7ece173e73ab2b4021ff1ab\n" + "something.txt : 7c8b7013903e3f6800bc34db8acfef88143e1eb5\n" + "foobar.txt : 3f0b692b963a9a3cc402e74f8b4374538b1125f7"));
	}
	
	@Test
	void testRemove() throws IOException {
		indy.remove("something.txt");
		File check = new File ("test/objects/7c8b7013903e3f6800bc34db8acfef88143e1eb5");
		Path filePath = Path.of("test/index");
		String content = Files.readString(filePath);
		assertTrue("Index remove method doesn't work", !check.exists()&&content.contains((CharSequence)"foo.txt : b66399e65f956699e7ece173e73ab2b4021ff1ab\nfoobar.txt : 3f0b692b963a9a3cc402e74f8b4374538b1125f7"));
	}
}
