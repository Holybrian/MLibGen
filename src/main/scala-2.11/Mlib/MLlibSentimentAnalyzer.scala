package Mlib
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.mllib.classification.NaiveBayesModel
import org.apache.spark.mllib.feature.HashingTF
import org.apache.spark.mllib.linalg.Vector


/**
  * Created by brian on 01/12/2017.
  */
object MLlibSentimentAnalyzer {
  def computeSentiment(text: String, stopWordsList: Broadcast[List[String]], model: NaiveBayesModel): Int = {
    val tweetInWords: Seq[String] = getBarebonesTweetText(text, stopWordsList.value)
    val polarity = model.predict(MLlibSentimentAnalyzer.transformFeatures(tweetInWords))
    normalizeMLlibSentiment(polarity)
  }

  /**
    * Normalize sentiment for visualization perspective.
    * We are normalizing sentiment as we need to be consistent with the polarity value with Core NLP and for visualization.
    *
    * @param sentiment polarity of the tweet
    * @return normalized to either -1, 0 or 1 based on tweet being negative, neutral and positive.
    */
  def normalizeMLlibSentiment(sentiment: Double) = {
    sentiment match {
      case x if x == 0 => -1 // negative
      case x if x == 2 => 0 // neutral
      case x if x == 4 => 1 // positive
      case _ => 0 // if cant figure the sentiment, term it as neutral
    }
  }

  /**
    * Strips the extra characters in tweets. And also removes stop words from the tweet text.
    *
    * @param tweetText     -- Complete text of a tweet.
    * @param stopWordsList -- Broadcast variable for list of stop words to be removed from the tweets.
    * @return Seq[String] after removing additional characters and stop words from the tweet.
    */
  def getBarebonesTweetText(tweetText: String, stopWordsList: List[String]): Seq[String] = {
    //Remove URLs, RT, MT and other redundant chars / strings from the tweets.
    tweetText.toLowerCase()
      .replaceAll("\n", "")
      .replaceAll("rt\\s+", "")
      .replaceAll("\\s+@\\w+", "")
      .replaceAll("@\\w+", "")
      .replaceAll("\\s+#\\w+", "")
      .replaceAll("#\\w+", "")
      .replaceAll("(?:https?|http?)://[\\w/%.-]+", "")
      .replaceAll("(?:https?|http?)://[\\w/%.-]+\\s+", "")
      .replaceAll("(?:https?|http?)//[\\w/%.-]+\\s+", "")
      .replaceAll("(?:https?|http?)//[\\w/%.-]+", "")
      .split("\\W+")
      .filter(_.matches("^[a-zA-Z]+$"))
      .filter(!stopWordsList.contains(_))
    //.fold("")((a,b) => a.trim + " " + b.trim).trim
  }

  val hashingTF = new HashingTF()

  /**
    * Transforms features to Vectors.
    *
    * @param tweetText -- Complete text of a tweet.
    * @return Vector
    */
  def transformFeatures(tweetText: Seq[String]): Vector = {
    hashingTF.transform(tweetText)
  }

}
