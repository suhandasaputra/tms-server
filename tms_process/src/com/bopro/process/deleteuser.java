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
public class deleteuser {

    private static Logger log = Logger.getLogger(deleteuser.class);

    public HashMap process(HashMap input) {
        BackendDBProcess dp = new BackendDBProcess();
        HashMap status = dp.deleteuser(input.get("merchant_id").toString(), input.get("account_id").toString(), input.get("user_id").toString());
        return status;
    }
}
