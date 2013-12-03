Overview
========

This app was created as my first "glassware" app.  It's a simple "hello world" app if you will but
instead of creating a card with Hello World it hooks into the voice actions and plays a sound.  In
This case you can speak:

OK Glass, do a shit

and it will fart in your ear.

There isn't much use to this application unless you are immature like myself or you want to play
a prank on your friends.  So as...hand them your Google glass and as they are looking at the options
for "OK Glass" yell, "Do a shit".  If you are trying to be a #glasshole then this is the app for you!

I'm going to treat this code as a walk-through tutorial on how to create a basic voice controlled app.

Step 1 - Create an App
======================
Create an App in Eclipse the normal way (File->New->Project->Android Application Project) You will pick need to pick "Compile with: Glass Development Kit Sneak Peak".  Install that via the SDK Manager if it isn't already installed.  You should use SDK 15 for 4.0.3 as well because that is what Glass is designed for.

You can use the wizard to create a blank activity for this project.  Even a blank activity will have an
Options menu, which you do not need.  Feel free to delete any reference to Options and and what not since
we will not be displaying a card to interact with.

Step 2 - Setup Voice Triggers
=============================
Now open up AndroidManifest.xml.  Remove any reference to Launchers in the Activity tab.  Instead your
intent filter should look like this:

```xml
<intent-filter>
                <action android:name="com.google.android.glass.action.VOICE_TRIGGER" />
</intent-filter>
<meta-data
                android:name="com.google.android.glass.VoiceTrigger"
                android:resource="@xml/voice_trigger_start" />
```
This tells the system to register a VOICE trigger that will launch her activity via the Activity android:name field.  In our example this is android:name="com.gfxhax.glass.doashit.ShitActivity".  We supply some
meta-data so it knows what words to use as the command.  These will be stored in a XML folder in a file
called voice_trigger_start.xml (this can be any name).

So Right Click on the "res" folder and add a folder called "xml".  Then add a file called "voice.trigger_start.xml.  Add the following line:

```xml
<trigger keyword="@string/do_a_shit" />
```

Now you could just add the string but this is the "proper" way to do it.  Now go to res/values/strings.xml
and add:

```xml
<string name="do_a_shit">do a shit</string>
```
That's it for voice registration!

Step 3 - Add the core code
==========================
Now open up your core activity under src/ .. This is typically called MainActivity.  In our example it
is called ShitActivity.java.  Remove the View code from the onCreate() method as well as any other methods
automatically created by the wizard.  We only need onCreate for this demo.

Modify your onCreate to look like this:

```java
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
```

This is the entire app (minus one function, which we will get to in a moment).  All we are doing here
is initializing a SoundPool and loading our fart.ogg file located in raw (also needs to be created and
a file needs to be copied into this folder.  note: right click refresh in Eclipse after it is copied)
Then you need to setup a setOnLoadCompleteListener or else the sound will not finish loading before the app
wants to play it.  Create an anonymous function that has one method (onLoadComplete) and call the
private function playSound().  Finally call finish() which closes the activity (and goes back to displaying
the clock in glass).

Now you may have noticed there are a few constants that are not defined.  You can define those right 
after you have declared your public class:

```java
    private static final int SOUND_PRIORITY = 1;
    private static final int MAX_STREAMS = 1;
    private SoundPool mSoundPool;
    private int mFartSoundId;
```

Also you will need the private function to play the sound:

```java
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
```
This function was stolen from the stopwatch GDK demo :)

Enjoy!
======
That's it!  Compile and push to your Glass device (ensure it is in debug mode)  I've included a
DoAShit.apk file in /bin/ section of this repo if you are not so inclined as to compile it.  You will notice that
it will fart whenever you push a new copy of the app to the device.  That is because the main activity
is loading.  This could be fixed but I wanted to keep this demo simple

(PS: If anyone wants to do a logo for this app I will gladly accept it)


