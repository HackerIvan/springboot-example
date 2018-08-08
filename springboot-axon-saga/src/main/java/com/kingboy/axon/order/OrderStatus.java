package com.kingboy.axon.order;

/**
 * User kingboy - KingBoyWorld@163.com
 * Date 2018/7/30 13:20
 * Desc
 */
public enum OrderStatus {
    NEW("新建"),FINISHED("完成"),FAILED("失败");

    private String status;

    private OrderStatus(String status) {
        this.status = status;
    }
}
