package zendeskigorkotlinkoin.ie.model;

import android.support.annotation.NonNull;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*** Created by igor on 03/06/2017. */
public final class Tickets {

    @SerializedName("id")
    @Expose
    private final Integer id;

    @SerializedName("subject")
    @Expose
    private final String subject;

    @SerializedName("description")
    @Expose
    private final String description;

    @SerializedName("status")
    @Expose
    private final String status;

    public Tickets (@NonNull Integer id, @NonNull String subject, @NonNull String description, @NonNull String status)
    {
        this.id = id;
        this.subject = subject;
        this.description = description;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }
}
