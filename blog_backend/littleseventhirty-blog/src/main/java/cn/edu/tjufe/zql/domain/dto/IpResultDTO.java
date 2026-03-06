package cn.edu.tjufe.zql.domain.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/6
 * @github https://github.com/little-seven-thirty
 */
@Data
public class IpResultDTO<T> implements Serializable {

    private Integer code;
    private String msg;
    private T data;

    public boolean isSuccess() {
        return Objects.nonNull(this.code) && this.code == 0;
    }
}
