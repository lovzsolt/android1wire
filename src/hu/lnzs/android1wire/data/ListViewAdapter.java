package hu.lnzs.android1wire.data;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.widget.SimpleAdapter;

public class ListViewAdapter {
	
	public static final String TEXT1 = "text1";
	public static final String TEXT2 = "text2";
	
	public static SimpleAdapter createAdapter(Context ctx, ArrayList<Map<String, String>> list) {
		/* View-kban szerepeltetni k�v�nt elemek azonos�t�ja: */
		final String[] fromMapKey = new String[] { TEXT1, TEXT2 };
		/* list�ban szerepl� View-k azonos�t�ja: */
		final int[] toLayoutId = new int[] { android.R.id.text1,
				android.R.id.text2 };
		return new SimpleAdapter(ctx, list,
				android.R.layout.simple_list_item_2, fromMapKey, toLayoutId);
	}
}
