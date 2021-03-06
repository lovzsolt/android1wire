package hu.lnzs.android1wire.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import hu.lnzs.android1wire.R;
import hu.lnzs.android1wire.data.ErzekeloData;
import hu.lnzs.android1wire.data.ListViewAdapter;
import hu.lnzs.android1wire.data.VezerloData;
import hu.lnzs.android1wire.logic.DownloaderTask;
import hu.lnzs.android1wire.logic.Erzekelo;
import hu.lnzs.android1wire.logic.IDataLoader;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ErzekeloFragment extends Fragment implements IDataLoader {

	@Override
	public void onResume() {
		loadDataFromObj();
		super.onResume();
	}

	private View mRootView;
	private Context mContext;
	private TextView tv;
	public static FragmentManager erzFragmentMgr;
	private ListView mLv;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.erzekelo_settings_fragment,
				container, false);
		mContext = inflater.getContext();
		// nem kell: Bundle args = getArguments();
		Button mBtnDownload = (Button) mRootView.findViewById(R.id.btnDonwload);
		tv = (TextView) mRootView.findViewById(R.id.dloadResult);
		mBtnDownload.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				onDownload(v);
			}
		});
		erzFragmentMgr = this.getFragmentManager();

		Button mBtnSave = (Button) mRootView.findViewById(R.id.saveButton);
		mBtnSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ErzekeloData.saveDataToFile(mContext);
				VezerloData.saveDataToFile(mContext);
			}
		});
		loadDataFromObj();
		return mRootView;
	}

	public void onDownload(View v) {
		Log.w("button", "clicked");
		tv.setText("download start...");

		DownloaderTask loaderTask = new DownloaderTask(this,
				DownloaderTask.DATATYPE.HEAD, "");
		loaderTask.execute();
	}

	public void loadDataFromObj() {

		mLv = (ListView) mRootView
				.findViewById(hu.lnzs.android1wire.R.id.listView1);
		final ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();

		for (Entry<String, Erzekelo> erz : ErzekeloData.getErzekeloSortMap()
				.entrySet()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put(ListViewAdapter.TEXT1, erz.getValue().getNev());
			map.put(ListViewAdapter.TEXT2, erz.getValue().getId());
			list.add(map);
		}

		final SimpleAdapter adapter = ListViewAdapter.createAdapter(mContext, list);

		mLv.setAdapter(adapter);
		final IDataLoader iLoader = this;
		mLv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				TextView item1 = (TextView) arg1
						.findViewById(android.R.id.text1);
				TextView item2 = (TextView) arg1
						.findViewById(android.R.id.text2);
				DialogFragment dialog = ErzekeloDiagFragment.newInstance(item1
						.getText().toString(), item2.getText().toString(), iLoader);
				dialog.show(erzFragmentMgr.beginTransaction(),
						"ErzekeloDiagFragment");
			}
		});
	}

	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		loadDataFromObj();
		mLv.invalidate();
		
	}
}
