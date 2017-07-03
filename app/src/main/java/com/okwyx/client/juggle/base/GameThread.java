package com.okwyx.client.juggle.base;


import com.okwyx.client.juggle.Constans;
import com.okwyx.client.juggle.logic.BaseLogic;

public class GameThread extends Thread {

	private BaseLogic mLogic;
	private boolean isRunning = true;
	private long timeSpan = 0;
	
	public GameThread(BaseLogic logic) {
		this.mLogic = logic;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		while (isRunning) {
			long startTime = System.currentTimeMillis();
			mLogic.fixedUpdate();
			timeSpan = System.currentTimeMillis() - startTime;
			sleep();
		}
	}

	public void setRunning(boolean flag) {
		isRunning = flag;
	}
	
	private void sleep() {
		try {
			long sleepTime = Constans.FixedTime - timeSpan;
			if (sleepTime > 0) {
				Thread.sleep(sleepTime);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
