# PagerIndicator 
> 关于我，欢迎关注  
  博客：ccapton(http://blog.csdn.net/ccapton) 微信：[Ccapton]()   
 
### 简介: 

这是一个ViewPager的指示器。当页面滑动时，指示器也会跳转到其相应的选项去；点击选项，ViewPager也会跳转到相应页面。

### 效果:   
![](https://raw.githubusercontent.com/Ccapton/pagerIndicator/master/indicator.gif)

### 如何配置
build.gradle(Project)
``` code
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
build.gradle(Module:app)
``` code 
 dependencies {
	        compile 'com.github.Ccapton:PagerIndicator:1.1'
	}
```

### 公共方法
``` code
  **为必须要先调用的方法
   
  setViewPager(ViewPager viewPager);//将要绑定的viewpager对象传进PagerIndicator对象中，供其调用。 **
  setTitleList(List<String> titleList) //设置标题的字符串ArrayList                               **
  setTextCheckedColor(int textCheckedColor) //设置标题选中时字体的颜色
 	setTextColor(int textColor)   //设置标题的字体颜色
 	setTextSize(int textSize)      //设置标题的字体大小
  setLineColor(int lineColor)    //设置横线的颜色
 	setLineHeight(int lineHeight)  //设置横线的厚度（高度）
 	setLineProportion(float lineProportion) //设置横线占标题宽度的比例（宽度）：0.0~1.0
```
	
### 使用方法
 1.例如：在activity_main.xml中,把该自定义控件PagerIndicator写在目标ViewPager前面
``` xml
 <?xml version="1.0" encoding="utf-8"?>
 <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/activity_main"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:background="#80bebebe"
    tools:context="chen.capton.demo.MainActivity">

    <!--默认样式default-->
     <chen.capton.custom.PagerIndicator
         xmlns:indicator="http://schemas.android.com/apk/res-auto"
         android:id="@+id/indicator"
         android:layout_width="match_parent"
         android:layout_height="wrap_content"/>
    <android.support.v4.view.ViewPager
        android:id="@+id/viewpager"
        android:background="#fff"
        android:layout_width="match_parent"
        android:layout_height="200dp">
    </android.support.v4.view.ViewPager>
    
     <!--自定义样式custom-->
    <chen.capton.custom.PagerIndicator
        xmlns:indicator="http://schemas.android.com/apk/res-auto"
        android:layout_marginTop="@dimen/activity_vertical_margin"
        android:background="@color/colorPrimary"
        android:id="@+id/indicator2"
        indicator:textColor="#fff"  //设置标题的字体颜色
        indicator:textCheckedColor="#03eC19" //设置标题选中时字体的颜色
        indicator:textSize="12sp"    //设置标题的字体大小
        indicator:lineColor="#03eC19"  //设置横线的颜色
        indicator:lineProportion="1"   //设置横线占标题宽度的比例（宽度）：0.0~1.0
        indicator:lineHeight="12dp"    //设置横线的厚度（高度）
        android:layout_width="match_parent"
        android:layout_height="50dp"/>
    <android.support.v4.view.ViewPager
        android:id="@+id/viewpager2"
        android:background="#fff"
        android:layout_width="match_parent"
        android:layout_height="200dp">
    </android.support.v4.view.ViewPager>
    </LinearLayout>
```
  2.在代码中findViewById获取PagerIndicator对象，然后调用其公共方法（上文提到了）。例如下面的例子：
 ``` code
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager= (ViewPager) findViewById(R.id.viewpager);
        ...省略代码...
        viewPager.setAdapter(adapter);
        indicator= (PagerIndicator) findViewById(R.id.indicator);
        indicator2= (PagerIndicator) findViewById(R.id.indicator2);
        indicator.setTitleList(titleList); //设置标题字符串ArrayList, 首先调用
        indicator.setViewPager(viewPager);  //设置目标ViewPager对象， 第二调用
        indicator.setTextColor(R.color.pager_indicator_black); /设置标题的字体颜色
        indicator.setTextSize(12);   //设置标题的字体大小
        indicator.setTextCheckedColor(R.color.pager_indicator_white); //设置标题选中时字体的颜色
        indicator.setLineHeight(12); //设置横线的厚度（高度）
        indicator.setLineColor(R.color.pager_indicator_white); //设置横线的颜色
        indicator.setLineProportion(0.8f); //设置横线占标题宽度的比例（宽度）：0.0~1.0
        //也可以链式调用
        // indicator.setTitleList(titleList).setViewPager(viewPager).setTextSize(12).setLineHeight(12)...;
        } 
 ```
### 作者的话
 一个诚意满满的作品，哈哈，欢迎大家采纳，源码仅供参考。
