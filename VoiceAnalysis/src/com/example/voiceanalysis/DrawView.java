package com.example.voiceanalysis;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public class DrawView extends View {

	private Byte[] mBytes;
	private float[] mPoints;
	public DrawView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//mBytes就是采集来的数据 这里是个大小为1024的数组，里面的数据都是byts类型，所以大小为-127到128
        if (mBytes == null) {
            return;
        }
        if (mPoints == null || mPoints.length < mBytes.length * 4) {
//mPoints主要用来存储要画直线的4个坐标（每个点两个坐标，所以一条直线需要两个点，也就是4个坐标）
            mPoints = new float[mBytes.length * 4];
        }
		//mRect.set(0, 0, getWidth(), getHeight());
//xOrdinate是x轴的总刻度，因为一次会传输过来1024个数据，每两个数据要画成一条直线，所以x轴我们分成1023段。你要是觉的太多了，也可以像我一样除以2，看自己需求了。
        int xOrdinate = (mBytes.length - 1)/2;
 
//以下的for循环将利用mBytes[i] mBytes[i+1] 这两个数据去生成4个坐标值，从而在刻画成两个坐标，来画线条
        for (int i = 0; i <xOrdinate ; i++) {

          //第i个点在总横轴上的坐标，
          //  mPoints[i * 4] = mRect.width() * i / xOrdinate;
 
//第i个点的在总纵轴上的坐标。他在画线上以总纵轴的1/2为基准线（mRect.height() / 2），所有的点或正或负以此线为基础标记。
//((byte) (mBytes[i] + 128))这个一直没有理解，如果+128是为了将数据全部换算为正整数，那么强转为byte后不又变回-127到128了么？？要是谁知道原因可以留言告诉我.....
//(mRect.height() / 2) / 128就是将二分之一的总长度换算成128个刻度，因为我们的数据是byte类型，所以刻画成128个刻度正好
     //       mPoints[i * 4 + 1] = mRect.height() / 2+ ((byte) (mBytes[i] + 128)) * (mRect.height() / 2) / 128;

 
//以下就是刻画第i+1个数据了，原理和刻画第i个一样
          //  mPoints[i * 4 + 2] = mRect.width() * (i + 1) / xOrdinate;
           // mPoints[i * 4 + 3] = mRect.height() / 2 + ((byte) (mBytes[i + 1] + 128)) * (mRect.height() / 2) / 128;
        }
 
//循环结束后，就得到了这一次波形的所有刻画坐标，直接画在画布上就好了
        //canvas.drawLines(mPoints, mForePaint);
    }

}
