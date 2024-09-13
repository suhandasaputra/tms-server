/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bopro.process;

import com.bopro.database.BackendDBProcess;
import java.util.HashMap;
import org.apache.log4j.Logger;

/**
 *
 * @author suhanda
 */
public class optiongetdevicetype {

    private static Logger log = Logger.getLogger(optiongetdevicetype.class);

    public HashMap process(HashMap input) {
        BackendDBProcess dp = new BackendDBProcess();
        HashMap result = dp.optiongetdevicetype(input.get("user_id").toString(), input.get("brand_id").toString());
        System.out.println("ini dia bro__________________ : "+input.get("brand_id").toString());
        return result;
    }
}
