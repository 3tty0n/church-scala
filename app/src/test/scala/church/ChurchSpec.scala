package church

import org.scalatest._
import church.Church._

class ChurchSpec extends FlatSpec {

  "Church Boolean" should "behave properly" in {
    assert(and[Boolean](fls)(fls).toBoolean === false)
    assert(or[Boolean](tru)(fls).toBoolean === true)
    assert(not[Boolean](tru).toBoolean === false)
    assert(test[Int](fls)(1)(2) === 2)
  }

  "Church Pair" should "behave properly" in {
    val p1 = pair[Bool[Boolean]](tru)(fls)
    val p2 = pair[Bool[Boolean]](tru)(fls)
    assert(fst[Bool[Boolean]](p1).toBoolean === true)
    assert(snd[Bool[Boolean]](p2).toBoolean === false)
  }

  "Church Number" should "behave properly" in {
    assert(times[Int](succ(succ(zero)))(succ(zero)).toInt === 2)
    assert(plus[Int](succ(succ(succ(zero))))(succ(succ(zero))).toInt === 5)
  }

  "Church List" should "behave properly" in {
    val xs = cons(1)(cons(2)(cons(3)(nil)))
    assert(xs(x => y => x + y)(0) === 6)
    assert(head(xs) === 1)
  }
}
