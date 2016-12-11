package org.rikushiru.play.yourdead;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Main2 extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);




    Button btn1 = (Button) findViewById(R.id.main2_btn1);
        btn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                EditText edi1 = (EditText) findViewById(R.id.main2_edit1);
                EditText edi2 = (EditText) findViewById(R.id.main2_edit2);
                EditText edi3 = (EditText) findViewById(R.id.main2_edit3);
                TextView result = (TextView) findViewById(R.id.main2_tex1);
                TextView tex2 = (TextView) findViewById(R.id.main2_tex2);
                TextView tex3 = (TextView) findViewById(R.id.main2_tex3);
                TextView tex4 = (TextView) findViewById(R.id.main2_tex4);

                String all = edi1.getText().toString();
                String tani = edi2.getText().toString();
                String ketu = edi3.getText().toString();

                int alla = Integer.parseInt(all);
                int tania = Integer.parseInt(tani);
                int ketua =  Integer.parseInt(ketu);

                int resultint = ((((265000)/alla)*tania)/(15))*ketua;

                if(0<=ketua && ketua <=15) {
                    result.setText(Integer.toString(resultint));
                    tex3.setText("円の損失です。");
                }
                if(ketua == 0){
                    result.setText(Integer.toString(resultint));
                    tex3.setText("円の損失です。");
                    tex2.setText("あなたは優等生です。");
                }
                else if(ketua >= 6 && ketua <=14){
                    tex2.setText("親不孝者。");
                    tex4.setText("この金額を親にどぶに捨てさせたのです。自覚なさい。");
                }
                else if(ketua == 15){
                    tex2.setText("死になさい。");
                    tex4.setText("この金額を親にどぶに捨てさせたのです。自覚なさい。");
                }
                else if(3<=ketua && ketua<= 5){
                    tex2.setText("そろそろ単位がピンチです。");
                    tex4.setText("この金額を親にどぶに捨てさせたのです。自覚なさい。");
                }
                else if(ketua == 1 || ketua == 2){
                    tex2.setText("これ以上欠席はやめましょう。");
                    tex4.setText("この金額を親にどぶに捨てさせたのです。自覚なさい。");
                }
                else {
                    result.setText(" ");
                    tex3.setText("  ");
                    tex2.setText("欠席の入力が不適切です。");
                    tex4.setText(" ");
                }
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
