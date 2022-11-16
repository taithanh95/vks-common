package com.bitsco.vks.common.constant;

/*
 * Declare constants used in the project
 */
public interface Constant {
    //file config in lib_common
    public static final String FILE_CONFIG = "config_common.properties";
    public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";
    public static final String CONTENT_TYPE_APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";
    public static final String UTF_8 = "UTF-8";
    public static final String EMPTY = "";
    public static final String BLANK = " ";
    public static final String DASH = "-";
    public static final String DOT = "\\.";
    public static int MAX_LENGTH_RESPONSE_CODE = 4;
    public static final int ZERO = 0;
    public static final int FIRST = 1;
    public static final int SECOND = 2;
    public static final String ID_VKSNDTC = "01";

    interface ENVIRONMENT {
        public static final String PRODUCTION = "production";
        public static final String TEST = "test";
        public static final String DEV = "dev";
    }

    interface EMAIL {
        interface TYPE {
            public static final int NOTIFICATION = 1;
            public static final int CONFIRM = 2;
        }

        interface STATUS {
            public static final int NEW = 0;
            public static final int SUCCESS = 1;
            public static final int ERROR = 2;
            public static final int TIME_OUT = 4;
        }
    }

    interface USER {
        interface STATUS {
            public static final int ACTIVE = 1;
            public static final int INACTIVE = 0;
            public static final int WAIT_FOR_CONFIRM_EMAIL = 2;
        }
    }

    interface ROLE {
        interface TYPE {
            public static final int MENU = 1;
            public static final int METHOD = 2;
        }
    }

    interface GROUP_ROLE {
        interface TYPE {
            public static final int MENU = 1;
        }

        interface APP_CODE {
            public static final String VKS_QLA = "VKS_QLA";
            public static final String VKS_REPORT = "VKS_REPORT";
        }
    }

    interface OTP {
        public static final long EXPIRY_DEFAULT = 300000;//5 phut
        public static final int TYPE_DEFAULT = 1;

        interface RESET {
            public static final long EXPIRY = 300000;//5 phut
            public static final int TYPE = 1;
        }
    }

    interface PARAMS {
        String ALPHA_NUMERIC_STRING = "0123456789abcdefghijklmnopqrstuvxyz";

        interface GROUP {
            public static final String CHECK_STOCK = "CHECK_STOCK";
            public static final String EVN_CUSTOMER_BILL = "EVN_CUSTOMER_BILL";
        }

        interface TYPE {
            public static final String NPP = "NPP";
        }

    }


    interface CRYPT {
        interface MD5 {
            public static final String ALGORITHM = "MD5";
            public static byte[] HEX_ARRAY = new byte[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        }
    }

    interface STATUS_OBJECT {
        public static final int ACTIVE = 1;
        public static final int INACTIVE = 0;
        public static final String ACTIVE_Y = "Y";
        public static final String INACTIVE_Y = "N";
        public static final int ALL = -1;
        public static final Boolean ACTIVE_B = true;
        public static final Boolean INACTIVE_B = false;
    }
    interface APPROVE_OBJECT {
        public static final String ACTIVE = "Y";
        public static final String INACTIVE = "N";
        public static final String WAITING = "W";

    }

    interface DATE {
        interface FORMAT {
            public static final String DATE_TIME_STAMP = "HH:mm:ss.SSS dd/MM/yyyy";
            public static final String DATE_TIME = "HH:mm:ss dd/MM/yyyy";
            public static final String DATE = "dd/MM/yyyy";
            public static final String TIME = "HH:mm:ss";
            public static final String TIME_2 = "HH:mm";
        }
    }


    interface LOG_APPENDER {
        public static final String CONTROLLER = "controller";
        public static final String COMMON = "common";
        public static final String DB = "db";
        public static final String CONNECTION = "connection";
        public static final String APPLICATION = "application";
        public static final String PRE_FILTER = "pre_filter";
        public static final String JOB = "job";
        public static final String SERVICE = "service";
        public static final String SUPPLIER = "supplier";
        public static final String THREAD = "thread";
    }

    interface PAGE {
        interface DEFAULT_VALUE {
            public static final String ASC_ORDER = "ASC";
            public static final String DESC_ORDER = "DESC";
            public static final int PAGE_DEFAULT = 1;
            public static final int SIZE_FIFTY = 50;
            public static final int SIZE_ALL = 0;
            public static final int PAGE_ALL = 0;
        }
    }

    interface KEY {
        public static final String USERNAME = "username";
        public static final String USER_TYPE = "user_type";
    }

    //Declare TABLE used in the DATABASE
    interface TABLE {
        public static final String ROLE = "role";
        public static final String USER_ROLE = "user_role";
        public static final String GROUP_ROLE = "group_role";
        public static final String USER_GROUP_ROLE = "user_group_role";
        public static final String GROUP_ROLE_MAP = "group_role_map";
        public static final String GROUP_USER = "group_user";
        public static final String GROUP_USER_GROUP_ROLE = "group_user_group_role";
        public static final String GROUP_USER_ROLE = "group_user_role";
        public static final String PARAM = "params";
        public static final String USERS = "users";
        public static final String LST_SPP = "LST_SPP";
        public static final String LST_DECISION = "LST_DECISION";
        public static final String EMAIL = "email";
        public static final String CUSTOMER = "customer";
        public static final String BILL = "bill";
        public static final String BANK = "bank";
        public static final String BILL_INDICATOR = "bill_indicator";
        public static final String METER = "meter";
        public static final String METER_INDEX = "meter_index";
        public static final String PROVINCE = "province";
        public static final String COMMUNE = "commune";
        public static final String DISTRICT = "district";
        public static final String VILLAGE = "village";
        public static final String BILL_PAYMENT = "bill_payment";
        public static final String TRANSACTION = "transaction";
        public static final String LIMIT_AMOUNT = "limit_amount";
        public static final String LIMIT_AMOUNT_HISTORY = "limit_amount_history";
        public static final String LIMIT_TRANS = "limit_trans";
        public static final String LIMIT_TRANS_HISTORY = "limit_trans_history";
        public static final String SUPPLIER = "supplier";
        public static final String POSITION = "position";
        public static final String POSITION_GROUP_ROLE = "position_group_role";
        public static final String INDEX_METER = "index_meter";
        public static final String EVN_PC = "evn_pc";
        public static final String BIG_CUSTOMER = "big_customer";
        public static final String LOG_GATEWAY = "log_gateway";
        public static final String USER_RELATIONSHIP = "user_relationship";
        public static final String LST_LAW = "LST_LAW";
        public static final String LST_INSPECTOR = "LST_INSPECTOR";
        public static final String LST_INVESTIGATIVE_AGENCY = "LST_INVESTIGATIVE_AGENCY";
        public static final String SPP_ACCUSED = "SPP_ACCUSED";
        public static final String SPP_CASE = "SPP_CASE";
        public static final String SPP_REGISTER = "SPP_REGISTER";
        public static final String LST_CODE = "LST_CODE";
        public static final String LST_ARMY = "LST_ARMY";
        public static final String LST_BORDERGUARDS = "LST_BORDERGUARDS";
        public static final String LST_RANGER = "LST_RANGER";
        public static final String LST_CUSTOMS = "LST_CUSTOMS";
        public static final String SPP_CENTENCE = "SPP_CENTENCE";
        public static final String LST_SPC = "LST_SPC";
        public static final String LST_POLICE = "LST_POLICE";
        public static final String LST_CONCLUSION = "LST_CONCLUTION";
        public static final String LST_POL = "LST_POL";
        public static final String SPP_DECISION_JUDICIAL = "SPP_DECISION_JUDICIAL";
        public static final String REPORT = "REPORT";
        // vis-phucnv start 08/04/2021
        public static final String ARREST_DETENTION_INFO = "ARREST_DETENTION_INFO";
        public static final String ARREST_DETENTION_ARRESTEE = "ARREST_DETENTION_ARRESTEE";
        public static final String ARREST_DETENTION_LAW_OFFENSE = "ARREST_DETENTION_LAW_OFFENSE";
        public static final String ARREST_DETENTION_SETTLEMENT_DECISION = "ARREST_DETENTION_SETTLEMENT_DECISION";
        public static final String ARREST_DETENTION_DISCIPLINE_VIOLATION = "ARREST_DETENTION_DISCIPLINE_VIOLATION";
        // vis-phucnv end 08/04/2021
    }

    //Declare SEQUENCE used in the DATABASE
    interface SEQUENCE {
        public static final String SQ_USERS = "SQ_USERS";
        public static final String SQ_GROUP_USER = "SQ_GROUP_USER";
        public static final String SQ_GROUP_USER_GROUP_ROLE = "SQ_GROUP_USER_GROUP_ROLE";
        public static final String SQ_GROUP_USER_ROLE = "SQ_GROUP_USER_ROLE";
        public static final String SQ_BANK = "SQ_BANK";
        public static final String SQ_COMMUNE = "SQ_COMMUNE";
        public static final String SQ_PROVINCE = "SQ_PROVINCE";
        public static final String SQ_DISTRICT = "SQ_DISTRICT";
        public static final String SQ_GROUP_ROLE = "SQ_GROUP_ROLE";
        public static final String SQ_ROLE = "SQ_ROLE";
        public static final String SQ_GROUP_ROLE_MAP = "SQ_GROUP_ROLE_MAP";
        public static final String SQ_PARAM = "SQ_PARAM";
        public static final String SQ_POSITION = "SQ_POSITION";
        public static final String SQ_POSITION_GROUP_ROLE = "SQ_POSITION_GROUP_ROLE";
        public static final String SQ_VILLAGE = "SQ_VILLAGE";
        public static final String SQ_USER_ROLE = "SQ_USER_ROLE";
        public static final String SQ_USER_GROUP_ROLE = "SQ_USER_GROUP_ROLE";
        public static final String SQ_REPORT = "SQ_REPORT";
        // vis-phucnv start 08/04/2021
        public static final String SQ_ARREST_DETENTION_ARRESTEE= "SQ_ARREST_DETENTION_ARRESTEE";
        public static final String SQ_ARREST_DETENTION_SETTLEMENT_DECISION= "SQ_SETTLEMENT_DECISION";
        public static final String SQ_ARREST_DETENTION_INFO= "SQ_ARREST_DETENTION_INFO";
        public static final String SQ_ARREST_DETENTION_LAW_OFFENSE= "SQ_LAW_OFFENSE";
        public static final String SQ_ARREST_DETENTION_DISCIPLINE_VIOLATION= "SQ_ARREST_DETENTION_DISCIPLINE_VIOLATION";
        // vis-phucnv end 08/04/2021
    }

    interface METER_INDEX {
        interface STATUS {
            public static final int NEW = 0;
            public static final int UPDATE = 1;
        }

        interface TYPE {
            public static final int INDEX_PERIOD = 1;
            public static final int REPAIR = 2;
        }
    }

    interface REST_FULL {
        public static final String HTTP_STATUS = "HTTP_STATUS";
        public static final String RESPONSE_BODY = "RESPONSE_BODY";
        public static final String RESPONSE = "RESPONSE";
    }

    interface BILL {
        interface STATUS {
            public static final int UNPAID = 0;
            public static final int PAID = 1;
        }
    }

    interface TRANSACTION {
        interface STATUS {
            public static final int NEW = 0;
            public static final int SUCCESS = 1;
            public static final int PENDING = 2;
            public static final int ERROR = 3;
            public static final int TIME_OUT = 4;
            public static final int REVERT = 5;
        }

        interface TYPE {
            public static final int BILLING = 1;
            public static final int REVERT = 2;
        }
    }

    interface FEIGN_CLIENT {
        public static final String SSO = "vks-sso";
        public static final String CATEGORY = "vks-category";
        public static final String NOTIFICATION = "vks-notification";
        public static final String MANAGE = "vks-manage";

    }

    interface PACKAGE {
        public static final String PKG_REPORT = "PKG_REPORT";
        public static final String PKG_ADM_USERS = "PKG_ADM_USERS";
    }

    interface FUNCTION {
        public static final String BOOK_01 = "BOOK_01";
        public static final String BOOK_02 = "BOOK_02";
        public static final String BOOK_03 = "BOOK_03";
        public static final String BOOK_04 = "BOOK_04";
        public static final String BOOK_05 = "BOOK_05";
        public static final String BOOK_06 = "BOOK_06";
        public static final String BOOK_07 = "BOOK_07";
        public static final String BOOK_08 = "BOOK_08";
        public static final String BOOK_12 = "BOOK_12";
        public static final String BOOK_22 = "BOOK_22";
        public static final String FN_SEARCH = "FN_SEARCH";
        public static final String SP_LOG_DELETE = "sp_log_delete";
        public static final String FN_INSERT_UPDATE= "fn_insert_update";
        public static final String FN_DELETE = "fn_delete";
    }
    //phucnv start add stremma 12/04/2020
    interface SCHEMA{
        public static final String SPP_REPORT = "spp_report";
    }
    interface ARREST_DETENTION {
        public static final int ACTIVE = 1;
    }
    //phucnv end add stremma 12/04/2020
}
