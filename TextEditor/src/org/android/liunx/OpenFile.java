/**
 * 
 */
package org.android.liunx;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author liunx
 *
 */
public class OpenFile extends Activity {
	private EditText etext;
	@Override
	public void onCreate(Bundle contents) {
		super.onCreate(contents);
	    setContentView(R.layout.open);
	    etext = (EditText) this.findViewById(R.id.EditText01);
		Toast.makeText(this, "I am OpenFile class", Toast.LENGTH_LONG).show();
		Bundle extras = getIntent().getExtras();
		//Intent bgIntent = getIntent();
		//String bgGlobal = bgIntent.;
		if (extras != null) {
			String getname = extras.getString("DEFAULTTEXT");
			Toast.makeText(this, getname, Toast.LENGTH_LONG).show();
    		try {
			// Read the file again
			FileInputStream fIn = openFileInput(getname.toString());
			InputStreamReader isr = new InputStreamReader(fIn);
			// We have to ready a buf to store the contents
			char[] InputBuffer = new char[512];
			isr.read(InputBuffer);
			etext.setText(InputBuffer, 0, 511);
			// then close the file
			isr.close();
			
    		} catch (IOException ioe) {
			ioe.printStackTrace();
		
			}
		}
		
	}

}
