package cn.edu.xmu.g12.g12ooadgoods.model.vo.flashsale;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@ApiModel("新FlashSale传值对象")
public class NewFlashSaleVo {
    @NotNull(message = "flashDate 不得为空")
    @Size(min = 1)
    private LocalDateTime flashDate;
}
