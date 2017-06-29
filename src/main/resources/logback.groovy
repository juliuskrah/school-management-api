def PATH = "target"
def fileName = "school-management"
// always a good idea to add an on console status listener
statusListener(OnConsoleStatusListener)
appender("STDOUT", ConsoleAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
  }
}
appender("FILE", FileAppender) {
  file = "${PATH}/${fileName}.log"
  encoder(PatternLayoutEncoder) {
    pattern = "%date %level [%thread] %logger{10} [%file:%line] %msg%n"
  }
}
appender("ROLLING", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    Pattern = "%d %level %thread %mdc %logger - %m%n"
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    FileNamePattern = "${PATH}/log/${fileName}-%d{yyyy-MM}.log.gz"
  }
}
logger("com.juliuskrah", DEBUG)
root(INFO, ["STDOUT", "FILE", "ROLLING"])