package hu.lnzs.android1wire;

import hu.lnzs.android1wire.data.VezerloData;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class VezerloActivity extends Activity {

	private TextView vezId;
	private TextView vezData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vezerlo);
		
		vezId = (TextView)findViewById(R.id.labelVezerlo);
		vezData = (TextView)findViewById(R.id.lbl_vezerlo_data);
		
		vezId.setText(VezerloData.id);
		vezData.setText(VezerloData.dataByte.toString());
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.vezerlo, menu);
		return true;
	}
}