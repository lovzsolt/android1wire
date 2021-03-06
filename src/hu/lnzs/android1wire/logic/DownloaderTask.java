package hu.lnzs.android1wire.logic;

import hu.lnzs.android1wire.data.ErzekeloData;
import hu.lnzs.android1wire.data.VezerloData;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

public class DownloaderTask extends AsyncTask<String, String, String> {

	private String valasz;
	private OwHttpClient owClient;
	private IDataLoader mDLoader;
	protected ListView mLv;
	private String deviceKey;
	public enum DATATYPE{HEAD, ERZEKELO, VEZERLO};
	private DATATYPE mDataType;
	public DownloaderTask(IDataLoader dLoader, DATATYPE dType, String dKey) {
		mDLoader = dLoader;
		mDataType = dType;
		deviceKey = dKey;
	}
	

	@Override
	protected void onPostExecute(String result) {
		try {
			switch(mDataType){
			case HEAD:
				owClient.owhttpRespMainFeldolgozo(valasz);
				mDLoader.loadDataFromObj();
				break;
			case ERZEKELO:
				ErzekeloData.erzekeloFeldolgozo(valasz, this.deviceKey);
				//mDLoader.loadDataFromObj();
				//ErzekeloData.putMap(mErzekelo);
				mDLoader.onClick(null, 0);
				break;
			case VEZERLO:
				VezerloData.vezerloFeldolgozo(valasz);
				mDLoader.loadDataFromObj();
				break;
			}

		} catch (Exception e) {
			Log.e("postExecute", e.toString());
		}
		super.onPostExecute(result);
	}

	@Override
	protected void onProgressUpdate(String... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

	@Override
	protected String doInBackground(String... params) {
		Log.w("background", "start");
		try {
			owClient = new OwHttpClient();
			valasz = owClient.owhttpReader(this.deviceKey);
		} catch (Exception e) {
			Log.e("background", e.getLocalizedMessage());
		}
		Log.w("background", "end");
		return "siker";
	}

}
