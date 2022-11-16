package com.bitsco.vks.common.model;

import com.bitsco.vks.common.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseModel {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.DATE.FORMAT.DATE_TIME)
    private Date createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.DATE.FORMAT.DATE_TIME)
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;
}
