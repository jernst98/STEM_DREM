package edu.cmu.cs.sb.drem.tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import org.junit.Test;

import edu.cmu.cs.sb.drem.DREM_FastLogistic2;
import edu.cmu.cs.sb.drem.RegulatorBindingData;

/*
 * A series of test cases for logistic regression.
 */

/*
 * To capture more test cases use the TestFileWriter class to
 * write the input to the constructor to a file.  When train is
 * called write the toString to a file.
 * 
 * You can optionally check that the case meets some requirement
 * (e.g. at least 10 of the coeffs end up not zero) at both the 
 * initial constructor call and the call of train.  Be sure to make
 * sure the file exists before writing anything after the train 
 * call, and if you decide the test case is poor make sure to delete
 * the initial file recorded at the constructor call.
 */
public class DREM_FastLogistic2Tests {
	private static final double EPSILON = 0.000001;
	private static final String DELIMS = "(,)\n \t";
	// Test file paths
	private String filepath = "TestFiles/LogisticRegressionTestFiles/";
	private String binaryTestInput = filepath
			+ "binaryLogisticRegressionTestFile.txt";
	private String binaryTestCoeffs = filepath
			+ "logisticRegressionTestBinaryCoeffs.txt";
	private String multiTestInput = filepath
			+ "multiLogisticRegressionTestFile.txt";
	private String multiTestCoeffs = filepath
			+ "logisticRegressionTestMultiCoeffs.txt";
	private String failTestInput = filepath
			+ "failLogisticRegressionTestFile.txt";
	private String[] ecoliTestInput = {
			filepath + "ecoliLogisticRegressionTestFile0.txt",
			filepath + "ecoliLogisticRegressionTestFile1.txt" };
	private String[] ecoliTestCoeffs = {
			filepath + "ecoliLogisticRegressionTestCoeffs0.txt",
			filepath + "ecoliLogisticRegressionTestCoeffs1.txt" };

	@Test
	public void simpleBinaryLogisticRegressionTest() {
		int[][] traindataindex = { { 0, 1, 2 }, { 0, 1, 2 } };
		double[][] traindata = { { 5, 6, 3 }, { 4, 1, 2 } };
		int[][] traindataTFindex = { { 0, 1 }, { 0, 1 }, { 0, 1 } };
		double[][] traindataTF = { { 9, 6 }, { 5, 2 }, { 7, 5 } };
		int[] y = { 1, 0 };
		int[] regTypes = {RegulatorBindingData.TF,RegulatorBindingData.
				TF,RegulatorBindingData.TF};

		double[] dtrainweight = { 0.9, 0.1, 0.7 };

		int numclasses = 2;

		int numbits = 3;

		DREM_FastLogistic2 reg = new DREM_FastLogistic2(traindataindex,
				traindata, traindataTFindex, traindataTF, y, dtrainweight,
				numclasses, numbits, regTypes);

		reg.train();
		String output = reg.toString();
		String answer = "Coefficients: (0,-2.0798335087700544)"
				+ "	(1,0.0)	(2,0.0)	(3,0.0)\t\n";

		assertTrue("Comparing output to recorded answer: " + output + "\n"
				+ answer + "\n", output.compareTo(answer) == 0);
	}

	@Test
	public void simpleMultiLogisticRegressionTest() {
		int[][] traindataindex = { { 0, 1, 2 }, { 0, 1, 2 } };
		double[][] traindata = { { 5, 6, 3 }, { 4, 1, 2 } };
		int[][] traindataTFindex = { { 0, 1 }, { 0, 1 }, { 0, 1 } };
		double[][] traindataTF = { { 9, 6 }, { 5, 2 }, { 7, 5 } };
		int[] y = { 1, 0, 1 };
		int[] regTypes = {RegulatorBindingData.TF,RegulatorBindingData.
				TF,RegulatorBindingData.TF};

		double[] dtrainweight = { 0.9, 0.1, 0.7 };

		int numclasses = 3;

		int numbits = 3;

		DREM_FastLogistic2 reg = new DREM_FastLogistic2(traindataindex,
				traindata, traindataTFindex, traindataTF, y, dtrainweight,
				numclasses, numbits, regTypes);

		reg.train();
		String output = reg.toString();

		String answer = "Coefficients: (0,-0.46994127990053736)"
				+ "	(1,0.0)	(2,0.0)	(3,0.0)	(4,2.030744802544175)	"
				+ "(5,0.0024863647064918004)	(6,0.0)	(7,0.0)	\n";
		assertTrue("Comparing output to recorded answer: " + output + "\n"
				+ answer + "\n", output.compareTo(answer) == 0);
	}

	@Test
	public void largeBinaryLogisticRegressionTest() throws IOException {
		runLogisticRegressionTest(binaryTestInput, binaryTestCoeffs, true);
	}

	@Test
	public void largeMultiLogisticRegressionTest() throws IOException {
		runLogisticRegressionTest(multiTestInput, multiTestCoeffs, true);
	}

	@Test
	public void ecoliEdgeCaseLogisticRegressionTest() throws IOException {
		for (int i = 0; i < 2; i++) {
			runLogisticRegressionTest(ecoliTestInput[i], ecoliTestCoeffs[i], true);
		}
	}

	@Test
	public void logisticRegressionFailureTest() throws IOException {
		runLogisticRegressionTest(failTestInput, binaryTestCoeffs, false);
	}

	public void runLogisticRegressionTest(String inputFile, String modelFile, 
			boolean shouldPass) throws IOException {
		// Parses the input arrays out of a saved text file
		BufferedReader br = new BufferedReader(
				new FileReader(inputFile));

		TestFileParser tfp = new TestFileParser(br);
		int[][] theInstancesIndex = tfp.parse2dArrayInt();
		double[][] theInstances = tfp.parse2dArrayDouble();
		int[][] theInstancesTFIndex = tfp.parse2dArrayInt();
		double[][] theInstancesTF = tfp.parse2dArrayDouble();
		int[] y = tfp.parseArrayInt();
		double[] recweight = tfp.parseArrayDouble();
		int numclasses = tfp.parseInt();
		int numbits = tfp.parseInt();

		int[] regTypes = new int[theInstancesTF.length];
		for(int i = 0; i < regTypes.length; i++)
			regTypes[i] = RegulatorBindingData.TF;
		
		// Construct and train the regressor
		DREM_FastLogistic2 reg = new DREM_FastLogistic2(theInstancesIndex,
				theInstances, theInstancesTFIndex, theInstancesTF, y,
				recweight, numclasses, numbits, regTypes);
		reg.setRidge(1.0);
		reg.train();

		// Read in model file and parse coeffs
		BufferedReader modelbr = new BufferedReader(new FileReader(modelFile));
		ArrayList<Double> modelCoeffList = new ArrayList<Double>();
		StringTokenizer modelcoeffst = new StringTokenizer(modelbr.readLine(),
				DELIMS);
		modelcoeffst.nextToken();
		while (modelcoeffst.hasMoreTokens()) {
			modelcoeffst.nextToken();
			modelCoeffList.add(Double.parseDouble(modelcoeffst.nextToken()));
		}

		// Verify reg against the saved model file
		String dcoeffString = reg.toString();
		ArrayList<Double> dCoeffList = new ArrayList<Double>();
		StringTokenizer dcoeffst = new StringTokenizer(dcoeffString, DELIMS);
		dcoeffst.nextToken();
		while (dcoeffst.hasMoreTokens()) {
			dcoeffst.nextToken();
			dCoeffList.add(Double.parseDouble(dcoeffst.nextToken()));
		}
		
		if(shouldPass)
			assertTrue("Comparing with saved model coeffs",
					eachEntryWithinEpsilon(dCoeffList, modelCoeffList, false));
		else
			assertFalse("Comparing with saved model coeffs (should fail)",
					eachEntryWithinEpsilon(dCoeffList, modelCoeffList, true));
	}

	public boolean eachEntryWithinEpsilon(ArrayList<Double> answer,
			ArrayList<Double> recorded, boolean wantFailue) {
		boolean fails = false;
		for (int i = 0; i < answer.size(); i++) {
			if (!withinEpsilon(answer.get(i), recorded.get(i))) {
				if (!wantFailue) {
					System.out.print("Entry " + i + " differs by ");
					System.out
							.println(Math.abs(answer.get(i) - recorded.get(i)));
					System.out.println("Answer: " + answer.get(i));
					System.out.println("Recorded: " + recorded.get(i));
				}
				fails = true;
			}
		}
		if (fails)
			return false;
		else
			return true;
	}

	public boolean withinEpsilon(Double answer, Double recorded) {
		return Math.abs(answer - recorded) <= EPSILON;
	}
}
