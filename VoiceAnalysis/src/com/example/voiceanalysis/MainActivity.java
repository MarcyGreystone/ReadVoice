package com.example.voiceanalysis;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.media.audiofx.Visualizer;
import android.media.audiofx.Visualizer.OnDataCaptureListener;

public class MainActivity extends Activity implements OnClickListener{
	private Button btn_startRecorder;
	private Button btn_stopRecorder;
	private Button btn_showVisualizer;
	
	private Visualizer mVisualizer;
	private MediaRecorder mMediaRecorder;
	private MediaPlayer mMediaPlayer;
	private String mediaPath = null;
	
	private int i;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();
		initListener();
		//设置sdcard的路径  
		  
	}
	
	private void initView(){
		btn_startRecorder = (Button) findViewById(R.id.button_startrecord);
		btn_stopRecorder = (Button) findViewById(R.id.button_stoprecord);
		btn_showVisualizer = (Button) findViewById(R.id.button_showvisualizer);
	}
	private void initData(){
		i = 0;
		mediaPath = Environment.getExternalStorageDirectory().getAbsolutePath();  
		Log.i("msg", mediaPath);
		mediaPath += "/audioDemo.3gpp";
		Log.i("msg", mediaPath);
	}
	
	private void initListener(){
		btn_startRecorder.setOnClickListener(this);
		btn_stopRecorder.setOnClickListener(this);
		btn_showVisualizer.setOnClickListener(this);
	}
	
	@SuppressLint("NewApi")
	private OnDataCaptureListener onDataCaptureListener = new OnDataCaptureListener(){
		@Override
		public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {	
			Log.i("msg", "waveform=="+waveform.length);
		}
		@Override
		public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {
			Log.i("msg", "Fftform=="+i);
			i++;
		}
		
	};

	//进行数据展示。
	@SuppressLint("NewApi")
	private void showVisualizer(){
		mMediaPlayer = new MediaPlayer();
		try {
			mMediaPlayer.setDataSource("audioDemo.3gpp");
		} catch (IOException e) {
			Log.i("msg", "IOException");
			e.printStackTrace();
		}
		
		//初始化
		mVisualizer = new Visualizer(mMediaPlayer.getAudioSessionId());
		//设置捕捉长度
		mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
		//设置监听
		mVisualizer.setDataCaptureListener(onDataCaptureListener
				, Visualizer.getMaxCaptureRate()/2, false, true);
		//许可或不许可可视化引擎
		mVisualizer.setEnabled(true);
	}
	
	//开始录音
	private void recordVoice(){
		mMediaRecorder = new MediaRecorder();
		mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);;
		mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		mMediaRecorder.setOutputFile(mediaPath);
		
		try {
			mMediaRecorder.prepare();
			Thread.sleep(1000);
			mMediaRecorder.start();
			Log.i("msg", "开始录音");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//停止录音，释放
	private void stopRecorder(){
		mMediaRecorder.stop();
		mMediaRecorder.release();
		mMediaRecorder = null;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.button_stoprecord:
			stopRecorder();
			break;
		case R.id.button_startrecord:
			recordVoice();
			break;
		case R.id.button_showvisualizer:
			showVisualizer();
			break;
		}
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		mMediaPlayer.release();
		mVisualizer.release();
	}
}
