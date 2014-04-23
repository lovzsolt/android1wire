package hu.lnzs.android1wire.data;

public class VezerloData {
	public static String id;
	public static Byte dataByte;
	
	public static void init(String pId, String data){
		id = pId;
		dataByte = Byte.valueOf(data);
	}
}
