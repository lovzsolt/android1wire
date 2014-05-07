package hu.lnzs.android1wire.data;

import hu.lnzs.android1wire.logic.Vezerlo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class VezerloData {
	private static String id;
	public static String getId() {
		return id;
	}

	private static SortedMap<String, Vezerlo> vezMap;
	public static SortedMap<String, Vezerlo> getVezMap() {
		return vezMap;
	}

	public static String getDataByte() {
		return dataByte;
	}
	
	public static Vezerlo getVezById(String pId){
		return vezMap.get(pId);
	};
	
	public static void putMap(Vezerlo vez){
		vezMap.put(vez.getId(), vez);
	};

	public static void setDataByte(String dataByte) {
		VezerloData.dataByte = dataByte;
		String dataBinary = Integer.toBinaryString(Integer.valueOf(VezerloData.dataByte));
		vezMap = new TreeMap<String, Vezerlo>();
		Integer i =dataBinary.length();
		for (char vezValue : dataBinary.toCharArray()) {
			Vezerlo vez = new Vezerlo(i.toString(), i.toString(), vezValue);
			vezMap.put((i--).toString(), vez);
		}
	}

	private static String dataByte;
	private static final String FILENAME = "vezerlo_file";

	public static void init(String pId) {
		id = pId;
	}

	public static boolean loadDataFromFile(Context ctx) {
		FileInputStream fis;
		try {
			vezMap = new TreeMap<String, Vezerlo>();
			File hostFile = ctx.getFileStreamPath(FILENAME);
			if (!hostFile.exists()) {
				return false;
			}
			fis = ctx.openFileInput(FILENAME);
			BufferedReader bsr = new BufferedReader(new InputStreamReader(fis));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = bsr.readLine()) != null) {
				sb.append(line);
			}
			fis.close();
			Log.w("jsonErzekelo", sb.toString());
			JSONObject vezObj = new JSONObject(sb.toString());
			/* a server elérés adatainak betöltése objektumba */
			Iterator<String> vezerloIterator = vezObj.keys();
			if (vezerloIterator.hasNext()) {
				id = vezerloIterator.next();
				if (id.equals("detail")){
				JSONObject detailObj = (JSONObject)vezObj.get(id);
				Iterator<String> detailIterator = detailObj.keys();
				while(detailIterator.hasNext()){
					String detailId = vezerloIterator.next();
					Vezerlo mVez = new Vezerlo(detailId, (String)detailObj.get(detailId), '-');
					putMap(mVez);	
				}
				
				} else {
				dataByte = (String) vezObj.get(id);
				}
			}
		} catch (JSONException e) {
			Log.e("verzerlo_loadData", e.getLocalizedMessage());
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			Toast.makeText(ctx, "nincs ilyen fájl", Toast.LENGTH_SHORT)
					.show();
			Log.e("verzerlo_loadData", e.getLocalizedMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("verzerlo_loadData", e.getLocalizedMessage());
			e.printStackTrace();
		} catch (Exception e) {
			Log.e("verzerlo_loadData", e.getLocalizedMessage());
			e.printStackTrace();
		}
		return true;
	}

	public static boolean saveDataToFile(Context context) {
		/*
		 * menteni fájlba, adatbázisba, stb. az adatok, ha nincs vagy rossz az
		 * adat akkor hibaüzenetet kiírni.
		 */
		// SaverTask saver = new SaverTask(this,SaverTask.SaveTypes.HOST);
		// saver.execute();
		/*
		 * egyelõre a fõszálban mûködik a mentés, de ezt késõbb lehet
		 * háttérszálba tenni!
		 */
		FileOutputStream fileOutStream;
		JSONObject vezerloData = new JSONObject();
		JSONObject vezDetailData = new JSONObject();
		try {
			vezerloData.put(id, dataByte);
			for(Vezerlo vez : vezMap.values()){
				vezDetailData.put(vez.getId(), vez.getNev());
			}
			vezerloData.put("detail", vezDetailData);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			fileOutStream = context.openFileOutput(FILENAME,
					Context.MODE_PRIVATE);
			fileOutStream.write(vezerloData.toString().getBytes());
			fileOutStream.close();
			Toast.makeText(context, "Mentés sikerült", Toast.LENGTH_SHORT)
					.show();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public static void vezerloFeldolgozo(String httpValasz) {
		Log.w("hvalasz", httpValasz);
		Document doc = Jsoup.parse(httpValasz);
		Element table = doc.getElementsByTag("table").get(1);
		/* vezerlõ Byte formátumban */
		setDataByte(table
				.getElementsByAttributeValue("name", "PIO.BYTE").get(0).val()
				.trim());
		Log.i("vez_data_byte", VezerloData.getDataByte());
		/* vezérlõ tömbként */
	}

}
