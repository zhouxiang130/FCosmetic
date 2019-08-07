package com.ffxz.cosmetics.ui.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ffxz.cosmetics.R;
import com.ffxz.cosmetics.base.BaseFragment;
import com.ffxz.cosmetics.ui.adapter.MineOrderTabAdapter;
import com.ffxz.cosmetics.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/6/15 0015.
 */

public class MyFreeOrderFragment extends BaseFragment {

	@BindView(R.id.tablayout)
	TabLayout tabLayout;
	@BindView(R.id.tv_mine_store_num)
	TextView tvStoreNum;
	@BindView(R.id.tv_mine_free_sayest)
	TextView tvMoney;
	@BindView(R.id.tv_account_pic)
	TextView tvAccountPic;
	@BindView(R.id.tv_total_store)
	TextView tvTotalStore;
	@BindView(R.id.viewpager)
	NoScrollViewPager mViewpager;
	@BindView(R.id.frag_cart_head)
	View vHead;
	private List<String> mTitle = new ArrayList<>();
	private List<Fragment> mFragment = new ArrayList<>();

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = createView(inflater.inflate(R.layout.fragment_account_list, container, false));
		return view;
	}

	@TargetApi(21)
	private void transTitle() {
		if (Build.VERSION.SDK_INT >= 21) {
			vHead.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void initData() {
		transTitle();
		mTitle.add("我的消费");
		mTitle.add("我的免单");
		for (int i = 0; i < mTitle.size(); i++) {
			mFragment.add(MyAccountListFragment.instant(i));
		}
		MineOrderTabAdapter adapter = new MineOrderTabAdapter(MyFreeOrderFragment.this.getChildFragmentManager(), mTitle, mFragment);
		mViewpager.setAdapter(adapter);
		//为TabLayout设置ViewPager
		tabLayout.setupWithViewPager(mViewpager);
		tabLayout.setTabsFromPagerAdapter(adapter);
	}


	public void process(String str, String s,int type) {
		tvMoney.setText(str);
		tvStoreNum.setText(s);
		if (type==0) {
			tvAccountPic.setText("累计消费（元）");
			tvTotalStore.setText("累计消费（个）");
		}else if (type==1){
			tvAccountPic.setText("累计免单（元）");
			tvTotalStore.setText("已免单（个）");
		}
	}
}
