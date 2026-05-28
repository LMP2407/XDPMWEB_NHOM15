package com.phonestore.dto.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class UpdateCartRequest {
    @NotNull
    @Min(value = 0, message = "Số lượng không hợp lệ")
    private Integer soLuong;

    public UpdateCartRequest() {}
    public Integer getSoLuong() { return soLuong; }
    public void setSoLuong(Integer soLuong) { this.soLuong = soLuong; }
}
