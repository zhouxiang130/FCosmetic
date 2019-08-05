package com.ffxz.cosmetics.ui.activity.splash;

import android.content.Context;

import com.ffxz.cosmetics.base.BasePresenter;
import com.ffxz.cosmetics.base.BaseView;
import com.ffxz.cosmetics.model.UpdateEntity;
import com.ffxz.cosmetics.util.UserUtils;
import com.ffxz.cosmetics.widget.Dialog.UpdateDialog;

/**
 * Created by Administrator on 2018/5/23 0023.
 */

public interface Splash_Contract {

	interface View extends BaseView {
		void setUpdateData(UpdateEntity.UpdateData data, String newVersion);

		void isUpdate();

		void isForceUpdate();

		void dismissDialog();

		void changePage();

		boolean alertDialog();
	}

	interface Presenter extends BasePresenter {

		void doAsyncTaskUpdate(UserUtils mUtils);

		boolean isWifi(Context context);

		void initDialog(UpdateDialog updateDialog);

	}

}
