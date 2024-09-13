/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bopro.database;

import com.bopro.singleton.DatasourceEntryBackend;
import com.bo.parameter.FieldParameterMatapos;
import com.bo.parameter.RuleNameParameter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;

public class BackendDBProcess {

    private static final Logger log = Logger.getLogger(BackendDBProcess.class);

    private void clearStatment(PreparedStatement stat) {
//        log.info("stat 2 : " + stat);
        if (stat != null) {
            try {
//                log.info("stat A");
                stat.clearBatch();
//                log.info("stat B");
                stat.clearParameters();
//                log.info("stat C");
                stat.close();
//                log.info("stat D");
                stat = null;
//                log.info("stat E");
            } catch (SQLException ex) {
//                log.error("clearStatment : " +ex.getMessage());
//                ex.printStackTrace();
            }
        }
    }

    private void clearDBConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException ex) {
//                log.error("clearDBConnection : "+ex.getMessage());
            }
        }
    }

    private void clearResultset(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
                rs = null;
            } catch (SQLException ex) {
//                log.error("clearResultset : "+ex.getMessage());
            }
        }
    }

    private void clearAllConnStatRS(Connection conn, PreparedStatement stat, ResultSet rs) {
        clearResultset(rs);
        clearStatment(stat);
        clearDBConnection(conn);
    }

    public boolean getEODDone() {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            stat = conn.prepareStatement("SELECT eoddone FROM configapp");
            rs = stat.executeQuery();
            while (rs.next()) {
                return rs.getBoolean("eoddone");
            }
        } catch (SQLException ex) {
//            ex.printStackTrace();
            log.error("getMessageIncoming : " + ex.getMessage());

        } finally {
            clearResultset(rs);
            clearStatment(stat);
        }
        return false;
    }

    public boolean updateEODDone() {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            stat = conn.prepareStatement("UPDATE configapp SET eoddone=false");
            stat.executeUpdate();

        } catch (SQLException ex) {
//            ex.printStackTrace();
            log.error("updateEODDone : " + ex.getMessage());

        } finally {
            clearResultset(rs);
            clearStatment(stat);
        }
        return false;
    }

//    public String BrandSeq() {
//        Connection conn = null;
//        PreparedStatement stat = null;
//        ResultSet rs = null;
//        try {
//            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
//            stat = conn.prepareStatement("SELECT nextval('brand_seq'::regclass)");
//            rs = stat.executeQuery();
//            while (rs.next()) {
//                return StringFunction.pad(rs.getString("nextval"), 2, "0", "Right");
//            }
//
//        } catch (SQLException ex) {
//            log.error("getListOrderTransactionPOS : " + ex.getMessage());
//        } finally {
//            clearAllConnStatRS(conn, stat, rs);
//        }
//        return "00";
//    }
//    public void setNextStan() {
//        Connection conn = null;
//        PreparedStatement stat = null;
//        ResultSet rs = null;
//        try {
//            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
//            stat = conn.prepareStatement("SELECT count(*) as count from stanmanage where standate = current_date");
//            rs = stat.executeQuery();
//            while (rs.next()) {
//                if (rs.getInt("count") == 0) {
//                    log.info("create next stan");
//                    clearStatment(stat);
//                    stat = conn.prepareStatement("INSERT INTO stanmanage(standate) VALUES (current_date)");
//                    stat.executeUpdate();
//                }
//            }
//        } catch (SQLException ex) {
//            log.error(ex.getMessage());
//        } finally {
//            clearAllConnStatRS(conn, stat, rs);
//        }
//    }
//
//    public String getNextStan() {
//        Connection conn = null;
//        PreparedStatement stat = null;
//        ResultSet rs = null;
//        int currentStan = 1;
//        try {
//            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
//            stat = conn.prepareStatement("SELECT stanno FROM stanmanage where standate = current_date");
//            rs = stat.executeQuery();
//            while (rs.next()) {
//                currentStan = rs.getInt("stanno");
//            }
//            clearStatment(stat);
//            stat = conn.prepareStatement("UPDATE stanmanage SET stanno=? WHERE standate=current_date;");
//            stat.setInt(1, currentStan + 1);
//            stat.executeUpdate();
//        } catch (SQLException ex) {
//            log.error("getNextStan : " + ex.getMessage());
//        } finally {
//            clearAllConnStatRS(conn, stat, rs);
//        }
//        return String.valueOf(currentStan);
//    }
    public HashMap signup(String bidang_usaha, String username, String jumlah, String companyname,
            String phonenumber, String email, String password) {
        HashMap result = new HashMap();
        Connection conn = null;
        PreparedStatement stat = null;
        PreparedStatement stat1 = null;
        ResultSet rs = null;

        result.put(RuleNameParameter.resp_code, "0001");
        result.put(RuleNameParameter.resp_desc, "Failed");
        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            stat = conn.prepareStatement("select * from tms_account where account_id = ?");
            stat.setString(1, email);
            rs = stat.executeQuery();
            if (rs.next()) {
                result.put(RuleNameParameter.resp_code, "0004");
                result.put(RuleNameParameter.resp_desc, "duplicate, already exist");
            } else {
                stat1 = conn.prepareStatement("insert into tms_account (account_id, account_level, account_name, password, company_id) values (?, ?, ?, ?, ?)");
                stat1.setString(1, email);
                stat1.setString(2, "1");
                stat1.setString(3, username);
                stat1.setString(4, password);
                stat1.setString(5, email);
                stat1.executeUpdate();
                stat1.close();

                stat1 = conn.prepareStatement("insert into int_company (company_id, company_name, phonenumber, bidang_usaha, jumlah_karyawan) values (?, ?, ?, ?, ?)");
                stat1.setString(1, email);
                stat1.setString(2, companyname);
                stat1.setInt(3, Integer.parseInt(phonenumber));
                stat1.setInt(4, Integer.parseInt(bidang_usaha));
                stat1.setInt(5, Integer.parseInt(jumlah));
                stat1.executeUpdate();

                result.put(RuleNameParameter.resp_code, "0000");
                result.put(RuleNameParameter.resp_desc, "Success");

            }
        } catch (SQLException e) {
            System.out.println(e);
            return result;
        } finally {
            clearAllConnStatRS(conn, stat, rs);
            clearStatment(stat1);
        }
        return result;
    }

    public HashMap<String, String> validate(String user_id, String password) {
        HashMap result = null;
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            result = new HashMap();
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            stat = conn.prepareStatement("select a.*, b.level_desc from tms_account a \n"
                    + "left join tms_account_level b on a.account_level = b.id_level::varchar \n"
                    + "where a.account_id = ? and a.account_status = '1'");
            stat.setString(1, user_id);
            rs = stat.executeQuery();
            if (rs.next()) {
                if (password.equals(rs.getString("password"))) {
                    result.put("account_id", rs.getString("account_id"));
                    result.put("account_name", rs.getString("account_name"));
                    result.put("account_level", rs.getString("account_level"));
                    result.put("level_desc", rs.getString("level_desc"));
                    result.put("merchant_id", rs.getString("merchant_id"));
                    result.put(FieldParameterMatapos.resp_code, "0000");
                    result.put(FieldParameterMatapos.resp_desc, "Success");
                } else {
                    result.put(FieldParameterMatapos.resp_code, "0005");
                    result.put(FieldParameterMatapos.resp_desc, "password not match");
                }
            } else {
                result.put(FieldParameterMatapos.resp_code, "0002");
                result.put(FieldParameterMatapos.resp_desc, "not found");
            }

        } catch (SQLException e) {
            result.put(FieldParameterMatapos.resp_code, "0001");
            result.put(FieldParameterMatapos.resp_desc, "Error : " + e.getMessage());
            System.out.println(e);
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap optiongetbidangusaha() {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        HashMap result = new HashMap();
        List l = new ArrayList();
        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            stat = conn.prepareStatement("select * from tms_bidang_usaha where usaha_status = '1'");
            rs = stat.executeQuery();
            while (rs.next()) {
                HashMap ab = new HashMap();
                ab.put("id_usaha", rs.getString("id_usaha"));
                ab.put("usaha_desc", rs.getString("usaha_desc"));
                l.add(ab);
                ab = null;
            }
            result.put("list", l);

            result.put("resp_code", "0000");
            result.put("resp_desc", "success");
        } catch (SQLException e) {
            result.put("resp_code", "0001");
            result.put("resp_desc", "Error : " + e.getMessage());
            System.out.println("error : " + e);
        } finally {
            l = null;
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap optiongetaccountlevel(String merchant_id) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        HashMap result = new HashMap();
        List l = new ArrayList();
        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            if (merchant_id.equals("Admin")) {
                stat = conn.prepareStatement("select * from tms_account_level where status='1'");
            } else {
                stat = conn.prepareStatement("select * from tms_account_level where status='1' and id_level != 1");
            }
            rs = stat.executeQuery();
            while (rs.next()) {
                HashMap ab = new HashMap();
                ab.put("id_level", rs.getString("id_level"));
                ab.put("level_desc", rs.getString("level_desc"));
                l.add(ab);
                ab = null;
            }
            result.put("list", l);

            result.put("resp_code", "0000");
            result.put("resp_desc", "success");
        } catch (SQLException e) {
            result.put("resp_code", "0001");
            result.put("resp_desc", "Error : " + e.getMessage());
            System.out.println("error : " + e);
        } finally {
            l = null;
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap optiongetmerchant() {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        HashMap result = new HashMap();
        List l = new ArrayList();
        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            stat = conn.prepareStatement("select * from tms_merchant where status = '1'");
            rs = stat.executeQuery();
            while (rs.next()) {
                HashMap ab = new HashMap();
                ab.put("merchant_id", rs.getString("merchant_id"));
                ab.put("merchant_name", rs.getString("merchant_name"));
                l.add(ab);
                ab = null;
            }
            result.put("list", l);

            result.put("resp_code", "0000");
            result.put("resp_desc", "success");
        } catch (SQLException e) {
            result.put("resp_code", "0001");
            result.put("resp_desc", "Error : " + e.getMessage());
            System.out.println("error : " + e);
        } finally {
            l = null;
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap optiongetbrand() {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        HashMap result = new HashMap();
        List l = new ArrayList();
        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            stat = conn.prepareStatement("select * from tms_brand where status = '1'");
            rs = stat.executeQuery();
            while (rs.next()) {
                HashMap ab = new HashMap();
                ab.put("brand_id", rs.getString("brand_id"));
                ab.put("brand_name", rs.getString("brand_name"));
                l.add(ab);
                ab = null;
            }
            result.put("list", l);

            result.put("resp_code", "0000");
            result.put("resp_desc", "success");
        } catch (SQLException e) {
            result.put("resp_code", "0001");
            result.put("resp_desc", "Error : " + e.getMessage());
            System.out.println("error : " + e);
        } finally {
            l = null;
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap optiongetdevicetype(String user_id, String brand_id) {
        System.out.println("masuk");
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        HashMap result = new HashMap();
        List l = new ArrayList();
        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            if ("0".equals(brand_id)) {
                System.out.println("11111111");
                stat = conn.prepareStatement("select * from tms_device_type where status = '1'");
            } else {
                System.out.println("22222222");
                stat = conn.prepareStatement("select * from tms_device_type where brand_id = '"+brand_id+"' and status = '1'");
            }
            rs = stat.executeQuery();
            while (rs.next()) {
                HashMap ab = new HashMap();
                ab.put("tipe_id", rs.getString("tipe_id"));
                ab.put("device_type", rs.getString("device_type"));
                l.add(ab);
                ab = null;
            }
            result.put("list", l);

            result.put("resp_code", "0000");
            result.put("resp_desc", "success");
        } catch (SQLException e) {
            result.put("resp_code", "0001");
            result.put("resp_desc", "Error : " + e.getMessage());
            System.out.println("error : " + e);
        } finally {
            l = null;
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap getlistmerchant(String merchant_id) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        HashMap result = new HashMap();
        List list = new ArrayList();
        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            stat = conn.prepareStatement("select a.*, b.usaha_desc from tms_merchant a left join tms_bidang_usaha b on a.bidang_usaha = b.id_usaha where a.status = '1'");
//            stat.setString(1, merchant_id);
            rs = stat.executeQuery();
            while (rs.next()) {
                HashMap ab = new HashMap();
                ab.put("merchant_id", rs.getString("merchant_id"));
                ab.put("merchant_name", rs.getString("merchant_name"));
                ab.put("usaha_desc", rs.getString("usaha_desc"));
                ab.put("phonenumber", rs.getLong("phonenumber"));
                ab.put("status", rs.getString("status"));
                ab.put("create_date", rs.getString("create_date"));
                ab.put("create_by", rs.getString("create_by"));
                ab.put("update_date", rs.getString("update_date"));
                ab.put("update_by", rs.getString("update_by"));

                list.add(ab);
                ab = null;
            }
            result.put("resp_code", "0000");
            result.put("resp_desc", "success");

            result.put("list", list);
        } catch (SQLException e) {
            result.put("resp_code", "0001");
            result.put("resp_desc", "Error : " + e.getMessage());
            System.out.println("error : " + e);
        } finally {
            list = null;
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap addmerchant(
            String merchant_id,
            String user_id,
            String merchant_name,
            String bidang_usaha,
            String phone_number) {
        HashMap result = new HashMap();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            stat = conn.prepareStatement("select * from tms_merchant where merchant_id = ?");
            stat.setString(1, merchant_id);
            rs = stat.executeQuery();
            if (rs.next()) {
                result.put(RuleNameParameter.resp_code, "0002");
                result.put(RuleNameParameter.resp_desc, merchant_id + " already exists");
            } else {
                stat.close();
                stat = conn.prepareStatement("insert into tms_merchant (merchant_id, merchant_name, bidang_usaha, phonenumber, create_by) values (?,?,?,?,?)");
                stat.setString(1, merchant_id);
                stat.setString(2, merchant_name);
                stat.setString(3, bidang_usaha);
                stat.setString(4, phone_number);
                stat.setString(5, user_id);
                stat.executeUpdate();
                result.put(RuleNameParameter.resp_code, "0000");
                result.put(RuleNameParameter.resp_desc, "Success");
            }
        } catch (SQLException e) {
            result.put(RuleNameParameter.resp_code, "0001");
            result.put(RuleNameParameter.resp_desc, "Error : " + e.getMessage());
            System.out.println(e);
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap editmerchant(
            String merchant_id,
            String merchant_name,
            String bidang_usaha,
            String phonenumber,
            String user_id) {
        HashMap result = new HashMap();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            stat = conn.prepareStatement("update tms_merchant set "
                    + "merchant_name = ?, "
                    + "bidang_usaha = ?, "
                    + "phonenumber = ?, "
                    + "update_date = ?, "
                    + "update_by = ? "
                    + "where merchant_id = ? and status = '1'");
            stat.setString(1, merchant_name);
            stat.setString(2, bidang_usaha);
            stat.setString(3, phonenumber);
            stat.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            stat.setString(5, user_id);
            stat.setString(6, merchant_id);
            stat.executeUpdate();
            result.put(RuleNameParameter.resp_code, "0000");
            result.put(RuleNameParameter.resp_desc, "Success");
        } catch (SQLException e) {
            System.out.println(e);
            result.put(RuleNameParameter.resp_code, "0001");
            result.put(RuleNameParameter.resp_desc, "Error : " + e.getMessage());
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap deletemerchant(String merchant_id, String user_id) {
        HashMap result = new HashMap();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            stat = conn.prepareStatement("update tms_merchant set status='0', update_date=?, update_by=? where merchant_id=?");
            stat.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stat.setString(2, user_id);
            stat.setString(3, merchant_id);

            stat.executeUpdate();

            result.put(RuleNameParameter.resp_code, "0000");
            result.put(RuleNameParameter.resp_desc, "Success");

        } catch (SQLException e) {
            result.put(RuleNameParameter.resp_code, "0001");
            result.put(RuleNameParameter.resp_desc, "Error : " + e.getMessage());
            System.out.println(e);
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap getdetailmerchant(String user_id, String merchant_id) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        HashMap result = new HashMap();
        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            stat = conn.prepareStatement("select a.*, b.usaha_desc from tms_merchant a \n"
                    + "left join tms_bidang_usaha b on a.bidang_usaha = b.id_usaha "
                    + "where a.merchant_id = ? ");
            stat.setString(1, merchant_id);
            rs = stat.executeQuery();
            if (rs.next()) {

                result.put("merchant_id", rs.getString("merchant_id"));
                result.put("merchant_name", rs.getString("merchant_name"));
                result.put("status", rs.getString("status"));
                result.put("bidang_usaha", rs.getString("bidang_usaha"));
                result.put("usaha_desc", rs.getString("usaha_desc"));
                result.put("phonenumber", rs.getString("phonenumber"));
                result.put("create_date", rs.getString("create_date"));
                result.put("create_by", rs.getString("create_by"));
                result.put("update_date", rs.getString("update_date"));
                result.put("update_by", rs.getString("update_by"));

                result.put("resp_code", "0000");
                result.put("resp_desc", "success");
            } else {
                result.put(FieldParameterMatapos.resp_code, "0002");
                result.put(FieldParameterMatapos.resp_desc, "not found");
            }
        } catch (SQLException e) {
            result.put("resp_code", "0001");
            result.put("resp_desc", "Error : " + e.getMessage());
            System.out.println("error : " + e);
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap getlistuser(String merchant_id) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        HashMap result = new HashMap();
        List list = new ArrayList();
        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            if (merchant_id.equals("admin")) {
                stat = conn.prepareStatement("select a.*, b.level_desc from tms_account a left join tms_account_level b on a.account_level = b.id_level::varchar where a.account_status = '1'");
            } else {
                stat = conn.prepareStatement("select a.*, b.level_desc from tms_account a left join tms_saccount_level b on a.account_level = b.id_level::varchar where a.account_status = '1' and a.merchant_id = ?");
                stat.setString(1, merchant_id);
            }
            rs = stat.executeQuery();
            while (rs.next()) {
                HashMap ab = new HashMap();
                ab.put("account_id", rs.getString("account_id"));
                ab.put("account_level", rs.getString("account_level"));
                ab.put("account_name", rs.getString("account_name"));
                ab.put("level_desc", rs.getString("level_desc"));
                ab.put("merchant_id", rs.getString("merchant_id"));
                ab.put("create_date", rs.getString("create_date"));
                ab.put("create_by", rs.getString("create_by"));
                ab.put("update_date", rs.getString("update_date"));
                ab.put("update_by", rs.getString("update_by"));
                list.add(ab);
                ab = null;
            }
            result.put("resp_code", "0000");
            result.put("resp_desc", "success");

            result.put("list", list);
        } catch (SQLException e) {
            result.put("resp_code", "0001");
            result.put("resp_desc", "Error : " + e.getMessage());
            System.out.println("error : " + e);
        } finally {
            list = null;
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap adduser(
            String merchant_id,
            String user_id,
            String account_email,
            String account_name,
            String account_level,
            String password) {
        HashMap result = new HashMap();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            stat = conn.prepareStatement("select * from tms_account where account_id = ?");
            stat.setString(1, account_email);
            rs = stat.executeQuery();
            if (rs.next()) {
                result.put(RuleNameParameter.resp_code, "0002");
                result.put(RuleNameParameter.resp_desc, account_email + " already exists");
            } else {
                stat.close();
                stat = conn.prepareStatement("insert into tms_account (account_id,account_level,account_name,password,merchant_id,create_by) values (?,?,?,?,?,?)");
                stat.setString(1, account_email);
                stat.setString(2, account_level);
                stat.setString(3, account_name);
                stat.setString(4, password);
                stat.setString(5, merchant_id);
                stat.setString(6, user_id);
                stat.executeUpdate();
                result.put(RuleNameParameter.resp_code, "0000");
                result.put(RuleNameParameter.resp_desc, "Success");
            }
        } catch (SQLException e) {
            result.put(RuleNameParameter.resp_code, "0001");
            result.put(RuleNameParameter.resp_desc, "Error : " + e.getMessage());
            System.out.println(e);
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap<String, String> edituser(
            String merchant_id,
            String account_id,
            String account_name,
            String account_level,
            String user_id
    ) {
        HashMap<String, String> result = new HashMap<>();
        String sql = "UPDATE tms_account SET "
                + "account_level = ?, "
                + "account_name = ?, "
                + "update_by = ?, "
                + "update_date = ? "
                + "WHERE merchant_id = ? AND account_id = ?";

        // Use try-with-resources to manage resources automatically
        try (Connection conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection(); PreparedStatement stat = conn.prepareStatement(sql)) {

            // Disable auto-commit to manage transactions manually
            conn.setAutoCommit(false);

            // Set parameters for the prepared statement
            stat.setString(1, account_level);
            stat.setString(2, account_name);
            stat.setString(3, user_id);
            stat.setTimestamp(4, Timestamp.from(Instant.now()));
            stat.setString(5, merchant_id);
            stat.setString(6, account_id);

            // Execute update and commit transaction if successful
            int rowsAffected = stat.executeUpdate();
            if (rowsAffected > 0) {
                conn.commit();
                result.put("resp_code", "0000");
                result.put("resp_desc", "Success");
            } else {
                conn.rollback();
                result.put("resp_code", "0002");
                result.put("resp_desc", "No rows updated");
            }

        } catch (SQLException e) {
            // Handle SQL exception and rollback transaction if necessary
            try {
                Connection conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            result.put("resp_code", "0001");
            result.put("resp_desc", "Error: " + e.getMessage());
        }

        return result;
    }

    public HashMap deleteuser(String merchant_id, String account_id, String user_id) {
        HashMap result = new HashMap();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            stat = conn.prepareStatement("update tms_account set account_status='0', update_date=?, update_by=? where merchant_id=? and account_id=?");
            stat.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stat.setString(2, user_id);
            stat.setString(3, merchant_id);
            stat.setString(4, account_id);
            stat.executeUpdate();
            result.put(RuleNameParameter.resp_code, "0000");
            result.put(RuleNameParameter.resp_desc, "Success");
        } catch (SQLException e) {
            result.put(RuleNameParameter.resp_code, "0001");
            result.put(RuleNameParameter.resp_desc, "Error : " + e.getMessage());
            System.out.println(e);
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap getdetailuser(String account_id, String merchant_id) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        HashMap result = new HashMap();
        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            stat = conn.prepareStatement("select a.*, b.level_desc from tms_account a \n"
                    + "left join tms_account_level b on a.account_level = b.id_level::varchar \n"
                    + "where merchant_id = ? and account_id = ?");
            stat.setString(1, merchant_id);
            stat.setString(2, account_id);
            rs = stat.executeQuery();
            if (rs.next()) {
                result.put("account_id", rs.getString("account_id"));
                result.put("account_level", rs.getString("account_level"));
                result.put("account_name", rs.getString("account_name"));
                result.put("level_desc", rs.getString("level_desc"));
                result.put("create_date", rs.getString("create_date"));
                result.put("merchant_id", rs.getString("merchant_id"));
                result.put("resp_code", "0000");
                result.put("resp_desc", "success");
            } else {
                result.put(FieldParameterMatapos.resp_code, "0002");
                result.put(FieldParameterMatapos.resp_desc, "not found");
            }

        } catch (SQLException e) {
            result.put("resp_code", "0001");
            result.put("resp_desc", "Error : " + e.getMessage());
            System.out.println("error : " + e);
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap getlistbrand(String user_id) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        HashMap result = new HashMap();
        List list = new ArrayList();
        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            stat = conn.prepareStatement("select * from tms_brand where status = '1'");
            rs = stat.executeQuery();
            while (rs.next()) {
                HashMap ab = new HashMap();
                ab.put("brand_id", rs.getString("brand_id"));
                ab.put("brand_name", rs.getString("brand_name"));
                ab.put("status", rs.getString("status"));
                ab.put("create_date", rs.getString("create_date"));
                ab.put("create_by", rs.getString("create_by"));
                ab.put("update_date", rs.getString("update_date"));
                ab.put("update_by", rs.getString("update_by"));
                list.add(ab);
                ab = null;
            }
            result.put("resp_code", "0000");
            result.put("resp_desc", "success");

            result.put("list", list);
        } catch (SQLException e) {
            result.put("resp_code", "0001");
            result.put("resp_desc", "Error : " + e.getMessage());
            System.out.println("error : " + e);
        } finally {
            list = null;
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap<String, String> addbrand(
            String user_id,
            String brand_id,
            String brand_name) {
        HashMap<String, String> result = new HashMap<>();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            conn.setAutoCommit(false);  // Mulai transaksi

            // Query untuk mengecek apakah brand_id sudah ada
            stat = conn.prepareStatement("select * from tms_brand where brand_id = ?");
            stat.setString(1, brand_id);
            rs = stat.executeQuery();

            if (rs.next()) {
                result.put(RuleNameParameter.resp_code, "0002");
                result.put(RuleNameParameter.resp_desc, brand_id + " already exists");
            } else {
                // Jika brand_id belum ada, maka insert
                stat.close();  // Tutup PreparedStatement sebelumnya
                stat = conn.prepareStatement("insert into tms_brand (brand_id, brand_name, create_by) values (?,?,?)");
                stat.setString(1, brand_id);
                stat.setString(2, brand_name);
                stat.setString(3, user_id);
                stat.executeUpdate();

                conn.commit();  // Commit transaksi setelah insert berhasil

                result.put(RuleNameParameter.resp_code, "0000");
                result.put(RuleNameParameter.resp_desc, "Success");
            }
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();  // Rollback transaksi jika terjadi kesalahan
                } catch (SQLException rollbackEx) {
                    System.out.println("Rollback failed: " + rollbackEx.getMessage());
                }
            }
            result.put(RuleNameParameter.resp_code, "0001");
            result.put(RuleNameParameter.resp_desc, "Error : " + e.getMessage());
            System.out.println(e);
        } finally {
            clearAllConnStatRS(conn, stat, rs);  // Tutup semua koneksi, statement, dan result set
        }
        return result;
    }

    public HashMap<String, String> editbrand(
            String brand_id,
            String brand_name,
            String user_id
    ) {
        HashMap<String, String> result = new HashMap<>();
        Connection conn = null;
        PreparedStatement stat = null;

        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            conn.setAutoCommit(false);  // Memulai transaksi

            stat = conn.prepareStatement("update tms_brand set "
                    + "brand_name = ?, "
                    + "update_by = ?, "
                    + "update_date = ? "
                    + "where brand_id = ?");
            stat.setString(1, brand_name);
            stat.setString(2, user_id);
            stat.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            stat.setString(4, brand_id);

            int rowsUpdated = stat.executeUpdate();
            if (rowsUpdated > 0) {
                conn.commit();  // Melakukan commit jika update berhasil
                result.put(RuleNameParameter.resp_code, "0000");
                result.put(RuleNameParameter.resp_desc, "Success");
            } else {
                conn.rollback();  // Rollback jika tidak ada baris yang diupdate
                result.put(RuleNameParameter.resp_code, "0003");
                result.put(RuleNameParameter.resp_desc, "Brand ID not found");
            }
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();  // Rollback jika terjadi kesalahan
                } catch (SQLException rollbackEx) {
                    System.out.println("Rollback failed: " + rollbackEx.getMessage());
                }
            }
            System.out.println(e);
            result.put(RuleNameParameter.resp_code, "0001");
            result.put(RuleNameParameter.resp_desc, "Error : " + e.getMessage());
        } finally {
            clearAllConnStatRS(conn, stat, null);
        }
        return result;
    }

    public HashMap<String, String> deletebrand(String brand_id, String user_id) {
        HashMap<String, String> result = new HashMap<>();
        Connection conn = null;
        PreparedStatement stat = null;

        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            conn.setAutoCommit(false);  // Memulai transaksi

            // Mengatur status menjadi '0' (menandakan penghapusan) dan memperbarui informasi
            stat = conn.prepareStatement("update tms_brand set status = '0', update_by = ?, update_date = ? where brand_id = ?");
            stat.setString(1, user_id);
            stat.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stat.setString(3, brand_id);

            int rowsUpdated = stat.executeUpdate();
            if (rowsUpdated > 0) {
                conn.commit();  // Commit jika update berhasil
                result.put(RuleNameParameter.resp_code, "0000");
                result.put(RuleNameParameter.resp_desc, "Success");
            } else {
                conn.rollback();  // Rollback jika tidak ada baris yang diupdate
                result.put(RuleNameParameter.resp_code, "0003");
                result.put(RuleNameParameter.resp_desc, "Brand ID not found");
            }
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();  // Rollback jika terjadi kesalahan
                } catch (SQLException rollbackEx) {
                    System.out.println("Rollback failed: " + rollbackEx.getMessage());
                }
            }
            result.put(RuleNameParameter.resp_code, "0001");
            result.put(RuleNameParameter.resp_desc, "Error : " + e.getMessage());
            System.out.println(e);
        } finally {
            clearAllConnStatRS(conn, stat, null);  // Menutup koneksi dan statement
        }
        return result;
    }

    public HashMap<String, String> getdetailbrand(String brand_id, String user_id) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        HashMap<String, String> result = new HashMap<>();

        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();

            stat = conn.prepareStatement("select * from tms_brand where brand_id = ? and status = ?");
            stat.setString(1, brand_id);
            stat.setString(2, "1");  // Status '1' menandakan aktif

            rs = stat.executeQuery();
            if (rs.next()) {
                result.put("brand_id", rs.getString("brand_id"));
                result.put("brand_name", rs.getString("brand_name"));
                result.put("status", rs.getString("status"));
                result.put("create_date", rs.getString("create_date"));
                result.put("create_by", rs.getString("create_by"));
                result.put("update_date", rs.getString("update_date"));
                result.put("update_by", rs.getString("update_by"));
                result.put("resp_code", "0000");
                result.put("resp_desc", "Success");
            } else {
                result.put("resp_code", "0002");
                result.put("resp_desc", "Brand not found");
            }
        } catch (SQLException e) {
            result.put("resp_code", "0001");
            result.put("resp_desc", "Error: " + e.getMessage());
            System.out.println("Error: " + e);
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }

        return result;
    }

    public HashMap getlisttipe(String user_id) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        HashMap result = new HashMap();
        List list = new ArrayList();
        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            stat = conn.prepareStatement("select * from tms_device_type where status = '1'");
            rs = stat.executeQuery();
            while (rs.next()) {
                HashMap ab = new HashMap();
                ab.put("tipe_id", rs.getString("tipe_id"));
                ab.put("device_type", rs.getString("device_type"));
                ab.put("brand_id", rs.getString("brand_id"));
                ab.put("status", rs.getString("status"));
                ab.put("create_date", rs.getString("create_date"));
                ab.put("create_by", rs.getString("create_by"));
                ab.put("update_date", rs.getString("update_date"));
                ab.put("update_by", rs.getString("update_by"));
                list.add(ab);
                ab = null;
            }
            result.put("resp_code", "0000");
            result.put("resp_desc", "success");

            result.put("list", list);
        } catch (SQLException e) {
            result.put("resp_code", "0001");
            result.put("resp_desc", "Error : " + e.getMessage());
            System.out.println("error : " + e);
        } finally {
            list = null;
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap<String, String> addtipe(
            String brand_id,
            String device_type,
            String user_id) {
        HashMap<String, String> result = new HashMap<>();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            conn.setAutoCommit(false);  // Mulai transaksi

            // Query untuk mengecek apakah brand_id sudah ada
            stat = conn.prepareStatement("select * from tms_device_type where brand_id = ? and device_type = ?");
            stat.setString(1, brand_id);
            stat.setString(2, device_type);
            rs = stat.executeQuery();

            if (rs.next()) {
                result.put(RuleNameParameter.resp_code, "0002");
                result.put(RuleNameParameter.resp_desc, device_type + " already exists");
            } else {
                // Jika brand_id belum ada, maka insert
                stat.close();  // Tutup PreparedStatement sebelumnya
                stat = conn.prepareStatement("insert into tms_device_type (device_type, brand_id, create_by) values (?,?,?)");
                stat.setString(1, device_type);
                stat.setString(2, brand_id);
                stat.setString(3, user_id);
                stat.executeUpdate();

                conn.commit();  // Commit transaksi setelah insert berhasil

                result.put(RuleNameParameter.resp_code, "0000");
                result.put(RuleNameParameter.resp_desc, "Success");
            }
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();  // Rollback transaksi jika terjadi kesalahan
                } catch (SQLException rollbackEx) {
                    System.out.println("Rollback failed: " + rollbackEx.getMessage());
                }
            }
            result.put(RuleNameParameter.resp_code, "0001");
            result.put(RuleNameParameter.resp_desc, "Error : " + e.getMessage());
            System.out.println(e);
        } finally {
            clearAllConnStatRS(conn, stat, rs);  // Tutup semua koneksi, statement, dan result set
        }
        return result;
    }

    public HashMap<String, String> edittipe(
            String tipe_id,
            String device_type,
            String user_id
    ) {
        HashMap<String, String> result = new HashMap<>();
        Connection conn = null;
        PreparedStatement stat = null;

        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            conn.setAutoCommit(false);  // Memulai transaksi

            stat = conn.prepareStatement("update tms_device_type set "
                    + "device_type = ?, "
                    + "update_by = ?, "
                    + "update_date = ? "
                    + "where tipe_id = ?");
            stat.setString(1, device_type);
            stat.setString(2, user_id);
            stat.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            stat.setLong(4, Long.parseLong(tipe_id));

            int rowsUpdated = stat.executeUpdate();
            if (rowsUpdated > 0) {
                conn.commit();  // Melakukan commit jika update berhasil
                result.put(RuleNameParameter.resp_code, "0000");
                result.put(RuleNameParameter.resp_desc, "Success");
            } else {
                conn.rollback();  // Rollback jika tidak ada baris yang diupdate
                result.put(RuleNameParameter.resp_code, "0003");
                result.put(RuleNameParameter.resp_desc, "Device ID not found");
            }
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();  // Rollback jika terjadi kesalahan
                } catch (SQLException rollbackEx) {
                    System.out.println("Rollback failed: " + rollbackEx.getMessage());
                }
            }
            System.out.println(e);
            result.put(RuleNameParameter.resp_code, "0001");
            result.put(RuleNameParameter.resp_desc, "Error : " + e.getMessage());
        } finally {
            clearAllConnStatRS(conn, stat, null);
        }
        return result;
    }

    public HashMap<String, String> deletetipe(String tipe_id, String user_id) {
        HashMap<String, String> result = new HashMap<>();
        Connection conn = null;
        PreparedStatement stat = null;

        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            conn.setAutoCommit(false);  // Memulai transaksi

            // Mengatur status menjadi '0' (menandakan penghapusan) dan memperbarui informasi
            stat = conn.prepareStatement("update tms_device_type set status = '0', update_by = ?, update_date = ? where tipe_id = ?");
            stat.setString(1, user_id);
            stat.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stat.setLong(3, Long.parseLong(tipe_id));

            int rowsUpdated = stat.executeUpdate();
            if (rowsUpdated > 0) {
                conn.commit();  // Commit jika update berhasil
                result.put(RuleNameParameter.resp_code, "0000");
                result.put(RuleNameParameter.resp_desc, "Success");
            } else {
                conn.rollback();  // Rollback jika tidak ada baris yang diupdate
                result.put(RuleNameParameter.resp_code, "0003");
                result.put(RuleNameParameter.resp_desc, "device ID not found");
            }
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();  // Rollback jika terjadi kesalahan
                } catch (SQLException rollbackEx) {
                    System.out.println("Rollback failed: " + rollbackEx.getMessage());
                }
            }
            result.put(RuleNameParameter.resp_code, "0001");
            result.put(RuleNameParameter.resp_desc, "Error : " + e.getMessage());
            System.out.println(e);
        } finally {
            clearAllConnStatRS(conn, stat, null);  // Menutup koneksi dan statement
        }
        return result;
    }

    public HashMap<String, String> getdetailtipe(String tipe_id, String user_id) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        HashMap<String, String> result = new HashMap<>();

        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            stat = conn.prepareStatement("select a.*, b.brand_name from tms_device_type a "
                    + "left join tms_brand b on a.brand_id = b.brand_id "
                    + "where a.tipe_id = ? and a.status = '1'");
            stat.setLong(1, Long.parseLong(tipe_id));

            rs = stat.executeQuery();
            if (rs.next()) {
                result.put("tipe_id", rs.getString("tipe_id"));
                result.put("device_type", rs.getString("device_type"));
                result.put("brand_id", rs.getString("brand_id"));
                result.put("brand_name", rs.getString("brand_name"));
                result.put("status", rs.getString("status"));
                result.put("create_date", rs.getString("create_date"));
                result.put("create_by", rs.getString("create_by"));
                result.put("update_date", rs.getString("update_date"));
                result.put("update_by", rs.getString("update_by"));
                result.put("resp_code", "0000");
                result.put("resp_desc", "Success");
            } else {
                result.put("resp_code", "0002");
                result.put("resp_desc", "tipe_id not found");
            }
        } catch (SQLException e) {
            result.put("resp_code", "0001");
            result.put("resp_desc", "Error: " + e.getMessage());
            System.out.println("Error: " + e);
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }

        return result;
    }

    public HashMap getlistterminal(String merchant_id) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        HashMap result = new HashMap();
        List list = new ArrayList();
        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            if (merchant_id.equals("admin")) {
                stat = conn.prepareStatement("select a.*, b.device_type from tms_terminal a "
                        + "left join tms_device_type b on a.tipe_id = b.tipe_id::varchar where a.status = '1'");
            } else {
                stat = conn.prepareStatement("select * from tms_terminal where merchant_id = ? status = '1'");
                stat.setString(1, merchant_id);
            }
            rs = stat.executeQuery();
            while (rs.next()) {
                HashMap ab = new HashMap();
                ab.put("terminal_id", rs.getString("terminal_id"));
                ab.put("tipe_id", rs.getString("tipe_id"));
                ab.put("device_type", rs.getString("device_type"));
                ab.put("status", rs.getString("status"));
                ab.put("create_date", rs.getString("create_date"));
                ab.put("create_by", rs.getString("create_by"));
                ab.put("update_date", rs.getString("update_date"));
                ab.put("update_by", rs.getString("update_by"));
                list.add(ab);
                ab = null;
            }
            result.put("resp_code", "0000");
            result.put("resp_desc", "success");

            result.put("list", list);
        } catch (SQLException e) {
            result.put("resp_code", "0001");
            result.put("resp_desc", "Error : " + e.getMessage());
            System.out.println("error : " + e);
        } finally {
            list = null;
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap addterminal(
            String terminal_id,
            String tipe_id,
            String user_id
    ) {
        HashMap result = new HashMap();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            stat = conn.prepareStatement("select * from tms_terminal where terminal_id = ?");
            stat.setString(1, terminal_id);
            rs = stat.executeQuery();
            if (rs.next()) {
                result.put(RuleNameParameter.resp_code, "0002");
                result.put(RuleNameParameter.resp_desc, terminal_id + " already exists");
            } else {
                stat.close();
                stat = conn.prepareStatement("insert into tms_terminal (terminal_id,tipe_id,create_by) values (?,?,?)");
                stat.setString(1, terminal_id);
                stat.setString(2, tipe_id);
                stat.setString(3, user_id);
                stat.executeUpdate();
                result.put(RuleNameParameter.resp_code, "0000");
                result.put(RuleNameParameter.resp_desc, "Success");
            }
        } catch (SQLException e) {
            result.put(RuleNameParameter.resp_code, "0001");
            result.put(RuleNameParameter.resp_desc, "Error : " + e.getMessage());
            System.out.println(e);
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap editterminal(
            String terminal_id,
            String tipe_id,
            String user_id
    ) {
        HashMap result = new HashMap();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            stat = conn.prepareStatement("update tms_terminal set "
                    + "tipe_id = ?, "
                    + "update_by = ?, "
                    + "update_date = ? "
                    + "where terminal_id = ?");
            stat.setString(1, tipe_id);
            stat.setString(2, user_id);
            stat.setTimestamp(3, Timestamp.from(Instant.now()));
            stat.setString(4, terminal_id);

            stat.executeUpdate();
            result.put(RuleNameParameter.resp_code, "0000");
            result.put(RuleNameParameter.resp_desc, "Success");
        } catch (SQLException e) {
            System.out.println(e);
            result.put(RuleNameParameter.resp_code, "0001");
            result.put(RuleNameParameter.resp_desc, "Error : " + e.getMessage());
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap deleteterminal(String terminal_id, String user_id) {
        HashMap result = new HashMap();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            stat = conn.prepareStatement("update tms_terminal set status = '0', update_date=?, update_by=?, where terminal_id=?");
            stat.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stat.setString(2, user_id);
            stat.setString(3, terminal_id);
            stat.executeUpdate();
            result.put(RuleNameParameter.resp_code, "0000");
            result.put(RuleNameParameter.resp_desc, "Success");
        } catch (SQLException e) {
            result.put(RuleNameParameter.resp_code, "0001");
            result.put(RuleNameParameter.resp_desc, "Error : " + e.getMessage());
            System.out.println(e);
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap getdetailterminal(String terminal_id, String user_id) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        HashMap result = new HashMap();
        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            stat = conn.prepareStatement("select * from tms_terminal where terminal_id = ? and status = '1'");
            stat.setString(1, terminal_id);
            rs = stat.executeQuery();
            if (rs.next()) {
                result.put("account_id", rs.getString("account_id"));
                result.put("account_level", rs.getString("account_level"));
                result.put("account_name", rs.getString("account_name"));
                result.put("level_desc", rs.getString("level_desc"));
                result.put("create_date", rs.getString("create_date"));
                result.put("merchant_id", rs.getString("merchant_id"));
                result.put("resp_code", "0000");
                result.put("resp_desc", "success");
            } else {
                result.put(FieldParameterMatapos.resp_code, "0002");
                result.put(FieldParameterMatapos.resp_desc, "not found");
            }

        } catch (SQLException e) {
            result.put("resp_code", "0001");
            result.put("resp_desc", "Error : " + e.getMessage());
            System.out.println("error : " + e);
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }
    
    public HashMap getlistsummary(String merchant_id) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        HashMap result = new HashMap();
        List list = new ArrayList();
        try {
            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            if (merchant_id.equals("Admin")) {
                stat = conn.prepareStatement("select * from tms_summary");
            } else {
                stat = conn.prepareStatement("select * from tms_summary where merchant_id=?");
                stat.setString(1, merchant_id);
            }
            rs = stat.executeQuery();
            while (rs.next()) {
                HashMap ab = new HashMap();
                ab.put("message_time", rs.getString("message_time"));
                ab.put("merchant_id", rs.getString("merchant_id"));
                ab.put("terminal_id", rs.getString("terminal_id"));
                ab.put("transaction_reference_number", rs.getString("transaction_reference_number"));
                ab.put("transaction_time", rs.getString("transaction_time"));
                ab.put("result", rs.getString("result"));
                ab.put("amount", rs.getString("amount"));
                ab.put("customer_data", rs.getString("customer_data"));
                ab.put("customer_pan", rs.getString("customer_pan"));
                ab.put("merchant_pan", rs.getString("merchant_pan"));
                ab.put("acquirer_id", rs.getString("acquirer_id"));
                ab.put("issuer_name", rs.getString("issuer_name"));
                ab.put("internal_reference", rs.getString("internal_reference"));
                list.add(ab);
                ab = null;
            }
            result.put("resp_code", "0000");
            result.put("resp_desc", "success");

            result.put("list", list);
        } catch (SQLException e) {
            result.put("resp_code", "0001");
            result.put("resp_desc", "Error : " + e.getMessage());
            System.out.println("error : " + e);
        } finally {
            list = null;
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

}
