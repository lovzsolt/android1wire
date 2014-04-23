package hu.lnzs.android1wire.logic;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class SaverTask extends AsyncTask<String, String, String>{

	private final String FILENAME = "host_file";
	public static enum SaveTypes{HOST, ERZEKELO, ERZEKELONEV};
	private Activity mAct;
	private SaveTypes mSaveType;
	
	public SaverTask(Activity act, SaveTypes saveType){
		mAct = act;
		mSaveType = saveType;
	}
	
	@Override
	protected String doInBackground(String... arg0) {
		
		switch(mSaveType){
		case HOST:
			saveHostData();
			break;
		case ERZEKELO: 
			break;
		case ERZEKELONEV: 
			break; 
		default:
			break; 
		}
		return mSaveType.toString();
	}

	@Override
	protected void onPostExecute(String result) {
		Toast.makeText(mAct.getApplicationContext(), "mentés sikeres", Toast.LENGTH_SHORT).show();
		super.onPostExecute(result);
	}
	
	private void saveHostData(){
		String string = "hello world!";
		FileOutputStream fos;
		try {
			fos = mAct.getApplicationContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);
			fos.write(string.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
