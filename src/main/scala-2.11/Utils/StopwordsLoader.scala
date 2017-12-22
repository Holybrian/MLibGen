package Utils
import scala.io.Source

/**
  * Created by brian on 29/11/2017.
  */
object StopwordsLoader {
  def loadStopWords(stopWordsFileName: String): List[String] = {
    Source.fromInputStream(getClass.getResourceAsStream("/" + stopWordsFileName)).getLines().toList
  }

}
