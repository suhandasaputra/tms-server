/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boserverpos.servlet;

import com.bo.function.JsonProcess;
import com.bo.function.impl.MessageProcessImpl;
import com.bopro.process.loginbackend;
import com.bo.function.MessageProcess;
import com.bo.parameter.FieldParameterMatapos;
import com.bo.parameter.RuleNameParameter;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.bo.parameter.ProcessingCode;
import com.bopro.process.addbrand;
import com.bopro.process.addmerchant;
import com.bopro.process.addterminal;
import com.bopro.process.addtipe;
import com.bopro.process.adduser;
import com.bopro.process.deletebrand;
import com.bopro.process.deletemerchant;
import com.bopro.process.deleteterminal;
import com.bopro.process.deletetipe;
import com.bopro.process.deleteuser;
import com.bopro.process.editbrand;
import com.bopro.process.editmerchant;
import com.bopro.process.editterminal;
import com.bopro.process.edittipe;
import com.bopro.process.edituser;
import com.bopro.process.getdetailbrand;
import com.bopro.process.getdetailmerchant;
import com.bopro.process.getdetailterminal;
import com.bopro.process.getdetailtipe;
import com.bopro.process.getdetailuser;
import com.bopro.process.getlistbrand;
import com.bopro.process.getlistmerchant;
import com.bopro.process.getlistterminal;
import com.bopro.process.getlisttipe;
import com.bopro.process.getlistuser;
import com.bopro.process.optiongetaccountlevel;
import com.bopro.process.optiongetbidangusaha;
import com.bopro.process.optiongetbrand;
import com.bopro.process.optiongetdevicetype;
import com.bopro.process.optiongetmerchant;
import com.bopro.process.signupowner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Level;
import org.json.simple.parser.ParseException;

/**
 *
 * author MATAJARI MITRA SOLUSI
 */
public class BackendProcess extends HttpServlet {

    private MessageProcess mp = new MessageProcessImpl();
    private static Logger log = Logger.getLogger(BackendProcess.class);

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws org.json.simple.parser.ParseException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, java.text.ParseException {
        BufferedReader in = null;
        HashMap input = null;
        String inputString = "";
        String line = "";
        try {
            in = new BufferedReader(new InputStreamReader(request.getInputStream()));
            input = new HashMap();
            while ((line = in.readLine()) != null) {
                inputString += line;
            }

//            matikan line ini saat pakaienkripsi
            inputString = inputString.replace("%20", " ");

            System.out.println("ini input string : " + inputString);
//            untuk pakai enkripsi
//            input = mp.decryptMessage(inputString);
            input = JsonProcess.decodeJson(inputString);
            log.info("\n" + "request Backend process message : " + input + " \n");
            System.out.println("\n" + "request Backend process message : " + input + " \n");
            if (ProcessingCode.loginbackend.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new loginbackend().process(input);
            } else if (ProcessingCode.signup.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new signupowner().process(input);
            } else if (ProcessingCode.optiongetbidangusaha.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new optiongetbidangusaha().process(input);
            } else if (ProcessingCode.optiongetaccountlevel.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new optiongetaccountlevel().process(input);
            } else if (ProcessingCode.optiongetmerchant.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new optiongetmerchant().process(input);
            } else if (ProcessingCode.optiongetbrand.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new optiongetbrand().process(input);
            } else if (ProcessingCode.optiongetdevicetype.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new optiongetdevicetype().process(input);
            } else if (ProcessingCode.getdetailemployee.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new getdetailmerchant().process(input);
            } else if (ProcessingCode.getlistemployee.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new getlistmerchant().process(input);
            } else if (ProcessingCode.addemployee.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new addmerchant().process(input);
            } else if (ProcessingCode.editemployee.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new editmerchant().process(input);
            } else if (ProcessingCode.deleteemployee.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new deletemerchant().process(input);

            } else if (ProcessingCode.getdetailuser.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new getdetailuser().process(input);
            } else if (ProcessingCode.getlistuser.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new getlistuser().process(input);
            } else if (ProcessingCode.adduser.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new adduser().process(input);
            } else if (ProcessingCode.edituser.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new edituser().process(input);
            } else if (ProcessingCode.deleteuser.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new deleteuser().process(input);

            } else if (ProcessingCode.getdetailbrand.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new getdetailbrand().process(input);
            } else if (ProcessingCode.getlistbrand.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new getlistbrand().process(input);
            } else if (ProcessingCode.addbrand.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new addbrand().process(input);
            } else if (ProcessingCode.editbrand.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new editbrand().process(input);
            } else if (ProcessingCode.deletebrand.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new deletebrand().process(input);

            } else if (ProcessingCode.getdetailtipe.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new getdetailtipe().process(input);
            } else if (ProcessingCode.getlisttipe.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new getlisttipe().process(input);
            } else if (ProcessingCode.addtipe.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new addtipe().process(input);
            } else if (ProcessingCode.edittipe.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new edittipe().process(input);
            } else if (ProcessingCode.deletetipe.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new deletetipe().process(input);

            } else if (ProcessingCode.getdetailterminal.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new getdetailterminal().process(input);
            } else if (ProcessingCode.getlistterminal.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new getlistterminal().process(input);
            } else if (ProcessingCode.addterminal.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new addterminal().process(input);
            } else if (ProcessingCode.editterminal.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new editterminal().process(input);
            } else if (ProcessingCode.deleteterminal.equals(input.get(FieldParameterMatapos.proccode).toString())) {
                input = new deleteterminal().process(input);
            }

        } catch (IOException e) {
            input.put(RuleNameParameter.resp_code, "0030");
            input.put(RuleNameParameter.resp_desc, "Error message request");
        }

//        untuk pakai enkripsi
//        response.getOutputStream().write(mp.encryptMessage(input).getBytes());
        response.getOutputStream().write(JsonProcess.generateJson(input).getBytes());
        response.getOutputStream().flush();
        System.out.println(
                "\n" + "response FROM Backend process message TO Web : " + input + " \n");
        log.info(
                "\n" + "response FROM Backend process message TO Web : " + input + " \n");

        in = null;
        input = null;
        inputString = "";
        line = "";
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);

        } catch (ParseException | java.text.ParseException ex) {
            java.util.logging.Logger.getLogger(BackendProcess.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);

        } catch (ParseException | java.text.ParseException ex) {
            java.util.logging.Logger.getLogger(BackendProcess.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}
