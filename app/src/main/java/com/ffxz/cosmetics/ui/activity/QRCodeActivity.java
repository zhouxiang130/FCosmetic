package com.ffxz.cosmetics.ui.activity;

import com.ffxz.cosmetics.R;
import com.ffxz.cosmetics.base.BaseActivity;

/**
 * Created by Administrator on 2019/8/6 0006.
 */

public class QRCodeActivity  extends BaseActivity{


	@Override
	protected int getContentView() {
		return R.layout.activity_qr_code;
	}

	@Override
	protected void initView() {
		setTitleText("我的二维码");

	}

	@Override
	protected void initData() {

	}
}
