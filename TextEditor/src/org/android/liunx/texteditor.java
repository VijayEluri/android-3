package org.android.liunx;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

public class texteditor extends Activity {
	// the menus
	private static final int MENU_OPEN = Menu.FIRST;
	private static final int MENU_SAVE = Menu.FIRST + 1;
	private static final int MENU_DELETE = Menu.FIRST + 2;
	// the dialogs
	private static final int DIALOG_OPEN = 1;
	private static final int DIALOG_SAVE = 2;
	private static final int DIALOG_DELETE = 4;
	
	static String filename = "Hello, Liunx";
	// save edit text
	//private EditText saveContents;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // R.layout.save not initialized at the moment
        //saveContents = (EditText) this.findViewById(R.id.EditText01);
    }
    
    
    /**
     * We can do open/save/delete operations with menu 
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	
    	menu.add(0, MENU_OPEN, 0, R.string.open);
    	menu.add(0, MENU_SAVE, 0, R.string.save);
    	menu.add(0, MENU_DELETE, 0, R.string.delete);
    	return true;
    }
    
    /**
     * process the menu press events
     */
    @Override 
    public boolean onOptionsItemSelected(MenuItem item) {
    	// get the id which menu is selected
    	final int id = item.getItemId();
    	boolean retval = false;
    	switch (id) {
    	case MENU_OPEN:
    		showDialog(DIALOG_OPEN);
    		retval = true;
    		break;
    	case MENU_SAVE:
    		showDialog(DIALOG_SAVE);
    		retval = true;
    		break;
    	case MENU_DELETE:
    		showDialog(DIALOG_DELETE);
    		retval =true;
    		break;
    		default:
    			break;
    	}
    	return retval;
    }
    /**
     * when showDialog, onDialogCreate will be called 
     */
    @Override
    protected Dialog onCreateDialog(int id) {
    	final View dialogview;
    	// the switch sentence
    	switch (id) {
    	case DIALOG_OPEN:
    		//Intent intent = new Intent(this, OpenFile.class);
    	    //startActivity(intent);
    		LayoutInflater openflater = LayoutInflater.from(this);
    		dialogview = openflater.inflate(R.layout.open, null);
    		Toast.makeText(this, "Dialog open", Toast.LENGTH_LONG).show();
    		String[] flist = this.fileList();
//    		int i;
//    		for (i = 0; i < flist.length; i++) {
//    			Toast.makeText(this, flist[i].toString(), Toast.LENGTH_LONG).show();
//    		}
    		final String[] items = flist;
    		
            return new AlertDialog.Builder(this)
            .setTitle("Open a file")
            .setItems(flist, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    /* User clicked so do some stuff */
//                    new AlertDialog.Builder(texteditor.this)
//                            .setMessage("You selected: " + which + " , " + items[which])
//                            .show();
                    /* We should use a new activity to do editor work */
                	//texteditor.filename = items[which];
                    Intent intent = new Intent(texteditor.this, OpenFile.class);
                    /* Pass the message to the new activitity */
                    //intent.putExtra(texteditor.filename, which);
                    //intent.putExtra("FileName", items[which]);
                    Bundle b = new Bundle();
                    b.putString("DEFAULTTEXT", items[which]);
                    intent.putExtras(b);
                    startActivity(intent);

                }
            })
            .create();
    	case DIALOG_SAVE:
    		LayoutInflater saveflater = LayoutInflater.from(this);
    		dialogview = saveflater.inflate(R.layout.save, null);
    		Toast.makeText(this, "Dialog save", Toast.LENGTH_LONG).show();
    		return new AlertDialog.Builder(this)
    		.setTitle("Save file")
    		.setIcon(R.drawable.icon)
    		.setView(dialogview)
    		.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// Get the input file name 
					EditText saveview = (EditText) dialogview.findViewById(R.id.EditText01);
					String filename = saveview.getText().toString();
					try {
						FileOutputStream fOut = openFileOutput(filename, MODE_WORLD_READABLE);
						OutputStreamWriter osw = new OutputStreamWriter(fOut);
						osw.write("Hello, World!");
						osw.flush();
						osw.close();
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
					
				}
			})
			.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			})
    		.create();
    	case DIALOG_DELETE:
    		LayoutInflater deleteflater = LayoutInflater.from(this);
    		dialogview = deleteflater.inflate(R.layout.delete, null);
    		Toast.makeText(this, "Dialog delete", Toast.LENGTH_LONG).show();
    		return new AlertDialog.Builder(this)
    		.setTitle("Delete file")
    		.setIcon(R.drawable.icon)
    		.setView(dialogview)
    		.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.cancel();
					//Toast.makeText(this, "Ok", Toast.LENGTH_LONG).show();
					return;
					
				}
			})
			.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			})
    		.create();
    	}
    	// we can not return null
    	return null;
    	
    }
}