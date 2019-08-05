package com.ffxz.cosmetics.ui.activity.goodDetails;

import com.ffxz.cosmetics.base.BasePresenter;
import com.ffxz.cosmetics.base.BaseView;
import com.ffxz.cosmetics.util.UserUtils;

/**
 * Created by Administrator on 2018/7/17 0017.
 */

public interface GoodDetails_contract {


	interface View extends BaseView {

		void isAsynGetDetailBefore();

		void setData();

		void dismissDialog();
	}

	interface Presenter extends BasePresenter {


		void doAsyncGetDetial(UserUtils mUtils, String productId, String sproductId);
	}
}
