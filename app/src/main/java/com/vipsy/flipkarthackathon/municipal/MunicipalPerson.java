package com.vipsy.flipkarthackathon.municipal;



class MunicipalPerson {
    String name;
    String complaint_title;
    String complaint_body;
    String  status;
    String time;
    String complain_id;

    MunicipalPerson(String complain_id, String name, String complaint_title, String complaint_body, String status, String time) {
        this.complain_id = complain_id;
        this.name = name;
        this.complaint_title = complaint_title;
        this.complaint_body = complaint_body;
        this.status = status;
        this.time = time;

    }
}