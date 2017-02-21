// => ?


def expr = {
  val x = {print("x"); 1}
  lazy val y = {print("y"); 2}
  def z = {print("z"); 3}
  z + y + x + z + y + x
}

expr


def cons[T] (hd: T, tl: => Stream[T]) = new Stream[T] {
  override def head = hd
  override lazy val tail = tl

}
