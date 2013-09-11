package com.eastflag.gameframework;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

//AppDirector : 카메라 감독, 싱글턴패턴
//MainActivity : 카메라, 영사기
//GameView : 필름, 장면 관리
//RenderingThread : 애니메이션 작가(일꾼), 필름에 그림을 그린다.
public class MainActivity extends Activity {

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

}
