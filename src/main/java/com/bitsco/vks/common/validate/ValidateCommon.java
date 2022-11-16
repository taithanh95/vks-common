package com.bitsco.vks.common.validate;

import com.bitsco.vks.common.constant.MessageContent;
import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.util.MessageCommon;
import com.bitsco.vks.common.util.StringCommon;

import java.time.DayOfWeek;
import java.util.Objects;

public class ValidateCommon {

    public static void validateNullObject(Object object, String objectName) {
        if (object instanceof String) {
            if (StringCommon.isNullOrBlank(String.valueOf(object)))
                throw new CommonException(Response.MISSING_PARAM, MessageCommon.getMessage(MessageContent.MISSING_PARAM, objectName));
        } else if (Objects.isNull(object))
            throw new CommonException(Response.MISSING_PARAM, MessageCommon.getMessage(MessageContent.MISSING_PARAM, objectName));
    }

    public static void validateStatusObject(Integer status) {
        if (status != Constant.STATUS_OBJECT.ACTIVE && status != Constant.STATUS_OBJECT.INACTIVE)
            throw new CommonException(Response.DATA_INVALID, "Trạng thái phải có giá trị " + Constant.STATUS_OBJECT.ACTIVE + " hoặc " + Constant.STATUS_OBJECT.INACTIVE);
    }

    public static void validateDayOfWeek(Integer dayOfWeek) {
        try {
            DayOfWeek.of(dayOfWeek);
        } catch (Exception e) {
            throw new CommonException(Response.DATA_INVALID, e.getMessage());
        }
    }
}
