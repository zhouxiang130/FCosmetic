package com.ffxz.cosmetics.model;

/**
 * Created by Suo on 2018/3/28.
 */

public class AccordMoneyEntity {


    /**
     * msg : 成功
     * code : 200
     * data : {"userMoney":"null","userCash":"0.0"}
     */

    private String msg;
    private String code;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userMoney : null
         * userCash : 0.0
         */

        private String userMoney;
        private String userCash;

        public String getUserMoney() {
            return userMoney;
        }

        public void setUserMoney(String userMoney) {
            this.userMoney = userMoney;
        }

        public String getUserCash() {
            return userCash;
        }

        public void setUserCash(String userCash) {
            this.userCash = userCash;
        }
    }
}