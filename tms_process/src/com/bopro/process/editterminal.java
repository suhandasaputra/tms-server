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
public class editterminal {

    private static Logger log = Logger.getLogger(editterminal.class);

    public HashMap process(HashMap input) throws UnsupportedEncodingException {
        BackendDBProcess dp = new BackendDBProcess();
        HashMap status = dp.editterminal(
                input.get("terminal_id").toString(),
                input.get("terminal_name").toString(),
                input.get("user_id").toString()
        );
        return status;
    }
}
