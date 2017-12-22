package Utils
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext

/**
  * Created by brian on 01/12/2017.
  */
object SQLContextSingleton {
  @transient
  @volatile private var instance: SQLContext = _

  def getInstance(sparkContext: SparkContext): SQLContext = {
    if (instance == null) {
      synchronized {
        if (instance == null) {
          instance = SQLContext.getOrCreate(sparkContext)
        }
      }
    }
    instance
  }

}
