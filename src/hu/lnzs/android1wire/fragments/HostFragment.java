package hu.lnzs.android1wire.fragments;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import hu.lnzs.android1wire.R;
import hu.lnzs.android1wire.data.HostData;
import android.os.Bundle;
import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class HostFragment extends Fragment {

	private View mRootView;
	private Context mContext;
	private EditText hostText;
	private EditText portText;
	private CheckBox chkEmulator;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.host_settings_fragment,
				container, false);
		mContext = inflater.getContext();
		// nem kell: Bundle args = getArguments();
		hostText = (EditText) mRootView.findViewById(R.id.hostText);
		portText = (EditText) mRootView.findViewById(R.id.portText);
		Button mentes = (Button) mRootView.findViewById(R.id.btnSaveHost);
		chkEmulator = (CheckBox) mRootView.findViewById(R.id.chkEmulator);

		mentes.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				saveData(v);
			}
		});
		/* be kell tölteni az adatokat az adatforrásból, ha létezik! */
		loadDataFromObject();
		return mRootView;
	}

	private void loadDataFromObject() {
		if (HostData.getHost() != null) {
			hostText.setText(HostData.getHost().getHostName());
			portText.setText(String.valueOf(HostData.getHost().getPort()));
		}
	}

	public void saveData(View v) {
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
		HostData.initHostData(hostText.getText().toString(), portText.getText()
				.toString());
		HostData.setEmulator(chkEmulator.isChecked());
		HostData.saveDataToFile(mContext);

	}

}
