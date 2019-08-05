package com.ffxz.cosmetics.ui.activity.mineSignIn;

import com.ffxz.cosmetics.base.BasePresenter;
import com.ffxz.cosmetics.base.BaseView;
import com.ffxz.cosmetics.model.SignInEntity;

/**
 * Created by Administrator on 2018/6/6 0006.
 */

public interface MineSign_contract {

	interface View extends BaseView {

		void setSignInData(SignInEntity.DataBean data);
	}

	interface Presenter extends BasePresenter {

		void doAsyncGetSignIn(String uid);
	}

}
