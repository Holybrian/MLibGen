package Utils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{Logging, SparkContext}
/**
  * Created by brian on 29/11/2017.
  */
object LogUtils extends Logging {

  def setLogLevels(sparkContext: SparkContext) {

    sparkContext.setLogLevel(Level.WARN.toString)
    val log4jInitialized = Logger.getRootLogger.getAllAppenders.hasMoreElements
    if (!log4jInitialized) {
      logInfo(
        """Setting log level to [WARN] for streaming executions.
          |To override add a custom log4j.properties to the classpath.""".stripMargin)
      Logger.getRootLogger.setLevel(Level.WARN)
    }
  }
}
