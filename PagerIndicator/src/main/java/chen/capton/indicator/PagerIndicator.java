package chen.capton.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import chen.capton.indicator.R;

import static android.content.ContentValues.TAG;

/**
 * Created by CAPTON on 2017/1/13.
 */

public class PagerIndicator extends HorizontalScrollView implements View.OnClickListener{
    private Context context;
    private int textColor;
    private int textCheckedColor;
    private int textSize;
    private int lineColor;
    private int lineHeight;
    private float lineProportion;
    private ViewPager viewPager;
    private List<TextView> textViewList=new ArrayList<>();

    private List<String> titleList;
    private LinearLayout wrapper;

    public ViewPager getViewPager() {return viewPager;}
    public PagerIndicator setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        return this;
    }
    public List<String> getTitleList() {return titleList;}
    public PagerIndicator setTitleList(List<String> titleList) {
        this.titleList = titleList;
        return this;
    }
    public int getTextColor() {return textColor;}
    public PagerIndicator setTextColor(int textColor) {
        this.textColor = getResources().getColor(textColor);
        invalidate();
        return this;
    }
    public int getTextSize() {return textSize;}
    public PagerIndicator setTextSize(int textSize) {
        this.textSize =  DisplayUtil.sp2px(context,textSize);;
        invalidate();
        return this;
    }
    public int getTextCheckedColor() {return textCheckedColor;}
    public PagerIndicator setTextCheckedColor(int textCheckedColor) {
        this.textCheckedColor = getResources().getColor(textCheckedColor);
        invalidate();
        return this;
    }
    public int getLineColor() {return lineColor;}
    public PagerIndicator setLineColor(int lineColor) {
        this.lineColor = getResources().getColor(lineColor);
        paint.setColor(this.lineColor);
        invalidate();
        return this;
    }
    public int getLineHeight() {return lineHeight;}
    public PagerIndicator setLineHeight(int lineHeight) {
        this.lineHeight = DisplayUtil.dip2px(context,lineHeight);
        invalidate();
        return this;
    }
    public float getLineProportion() {return lineProportion;}
    public PagerIndicator setLineProportion(float lineProportion) {
        this.lineProportion = lineProportion;
        invalidate();
        return this;
    }

    public PagerIndicator(Context context) {
        this(context,null);
    }
    public PagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {super(context, attrs, defStyleAttr);}
    public PagerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        this.context=context;
        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.PagerIndicator);
        textColor=ta.getColor(R.styleable.PagerIndicator_textColor,getResources().getColor(R.color.pager_indicator_black)); //默认标题字体为黑色
        textCheckedColor=ta.getColor(R.styleable.PagerIndicator_textCheckedColor,getResources().getColor(android.R.color.white)); //默认选中标题为白色
        lineColor=ta.getColor(R.styleable.PagerIndicator_lineColor,getResources().getColor(android.R.color.white)); //默认横线为白色
        textSize= (int) ta.getDimension(R.styleable.PagerIndicator_textSize,44);    //默认14sp(44px)
        lineHeight= (int) ta.getDimension(R.styleable.PagerIndicator_lineHeight,18); //默认6dp(18px)
        lineProportion=ta.getFloat(R.styleable.PagerIndicator_lineProportion,0.33f); //默认选项宽度的1/3
        if(lineProportion>1){
            lineProportion=1;
        }
        if(lineProportion<0){
            lineProportion=0;
        }
        ta.recycle();
        wrapper=new LinearLayout(context);
        paint=new Paint();
        paint.setColor(lineColor);
    }

    boolean once;
    int width,heigth;
    int childrenWidth=0;
    List<Integer> childWidthList=new ArrayList<>();
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        heightSize=heightMode==MeasureSpec.AT_MOST?DisplayUtil.dip2px(context,40):heightSize;
        width=widthSize;
        Log.i(TAG, "onMeasure: width "+width);
        heigth=heightSize;
        while (!once) {
            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(widthSize, heightSize);
            lp2.gravity=LinearLayout.HORIZONTAL;
            wrapper.setLayoutParams(lp2);
            for (int i = 0; i <titleList.size(); i++) {
                int childWidth=titleList.get(i).length()*60;
                childWidthList.add(childWidth);
                TextView textView=new TextView(context);
                textView.setText(titleList.get(i));
                textView.setTextSize(DisplayUtil.px2sp(context,textSize));
                textView.setTextColor(textColor);
                textView.setGravity(Gravity.CENTER);
                LinearLayout.LayoutParams lp3=new LinearLayout.LayoutParams(childWidth,heightSize-DisplayUtil.px2dip(context,lineHeight));
                textView.setLayoutParams(lp3);
                textView.setTag(i);
                textView.setOnClickListener(this);
                childrenWidth+=childWidth;
                textViewList.add(textView);
                 wrapper.addView(textView);
            }

            if(childrenWidth<widthSize){
                LinearLayout.LayoutParams lp4=new LinearLayout.LayoutParams(width/titleList.size(),heightSize-DisplayUtil.px2dip(context,lineHeight));
                childWidthList.clear();
                for (int i = 0; i <titleList.size(); i++) {
                    textViewList.get(i).setLayoutParams(lp4);
                    childWidthList.add(width/titleList.size());
                }
                childrenWidth=width;
            }
            textViewList.get(0).setTextColor(textCheckedColor);
            addView(wrapper);
            once=true;
        }
        setMeasuredDimension(width,heigth);
    }
    int tempPosition=0;
    float temp=0;
    float goneWidth=0;
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        wrapper.layout(0,0,childrenWidth,heigth);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if(position==tempPosition) {
                    left = childWidthList.get(position)*(1-lineProportion)/2+positionOffset*childWidthList.get(position)+goneWidth;
                    right = childWidthList.get(position)*(1+lineProportion)/2+positionOffset*childWidthList.get(position)+goneWidth;
                    top = heigth - DisplayUtil.px2dip(context,lineHeight);
                    bottom = heigth;
                    temp = positionOffset;
                }else {
                    int sum=0;
                    for (int i = 0; i < position; i++) {
                        sum+=childWidthList.get(i);
                    }
                    goneWidth=sum;
                    if(position>tempPosition) {
                        left = childWidthList.get(position)*(1-lineProportion)/2+goneWidth;
                        right = childWidthList.get(position)*(1+lineProportion)/2+goneWidth;
                    }else{
                        left = childWidthList.get(position)*(1-lineProportion)/2+positionOffset*childWidthList.get(position)+goneWidth;
                        right = childWidthList.get(position)*(1+lineProportion)/2+positionOffset*childWidthList.get(position)+goneWidth;
                    }
                    float scrollCenter=goneWidth+childWidthList.get(position)/2;
                    if (scrollCenter>=width/2){
                        smoothScrollTo((int) (scrollCenter-width/2),0);
                    }else {
                        smoothScrollTo(0,0);
                    }
                    temp=0;
                    tempPosition=position;
                }
                invalidate();
            }
            @Override
            public void onPageSelected(int position) {
                for(TextView textview:textViewList){
                    textview.setTextColor(textColor);
                }
                textViewList.get(position).setTextColor(textCheckedColor);
                invalidate();
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }
    Paint paint;
    float left,top,right,bottom;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(left,top,right,bottom,paint);
    }
    @Override
    public void onClick(View v) {
        if(v instanceof TextView){
            viewPager.setCurrentItem((Integer) v.getTag());
        }
    }
}
