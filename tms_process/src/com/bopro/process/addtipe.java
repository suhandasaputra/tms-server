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
public class addtipe {

    private static Logger log = Logger.getLogger(addtipe.class);

    public HashMap process(HashMap input) throws UnsupportedEncodingException {
        BackendDBProcess dp = new BackendDBProcess();
        HashMap status = dp.addtipe(
                input.get("brand_id").toString(),
                input.get("device_type").toString(),
                input.get("user_id").toString()
        );
        return status;
    }
}
