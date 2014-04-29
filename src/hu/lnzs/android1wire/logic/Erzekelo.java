package hu.lnzs.android1wire.logic;

import java.util.Map;

public class Erzekelo {
	private String nev;
	private String address;
	private String alias;
	private int crc8;
	private String errata;
	private int family;
	private String id;
	private String locator;
	private String power;
	private String r_address;
	private String r_id;
	private String r_locator;
	private Float temperature;
	private short temphigh;
	private short templow;
	private String type;
	private boolean isSet;
	
	/** Constructor */
	public Erzekelo(Map<String, String> adatok){
		nev = adatok.get("nev");
		address = adatok.get("address");
		alias = adatok.get("alias");
		crc8 = Integer.valueOf(adatok.get("crc8"));
		errata = adatok.get("errata");
		family = Integer.valueOf(adatok.get("family"));
		id = adatok.get("id");
		power = adatok.get("power");
		r_address = adatok.get("r_address");
		r_id = adatok.get("r_id");
		r_locator = adatok.get("r_locator");
		temperature = Float.valueOf(adatok.get("temperature"));
		temphigh = Integer.valueOf(adatok.get("temphigh")).shortValue();
		templow = Integer.valueOf(adatok.get("templow")).shortValue();
		type = adatok.get("type");
	}
	/** Constructor */
	public Erzekelo(String paramNev){
		nev = paramNev;
		id = paramNev;
		temperature = 0f;
		isSet = false;
	}
	
	public void setData(Map<String, String> adatok){
		//nev = adatok.get("nev");
		address = adatok.get("address");
		alias = adatok.get("alias");
		//crc8 = Integer.valueOf(adatok.get("crc8"));
		//errata = adatok.get("errata");
		family = Integer.valueOf(adatok.get("family"));
		/* azért, hogy ne írjuk felül az eredetit */
		//id = adatok.get("id");
		power = adatok.get("power");
		r_address = adatok.get("r_address");
		r_id = adatok.get("r_id");
		r_locator = adatok.get("r_locator");
		temperature = Float.valueOf(adatok.get("temperature"));
		//temphigh = Integer.valueOf(adatok.get("temphigh")).shortValue();
		//templow = Integer.valueOf(adatok.get("templow")).shortValue();
		type = adatok.get("type");
		isSet = true;
	}
	

	/* GETTERS */
	public String getNev() {
		return nev;
	}
	public String getAddress() {
		return address;
	}
	public String getAlias() {
		return alias;
	}
	public int getCrc8() {
		return crc8;
	}
	public String getErrata() {
		return errata;
	}
	public int getFamily() {
		return family;
	}
	public String getId() {
		return id;
	}
	public String getLocator() {
		return locator;
	}
	public String getPower() {
		return power;
	}
	public String getR_address() {
		return r_address;
	}
	public String getR_id() {
		return r_id;
	}
	public String getR_locator() {
		return r_locator;
	}
	public Float getTemperature() {
		return temperature;
	}
	public short getTemphigh() {
		return temphigh;
	}
	public short getTemplow() {
		return templow;
	}
	public String getType() {
		return type;
	}

	public void setNev(String nev) {
		this.nev = nev;
	}
	public boolean isSet(){
		return isSet;
	}
	
}