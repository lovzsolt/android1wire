package hu.lnzs.android1wire;

import hu.lnzs.android1wire.data.VezerloData;
import hu.lnzs.android1wire.logic.DownloaderTask;
import hu.lnzs.android1wire.logic.IDataLoader;
import android.os.Bundle;
import android.app.Activity;
import android.content.DialogInterface;
import android.view.Menu;
import android.widget.TextView;

public class VezerloActivity extends Activity implements IDataLoader {
	private TextView vezId;
	private TextView vezData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vezerlo);
		vezId = (TextView) findViewById(R.id.labelVezerlo);
		vezData = (TextView) findViewById(R.id.lbl_vezerlo_data);
		vezId.setText(VezerloData.id);
		DownloaderTask dLoader = new DownloaderTask(this, DownloaderTask.DATATYPE.VEZERLO, VezerloData.id);
		dLoader.execute();
		
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
		vezData.setText(VezerloData.dataByte.toString());
		
	}
}