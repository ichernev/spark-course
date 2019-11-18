package bg.ontarget.sparkcourse

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

object WordCount {
    def main(args: Array[String]) {

        val conf = new SparkConf()
          .setAppName("standalone app")
        val sc = new SparkContext(conf)

        val count = sc
          .textFile("README.md")
          .filter(l => l.contains("Python"))
          .count()

        println("count is " + count)

        // /* configure spark application */
        // val conf = new SparkConf().setAppName("Spark Scala WordCount Example").setMaster("local[1]")

        // /* spark context*/
        // val sc = new SparkContext(conf)

        // /* map */
        // var map = sc.textFile("data/wordcount/input.txt").flatMap(line => line.split(" ")).map(word => (word,1))

        // /* reduce */
        // var counts = map.reduceByKey(_ + _)

        // /* print */
        // counts.collect().foreach(println)

        // /* or save the output to file */
        // counts.saveAsTextFile("out.txt")

        sc.stop()
    }
}

