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
//mBytes���ǲɼ��������� �����Ǹ���СΪ1024�����飬��������ݶ���byts���ͣ����Դ�СΪ-127��128
        if (mBytes == null) {
            return;
        }
        if (mPoints == null || mPoints.length < mBytes.length * 4) {
//mPoints��Ҫ�����洢Ҫ��ֱ�ߵ�4�����꣨ÿ�����������꣬����һ��ֱ����Ҫ�����㣬Ҳ����4�����꣩
            mPoints = new float[mBytes.length * 4];
        }
		//mRect.set(0, 0, getWidth(), getHeight());
//xOrdinate��x����̶ܿȣ���Ϊһ�λᴫ�����1024�����ݣ�ÿ��������Ҫ����һ��ֱ�ߣ�����x�����Ƿֳ�1023�Ρ���Ҫ�Ǿ���̫���ˣ�Ҳ��������һ������2�����Լ������ˡ�
        int xOrdinate = (mBytes.length - 1)/2;
 
//���µ�forѭ��������mBytes[i] mBytes[i+1] ����������ȥ����4������ֵ���Ӷ��ڿ̻����������꣬��������
        for (int i = 0; i <xOrdinate ; i++) {

          //��i�������ܺ����ϵ����꣬
          //  mPoints[i * 4] = mRect.width() * i / xOrdinate;
 
//��i��������������ϵ����ꡣ���ڻ��������������1/2Ϊ��׼�ߣ�mRect.height() / 2�������еĵ�������Դ���Ϊ������ǡ�
//((byte) (mBytes[i] + 128))���һֱû����⣬���+128��Ϊ�˽�����ȫ������Ϊ����������ôǿתΪbyte���ֱ��-127��128��ô����Ҫ��˭֪��ԭ��������Ը�����.....
//(mRect.height() / 2) / 128���ǽ�����֮һ���ܳ��Ȼ����128���̶ȣ���Ϊ���ǵ�������byte���ͣ����Կ̻���128���̶�����
     //       mPoints[i * 4 + 1] = mRect.height() / 2+ ((byte) (mBytes[i] + 128)) * (mRect.height() / 2) / 128;

 
//���¾��ǿ̻���i+1�������ˣ�ԭ��Ϳ̻���i��һ��
          //  mPoints[i * 4 + 2] = mRect.width() * (i + 1) / xOrdinate;
           // mPoints[i * 4 + 3] = mRect.height() / 2 + ((byte) (mBytes[i + 1] + 128)) * (mRect.height() / 2) / 128;
        }
 
//ѭ�������󣬾͵õ�����һ�β��ε����п̻����ֱ꣬�ӻ��ڻ����Ͼͺ���
        //canvas.drawLines(mPoints, mForePaint);
    }

}
