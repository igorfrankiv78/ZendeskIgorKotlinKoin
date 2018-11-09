package zendeskigorkotlinkoin.ie.zendesk.localjava;

/*** Created by igorfrankiv on 24/10/2018.*/
import java.util.List;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import org.json.JSONException;

import zendeskigorkotlinkoin.ie.model.Tickets;
import zendeskigorkotlinkoin.ie.model.TicketsResults;

/*** Created by igorfrankiv on 23/01/2018.*/

public final class JSONUtil {
//
//    @SerializedName("id")
//    @Expose
//    private final Integer id;
//
//    @SerializedName("subject")
//    @Expose
//    private final String subject;
//
//    @SerializedName("description")
//    @Expose
//    private final String description;
//
//    @SerializedName("status")
//    @Expose
//    private final String status;

    public final static String createJsonString(List<Tickets> listOfUserLocations ){
        String stringJson ="";
        try {
            JSONArray arr = new JSONArray();
            for (Tickets s : listOfUserLocations) {
                JSONObject key = new JSONObject();
                key.put("id", s.getId() );
                key.put("subject", s.getSubject() );
                key.put("description", s.getDescription() );
                key.put("status", s.getStatus() );
                arr.put(key);
            }
            JSONObject json = new JSONObject();
//            json.put("isRecorded", isRecorded);
            json.put("tickets", arr);
            stringJson = json.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("writringJS ", String.valueOf( listOfUserLocations.size() ));
        return stringJson;
    }

    // make it an Observable method later
    public final static TicketsResults readJsonString(String jsonObj){
        List<Tickets> listOfUserLocations = new ArrayList<>();
//        String isRecorded = "NO";
        try {
            if ( jsonObj != null) {
                JSONObject jsonObject = new JSONObject(jsonObj);

//                if((jsonObject.has("isRecorded")) && (jsonObject.getString("isRecorded"))!=null)
//                    isRecorded = jsonObject.getString("isRecorded");
//                Log.e("isRecorded ", String.valueOf( isRecorded));
                JSONArray tickets;
                if ((jsonObject.has("tickets")) &&
                        (tickets = jsonObject.getJSONArray("tickets")) != null &&
                        tickets.length() > 0)
                    for (int i = 0; i < tickets.length(); i++) {
                        JSONObject ticketsObj = tickets.getJSONObject(i);
                        listOfUserLocations.add(new Tickets(
                                ticketsObj.getInt("id"),
                                ticketsObj.getString("subject"),
                                ticketsObj.getString("description"),
                                ticketsObj.getString("status")
                        ));
                    }
                Log.e("readingJS ", String.valueOf( listOfUserLocations.size()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new TicketsResults( listOfUserLocations );// ( listOfUserLocations,  isRecorded);
    }

}

