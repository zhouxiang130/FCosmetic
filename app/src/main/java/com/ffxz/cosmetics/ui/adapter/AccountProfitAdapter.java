package com.ffxz.cosmetics.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ffxz.cosmetics.R;
import com.ffxz.cosmetics.base.URLBuilder;
import com.ffxz.cosmetics.model.AccountEntity;
import com.ffxz.cosmetics.widget.MaterialRippleView;
import com.ffxz.cosmetics.widget.RoundedImageView.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/4/17.
 */

public class AccountProfitAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private Context mContext;
	List<AccountEntity.AccountData> mList;
	ProfitDetialClickListener mItemClickListener;

	public AccountProfitAdapter(Context mContext, List<AccountEntity.AccountData> mList) {
		this.mContext = mContext;
		this.mList = mList;
	}

	public void setOnItemClickListener(ProfitDetialClickListener listener) {
		this.mItemClickListener = listener;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		ProfitViewHolder profitViewHolder;
		profitViewHolder = new ProfitViewHolder(MaterialRippleView.create(LayoutInflater
				.from(mContext).inflate(R.layout.item_mine_account_profit, parent, false)), mItemClickListener);
		return profitViewHolder;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
		if (holder instanceof ProfitViewHolder) {
			if (null == mList.get(position).getBackUserId()) {
				((ProfitViewHolder) holder).llWithdrawal.setVisibility(View.GONE);
				((ProfitViewHolder) holder).llCash.setVisibility(View.VISIBLE);
				if (!TextUtils.isEmpty(mList.get(position).getCashMoney())) {
					((ProfitViewHolder) holder).tvCashMoney.setText("￥" + mList.get(position).getCashMoney());
				}
				if (!TextUtils.isEmpty(mList.get(position).getAlipayName())) {
					((ProfitViewHolder) holder).tvAlipayName.setText(mList.get(position).getAlipayName());
				}
				if (!TextUtils.isEmpty(mList.get(position).getCashState())) {
					((ProfitViewHolder) holder).tvCashState.setText(mList.get(position).getCashState());
				}
				if (!TextUtils.isEmpty(mList.get(position).getInsertTime())) {
					((ProfitViewHolder) holder).tvInsertTime.setText(mList.get(position).getInsertTime());
				}
			} else {
				((ProfitViewHolder) holder).llWithdrawal.setVisibility(View.VISIBLE);
				((ProfitViewHolder) holder).llCash.setVisibility(View.GONE);
				if (!TextUtils.isEmpty(mList.get(position).getBackUserHeadImg())) {
					String url;
					if (mList.get(position).getBackUserHeadImg().startsWith("http")) {
						url = mList.get(position).getBackUserHeadImg();
					} else {
						url = URLBuilder.URLBaseHeader + mList.get(position).getBackUserHeadImg();
					}
					Glide.with(mContext)
							.load(url)
							.asBitmap()
							.fitCenter()
							.error(R.mipmap.default_avatar)
							.into(((ProfitViewHolder) holder).ivHeader);
				}
				if (!TextUtils.isEmpty(mList.get(position).getInsertTime())) {
					((ProfitViewHolder) holder).tvTime.setText(mList.get(position).getInsertTime());
				}
				if (!TextUtils.isEmpty(mList.get(position).getBackUserName())) {
					((ProfitViewHolder) holder).tvUserName.setText(mList.get(position).getBackUserName());
				}
				if (!TextUtils.isEmpty(mList.get(position).getMoney())) {
					((ProfitViewHolder) holder).tvMoney.setText("￥" + mList.get(position).getMoney());
				}
			}
		}
	}

	@Override
	public int getItemCount() {
		return mList.size();
	}

	class ProfitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		@BindView(R.id.ll_withdrawal)
		LinearLayout llWithdrawal;
		@BindView(R.id.ll_cash)
		LinearLayout llCash;
		@BindView(R.id.iv_header)
		RoundedImageView ivHeader;
		@BindView(R.id.tv_user_name)
		TextView tvUserName;
		@BindView(R.id.tv_time)
		TextView tvTime;
		@BindView(R.id.tv_money)
		TextView tvMoney;

		@BindView(R.id.tv_cash_money)
		TextView tvCashMoney;
		@BindView(R.id.tv_alipay_name)
		TextView tvAlipayName;
		@BindView(R.id.tv_insert_time)
		TextView tvInsertTime;
		@BindView(R.id.tv_cash_state)
		TextView tvCashState;
		private ProfitDetialClickListener mListener;

		public ProfitViewHolder(View itemView, ProfitDetialClickListener listener) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			this.mListener = listener;
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			if (mListener != null) {
				mListener.onItemClick(v, getPosition());
			}
		}
	}

	public interface ProfitDetialClickListener {
		void onItemClick(View view, int postion);
	}
}