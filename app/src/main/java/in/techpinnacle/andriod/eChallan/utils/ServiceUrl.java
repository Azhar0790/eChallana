package in.techpinnacle.andriod.eChallan.utils;

/**
 * Created by Vamsy on 17-Nov-15.
 */
public class ServiceUrl {
    public static final String REST_SERVICE_URL = "http://192.168.2.7:9090/TrafficChallanWeb/";
    //public static final String REST_SERVICE_URL = "http://192.168.173.1:8080/TrafficChallanWeb/";
    public static final String GET_USER = REST_SERVICE_URL + "getUser";
    public static final String SET_USER = REST_SERVICE_URL + "setUser";
    public static final String GET_CITIZEN = REST_SERVICE_URL + "getCitizen";
    public static final String GET_OFFENCES = REST_SERVICE_URL + "getOffences";
    public static final String ADD_OFFENCE = REST_SERVICE_URL + "addOffence";
}
