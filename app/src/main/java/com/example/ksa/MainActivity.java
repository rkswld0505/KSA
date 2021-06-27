package com.example.ksa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity
{
    int i=0;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.detect_button).setOnClickListener(mClickListener);
      // findViewById(R.id.test_button).setOnClickListener(testClickListener);
        ImageView ambulanceimg=(ImageView)findViewById(R.id.ambulance_png);
        ambulanceimg.setVisibility(View.INVISIBLE);


    }
    Button.OnClickListener mClickListener = new View.OnClickListener()
    {
        public void onClick(View v)
        {


            if(i==0) // 감지중 test
            {
                ImageView LoadImg = (ImageView)findViewById(R.id.load_img); //iv.setImageResource(R.drawable.img);
                Glide.with(LoadImg).load(R.drawable.loading).into(LoadImg);
                Toast.makeText(getApplicationContext(), "감지가 시작되었습니다.", Toast.LENGTH_SHORT).show();
                Button  Btn = (Button) findViewById(R.id.detect_button); //감지버튼 비활성화
                Btn.setEnabled(false); //비활성
                LoadImg.setVisibility(v.VISIBLE);

                i=1;
            }

            else //감지멈춤
            {

                ImageView LoadImg = (ImageView)findViewById(R.id.load_img);
                Glide.with(LoadImg).load(R.drawable.loading).into(LoadImg);
                LoadImg.setVisibility(v.INVISIBLE);

               // TextView DetectText = (TextView)findViewById(R.id.detect_text);
               // Button testbtn = (Button)findViewById(R.id.detect_button);
               // DetectText.setVisibility(v.INVISIBLE);
                i=0;
            }

        }
    };
   /*
    Button.OnClickListener testClickListener = new View.OnClickListener()

    {
      public void onClick(View v)
      {
          ImageView ambulanceimg=(ImageView)findViewById(R.id.ambulance_png);
          ambulanceimg.setVisibility(View.VISIBLE);
      }
    };
    */
}