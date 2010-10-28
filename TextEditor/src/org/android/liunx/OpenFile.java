/**
 * 
 */
package org.android.liunx;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * @author liunx
 *
 */
public class OpenFile extends ListActivity {
	@Override
	public void onCreate(Bundle contents) {
		super.onCreate(contents);
		Toast.makeText(this, "I am OpenFile class", Toast.LENGTH_LONG).show();
		Bundle extras = getIntent().getExtras();
		//Intent bgIntent = getIntent();
		//String bgGlobal = bgIntent.;
		if (extras != null) {
			String getname = extras.getString("DEFAULTTEXT");
			Toast.makeText(this, getname, Toast.LENGTH_LONG).show();
		}
		
	}

}
