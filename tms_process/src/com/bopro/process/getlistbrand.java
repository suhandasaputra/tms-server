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
public class getlistbrand {

    private static Logger log = Logger.getLogger(getlistbrand.class);
    public HashMap process(HashMap input) {
        BackendDBProcess dp = new BackendDBProcess();
        HashMap result = dp.getlistbrand(input.get("user_id").toString());
        dp = null;
        return result;
    }
}
