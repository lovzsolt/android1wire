package hu.lnzs.android1wire.data;

import hu.lnzs.android1wire.logic.Erzekelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class ErzekeloData {
	private static SortedMap<String, Erzekelo> erzekeloSortMap;
	public static final String FILENAME = "erzekelo_file";

	static {
		erzekeloSortMap = new TreeMap<String, Erzekelo>();
	}

	public static SortedMap<String, Erzekelo> getErzekeloSortMap() {
		return erzekeloSortMap;
	}

	public static void setErzekeloSortMap(SortedMap<String, Erzekelo> erzSortMap) {

		erzekeloSortMap.clear();
		erzekeloSortMap = erzSortMap;
	}

	public static void putMap(Erzekelo erz) {
		erzekeloSortMap.put(erz.getId(), erz);
	}

	public static Erzekelo getErzById(String erzId) {
		return erzekeloSortMap.get(erzId);
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
			JSONObject erzObject = new JSONObject(sb.toString());
			/* a server el�r�s adatainak bet�lt�se objektumba */

			Iterator<String> erzIterator = erzObject.keys();
			while (erzIterator.hasNext()) {
				String erzId = erzIterator.next();
				Erzekelo erz = new Erzekelo(erzId);
				erz.setNev((String) erzObject.get(erzId));
				putMap(erz);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			Toast.makeText(context, "nincs ilyen f�jl", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("erzekelo_loadData", e.getLocalizedMessage());
			e.printStackTrace();
		} catch (Exception e) {
			Log.e("erzekelo_loadData", e.getLocalizedMessage());
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
		JSONObject erzekeloData = new JSONObject();
		try {

			for (Entry<String, Erzekelo> erz : getErzekeloSortMap().entrySet()) {
				erzekeloData.put(erz.getKey(), erz.getValue().getNev());
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			fileOutStream = context.openFileOutput(FILENAME,
					Context.MODE_PRIVATE);
			fileOutStream.write(erzekeloData.toString().getBytes());
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
