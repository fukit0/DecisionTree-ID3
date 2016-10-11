package com.ml;

import java.util.ArrayList;
import java.util.HashMap;


public class ID3 {

	private DataTable dataTable;
	private double entropy;
	
	public ID3() {

	}

	public DataTable getDataTable() {
		return dataTable;
	}


	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}
	
	public double getEntropy() {
		return entropy;
	}

	public void setEntropy(double entropy) {
		this.entropy = entropy;
	}

	public Node buildTree() {
		
		return runID3(dataTable.getSamples(), dataTable.getTargetAttributes(), dataTable.getAttributes());
	}
	
	public Node runID3 (ArrayList<SampleObject> samples, HashMap<String, Boolean> targetAttributes, ArrayList<Attribute> attributes) {
		
		if(areAllSamplesPositive(samples, Boolean.TRUE)){
			return new Leaf(new Label(Boolean.TRUE));
		}
		
		if(areAllSamplesNegative(samples, Boolean.TRUE)){
			return new Leaf(new Label(Boolean.FALSE));
		}
		
		// else
		Attribute bestAttribute = findBestAttribute(samples,targetAttributes,attributes);
			
		
		return null;
		
		
	}
	
	private Attribute findBestAttribute(ArrayList<SampleObject> samples, HashMap<String, Boolean> targetAttributes,
			ArrayList<Attribute> attributes) {
		
		double bestGain = -999;
		int bestAttribute = -999;
		
		for (Attribute attribute : attributes) {
			
			int attIndex = attribute.getColumnIndex();
			double gain = calculateGain(attIndex,samples,targetAttributes);
			
			
			if (gain >= bestGain) {
				bestGain = gain;
				bestAttribute = attIndex;
			}
		}
		
		for (Attribute attribute : attributes) {
			if (attribute.getColumnIndex() == bestAttribute)
				return attribute;
		}
		return null;
	}



	private double calculateGain(int attIndex, ArrayList<SampleObject> samples,
			HashMap<String, Boolean> targetAttributes) {

		// attribute value occurrences for the attribute with index @attIndex
		ArrayList<Integer> valueOccurrences = dataTable.getAttributeValueOccurrences(attIndex,samples);
		
		
		return 0;
	}

	
	public void calculateEntropy(){
		
		int total = dataTable.getSamples().size();
		int positives = 0;
		int negatives = 0;
		
		for (SampleObject sampleObject : dataTable.getSamples()) {
			
			if( sampleObject.getClassLabelValue()){
				positives++;
			}else{
				negatives++;
			}
		}
		
		double positiveRatio = (double)positives/total;
		double negativeRatio = (double)negatives/total;

		if (positiveRatio != 0)
			positiveRatio = -(positiveRatio) * (Math.log(positiveRatio)/Math.log(2));
		if (negativeRatio != 0)
			negativeRatio = - (negativeRatio) * (Math.log(negativeRatio)/Math.log(2));

		entropy = positiveRatio + negativeRatio;

		
	}
	
	/**
	 * 
	 * @param samples
	 * @param targetValue
	 * @return
	 */
	private boolean areAllSamplesNegative(ArrayList<SampleObject> samples, Boolean targetValue) {
		return areAllSamplesBelongToSameClass(samples,targetValue);
	}

	/**
	 * 
	 * @param samples
	 * @param targetValue
	 * @return
	 */
	private boolean areAllSamplesPositive(ArrayList<SampleObject> samples, Boolean targetValue) {
		return areAllSamplesBelongToSameClass(samples,targetValue);
	}

	private boolean areAllSamplesBelongToSameClass(ArrayList<SampleObject> samples, Boolean targetValue ){
		
		for (SampleObject sampleObject : samples) {
			
			if(sampleObject.getClassLabelValue().booleanValue() != targetValue.booleanValue()){
				return false;
			}
		}
		
		return true;
		
	}

	
	
	
	

	
}