package com.bitsco.vks.common.response;

import com.bitsco.vks.common.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private String username;
    private String accessToken;
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE_TIME, timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE_TIME, timezone = "Asia/Ho_Chi_Minh")
    private Date expiredAt;
    private Object user;

    public Token(String username, String accessToken, Date createdAt, Date expiredAt) {
        this.username = username;
        this.accessToken = accessToken;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }
}
