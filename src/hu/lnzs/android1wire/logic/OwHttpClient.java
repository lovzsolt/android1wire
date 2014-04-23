package hu.lnzs.android1wire.logic;

import hu.lnzs.android1wire.data.ErzekeloData;
import hu.lnzs.android1wire.data.HostData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

public class OwHttpClient {
	protected Map<String, String> modulok;
	protected Map<String, String> adatok;
	public String valasz;

	public OwHttpClient() {
		adatok = new HashMap<String, String>();
		modulok = new HashMap<String, String>();
	}

	/**
	 * @param args
	 *            file : meghívandó fájl, url
	 * @throws Exception
	 */
	protected String owhttpReader(String file) {
		InputStream valaszStream = null;
		StringBuilder sb = new StringBuilder();
		// Document doc = null;
		try {
			HttpURLConnection urlConnection = (HttpURLConnection) HostData
					.getURL("").openConnection();
			// set up some things on the connection
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoOutput(true);
			// and connect!
			urlConnection.connect();

			 int status = urlConnection.getResponseCode();
			if (status >= HttpStatus.SC_BAD_REQUEST) {
				valaszStream = urlConnection.getErrorStream();
				Log.e("statusz", String.valueOf(status));
			} else {

				HttpClient httpclient = new DefaultHttpClient();
				HttpGet httpget = new HttpGet(HostData.getURL(file).toURI());
				// Execute the request
				HttpResponse response;
				try {
					response = httpclient.execute(httpget);
					// Examine the response status
					Log.i("Praeda", response.getStatusLine().toString());

					// Get hold of the response entity
					HttpEntity entity = response.getEntity();
					// If the response does not enclose an entity, there is no
					// need
					// to worry about connection release

					if (entity != null) {

						// A Simple JSON Response Read
						valaszStream = entity.getContent();
						if (valaszStream.available() > 0) {
							BufferedReader reader = new BufferedReader(
									new InputStreamReader(valaszStream));
							String line = null;
							// Read Server Response
							while ((line = reader.readLine()) != null) {
								// Append server response in string
								sb.append(line + "");
							}
							reader.close();
						}
						// now you have the string representation of the HTML
						// request
						valaszStream.close();
					}

				} catch (Exception e) {
				}

			}

			urlConnection.disconnect();
		} catch (MalformedURLException e) {
			Log.e("reader",
					"MalformedURLException hiba: " + e.getLocalizedMessage());
			// throw e;
		} catch (IOException e) {
			Log.e("reader", "IOexception hiba: " + e.toString());
			// throw e;
		} catch (Exception e) {
			Log.e("reader", "egyéb hiba: " + e.getLocalizedMessage());
		}
		return sb.toString();
	}

	protected void owhttpRespMainFeldolgozo(String httpValasz) throws Exception {
		Document doc = Jsoup.parse(httpValasz);
		Elements elemek = doc.getElementsByTag("BIG");
		Erzekelo mErzekelo = null;
		SortedMap<String, Erzekelo> erzekeloSortMap = new TreeMap<String, Erzekelo>();
		ArrayList<Erzekelo> erzekeloMap = new ArrayList<Erzekelo>();
		for (Element elem : elemek) {
			String elemId = elem.html();
			// érzékelõk
			if (elemId.startsWith("10.")) {
				mErzekelo = new Erzekelo(elemId);
				modulok.put(elemId, "erzekelo");
				erzekeloSortMap.put(elemId, mErzekelo);
				erzekeloMap.add(mErzekelo);
				// vezérlõ
			} else if (elemId.startsWith("29.")) {
				modulok.put(elemId, "vezerlo");
			}
		}
		ErzekeloData.setErzekeloSortMap(erzekeloSortMap);
	}

	protected void owhttpRespErzekeloFeldolgozo(String httpValasz,
			Erzekelo xErzekelo) {
		Log.w("hvalasz", httpValasz);
		Document doc = Jsoup.parse(httpValasz);
		Element table = doc.getElementsByTag("table").get(1);
		Elements tr = table.getElementsByTag("tr");
		String erzekeloNev = doc.getElementsByTag("H1").html();
		// Erzekelo mErzekelo = new Erzekelo(erzekeloNev);
		for (Element elem : tr) {
			Elements td = elem.getElementsByTag("td");
			String nev = td.get(0).getElementsByTag("B").html();
			String adat = null;
			if (td.get(1).hasText()) {
				adat = td.get(1).html();
			} else {
				adat = "ismeretlen";
			}
			adatok.put(nev, adat);

		}
		adatok.put("url", "?a=" + erzekeloNev);
		xErzekelo.setData(adatok);
	}

}
