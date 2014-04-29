package hu.lnzs.android1wire.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class VezerloData {
	public static String id;
	public static String dataByte;
	private static final String FILENAME = "vezerlo_file";
	
	public static void init(String pId, String data){
		id = pId;
		dataByte = data;
	}
	
	public static boolean loadDataFromFile(Context context) {
		FileInputStream fis;
		try {
			File hostFile = context.getFileStreamPath(FILENAME);
			if (!hostFile.exists()) {
				return false;
			}
			fis = context.openFileInput(FILENAME);
			BufferedReader bsr = new BufferedReader(new InputStreamReader(fis));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = bsr.readLine()) != null) {
				sb.append(line);
			}
			fis.close();
			Log.w("jsonErzekelo", sb.toString());
			JSONObject vezObj = new JSONObject(sb.toString());
			/* a server el�r�s adatainak bet�lt�se objektumba */

			Iterator<String> vezerloIterator = vezObj.keys();
				id = vezerloIterator.next();
				dataByte = (String) vezObj.get(id);
		} catch (JSONException e) {
			Log.e("verzerlo_loadData", e.getLocalizedMessage());
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			Toast.makeText(context, "nincs ilyen f�jl", Toast.LENGTH_SHORT)
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
		 * menteni f�jlba, adatb�zisba, stb. az adatok, ha nincs vagy rossz az
		 * adat akkor hiba�zenetet ki�rni.
		 */
		// SaverTask saver = new SaverTask(this,SaverTask.SaveTypes.HOST);
		// saver.execute();
		/*
		 * egyel�re a f�sz�lban m�k�dik a ment�s, de ezt k�s�bb lehet
		 * h�tt�rsz�lba tenni!
		 */
		FileOutputStream fileOutStream;
		JSONObject vezerloData = new JSONObject();
		try {
				vezerloData.put(id, dataByte);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			fileOutStream = context.openFileOutput(FILENAME,
					Context.MODE_PRIVATE);
			fileOutStream.write(vezerloData.toString().getBytes());
			fileOutStream.close();
			Toast.makeText(context, "Ment�s siker�lt", Toast.LENGTH_SHORT)
					.show();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	
}
