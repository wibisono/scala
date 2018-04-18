package scala.collection.mutable

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._
import org.openjdk.jmh.infra.Blackhole

@BenchmarkMode(Array(Mode.AverageTime))
@Fork(2)
@Threads(1)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
class QueueBenchmark {

  @Param(Array("10", "1000", "10000"))
  var size: Int = _
  var integers: List[Int] = _
  var strings: List[String] = _


  @Setup(Level.Trial) def initNumbers: Unit = {
    integers = (1 to size).toList
    strings = integers.map(_.toString)
  }

  @Benchmark def dequeueAllInteger(bh: Blackhole): Unit = {
    val queue = new Queue[Int]
    queue.enqueueAll(integers)
    queue.dequeueAll(_ % 2 == 0)
    bh.consume(queue)
  }

  @Benchmark def dequeueAllString(bh: Blackhole): Unit = {
    val queue = new Queue[String]
    queue.enqueueAll(strings)
    queue.dequeueAll(_.length % 2 == 0)
    bh.consume(queue)
  }

  @Benchmark def dequeueAPInteger(bh: Blackhole): Unit = {
    val queue = new Queue[Int]
    queue.enqueueAll(integers)
    queue.dequeueAP(_ % 2 == 0)
    bh.consume(queue)
  }

  @Benchmark def dequeueAPString(bh: Blackhole): Unit = {
    val queue = new Queue[String]
    queue.enqueueAll(strings)
    queue.dequeueAP(_.length % 2 == 0)
    bh.consume(queue)
  }
}
