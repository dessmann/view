package com.dsm.view.gesture;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.dsm.platform.util.BitmapUtil;
import com.dsm.platform.util.ScreenUtils;
import com.dsm.platform.util.log.LogUtil;
import com.dsm.view.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 手势密码
 *
 * @author SJL
 * @date 2016/10/16 21:26
 */
@SuppressWarnings("ALL")
public class GestureView extends View {
    private static final String TAG = "GestureView";

    private final Context mContext;
    private Paint mPaint;//画笔
    private int lineColor;//线条颜色
    private float lineWidth;//线条粗细

    private Bitmap mBitmapNormal;//正常点图片
    private Bitmap mBitmapSelect;//选中点图片

    private int column = 3;//方正单行点数
    private int imgRadius;//图片半径
    private int imgSpace;//图片间距
    private Point[][] points;//方正地图
    private List<Point> selectPoints;//选中点列表
    private Point currentPoint;//手指在手机上的位置
    private Boolean isDrawing;//是否在绘制手势密码

    public GestureView(Context context) {
        this(context, null);
    }

    public GestureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GestureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        //初始化属性
        initAttrs(attrs);
        //初始化工具
        initTools();
        requestLayout();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.GestureView);
        column = typedArray.getInteger(R.styleable.GestureView_column, 3);
        imgRadius = typedArray.getInt(R.styleable.GestureView_imgRadius, 50) * ScreenUtils.getScreenWidth(mContext) / 720;
        imgSpace = typedArray.getInt(R.styleable.GestureView_imgSpace, 50) * ScreenUtils.getScreenWidth(mContext) / 720;
        lineColor = typedArray.getColor(R.styleable.GestureView_gestureLineColor, Color.BLACK);
        lineWidth = typedArray.getFloat(R.styleable.GestureView_gestureLineWidth, 4);
        int imgNormal = typedArray.getResourceId(R.styleable.GestureView_imgNormal, 0);
        int imgSelect = typedArray.getResourceId(R.styleable.GestureView_imgSelect, 0);
        typedArray.recycle();
    }

    private void initTools() {
        //初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(lineColor);
        mPaint.setStrokeWidth(lineWidth);

        //初始化点
        points = new Point[column][column];

        //初始化点图片
        mBitmapNormal = BitmapUtil.decodeSampledBitmapFromResource(getResources(), R.mipmap.gesture_normal, imgRadius * 2, imgRadius * 2);
        mBitmapSelect = BitmapUtil.decodeSampledBitmapFromResource(getResources(), R.mipmap.gesture_select, imgRadius * 2, imgRadius * 2);
//        mBitmapNormal = zoomImg(BitmapFactory.decodeResource(getResources(), R.mipmap.gesture_normal), imgRadius * 2, imgRadius * 2);
//        mBitmapSelect = zoomImg(BitmapFactory.decodeResource(getResources(), R.mipmap.gesture_select), imgRadius * 2, imgRadius * 2);

        selectPoints = new ArrayList<>();
        isDrawing = false;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LogUtil.i(TAG, "onSizeChanged:" + getWidth());
        int width = getWidth();
        int height = getHeight();
        if (width > height) {
            int tmp = width;
            width = height;
            height = tmp;
        }
        measure(0, 0);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayoutParams();
        params.width = width;
        params.height = height;
        setLayoutParams(params);
//        int space = (width - column * imgRadius * 2) / (column - 1);

        for (int i = 0; i < column; i++) {
            for (int j = 0; j < column; j++) {
                points[i][j] = new Point(imgSpace * i + imgRadius * (1 + 2 * i), imgSpace * j + imgRadius * (1 + 2 * j), i + j * column);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtil.i(TAG, "onMeasure:" + imgRadius * 2 * column + 100);
        setMeasuredDimension(imgRadius * 2 * column + imgSpace*(column-1), imgRadius * 2 * column + imgSpace*(column-1));
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        drawLines(canvas);
        drawPoints(canvas);
    }

    /**
     * 绘制点
     *
     * @param canvas
     */
    private void drawPoints(Canvas canvas) {
        for (int i = 0; i < column; i++) {
            for (int j = 0; j < column; j++) {
                Bitmap tmpBitmap = null;
                Point currentPoint = points[i][j];
                if (currentPoint.status == Point.NORMAL) {
                    tmpBitmap = mBitmapNormal;
                } else if (currentPoint.status == Point.SELECT) {
                    tmpBitmap = mBitmapSelect;
                }
                canvas.drawBitmap(tmpBitmap, currentPoint.x - imgRadius, currentPoint.y - imgRadius, null);
            }
        }
    }

    /**
     * 重置
     */
    public void reset() {
        for (int i = 0; i < column; i++) {
            for (int j = 0; j < column; j++) {
                points[i][j].status = Point.NORMAL;
            }
        }
        selectPoints.clear();
        invalidate();
    }

    /**
     * 绘制线
     *
     * @param canvas
     */
    private void drawLines(Canvas canvas) {
        if (selectPoints.size() > 0) {
            Point tmpPoint = null;
            for (Point itemPoint : selectPoints) {
                if (tmpPoint != null) {
                    drawLine(canvas, tmpPoint, itemPoint);
                }
                tmpPoint = itemPoint;
            }
            if (isDrawing) {
                drawLine(canvas, tmpPoint, currentPoint);
            }
        }
    }

    /**
     * 绘制两点间的线
     *
     * @param canvas
     * @param startPoint
     * @param endPoint
     */
    private void drawLine(Canvas canvas, Point startPoint, Point endPoint) {
        canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return true;
        }
        currentPoint = new Point((int) event.getX(), (int) event.getY());
//        Log.i(TAG, "onTouchEvent:" + event.getX() + "," + event.getY() + "," + currentPoint.x + "," + currentPoint.y);
        if (MotionEvent.ACTION_DOWN == event.getAction()) {//手指按下
            reset();
            if (isSelectPoint(currentPoint)) {
                isDrawing = true;
            }
        } else if (MotionEvent.ACTION_MOVE == event.getAction() && isDrawing) {//手指移动
            isSelectPoint(currentPoint);
        } else if (MotionEvent.ACTION_UP == event.getAction() && isDrawing) {//手指松开
            isDrawing = false;
            currentPoint = null;
            if (onDrawFinishedListener != null) {
                onDrawFinishedListener.onDrawFinished(this, GestureUtil.getSelectResult(selectPoints));
            }
        }
        invalidate();
        return true;
    }

    /**
     * 处理图片
     *
     * @param bm        所要转换的bitmap
     * @param newWidth  新的宽
     * @param newHeight 新的高
     * @return 指定宽高的bitmap
     */
    private Bitmap zoomImg(Bitmap bm, float newWidth, float newHeight) {
        newWidth = newWidth <= 0 ? 1 : newWidth;
        newHeight = newHeight <= 0 ? 1 : newHeight;

        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = newWidth / width;
        float scaleHeight = newHeight / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }

    /**
     * 判断该点击的点是否能否成为选中点
     * 能，选中点并加如选中点列表
     *
     * @param point
     * @return
     */
    private Boolean isSelectPoint(Point point) {
        for (int i = 0; i < column; i++) {
            for (int j = 0; j < column; j++) {
                if (points[i][j].status == Point.NORMAL && points[i][j].getDistance(point) < imgRadius) {
                    //当前点未被选中，并且该点位置和point点距离小于半径
                    points[i][j].status = Point.SELECT;
                    selectPoints.add(points[i][j]);
                    return true;
                }
            }
        }
        return false;
    }

    private OnDrawFinishedListener onDrawFinishedListener;

    public interface OnDrawFinishedListener {
        boolean onDrawFinished(GestureView gestureView, String selectNum);
    }

    /**
     * 设置绘制完成监听接口
     *
     * @param onDrawFinishedListener
     */
    public void setOnDrawFinishedListener(OnDrawFinishedListener onDrawFinishedListener) {
        this.onDrawFinishedListener = onDrawFinishedListener;
    }

    public class Point {
        public static final int NORMAL = 1;
        public static final int SELECT = 2;
        public final int x;
        public final int y;
        public int num;
        public int status;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
            this.status = NORMAL;
        }

        public Point(int x, int y, int num) {
            this.x = x;
            this.y = y;
            this.num = num;
            this.status = NORMAL;
        }

        /**
         * 获取两点间距离
         *
         * @param point
         * @return
         */
        public float getDistance(Point point) {
            return (float) Math.sqrt((x - point.x) * (x - point.x) + (y - point.y) * (y - point.y));
        }
    }
}
