package hu.lnzs.android1wire;

import hu.lnzs.android1wire.data.ErzekeloData;
import hu.lnzs.android1wire.data.ListViewAdapter;
import hu.lnzs.android1wire.logic.DownloaderTask;
import hu.lnzs.android1wire.logic.Erzekelo;
import hu.lnzs.android1wire.logic.IDataLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class ErzekeloActivity extends Activity implements IDataLoader {

	private ListView mLv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_erzekelo);
		for (String key : ErzekeloData.getErzekeloSortMap().keySet()) {
			DownloaderTask dTask = new DownloaderTask(this,
					DownloaderTask.DATATYPE.ERZEKELO, key);
			dTask.execute();
		}
		loadDataFromObj();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.erzekelo, menu);
		return true;
	}

	public void loadDataFromObj() {
		mLv = (ListView) findViewById(hu.lnzs.android1wire.R.id.listErzekelo);
		final ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();

		for (Erzekelo erz : ErzekeloData.getErzekeloSortMap().values()) {
			if (erz.isSet()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put(ListViewAdapter.TEXT1, erz.getNev());
				map.put(ListViewAdapter.TEXT2, erz.getTemperature().toString());
				list.add(map);
			}
		}
		final SimpleAdapter adapter = ListViewAdapter.createAdapter(this, list);
		mLv.setAdapter(adapter);
		mLv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				loadDataFromObj();
				mLv.invalidate();
			}
		});
	}


	@Override
	public void onClick(DialogInterface dialog, int which) {
		loadDataFromObj();
		mLv.invalidate();
	}
}
