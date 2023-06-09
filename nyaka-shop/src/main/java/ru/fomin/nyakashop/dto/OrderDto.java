package ru.fomin.nyakashop.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "User order")
public class OrderDto {

    @ApiModelProperty(value = "Order id", dataType = "integer", example = "3")
    Long id;

    @ApiModelProperty(value = "Price of all order positions", dataType = "number", example = "233.44")
    BigDecimal totalPrice;

    String createdAt;
    String updateAt;
    String address;
    String phone;
    String status;
    String statusName;

}
