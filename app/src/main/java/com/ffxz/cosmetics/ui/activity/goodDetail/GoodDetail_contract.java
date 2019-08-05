package com.ffxz.cosmetics.ui.activity.goodDetail;

import com.ffxz.cosmetics.base.BasePresenter;
import com.ffxz.cosmetics.base.BaseView;

/**
 * Created by Administrator on 2018/7/17 0017.
 */

public interface GoodDetail_contract {


	interface View extends BaseView {

	}

	interface Presenter extends BasePresenter {


		void doAsyncGetDetial();
	}
}
