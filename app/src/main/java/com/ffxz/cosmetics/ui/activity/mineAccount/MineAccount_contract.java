package com.ffxz.cosmetics.ui.activity.mineAccount;

import com.ffxz.cosmetics.base.BasePresenter;
import com.ffxz.cosmetics.base.BaseView;
import com.ffxz.cosmetics.model.AccordMoneyEntity;
import com.ffxz.cosmetics.model.AccountEntity;
import com.ffxz.cosmetics.util.UserUtils;
import com.ffxz.cosmetics.widget.ProgressLayout;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

/**
 * Created by Administrator on 2018/6/5 0005.
 */

public interface MineAccount_contract {

	interface View extends BaseView {

		void initDialog();

		void showToast(String o);

		void setDatas(AccordMoneyEntity.DataBean data);

		void dismissDialog();

		void notifyDataSetChanged();


		void pageNumReduce();
	}

	interface Presenter extends BasePresenter {


		void doAsyncGetData(UserUtils mUtils);

		void doRefreshData(int type ,int pageNum, ProgressLayout mProgressLayout, XRecyclerView mRecyclerView, List<AccountEntity.AccountData> mList);

		void doRequestData(int type ,int pageNum, ProgressLayout mProgressLayout, XRecyclerView mRecyclerView, List<AccountEntity.AccountData> mList);
	}
}
