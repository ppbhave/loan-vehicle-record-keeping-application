package project.pbhave.vehiclesell;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class inputForm extends AppCompatActivity {
    EditText KMS;
    EditText LITERS;
    EditText PUMP;
    DataBseHelper myDB;
    Bitmap photo;
    String directory,User,NOplate,name,Kms, Liters, Pump;
    int Rid;
    Button submit;
    private  static final int CAMERA_REQUEST=123;
    ImageView img;
    Boolean flag=false;
    TextView owner,nplate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_form);
        submit=(Button)findViewById(R.id.submit);
        KMS = (EditText) findViewById(R.id.editkms);
        LITERS = (EditText) findViewById(R.id.editliters);
        PUMP = (EditText) findViewById(R.id.editpump);
        final Button click=(Button) findViewById(R.id.picclick);
        owner=findViewById(R.id.owner);
        nplate=findViewById(R.id.noplate);
            myDB = new DataBseHelper(this);
    User= getIntent().getExtras().getString("Uname");
        NOplate=getIntent().getExtras().getString("PlateNo");
        String text=owner.getText()+User;
        owner.setText(text);
        text=nplate.getText()+NOplate;
        nplate.setText(text);
        // User="p9";NOplate="mh08";
          submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Kms = KMS.getText().toString();
                 Liters = LITERS.getText().toString();
                 Pump = PUMP.getText().toString();
                if(!flag || Kms.matches("") || Liters.matches("") || Pump.matches("")){
                    exception("Some feilds are empty!!");
                }else{
                    try{ directory=imageSave(photo);
                        Long result = myDB.insertData( User, NOplate, Kms, Liters, Pump, directory, name);
                        status(result);}
                    catch(SQLException e){exception(e.toString());}
                }}


        } );  }                                                               //oncreate() ended

    public void goToAct(View view){
        Intent i=new Intent(this,dataRead.class).putExtra("user",User).putExtra("plate",NOplate);
        startActivity(i);
    }

    public void status(Long result) {                             //onSubmitEvent
        if (result >= 0) {
            Toast.makeText(this, "Data inserted Succcessfully", Toast.LENGTH_SHORT).show();
              startActivity(new Intent(this,QRscan.class));
        } else {
            Toast.makeText(this, "Data unsuccessfully saved", Toast.LENGTH_SHORT).show();
        }

    }
    public void exception(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


    public void takePic(View view) {                                   //picture taken
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,CAMERA_REQUEST);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data)              //Camera result
    {
        if(requestCode == CAMERA_REQUEST && resultCode== Activity.RESULT_OK)
        {   img = (ImageView)findViewById(R.id.img1);
            photo=(Bitmap) data.getExtras().get("data");
            img.setImageBitmap(photo);
            flag=true;

        }
    }

    public String imageSave(Bitmap photo){                                            //internal storage

        Cursor res = myDB.getID();
        res.moveToFirst();
        ContextWrapper cw=new ContextWrapper(getApplicationContext());
      //  Rid=res.getInt(res.getColumnIndex("max(ID)"));
         Rid=res.getInt(0);//get MAx ID
         Rid++;
        name= "image"+Rid+".JPG";
        File path= cw.getDir("imageDirectory", ContextWrapper.MODE_PRIVATE);
        File f=new File(path,name);
        FileOutputStream fout;
        //if(f.exists())  f.delete();
        try {  fout=new FileOutputStream(f);
            photo.compress(Bitmap.CompressFormat.PNG,100,fout);
            fout.flush();
            fout.close();//must be set at last
        } catch (Exception e) {
            Toast.makeText(this,e.toString()+"",Toast.LENGTH_SHORT).show();
        }
        return path.getAbsolutePath();

    }
}
