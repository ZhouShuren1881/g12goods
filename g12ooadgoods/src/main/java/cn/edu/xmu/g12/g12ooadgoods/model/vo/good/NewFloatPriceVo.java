package cn.edu.xmu.g12.g12ooadgoods.model.vo.good;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@ApiModel("新价格浮动传值对象")
public class NewFloatPriceVo {
    @NotNull(message = "activityPrice不得为空")
    @Size(min = 1)
    private Long activityPrice;

    @NotNull(message = "beginTime不得为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;

    @NotNull(message = "endTime不得为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @NotNull(message = "activityPrice不得为空")
    @Size(min = 1)
    private Integer quantity;
}
