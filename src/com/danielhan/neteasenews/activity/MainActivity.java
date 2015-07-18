package com.danielhan.neteasenews.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.danielhan.neteasenews.R;
import com.danielhan.neteasenews.adapter.DragAdapter;
import com.danielhan.neteasenews.adapter.OtherAdapter;
import com.danielhan.neteasenews.bean.ColumnBean;
import com.danielhan.neteasenews.customview.DragGridView;
import com.danielhan.neteasenews.customview.PagerSlidingTabStrip;
import com.danielhan.neteasenews.db.ColumnDao;

public class MainActivity extends FragmentActivity {
	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private ImageView iv_change_column;
	private TextView tv_ok;
	private LinearLayout ll_tab_top;
	private TextView tv_change_column;
	private LinearLayout ll_tab_bottom;
	private RelativeLayout rl_change_column;
	private LinearLayout ll_change_column;
	private MyPagerAdapter adapter;
	private DragGridView userGridview;
	private GridView otherGridView;
	private Animation bottomTabAnimation;
	private Animation changeColumnAnimation;
	private Animation tvchangeColumnAnimation;
	private boolean isChangeColunmOpen = false;
	private final String[] TITLES = { "头条", "娱乐", "热点", "体育", "北京", "财经", "科技",
			"段子", "图片", "汽车", "时尚", "轻松一刻", "军事", "历史", "游戏", "彩票", "原创", "政务" };
	/** 用户栏目对应的适配器，可以拖动 */
	private DragAdapter userAdapter;
	/** 其它栏目对应的适配器 */
	private OtherAdapter otherAdapter;
	/** 其它栏目列表 */
	List<ColumnBean> otherChannelList = new ArrayList<ColumnBean>();
	/** 用户栏目列表 */
	List<ColumnBean> userChannelList = new ArrayList<ColumnBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		listen();
	}

	public void initView() {
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager = (ViewPager) findViewById(R.id.pager);
		iv_change_column = (ImageView) findViewById(R.id.iv_change_column);
		tv_ok = (TextView) findViewById(R.id.tv_ok);
		ll_tab_top = (LinearLayout) findViewById(R.id.ll_tab_top);
		tv_change_column = (TextView) findViewById(R.id.tv_change_column);
		ll_tab_bottom = (LinearLayout) findViewById(R.id.ll_tab_bottom);
		rl_change_column = (RelativeLayout) findViewById(R.id.rl_change_column);
		ll_change_column = (LinearLayout) findViewById(R.id.ll_change_column);
		userGridview = (DragGridView) findViewById(R.id.gridview);
		otherGridView = (GridView) findViewById(R.id.otherGridView);

//		ViewTreeObserver vto2 = ll_change_column.getViewTreeObserver();
//		vto2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
//			@Override
//			public void onGlobalLayout() {
//				ll_change_column.getViewTreeObserver()
//						.removeGlobalOnLayoutListener(this);
//				LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT,
//						ll_change_column.getHeight());
//				lp.setMargins(0, -ll_change_column.getHeight(), 0, 0);
//				ll_change_column.setLayoutParams(lp);
//			}
//		});
		ll_change_column.setVisibility(View.GONE);

		adapter = new MyPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);
		final int pageMargin = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
						.getDisplayMetrics());
		pager.setPageMargin(pageMargin);
		tabs.setViewPager(pager);
		userChannelList = new ColumnDao(MainActivity.this).getColumn("1");
		otherChannelList = new ColumnDao(MainActivity.this).getColumn("0");
		userAdapter = new DragAdapter(this, userChannelList);
		userGridview.setAdapter(userAdapter);
		otherAdapter = new OtherAdapter(this, otherChannelList);
		otherGridView.setAdapter(otherAdapter);
		otherGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.e("onItemClick", "onItemClick");
			}
		});
	}

	public void listen() {
		iv_change_column.setOnClickListener(new MyOnClickListener());
		tv_change_column.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// 当“切换栏目”显示在上层时，防止滑动影响到psts
				return true;
			}
		});
		rl_change_column.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//防止上层滑动时,会使得下面的viewpager滑动
				return true;
			}
		});
		
		userGridview.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				tv_change_column.setText("拖动排序");
				iv_change_column.setVisibility(View.GONE);
				tv_ok.setVisibility(View.VISIBLE);
				return userGridview.onItemLongClick(parent, view, position, id);
			}
		});
		tv_ok.setOnClickListener(new MyOnClickListener());
	}

	class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.iv_change_column:
				if (!isChangeColunmOpen) {
					// bottomTab动画
					bottomTabAnimation = AnimationUtils.loadAnimation(
							MainActivity.this, R.anim.push_bottom_out);
					bottomTabAnimation.setFillAfter(true);
					ll_tab_bottom.startAnimation(bottomTabAnimation);
					// 箭头方向改变
					iv_change_column.setImageResource(R.drawable.arrow_up);
					// 切换栏目打开
					ll_change_column.setVisibility(View.VISIBLE);
					changeColumnAnimation = AnimationUtils.loadAnimation(
							MainActivity.this, R.anim.push_top_in);
					changeColumnAnimation.setFillAfter(true);
					ll_change_column.startAnimation(changeColumnAnimation);
					isChangeColunmOpen = true;
					// 切换栏目标题显示
					tvchangeColumnAnimation = AnimationUtils.loadAnimation(
							MainActivity.this, R.anim.alpha_show);
					tv_change_column.setVisibility(View.VISIBLE);
					tv_change_column.startAnimation(tvchangeColumnAnimation);
				} else {
					// bottomTab动画
					bottomTabAnimation = AnimationUtils.loadAnimation(
							MainActivity.this, R.anim.push_bottom_in);
					bottomTabAnimation.setFillAfter(true);
					ll_tab_bottom.startAnimation(bottomTabAnimation);
					// 箭头方向改变
					iv_change_column.setImageResource(R.drawable.arrow_down);
					// 切换栏目关闭
					changeColumnAnimation = AnimationUtils.loadAnimation(
							MainActivity.this, R.anim.push_top_out);
					changeColumnAnimation.setFillAfter(true);
					ll_change_column.startAnimation(changeColumnAnimation);
					changeColumnAnimation.setAnimationListener(new AnimationListener() {
						@Override
						public void onAnimationStart(Animation animation) {
							
						}
						
						@Override
						public void onAnimationRepeat(Animation animation) {
							
						}
						
						@Override
						public void onAnimationEnd(Animation animation) {
							ll_change_column.setVisibility(View.GONE);
						}
					});
					isChangeColunmOpen = false;
					// 切换栏目标题隐藏
					tvchangeColumnAnimation = AnimationUtils.loadAnimation(
							MainActivity.this, R.anim.alpha_hide);
					tv_change_column.startAnimation(tvchangeColumnAnimation);
					tv_change_column.setVisibility(View.GONE);
				}
				break;
			case R.id.tv_ok:
				userAdapter.showAllCheckBox(false);
				tv_change_column.setText(getResources().getString(R.string.change_column));
				iv_change_column.setVisibility(View.VISIBLE);
				tv_ok.setVisibility(View.GONE);
				break;
			}
		}

	}

	class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		@Override
		public Fragment getItem(int position) {
			return SuperAwesomeCardFragment.newInstance(position);
		}

	}
}
