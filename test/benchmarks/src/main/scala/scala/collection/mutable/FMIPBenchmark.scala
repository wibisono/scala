package scala.collection.mutable

import org.openjdk.jmh.annotations._
import org.openjdk.jmh.infra._
import org.openjdk.jmh.runner.IterationType
import benchmark._
import java.util.concurrent.TimeUnit

import scala.collection.mutable._

@BenchmarkMode(Array(Mode.AverageTime))
@Fork(2)
@Threads(1)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
class FMIPBenchmark {
  @Param(Array("10", "100", "1000", "5000"))
  var size: Int = _

  var map = new HashMap[Int, Int]()
  var set = new HashSet[Int]()

  @Setup(Level.Invocation) def initializeMutable = (0 to size).foreach { v => 
    { 
      map.put(v, v)
      set += v
    } 
  }

  @TearDown(Level.Invocation) def tearDown = {map.clear(); set.clear()}

  @Benchmark
  def mapFMIP(): Unit = {
    map.flatMapInPlace { case (k, v) => HashMap(k -> v, v -> k) }
    map.flatMapInPlace { case (_, v) => HashMap(1 -> v) }
  }

  @Benchmark
  def mapFMIP1(): Unit = {
    map.flatMapInPlace1 { case (k, v) => HashMap(k -> v, v -> k) }
    map.flatMapInPlace1 { case (_, v) => HashMap(1 -> v) }
  }

  @Benchmark
  def setFMIP(): Unit = {
    set.flatMapInPlace { e => HashSet(e+1, e+2, e+3) }
  }

  @Benchmark
  def setFMIP1(): Unit = {
    set.flatMapInPlace1 { e => HashSet(e+1, e+2, e+3) }
  }


}
