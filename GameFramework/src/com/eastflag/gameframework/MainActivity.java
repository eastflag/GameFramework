package com.eastflag.gameframework;

import java.io.IOException;

import com.eastflag.gameframework.scene.IScene;
import com.eastflag.gameframework.scene.MainScene;
import com.eastflag.gameframework.scene.StartShootScene;

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
		
		createBGM();
	}
	
    @Override
    protected void onPause() {
        super.onPause();
        
        pauseBGM();
    }

    @Override
    protected void onResume() {
        super.onResume();
        
        playBGM();
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	
    	stopBGM();
    }

	public void createBGM(){
    	try{
    		mMediaPlayer = new MediaPlayer();
        	AssetManager am = getAssets();
    		AssetFileDescriptor descriptor = am.openFd("backmusic.mp3");
    		mMediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
    		mMediaPlayer.prepare();
    		//mMediaPlayer.start();
    		mMediaPlayer.setVolume(1f, 1f);
    	}
    	catch (IllegalArgumentException e) {Log.e("MediaPlayer Error", e.getMessage());}
		catch (IllegalStateException e) {Log.e("MediaPlayer Error", e.getMessage());}
		catch (IOException e) {}
    }
	
	public void toggleBGM() {
		if(AppDirector.getInstance().ismIsBGM()) {
			playBGM();
		} else {
			pauseBGM();
		}
	}
	
	public void playBGM() {
		try{
    		mMediaPlayer.start();
    	}
		catch (IllegalStateException e) {Log.e("MediaPlayer Error", e.getMessage());}
	}
	
	public void pauseBGM() {
		try{
    		mMediaPlayer.pause();;
    	}
		catch (IllegalStateException e) {Log.e("MediaPlayer Error", e.getMessage());}
	}
    
    public void stopBGM(){	
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
			if (mIScene instanceof MainScene){
				finishApp();
			}
			if (mIScene instanceof StartShootScene){
				retryGame();
			}

    	}
		
		return false;
	}

	public void finishApp(){
  		AlertDialog.Builder builder = new AlertDialog.Builder(this);
  		builder.setMessage("게임을 종료하시겠습니까?")
	  		.setPositiveButton("YES", new DialogInterface.OnClickListener() {
	  			public void onClick(DialogInterface dialog, int which) {
	  				System.exit(-1);
	  				finish();
	  				return;
	  			}
	  		})
	  		.setNegativeButton("NO", new DialogInterface.OnClickListener() {
	  			public void onClick(DialogInterface dialog, int which) {
	  				return;
	  			}
	  		})
	  		.show();;
  	}
	
	public void retryGame(){
		Log.e("ldk", "retryGame");
  		AlertDialog.Builder builder = new AlertDialog.Builder(this);
  		builder.setMessage("게임을 다시 하시겠습니까?")
  			.setPositiveButton("YES", new DialogInterface.OnClickListener() {
	  			public void onClick(DialogInterface dialog, int which) {
	  				AppDirector.getInstance().getmGameView().changeScene(new StartShootScene());
	  			}
	  		})
	  		.setNegativeButton("NO", new DialogInterface.OnClickListener() {
	  			public void onClick(DialogInterface dialog, int which) {
	  				AppDirector.getInstance().getmGameView().changeScene(new MainScene());
	  			}
	  		})
	  		.show();;
  	}
}
