package com.ffxz.cosmetics.ui.activity;


import com.ffxz.cosmetics.R;
import com.ffxz.cosmetics.base.BaseActivity;

public class WithdrawalSuccessActivity extends BaseActivity {


	@Override
	protected int getContentView() {
		return R.layout.activity_with_success;
	}

	@Override
	protected void initView() {
		setTitleText("提现成功");
		hideTitle();
	}

	@Override
	protected void initData() {

	}
}
