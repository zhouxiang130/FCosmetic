package com.ffxz.cosmetics.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.ffxz.cosmetics.R;
import com.ffxz.cosmetics.base.LazyLoadFragment;
import com.ffxz.cosmetics.base.URLBuilder;
import com.ffxz.cosmetics.model.AccountListEntity;
import com.ffxz.cosmetics.ui.activity.StoreFellInDetailActivity;
import com.ffxz.cosmetics.ui.adapter.MyAccListAdapter;
import com.ffxz.cosmetics.util.Utils;
import com.ffxz.cosmetics.widget.ProgressLayout;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2019/6/17 0017.
 */

public class MyAccountListFragment extends LazyLoadFragment {
	private int flag, pageNum = 1;
	private MyAccListAdapter shopListAdapter;
	private List<AccountListEntity.DataBean.ListBean> data = null;
	private List<AccountListEntity.DataBean.ListBean> dataList;
	@BindView(R.id.xrecyclerView)
	XRecyclerView mRecyclerView;
	@BindView(R.id.progress_layout)
	ProgressLayout mProgressLayout;
	private AccountListEntity.DataBean datas;
	private String urls;
	private MyFreeOrderFragment parentFragment;

	public static MyAccountListFragment instant(int flag) {
		MyAccountListFragment fragment = new MyAccountListFragment();
		fragment.flag = flag;
		return fragment;
	}


	@Override
	protected int setContentView() {
		return R.layout.layout_shop_list;
	}

	@Override
	protected void lazyLoad() {
		refresh();
	}

	@Override
	protected void initView() {
		parentFragment = (MyFreeOrderFragment) getParentFragment();
	}


	private void refresh() {
		mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
			@Override
			public void onRefresh() {
				pageNum = 1;
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						doAsyncGetData(mUtils.getUid(), pageNum, flag);
					}
				}, 500);
			}

			@Override
			public void onLoadMore() {
				pageNum++;
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						mRecyclerView.setPullRefreshEnabled(false);
						loadMoreData(mUtils.getUid(), pageNum, flag);
					}
				}, 500);
			}
		});
		mRecyclerView.refresh();
	}


	@Override
	protected void initData() {
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(linearLayoutManager);
		mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
		mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
		mRecyclerView.setLoadingMoreEnabled(true);
		shopListAdapter = new MyAccListAdapter(getContext(), data, flag);
		mRecyclerView.setAdapter(shopListAdapter);
		shopListAdapter.setOnItemClickListener(new MyAccListAdapter.onItemClickListener() {
			@Override
			public void onItemClick(View view, int postion) {
				if (flag == 0) {
					Intent intent = new Intent(getContext(), StoreFellInDetailActivity.class);
					intent.putExtra("orderDescId", datas.getList().get(postion - 1).getOrderDescId());
					intent.putExtra("shopId", datas.getList().get(postion - 1).getShopId());
					intent.putExtra("shopName", datas.getList().get(postion - 1).getShopName());
					getContext().startActivity(intent);
				}
			}
		});

	}


	/**
	 * @param userId
	 * @param pageNum
	 * @param flag
	 */
	public void doAsyncGetData(String userId, int pageNum, final int flag) {
		Map<String, String> map = new HashMap<>();
		map.put("userId", userId);
		map.put("pageNum", String.valueOf(pageNum));
		if (flag == 0) {
			urls = URLBuilder.billCash;
		} else {
			urls = URLBuilder.searchUserBill;
		}
		Log.i(TAG, "传递的json值: " + URLBuilder.format(map));

		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + urls)
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<AccountListEntity>() {

			@Override
			public AccountListEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				Log.e(TAG, "json的值1==" + json);
				AccountListEntity shopListEntity = new Gson().fromJson(json, AccountListEntity.class);
				return shopListEntity;
			}

			@Override
			public void onResponse(AccountListEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					if (response.getData() != null) {
						datas = response.getData();
						dataList = datas.getList();
						showContent(dataList, flag);
					} else {
						setNoneList();
					}
				} else {
					Log.i(TAG, "我挂了" + response.getMsg());
					showNetError();
				}
				setRefreshComplete();
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				showContent(null, flag);
				setRefreshComplete();
				if (call.isCanceled()) {
					call.cancel();
				} else {
				}
			}
		});
	}


	public void loadMoreData(String userId, int pageNum, final int flag) {
		Map<String, String> map = new HashMap<>();
		map.put("userId", userId);
		map.put("pageNum", String.valueOf(pageNum));
		if (flag == 0) {
			urls = URLBuilder.billCash;
		} else {
			urls = URLBuilder.searchUserBill;
		}
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + urls)
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<AccountListEntity>() {

			@Override
			public AccountListEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				AccountListEntity shopListEntity = new Gson().fromJson(json, AccountListEntity.class);
				return shopListEntity;
			}

			@Override
			public void onResponse(AccountListEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					if (response.getData() != null) {
						AccountListEntity.DataBean data = response.getData();
						if (data.getList() != null && data.getList().size() > 0) {
							dataList.addAll(data.getList());
							showContent(dataList, flag);
							setRefreshComplete();
						} else {
							setNoMore(true);
						}
					} else {
						setNoneList();
					}
				} else {
				}
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				setRefreshComplete();
				if (call.isCanceled()) {
					call.cancel();
				} else {
				}
			}
		});
	}


	public void showContent(List<AccountListEntity.DataBean.ListBean> data, int type) {
		this.data = data;
		if (datas.getmMoney() != null && datas.getMcash() != null) {
			parentFragment.process(datas.getmMoney(), datas.getMcash(), type);
		}
		if (dataList != null && dataList.size() != 0) {
			mProgressLayout.showContent();
			shopListAdapter.setData(data);
		} else {
			setNoneList();
		}
	}

	public void setRefreshComplete() {
		if (mRecyclerView != null) {
			mRecyclerView.setPullRefreshEnabled(true);
			mRecyclerView.refreshComplete();
		}
	}


	public void setNoneList() {
		mProgressLayout.showNone(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});//没有数据
	}


	public void showNetError() {
		mProgressLayout.showNetError(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				setLocInfo();
			}
		});//没有数据
	}

	public void setNoMore(boolean b) {
		mRecyclerView.refreshComplete();
		mRecyclerView.setNoMore(b);
		mRecyclerView.setPullRefreshEnabled(true);
		pageNum--;
	}
}
