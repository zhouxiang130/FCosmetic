package com.ffxz.cosmetics.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ffxz.cosmetics.R;
import com.ffxz.cosmetics.base.BaseActivity;
import com.ffxz.cosmetics.base.Key;
import com.ffxz.cosmetics.base.URLBuilder;
import com.ffxz.cosmetics.model.AccountWithEntity;
import com.ffxz.cosmetics.util.LogUtils;
import com.ffxz.cosmetics.util.ToastUtils;
import com.ffxz.cosmetics.util.Utils;
import com.ffxz.cosmetics.widget.Dialog.CustomPostDialog;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Suo on 2018/3/15.
 * 已经修改
 */

public class MineAccountWithdrawActivity extends BaseActivity {

	@BindView(R.id.mine_account_withdraw_etmoney)
	EditText etMoney;
	@BindView(R.id.mine_account_withdraw_tv_money)
	TextView tvMoney;
	@BindView(R.id.et_alipay_name)
	EditText etAlipayName;
	@BindView(R.id.et_alipay_account)
	EditText etAlipayAccount;
	@BindView(R.id.withdraw_btn_confirm)
	Button btnConfirm;
	@BindView(R.id.scrollView)
	ScrollView mScrollView;
	private CustomPostDialog postDialog;
	private boolean isScroll = false;
	private String userMoney;

	@Override
	protected int getContentView() {
		return R.layout.activity_mine_account_withdraw;
	}

	@Override
	protected void initView() {
		setTitleText("提现");
		userMoney = getIntent().getStringExtra("userMoney");
		if (userMoney != null && !TextUtils.isEmpty(userMoney)) {
			tvMoney.setText(userMoney);
		}
	}

	@Override
	protected void initData() {
		etMoney.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				scrollVertical(mScrollView.getHeight());
				return false;
			}
		});
		etAlipayName.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				scrollVertical(mScrollView.getHeight());
				return false;
			}
		});
		etAlipayAccount.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				scrollVertical(mScrollView.getHeight());
				return false;
			}
		});
	}

	@OnClick({R.id.withdraw_btn_confirm, R.id.mine_account_withdraw_tv_all})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.withdraw_btn_confirm:
				String money = etMoney.getText().toString().trim();
				if (TextUtils.isEmpty(money)) {
					//money输入为空 此时不发送验证码
					ToastUtils.showToast(this, "请输入要提现的金额");
					return;
				}
				if (money.contains(".") && money.substring(money.indexOf(".")).length() > 3) {
					//小数位大于2
					ToastUtils.showToast(getApplicationContext(), "取现小数不得大于两位数,请重新输入");
					return;
				}
				float have = Float.parseFloat(userMoney);
				float want = Float.parseFloat(etMoney.getText().toString().trim());
				if (have < want) {
					ToastUtils.showToast(getApplicationContext(), "余额不足,请重新输入");
					return;
				}
				doAsyncPost();
				break;
			case R.id.mine_account_withdraw_tv_all:
				if (!TextUtils.isEmpty(userMoney)) {
					float bal = Float.parseFloat(userMoney);
					if (bal > 0) {
						etMoney.setText(userMoney);
					}
				}
				break;
		}
	}


	private void doAsyncPost() {
		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		map.put("alipayName", etAlipayName.getText().toString().trim());
		map.put("alipayAccount", etAlipayAccount.getText().toString().trim());
		map.put("money", etMoney.getText().toString().trim());
		LogUtils.e("changeName传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/saveCash")
				.addParams(Key.data, URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<AccountWithEntity>() {

			@Override
			public AccountWithEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.e("json的值" + json);
				return new Gson().fromJson(json, AccountWithEntity.class);
			}

			@Override
			public void onResponse(final AccountWithEntity response) {
				Log.e("-0-", "0--");
				if (response != null && response.getCode().equals("200")) {
					//返回值为200 说明请求成功
					if (postDialog == null) {
						postDialog = new CustomPostDialog(MineAccountWithdrawActivity.this);
					}
					if (!postDialog.isShowing()) {
						postDialog.show();
					}
					btnConfirm.postDelayed(new Runnable() {
						@Override
						public void run() {
							Intent intent = new Intent(MineAccountWithdrawActivity.this, WithdrawalSuccessActivity.class);
							intent.putExtra("insertTime", response.getData().getInsertTime());
							intent.putExtra("money", response.getData().getMoney());
							startActivity(intent);
							finish();
						}
					}, 600);
				} else {
					ToastUtils.showToast(MineAccountWithdrawActivity.this, "请求失败 :)" + response.getMsg());
				}
			}

			@Override
			public void onError(Call call, Exception e) {
				Log.e("-0-", "1--");
				super.onError(call, e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(MineAccountWithdrawActivity.this, "网络故障,请稍后再试");
				}

			}
		});
	}

	/**
	 * 使滚动条滚动至指定位置（垂直滚动）
	 *
	 * @param to 滚动到的位置
	 */
	protected void scrollVertical(final int to) {
		if (!isScroll) {
			isScroll = true;
			mScrollView.postDelayed(new Runnable() {
				@Override
				public void run() {
					LogUtils.i("弹出后的高度是" + to);
					mScrollView.scrollTo(0, to);
					isScroll = false;
				}
			}, 500);
		}
	}

	private void dismissDialog2() {
		if (postDialog != null) {
			postDialog.dismiss();
			postDialog = null;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dismissDialog2();
		OkHttpUtils.getInstance().cancelTag(this);
	}

}
