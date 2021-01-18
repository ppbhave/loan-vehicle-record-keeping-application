package project.pbhave.vehiclesell;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class dataRead extends AppCompatActivity {
    DataBseHelper myDB;
    TableLayout tableLayout;
    TableRow head;
    String directory, name,user,plate;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_read);
        user=getIntent().getExtras().getString("user");
        plate=getIntent().getExtras().getString("plate");
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        head = (TableRow) findViewById(R.id.head);
        back = findViewById(R.id.toForm);
        myDB = new DataBseHelper(this);//DatabaseHelper
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        TableRow Row;
        try {
            Cursor c = myDB.Retrive();

            if (c != null && c.getCount() > 0) {
                ImageView meterImg[] = new ImageView[c.getCount()];
                int count = 0,id;
                while (c.moveToNext()) {  //retrive parameters
                    StringBuilder bf = new StringBuilder();
                    id=c.getInt(0);
                    bf.append("Owner         : ").append(c.getString(1)).append("\n");
                    bf.append("No.Plate      : ").append(c.getString(2)).append("\n");
                    bf.append("Kilometers   : ").append(c.getString(3)).append("\n");
                    bf.append("Liters            : ").append(c.getString(4)).append("\n");
                    bf.append("Filled at pump: ").append(c.getString(5)).append("\n");
                    directory = c.getString(6);
                    name = c.getString(7);
                    // creating imageview
                    Row = new TableRow(this);
                    Row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT)); //creating row by reference

                    meterImg[count] = new ImageView(this);
                    File f = new File(directory, name);
                    Bitmap photo = BitmapFactory.decodeStream(new FileInputStream(f));
                    meterImg[count] .setImageBitmap(photo);
                    LayoutParams lay1=new LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT);
                    lay1.setMargins(0,0,30,0);
                    meterImg[count].setLayoutParams(lay1);
                    //meterImg[count].setPadding(2, 2, 2, 2);
                    meterImg[count].setClickable(true);
                    meterImg[count].setId(id);
                    Row.addView( meterImg[count]);
                    meterImg[count].setOnClickListener(ImgClick);
                    count++;

                    TextView tv = new TextView(this);
                    LayoutParams lay=new LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT);
                     lay.setMargins(30,0,0,0);
                    tv.setLayoutParams(lay);
                    tv.setGravity(Gravity.CENTER);
                    tv.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                    tv.setTextSize(18);

                    tv.setText(bf.toString());
                    tv.setPadding(30, 5, 5, 5);
                    Row.addView(tv);
                    tableLayout.addView(Row);
                }
            }
        } catch (Exception e) {
            exception(e.toString());
        }

    }

    private void goBack() {
        startActivity(new Intent(this, inputForm.class).putExtra("Uname",user).putExtra("PlateNo",plate));
    }

    View.OnClickListener ImgClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            gotoAct(view.getId());
        }
    };

    public void gotoAct(int id ) {
        startActivity(new Intent(this, ExpandedImg.class).putExtra("id", id).putExtra("user",user).putExtra("plate",plate));
    }

    private void exception(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

}

