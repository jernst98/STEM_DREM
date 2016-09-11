package edu.cmu.cs.sb.drem.tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/*
 * A simple writer class to record test files for unit tests.
 * Files should be read using the TestFileParser class in the
 * same order they were originally written.  When writing a 
 * test file make sure the file does not exist, using the
 * fileExists() method, before writing to avoid extremely long
 * files.
 */

public class TestFileWriter {
	private Writer w;
	private File file;
	private boolean exists;
	public TestFileWriter(String name) throws IOException
	{
		file = new File(name);
		w = new BufferedWriter(new FileWriter(file));
		exists = file.exists();
	}
	public boolean fileExists()
	{
		return exists;
	}
	/*
	 * Please create a new TestFileWriter after successfully 
	 * deleting the file.
	 */
	public boolean deleteFile() throws IOException
	{
		boolean deleted = file.delete();
		if(deleted)
			close();
		return deleted;
	}
	public void close() throws IOException
	{
		w.close();
	}
	/*
	 * The following three methods write single primitives
	 * to a file.  Do not use these to write arrays as each
	 * call ends the line.
	 */
	public void writeInt(int i) throws IOException
	{
		w.write(i + "\n");
	}
	public void writeDouble(double i) throws IOException
	{
		w.write(i + "\n");
	}
	public void writeFloat(float i) throws IOException
	{
		w.write(i + "\n");
	}
	/*
	 * 	1d arrays are formatted with the length on the first line and
	 * 	that number of comma delimited entries on the second with a
	 * 	trailing comma.
	 * 
	 * 	Example:
	 * 	5
	 * 	1,2,3,4,5,
	 * 
	 * 	This is an array of length 5 and entries in order 1,2,3,4,5.
	 */
	public void writeIntArray(int[] arr) throws IOException
	{
		writeInt(arr.length);
		for(int i = 0; i < arr.length; i++)
			w.write(arr[i] + ",");
		w.write("\n");
	}
	public void writeDoubleArray(double[] arr) throws IOException
	{
		writeInt(arr.length);
		for(int i = 0; i < arr.length; i++)
			w.write(arr[i] + ",");
		w.write("\n");
	}public void writeFloatArray(float[] arr) throws IOException
	{
		writeInt(arr.length);
		for(int i = 0; i < arr.length; i++)
			w.write(arr[i] + ",");
		w.write("\n");
	}
	/*
	 * 2d arrays are formatted with the number of 1d arrays on the
	 * first line followed by that number of 1d arrays.
	 * Example:
	 * 	2
	 * 	2
	 * 	1,4,
	 * 	2
	 * 	5,6,
	 * 
	 * This is a 2x2 array with entries 1,4 in the first row and
	 * 5,6 in the second row.
	 */
	public void write2DIntArray(int[][] arr) throws IOException
	{
		writeInt(arr.length);
		for(int i = 0; i < arr.length; i++)
			writeIntArray(arr[i]);
	}
	public void write2DDoubleArray(double[][] arr) throws IOException
	{
		writeInt(arr.length);
		for(int i = 0; i < arr.length; i++)
			writeDoubleArray(arr[i]);
	}
	public void write2DFloatArray(float[][] arr) throws IOException
	{
		writeInt(arr.length);
		for(int i = 0; i < arr.length; i++)
			writeFloatArray(arr[i]);
	}
}
