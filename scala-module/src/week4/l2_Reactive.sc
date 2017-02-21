import sun.misc.Signal
import
//val sig = Var(3)
//sig.update(5)


class BankAccount extends {
  val balance = Var

  def deposit(amount: Int): Unit = {
    if (amount > 0) {
      balance = balance + amount
    }
  }

  def withdraw(amount: Int) : Unit = {
    if (0 < amount && amount <= balance) {
      balance = balance - amount
    } else throw new Error("Insuficient funds")
  }
}
