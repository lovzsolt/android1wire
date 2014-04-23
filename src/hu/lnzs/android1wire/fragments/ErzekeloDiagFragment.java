package hu.lnzs.android1wire.fragments;

import hu.lnzs.android1wire.R;
import hu.lnzs.android1wire.data.ErzekeloData;
import hu.lnzs.android1wire.logic.Erzekelo;
import hu.lnzs.android1wire.logic.IDataLoader;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class ErzekeloDiagFragment extends DialogFragment {
	private View fragmentView;
	private TextView erzIdText;
	private TextView erzNevText;
	private IDataLoader iLoader;
	public static ErzekeloDiagFragment newInstance(final String erznev,
			final String erzid, IDataLoader iLoader) {
		ErzekeloDiagFragment f = new ErzekeloDiagFragment();
		Bundle mBundle = new Bundle();
		mBundle.putString("erznev", erznev);
		mBundle.putString("erzid", erzid);
		f.setArguments(mBundle);
		f.iLoader = iLoader;
		return f;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Get the layout inflater
		LayoutInflater inflater = getActivity().getLayoutInflater();
		fragmentView = inflater.inflate(R.layout.erzekelo_dialog, null);
		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout

		builder.setView(fragmentView)
				.setPositiveButton(R.string.diag_ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Erzekelo erz = ErzekeloData
										.getErzById((String) erzIdText
												.getText());
								erz.setNev(erzNevText.getText().toString());
								ErzekeloData.putMap(erz);
								iLoader.onClick(dialog, id);
							}
						})
				.setNegativeButton(R.string.diag_megse,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// do something
							}
						});
		Bundle args = this.getArguments();

		erzIdText = (TextView) fragmentView.findViewById(R.id.erzIdText);
		erzNevText = (TextView) fragmentView.findViewById(R.id.erzNevText);
		if (args != null) {
			erzIdText.setText(args.getString("erzid"));
			erzNevText.setText(args.getString("erznev"));
		}

		return builder.create();
	}
}
