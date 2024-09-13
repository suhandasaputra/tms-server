/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bopro.process;

import com.bopro.database.BackendDBProcess;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import org.apache.log4j.Logger;

/**
 *
 * @author suhanda
 */
public class editmerchant {

    private static Logger log = Logger.getLogger(editmerchant.class);

    public HashMap process(HashMap input) throws UnsupportedEncodingException {
        BackendDBProcess dp = new BackendDBProcess();
        HashMap status = dp.editmerchant(
                input.get("merchant_id").toString(),
                input.get("merchant_name").toString(),
                input.get("bidang_usaha").toString(),
                input.get("phonenumber").toString(),
                input.get("user_id").toString()
        );
        return status;
    }
}
