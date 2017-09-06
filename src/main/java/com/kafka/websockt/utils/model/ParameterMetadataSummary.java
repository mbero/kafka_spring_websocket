package com.kafka.websockt.utils.model;

public class ParameterMetadataSummary {

	public String locationName;
	public String locationDescription;
	public String deviceName;
	public String deviceDescription;
	public String parameterName;
	public String unitName;

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getLocationDescription() {
		return locationDescription;
	}

	public void setLocationDescription(String locationDescription) {
		this.locationDescription = locationDescription;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceDescription() {
		return deviceDescription;
	}

	public void setDeviceDescription(String deviceDescription) {
		this.deviceDescription = deviceDescription;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public ParameterMetadataSummary() {

	}

	public ParameterMetadataSummary(String locationName, String locationDescription, String deviceName,
			String deviceDescription, String parameterName, String unitName) {
		super();
		this.locationName = locationName;
		this.locationDescription = locationDescription;
		this.deviceName = deviceName;
		this.deviceDescription = deviceDescription;
		this.parameterName = parameterName;
		this.unitName = unitName;
	}

}
