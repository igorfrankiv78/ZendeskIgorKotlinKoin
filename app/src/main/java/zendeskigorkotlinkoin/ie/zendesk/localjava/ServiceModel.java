package zendeskigorkotlinkoin.ie.zendesk.localjava;

import android.content.Context;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import io.reactivex.Observable;
import zendeskigorkotlinkoin.ie.model.Tickets;
import zendeskigorkotlinkoin.ie.model.TicketsResults;

/*** Created by igorfrankiv on 24/10/2018.*/

public class ServiceModel implements IServiceModel {

//    The Oracle Declaration is
//    Implementation Notes
//    This lock supports a maximum of 65535 recursive write locks and 65535 read locks.
//    Attempts to exceed these limits result in Error throws from locking methods.
//    https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/locks/ReentrantReadWriteLock.html
    private ReadWriteLock rwlock = new ReentrantReadWriteLock();
    private List<Tickets> mListOfUserLocations;

    public static final String JSON_LOCATION_FILE = "jsontickets.json";
    public static final String DEFAULT_FILE_ENCODING = "UTF-8";
    public static final String READD_ERROR_MESSAGE ="Could not read the file!";
    public static final String WRITE_ERROR_MESSAGE ="Could not write the file!";
    private final Context mContext;

    public ServiceModel(Context context ) {
        this.mContext = context;
        mListOfUserLocations = new ArrayList<>();
    }

    private String readJSONFileFromLocalDIR(Context context)
    {
        rwlock.readLock().lock();

        StringBuilder jsonData = new StringBuilder();
        BufferedReader in = null;
        try
        {
            File file = new File(context.getFilesDir(), JSON_LOCATION_FILE);
            in = new BufferedReader(new InputStreamReader(new FileInputStream(file), DEFAULT_FILE_ENCODING));
            String line;
            while ((line = in.readLine()) != null)
            {jsonData.append(line);}
        }catch (Exception e)     { e.printStackTrace(); }
        finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                return READD_ERROR_MESSAGE;
            }
            finally {

                rwlock.readLock().unlock();

            }
        }
        return jsonData.toString();
    }

    private  boolean writingJSONFileToLocalDIR( Context context, String jsonObj) {

        rwlock.writeLock().lock();

        try{ FileOutputStream output = context.openFileOutput(JSON_LOCATION_FILE, Context.MODE_PRIVATE);//
            OutputStreamWriter writer = new OutputStreamWriter(output);
            writer.write(jsonObj);
            writer.flush();
            writer.close();
        }
        catch(Exception e)
        {
            return false;
        }
        finally {

            rwlock.writeLock().unlock();

        }
        return true;
    }

    @Override
    public Observable<TicketsResults> readIOdataRX ( ){

        List<Tickets> listOfTickets = null;

        if( new File( mContext.getFilesDir(),  ServiceModel.JSON_LOCATION_FILE).exists()) {

            final String jsonStr = readJSONFileFromLocalDIR( mContext );

            final TicketsResults userLocations = JSONUtil.readJsonString(jsonStr);

//            String isRecorded  = userLocations.getIsRecorded();
//            if (isRecorded.contains( ServicePresenter.STATE_OF_RECORDDING_YES )) {
                listOfTickets = userLocations.getResults();
//            }
//            else
//                listOfTickets = new ArrayList<>();
        }
        return Observable.just( new TicketsResults( listOfTickets ) );
    }

    //=================================================
    @Override
    public Boolean finalWriteIODataRX( String isRecorded){

        Boolean isWrittenIO = false;
        if(mListOfUserLocations != null && mListOfUserLocations.size() > 0 ) {
            String jsonListOfUserLocationsObj = JSONUtil.createJsonString( mListOfUserLocations );
            isWrittenIO = writingJSONFileToLocalDIR(mContext, jsonListOfUserLocationsObj);
        }
        return isWrittenIO;
    }
}
