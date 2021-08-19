package com.example.ksa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;


public class MainActivity extends AppCompatActivity
{

    private static final int REQUEST_CODE_AUDIO_AND_WRITE_EXTERNAL_STORAGE = 1;
    MediaRecorder recorder=null;
    String filename;
    MediaPlayer player;
    int position = 0;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.detect_button).setOnClickListener(StartClick); // 스타트 리스너
        findViewById(R.id.stop_button).setOnClickListener(StopClick);
      // findViewById(R.id.test_button).setOnClickListener(testClickListener);
        //ImageView ambulanceimg=(ImageView)findViewById(R.id.ambulance_png); 엠뷸런스이미지
        //ambulanceimg.setVisibility(View.INVISIBLE);

        //권환요청
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO) && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_AUDIO_AND_WRITE_EXTERNAL_STORAGE);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
                // 유저가 거부하면서 다시 묻지 않기를 클릭, 권한이 없다고 유저에게 직접 알림.
            }
        } else {
            //startUsingSpeechSDK();
        }

        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard,"recorded.wav");
        filename= file.getAbsolutePath();
        Log.d("activity12","저장할 파일명:"+filename);
    }

    Button.OnClickListener StartClick = new View.OnClickListener()
    {
        public void onClick(View v)  //start버튼 눌렀을때
        {
            ImageView LoadImg = (ImageView) findViewById(R.id.load_img); //iv.setImageResource(R.drawable.img);
            Glide.with(LoadImg).load(R.drawable.loading).into(LoadImg); //움직이는 로딩img

           //Button Btn = (Button) findViewById(R.id.detect_button);
           //Btn.setEnabled(false); //감지버튼 비활성화
            LoadImg.setVisibility(v.VISIBLE);

           settingAudio();
           startRecording();
           Toast.makeText(getApplicationContext(), "감지가 시작되었습니다.", Toast.LENGTH_SHORT).show();
        }
    };

    View.OnClickListener StopClick=new View.OnClickListener()
        {
            public void onClick(View v) //stop버튼 눌렀을때
            {
                ImageView LoadImg = (ImageView)findViewById(R.id.load_img);
                Glide.with(LoadImg).load(R.drawable.loading).into(LoadImg);
                LoadImg.setVisibility(v.INVISIBLE);

                stopRecording();
            //    Toast.makeText(getApplicationContext(), "감지가 종료되었습니다.", Toast.LENGTH_SHORT).show();



                // TextView DetectText = (TextView)findViewById(R.id.detect_text);
                // Button testbtn = (Button)findViewById(R.id.detect_button);
                // DetectText.setVisibility(v.INVISIBLE);

            }
        };

   private void settingAudio()// 셋팅
   {
       /* 그대로 저장하면 용량이 크다.
        * 프레임 : 한 순간의 음성이 들어오면, 음성을 바이트 단위로 전부 저장하는 것
        * 초당 15프레임 이라면 보통 8K(8000바이트) 정도가 한순간에 저장됨
        * 따라서 용량이 크므로, 압축할 필요가 있 */
       if(recorder != null)
       {
           recorder.stop();
           recorder.release();
           recorder = null;
       }
           recorder = new MediaRecorder();
           recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
           recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
           recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
           recorder.setOutputFile(filename);

           try {
               recorder.prepare();
           } catch (IOException e) {
               e.printStackTrace();
           }

   }
   private void startRecording()
   {
       recorder.start();
       Log.i("Recording", "녹음 시작됨");
   }
    private void stopRecording()
    {
        if (recorder != null) 
        {
            recorder.stop();
            recorder.release();
            recorder = null;
            Log.i("Recording", "녹음 중지됨");
        }
    }

}