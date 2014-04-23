package hu.lnzs.android1wire.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpHost;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class HostData {
	public static HttpHost host;
	public static final String FILENAME = "host_file";
	public static void initHostData(String h, String p){
		HostData.host = new HttpHost(h, Integer.valueOf(p));
		Log.i("hostlog",HostData.host.toString());
		
	}
	private static Boolean emulator = false;  
	
	public static Boolean getEmulator() {
		return emulator;
	}

	public static void setEmulator(Boolean emulator) {
		HostData.emulator = emulator;
	}

	public static URL getURL(String file) throws URISyntaxException, MalformedURLException{
		if(getEmulator()){
			file  = "owhttp/"+file;
		}
		URL mURL= new URL(host.getSchemeName(), host.getHostName(), HostData.host.getPort(), file);
		Log.e("url", mURL.toString());
		return mURL;
	}
	
	public static boolean loadDataFromFile(Context context) {
				File hostFile = context.getFileStreamPath(FILENAME);
		if (!hostFile.exists()){
			return false;
		}
		FileInputStream fis;
		try {
			fis = context.openFileInput(FILENAME);
			BufferedReader bsr = new BufferedReader(new InputStreamReader(fis));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = bsr.readLine()) != null) {
				sb.append(line);
			}
			fis.close();
			JSONObject hostData = new JSONObject(sb.toString());

			/* a server elérés adatainak betöltése objektumba */
			HostData.initHostData((String) hostData.get("host"),
					String.valueOf(hostData.get("port"))
					);
			
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			Toast.makeText(context, "nincs ilyen fájl", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("host_loadData", e.getLocalizedMessage());
			e.printStackTrace();
		}
	
	return true;
	}
	
	
	public static boolean saveDataToFile(Context context){
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
		FileOutputStream fos;
		JSONObject hostData = new JSONObject();
		try {
			/* mentés elõtt objektumba töltés */
			
			//HostData.initHostData(hostText.getText().toString(), portText.getText().toString());
			//HostData.setEmulator(chkEmulator.isChecked());
			hostData.put("host", host.getHostName());
			hostData.put("port", host.getPort());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			fos = context.openFileOutput(FILENAME,
					Context.MODE_PRIVATE);
			fos.write(hostData.toString().getBytes());
			fos.close();
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

	
	
	

	
	

}
