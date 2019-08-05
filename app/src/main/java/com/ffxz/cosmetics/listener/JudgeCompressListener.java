package com.ffxz.cosmetics.listener;


import com.ffxz.cosmetics.model.JudgeGoodsData;
import com.ffxz.cosmetics.util.luban.OnCompressListener;

/**
 * Created by Suo on 2017/9/21.
 */

public interface JudgeCompressListener extends OnCompressListener {

    void onJudgeSuccess(JudgeGoodsData data);
}
