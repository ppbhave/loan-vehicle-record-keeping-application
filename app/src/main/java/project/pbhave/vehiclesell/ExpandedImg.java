package project.pbhave.vehiclesell;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Button;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ExpandedImg extends AppCompatActivity {
    ImageView img;
    String user,plate;
    DataBseHelper db=new DataBseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanded_img);
        img=findViewById(R.id.Expanded);
        int id =getIntent().getExtras().getInt("id");
        user=getIntent().getExtras().getString("user");
        plate=getIntent().getExtras().getString("plate");
       popup();
        Cursor c= db.RetriveForImage(id);
        c.moveToFirst();
        String  directory = c.getString(c.getColumnIndex("directory"));
        String name = c.getString(c.getColumnIndex("name"));

        File f = new File(directory, name);
        Bitmap photo;

        try {
            photo = BitmapFactory.decodeStream(new FileInputStream(f));
             img.setImageBitmap(photo);
        } catch (FileNotFoundException e) {
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }
        img.setClickable(true);
       img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) { gotoAct();                                    }
              });
}

public void gotoAct() { startActivity(new Intent(this,dataRead.class).putExtra("user",user).putExtra("plate",plate));}
public  void popup()
{ Toast.makeText(this,"Click on the image to go back",Toast.LENGTH_SHORT).show();}
}

