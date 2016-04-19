package com.exile.roundmenuview;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.ImageView;



public class RoundMenuView extends ImageView implements OnGestureListener {
    private static final int childMenuSize = 8;
    /**
     * 单个条目的度数
     */
    private static final float childAngle = 360f / childMenuSize;
    private float offsetAngle = 0;
    private Paint paint;
    private GestureDetector gestureDetector;
    private int selectId = -1;
    /**
     * 直径
     */
    private int diameter;
    /**
     * 半径
     */
    private int radius;
    private Paint paintBg;
    private Bitmap start;
    private Bitmap startSelect;
    //星星距外圆的距离
    private int panddingBg;
    //星星背景的宽度
    private int panddingHight;
    //内容距星星的距离
    private int panddingContent;
    //非选中区域的画笔
    private Paint contentPaint;

    public RoundMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        gestureDetector = new GestureDetector(getContext(), this);


        //加载星星图片
        start = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_star_gray);
        startSelect = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_star);
        //设置星星背景的宽度
        panddingHight = DensityUtil.dip2px(getContext(), 38);
        panddingBg = (panddingHight - start.getHeight()) / 2;
        panddingContent = DensityUtil.dip2px(getContext(),10);
        //内容的画笔
        contentPaint = new Paint();
        contentPaint.setColor(getResources().getColor(R.color.bgColor_overlay));
        contentPaint.setAntiAlias(true);
        contentPaint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        contentPaint.setStyle(Style.STROKE);
        contentPaint.setAlpha(9);
        contentPaint.setStrokeWidth(DensityUtil.dip2px(getContext(), 2));
        //星星的背景
        paintBg = new Paint();
        paintBg.setAntiAlias(true);
        paintBg.setColor(getResources().getColor(R.color.bgColor_overlay));
        paintBg.setStyle(Style.STROKE);
        paintBg.setStrokeWidth(panddingHight);
    }

    /**
     * 绘制星级
     */
    private void drawStar(Canvas canvas) {
        /**
         * 绘制每个块块，每个块块上的文本
         */

        for (int i = 1; i < 8; i++) {

            drawRect(canvas, i, selectId);
        }


    }

    /**
     * 设置每个星星
     *
     * @param canvas
     * @param selectId
     * @param i
     */
    private void drawRect(Canvas canvas, int i, int selectId) {

        RectF mRectf = null;

        if (i == 4) {
            mRectf = new RectF(radius - start.getWidth() / 2,
                    panddingBg,
                    radius + start.getWidth() / 2,
                    start.getHeight() + panddingBg);
        } else if (i < 4) {
            float temp = 12.8f + 25.7f * (i - 1);
            float x = (float) (radius - Math.cos(temp * Math.PI / 180) * radius + panddingBg / 2);
            float y = (float) (radius - Math.sin(temp * Math.PI / 180) * radius + panddingBg / 2);
            mRectf = new RectF(x, y, x + start.getWidth(), y + start.getHeight());
        } else {
            float temp = 180 - (12.8f + 25.7f * (i - 1));
            float x = (float) (radius + Math.cos(temp * Math.PI / 180) * radius - panddingBg / 2);
            float y = (float) (radius - Math.sin(temp * Math.PI / 180) * radius + panddingBg / 2);
            mRectf = new RectF(x - start.getWidth(), y, x, y + start.getHeight());
        }
        canvas.drawBitmap(selectId < i ? start : startSelect, null, mRectf, null);
    }

    /**
     * 画扇形
     *
     * @param canvas
     * @param rectF
     */
    private void drawArc(Canvas canvas, RectF rectF) {
        /**
         * 绘制每个块块，每个块块上的文本
         */
        float sweepAngle = (float) (360 / childMenuSize);
        float tmpAngle = offsetAngle;

        for (int i = 0; i < childMenuSize; i++) {


//            if (i == selectId) { //如果是选中就将扇形画成实心的,否则画空心的扇形
//                paint.setColor(Color.BLUE);
//                paint.setStyle(Style.FILL);
//                canvas.drawArc(rectF, i * childAngle + offsetAngle, childAngle,
//                        true, paint);
//            } else {
//                canvas.drawArc(rectF, i * childAngle + offsetAngle, childAngle,
//                        true, contentPaint);
//            }
            canvas.drawArc(rectF, i * childAngle + offsetAngle, childAngle,
                    true, i == selectId ? paint : contentPaint);
            String str = "菜单" + i;
            Path path = new Path();
            path.addArc(rectF, tmpAngle, sweepAngle);
            //计算文字宽高
            Rect rect = new Rect();
            paint.getTextBounds(str, 0, str.length(), rect);
            int strW = rect.width();
            paint.setColor(Color.WHITE);
            float hOffset = (float) ((diameter - 160) * Math.PI / childMenuSize / 2 - strW / 2);// 水平偏移
            float vOffset = radius / childMenuSize;// 垂直偏移
            canvas.drawTextOnPath(str, path, hOffset, vOffset, paint);
            tmpAngle += sweepAngle;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                selectId = whichSector(event.getX() - radius, event.getY() - radius, radius);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        gestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制星星的背景(第一层)
        drawSatrBg(canvas);
        RectF mRectf = new RectF(panddingHight + panddingContent,
                panddingHight + panddingContent,
                diameter - panddingHight - panddingContent,
                diameter - panddingHight - panddingContent);
        paint.setStyle(Style.FILL_AND_STROKE);
//        //外围灰色的圆（第一层）
//        paint.setColor(Color.TRANSPARENT);
//        canvas.drawCircle(radius, radius, radius - 80, paint);
//        外围绿色的圆（第二层）
//        paint.setColor(Color.WHITE);
//        canvas.drawCircle(radius, radius, radius - 55, paint);
        //最里层的圆  （第三层）
        paint.setColor(getResources().getColor(R.color.bgColor_overlay_gray));
        canvas.drawCircle(radius, radius, radius - panddingHight - panddingContent, paint);

        // 画空心扇形
        paint.setColor(Color.BLUE);
        paint.setStyle(Style.STROKE);
        //绘制选中区域和非选中区域
        drawArc(canvas, mRectf);
        //绘制星星
        drawStar(canvas);

        // 画中心外圆
        paint.setColor(Color.WHITE);
        paint.setStyle(Style.FILL);
        canvas.drawCircle(radius, radius, 25, paint);

        // 画三角形
        Path path = new Path();
        path.moveTo(radius - 25, radius);// 此点为多边形的起点
        path.lineTo(radius + 25, radius);
        path.lineTo(radius, radius - 60);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, paint);

        // 画中心内圆
        paint.setColor(getResources().getColor(R.color.default_txt_orange_color));
        canvas.drawCircle(radius, radius, 20, paint);


    }

    /**
     * 绘制星星的背景
     *
     * @param canvas
     */
    private void drawSatrBg(Canvas canvas) {
        RectF mRectf = new RectF(panddingHight / 2, panddingHight / 2, diameter - panddingHight / 2, diameter - panddingHight / 2);

//        canvas.drawArc(mRectf, 180, 180, true, paintBg);
//        paintBg.setColor(Color.WHITE);
        for (int i = 0; i < 7; i++) {
//            canvas.drawCircle(diameter / 2, diameter / 2, diameter / 2 - panddingBg*2, paintBg);
            canvas.drawArc(mRectf, 180 + i * 25.7f, 25f, false, paintBg);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
        // 获取圆形的直径
        diameter = width - getPaddingLeft() - getPaddingRight();
        radius = diameter / 2;
        setMeasuredDimension(width, width);
    }


    /**
     * 计算两个坐标点与圆点之间的夹角
     *
     * @param px1 点1的x坐标
     * @param py1 点1的y坐标
     * @param px2 点2的x坐标
     * @param py2 点2的y坐标
     * @return 夹角度数
     */
    private double calculateScrollAngle(float px1, float py1, float px2,
                                        float py2) {
        double radian1 = Math.atan2(py1, px1);
        double radian2 = Math.atan2(py2, px2);
        double diff = radian2 - radian1;
        return Math.round(diff / Math.PI * 180);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        float tpx = e2.getX() - radius;
        float tpy = e2.getY() - radius;
        float disx = (int) distanceX;
        float disy = (int) distanceY;
        double scrollAngle = calculateScrollAngle(tpx, tpy, tpx + disx, tpy
                + disy);
        offsetAngle -= scrollAngle;
        selectId = whichSector(0, -60, radius);//0,40是中心三角定点相对于圆点的坐标

        invalidate();
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        // TODO Auto-generated method stub
        return false;
    }


    /**
     * 计算点在那个扇形区域
     *
     * @param X
     * @param Y
     * @param R 半径
     * @return
     */
    private int whichSector(double X, double Y, double R) {

        double mod;
        mod = Math.sqrt(X * X + Y * Y); //将点(X,Y)视为复平面上的点，与复数一一对应，现求复数的模。
        double offset_angle;
        double arg;
        arg = Math.round(Math.atan2(Y, X) / Math.PI * 180);//求复数的辐角。
        arg = arg < 0 ? arg + 360 : arg;
        if (offsetAngle % 360 < 0) {
            offset_angle = 360 + offsetAngle % 360;
        } else {
            offset_angle = offsetAngle % 360;
        }
        if (mod > R) { //如果复数的模大于预设的半径，则返回0。
            return -2;
        } else { //根据复数的辐角来判别该点落在那个扇区。
            for (int i = 0; i < childMenuSize; i++) {
                if (isSelect(arg, i, offset_angle) || isSelect(360 + arg, i, offset_angle)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 判读该区域是否被选中
     *
     * @param arg         角度
     * @param i
     * @param offsetAngle 偏移角度
     * @return 是否被选中
     */
    private boolean isSelect(double arg, int i, double offsetAngle) {
        return arg > (i * childAngle + offsetAngle % 360) && arg < ((i + 1) * childAngle + offsetAngle % 360);
    }

}