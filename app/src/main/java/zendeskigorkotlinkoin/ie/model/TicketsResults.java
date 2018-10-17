package zendeskigorkotlinkoin.ie.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/*** Created by igor on 03/06/2017. */
public final class TicketsResults {

    @SerializedName("tickets")
    @Expose
    private final List<Tickets> tickets;

    public TicketsResults (List<Tickets> tickets)
    {
       this.tickets = tickets;
    }

    public List<Tickets> getResults() {
        return tickets;
    }

}
