package com.ffxz.cosmetics.ui.fragment.MineFrags;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ffxz.cosmetics.R;
import com.ffxz.cosmetics.base.BaseFragment;
import com.ffxz.cosmetics.model.MineEntity;
import com.ffxz.cosmetics.model.NormalEntity;
import com.ffxz.cosmetics.ui.activity.MineAboutActivity;
import com.ffxz.cosmetics.ui.activity.MineAddressManageActivity;
import com.ffxz.cosmetics.ui.activity.MineCollectionActivity;
import com.ffxz.cosmetics.ui.activity.MineOrderActivity;
import com.ffxz.cosmetics.ui.activity.MineSettingActivity;
import com.ffxz.cosmetics.ui.activity.MyFreeOrderActivity;
import com.ffxz.cosmetics.ui.activity.mineAccount.MineAccount2Activity;
import com.ffxz.cosmetics.util.AuthorUtils;
import com.ffxz.cosmetics.util.IntentUtils;
import com.ffxz.cosmetics.util.ToastUtils;
import com.ffxz.cosmetics.widget.Dialog.CustomNormalContentDialog;
import com.sobot.chat.SobotApi;
import com.sobot.chat.api.enumtype.SobotChatTitleDisplayMode;
import com.sobot.chat.api.model.Information;
import com.sobot.chat.utils.ZhiChiConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Suo on 2017/11/18.
 *
 * @TODO 个人中心 2.0版本升级页面
 */

public class MineFrag1 extends BaseFragment implements MineFrag_contract.View {
	@BindView(R.id.tv_title)
	TextView tvTitle;
	@BindView(R.id.frag_mine_tv_info)
	TextView fragMineTvInfo;
	Unbinder unbinder;
	CustomNormalContentDialog mDialog;
	private MineFrag_contract.Presenter mineFragView = new MineFrag_presenter(this);

	/**
	 * 需要进行检测的权限数组
	 */
	private static final int PERMISSON_REQUESTCODE = 0;
	private boolean isNeedCheck = true;
	protected String[] needPermissions = {
			Manifest.permission.CALL_PHONE
	};

	private String serviceTel;
	private String upintegral;


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = createView(inflater.inflate(R.layout.fragment_mine1, container, false));
		unbinder = ButterKnife.bind(this, view);
		return view;
	}

	@Override
	protected void initData() {
		mineFragView.subscribe();
		regReceiver();
		tvTitle.setText("菲梵仙子");
	}

	@Override
	public void setMineNewNum(boolean b, NormalEntity response) {
		if (b) {
			String text = response.getData().toString();
			fragMineTvInfo.setVisibility(View.VISIBLE);
			int i = (int) Float.parseFloat(text);
			if (i > 99) {
				fragMineTvInfo.setText("99");
			} else {
				fragMineTvInfo.setText(i + "");
			}

		} else {
			fragMineTvInfo.setVisibility(View.GONE);
		}
	}


	@Override
	public void onResume() {
		super.onResume();
		mineFragView.doAsyncGetInfo(mUtils);
	}


	@OnClick({R.id.frag_mine_info, R.id.frag_mine_account, R.id.frag_mine_order_all, R.id.frag_mine_md, R.id.frag_mine_address,
			R.id.frag_mine_collection, R.id.frag_mine_online_service, R.id.frag_mine_rl_setting, R.id.frag_mine_rl_about})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.frag_mine_info:
				if (mUtils.isLogin()) {
					IntentUtils.IntentToInfoCenter(getActivity());
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_account:
				if (mUtils.isLogin()) {
					Intent intentAccount = new Intent(getActivity(), MineAccount2Activity.class);
					intentAccount.putExtra("upintegral", upintegral);
					startActivity(intentAccount);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_order_all:
				if (mUtils.isLogin()) {
					Intent intentAll = new Intent(getActivity(), MineOrderActivity.class);
					intentAll.putExtra("page", 0);
					startActivity(intentAll);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_md://我的免单
				if (mUtils.isLogin()) {
//					Intent intentInvite = new Intent(getActivity(), MineInviteActivity.class);
//					startActivity(intentInvite);

					Intent intentFreeOrder = new Intent(getActivity(), MyFreeOrderActivity.class);
					startActivity(intentFreeOrder);

//					ToastUtils.showToast(getContext(), "我的免单");
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_address:
				if (mUtils.isLogin()) {
					Intent intentAddress = new Intent(getActivity(), MineAddressManageActivity.class);
					startActivity(intentAddress);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_collection://我的收藏
				if (mUtils.isLogin()) {

					Intent intentSetting = new Intent(getActivity(), MineCollectionActivity.class);
					startActivity(intentSetting);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_online_service://在线客服
				if (mUtils.isLogin()) {
//					Intent intentCoupon = new Intent(getActivity(), MineCouponActivity.class);
//					startActivity(intentCoupon);

					mineFragView.doCustomServices();
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_rl_setting:

//				Intent intent = new Intent(getActivity(),MineRefundClassActivity.class);
//				intent.putExtra("mList", mList.get(position));
//				intent.putExtra("Money", mList.get(position).getMoney());
//				startActivity(intent);

				if (mUtils.isLogin()) {
					Intent intentSetting = new Intent(getActivity(), MineSettingActivity.class);
					startActivity(intentSetting);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_rl_about:
				if (mUtils.isLogin()) {
					Intent intentAbout = new Intent(getActivity(), MineAboutActivity.class);
					startActivity(intentAbout);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
		}
	}

	private void setCallDialog() {
		if (mDialog == null) {
			mDialog = new CustomNormalContentDialog(getActivity());
		}
	}


	private MyReceiver receiver;//广播

	private void regReceiver() {
		//注册广播获取新收到的信息和未读消息数
		if (receiver == null) {
			receiver = new MyReceiver();
		}
		IntentFilter filter = new IntentFilter();
		filter.addAction(ZhiChiConstant.sobot_unreadCountBrocast);
		getActivity().registerReceiver(receiver, filter);
	}

	//设置广播获取新收到的信息和未读消息数
	class MyReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			int noReadNum = intent.getIntExtra("noReadCount", 0);
			String content = intent.getStringExtra("content");
			//未读消息数
			if (noReadNum != 0) {
//				tvCoustomNum.setVisibility(View.VISIBLE);
//				tvCoustomNum.setText(noReadNum + "");
			}
			//新消息内容
			com.sobot.chat.utils.LogUtils.i("新消息内容:" + content);
		}
	}


	private void setActionCall(String serviceTel) {
		//拨打电话
		if (serviceTel != null) {
			Intent callIntent = new Intent();
			callIntent.setAction(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:" + serviceTel));
			startActivity(callIntent);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode) {
			case PERMISSON_REQUESTCODE:
				if (!AuthorUtils.verifyPermissions(grantResults)) {
					isNeedCheck = false;
				} else {
					isNeedCheck = true;
				}
				if (isNeedCheck) {
					setActionCall(serviceTel);
				}
				break;
		}
	}


	@Override
	public void setSobotApi(Information userInfo) {
		//设置标题显示模式
		SobotApi.setChatTitleDisplayMode(getActivity().getApplicationContext(), SobotChatTitleDisplayMode.values()[0], "");
		//设置是否开启消息提醒
		SobotApi.setNotificationFlag(getActivity().getApplicationContext(), true, R.mipmap.logo, R.mipmap.logo);
		SobotApi.hideHistoryMsg(getActivity().getApplicationContext(), 0);
		SobotApi.setEvaluationCompletedExit(getActivity().getApplicationContext(), false);
		SobotApi.startSobotChat(getActivity(), userInfo);
	}

	@Override
	public void setDatas(MineEntity.MineData data) {
		preferencesUtil.setValue("userType", data.getUserType());
		mineFragView.setServiceTel(data.getServiceTel());
		if (!TextUtils.isEmpty(data.getUpintegral())) {
			upintegral = data.getUpintegral();
		}
		if (!TextUtils.isEmpty(data.getServiceTel())) {
			mUtils.saveServiceTel(data.getServiceTel());
		}
	}


	@Override
	public void showToast(String s) {
		ToastUtils.showToast(getActivity(), s);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mineFragView.dismissDialog(mDialog);
		getActivity().unregisterReceiver(receiver);
	}

}
