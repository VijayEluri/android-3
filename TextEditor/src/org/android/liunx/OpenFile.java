/**
 * 
 */
package org.android.liunx;

import android.app.ListActivity;
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
		
	}

}
