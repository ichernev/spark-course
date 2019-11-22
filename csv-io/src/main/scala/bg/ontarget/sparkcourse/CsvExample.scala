package bg.ontarget.sparkcourse

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

object CsvExample {
    def main(args: Array[String]) {

        val spark = org.apache.spark.sql.SparkSession.builder
            .appName("Spark CSV Reader")
            .getOrCreate;

        val df = spark.read
            .format("csv")
            .option("header", "true") //first line in file has headers
            .option("mode", "DROPMALFORMED")
            .load("sample.csv")

        df
            .select(df("Name"), df("Age") + 1)
            .write.csv("output2.csv")
        // val conf = new SparkConf()
        //   .setAppName("standalone app")
        // val sc = new SparkContext(conf)

        // val count = sc
        //   .textFile("README.md")
        //   .filter(l => l.contains("Python"))
        //   .count()

        // println("count is " + count)

        // sc.stop()
    }
}

