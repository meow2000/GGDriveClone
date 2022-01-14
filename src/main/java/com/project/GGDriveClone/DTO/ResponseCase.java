package com.project.GGDriveClone.DTO;

public interface ResponseCase {
    //common
    ResponseStatus SUCCESS = new ResponseStatus(1000, "SUCCESS");
    ResponseStatus ERROR = new ResponseStatus(4, "ERROR");

    //login
    ResponseStatus LOGIN_FAIL = new ResponseStatus(5, "LOGIN FAIL");

    //file
    ResponseStatus USER_OR_FILE_NOT_EXIST = new ResponseStatus(6, "USER OR FILE NOT EXIST");
    ResponseStatus ACCESS_CONTROL_EXIST = new ResponseStatus(7, "USER ALREADY HAVE PERMISSION TO ACCESS THIS FILE");

    // patientInfo
    ResponseStatus NOT_FOUND_PATIENT_INFO = new ResponseStatus(1405, "PATIENT INFO NOT FOUND");
    ResponseStatus INVALID_PATIENT_INFO_PARAM = new ResponseStatus(5000, "invalid patient info param");

    //login
    ResponseStatus INVALID_WEB_LOGIN_PARAM = new ResponseStatus(1406, "Invalid web login param!");
    ResponseStatus INVALID_WEB_PASSWORD = new ResponseStatus(1407, "Invalid web login password param!");

    //changePassword
    ResponseStatus INVALID_NEW_PASSWORD = new ResponseStatus(1408, "Invalid web password");
    ResponseStatus OLD_PASSWORD_IS_INCORRECT = new ResponseStatus(1409, "old password is incorrect");
    ResponseStatus INVALID_IMAGES_TYPE = new ResponseStatus(1410, "Invalid image type");

    ResponseStatus DATA_FROM_ELINK = new ResponseStatus(1500, "One time code is not exist in Kusuri system");

    ResponseStatus EXISTED_NAME_OR_EMAIL = new ResponseStatus(1501, "Username or email is existed");

    ResponseStatus MEMBER_PCR_SOURCE_NOT_FOUND = new ResponseStatus(1503, "Member pcr source not found");

    ResponseStatus INVALID_IP = new ResponseStatus(1504, "Invalid IP");

    ResponseStatus MULTIPLE_MEDICATE = new ResponseStatus(1505, "QR has multiple medicate");

    ResponseStatus LACK_OF_INFORMATION = new ResponseStatus(1506, "lack of information");

    ResponseStatus INVALID_USER_ID = new ResponseStatus(1506, "invalid user id");

    ResponseStatus INVALID_USER_ID_OR_PID = new ResponseStatus(1507, "invalid user id or pid");



}

