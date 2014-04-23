package hu.lnzs.android1wire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import hu.lnzs.android1wire.data.ErzekeloData;
import hu.lnzs.android1wire.data.HostData;
import hu.lnzs.android1wire.fragments.ErzekeloDiagFragment;
import hu.lnzs.android1wire.fragments.ErzekeloFragment;
import hu.lnzs.android1wire.logic.DownloaderTask;
import hu.lnzs.android1wire.logic.Erzekelo;
import hu.lnzs.android1wire.logic.IDataLoader;
import android.os.Bundle;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ErzekeloActivity extends Activity implements IDataLoader {

	private final String TEXT1 = "text1";
	private final String TEXT2 = "text2";
	private ListView mLv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_erzekelo);
		for (String key : ErzekeloData.getErzekeloSortMap().keySet()) {
			DownloaderTask dTask = new DownloaderTask(this,
					DownloaderTask.DATATYPE.CHILD, key);
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
			Map<String, String> map = new HashMap<String, String>();
			map.put(TEXT1, erz.getNev());
			map.put(TEXT2, erz.getTemperature().toString());
			list.add(map);
		}

		final SimpleAdapter adapter = createAdapter(list);
		mLv.setAdapter(adapter);
		mLv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				loadDataFromObj();
				mLv.invalidate();
			}
		});
	}

	public SimpleAdapter createAdapter(ArrayList<Map<String, String>> list) {
		/* View-kban szerepeltetni kívánt elemek azonosítója: */
		final String[] fromMapKey = new String[] { TEXT1, TEXT2 };
		/* listában szereplõ View-k azonosítója: */
		final int[] toLayoutId = new int[] { android.R.id.text1,
				android.R.id.text2 };
		return new SimpleAdapter(this, list,
				android.R.layout.simple_list_item_2, fromMapKey, toLayoutId);
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		loadDataFromObj();
		mLv.invalidate();
	}
}
