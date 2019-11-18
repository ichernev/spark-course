val sc = new SparkContext(...)
val userData = sc.sequenceFile[UserId, UserInfo]("hdfs://...")
  // .partitionBy(new HashPartitioner(100)
  .persist()

def processNewLogs(logFileName: String) {
  val events = sc.sequenceFile[UserId, LinkInfo](logFileName)
  val joined = userData.join(events) // RDD of (UserId, (UserInfo, LinkInfo))

  val offTopicVisits = joined.filter {
    case (userId, (userInfo, linkInfo)) =>
      !userInfo.topics.contains(linkInfo.topic)
    }.count()
  println("Number of visits to non-subscribed topics " + offTopicVisits)
}
