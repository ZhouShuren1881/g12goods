package cn.edu.xmu.g12.g12ooadgoods.model.vo.groupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class NewGrouponVo {
    @NotNull(message = "name 不得为空")
    @Size(min = 1)
    private String strategy;

    @NotNull(message = "beginTime 不得为空")
    @Size(min = 1)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;

    @NotNull(message = "endTime 不得为空")
    @Size(min = 1)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}
