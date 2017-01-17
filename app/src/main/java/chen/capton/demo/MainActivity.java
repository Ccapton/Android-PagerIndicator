package chen.capton.demo;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import chen.capton.indicator.PagerIndicator;

public class MainActivity extends AppCompatActivity {
   ViewPager viewPager;
   ViewPager viewPager2;
    PagerIndicator indicator;
    PagerIndicator indicator2;
    List<View> viewList;
    List<View> viewList2;
    ArrayList<String> titleList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager= (ViewPager) findViewById(R.id.viewpager);
        viewPager2= (ViewPager) findViewById(R.id.viewpager2);
        viewList=new ArrayList<>();
        viewList2=new ArrayList<>();
        LayoutInflater inflater=LayoutInflater.from(this);
        for (int i = 0; i <8; i++) {
            View view=inflater.inflate(R.layout.page,null);
            TextView textview= (TextView) view.findViewById(R.id.text);
            textview.setText("Page"+i);
            viewList.add(view);
        }
        for (int i = 0; i <8; i++) {
            View view2=inflater.inflate(R.layout.page,null);
            TextView textview2= (TextView) view2.findViewById(R.id.text);
            textview2.setText("Page"+i);
            viewList2.add(view2);
        }
        PagerAdapter adapter=new PagerAdapter() {
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(viewList.get(position));
            }
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position));
                return viewList.get(position);
            }
            @Override
            public int getCount() {
                return viewList.size();
            }
            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }
        };
        PagerAdapter adapter2=new PagerAdapter() {
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(viewList2.get(position));
            }
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList2.get(position));
                return viewList2.get(position);
            }
            @Override
            public int getCount() {
                return viewList2.size();
            }
            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }
        };
        viewPager.setAdapter(adapter);
        viewPager2.setAdapter(adapter2);
        for (int i = 0; i <8; i++) {
            titleList.add("我很帅");
        }
        indicator= (PagerIndicator) findViewById(R.id.indicator);
        indicator2= (PagerIndicator) findViewById(R.id.indicator2);
        indicator.setTitleList(titleList);
        indicator.setViewPager(viewPager);
        indicator2.setTitleList(titleList).setViewPager(viewPager2);
    }
}
