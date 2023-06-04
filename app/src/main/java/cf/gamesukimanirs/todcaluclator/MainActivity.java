package cf.gamesukimanirs.todcaluclator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final double onenmtoft = 6076.1154855643;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button exec = findViewById(R.id.exec);

        exec.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                try {
                    EditText presentft = findViewById(R.id.presentft);
                    EditText extimatedft = findViewById(R.id.extimatedft);
                    EditText gspeed = findViewById(R.id.gspeed);
                    Switch vsdegtoggle = findViewById(R.id.degftmtog);
                    int presentftnum = Integer.parseInt(presentft.getText().toString());
                    int estimatedftnum = Integer.parseInt(extimatedft.getText().toString());
                    int gspeednum = Integer.parseInt(gspeed.getText().toString());
                    double vsnum = 0;

                    double todnm = 0;

                    int moveft = presentftnum - estimatedftnum;

                    if (vsdegtoggle.isChecked()) {
                        //vs
                        EditText vs = findViewById(R.id.vs);
                        vsnum = Integer.parseInt(vs.getText().toString());

                        double hypotenuse = (double)gspeednum / 60 *onenmtoft;
                        //hypotenuse^2 = bottom^2 + vsnum^2
                        double bottom = Math.sqrt((double)hypotenuse*hypotenuse - vsnum*vsnum);
                        //cos VSNUM = (hypotenuse + bottom - vsnum)/2(hypotenuse*bottom)
                        double cosangle = (double)(hypotenuse*hypotenuse + bottom*bottom - vsnum*vsnum)
                                                            /(2*hypotenuse*bottom);
                        double angle = (double) Math.round((double) Math.toDegrees(Math.acos(cosangle))*-10)/10;
                        TextView resultvs = findViewById(R.id.resultvs);
                        resultvs.setText("angle:"+angle+" degrees");
                        /*new AlertDialog.Builder(MainActivity.this)
                            .setTitle("info")
                            .setMessage("hypo:"+hypotenuse+",bottom:"+bottom+",cosangle:"+cosangle+"" +
                                    ",angle:"+angle+",vsnum"+vsnum+",")
                            .setPositiveButton("OK",null)
                            .create()
                            .show();
                        */
                    } else {
                        //deg
                        EditText deg = findViewById(R.id.degrees);
                        int degreenum = Integer.parseInt(deg.getText().toString());

                        double sinacute = Math.sin(Math.toRadians(degreenum));
                        double hypotenuse = (double) gspeednum / 60 *onenmtoft;
                        vsnum = (double) hypotenuse*sinacute;
                        //VS = hypotenuse/sin(90) * sin(acute)
                        TextView resultvs = findViewById(R.id.resultvs);
                        resultvs.setText("V/S:"+Math.round(-vsnum)+"ft/min");
                    }

                    double needmin = (double) moveft / vsnum;
                    todnm = (double) Math.round((double) (gspeednum/60)*needmin*10)/10;

                    /*new AlertDialog.Builder(MainActivity.this)
                            .setTitle("info")
                            .setMessage("vsnum:"+vsnum+",needmin:"+needmin+",moveft:"+moveft+"" +
                                    ",gspeednum:"+gspeednum+",")
                            .setPositiveButton("OK",null)
                            .create()
                            .show();*/

                    TextView resultnm = findViewById(R.id.resultnm);
                    resultnm.setText("T/D: "+todnm+"nm");

                }catch (NumberFormatException e){
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Error!")
                            .setMessage("数字を入力してください")
                            .setPositiveButton("OK",null)
                            .create()
                            .show();
                    TextView resultnm = findViewById(R.id.resultnm);
                    resultnm.setText("T/D:Error!");
                    TextView resultvs = findViewById(R.id.resultvs);
                    resultvs.setText("V/S:-");
                }
            }
        });
    }
}