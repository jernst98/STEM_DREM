package edu.cmu.cs.sb.drem.tests;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import edu.cmu.cs.sb.drem.DREM_IO_Batch;

public class TotalDremTest {
	private static final double EPSILON = 0.000001;
	private static final String DELIMS = " \t\n";
	private String filepath = "TestFiles/TotalTestFiles/";
	
	//Input and output file names for the testcases
	private String yeastTestOutputName = filepath+"TotalDremRunTestOutput.txt";
	private String yeastRecordedOutputName = filepath+"NewHeatModel.txt";
	private String yeastTestDefaults = filepath+"TotalDremTestDefaults.txt";
	
	private String ecoliTestOutputName = filepath+"ecoliDremRunTestOutput.txt";
	private String ecoliRecordedOutputName = filepath+"ecoliDremRunModel.txt";
	private String ecoliTestDefaults = filepath+"defaultsEcoliCurated.txt";
	
	private String h1n1TestOutputName = filepath+"H1N1DremRunTestOutput.txt";
	private String h1n1RecordedOutputName = filepath+"H1N1Model.txt";
	private String h1n1TestDefaults = filepath+"H1N1DremDefaults.txt";
	
	private String hyperosmoticTestOutputName = filepath+"HyperosmoticDremRunTestOutput.txt";
	private String hyperosmoticRecordedOutputName = filepath+"HyperosmoticDremRunModel.txt";
	private String hyperosmoticTestDefaults = filepath+"HyperosmoticDremDefaults.txt";
	
	private String temporalTestOutputName = filepath + "TemporalDremRunTestOutput.txt";
	private String temporalRecordedOutputName = filepath + "TemporalDremRunModel.txt";
	private String temporalTestDefaults = filepath + "TemporalDremDefaults.txt";
	
	@Test
	public void totalDremRunYeastTest()
	{
		System.out.println("Running yeast test.");
		runTotalTest(yeastTestDefaults, yeastTestOutputName, yeastRecordedOutputName);
		System.out.println("Yeast test finished.");
	}
	@Test
	public void totalDremRunEcoliTest()
	{
		System.out.println("Running ecoli test.");
		runTotalTest(ecoliTestDefaults, ecoliTestOutputName, ecoliRecordedOutputName);
		System.out.println("Ecoli test finished.");
	}
	@Test
	public void totalDremRunH1N1Test() {
		System.out.println("Running h1n1 test.");
		runTotalTest(h1n1TestDefaults, h1n1TestOutputName, h1n1RecordedOutputName);
		System.out.println("H1N1 test finished.");
	}

	@Test
	public void totalDremRunHyperosmoticTest() {
		System.out.println("Running hyperosmotic test.");
		runTotalTest(hyperosmoticTestDefaults, hyperosmoticTestOutputName,
				hyperosmoticRecordedOutputName);
		System.out.println("Hyperosmotic test finished.");
	}
	
	@Test
	public void totalDremRunTemporalTest() {
		System.out.println("Running temporal binding data test.");
		runTotalTest(temporalTestDefaults, temporalTestOutputName, temporalRecordedOutputName);
		System.out.println("Temporal binding data test finished.");
	}

	public void runTotalTest(String defaults, String output, String model)
	{
		boolean passed = true;
		File testOutput = new File(output);
		if (testOutput.exists())
			testOutput.delete();
		new DREM_IO_Batch(defaults, output);
		assertTrue("Checking that the output file exists", testOutput.exists());
		try {
			BufferedReader brTest = new BufferedReader(new FileReader(
					testOutput));
			BufferedReader brRecorded = new BufferedReader(new FileReader(
					new File(model)));

			String tempr;
			tempr = brRecorded.readLine();
			StringTokenizer str = new StringTokenizer(tempr, DELIMS);
			str.nextToken();// Num.
			str.nextToken();// Coefficients

			String tempt;
			tempt = brTest.readLine();
			StringTokenizer stt = new StringTokenizer(tempt, DELIMS);
			stt.nextToken();// Num.
			stt.nextToken();// Coefficients

			int nr = Integer.parseInt(str.nextToken());
			int nt = Integer.parseInt(stt.nextToken());
			assertTrue("Comparing num. coeffs.", nr == nt);
			int n = nr;

			double meanr, meant;
			double sdr, sdt;
			int childrenr, childrent;
			double coeffr, coefft;
			for (int i = 0; (tempt = brTest.readLine()) != null; i++) {
				tempr = brRecorded.readLine();
				str = new StringTokenizer(tempr, DELIMS);
				meanr = Double.parseDouble(str.nextToken());
				sdr = Double.parseDouble(str.nextToken());
				childrenr = Integer.parseInt(str.nextToken());

				stt = new StringTokenizer(tempt, DELIMS);
				meant = Double.parseDouble(stt.nextToken());
				sdt = Double.parseDouble(stt.nextToken());
				childrent = Integer.parseInt(stt.nextToken());
				if (!withinEpsilon("Comparing means at branch " + i, meant,
						meanr))
					passed = false;
				
				if (!withinEpsilon("Comparing standard deviations at branch "
						+ i, sdt, sdr))
				{
					if(i == 0)
					{
						System.out.println("The first standard deviation does not " + 
								"match the standard deviation in the model file.  This " + 
								"may be due to the model file being created in the " + 
								"non-batch mode.  The non-batch mode sets the first " + 
								"standard deviation to a fraction of the y-axis length, " + 
								"because the display size of the node is based on the " + 
								"standard deviation.");
					}
					passed = false;
				}
				if(!same("Comparing children at branch " + i, childrent, childrenr))
					passed = false;
				
				/*
				 * If we are at a branch and not a path or leaf node, then
				 * coeffs must be compared.
				 */
				if (childrenr >= 2) {
					for (int j = 0; j < (n + 1) * (childrenr-1); j++)// n+1 for intercept
					{
						tempr = brRecorded.readLine();
						str = new StringTokenizer(tempr, DELIMS);
						str.nextToken();// INT or Gene name
						coeffr = Double.parseDouble(str.nextToken());

						tempt = brTest.readLine();
						stt = new StringTokenizer(tempt, DELIMS);
						stt.nextToken();// INT or Gene name
						coefft = Double.parseDouble(stt.nextToken());
						if (!withinEpsilon("Comparing coeff " + j
								+ " at branch " + i, coefft, coeffr))
							passed = false;
					}
				}
			}
		} catch (FileNotFoundException e) {
			fail("No recorded output for comparison");
		} catch (IOException e) {
			fail("Too many entires in test case file");
		} finally {
			testOutput.delete();
		}
		assertTrue("Checking all requirements were passed", passed);
	}
	public boolean withinEpsilon(String info, Double answer, Double recorded)
	{
		double diff = Math.abs(answer-recorded);
		boolean ret = (diff <= EPSILON);
		if(ret == false)
		{
			System.out.println(info);
			System.out.println("Answer: " + answer);
			System.out.println("Recorded: " + recorded);
			System.out.println("Diff: " + diff);
		}
		return ret;
	}
	public boolean same(String info, int answer, int recorded)
	{
		boolean ret = (answer==recorded);
		if(ret == false)
		{
			System.out.println(info);
			System.out.println("Answer: " + answer);
			System.out.println("Recorded: " + recorded);
			System.out.println("Diff: " + (answer-recorded));
		}
		return ret;
	}
}
