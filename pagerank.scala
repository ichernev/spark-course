val links = sc.objectFile[(String, Seq[String])]("links")
  .partitionBy(new HashPartitioner(100))
  .persist()

// Keep partitioner by using mapValues
var ranks = links.mapValues(v => 1.0)

for (i <- 0 until 10) {
  val contributions = links.join(ranks).flatMap {
    case (pageId, (pageLinks, rank)) =>
      pageLinks.map(dest => (dest, rank / pageLinks.size))
  }
  ranks = contributions
    .reduceByKey((x, y) => x + y)
    .mapValues(v => 0.15 + 0.85 * v)
}

ranks.saveAsTextFile("ranks")
