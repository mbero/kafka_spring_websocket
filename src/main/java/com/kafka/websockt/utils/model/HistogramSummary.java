package com.kafka.websockt.utils.model;

public class HistogramSummary {

	private Integer readTagID;
	private ParameterMetadataSummary parameterMetadataSummary;
	private Integer meanValue;

	public Integer getReadTagID() {
		return readTagID;
	}

	public void setReadTagID(Integer readTagID) {
		this.readTagID = readTagID;
	}

	public ParameterMetadataSummary getParameterMetadataSummary() {
		return parameterMetadataSummary;
	}

	public void setParameterMetadataSummary(ParameterMetadataSummary parameterMetadataSummary) {
		this.parameterMetadataSummary = parameterMetadataSummary;
	}

	public Integer getMeanValue() {
		return meanValue;
	}

	public void setMeanValue(Integer meanValue) {
		this.meanValue = meanValue;
	}

	public HistogramSummary(Integer readTagID, ParameterMetadataSummary parameterMetadataSummary, Integer meanValue) {
		super();
		this.readTagID = readTagID;
		this.parameterMetadataSummary = parameterMetadataSummary;
		this.meanValue = meanValue;
	}

}
