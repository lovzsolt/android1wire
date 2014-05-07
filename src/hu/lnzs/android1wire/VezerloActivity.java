package hu.lnzs.android1wire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hu.lnzs.android1wire.data.ErzekeloData;
import hu.lnzs.android1wire.data.ListViewAdapter;
import hu.lnzs.android1wire.data.VezerloData;
import hu.lnzs.android1wire.fragments.ErzekeloDiagFragment;
import hu.lnzs.android1wire.fragments.VezDiagFragment;
import hu.lnzs.android1wire.logic.DownloaderTask;
import hu.lnzs.android1wire.logic.Erzekelo;
import hu.lnzs.android1wire.logic.IDataLoader;
import hu.lnzs.android1wire.logic.Vezerlo;
import android.os.Bundle;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class VezerloActivity extends Activity implements IDataLoader {
	private TextView vezId;
	public static FragmentManager vezFragMgr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vezerlo);
		vezId = (TextView) findViewById(R.id.labelVezerlo);
		vezId.setText(VezerloData.getId());
		DownloaderTask dLoader = new DownloaderTask(this,
				DownloaderTask.DATATYPE.VEZERLO, VezerloData.getId());
		dLoader.execute();
		vezFragMgr = this.getFragmentManager();
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
		mLv = (ListView) findViewById(hu.lnzs.android1wire.R.id.listVezerlo);
		final ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
		short i = 0;
		for (Vezerlo vez : VezerloData.getVezMap().values()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put(ListViewAdapter.TEXT1, vez.getNev());
			map.put(ListViewAdapter.TEXT2, vez.getValueName() );
			list.add(map);
		}
		final SimpleAdapter adapter = ListViewAdapter.createAdapter(this, list);
		mLv.setAdapter(adapter);
		
		final IDataLoader iLoader = this;
		mLv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				TextView item1 = (TextView) arg1
						.findViewById(android.R.id.text1);
				TextView item2 = (TextView) arg1
						.findViewById(android.R.id.text2);
				DialogFragment dialog = VezDiagFragment.newInstance(item1
						.getText().toString(), item2.getText().toString(), iLoader);
				dialog.show(vezFragMgr.beginTransaction(),
						"vezDiagFragment");
			}
		});
		
		
		
	}
}