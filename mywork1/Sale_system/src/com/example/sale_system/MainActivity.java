package com.example.sale_system;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.ClipData.Item;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {

EditText username,password,email,phoneno;
	Button add,delete,modify,show;
	SQLiteDatabase db;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       username=(EditText)findViewById(R.id.txtusername);
        password=(EditText)findViewById(R.id.txtpassword);
        email=(EditText)findViewById(R.id.txtemail);
        phoneno=(EditText)findViewById(R.id.txtphone);
        ///-------------------------------------
       add=(Button)findViewById(R.id.btnadd);
        delete=(Button)findViewById(R.id.btndelete);
       modify=(Button)findViewById(R.id.btnmodify);
        show=(Button)findViewById(R.id.btnview);
        //-------------------------------
        add.setOnClickListener(this);
       delete.setOnClickListener(this);
       modify.setOnClickListener(this);
        show.setOnClickListener(this);       
        ///---------database create
        db=openOrCreateDatabase("logindb", Context.MODE_PRIVATE, null);
    	db.execSQL("CREATE TABLE IF NOT EXISTS a(username VARCHAR,password VARCHAR,email VARCHAR);");
    }

    public void onClick(View view)
    {
    	if(view==add)
    	{
    		if(username.getText().toString().trim().length()==0||
    	    		   password.getText().toString().trim().length()==0||
    	    		  // phoneno.getText().toString().trim().length()==0||
    	    		   email.getText().toString().trim().length()==0)
    		{
    			showMessage("Error", "Please enter all values");
    			return;
    		}
    		//db.execSQL("INSERT INTO Login VALUES('"+username.getText().toString()+"','"+password.getText().toString()+ "','"+email.getText().toString()+"','"+phoneno.getText().toString()+");");
    		db.execSQL("INSERT INTO a VALUES('"+username.getText()+"','"+password.getText()+"','"+email.getText()+"');");
    		showMessage("Success", "Record added");
 		showMessage("Success", "Record added");
    		clearText();
    	if(view==delete)
    		{
    			if(username.getText().toString().trim().length()==0)
    			{
    				showMessage("Error", "Please Username fill.");
        			return;
    			}
    			Cursor c=db.rawQuery("SELECT * FROM a WHERE Username='"+username.getText()+"'", null);
    			if(c.moveToFirst())
        		{
        			db.execSQL("DELETE FROM a WHERE Username='"+username.getText()+"'");
        			showMessage("Success", "Record Deleted");
        		}
        		else
        		{
        			showMessage("Error", "Invalid UserName");
        		}
        		clearText();
    		}
    	}
    	if(view==modify)
    	{
    		if(username.getText().toString().trim().length()==0)
    		{
    			showMessage("Error", "Please enter UserName");
    			return;
    		}
    		Cursor c=db.rawQuery("SELECT * FROM a WHERE Username='"+username.getText()+"'", null);
    		if(c.moveToFirst())
    		{
    			db.execSQL("UPDATE a SET Username='"+username.getText()+"',PhoneNo='"+phoneno.getText()+"',Password='"+password.getText()+"' ,Email='"+email.getText()+"'WHERE Username='"+username.getText()+"'");
    			showMessage("Success", "Record Modified");
    		}
    		else
    		{
    			showMessage("Error", "Invalid UserName");
    		}
    		clearText();
    	}
    	if(view==show)
    	{
    		if(username.getText().toString().trim().length()==0)
    		{
    			showMessage("Error", "Please enter Usrname");
    			return;
    		}
    		Cursor c=db.rawQuery("SELECT * FROM a WHERE Username='"+username.getText()+"'", null);
    		//Cursor c=db.rawQuery("SELECT * FROM a", null);
    		if(c.moveToFirst())
    		{
    			username.setText(c.getString(0));
    			password.setText(c.getString(1));
    			email.setText(c.getString(2));
    			//phoneno.setText(c.getString(4));
    		}
    		else
    		{
    			showMessage("Error", "Invalid Rollno");
    			clearText();
    		}
    	}
    }
    
    public void showMessage(String title,String message)
    {
    	Builder builder=new Builder(this);
    	builder.setCancelable(true);
    	builder.setTitle(title);
    	builder.setMessage(message);
    	builder.show();
	}
    public void clearText()
    {
    	username.setText("");
    	password.setText("");
    email.setText("");
    	phoneno.setText("");
    	username.requestFocus();
    }
}
