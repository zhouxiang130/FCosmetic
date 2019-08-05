package com.ffxz.cosmetics.ui.fragment.MineFrags;

import com.ffxz.cosmetics.base.BasePresenter;
import com.ffxz.cosmetics.base.BaseView;
import com.ffxz.cosmetics.model.MineEntity;
import com.ffxz.cosmetics.model.NormalEntity;
import com.ffxz.cosmetics.util.UserUtils;
import com.ffxz.cosmetics.widget.Dialog.CustomNormalContentDialog;
import com.sobot.chat.api.model.Information;

/**
 * Created by Administrator on 2018/6/4 0004.
 */

public interface MineFrag_contract {

	interface View extends BaseView {

		void setMineNewNum(boolean b, NormalEntity response);

		void setDatas(MineEntity.MineData data);

		void showToast(String s);

		void setSobotApi(Information userInfo);
	}

	interface Presenter extends BasePresenter {

		void setServiceTel(String serviceTel);

		void doCustomServices();

		void doAsyncGetInfo(UserUtils mUtils);

		void dismissDialog(CustomNormalContentDialog mDialog);
	}

}
