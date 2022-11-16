package com.bitsco.vks.common.model;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.StringCommon;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Truong Nguyen
 * Date: 16-Jul-19
 * Time: 11:43 AM
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Otp {
    private long id;
    private long userId;
    private long transId;
    private int type;
    private String phone;
    private String code;
    private long expiry;
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE_TIME_STAMP, timezone = "Asia/Ho_Chi_Minh")
    private Date expiryTime;
    private int status;

    public Otp(long userId, long transId, int type, String phone, long expiry) {
        this.id = System.currentTimeMillis();
        this.userId = userId;
        this.transId = transId;
        this.type = type;
        this.phone = phone;
        this.expiry = expiry;
        this.expiryTime = new Date(this.id + expiry);
        this.status = Constant.STATUS_OBJECT.ACTIVE;
    }

    public Otp(long userId, long transId, int type, String phone, String code) {
        this.userId = userId;
        this.transId = transId;
        this.type = type;
        this.phone = phone;
        this.code = code;
        this.status = Constant.STATUS_OBJECT.ACTIVE;
    }

    public Otp(int type, String phone, long expiry) {
        this.id = System.currentTimeMillis();
        this.type = type;
        this.phone = phone;
        this.expiry = expiry;
        this.expiryTime = new Date(this.id + expiry);
        this.status = Constant.STATUS_OBJECT.ACTIVE;
    }

    public void coppy(Otp otp) {
        this.id = System.currentTimeMillis();
        this.userId = otp.getUserId();
        this.transId = otp.getTransId();
        this.type = otp.getType();
        if (!StringCommon.isNullOrBlank(otp.getPhone()))
            this.phone = otp.getPhone();
        this.expiry = otp.getExpiry();
        this.expiryTime = new Date(this.id + otp.getExpiry());
        this.status = otp.getStatus();
    }

    public void setExpiry(long expiry) {
        this.expiry = expiry;
        this.expiryTime = new Date(System.currentTimeMillis() + expiry);
    }
}
