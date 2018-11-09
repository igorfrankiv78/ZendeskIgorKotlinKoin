package zendeskigorkotlinkoin.ie.screens.listoftickets.view.viewadapt

/*** Created by igorfrankiv on 17/10/2018.*/
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import zendeskigorkotlinkoin.ie.model.Tickets
import zendeskigorkotlinkoin.ie.model.TicketsResults
import zendeskigorkotlinkoin.ie.util.str.UtilReduceStrKotlin
import zendeskigorkotlinkoin.ie.R

class ZendeskListViewAdapter(val ticketsResults: TicketsResults,
                             val mContext:Context,
                             private val onClick: (Tickets) -> Unit ) :
      RecyclerView.Adapter<ZendeskListViewAdapter.ViewHolder>() {

    private val NEW_TICKET_STR: String = "new"
    private val OPEN_TICKET_STR: String = "open"
    private val PENDING_TICKET_STR: String = "pending"

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.display(ticketsResults.results[ position ], onClick)

        when (this.ticketsResults.results.get(position).status) {
            NEW_TICKET_STR -> {
                holder.statusTextV.setBackgroundColor(Color.parseColor(mContext.resources.getString(R.string.NEW_TICKET_COLOR)))
                holder.statusTextV.text = mContext.resources.getString(R.string.SET_CHAR_AS_NEW)
            }
            OPEN_TICKET_STR -> {
                holder.statusTextV.setBackgroundColor(Color.parseColor(mContext.resources.getString(R.string.OPEN_TICKET_COLOR)))
                holder.statusTextV.text = mContext.resources.getString(R.string.SET_CHAR_AS_OPEN)
            }
            PENDING_TICKET_STR -> {
                holder.statusTextV.setBackgroundColor(Color.parseColor(mContext.resources.getString(R.string.PENDING_TICKET_COLOR)))
                holder.statusTextV.text = mContext.resources.getString(R.string.SET_CHAR_AS_PENDING)
            }
        }

        holder.idTextV.text = "#" + this.ticketsResults.results.get(position).id.toString()
        holder.subjectTextV.setText(UtilReduceStrKotlin.reduceStr(this.ticketsResults.results.get(position).subject, 14, 11))
        holder.descriptionTextV.setText(UtilReduceStrKotlin.reduceStr(this.ticketsResults.results.get(position).description, 23, 20))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_view_cell_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this.ticketsResults.results.size
    }

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        internal val statusTextV:TextView
        internal val idTextV:TextView
        internal val subjectTextV:TextView
        internal val descriptionTextV:TextView
        internal val weatherItemLayout: LinearLayout

        init {
            weatherItemLayout = view.findViewById<LinearLayout>(R.id.linearLayoutMainContainer)
            statusTextV =  view.findViewById<View>(R.id.statusTextV) as (TextView)
            idTextV = view.findViewById<View>(R.id.idTextV) as (TextView)
            subjectTextV = view.findViewById<View>(R.id.subjectTextV) as (TextView)
            descriptionTextV = view.findViewById<View>(R.id.descriptionTextV) as (TextView)
        }

        fun display(dailyForecastModel: Tickets, onClick: (Tickets) -> Unit ) {
            weatherItemLayout.setOnClickListener { onClick(dailyForecastModel) }
        }
    }
}