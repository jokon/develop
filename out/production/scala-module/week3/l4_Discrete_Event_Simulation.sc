
class Wire

def inverter(input: Wire, output:Wire): Unit
def andGate(i1:Wire, i2: Wire, otuput: Wire) : Unit
def orGate(i1: Wire, i2: Wire, output: Wire): Unit

def halfAdder(a:Wire, b:Wire, s:Wire, c:Wire) :Unit = {
  val d = new Wire
  val e = new Wire
  orGate(a, b, d)
  andGate (a, b, c)
  inverter(c,e)
  andGate(d,e,s)
}
