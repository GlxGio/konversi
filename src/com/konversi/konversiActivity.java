package com.konversi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class konversiActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final Button exit = (Button) findViewById(R.id.exit);
        final Button hitung = (Button) findViewById(R.id.ok);
        final AlertDialog.Builder pesan = new AlertDialog.Builder(this);
        final EditText input = (EditText) findViewById (R.id.input);
        final TextView hasil = (TextView) findViewById (R.id.hasil);
        final Spinner spinner = (Spinner) findViewById (R.id.spinner);
        final Spinner spinner1 = (Spinner) findViewById (R.id.spinner1);
        final TextView date = (TextView) findViewById (R.id.date);
        
        final uraikonversiActivity cp = new uraikonversiActivity(this);
        
        date.setText("Update terakhir = "+ cp.gettanggal() + " . Sumber dari ECB");
        
        final String spinner_array[] = new String[cp.kodekonversi.size()];
        
        for (int i = 0; i < cp.kodekonversi.size(); i++) {
			spinner_array[i] = cp.kodekonversi.get(i);
		}
        
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, spinner_array);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner1.setAdapter(adapter);
       
		pesan.setMessage("Keluar Dari Program?")
		.setCancelable(false)
		.setPositiveButton("Ya",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						finish();
					}
				})
		.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		
		exit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				final AlertDialog pesan2;
				pesan2 = pesan.create();
				pesan2.show();
			}
		});
		
		hitung.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {

					float value = Float.parseFloat(input.getText().toString());
					value = value * cp.getnilaitukar(spinner.getSelectedItem().toString(), spinner1.getSelectedItem().toString());
					hasil.setText(String.valueOf(value));
				} catch (Exception ex) {
					hasil.setText(R.string.error);
				}
			}
		});
    }
}