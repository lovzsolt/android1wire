package hu.lnzs.android1wire;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class VezerloActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vezerlo);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.vezerlo, menu);
		return true;
	}
}