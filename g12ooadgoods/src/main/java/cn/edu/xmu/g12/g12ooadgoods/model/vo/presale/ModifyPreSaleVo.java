package cn.edu.xmu.g12.g12ooadgoods.model.vo.presale;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ModifyPreSaleVo {
//  "name": "string",
//  "advancePayPrice": 0,
//  "restPayPrice": 0,
//  "quantity": 0,
//  "beginTime": "string",
//  "payTime": "string",
//  "endTime": "string"

    private String name;
    private Long advancePayPrice;
    private Long restPayPrice;
    private Integer quantity;
    private LocalDateTime beginTime;
    private LocalDateTime payTime;
    private LocalDateTime endTime;
}