

trait Simulation {
  def currentime: Int = ???
  def afterDelay(delay: Int)(block: => Unit): Unit = ???
  def run(): Unit= ???
}
trait Action

class Wire {
  private var sigVal = false
  private var actions: List[Action] = List()
  def getSignal: Boolean = sigVal
  def setSignal(s: Boolean) : Unit =
    if (s != sigVal) {
      sigVal = actions foreach (_())
    }

  def addAction(a: Action): Unit = {
    actions = a :: actions
    a()
  }
}

def inverter(input: Wire, output: Wire): Unit = {
  def invertAction(): Unit = {
    val inputSig = input.getSignal
    afterDelay(InverterDelay) {output setSignal !inputSig}
  }
  input addAction invertAction
}

def andGate(in1: Wire, in2: Wire, output: Wire): Unit = {
  def andAction(): Unit = {
    val in2Sig = in1.getSignal
    val in2Sig = in2.getSignal
    afterDelay(AndGateDel) {output setSignal(in1Sig & in2Sig)}
  }

  in1 addAction(andAction)
  in2 addAction(andAction)
}

def orGate(in1: Wire, in2: Wire, output: Wire): Unit = {
  def orAction(): Unit = {
    val in2Sig = in1.getSignal
    val in2Sig = in2.getSignal
    afterDelay(OrGateDel) {output setSignal(in1Sig | in2Sig)}
  }

  in1 addAction(andAction)
  in2 addAction(andAction)
}