package church

object Church extends App {
  type Bool[T] = T => T => T

  def tru[A]: Bool[A] = t => f => t

  def fls[A]: Bool[A] = t => f => f

  def test[A]: (Bool[A]) => Bool[A] = l => m => n => l(m)(n)

  def and[A]: (Bool[A]) => (Bool[A]) => Bool[A] = (m) => (n) => a => b => m(n(a)(b))(m(a)(b))

  def or[A]: (Bool[A]) => (Bool[A]) => Bool[A] = (m) => (n) => a => b => m(m(a)(b))(n(a)(b))

  def not[A]: (Bool[A]) => Bool[A] = (m) => a => b => m(b)(a)

  implicit class Bool2Boolean(val b: Bool[Boolean]) {
    def toBoolean: Boolean = b(true)(false)
  }

  implicit class Boolean2Bool(val b: Boolean) {
    def toBool: Bool[Boolean] = if (b) tru else fls
  }

  assert(!and[Boolean](true.toBool)(false.toBool).toBoolean)

  type Pair[T] = (T => T => T) => T

  def pair[A]: A => A => Pair[A] = f => s => b => b(f)(s)

  def fst[A]: (Pair[A]) => A = p => p(tru)

  def snd[A]: (Pair[A]) => A = p => p(fls)

  val p1 = pair[Bool[Boolean]](tru)(fls)

  val p2 = pair[Bool[Boolean]](tru)(fls)

  assert(fst[Bool[Boolean]](p1).toBoolean)

  type Num[T] = (T => T) => T => T

  def zero[A]: Num[A] = s => z => z

  def succ[A]: Num[A] => Num[A] = n => s => z => s(n(s)(z))

  def plus[A]: (Num[A]) => (Num[A]) => Num[A] = m => n => s => z => m(s)(n(s)(z))

  def times[A]: (Num[A]) => (Num[A]) => Num[A] = m => n => m compose n

  implicit class Num2Int(val num: Num[Int]) {
    def toInt: Int = num(_ + 1)(0)
  }

  implicit class Int2Num(val n: Int) {
    def toNum: Num[Int] = {
      def aux(n: Int): Num[Int] = n match {
        case 0 => zero
        case x => succ[Int](aux(x - 1))
      }
      aux(n)
    }
  }

  assert(times[Int](succ[Int](succ[Int](zero)))(succ[Int](succ[Int](zero))).toInt == 4)

  assert(times[Int](3.toNum)(4.toNum).toInt == 12)

}
