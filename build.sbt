name := "Mllib_Model_Gen"

version := "1.0"

scalaVersion := "2.11.8"



val sparkVersion = "1.6.2"
val sparkCsvVersion = "1.4.0"
val configVersion = "1.3.0"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-streaming" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-mllib" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-streaming-twitter" % sparkVersion,
  "com.databricks" %% "spark-csv" % sparkCsvVersion,
  "com.typesafe" % "config" % configVersion
)
