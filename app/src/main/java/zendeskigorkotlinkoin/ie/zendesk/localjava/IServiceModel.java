package zendeskigorkotlinkoin.ie.zendesk.localjava;

import io.reactivex.Observable;
import zendeskigorkotlinkoin.ie.model.TicketsResults;

/**
 * Created by igorfrankiv on 24/10/2018.
 */

public interface IServiceModel {

    Observable<TicketsResults> readIOdataRX();
    Boolean finalWriteIODataRX(String isRecorded);


}
