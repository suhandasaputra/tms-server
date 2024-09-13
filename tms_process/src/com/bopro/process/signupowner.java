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
public class signupowner {

    private static Logger log = Logger.getLogger(signupowner.class);

    public HashMap process(HashMap input) {
        BackendDBProcess dp = new BackendDBProcess();
        HashMap status = dp.signup(                             
                input.get("bidang_usaha").toString(),
                input.get("username").toString(),
//                input.get("position").toString(),
                input.get("jumlah").toString(),
                input.get("companyname").toString(),
                input.get("phonenumber").toString(),
                input.get("email").toString(),
                input.get("password").toString()
        );
        return status;
    }
}
