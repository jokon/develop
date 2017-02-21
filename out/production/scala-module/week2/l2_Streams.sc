
// ((1 to 100).toStream filter isPrime) (1)

/*
trait StreamDef[+A] extends Seq[A] {
  def isEmpty : Boolean
  def head : A
  def tail:StreamDef[A]
  //...
}
*/

def streamRange(lo: Int, hi: Int) : Stream[Int] = {
  print(lo + " ")
  if (lo >= hi) Stream.empty
  else Stream.cons(lo, streamRange(lo + 1, hi))

}


streamRange(1,10).take(4).toList
