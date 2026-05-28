package com.phonestore.dto.admin;

import jakarta.validation.constraints.NotBlank;

public class UpdatePaymentStatusRequest {
    @NotBlank(message = "Trạng thái thanh toán không được để trống")
    private String trangThaiTT;

    public UpdatePaymentStatusRequest() {}
    public String getTrangThaiTT() { return trangThaiTT; }
    public void setTrangThaiTT(String trangThaiTT) { this.trangThaiTT = trangThaiTT; }
}
