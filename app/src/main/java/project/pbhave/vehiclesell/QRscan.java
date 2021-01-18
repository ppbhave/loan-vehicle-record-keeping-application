package project.pbhave.vehiclesell;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QRscan extends AppCompatActivity {

    private IntentIntegrator qrScan;
    Button QRscan,Read;
    private TextView textViewName,textViewAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscan);
        qrScan = new IntentIntegrator(this);
        QRscan = (Button)findViewById(R.id.ScanQR);
        QRscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrScan.initiateScan();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else if(result.getContents().contains("><")){
                //if qr contains data
                String arr1[]=result.getContents().split("><");
                Intent i=new Intent(this,inputForm.class).putExtra("Uname",arr1[0]).putExtra("PlateNo",arr1[1]);
                startActivity(i);
            }else{ Toast.makeText(this, "Invalid QR code Format.", Toast.LENGTH_LONG).show();}
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
