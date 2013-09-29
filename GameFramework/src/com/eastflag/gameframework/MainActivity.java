package com.eastflag.gameframework;

import java.io.IOException;

import com.eastflag.gameframework.scene.IScene;
import com.eastflag.gameframework.scene.MainScene;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

//AppDirector : 카메라 감독, 싱글턴패턴
//MainActivity : 카메라, 영사기
//GameView : 필름, 장면 관리
//RenderingThread : 애니메이션 작가(일꾼), 필름에 그림을 그린다.
public class MainActivity extends Activity {
	private MediaPlayer mMediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		
		//타이틀바 없애기
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//상태바 없애기
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		//초기화
		AppDirector.getInstance().setmMainActivity(this);
		AppDirector.getInstance().initialize();
		
		//게임뷰 세팅
		setContentView(new GameView(this)); //카메라에 필름 끼우기
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void PlayBG(){
    	try{
    		mMediaPlayer = new MediaPlayer();
        	AssetManager am = getAssets();
    		AssetFileDescriptor descriptor = am.openFd("backmusic.mp3");
    		mMediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
    		mMediaPlayer.prepare();
    		mMediaPlayer.start();
    		mMediaPlayer.setVolume(1f, 1f);
    	}
    	catch (IllegalArgumentException e) {Log.e("MediaPlayer Error", e.getMessage());}
		catch (IllegalStateException e) {Log.e("MediaPlayer Error", e.getMessage());}
		catch (IOException e) {}
    }
    
    public void StopBG(){	
    	if(mMediaPlayer != null)	{
    		try{
        		mMediaPlayer.release();
        		mMediaPlayer = null;
    		}catch (IllegalArgumentException e) {}
    		catch (IllegalStateException e) {}
    	}
    }
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
    	
    	if(keyCode == KeyEvent.KEYCODE_BACK){
    		IScene mIScene = AppDirector.getInstance().getmGameView().mIScene;
//			if(mIScene instanceof MainScene){
//				finishApp();
//			}
    		finishApp();
    	}
		
		return false;
	}

	public void finishApp(){
  		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
  		alertDialog.setMessage("게임을 종료하시겠습니까?");
  		alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener()
  		{
  			public void onClick(DialogInterface dialog, int which)
  			{
  				System.exit(-1);
  				finish();
  				return;
  			}
  		});
  		alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener()
  		{
  			public void onClick(DialogInterface dialog, int which)
  			{
  				return;
  			}
  		});

  		alertDialog.show();;
  	}
}
