package org.citygml4j.binding.cityjson.feature;

public class RailwayType extends AbstractTransportationComplexType {
	private final CityObjectTypeName type = CityObjectTypeName.RAILWAY;
	
	RailwayType() {
	}
	
	public RailwayType(String gmlId) {
		super(gmlId);
	}
	
	@Override
	public CityObjectTypeName getType() {
		return type;
	}

}