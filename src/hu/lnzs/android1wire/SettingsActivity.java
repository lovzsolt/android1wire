package hu.lnzs.android1wire;

import hu.lnzs.android1wire.fragments.HostFragment;
import hu.lnzs.android1wire.fragments.ErzekeloFragment;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class SettingsActivity extends FragmentActivity {
	private Tab tab[];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);
		tab = new Tab[2];
		int i=0;
		tab[i] = actionBar
				.newTab()
				.setText(R.string.beallitas)
				.setTabListener(
						new TabListener<HostFragment>(this, "hoszt",
								HostFragment.class));
		actionBar.addTab(tab[i],i++);
		tab[i] = actionBar
				.newTab()
				.setText(R.string.erzekelo)
				.setTabListener(
						new TabListener<ErzekeloFragment>(this, "erzekelo",
								ErzekeloFragment.class));
		actionBar.addTab(tab[i],i++);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	
}
