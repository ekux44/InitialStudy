package edu.utulsa.ibcb.moodstudy;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class GameResultsActivity extends Activity implements OnClickListener {

	private boolean won;
	private int prizeNumber;
	private int luckyFeeling;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(this);

		// Load layout from final_survey.xml
		won = getIntent().getExtras().getBoolean("won");
		prizeNumber = getIntent().getExtras().getInt("prize");
		luckyFeeling = getIntent().getExtras().getInt("luckyFeeling");
		// Log.i("pa", prizeNumber + " " + won);

		if (won) {
			setContentView(R.layout.win);

			// look up prize message
			String prize = "";
			switch (prizeNumber) {
			case 1:
				prize = getString(R.string.prize1);
				break;
			case 2:
				prize = getString(R.string.prize2);
				break;
			case 3:
				prize = getString(R.string.prize3);
				break;
			case 4:
				prize = getString(R.string.prize4);
				break;
			case 5:
				prize = getString(R.string.prize5);
				break;
			case 6:
				prize = getString(R.string.prize6);
			}

			// set prize message
			TextView message = ((TextView) findViewById(R.id.resultsTextView));
			message.setText(message.getText() + " " + prize + "!");
		} else {
			setContentView(R.layout.lose);
		}

		Button replayButton = (Button) findViewById(R.id.replayButton);
		replayButton.setOnClickListener(this);

		Button exitButton = (Button) findViewById(R.id.exitButton);
		exitButton.setOnClickListener(this);

		// disable exit button unless on play number 36
		String username = settings.getString("username", "");
		if (settings.getInt("playNumberFor" + username, Integer.MAX_VALUE) >= 36) {
			// disable replay button
			replayButton.setVisibility(Button.INVISIBLE);
			replayButton.setClickable(false);
		} else {
			// disable exit button
			exitButton.setVisibility(Button.INVISIBLE);
			exitButton.setClickable(false);

			// increment play number
			Editor edit = settings.edit();
			edit.putInt("playNumberFor" + username, settings.getInt(
					"playNumberFor" + username, Integer.MAX_VALUE) + 1);
			edit.commit();
		}

		// initialize media player
		MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.payout);
		try {
			mediaPlayer.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (won)
			mediaPlayer.start();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.exitButton:
			onBackPressed();
			break;
		case R.id.replayButton:
			Intent i = new Intent(this, GamePromptActivity.class);
			i.putExtra("luckyFeeling", luckyFeeling);
			startActivity(i);
		}
	}

	@Override
	public void onBackPressed() {
		startActivity(new Intent(this, FinalSurveyActivity.class));
	}

}
