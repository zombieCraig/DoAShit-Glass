package com.gfxhax.glass.doashit;

import android.os.Bundle;
import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;

public class ShitActivity extends Activity {
    private static final int SOUND_PRIORITY = 1;
    private static final int MAX_STREAMS = 1;
	private SoundPool mSoundPool;
	private int mFartSoundId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSoundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
		mFartSoundId = mSoundPool.load(this, R.raw.fart, SOUND_PRIORITY);
		mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
			@Override
		    public void onLoadComplete(SoundPool soundPool, int mySoundId, int status) {
				playSound(mFartSoundId);
			}
		});
		finish();
	}


    /**
     * Plays the provided {@code soundId}.
     */
    private void playSound(int soundId) {
        mSoundPool.play(soundId,
                        1 /* leftVolume */,
                        1 /* rightVolume */,
                        SOUND_PRIORITY,
                        0 /* loop */,
                        1 /* rate */);
    }

}
