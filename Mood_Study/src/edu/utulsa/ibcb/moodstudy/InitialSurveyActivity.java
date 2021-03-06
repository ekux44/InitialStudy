package edu.utulsa.ibcb.moodstudy;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;

/**
 * Initial survey of mood before playing the game
 * 
 * @author Eric Kuxhausen
 */
public class InitialSurveyActivity extends Activity implements OnClickListener {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(this);

		// Load layout
		setContentView(R.layout.initial_survey);

		Button playButton = (Button) findViewById(R.id.playButton);
		playButton.setOnClickListener(this);
	}

	public void onClick(View v) {
		int lucky = ((SeekBar) findViewById(R.id.moodSeekBar)).getProgress();

		Intent iNext = new Intent(this, GamePromptActivity.class);
		iNext.putExtra("luckyFeeling", lucky);

		// Store initial visual analog scale result for upload later
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor edit = settings.edit();
		edit.putInt("initialSurveyActivityResult", lucky);
		edit.commit();

		switch (v.getId()) {
		case R.id.playButton:
			startActivity(iNext);
			break;

		}
	}

	@Override
	public void onBackPressed() {
		startActivity(new Intent(this, MainActivity.class));
	}

}