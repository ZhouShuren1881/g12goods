package cn.edu.xmu.g12.g12ooadgoods.model.vo.presale;

import cn.edu.xmu.g12.g12ooadgoods.model.po.PresaleActivityPo;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.flashsale.NewFlashSaleVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class NewPreSaleVo {
//  "name": "string",
//  "advancePayPrice": 0,
//  "restPayPrice": 0,
//  "quantity": 0,
//  "beginTime": "string",
//  "payTime": "string",
//  "endTime": "string"

    @NotNull(message = "name 不得为空")
    @Size(min = 1)
    private String name;

    @NotNull(message = "advancePayPrice 不得为空")
    @Min(0)
    private Long advancePayPrice;

    @NotNull(message = "restPayPrice 不得为空")
    @Min(0)
    private Long restPayPrice;

    private Integer quantity;
    @NotNull(message = "beginTime 不得为空")
    @Size(min = 1)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime beginTime;

    @NotNull(message = "payTime 不得为空")
    @Size(min = 1)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    @NotNull(message = "endTime 不得为空")
    @Size(min = 1)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}
