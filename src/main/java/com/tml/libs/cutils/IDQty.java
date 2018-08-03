package com.tml.libs.cutils;

/**
 * Created by TML on 10/02/2017.
 */

public class IDQty {
    public String id;
    public int qty;
    public IDQty(String id, int qty) {
        this.id = id;
        this.qty = qty;
    }

    public IDQty(String id) {
        this.id = id;
        qty = 0;
    }
}
