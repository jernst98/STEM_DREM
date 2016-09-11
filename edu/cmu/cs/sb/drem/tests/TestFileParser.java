package edu.cmu.cs.sb.drem.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

/*
 * A simple parser class to parse input files for unit tests.
 * Files should have been written using the TestFileWriter class.
 */
public class TestFileParser {
	private BufferedReader br;
	public TestFileParser(BufferedReader reader)
	{
		br = reader;
	}
	public void close() throws IOException
	{
		br.close();
	}
	public int parseInt() throws NumberFormatException, IOException
	{
		return Integer.parseInt(br.readLine());
	}
	public double parseDouble() throws NumberFormatException, IOException
	{
		return Double.parseDouble(br.readLine());
	}
	public float parseFloat() throws NumberFormatException, IOException
	{
		return Float.parseFloat(br.readLine());
	}
	/*
	 * 1d arrays are formatted with the length on the first line and
	 * that number of comma delimited entries on the second with a
	 * trailing comma.
	 * Example:
	 * 	5
	 * 	1,2,3,4,5,
	 * 
	 * This is an array of length 5 and entries in order 1,2,3,4,5.
	 */
	public double[] parseArrayDouble() throws NumberFormatException, IOException
	{
		double[] arr;
		arr = new double[Integer.parseInt(br.readLine())];
		String row = br.readLine();
		StringTokenizer st = new StringTokenizer(row,",");
		for(int i = 0; st.hasMoreTokens(); i++)
			arr[i] = Double.parseDouble(st.nextToken());
		return arr;
	}
	public int[] parseArrayInt() throws NumberFormatException, IOException
	{
		int[] arr;
		arr = new int[Integer.parseInt(br.readLine())];
		String row = br.readLine();
		StringTokenizer st = new StringTokenizer(row,",");
		for(int i = 0; st.hasMoreTokens(); i++)
			arr[i] = Integer.parseInt(st.nextToken());
		return arr;
	}
	public float[] parseArrayFloat() throws NumberFormatException, IOException
	{
		float[] arr;
		arr = new float[Integer.parseInt(br.readLine())];
		String row = br.readLine();
		StringTokenizer st = new StringTokenizer(row,",");
		for(int i = 0; st.hasMoreTokens(); i++)
			arr[i] = Float.parseFloat(st.nextToken());
		return arr;
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
	public int[][] parse2dArrayInt() throws NumberFormatException, IOException
	{
		int[][] arr;
		arr = new int[Integer.parseInt(br.readLine())][];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = this.parseArrayInt();
		}
		return arr;
	}
	public double[][] parse2dArrayDouble() throws NumberFormatException, IOException
	{
		double[][] arr;
		arr = new double[Integer.parseInt(br.readLine())][];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = this.parseArrayDouble();
		}
		return arr;
	}
	public float[][] parse2dArrayFloat() throws NumberFormatException, IOException
	{
		float[][] arr;
		arr = new float[Integer.parseInt(br.readLine())][];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = this.parseArrayFloat();
		}
		return arr;
	}
}
