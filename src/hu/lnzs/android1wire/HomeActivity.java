package hu.lnzs.android1wire;

import hu.lnzs.android1wire.data.ErzekeloData;
import hu.lnzs.android1wire.data.HostData;
import hu.lnzs.android1wire.data.VezerloData;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity {

	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		listView = (ListView) findViewById(R.id.menuList);
		ArrayAdapter<String> adapter = createArrayAdapter();
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				itemClick(arg1);
			}
		});
	}

	private ArrayAdapter<String> createArrayAdapter() {
		List<String> list = new ArrayList<String>();
		list.add(getString(R.string.erzekelo));
		list.add(getString(R.string.vezerlo));
		list.add(getString(R.string.beallitas));
		return new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	private void itemClick(View v) {
		Intent mIntent = new Intent();
		try {
			TextView text = (TextView) v;
				/* nem túl szép megoldás, de egyelõre ez van */
			if (text.getText().equals(getString(R.string.beallitas))) {
				mIntent.setClass(this, SettingsActivity.class);
			} else if (text.getText().equals(getString(R.string.erzekelo))) {
				mIntent.setClass(this, ErzekeloActivity.class);
			} else if (text.getText().equals(getString(R.string.vezerlo))) {
				mIntent.setClass(this, VezerloActivity.class);
			}
			this.startActivity(mIntent);
		} catch (ActivityNotFoundException anfe){
			Log.e("itemClick", anfe.getLocalizedMessage());
		} catch (Exception e) {
			Toast.makeText(this, "Elem nem Textview", Toast.LENGTH_SHORT).show();
		}
	}
}
