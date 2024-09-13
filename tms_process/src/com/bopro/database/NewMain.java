/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.bopro.database;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Arrays;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.StringJoiner;
/**
 *
 * @author suhan
 */
public class NewMain {

    /**
     * @param args the command line arguments
     * @throws java.text.ParseException
     */
//    public static void main(String[] args) throws ParseException {
//        String cuti_list = "CT|CN|CM";
//        String cuti_terpakai = "2|0|0";
//        String cuti_sisa = "10|2|30";
//        String kode_cuti = "CM";
//
//        int ambil_cuti = 2;
//        int index = 0;
//        int sebelum_terpakai = 0;
//        int sebelum_sisa = 0;
//        int sesudah_terpakai = 0;
//        int sesudah_sisa = 0;
//
//        System.out.println("cuti_list nya : " + cuti_list);
//        System.out.println("cuti_terpakai nya : " + cuti_terpakai);
//        System.out.println("cuti_sisa nya : " + cuti_sisa);
//
//        System.out.println("=======================================================");
//
//        System.out.println("kode cuti nya : " + kode_cuti);
//        System.out.println("ngambil cuti nya : " + ambil_cuti);
//
//        System.out.println("=======================================================");
//
//        List<String> list_kodecuti = Arrays.asList(cuti_list.split("\\|"));
//        List<String> list_sisacuti = Arrays.asList(cuti_sisa.split("\\|"));
//        List<String> list_terpakaicuti = Arrays.asList(cuti_terpakai.split("\\|"));
//
//        for (int i = 0; i < list_kodecuti.size(); i++) {
//            if (list_kodecuti.get(i).equals(kode_cuti)) {
//                index = i;
//            }
//        }
//
//        sebelum_terpakai = Integer.parseInt(list_terpakaicuti.get(index));
//        sebelum_sisa = Integer.parseInt(list_sisacuti.get(index));
//
//        System.out.println("cuti terpakai before: " + sebelum_terpakai);
//        System.out.println("sisa cuti before: " + sebelum_sisa);
//
//        System.out.println("=======================================================");
//
//        sesudah_terpakai = (sebelum_terpakai + ambil_cuti);
//        sesudah_sisa = (sebelum_sisa - ambil_cuti);
//
//        System.out.println("cuti terpakai after : " + sesudah_terpakai);
//        System.out.println("cuti sisa after : " + sesudah_sisa);
//
//        System.out.println("=======================================================");
//
//        list_terpakaicuti.set(index, String.valueOf(sesudah_terpakai));
//        list_sisacuti.set(index, String.valueOf(sesudah_sisa));
//
//        System.out.println("list_sisacuti : " + list_sisacuti);
//        System.out.println("list_terpakaicuti : " + list_terpakaicuti);
//
////        membuat list dengan delimiter
//        System.out.println("sebelum : " + list_terpakaicuti);
//        StringJoiner joiner = new StringJoiner("|");
//        for (int i = 0; i < list_terpakaicuti.size(); i++) {
//            System.out.println("index : " + list_terpakaicuti.get(i));
//            joiner.add(list_terpakaicuti.get(i));
//        }
//        String joinedString = joiner.toString();
//        System.out.println("sesudah : " + joinedString);
//        
//    }
    // Main class  
//    public static void main(String[] args) {
//        HashMap bedanya = new HashMap();
//        String joindate = "2023-01-30";
//        String leave = "2024-01-29";
//
//        String tahunkerja = "0";
//        int thnkerjaold = Integer.parseInt(tahunkerja);
//
//        bedanya = Calculatedate.calculatedate(joindate, leave);
//        int tahunkerjanew = Integer.parseInt(bedanya.get("differentyear").toString());
//
//        if (tahunkerjanew > thnkerjaold) {
//            System.out.println("generate cuti");
//            System.out.println("update tahun cuti di database");
//        } else {
//            System.out.println("jangan generate");
//        }
//    }
    public static void main(String[] args) throws ParseException {
        String cuti_list = "CT|CN|CM";
        String cuti_desc = "Cuti Tahunan|Cuti Nikah|Cuti Melahirkan";
        String cuti_bucket = "12|2|60";
        String cuti_terpakai = "2|0|0";
        String cuti_sisa = "10|2|30";
        

        String new_kode_cuti = "CC";
        String new_cuti_desc = "Cuti Cuma-Cuma";
        String new_bucket_cuti = "10";
        

        System.out.println("cuti_code nya : " + cuti_list);
        System.out.println("cuti_desc nya : " + cuti_desc);
        System.out.println("cuti_bucket nya : " + cuti_bucket);
        System.out.println("cuti_terpakai nya : " + cuti_terpakai);
        System.out.println("cuti_sisa nya : " + cuti_sisa);
        

        System.out.println("=======================================================");

        System.out.println("kode cuti baru nya : " + new_kode_cuti);
        System.out.println("bucket cuti baru nya : " + new_bucket_cuti);
        System.out.println("desc cuti baru nya : " + new_cuti_desc);

        System.out.println("=======================================================");

//        membuat list dengan delimiter
        StringJoiner joiner_kode_cuti = new StringJoiner("|");
        StringJoiner joiner_cuti_desc = new StringJoiner("|");
        StringJoiner joiner_cuti_bucket = new StringJoiner("|");
        StringJoiner joiner_cuti_terpakai = new StringJoiner("|");
        StringJoiner joiner_cuti_sisa = new StringJoiner("|");
        

        joiner_kode_cuti.add(cuti_list);
        joiner_kode_cuti.add(new_kode_cuti);
        
        joiner_cuti_desc.add(cuti_desc);
        joiner_cuti_desc.add(new_cuti_desc);
        
        joiner_cuti_bucket.add(cuti_bucket);
        joiner_cuti_bucket.add(new_bucket_cuti);
        
        joiner_cuti_terpakai.add(cuti_terpakai);
        joiner_cuti_terpakai.add("0");
        
        joiner_cuti_sisa.add(cuti_sisa);
        joiner_cuti_sisa.add("0");
        
        String joinedString_code = joiner_kode_cuti.toString();
        String joinedString_desc = joiner_cuti_desc.toString();
        String joinedString_bucket = joiner_cuti_bucket.toString();
        String joinedString_terpakai = joiner_cuti_terpakai.toString();
        String joinedString_sisa = joiner_cuti_sisa.toString();
        
        System.out.println("sesudah kode : " + joinedString_code);
        System.out.println("sesudah desc : " + joinedString_desc);
        System.out.println("sesudah bucket : " + joinedString_bucket);
        System.out.println("sesudah terpakai : " + joinedString_terpakai);
        System.out.println("sesudah sisa : " + joinedString_sisa);

    }
}
