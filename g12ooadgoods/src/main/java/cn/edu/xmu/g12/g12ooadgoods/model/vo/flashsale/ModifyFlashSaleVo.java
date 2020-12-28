package cn.edu.xmu.g12.g12ooadgoods.model.vo.flashsale;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class ModifyFlashSaleVo {
    @NotNull(message = "flashDate不得为空")
    @Size(min = 1)
    private LocalDateTime flashDate;
}
