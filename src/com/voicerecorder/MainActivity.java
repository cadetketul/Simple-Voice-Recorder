package com.voicerecorder;

import java.io.IOException;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	private final String PATH = "/sdcard/recording.wav";
	
	MediaRecorder recorder = null;
	Button recBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		recBtn = (Button) findViewById(R.id.button1);
		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
		recorder.setOutputFile(PATH);
		recBtn.setOnClickListener(recordEvent);
	}

	private OnClickListener recordEvent = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			recBtn = (Button) findViewById(R.id.button1);
			try {
				recorder.prepare();
				recorder.start();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			recBtn.setBackgroundResource(android.R.drawable.ic_media_pause);
			recBtn.setOnClickListener(stopEvent);
		}
	};
	
	private OnClickListener stopEvent = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			recorder.stop();
			recorder.release();
			Toast.makeText(getApplicationContext(), "Recording saved to: "+PATH,
					Toast.LENGTH_LONG).show();
			recBtn.setBackgroundResource(android.R.drawable.btn_star);
			recBtn.setOnClickListener(recordEvent);
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}