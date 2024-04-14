//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.minlmedia.Item
//import com.example.minlmedia.R
//import com.bumptech.glide.Glide
//
//class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//    val courseIV: ImageView
//    val courseNameTV: TextView
//    init {
//        courseIV = itemView.findViewById(R.id.idIVCourseImage)
//        courseNameTV = itemView.findViewById(R.id.idTVCourseName)
//    }
//    fun bindData(imageUrl: String) {
//        Glide.with(itemView.context)
//            .load(imageUrl)
//            .into(courseIV)
//    }
//}
//
//class CourseAdapter(private val context: Context, courseModelArrayList: List<Item>) :
//    RecyclerView.Adapter<ViewHolder>() {
//    private val courseModelArrayList: List<Item>
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        // to inflate the layout for each item of recycler view.
//        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        // to set data to textview and imageview of each card layout
//        val model: Item = courseModelArrayList[position]
//        holder.courseNameTV.setText(model.getCourse_name())
//        holder.bindData(model.getCourse_image())
//    }
//
//    override fun getItemCount(): Int {
//        // this method is used for showing number of card items in recycler view.
//        return courseModelArrayList.size
//    }
//
//    // View holder class for initializing of your views such as TextView and Imageview.
//
//
//    // Constructor
//    init {
//        this.courseModelArrayList = courseModelArrayList
//    }
//}
