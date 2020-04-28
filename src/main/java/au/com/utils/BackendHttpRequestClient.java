package au.com.utils;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import components.screens.BaseClass;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.*;
import static com.jayway.restassured.RestAssured.given;
import static components.screens.BaseClass.prop;

public class BackendHttpRequestClient {
    private String xAuthToken = Constants.apiKey;

    public BackendHttpRequestClient() {

            String env = prop.getProperty("env");
            System.out.println("env is " + env);
            ServerSelection.ServerType serverType = ServerSelection.getServerType(env);
            ServerSelection serverInfo = new ServerSelection(serverType);

            RestAssured.basePath = serverInfo.getBasePath();
            RestAssured.baseURI = serverInfo.getServerUrl();
        // create Token here if username password need to use in API
    }

    public boolean verifyDateFromCity(String attr,String datePass, int dgr) throws Exception {
        Response response = given()
                .header("Cookie", "X-Auth-Token=" + xAuthToken)
                .when()
                .get("forecast?APPID="+xAuthToken+"&q={attr}&units=Metric", attr);
        String responseMessage = response.asString();
        JSONArray jsonArray= (JSONArray) new JSONObject(responseMessage).get("list");
        boolean value=false;
        for(int i=0;i<jsonArray.length();i++){
            JSONObject jsonTestUpdate = jsonArray.getJSONObject(i);
            if(jsonTestUpdate.get("dt_txt").toString().contains(datePass)){
                String temp=((JSONObject)jsonTestUpdate.get("main")).get("temp").toString();
                System.out.println("for Date "+jsonTestUpdate.get("dt_txt"));
                System.out.println("Temp is "+temp);
                if(Double.parseDouble(temp)>dgr) {
                    value = true;
                }
                else {
                    value = false;
                    break;
                }
            }
        }

        return value;
    }
    public void isServiceOn() {
        given()
                .header("Cookie", "X-Auth-Token=")
                .when()
                .get("weather?APPID="+xAuthToken+"&q={attr}","Sydney")
                .then()
                .assertThat().statusCode(200);
    }


    public int getdiff(int dayOfWeek, int passDay)
    {
        int dayDiff=0;
        if(dayOfWeek==passDay){
            dayDiff=0;
        }else if(dayOfWeek>passDay){
            dayDiff=7-dayOfWeek;
        }else {
            dayDiff=passDay-dayOfWeek;
        }
        return dayDiff;
    }

    public String getDateforComingDay(String arg0){
        int dayDiff=0;
        Calendar c = Calendar.getInstance();
        c.setTime(c.getTime());
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        switch (arg0) {
            case "SUNDAY":
                dayDiff=getdiff(dayOfWeek,1);
                break;
            case "MONDAY":
                dayDiff=getdiff(dayOfWeek,2);
                break;
            case "TUESDAY":
                dayDiff=getdiff(dayOfWeek,3);
                break;
            case "WEDNESDAY":
                dayDiff=getdiff(dayOfWeek,4);
                break;
            case "THURSDAY":
                dayDiff=getdiff(dayOfWeek,5);
                break;
            case "FRIDAY":
                dayDiff=getdiff(dayOfWeek,6);
                break;
            case "SATURDAY":
                dayDiff=getdiff(dayOfWeek,7);
                break;

        }
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_MONTH, dayDiff);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate= formatter.format(cal.getTime());
        return strDate;
    }

}
