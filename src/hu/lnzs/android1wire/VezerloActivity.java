package hu.lnzs.android1wire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hu.lnzs.android1wire.data.ErzekeloData;
import hu.lnzs.android1wire.data.ListViewAdapter;
import hu.lnzs.android1wire.data.VezerloData;
import hu.lnzs.android1wire.logic.DownloaderTask;
import hu.lnzs.android1wire.logic.Erzekelo;
import hu.lnzs.android1wire.logic.IDataLoader;
import android.os.Bundle;
import android.app.Activity;
import android.content.DialogInterface;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class VezerloActivity extends Activity implements IDataLoader {
	private TextView vezId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vezerlo);
		vezId = (TextView) findViewById(R.id.labelVezerlo);
		vezId.setText(VezerloData.id);
		DownloaderTask dLoader = new DownloaderTask(this,
				DownloaderTask.DATATYPE.VEZERLO, VezerloData.id);
		dLoader.execute();
		VezerloData.dataByte = "130";
		loadDataFromObj();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.vezerlo, menu);
		return true;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
	}

	@Override
	public void loadDataFromObj() {
		ListView mLv;
		String dataBinary = Integer.toBinaryString(Integer.valueOf(VezerloData.dataByte));
		
		mLv = (ListView) findViewById(hu.lnzs.android1wire.R.id.listVezerlo);
		final ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
		short i = 0;
		for (char vezerlo : dataBinary.toCharArray()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put(ListViewAdapter.TEXT1, String.valueOf(++i));
			map.put(ListViewAdapter.TEXT2, String.valueOf(vezerlo).equals("1") ? "bekapcsolva" : "kikapcsolva" );
			list.add(map);
		}
		final SimpleAdapter adapter = ListViewAdapter.createAdapter(this, list);
		mLv.setAdapter(adapter);
	}
}