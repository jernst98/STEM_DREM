package edu.cmu.cs.sb.drem.tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import edu.cmu.cs.sb.core.DataSetCore;
import edu.cmu.cs.sb.drem.DREM_DataSet;
import edu.cmu.cs.sb.drem.RegulatorBindingData;

public class MIRNABindingDataTest {
	private String filepath = "TestFiles/TestMIRNAData/";
	private String smallGridMIRNAFile = filepath+"mirnaGRID.txt";
	private String smallGridTFFile = filepath+"tfGRID.txt";
	private String small3ColMIRNAFile = filepath+"mirna3Col.txt";
	private String small3ColTFFile = filepath+"tf3Col.txt";
	@Test
	public void smallGridBindingDataSet() throws IOException
	{
		//Create a data set with fake info for use by the RegulatorBindingData constructor
		DataSetCore dsc = new DataSetCore();
		dsc.data = new double[4][];
		dsc.genenames = new String[4];
		dsc.genenames[0] = "GENE1";
		dsc.genenames[1] = "GENE2";
		dsc.genenames[2] = "GENE3";
		dsc.genenames[3] = "GENE4";
		dsc.probenames = new String[4];
		dsc.probenames[0] = "GENE1";
		dsc.probenames[1] = "GENE2";
		dsc.probenames[2] = "GENE3";
		dsc.probenames[3] = "GENE4";
		dsc.numcols = 1;
		DREM_DataSet theds = new DREM_DataSet(dsc,null);
		RegulatorBindingData rbd = new RegulatorBindingData(smallGridTFFile, smallGridMIRNAFile, 
				false, theds, "|;,", false, null, null, null);
		//Creates the expected arrays for comparison with what the constructor makes
		double[][] gene2RegData = {{1,1,1,1},{1,1},{1,1},{1,1,1}};
		int [][] gene2RegDataIndex = {{1,2,4,6},{0,4},{5,6},{0,3,6}};
		double[][] reg2GeneData = {{1,1},{1},{1},{1},{1,1},{1},{1,1,1}};
		int[][] reg2GeneDataIndex = {{1,3},{0},{0},{3},{0,1},{2},{0,2,3}};
		int numReg = 7;
		String[] regNames = {"TF1","TF2","TF3","MIRNA1","MIRNA2","MIRNA3","MIRNA4"};
		int[] regTypes = {RegulatorBindingData.TF,RegulatorBindingData.TF,
				RegulatorBindingData.TF,RegulatorBindingData.MIRNA,
				RegulatorBindingData.MIRNA,RegulatorBindingData.MIRNA,
				RegulatorBindingData.MIRNA};
		double[] existingBindValsSorted = {0,1};
		
		check2dArray(gene2RegData,rbd.gene2RegBinding[0],"gene2RegulatorBinding");
		check2dArray(gene2RegDataIndex,rbd.gene2RegBindingIndex[0], "gene2RegulatorBindingIndex");
		check2dArray(reg2GeneData,rbd.reg2GeneBinding[0], "regulator2GeneBinding");
		check2dArray(reg2GeneDataIndex,rbd.reg2GeneBindingIndex[0],"regulator2GeneBindingIndex");
		assertTrue("Comparing count of regulators", numReg == rbd.numberRegs);
		checkArray(regNames, rbd.regNames, "regulatorNames");
		checkArray(regTypes,rbd.regTypes, "regulatorTypes");
		checkArray(existingBindValsSorted, rbd.existingBindingValuesSorted,
				"existingBindingValuesSorted");
		check2dArray(gene2RegData,rbd.gene2RegMaxBinding,"gene2RegulatorMaxBinding");
		check2dArray(gene2RegDataIndex,rbd.gene2RegMaxBindingIndex, "gene2RegulatorMaxBindingIndex");
	}
	@Test
	public void small3ColBindingDataSet() throws IOException
	{
		//Create a data set with fake info for use by the RegulatorBindingData constructor
		DataSetCore dsc = new DataSetCore();
		dsc.data = new double[4][];
		dsc.genenames = new String[4];
		dsc.genenames[0] = "GENE1";
		dsc.genenames[1] = "GENE2";
		dsc.genenames[2] = "GENE3";
		dsc.genenames[3] = "GENE4";
		dsc.probenames = new String[4];
		dsc.probenames[0] = "GENE1";
		dsc.probenames[1] = "GENE2";
		dsc.probenames[2] = "GENE3";
		dsc.probenames[3] = "GENE4";
		dsc.numcols = 1;
		DREM_DataSet theds = new DREM_DataSet(dsc,null);
		RegulatorBindingData rbd = new RegulatorBindingData(small3ColTFFile, small3ColMIRNAFile, 
				false, theds, "|;,", false, null, null, null);
		//Creates the expected arrays for comparison with what the constructor makes
		double[][] gene2RegData = {{1,1,1,1},{1,1},{1,1},{1,1,1}};
		int [][] gene2RegDataIndex = {{1,2,4,6},{0,4},{5,6},{0,3,6}};
		double[][] reg2GeneData = {{1,1},{1},{1},{1},{1,1},{1},{1,1,1}};
		int[][] reg2GeneDataIndex = {{1,3},{0},{0},{3},{0,1},{2},{0,2,3}};
		int numReg = 7;
		String[] regNames = {"TF1","TF2","TF3","MIRNA1","MIRNA2","MIRNA3","MIRNA4"};
		int[] regTypes = {RegulatorBindingData.TF,RegulatorBindingData.TF,
				RegulatorBindingData.TF,RegulatorBindingData.MIRNA,
				RegulatorBindingData.MIRNA,RegulatorBindingData.MIRNA,
				RegulatorBindingData.MIRNA};
		double[] existingBindValsSorted = {0,1};
		
		check2dArray(gene2RegData,rbd.gene2RegBinding[0],"gene2RegulatorBinding");
		check2dArray(gene2RegDataIndex,rbd.gene2RegBindingIndex[0], "gene2RegulatorBindingIndex");
		check2dArray(reg2GeneData,rbd.reg2GeneBinding[0], "regulator2GeneBinding");
		check2dArray(reg2GeneDataIndex,rbd.reg2GeneBindingIndex[0],"regulator2GeneBindingIndex");
		assertTrue("Comparing count of regulators", numReg == rbd.numberRegs);
		checkArray(regNames, rbd.regNames, "regulatorNames");
		checkArray(regTypes,rbd.regTypes, "regulatorTypes");
		checkArray(existingBindValsSorted, rbd.existingBindingValuesSorted,
				"existingBindingValuesSorted");
	}
	public void checkArray(String[] arr1, String[] arr2, String name)
	{
		assertTrue("Comparing "+name+" array sizes", 
				arr1.length == arr2.length);
		for(int j = 0; j < arr1.length; j++)
		{
			assertTrue("Comparing entries of "+name+"["+j+"]:\n"
					+arr1[j]+"\n"+arr2[j],
					arr1[j].equals(arr2[j]));
		}
	}
	public void checkArray(double[] arr1, double[] arr2, String name)
	{
		assertTrue("Comparing "+name+" array sizes", 
				arr1.length == arr2.length);
		for(int j = 0; j < arr1.length; j++)
		{
			assertTrue("Comparing entries of "+name+"["+j+"]:\n"
					+arr1[j]+"\n"+arr2[j],
					arr1[j] == arr2[j]);
		}
	}
	public void check2dArray(double[][] arr1, double[][] arr2, String name)
	{
		assertTrue("Comparing "+name+" array sizes", 
				arr1.length == arr2.length);
		for(int i = 0; i < arr1.length; i++)
		{
			checkArray(arr1[i],arr2[i],name+"["+ i+"]");
		}
	}
	public void checkArray(int[] arr1, int[] arr2, String name)
	{
		assertTrue("Comparing "+name+" array sizes", 
				arr1.length == arr2.length);
		for(int j = 0; j < arr1.length; j++)
		{
			assertTrue("Comparing entries of "+name+"["+j+"]:\n"
					+arr1[j]+"\n"+arr2[j],
					arr1[j] == arr2[j]);
		}
	}
	public void check2dArray(int[][] arr1, int[][] arr2, String name)
	{
		assertTrue("Comparing "+name+" array sizes", 
				arr1.length == arr2.length);
		for(int i = 0; i < arr1.length; i++)
		{
			checkArray(arr1[i],arr2[i],name+"["+ i+"]");
		}
	}
}
