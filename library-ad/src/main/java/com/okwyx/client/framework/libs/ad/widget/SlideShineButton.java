package com.okwyx.client.framework.libs.ad.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.Button;

public class SlideShineButton extends Button {
	private int refreshTime = 20;
	
	private int[] colors = {0xfafafa,-0x7f050506,0xfafafa};
	
	private Paint paint;
	private Matrix matrix;
	private LinearGradient linearGradient;
	private InvalidateThread invalidateThread;
	private boolean attached;
	private int speed = 0;
	private int marginX = 0;
	private float width = 0;

	public SlideShineButton(Context context) {
		super(context);
		init();
	}

	public SlideShineButton(Context context, AttributeSet attrs,int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public SlideShineButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		this.matrix = new Matrix();
		this.paint = new Paint();
		this.paint.setAntiAlias(true);
	}

	private void initAnimation() {
		this.width = getWidth();
		this.speed = getWidth() / 20;
		float f1 = -this.width / 4.0F;
		Shader.TileMode localTileMode = Shader.TileMode.CLAMP;
		this.linearGradient = new LinearGradient(f1, 0.0F, 0.0F, 0.0F, colors, new float[] { 0.0F, 0.5F, 1.0F },
				localTileMode);
		this.paint.setShader(this.linearGradient);
	}

	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		this.attached = true;
	}

	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		this.attached = false;
		this.linearGradient = null;
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		this.matrix.setTranslate(this.marginX, 0.0F);
		this.matrix.preRotate(40.0F);
		this.linearGradient.setLocalMatrix(this.matrix);
		canvas.drawPaint(this.paint);
		this.marginX += this.speed;
		if (this.marginX > this.width * 6.0F / 5.0F) {
			this.marginX = 0;
			this.refreshTime = 400;
			return;
		}
		this.refreshTime = 20;
	}

	protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3,
			int paramInt4) {
		super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
		if (this.invalidateThread == null) {
			initAnimation();
			this.invalidateThread = new InvalidateThread();
			this.invalidateThread.start();
		}
	}

	private class InvalidateThread extends Thread {
		private InvalidateThread() {
		}

		public void run() {
			try {
				while (attached) {
					sleep(refreshTime);
					SlideShineButton.this.postInvalidate();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
